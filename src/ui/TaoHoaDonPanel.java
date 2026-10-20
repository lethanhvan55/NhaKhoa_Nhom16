package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class TaoHoaDonPanel extends JPanel {

    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;
    private final Color MAU_DO = GiaoDienStyle.MAU_DO;

    private JTable tblLichHen;
    private JTable tblDichVu;

    private JTextField txtTimKiem;
    private JComboBox<String> cboTrangThai;

    private JTextField txtMaLichHen;
    private JTextField txtMaBenhNhan;
    private JTextField txtTenKhachHang;
    private JTextField txtSoDienThoai;
    private JTextField txtNgayHen;
    private JTextField txtGioHen;
    private JTextField txtSoKhach;
    private JTextField txtGhiChu;

    private JTextField txtMaGiamGia;
    private JTextField txtTongTien;
    private JTextField txtChietKhau;
    private JTextField txtThue;
    private JTextField txtTienCoc;
    private JTextField txtCanThanhToan;

    private JButton btnTienMat;
    private JButton btnChuyenKhoan;
    private JButton btnThanhToan;

    public TaoHoaDonPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoGiaoDien();
    }

    private void taoGiaoDien() {
        JLabel lblTieuDe = new JLabel("Tạo hóa đơn");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 23));
        lblTieuDe.setForeground(MAU_NAVY);
        lblTieuDe.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        add(lblTieuDe, BorderLayout.NORTH);

        JPanel pnlBenTrai = taoBenTrai();
        JPanel pnlBenPhai = taoBenPhai();

        JSplitPane splitTong = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                pnlBenTrai,
                pnlBenPhai
        );

        splitTong.setResizeWeight(0.57);
        splitTong.setDividerLocation(650);
        splitTong.setDividerSize(8);
        splitTong.setBorder(null);
        splitTong.setBackground(MAU_NEN);

        add(splitTong, BorderLayout.CENTER);
    }

    // ==================== BÊN TRÁI ====================

    private JPanel taoBenTrai() {
        JPanel pnlBenTrai = new JPanel(new BorderLayout(0, 10));
        pnlBenTrai.setBackground(MAU_NEN);

        JPanel pnlTimKiem = taoKhuVucTimKiem();
        JPanel pnlDanhSach = taoKhuVucDanhSachLichHen();
        JPanel pnlDichVu = taoKhuVucDichVu();

        JSplitPane splitDuoi = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                pnlDanhSach,
                pnlDichVu
        );

        splitDuoi.setResizeWeight(0.58);
        splitDuoi.setDividerLocation(300);
        splitDuoi.setDividerSize(7);
        splitDuoi.setBorder(null);
        splitDuoi.setBackground(MAU_NEN);

        pnlBenTrai.add(pnlTimKiem, BorderLayout.NORTH);
        pnlBenTrai.add(splitDuoi, BorderLayout.CENTER);

        return pnlBenTrai;
    }

    private JPanel taoKhuVucTimKiem() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(8, 8));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Tìm kiếm lịch hẹn");

        JPanel pnlNhap = new JPanel(new BorderLayout(8, 0));
        pnlNhap.setBackground(MAU_TRANG);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTimKiem.setPreferredSize(new Dimension(200, 35));
        txtTimKiem.setToolTipText("Nhập mã lịch hẹn, tên khách hàng hoặc số điện thoại");

        JButton btnTimKiem = taoNut("Tìm kiếm", MAU_NAVY);
        JButton btnLamMoi = taoNut("Làm mới", new Color(110, 120, 135));

        JPanel pnlNut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        pnlNut.setBackground(MAU_TRANG);
        pnlNut.add(btnTimKiem);
        pnlNut.add(btnLamMoi);

        pnlNhap.add(txtTimKiem, BorderLayout.CENTER);
        pnlNhap.add(pnlNut, BorderLayout.EAST);

        JPanel pnlTrangThai = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlTrangThai.setBackground(MAU_TRANG);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Arial", Font.PLAIN, 13));

        cboTrangThai = new JComboBox<String>(new String[] {
                "Tất cả",
                "Chờ xác nhận",
                "Đã xác nhận",
                "Đã hoàn thành",
                "Đã hủy"
        });
        cboTrangThai.setPreferredSize(new Dimension(180, 30));

        pnlTrangThai.add(lblTrangThai);
        pnlTrangThai.add(cboTrangThai);

        JPanel pnlNoiDung = new JPanel();
        pnlNoiDung.setLayout(new BoxLayout(pnlNoiDung, BoxLayout.Y_AXIS));
        pnlNoiDung.setBackground(MAU_TRANG);
        pnlNoiDung.add(pnlNhap);
        pnlNoiDung.add(Box.createVerticalStrut(10));
        pnlNoiDung.add(pnlTrangThai);

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(pnlNoiDung, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoKhuVucDanhSachLichHen() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Danh sách lịch hẹn");

        String[] cot = {
                "Mã lịch hẹn",
                "Tên khách hàng",
                "Số điện thoại",
                "Ngày hẹn",
                "Giờ",
                "Trạng thái"
        };

        Object[][] duLieu = {
                {"LH0001", "Nguyễn Minh Anh", "0901234567", "19/10/2026", "08:00", "Đã xác nhận"},
                {"LH0002", "Trần Quốc", "0391234242", "19/10/2026", "09:00", "Chờ xác nhận"},
                {"LH0003", "Lê Hoàng Nam", "0912345678", "19/10/2026", "10:00", "Đã xác nhận"},
                {"LH0004", "Phạm Ngọc Hà", "0987654321", "19/10/2026", "13:30", "Đã hoàn thành"},
                {"LH0005", "Đỗ Minh Quân", "0342183179", "19/10/2026", "14:00", "Chờ xác nhận"},
                {"LH0006", "Võ Thanh Tâm", "0934567890", "20/10/2026", "08:30", "Đã xác nhận"}
        };

        DefaultTableModel model = new DefaultTableModel(duLieu, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLichHen = new JTable(model);
        tblLichHen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblLichHen.setRowHeight(32);
        tblLichHen.setFont(new Font("Arial", Font.PLAIN, 12));
        tblLichHen.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblLichHen.getTableHeader().setBackground(MAU_NAVY);
        tblLichHen.getTableHeader().setForeground(Color.WHITE);
        tblLichHen.setSelectionBackground(new Color(220, 235, 250));
        tblLichHen.setSelectionForeground(Color.BLACK);
        GiaoDienStyle.dinhDangBang(tblLichHen);

        JScrollPane scrDanhSach = new JScrollPane(tblLichHen);
        scrDanhSach.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(scrDanhSach, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoKhuVucDichVu() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Dịch vụ trong lịch hẹn");

        String[] cot = {
                "STT",
                "Tên dịch vụ",
                "Số lượng",
                "Đơn giá",
                "Thành tiền"
        };

        Object[][] duLieu = {
                {"1", "Khám răng tổng quát", "1", "100.000", "100.000"},
                {"2", "Lấy cao răng", "1", "200.000", "200.000"},
                {"3", "Chụp X-quang răng", "1", "150.000", "150.000"}
        };

        DefaultTableModel model = new DefaultTableModel(duLieu, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(model);
        tblDichVu.setRowHeight(30);
        tblDichVu.setFont(new Font("Arial", Font.PLAIN, 12));
        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblDichVu.getTableHeader().setBackground(MAU_NAVY);
        tblDichVu.getTableHeader().setForeground(Color.WHITE);
        GiaoDienStyle.dinhDangBang(tblDichVu);

        JScrollPane scrDichVu = new JScrollPane(tblDichVu);
        scrDichVu.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(scrDichVu, BorderLayout.CENTER);

        return pnl;
    }

    // ==================== BÊN PHẢI ====================

    private JPanel taoBenPhai() {
        JPanel pnlBenPhai = new JPanel(new BorderLayout(0, 10));
        pnlBenPhai.setBackground(MAU_NEN);

        JPanel pnlThongTin = taoThongTinKhachHang();
        JPanel pnlThanhToan = taoKhuVucThanhToan();

        JSplitPane splitPhai = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                pnlThongTin,
                pnlThanhToan
        );

        splitPhai.setResizeWeight(0.48);
        splitPhai.setDividerLocation(300);
        splitPhai.setDividerSize(7);
        splitPhai.setBorder(null);
        splitPhai.setBackground(MAU_NEN);

        pnlBenPhai.add(splitPhai, BorderLayout.CENTER);

        return pnlBenPhai;
    }

    private JPanel taoThongTinKhachHang() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin khách hàng");

        JPanel pnlThongTin = new JPanel(new GridLayout(0, 2, 10, 9));
        pnlThongTin.setBackground(MAU_TRANG);

        txtMaLichHen = taoOThongTin("LH0002");
        txtMaBenhNhan = taoOThongTin("BN0002");
        txtTenKhachHang = taoOThongTin("Trần Quốc");
        txtSoDienThoai = taoOThongTin("0391234242");
        txtNgayHen = taoOThongTin("19/10/2026");
        txtGioHen = taoOThongTin("09:00");
        txtSoKhach = taoOThongTin("1");
        txtGhiChu = taoOThongTin("Khám và tư vấn");

        themTruong(pnlThongTin, "Mã lịch hẹn", txtMaLichHen);
        themTruong(pnlThongTin, "Mã bệnh nhân", txtMaBenhNhan);
        themTruong(pnlThongTin, "Tên khách hàng", txtTenKhachHang);
        themTruong(pnlThongTin, "Số điện thoại", txtSoDienThoai);
        themTruong(pnlThongTin, "Ngày hẹn", txtNgayHen);
        themTruong(pnlThongTin, "Giờ hẹn", txtGioHen);
        themTruong(pnlThongTin, "Số khách", txtSoKhach);
        themTruong(pnlThongTin, "Ghi chú", txtGhiChu);

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(pnlThongTin, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoKhuVucThanhToan() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 8));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin hóa đơn");

        JPanel pnlMaGiamGia = new JPanel(new BorderLayout(6, 0));
        pnlMaGiamGia.setBackground(MAU_TRANG);

        txtMaGiamGia = new JTextField();
        txtMaGiamGia.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMaGiamGia.setPreferredSize(new Dimension(100, 32));
        txtMaGiamGia.setToolTipText("Nhập mã giảm giá");

        JButton btnApDung = taoNut("Áp dụng", MAU_TEAL);

        pnlMaGiamGia.add(txtMaGiamGia, BorderLayout.CENTER);
        pnlMaGiamGia.add(btnApDung, BorderLayout.EAST);

        JPanel pnlChiTietTien = new JPanel(new GridLayout(0, 2, 8, 7));
        pnlChiTietTien.setBackground(MAU_TRANG);

        txtTongTien = taoOThongTin("450.000 VNĐ");
        txtChietKhau = taoOThongTin("0 VNĐ");
        txtThue = taoOThongTin("45.000 VNĐ");
        txtTienCoc = taoOThongTin("100.000 VNĐ");
        txtCanThanhToan = taoOThongTin("395.000 VNĐ");

        themDongTien(pnlChiTietTien, "Tổng tiền dịch vụ", txtTongTien);
        themDongTien(pnlChiTietTien, "Chiết khấu", txtChietKhau);
        themDongTien(pnlChiTietTien, "Thuế VAT (10%)", txtThue);
        themDongTien(pnlChiTietTien, "Tiền đã đặt cọc", txtTienCoc);

        JPanel pnlTongThanhToan = new JPanel(new BorderLayout(8, 0));
        pnlTongThanhToan.setBackground(new Color(235, 242, 250));
        pnlTongThanhToan.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 215, 235)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblCanThanhToan = new JLabel("CẦN THANH TOÁN");
        lblCanThanhToan.setFont(new Font("Arial", Font.BOLD, 13));
        lblCanThanhToan.setForeground(MAU_NAVY);

        txtCanThanhToan.setFont(new Font("Arial", Font.BOLD, 16));
        txtCanThanhToan.setForeground(MAU_DO);
        txtCanThanhToan.setBackground(new Color(235, 242, 250));
        txtCanThanhToan.setBorder(BorderFactory.createEmptyBorder());

        pnlTongThanhToan.add(lblCanThanhToan, BorderLayout.WEST);
        pnlTongThanhToan.add(txtCanThanhToan, BorderLayout.EAST);

        JPanel pnlPhuongThuc = new JPanel(new GridLayout(1, 2, 8, 0));
        pnlPhuongThuc.setBackground(MAU_TRANG);

        btnTienMat = taoNut("Thanh toán tiền mặt", MAU_TEAL);
        btnChuyenKhoan = taoNut("Thanh toán chuyển khoản", MAU_NAVY);

        btnTienMat.setPreferredSize(new Dimension(150, 40));
        btnChuyenKhoan.setPreferredSize(new Dimension(150, 40));

        pnlPhuongThuc.add(btnTienMat);
        pnlPhuongThuc.add(btnChuyenKhoan);

        btnThanhToan = taoNut("THANH TOÁN", new Color(230, 126, 34));
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 16));
        btnThanhToan.setPreferredSize(new Dimension(200, 46));

        JPanel pnlNutThanhToan = new JPanel(new BorderLayout());
        pnlNutThanhToan.setBackground(MAU_TRANG);
        pnlNutThanhToan.add(btnThanhToan, BorderLayout.CENTER);

        JPanel pnlNoiDung = new JPanel();
        pnlNoiDung.setLayout(new BoxLayout(pnlNoiDung, BoxLayout.Y_AXIS));
        pnlNoiDung.setBackground(MAU_TRANG);

        pnlNoiDung.add(pnlMaGiamGia);
        pnlNoiDung.add(Box.createVerticalStrut(10));
        pnlNoiDung.add(pnlChiTietTien);
        pnlNoiDung.add(Box.createVerticalStrut(10));
        pnlNoiDung.add(pnlTongThanhToan);
        pnlNoiDung.add(Box.createVerticalStrut(12));
        pnlNoiDung.add(pnlPhuongThuc);
        pnlNoiDung.add(Box.createVerticalStrut(10));
        pnlNoiDung.add(pnlNutThanhToan);

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(pnlNoiDung, BorderLayout.CENTER);

        return pnl;
    }

    // ==================== HÀM DÙNG CHUNG ====================

    private JPanel taoPanelTrang() {
        JPanel pnl = new JPanel();
        pnl.setBackground(MAU_TRANG);
        return pnl;
    }

    private JLabel taoTieuDe(String noiDung) {
        JLabel lbl = new JLabel(noiDung);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setForeground(MAU_NAVY);
        return lbl;
    }

    private JTextField taoOThongTin(String noiDung) {
        JTextField txt = new JTextField(noiDung);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setEditable(false);
        txt.setBackground(new Color(248, 249, 252));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 229, 235)),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return txt;
    }

    private void themTruong(JPanel pnl, String tenTruong, JTextField txt) {
        JLabel lbl = new JLabel(tenTruong);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(new Color(75, 85, 99));

        pnl.add(lbl);
        pnl.add(txt);
    }

    private void themDongTien(JPanel pnl, String tenDong, JTextField txt) {
        JLabel lbl = new JLabel(tenDong);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(new Color(75, 85, 99));

        txt.setHorizontalAlignment(SwingConstants.RIGHT);

        pnl.add(lbl);
        pnl.add(txt);
    }

    private JButton taoNut(String noiDung, Color mauNen) {
        JButton btn = new JButton(noiDung);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mauNen);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 34));

        return btn;
    }
}
