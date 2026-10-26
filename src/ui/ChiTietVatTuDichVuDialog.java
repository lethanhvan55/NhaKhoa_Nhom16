package ui;

import dao.ChiTietVatTu_DichVu_DAO;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ChiTietVatTuDichVuDialog extends JDialog {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnLuu, btnHuy;
    private String maDV;
    private ChiTietVatTu_DichVu_DAO dao = new ChiTietVatTu_DichVu_DAO();
    private boolean isSaved = false;

    public ChiTietVatTuDichVuDialog(Window parent, String maDV, String tenDV) {
        super(parent, "Quản lý Vật tư cho Dịch vụ: " + tenDV, Dialog.ModalityType.APPLICATION_MODAL);
        this.maDV = maDV;

        setSize(750, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initUI();
        loadData();
    }

    private void initUI() {
        JPanel pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.setBorder(new EmptyBorder(15, 15, 15, 15));
        pnlMain.setBackground(new Color(245, 248, 252));

        // Tiêu đề
        JLabel lblTieuDe = new JLabel("CHỌN VẬT TƯ TIÊU HAO CHO DỊCH VỤ: " + maDV);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 16));
        lblTieuDe.setForeground(new Color(34, 115, 170));
        pnlMain.add(lblTieuDe, BorderLayout.NORTH);

        // Bảng danh sách vật tư
        String[] columns = {"Chọn", "Mã vật tư", "Tên vật tư", "ĐVT", "Tồn kho", "Số lượng sử dụng"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // Cột checkbox
                if (columnIndex == 4 || columnIndex == 5) return Integer.class; // Cột số lượng
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 5; // Chỉ cho phép tích chọn và sửa số lượng sử dụng
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        // Định độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(220);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        // Nút chức năng
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setOpaque(false);

        btnLuu = new JButton("Lưu thay đổi");
        btnLuu.setFont(new Font("Arial", Font.BOLD, 13));
        btnLuu.setBackground(new Color(15, 145, 135));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFocusPainted(false);

        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setFont(new Font("Arial", Font.BOLD, 13));
        btnHuy.setBackground(new Color(110, 120, 135));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFocusPainted(false);

        pnlButtons.add(btnLuu);
        pnlButtons.add(btnHuy);
        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        add(pnlMain);

        // Sự kiện nút bấm
        btnLuu.addActionListener(e -> xuLyLuu());
        btnHuy.addActionListener(e -> dispose());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Object[]> list = dao.getDanhSachVatTuVaSuDung(maDV);
        for (Object[] row : list) {
            tableModel.addRow(row);
        }
    }

    private void xuLyLuu() {
        // Kiểm tra hợp lệ số lượng sử dụng
        List<Object[]> updatedList = new java.util.ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            boolean chon = (boolean) tableModel.getValueAt(i, 0);
            if (chon) {
                try {
                    int soLuong = Integer.parseInt(tableModel.getValueAt(i, 5).toString());
                    if (soLuong <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng sử dụng của vật tư ở dòng " + (i + 1) + " phải lớn hơn 0!", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    tableModel.setValueAt(soLuong, i, 5);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng sử dụng phải là số nguyên hợp lệ!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            updatedList.add(new Object[]{
                    tableModel.getValueAt(i, 0),
                    tableModel.getValueAt(i, 1),
                    tableModel.getValueAt(i, 2),
                    tableModel.getValueAt(i, 3),
                    tableModel.getValueAt(i, 4),
                    tableModel.getValueAt(i, 5)
            });
        }

        // Lưu xuống cơ sở dữ liệu
        boolean success = dao.capNhatVatTuChoDichVu(maDV, updatedList);
        if (success) {
            JOptionPane.showMessageDialog(this, "Đã cập nhật danh sách vật tư cho dịch vụ thành công!");
            isSaved = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu xuống cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return isSaved;
    }
}