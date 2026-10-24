package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class TaiKhoanPanel extends JPanel {

    // Khai báo các thành phần giao diện nhập liệu
    private JTextField txtMaTK;
    private JTextField txtTenDN;
    private JPasswordField txtMatKhau;
    private JTextField txtNhanVien;
    private JTextField txtTimKiem;

    private JComboBox<String> cboQuyen;
    private JComboBox<String> cboTrangThai;

    private JTable table;
    private DefaultTableModel tableModel;

    // Các nút chức năng để dễ dàng quản lý sự kiện và phân quyền
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JButton btnTim;

    // Lưu quyền của tài khoản hiện đang đăng nhập (Mặc định: "Quản lý")
    private String currentUserRole = "Quản lý";

    public TaiKhoanPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 248, 252));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1. Tiêu đề
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBackground(new Color(245, 248, 252));

        JLabel lblTieuDe = new JLabel(
                "QUẢN LÝ TÀI KHOẢN",
                SwingConstants.LEFT
        );

        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(new Color(35, 65, 95));

        pnlNorth.add(lblTieuDe, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);

        // 2. Khu vực thông tin tài khoản
        JPanel pnlThongTin = new JPanel(new GridLayout(3, 2, 15, 12));
        pnlThongTin.setBackground(Color.WHITE);

        pnlThongTin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Thông tin tài khoản"),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        txtMaTK = new JTextField();
        txtTenDN = new JTextField();
        txtMatKhau = new JPasswordField();
        txtNhanVien = new JTextField();

        cboQuyen = new JComboBox<>(new String[] {
                "Quản lý", "Lễ tân", "Nha sĩ", "Phụ tá"
        });

        cboTrangThai = new JComboBox<>(new String[] {
                "Hoạt động", "Khóa"
        });

        pnlThongTin.add(createField("Mã tài khoản:", txtMaTK));
        pnlThongTin.add(createField("Tên đăng nhập:", txtTenDN));
        pnlThongTin.add(createField("Mật khẩu:", txtMatKhau));
        pnlThongTin.add(createField("Mã nhân viên:", txtNhanVien));
        pnlThongTin.add(createField("Quyền:", cboQuyen));
        pnlThongTin.add(createField("Trạng thái:", cboTrangThai));

        // 3. Các nút chức năng
        JPanel pnlButton = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 12, 8)
        );
        pnlButton.setBackground(Color.WHITE);

        btnThem = createButton("Thêm", new Color(15, 145, 135));
        btnSua = createButton("Sửa", new Color(45, 105, 175));
        btnXoa = createButton("Xóa", new Color(205, 75, 75));
        btnLamMoi = createButton("Làm mới", new Color(100, 115, 135));

        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnLamMoi);

        JPanel pnlTop = new JPanel(new BorderLayout(5, 8));
        pnlTop.setBackground(new Color(245, 248, 252));
        pnlTop.add(pnlThongTin, BorderLayout.CENTER);
        pnlTop.add(pnlButton, BorderLayout.SOUTH);

        // 4. Bảng danh sách tài khoản
        String[] headers = {
                "Mã tài khoản",
                "Tên đăng nhập",
                "Mật khẩu",
                "Mã nhân viên",
                "Quyền",
                "Trạng thái"
        };

        tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(195, 240, 233));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(225, 230, 235));

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(35, 65, 95));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder(
                "Danh sách tài khoản"
        ));

        // 5. Tìm kiếm
        JPanel pnlTimKiem = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 8)
        );
        pnlTimKiem.setBackground(Color.WHITE);

        txtTimKiem = new JTextField(22);
        btnTim = createButton("Tìm kiếm", new Color(15, 145, 135));

        pnlTimKiem.add(new JLabel("Tên đăng nhập / Mã nhân viên:"));
        pnlTimKiem.add(txtTimKiem);
        pnlTimKiem.add(btnTim);

        JPanel pnlDanhSach = new JPanel(new BorderLayout(5, 8));
        pnlDanhSach.setBackground(new Color(245, 248, 252));
        pnlDanhSach.add(pnlTimKiem, BorderLayout.NORTH);
        pnlDanhSach.add(scroll, BorderLayout.CENTER);

        // 6. Ghép các khu vực
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 12));
        pnlCenter.setBackground(new Color(245, 248, 252));

        pnlCenter.add(pnlTop, BorderLayout.NORTH);
        pnlCenter.add(pnlDanhSach, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        // --- 7. XỬ LÝ SỰ KIỆN VÀ PHÂN QUYỀN ---
        initEventHandlers();
        apDungPhanQuyen(currentUserRole);
    }

    private JPanel createField(String label, JComponent input) {
        JPanel panel = new JPanel(new BorderLayout(8, 5));
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(input, BorderLayout.CENTER);

        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(105, 35));

        return button;
    }

    // ==========================================
    // CÀI ĐẶT SỰ KIỆN CHO CÁC NÚT & BẢNG
    // ==========================================
    private void initEventHandlers() {
        btnThem.addActionListener(e -> xuLyThem());
        btnSua.addActionListener(e -> xuLySua());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnLamMoi.addActionListener(e -> xuLyLamMoi());
        btnTim.addActionListener(e -> xuLyTimKiem());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hienThiChiTietLenForm();
            }
        });
    }

    private void xuLyThem() {
        String maTK = txtMaTK.getText().trim();
        String tenDN = txtTenDN.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();
        String maNV = txtNhanVien.getText().trim();
        String quyen = cboQuyen.getSelectedItem().toString();
        String trangThai = cboTrangThai.getSelectedItem().toString();

        if (maTK.isEmpty() || tenDN.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ Mã tài khoản, Tên đăng nhập và Mật khẩu!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Thêm dữ liệu vào bảng giao diện
        Object[] rowData = {maTK, tenDN, matKhau, maNV, quyen, trangThai};
        tableModel.addRow(rowData);

        JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
        xuLyLamMoi();
    }

    private void xuLySua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn dòng cần sửa trên bảng!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setValueAt(txtMaTK.getText().trim(), selectedRow, 0);
        tableModel.setValueAt(txtTenDN.getText().trim(), selectedRow, 1);
        tableModel.setValueAt(new String(txtMatKhau.getPassword()).trim(), selectedRow, 2);
        tableModel.setValueAt(txtNhanVien.getText().trim(), selectedRow, 3);
        tableModel.setValueAt(cboQuyen.getSelectedItem().toString(), selectedRow, 4);
        tableModel.setValueAt(cboTrangThai.getSelectedItem().toString(), selectedRow, 5);

        JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!");
    }

    private void xuLyXoa() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn dòng cần xóa trên bảng!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa tài khoản này?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!");
            xuLyLamMoi();
        }
    }

    private void xuLyLamMoi() {
        txtMaTK.setText("");
        txtTenDN.setText("");
        txtMatKhau.setText("");
        txtNhanVien.setText("");
        cboQuyen.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        txtTimKiem.setText("");
        table.clearSelection();
    }

    private void xuLyTimKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String tenDN = tableModel.getValueAt(i, 1).toString().toLowerCase();
            String maNV = tableModel.getValueAt(i, 3).toString().toLowerCase();

            if (tenDN.contains(keyword) || maNV.contains(keyword)) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản phù hợp!");
    }

    private void hienThiChiTietLenForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaTK.setText(tableModel.getValueAt(row, 0).toString());
            txtTenDN.setText(tableModel.getValueAt(row, 1).toString());
            txtMatKhau.setText(tableModel.getValueAt(row, 2).toString());
            txtNhanVien.setText(tableModel.getValueAt(row, 3).toString());
            cboQuyen.setSelectedItem(tableModel.getValueAt(row, 4).toString());
            cboTrangThai.setSelectedItem(tableModel.getValueAt(row, 5).toString());
        }
    }

    // ==========================================
    // HỆ THỐNG PHÂN QUYỀN (ROLE-BASED ACCESS CONTROL)
    // ==========================================
    public void apDungPhanQuyen(String role) {
        this.currentUserRole = role;

        switch (role) {
            case "Quản lý":
                // Quản lý toàn quyền thao tác CRUD
                setComponentEnabled(true);
                break;

            case "Lễ tân":
                // Lễ tân chỉ được sửa thông tin cơ bản, KHÔNG được thêm/xóa tài khoản hoặc đổi quyền
                btnThem.setEnabled(false);
                btnXoa.setEnabled(false);
                btnSua.setEnabled(true);
                cboQuyen.setEnabled(false);
                break;

            case "Nha sĩ":
            case "Phụ tá":
                // Các nhóm quyền chuyên môn không được phép vào quản lý tài khoản
                setComponentEnabled(false);
                JOptionPane.showMessageDialog(this,
                        "Bạn không có quyền truy cập chức năng Quản lý tài khoản!",
                        "Hạn chế quyền", JOptionPane.ERROR_MESSAGE);
                break;

            default:
                setComponentEnabled(false);
                break;
        }
    }

    private void setComponentEnabled(boolean status) {
        txtMaTK.setEnabled(status);
        txtTenDN.setEnabled(status);
        txtMatKhau.setEnabled(status);
        txtNhanVien.setEnabled(status);
        cboQuyen.setEnabled(status);
        cboTrangThai.setEnabled(status);
        btnThem.setEnabled(status);
        btnSua.setEnabled(status);
        btnXoa.setEnabled(status);
    }
}