
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

public class TimKiemNhanVienPanel extends JPanel {

    private final Color NAVY = new Color(35, 68, 105);
    private final Color BLUE = new Color(35, 119, 224);
    private final Color BG = new Color(243, 246, 251);
    private final Color TEXT = new Color(45, 55, 68);

    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtSoDienThoai;
    private JTextField txtEmail;

    private JComboBox<String> cboChucVu;
    private JComboBox<String> cboTrangThai;

    private JTable tblNhanVien;
    private DefaultTableModel model;

    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnXemChiTiet;

    public TimKiemNhanVienPanel() {

        setLayout(new BorderLayout());
        setBackground(BG);

        // Panel chính
        JPanel pnlChinh = new JPanel(new BorderLayout());
        pnlChinh.setBackground(BG);

        // Tiêu đề
        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(Color.WHITE);
        pnlTieuDe.setBorder(
                new EmptyBorder(17, 22, 17, 24)
        );

        JLabel lblTieuDe = new JLabel("Tìm kiếm nhân viên");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));
        lblTieuDe.setForeground(NAVY);
        lblTieuDe.setIcon(
                FontIcon.of(FontAwesomeSolid.SEARCH, 22, NAVY)
        );

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlChinh.add(pnlTieuDe, BorderLayout.NORTH);

        // Nội dung chính
        JPanel pnlNoiDung = new JPanel(
                new BorderLayout(0, 18)
        );
        pnlNoiDung.setBackground(BG);
        pnlNoiDung.setBorder(
                new EmptyBorder(18, 18, 18, 18)
        );

        // Khung tìm kiếm
        JPanel pnlTimKiem = new JPanel( new BorderLayout(0, 16));

        pnlTimKiem.setBackground(Color.WHITE);
        pnlTimKiem.setBorder(new CompoundBorder(new LineBorder(new Color(226, 231, 238)),new EmptyBorder(16, 16, 16, 16)));

        JLabel lblDieuKien = new JLabel("Điều kiện tìm kiếm");
        lblDieuKien.setFont(new Font("Arial", Font.BOLD, 17));
        lblDieuKien.setForeground(TEXT);

        pnlTimKiem.add(lblDieuKien, BorderLayout.NORTH);

        // Các trường tìm kiếm
        JPanel pnlKhungTimKiem = new JPanel(new GridLayout(2, 3, 14, 14));
        pnlKhungTimKiem.setBackground(Color.WHITE);

        // Mã nhân viên
        JPanel pnlMa = new JPanel(new BorderLayout(0, 6));
        pnlMa.setBackground(Color.WHITE);

        JLabel lblMa = new JLabel("Mã nhân viên");
        lblMa.setFont(new Font("Arial", Font.BOLD, 12));
        lblMa.setForeground(TEXT);

        txtMaNV = new JTextField();

        // Họ và tên
        JPanel pnlHoTen = new JPanel(new BorderLayout(0, 6));
        pnlHoTen.setBackground(Color.WHITE);

        JLabel lblHoTen = new JLabel("Họ và tên");
        lblHoTen.setFont(new Font("Arial", Font.BOLD, 12));
        lblHoTen.setForeground(TEXT);

        txtHoTen = new JTextField();

        // Số điện thoại
        JPanel pnlSDT = new JPanel(new BorderLayout(0, 6));
        pnlSDT.setBackground(Color.WHITE);

        JLabel lblSDT = new JLabel("Số điện thoại");
        lblSDT.setFont(new Font("Arial", Font.BOLD, 12));
        lblSDT.setForeground(TEXT);

        txtSoDienThoai = new JTextField();

        // Email
        JPanel pnlEmail = new JPanel(new BorderLayout(0, 6));
        pnlEmail.setBackground(Color.WHITE);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        lblEmail.setForeground(TEXT);

        txtEmail = new JTextField();

        // Chức vụ
        JPanel pnlChucVu = new JPanel(new BorderLayout(0, 6));
        pnlChucVu.setBackground(Color.WHITE);

        JLabel lblChucVu = new JLabel("Chức vụ");
        lblChucVu.setFont(new Font("Arial", Font.BOLD, 12));
        lblChucVu.setForeground(TEXT);

        cboChucVu = new JComboBox<>(
                new String[]{
                        "Tất cả chức vụ",
                        "Lễ tân",
                        "Bác sĩ",
                        "Phụ tá",
                        "Quản lý"
                }
        );

        // Trạng thái
        JPanel pnlTrangThai = new JPanel(new BorderLayout(0, 6));
        pnlTrangThai.setBackground(Color.WHITE);

        JLabel lblTrangThai = new JLabel("Trạng thái làm việc");
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 12));
        lblTrangThai.setForeground(TEXT);

        cboTrangThai = new JComboBox<>(
                new String[]{
                        "Tất cả trạng thái",
                        "Đang làm việc",
                        "Tạm nghỉ",
                        "Đã nghỉ việc"
                }
        );

        // Định dạng các ô nhập liệu
        dinhDangTextField(txtMaNV);
        dinhDangTextField(txtHoTen);
        dinhDangTextField(txtSoDienThoai);
        dinhDangTextField(txtEmail);

        // Định dạng danh sách lựa chọn
        dinhDangComboBox(cboChucVu);
        dinhDangComboBox(cboTrangThai);

        // Ghép nhãn và ô nhập liệu
        pnlMa.add(lblMa, BorderLayout.NORTH);
        pnlMa.add(txtMaNV, BorderLayout.CENTER);

        pnlHoTen.add(lblHoTen, BorderLayout.NORTH);
        pnlHoTen.add(txtHoTen, BorderLayout.CENTER);

        pnlSDT.add(lblSDT, BorderLayout.NORTH);
        pnlSDT.add(txtSoDienThoai, BorderLayout.CENTER);

        pnlEmail.add(lblEmail, BorderLayout.NORTH);
        pnlEmail.add(txtEmail, BorderLayout.CENTER);

        pnlChucVu.add(lblChucVu, BorderLayout.NORTH);
        pnlChucVu.add(cboChucVu, BorderLayout.CENTER);

        pnlTrangThai.add(lblTrangThai, BorderLayout.NORTH);
        pnlTrangThai.add(cboTrangThai, BorderLayout.CENTER);

        // Thêm các trường vào lưới
        pnlKhungTimKiem.add(pnlMa);
        pnlKhungTimKiem.add(pnlHoTen);
        pnlKhungTimKiem.add(pnlSDT);
        pnlKhungTimKiem.add(pnlEmail);
        pnlKhungTimKiem.add(pnlChucVu);
        pnlKhungTimKiem.add(pnlTrangThai);

        // Các nút chức năng
        JPanel pnlNut = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlNut.setBackground(Color.WHITE);

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setIcon(FontIcon.of(FontAwesomeSolid.SEARCH, 14, Color.WHITE));

        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 12));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setBackground(BLUE);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnTimKiem.setBorder(new LineBorder(BLUE));

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, 14, TEXT));
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 12));
        btnLamMoi.setForeground(TEXT);
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLamMoi.setBorder(new LineBorder(new Color(215, 220, 227)));

        pnlNut.add(btnTimKiem);
        pnlNut.add(btnLamMoi);

        // Ghép nội dung tìm kiếm và các nút
        JPanel pnlNoiDungTimKiem = new JPanel(new BorderLayout(0, 14));
        pnlNoiDungTimKiem.setBackground(Color.WHITE);
        pnlNoiDungTimKiem.add(pnlKhungTimKiem, BorderLayout.CENTER);
        pnlNoiDungTimKiem.add(pnlNut, BorderLayout.SOUTH);

        pnlTimKiem.add(pnlNoiDungTimKiem, BorderLayout.CENTER);

        // Khung bảng kết quả
        JPanel pnlBang = new JPanel(new BorderLayout(0, 14));
        pnlBang.setBackground(Color.WHITE);
        pnlBang.setBorder(new CompoundBorder(new LineBorder(new Color(226, 231, 238)),new EmptyBorder(16, 16, 16, 16)));

        JLabel lblKetQua = new JLabel("Kết quả tìm kiếm");
        lblKetQua.setFont(new Font("Arial", Font.BOLD, 17));
        lblKetQua.setForeground(TEXT);

        pnlBang.add(lblKetQua, BorderLayout.NORTH);

        // Tên các cột trong bảng
        String[] arrCot = {
                "STT",
                "MÃ NHÂN VIÊN",
                "HỌ VÀ TÊN",
                "SỐ ĐIỆN THOẠI",
                "EMAIL",
                "CHỨC VỤ",
                "TRẠNG THÁI"
        };

        // Tạo bảng trống, không có dữ liệu mẫu
        model = new DefaultTableModel(arrCot, 0) {
            @Override
            public boolean isCellEditable(
                    int row, int column
            ) {
                return false;
            }
        };

        tblNhanVien = new JTable(model);
        tblNhanVien.setRowHeight(42);
        tblNhanVien.setFont(new Font("Arial", Font.PLAIN, 12));
        tblNhanVien.setForeground(TEXT);
        tblNhanVien.setGridColor(new Color(238, 241, 245));
        tblNhanVien.setShowVerticalLines(false);
        tblNhanVien.setSelectionBackground(new Color(239, 247, 255));
        tblNhanVien.setSelectionForeground(TEXT);
        tblNhanVien.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Định dạng tiêu đề bảng
        JTableHeader tblHeader =tblNhanVien.getTableHeader();

        tblHeader.setPreferredSize(new Dimension(0, 44));
        tblHeader.setFont(new Font("Arial", Font.BOLD, 11));
        tblHeader.setForeground(new Color(105, 115, 126));
        tblHeader.setBackground(new Color(248, 249, 251));
        tblHeader.setReorderingAllowed(false);

        // Độ rộng các cột
        int[] arrDoRong = {
                45, 110, 180, 130, 190, 100, 140
        };

        for (int i = 0; i < arrDoRong.length; i++) {
            tblNhanVien.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(arrDoRong[i]);
        }

        JScrollPane scrBang = new JScrollPane(tblNhanVien);
        scrBang.setBorder( BorderFactory.createEmptyBorder());
        scrBang.getViewport().setBackground(Color.WHITE);

        pnlBang.add(scrBang, BorderLayout.CENTER);

        // Phần cuối bảng
        JPanel pnlCuoiBang = new JPanel(new BorderLayout());
        pnlCuoiBang.setBackground(Color.WHITE);

        JLabel lblHuongDan = new JLabel("Vui lòng chọn một nhân viên trong bảng");
        lblHuongDan.setFont(new Font("Arial", Font.PLAIN, 12));
        lblHuongDan.setForeground(new Color(130, 140, 150));

        btnXemChiTiet = new JButton("Xem chi tiết");
        btnXemChiTiet.setIcon(FontIcon.of(FontAwesomeSolid.EYE, 14, Color.WHITE));
        btnXemChiTiet.setFont(new Font("Arial", Font.BOLD, 12));
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setBackground(BLUE);
        btnXemChiTiet.setFocusPainted(false);
        btnXemChiTiet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnXemChiTiet.setBorder(new LineBorder(BLUE));

        pnlCuoiBang.add(lblHuongDan, BorderLayout.WEST);
        pnlCuoiBang.add(btnXemChiTiet, BorderLayout.EAST);

        pnlBang.add(pnlCuoiBang, BorderLayout.SOUTH);

        pnlNoiDung.add(pnlTimKiem, BorderLayout.NORTH);
        pnlNoiDung.add(pnlBang, BorderLayout.CENTER);

        pnlChinh.add(pnlNoiDung, BorderLayout.CENTER);

        add(pnlChinh, BorderLayout.CENTER);
    }


    private void dinhDangTextField(JTextField txt) {
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setForeground(TEXT);
        txt.setBackground(Color.WHITE);
        txt.setPreferredSize(new Dimension(120, 36));
        txt.setBorder(new LineBorder(new Color(211, 217, 225)));
    }

    private void dinhDangComboBox(JComboBox<String> cbo) {
        cbo.setFont(new Font("Arial", Font.PLAIN, 12));
        cbo.setForeground(TEXT);
        cbo.setBackground(Color.WHITE);
        cbo.setPreferredSize(new Dimension(120, 36));
        cbo.setBorder(
                new LineBorder(new Color(211, 217, 225))
        );
    }


    public JTextField getTxtMaNV() {
        return txtMaNV;
    }

    public JTextField getTxtHoTen() {
        return txtHoTen;
    }

    public JTextField getTxtSoDienThoai() {
        return txtSoDienThoai;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JComboBox<String> getCboChucVu() {
        return cboChucVu;
    }

    public JComboBox<String> getCboTrangThai() {
        return cboTrangThai;
    }

    public JTable getTblNhanVien() {
        return tblNhanVien;
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
