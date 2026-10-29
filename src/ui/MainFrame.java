package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

    private Color mauMenu = new Color(35, 70, 110);

    private JPanel pnlNoiDung;
    private CardLayout clNoiDung;

    private LeftMenuPanel pnlMenu;

    private JLabel lblTenNguoiDung;
    private JLabel lblVaiTro;

    // Khai báo tham chiếu trực tiếp đến các Panel cần đồng bộ dữ liệu
    private TiepNhanLichHenPanel pnlTiepNhanLichHen;
    private CheckInPanel pnlCheckIn;
    private HoSoBenhNhanPanel pnlHoSoBenhNhan;
    private TaoHoaDonPanel pnlTaoHoaDon;

    private String maNVHienTai;

    public MainFrame() {
        this(null);
    }

    public MainFrame(String maNV) {
        this.maNVHienTai = maNV;

        khoiTaoCuaSo();
        taoMenu();
        taoNoiDung();
    }

    private void khoiTaoCuaSo() {
        setTitle("Phần mềm quản lý nha khoa");
        setSize(1200, 750);
        setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void taoMenu() {
        pnlMenu = new LeftMenuPanel(this);
        add(pnlMenu, BorderLayout.WEST);
    }

    private void taoNoiDung() {
        clNoiDung = new CardLayout();
        pnlNoiDung = new JPanel(clNoiDung);

        pnlNoiDung.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlNoiDung.setBackground(Color.WHITE);

        pnlNoiDung.add(new HomePanel(), "Trang chủ");

        pnlHoSoBenhNhan = new HoSoBenhNhanPanel(maNVHienTai);
        pnlNoiDung.add(pnlHoSoBenhNhan, "Hồ sơ bệnh nhân");

        pnlTaoHoaDon = new TaoHoaDonPanel();
        pnlNoiDung.add(pnlTaoHoaDon, "Tạo hóa đơn");

        // Khởi tạo và gán biến cho Tiếp nhận lịch hẹn (Truyền 'this' vào)
        pnlTiepNhanLichHen = new TiepNhanLichHenPanel(this);
        pnlNoiDung.add(pnlTiepNhanLichHen, "Tiếp nhận lịch hẹn");

        // Khởi tạo và gán biến cho Check In (Truyền 'this' vào)
        pnlCheckIn = new CheckInPanel(this);
        pnlNoiDung.add(pnlCheckIn, "Check In");

        pnlNoiDung.add(new KeHoachDieuTriPanel(), "Kế hoạch điều trị");
        pnlNoiDung.add(new DichVuPanel(), "Dịch vụ");
        pnlNoiDung.add(new PhongDieuTriPanel(), "Phòng điều trị");
        pnlNoiDung.add(new VatTuPanel(), "Vật tư");
        pnlNoiDung.add(new NhanVienPanel(), "Nhân viên");
        pnlNoiDung.add(new TaiKhoanPanel(), "Tài khoản");
        pnlNoiDung.add(new PhanCongNhanVienPanel(), "Phân công nhân viên");
        pnlNoiDung.add(new TraCuuHoaDonPanel(), "Tra cứu hóa đơn");
        pnlNoiDung.add(new KhuyenMaiPanel(), "Khuyến mãi");
        pnlNoiDung.add(new HoTroPanel(), "Hỗ trợ");
        pnlNoiDung.add(new ThongKeDoanhThuPanel(), "Thống kê doanh thu");
        pnlNoiDung.add(new ThongKeDichVuPanel(), "Thống kê dịch vụ");
        pnlNoiDung.add(new ThongKeKhachHangPanel(), "Thống kê khách hàng");
        pnlNoiDung.add(new TimKiemBenhNhanPanel(), "Tìm kiếm bệnh nhân");
        pnlNoiDung.add(new TimKiemNhanVienPanel(), "Tìm kiếm nhân viên");
        pnlNoiDung.add(new TimKiemPhongDieuTriPanel(), "Tìm kiếm phòng điều trị");
        pnlNoiDung.add(new TimKiemDichVuPanel(), "Tìm kiếm dịch vụ");
        pnlNoiDung.add(new TimKiemVatTuPanel(), "Tìm kiếm vật tư");

        add(pnlNoiDung, BorderLayout.CENTER);

        hienThiManHinh("Trang chủ");
    }

    public void capNhatThongTinNguoiDung(String tenNguoiDung, String vaiTro) {
        if (lblTenNguoiDung != null) lblTenNguoiDung.setText(tenNguoiDung);
        if (lblVaiTro != null) lblVaiTro.setText(vaiTro);
    }

    private JPanel taoManHinhTam(String tenManHinh) {
        JPanel pnlTam = new JPanel();
        pnlTam.setLayout(new BorderLayout());
        pnlTam.setBackground(new Color(245, 247, 250));
        pnlTam.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel lblTenManHinh = new JLabel(tenManHinh);
        lblTenManHinh.setFont(new Font("Arial", Font.BOLD, 24));
        lblTenManHinh.setForeground(mauMenu);

        pnlTam.add(lblTenManHinh, BorderLayout.NORTH);
        return pnlTam;
    }

    public void hienThiManHinh(String tenManHinh) {
        clNoiDung.show(pnlNoiDung, tenManHinh);

        // Kích hoạt làm mới dữ liệu tự động khi người dùng bấm chuyển tab
        if ("Tiếp nhận lịch hẹn".equals(tenManHinh)) {
            if (pnlTiepNhanLichHen != null) {
                pnlTiepNhanLichHen.refreshData();
            }
        } else if ("Check In".equals(tenManHinh)) {
            if (pnlCheckIn != null) {
                pnlCheckIn.refreshData();
            }
        } else if ("Tạo hóa đơn".equals(tenManHinh)) {
            if (pnlTaoHoaDon != null) {
                pnlTaoHoaDon.refreshData();
            }
        }
    }

    public void moHoSoBenhNhan(String maBenhNhan) {
        if (maBenhNhan == null || maBenhNhan.trim().isEmpty()) {
            return;
        }
        pnlHoSoBenhNhan.nhanMaBenhNhanTuCheckIn(maBenhNhan);
        hienThiManHinh("Hồ sơ bệnh nhân");
    }

    public void dangXuat() {
        dispose();
        new DangNhapPanel().setVisible(true);
    }
}