
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class TimKiemDichVuPanel extends JPanel {

    private final Color NAVY = new Color(18, 39, 70);
    private final Color BLUE = new Color(27, 119, 235);
    private final Color BG = new Color(243, 246, 250);
    private final Color TEXT = new Color(45, 55, 68);

    private JTextField txtMaDichVu;
    private JTextField txtTenDichVu;
    private JTextField txtGiaTu;
    private JTextField txtGiaDen;

    private JComboBox<String> cboTrangThai;

    private JTable tblDichVu;
    private DefaultTableModel model;

    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnXemChiTiet;

    public TimKiemDichVuPanel() {
        setLayout(new BorderLayout());
        setBackground(BG);

        // Panel chính
        JPanel pnlChinh = new JPanel(new BorderLayout());
        pnlChinh.setBackground(BG);

        // Tiêu đề
        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(Color.WHITE);
        pnlTieuDe.setBorder(new EmptyBorder(17, 22, 17, 24));

        JLabel lblTieuDe = new JLabel("Tìm kiếm dịch vụ");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));
        lblTieuDe.setForeground(NAVY);
        lblTieuDe.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, 22, NAVY)
        );

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlChinh.add(pnlTieuDe, BorderLayout.NORTH);

        // Nội dung chính
        JPanel pnlNoiDung = new JPanel(new BorderLayout(0, 18));
        pnlNoiDung.setBackground(BG);
        pnlNoiDung.setBorder(new EmptyBorder(18, 18, 18, 18));

        // Khung tìm kiếm
        JPanel pnlTimKiem = new JPanel(new BorderLayout(0, 16));
        pnlTimKiem.setBackground(Color.WHITE);
        pnlTimKiem.setBorder( new CompoundBorder( new LineBorder(new Color(226, 231, 238)),new EmptyBorder(16, 16, 16, 16)));

        JLabel lblDieuKien = new JLabel("Điều kiện tìm kiếm");
        lblDieuKien.setFont(new Font("Arial", Font.BOLD, 17));
        lblDieuKien.setForeground(TEXT);

        pnlTimKiem.add(lblDieuKien, BorderLayout.NORTH);

        JPanel pnlKhungTimKiem = new JPanel(new GridLayout(2, 3, 14, 14));
        pnlKhungTimKiem.setBackground(Color.WHITE);
        // Mã dịch vụ
        JPanel pnlMa = new JPanel(new BorderLayout(0, 6));
        pnlMa.setBackground(Color.WHITE);

        JLabel lblMa = new JLabel("Mã dịch vụ");
        lblMa.setFont(new Font("Arial", Font.BOLD, 12));
        lblMa.setForeground(TEXT);

        txtMaDichVu = new JTextField();

        // Tên dịch vụ
        JPanel pnlTen = new JPanel(new BorderLayout(0, 6));
        pnlTen.setOpaque(false);

        JLabel lblTen = new JLabel("Tên dịch vụ");
        lblTen.setFont(new Font("Arial", Font.BOLD, 12));
        lblTen.setForeground(TEXT);

        txtTenDichVu = new JTextField();

        // Trạng thái
        JPanel pnlTrangThai = new JPanel(new BorderLayout(0, 6));
        pnlTrangThai.setBackground(Color.WHITE);

        JLabel lblTrangThai = new JLabel("Trạng thái");
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 12));
        lblTrangThai.setForeground(TEXT);

        cboTrangThai = new JComboBox<>(new String[]{"Tất cả","Đang cung cấp","Ngừng cung cấp"});

        // Giá từ
        JPanel pnlGiaTu = new JPanel(new BorderLayout(0, 6));
        pnlGiaTu.setBackground(Color.WHITE);

        JLabel lblGiaTu = new JLabel("Giá từ (VNĐ)");
        lblGiaTu.setFont(new Font("Arial", Font.BOLD, 12));
        lblGiaTu.setForeground(TEXT);

        txtGiaTu = new JTextField();

        // Giá đến
        JPanel pnlGiaDen = new JPanel(new BorderLayout(0, 6));
        pnlGiaDen.setBackground(Color.WHITE);

        JLabel lblGiaDen = new JLabel("Giá đến (VNĐ)");
        lblGiaDen.setFont(new Font("Arial", Font.BOLD, 12));
        lblGiaDen.setForeground(TEXT);

        txtGiaDen = new JTextField();

        // Định dạng các ô nhập liệu
        txtMaDichVu.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMaDichVu.setForeground(TEXT);
        txtMaDichVu.setBackground(Color.WHITE);
        txtMaDichVu.setPreferredSize(new Dimension(120, 36));
        txtMaDichVu.setBorder(new LineBorder(new Color(211, 217, 225)));

        txtTenDichVu.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTenDichVu.setForeground(TEXT);
        txtTenDichVu.setBackground(Color.WHITE);
        txtTenDichVu.setPreferredSize(new Dimension(120, 36));
        txtTenDichVu.setBorder(new LineBorder(new Color(211, 217, 225)));

        txtGiaTu.setFont(new Font("Arial", Font.PLAIN, 13));
        txtGiaTu.setForeground(TEXT);
        txtGiaTu.setBackground(Color.WHITE);
        txtGiaTu.setPreferredSize(new Dimension(120, 36));
        txtGiaTu.setBorder(new LineBorder(new Color(211, 217, 225)));

        txtGiaDen.setFont(new Font("Arial", Font.PLAIN, 13));
        txtGiaDen.setForeground(TEXT);
        txtGiaDen.setBackground(Color.WHITE);
        txtGiaDen.setPreferredSize(new Dimension(120, 36));
        txtGiaDen.setBorder(new LineBorder(new Color(211, 217, 225)));

        // Định dạng combobox
        cboTrangThai.setFont(new Font("Arial", Font.PLAIN, 13));
        cboTrangThai.setForeground(TEXT);
        cboTrangThai.setBackground(Color.WHITE);
        cboTrangThai.setPreferredSize(new Dimension(120, 36));
        cboTrangThai.setBorder(new LineBorder(new Color(211, 217, 225)));

        // Ghép nhãn và ô nhập liệu
        pnlMa.add(lblMa, BorderLayout.NORTH);
        pnlMa.add(txtMaDichVu, BorderLayout.CENTER);

        pnlTen.add(lblTen, BorderLayout.NORTH);
        pnlTen.add(txtTenDichVu, BorderLayout.CENTER);

        pnlTrangThai.add(lblTrangThai, BorderLayout.NORTH);
        pnlTrangThai.add(cboTrangThai, BorderLayout.CENTER);

        pnlGiaTu.add(lblGiaTu, BorderLayout.NORTH);
        pnlGiaTu.add(txtGiaTu, BorderLayout.CENTER);

        pnlGiaDen.add(lblGiaDen, BorderLayout.NORTH);
        pnlGiaDen.add(txtGiaDen, BorderLayout.CENTER);

        // Nút chức năng
        JPanel pnlNut = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlNut.setBackground(Color.WHITE);

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, 14, Color.WHITE));

        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 12));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setBackground(BLUE);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, 14, TEXT));

        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 12));
        btnLamMoi.setForeground(TEXT);
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnTimKiem.setBorder( new LineBorder(BLUE));
        btnLamMoi.setBorder(new LineBorder(new Color(215, 220, 227)));

        pnlNut.add(btnTimKiem);
        pnlNut.add(btnLamMoi);

        pnlKhungTimKiem.add(pnlMa);
        pnlKhungTimKiem.add(pnlTen);
        pnlKhungTimKiem.add(pnlTrangThai);
        pnlKhungTimKiem.add(pnlGiaTu);
        pnlKhungTimKiem.add(pnlGiaDen);
        pnlKhungTimKiem.add(pnlNut);

        pnlTimKiem.add(pnlKhungTimKiem, BorderLayout.CENTER);

        // Khung bảng kết quả
        JPanel pnlBang = new JPanel(new BorderLayout(0, 14));
        pnlBang.setBackground(Color.WHITE);
        pnlBang.setBorder(new CompoundBorder(new LineBorder(new Color(226, 231, 238)), new EmptyBorder(16, 16, 16, 16)));

        JLabel lblKetQua = new JLabel("Kết quả tìm kiếm");
        lblKetQua.setFont(new Font("Arial", Font.BOLD, 17));
        lblKetQua.setForeground(TEXT);

        pnlBang.add(lblKetQua, BorderLayout.NORTH);

        String[] columns = {
                "STT",
                "MÃ DỊCH VỤ",
                "TÊN DỊCH VỤ",
                "ĐƠN GIÁ (VNĐ)",
                "NGÀY DỰ KIẾN",
                "TRẠNG THÁI"
        };

        // Bảng trống, không có dữ liệu mẫu
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(model);
        tblDichVu.setRowHeight(46);
        tblDichVu.setFont(new Font("Arial", Font.PLAIN, 13));
        tblDichVu.setForeground(TEXT);
        tblDichVu.setGridColor(new Color(238, 241, 245));
        tblDichVu.setShowVerticalLines(false);
        tblDichVu.setSelectionBackground(new Color(239, 247, 255));
        tblDichVu.setSelectionForeground(TEXT);

        JTableHeader tblHeader = tblDichVu.getTableHeader();
        tblHeader.setPreferredSize(new Dimension(0, 44));
        tblHeader.setFont(new Font("Arial", Font.BOLD, 11));
        tblHeader.setForeground(new Color(105, 115, 126));
        tblHeader.setBackground(new Color(248, 249, 251));
        tblHeader.setReorderingAllowed(false);

        int[] arrDoRong = {45, 100, 180, 130, 125, 130};

        for (int i = 0; i < arrDoRong.length; i++) {
            tblDichVu.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(arrDoRong[i]);
        }

        JScrollPane scrBang = new JScrollPane(tblDichVu);
        scrBang.setBorder(BorderFactory.createEmptyBorder());
        scrBang.getViewport().setBackground(Color.WHITE);

        pnlBang.add(scrBang, BorderLayout.CENTER);

        // Phần cuối bảng
        JPanel pnlCuoiBang = new JPanel(new BorderLayout());
        pnlCuoiBang.setBackground(Color.WHITE);

        JLabel lblHuongDan = new JLabel(
                "Vui lòng chọn một dịch vụ trong bảng"
        );
        lblHuongDan.setFont(new Font("Arial", Font.PLAIN, 12));
        lblHuongDan.setForeground(new Color(130, 140, 150));

        btnXemChiTiet = new JButton("Xem chi tiết");
        btnXemChiTiet.setIcon(FontIcon.of(FontAwesomeSolid.EYE, 14, Color.WHITE));

        btnXemChiTiet.setFont(new Font("Arial", Font.BOLD, 12));
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setBackground(BLUE);
        btnXemChiTiet.setFocusPainted(false);
        btnXemChiTiet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnlCuoiBang.add(lblHuongDan, BorderLayout.WEST);
        pnlCuoiBang.add(btnXemChiTiet, BorderLayout.EAST);

        pnlBang.add(pnlCuoiBang, BorderLayout.SOUTH);

        pnlNoiDung.add(pnlTimKiem, BorderLayout.NORTH);
        pnlNoiDung.add(pnlBang, BorderLayout.CENTER);

        pnlChinh.add(pnlNoiDung, BorderLayout.CENTER);

        add(pnlChinh, BorderLayout.CENTER);
    }


    public JTextField getTxtMaDichVu() {
        return txtMaDichVu;
    }

    public JTextField getTxtTenDichVu() {
        return txtTenDichVu;
    }

    public JTextField getTxtGiaTu() {
        return txtGiaTu;
    }

    public JTextField getTxtGiaDen() {
        return txtGiaDen;
    }

    public JComboBox<String> getCboTrangThai() {
        return cboTrangThai;
    }

    public JTable getTblDichVu() {
        return tblDichVu;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JButton getBtnTimKiem() {
        return btnTimKiem;
    }

    public JButton getBtnLamMoi() {
        return btnLamMoi;
    }

    public JButton getBtnXemChiTiet() {
        return btnXemChiTiet;
    }
}
