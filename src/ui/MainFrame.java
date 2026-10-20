
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

    public MainFrame() {
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

        pnlNoiDung.add(
        	    new TraCuuPanel(),
        	    "Tra cứu"
        	);

        pnlNoiDung.add(
            new HoSoBenhNhanPanel(),
            "Hồ sơ bệnh nhân"
        );

        pnlNoiDung.add(
            new LichHenPanel(),
            "Lịch hẹn"
        );

        pnlNoiDung.add(
            new TiepNhanLichHenPanel(),
            "Tiếp nhận lịch hẹn"
        );

        pnlNoiDung.add(
            new CheckInPanel(),
            "Check In"
        );

        pnlNoiDung.add(
            new KeHoachDieuTriPanel(),
            "Kế hoạch điều trị"
        );

        pnlNoiDung.add(
            new DichVuPanel(),
            "Dịch vụ"
        );

        pnlNoiDung.add(
            new PhongDieuTriPanel(),
            "Phòng điều trị"
        );

        pnlNoiDung.add(
            new VatTuPanel(),
            "Vật tư"
        );

        pnlNoiDung.add(
            new NhanVienPanel(),
            "Nhân viên"
        );

        pnlNoiDung.add(
            new TaiKhoanPanel(),
            "Tài khoản"
        );

        pnlNoiDung.add(
            new PhanCongNhanVienPanel(),
            "Phân công nhân viên"
        );

        pnlNoiDung.add(
            new HoaDonPanel(),
            "Hóa đơn"
        );
        pnlNoiDung.add(
                new LichHenPanel(),
                "Tạo hóa đơn"
        );

        pnlNoiDung.add(
                new HoaDonPanel(),
                "Tra cứu hóa đơn"
        );
        pnlNoiDung.add(
            new KhuyenMaiPanel(),
            "Khuyến mãi"
        );

        pnlNoiDung.add(
            new ThongKePanel(),
            "Thống kê"
        );

        pnlNoiDung.add(
            new HoTroPanel(),
            "Hỗ trợ"
        );

        add(pnlNoiDung, BorderLayout.CENTER);

        hienThiManHinh("Trang chủ");
    }
    public void capNhatThongTinNguoiDung(
            String tenNguoiDung,
            String vaiTro) {

        lblTenNguoiDung.setText(tenNguoiDung);
        lblVaiTro.setText(vaiTro);
    }
    private JPanel taoManHinhTam(String tenManHinh) {
        JPanel pnlTam = new JPanel();
        pnlTam.setLayout(new BorderLayout());
        pnlTam.setBackground(new Color(245, 247, 250));

        pnlTam.setBorder(
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        JLabel lblTenManHinh = new JLabel(tenManHinh);
        lblTenManHinh.setFont(
            new Font("Arial", Font.BOLD, 24)
        );
        lblTenManHinh.setForeground(mauMenu);

        pnlTam.add(lblTenManHinh, BorderLayout.NORTH);

        return pnlTam;
    }

    public void hienThiManHinh(String tenManHinh) {
        clNoiDung.show(pnlNoiDung, tenManHinh);
    }

    public void dangXuat() {
        dispose();
        new DangNhapPanel().setVisible(true);
    }
}
