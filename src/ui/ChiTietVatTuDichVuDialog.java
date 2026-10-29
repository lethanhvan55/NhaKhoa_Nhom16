package ui;

import dao.ChiTietVatTu_DichVu_DAO;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

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
        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        // Định độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(220);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);

        // Đăng ký custom Editor và Renderer cho cột "Số lượng sử dụng" (Cột 5)
        table.getColumnModel().getColumn(5).setCellEditor(new SpinnerEditor());
        table.getColumnModel().getColumn(5).setCellRenderer(new SpinnerRenderer());

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
            // Nếu số lượng chưa có hoặc null, mặc định gán là 0
            if (row[5] == null) {
                row[5] = 0;
            }
            tableModel.addRow(row);
        }
    }

    private void xuLyLuu() {
        // Dừng cell đang chỉnh sửa để nhận giá trị mới nhất từ JSpinner
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        List<Object[]> updatedList = new java.util.ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            boolean chon = (boolean) tableModel.getValueAt(i, 0);
            if (chon) {
                try {
                    int soLuong = Integer.parseInt(tableModel.getValueAt(i, 5).toString());
                    if (soLuong <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng sử dụng của vật tư ở dòng " + (i + 1) + " phải lớn hơn 0 khi đã chọn!", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
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

    // ==================== CUSTOM SPINNER EDITOR & RENDERER (Bắt đầu từ 0) ====================

    private static class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
        private final JSpinner spinner;

        public SpinnerEditor() {
            // Thiết lập giá trị tối thiểu là 0
            spinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
            editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            int val = 0;
            if (value != null) {
                try {
                    val = Integer.parseInt(value.toString());
                    if (val < 0) val = 0;
                } catch (Exception ignored) {}
            }
            spinner.setValue(val);
            return spinner;
        }

        @Override
        public Object getCellEditorValue() {
            return spinner.getValue();
        }
    }

    private static class SpinnerRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
        private final JSpinner spinner;

        public SpinnerRenderer() {
            spinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
            editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            int val = 0;
            if (value != null) {
                try {
                    val = Integer.parseInt(value.toString());
                } catch (Exception ignored) {}
            }
            spinner.setValue(val);
            
            if (isSelected) {
                spinner.setBackground(table.getSelectionBackground());
            } else {
                spinner.setBackground(table.getBackground());
            }
            return spinner;
        }
    }
}