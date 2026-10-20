
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class TaiKhoanPanel extends JPanel {

    private JTextField txtMaTK;
    private JTextField txtTenDN;
    private JPasswordField txtMatKhau;
    private JTextField txtNhanVien;

    private JComboBox cboQuyen;
    private JComboBox cboTrangThai;

    private JTable table;
    private DefaultTableModel tableModel;

    

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

        JButton btnThem = createButton("Thêm", new Color(15, 145, 135));
        JButton btnSua = createButton("Sửa", new Color(45, 105, 175));
        JButton btnXoa = createButton("Xóa", new Color(205, 75, 75));
        JButton btnLamMoi = createButton("Làm mới", new Color(100, 115, 135));

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

        table.getTableHeader().setFont( new Font("Arial", Font.BOLD, 13));
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

        JTextField txtTimKiem = new JTextField(22);
        JButton btnTim = createButton("Tìm kiếm", new Color(15, 145, 135));

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

        // xử lý
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
    
    
}
