package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class LeftMenuPanel extends JPanel implements ActionListener {

    private MainFrame parentView;

    private Color mauMenu = new Color(35, 70, 110);
    private Color mauXanhChon = new Color(55, 130, 210);
    private Color mauTrang = Color.WHITE;
    private Color mauDo = new Color(200, 55, 55);
    private Color mauDuongKe = new Color(50, 95, 145);

    private JPanel pnlThongTinNguoiDung;
    private JPanel pnlDanhSachManHinh;
    private JPanel pnlDangXuat;

    private JPanel pnlMenuLichHen;
    private JPanel pnlMenuNhanVien;
    private JPanel pnlMenuBenhNhan;
    private JPanel pnlMenuDieuTri;
    private JPanel pnlMenuHoaDon;
    private JPanel pnlMenuThongKe;
    private JPanel pnlMenuDichVu;
    private JPanel pnlMenuVatTu;

    private JLabel lblAvatar;
    private JLabel lblTenNguoiDung;
    private JLabel lblVaiTro;

    private JButton btnTrangChu;
    private JButton btnLichHen;
    private JButton btnNhanVien;
    private JButton btnBenhNhan;
    private JButton btnDieuTri;
    private JButton btnVatTu;
    private JButton btnDichVu;
    private JButton btnHoaDon;
    private JButton btnHoTro;
    private JButton btnThoat;

    private JButton btnTiepNhanLichHen;
    private JButton btnCheckIn;

    private JButton btnTimKiemNhanVien;
    private JButton btnQuanLyNhanVien;
    private JButton btnPhanCong;
    private JButton btnTaiKhoan;
    private JButton btnThongKe;
    private JButton btnThongKeDoanhThu;
    private JButton btnThongKeDichVu;
    private JButton btnThongKeKhachHang;

    private JButton btnTimKiemBenhNhan;
    private JButton btnHoSoBenhNhan;
    private JButton btnKeHoachDieuTri;

//    private JButton btnTimKiemPhongDieuTri;
//    private JButton btnQuanLyPhongDieuTri;

    private JButton btnTimKiemDichVu;
    private JButton btnCapNhatDichVu;

    private JButton btnTimKiemVatTu;
    private JButton btnCapNhatVatTu;

    private JButton btnTaoHoaDon;
    private JButton btnTraCuuHoaDon;
    private JButton btnKhuyenMai;

    private JButton nutDangChon;

    private boolean dangMoLichHen = false;
    private boolean dangMoNhanVien = false;
    private boolean dangMoThongKe = false;
    private boolean dangMoBenhNhan = false;
    private boolean dangMoDieuTri = false;
    private boolean dangMoDichVu = false;
    private boolean dangMoVatTu = false;
    private boolean dangMoHoaDon = false;

    public LeftMenuPanel(MainFrame view) {
        this.parentView = view;

        setLayout(new BorderLayout());
        setBackground(mauMenu);
        setPreferredSize(new Dimension(240, 0));

        taoThongTinNguoiDung();
        taoDanhSachManHinh();
        taoNutDangXuat();

        JScrollPane scrMenu = new JScrollPane(pnlDanhSachManHinh);
        scrMenu.setBorder(null);
        scrMenu.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrMenu.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrMenu.getVerticalScrollBar().setUnitIncrement(16);
        scrMenu.setBackground(mauMenu);
        scrMenu.getViewport().setBackground(mauMenu);

        add(pnlThongTinNguoiDung, BorderLayout.NORTH);
        add(scrMenu, BorderLayout.CENTER);
        add(pnlDangXuat, BorderLayout.SOUTH);

        capNhatMauNut(btnTrangChu);
    }

    private void taoThongTinNguoiDung() {
        pnlThongTinNguoiDung = new JPanel();
        pnlThongTinNguoiDung.setLayout(new BorderLayout(0, 10));
        pnlThongTinNguoiDung.setBackground(mauMenu);
        pnlThongTinNguoiDung.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        Icon iconAvatar = FontIcon.of(FontAwesomeSolid.USER_CIRCLE, 65, mauTrang);

        lblAvatar = new JLabel(iconAvatar);
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);

        lblTenNguoiDung = new JLabel("Nguyễn Văn A");
        lblTenNguoiDung.setFont(new Font("Arial", Font.BOLD, 16));
        lblTenNguoiDung.setForeground(mauTrang);
        lblTenNguoiDung.setHorizontalAlignment(SwingConstants.CENTER);

        lblVaiTro = new JLabel("Quản trị viên");
        lblVaiTro.setFont(new Font("Arial", Font.PLAIN, 13));
        lblVaiTro.setForeground(mauTrang);
        lblVaiTro.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pnlTenVaVaiTro = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlTenVaVaiTro.setBackground(mauMenu);
        pnlTenVaVaiTro.add(lblTenNguoiDung);
        pnlTenVaVaiTro.add(lblVaiTro);

        pnlThongTinNguoiDung.add(lblAvatar, BorderLayout.NORTH);
        pnlThongTinNguoiDung.add(pnlTenVaVaiTro, BorderLayout.CENTER);
    }

    private void taoDanhSachManHinh() {
        pnlDanhSachManHinh = new JPanel();
        pnlDanhSachManHinh.setLayout(new BoxLayout(pnlDanhSachManHinh, BoxLayout.Y_AXIS));
        pnlDanhSachManHinh.setBackground(mauMenu);
        pnlDanhSachManHinh.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));

        btnTrangChu = taoNutMenu("Trang chủ", FontAwesomeSolid.HOME);

        btnLichHen = taoNutMenu("Lịch hẹn ▼", FontAwesomeSolid.CALENDAR_ALT);
        btnTiepNhanLichHen = taoNutCon("Tiếp nhận lịch hẹn",FontAwesomeSolid.CALENDAR_CHECK);
        btnCheckIn = taoNutCon("Check In/Tìm kiếm",FontAwesomeSolid.CLIPBOARD_CHECK);

        pnlMenuLichHen = taoPanelMenuCon();
        pnlMenuLichHen.add(btnTiepNhanLichHen);
        pnlMenuLichHen.add(btnCheckIn);
        pnlMenuLichHen.setVisible(false);

        btnNhanVien = taoNutMenu("Nhân viên ▼", FontAwesomeSolid.USERS);
        btnTimKiemNhanVien = taoNutCon("Tìm kiếm nhân viên",FontAwesomeSolid.USER_SECRET);
        btnQuanLyNhanVien = taoNutCon("Cập nhật nhân viên",FontAwesomeSolid.USER_TIE);
        btnPhanCong = taoNutCon("Phân công nhân viên",FontAwesomeSolid.TASKS);
        btnTaiKhoan = taoNutCon("Tài khoản",FontAwesomeSolid.USER_CIRCLE);
        btnThongKe = taoNutCon("Thống kê ▼", FontAwesomeSolid.CHART_BAR);
        btnThongKeDoanhThu = taoNutSubCon("Doanh thu",FontAwesomeSolid.MONEY_BILL);
        btnThongKeDichVu = taoNutSubCon("Thống kê Dịch vụ",FontAwesomeSolid.CALCULATOR);
        btnThongKeKhachHang = taoNutSubCon("Thống kê khách hàng",FontAwesomeSolid.USER_CIRCLE);

        pnlMenuThongKe = taoPanelMenuCon();
        pnlMenuThongKe.add(btnThongKeDoanhThu);
        pnlMenuThongKe.add(btnThongKeDichVu);
        pnlMenuThongKe.add(btnThongKeKhachHang);
        pnlMenuThongKe.setVisible(false);
        pnlMenuNhanVien = taoPanelMenuCon();
        pnlMenuNhanVien.add(btnTimKiemNhanVien);
        pnlMenuNhanVien.add(btnQuanLyNhanVien);
        pnlMenuNhanVien.add(btnPhanCong);
        pnlMenuNhanVien.add(btnTaiKhoan);
        pnlMenuNhanVien.add(btnThongKe);
        pnlMenuNhanVien.add(pnlMenuThongKe);
        pnlMenuNhanVien.setVisible(false);


        btnBenhNhan = taoNutMenu("Bệnh nhân ▼", FontAwesomeSolid.FOLDER_OPEN);
        btnTimKiemBenhNhan = taoNutCon("Tìm kiếm hồ sơ",FontAwesomeSolid.FOLDER_PLUS);
        btnHoSoBenhNhan = taoNutCon("Cập nhật hồ sơ",FontAwesomeSolid.FOLDER_OPEN);
        btnKeHoachDieuTri = taoNutCon("Kế hoạch điều trị",FontAwesomeSolid.CLIPBOARD_LIST);

        pnlMenuBenhNhan = taoPanelMenuCon();
        pnlMenuBenhNhan.add(btnTimKiemBenhNhan);
        pnlMenuBenhNhan.add(btnHoSoBenhNhan);
        pnlMenuBenhNhan.add(btnKeHoachDieuTri);
        pnlMenuBenhNhan.setVisible(false);

//        btnDieuTri = taoNutMenu("Phòng điều trị ▼", FontAwesomeSolid.HOSPITAL);
//        btnTimKiemPhongDieuTri = taoNutCon("Tìm kiếm phòng điều trị",FontAwesomeSolid.HOSPITAL_USER);
//        btnQuanLyPhongDieuTri = taoNutCon("Cập nhật phòng điều trị",FontAwesomeSolid.HOSPITAL);

        pnlMenuDieuTri = taoPanelMenuCon();
//        pnlMenuDieuTri.add(btnTimKiemPhongDieuTri);
//        pnlMenuDieuTri.add(btnQuanLyPhongDieuTri);
        pnlMenuDieuTri.setVisible(false);

        btnDichVu = taoNutMenu("Dịch vụ ▼",FontAwesomeSolid.TOOTH);
        btnTimKiemDichVu = taoNutCon("Tìm kiếm dịch vụ",FontAwesomeSolid.TEETH_OPEN);
        btnCapNhatDichVu = taoNutCon("Cập nhật dịch vụ", FontAwesomeSolid.TOOTH);

        pnlMenuDichVu = taoPanelMenuCon();
        pnlMenuDichVu.add(btnTimKiemDichVu);
        pnlMenuDichVu.add(btnCapNhatDichVu);
        pnlMenuDichVu.setVisible(false);

        btnVatTu = taoNutMenu("Vật tư ▼",FontAwesomeSolid.BOXES);
        btnTimKiemVatTu = taoNutCon("Tìm kiếm vật tư",FontAwesomeSolid.SEARCH_PLUS);
        btnCapNhatVatTu = taoNutCon("Cập nhật vật tư", FontAwesomeSolid.BOXES);

        pnlMenuVatTu = taoPanelMenuCon();
        pnlMenuVatTu.add(btnTimKiemVatTu);
        pnlMenuVatTu.add(btnCapNhatVatTu);
        pnlMenuVatTu.setVisible(false);

        btnHoaDon = taoNutMenu("Hóa đơn ▼", FontAwesomeSolid.FILE_INVOICE_DOLLAR);
        btnTaoHoaDon = taoNutCon("Tạo hóa đơn", FontAwesomeSolid.CALENDAR_CHECK);
        btnTraCuuHoaDon = taoNutCon("Tra cứu hóa đơn",FontAwesomeSolid.SEARCH);
        btnKhuyenMai = taoNutCon("Khuyến mãi",FontAwesomeSolid.TAG);

        pnlMenuHoaDon = taoPanelMenuCon();
        pnlMenuHoaDon.add(btnTaoHoaDon);
        pnlMenuHoaDon.add(btnTraCuuHoaDon);
        pnlMenuHoaDon.add(btnKhuyenMai);
        pnlMenuHoaDon.setVisible(false);

        btnHoTro = taoNutMenu("Hỗ trợ", FontAwesomeSolid.QUESTION_CIRCLE);

        pnlDanhSachManHinh.add(btnTrangChu);
        pnlDanhSachManHinh.add(taoDuongKe());

        pnlDanhSachManHinh.add(btnLichHen);
        pnlDanhSachManHinh.add(pnlMenuLichHen);
        pnlDanhSachManHinh.add(taoDuongKe());

        pnlDanhSachManHinh.add(btnNhanVien);
        pnlDanhSachManHinh.add(pnlMenuNhanVien);
        pnlDanhSachManHinh.add(taoDuongKe());

        pnlDanhSachManHinh.add(btnBenhNhan);
        pnlDanhSachManHinh.add(pnlMenuBenhNhan);
        pnlDanhSachManHinh.add(taoDuongKe());

//        pnlDanhSachManHinh.add(btnDieuTri);
//        pnlDanhSachManHinh.add(pnlMenuDieuTri);
//        pnlDanhSachManHinh.add(taoDuongKe());

        pnlDanhSachManHinh.add(btnDichVu);
        pnlDanhSachManHinh.add(pnlMenuDichVu);
        pnlDanhSachManHinh.add(taoDuongKe());

        pnlDanhSachManHinh.add(btnVatTu);
        pnlDanhSachManHinh.add(pnlMenuVatTu);
        pnlDanhSachManHinh.add(taoDuongKe());

        pnlDanhSachManHinh.add(btnHoaDon);
        pnlDanhSachManHinh.add(pnlMenuHoaDon);
        pnlDanhSachManHinh.add(taoDuongKe());

        pnlDanhSachManHinh.add(btnHoTro);
    }

    private JPanel taoDuongKe() {
        JPanel pnl = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(mauDuongKe);
                g.drawLine(0, 6, getWidth() - 5, 6);
            }
        };
        pnl.setBackground(mauMenu);
        pnl.setMaximumSize(new Dimension(215, 14));
        pnl.setPreferredSize(new Dimension(215, 14));
        return pnl;
    }

    private JPanel taoPanelMenuCon() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setBackground(mauMenu);
        pnl.setAlignmentX(LEFT_ALIGNMENT);
        pnl.setMaximumSize(new Dimension(215, 500));
        return pnl;
    }

    private JButton taoNutMenu(String tenNut, FontAwesomeSolid tenIcon) {
        JButton btn = new JButton(tenNut);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setForeground(mauTrang);
        btn.setBackground(mauMenu);
        btn.setIcon(FontIcon.of(tenIcon, 20, mauTrang));
        btn.setIconTextGap(12);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, mauDuongKe),
                BorderFactory.createEmptyBorder(10, 12, 10, 8)
        ));
        btn.setPreferredSize(new Dimension(215, 44));
        btn.setMinimumSize(new Dimension(215, 44));
        btn.setMaximumSize(new Dimension(215, 44));
        btn.setAlignmentX(LEFT_ALIGNMENT);

        btn.addActionListener(this);
        return btn;
    }

    private JButton taoNutCon(String tenNut,FontAwesomeSolid tenIcon) {
        JButton btn = new JButton(tenNut);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setForeground(mauTrang);
        btn.setBackground(mauMenu);
        btn.setIcon(FontIcon.of(tenIcon, 20, mauTrang));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);

        btn.setBorder(BorderFactory.createEmptyBorder(8, 28, 8, 8));
        btn.setPreferredSize(new Dimension(215, 38));
        btn.setMinimumSize(new Dimension(215, 38));
        btn.setMaximumSize(new Dimension(215, 38));
        btn.setAlignmentX(LEFT_ALIGNMENT);

        btn.addActionListener(this);
        return btn;
    }

    private JButton taoNutSubCon(String tenNut,FontAwesomeSolid tenIcon) {
        JButton btn = new JButton(tenNut);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setForeground(mauTrang);
        btn.setBackground(mauMenu);
        btn.setIcon(FontIcon.of(tenIcon, 20, mauTrang));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 42, 6, 8));
        btn.setPreferredSize(new Dimension(215, 34));
        btn.setMinimumSize(new Dimension(215, 34));
        btn.setMaximumSize(new Dimension(215, 34));
        btn.setAlignmentX(LEFT_ALIGNMENT);

        btn.addActionListener(this);
        return btn;
    }

    private void capNhatMauNut(JButton nutMoi) {
        if (nutDangChon != null) {
            nutDangChon.setBackground(mauMenu);
            nutDangChon.setForeground(nutDangChon.getText().startsWith("-") ? new Color(210, 225, 240) : mauTrang);
        }

        nutDangChon = nutMoi;

        if (nutDangChon != null) {
            nutDangChon.setBackground(mauXanhChon);
            nutDangChon.setForeground(mauTrang);
        }

        pnlDanhSachManHinh.revalidate();
        pnlDanhSachManHinh.repaint();
    }

    private void capNhatMenuCon(JPanel panel, JButton nut, boolean dangMo, String tenGoc) {
        panel.setVisible(dangMo);
        nut.setText(tenGoc + (dangMo ? "  ▲" : "  ▼"));
        pnlDanhSachManHinh.revalidate();
        pnlDanhSachManHinh.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnTrangChu) {
            capNhatMauNut(btnTrangChu);
            parentView.hienThiManHinh("Trang chủ");
        }
        else if (o == btnLichHen) {
            dangMoLichHen = !dangMoLichHen;
            capNhatMenuCon(pnlMenuLichHen, btnLichHen, dangMoLichHen, "Lịch hẹn");
            if (dangMoLichHen) capNhatMauNut(btnLichHen);
        }

        else if (o == btnTiepNhanLichHen) {
            capNhatMauNut(btnTiepNhanLichHen);
            parentView.hienThiManHinh("Tiếp nhận lịch hẹn");
        }
        else if (o == btnCheckIn) {
            capNhatMauNut(btnCheckIn);
            parentView.hienThiManHinh("Check In");
        }
        else if (o == btnNhanVien) {
            dangMoNhanVien = !dangMoNhanVien;
            capNhatMenuCon(pnlMenuNhanVien, btnNhanVien, dangMoNhanVien, "Nhân viên");
            if (dangMoNhanVien) capNhatMauNut(btnNhanVien);
        }
        else if (o == btnQuanLyNhanVien) {
            capNhatMauNut(btnQuanLyNhanVien);
            parentView.hienThiManHinh("Nhân viên");
        }
        else if (o == btnTimKiemNhanVien) {
            capNhatMauNut(btnTimKiemNhanVien);
            parentView.hienThiManHinh("Tìm kiếm nhân viên");
        }
        else if (o == btnPhanCong) {
            capNhatMauNut(btnPhanCong);
            parentView.hienThiManHinh("Phân công nhân viên");
        }
        else if (o == btnTaiKhoan) {
            capNhatMauNut(btnTaiKhoan);
            parentView.hienThiManHinh("Tài khoản");
        }
        else if (o == btnThongKe) {
            dangMoThongKe = !dangMoThongKe;
            capNhatMenuCon(pnlMenuThongKe, btnThongKe, dangMoThongKe, "Thống kê");
            if (dangMoThongKe) capNhatMauNut(btnThongKe);
        }
        else if (o == btnThongKeDoanhThu) {
            capNhatMauNut(btnThongKeDoanhThu);
            parentView.hienThiManHinh("Thống kê doanh thu");
        }
        else if (o == btnThongKeDichVu) {
            capNhatMauNut(btnThongKeDichVu);
            parentView.hienThiManHinh("Thống kê dịch vụ");
        }
        else if (o == btnThongKeKhachHang) {
            capNhatMauNut(btnThongKeKhachHang);
            parentView.hienThiManHinh("Thống kê khách hàng");
        }
        else if (o == btnBenhNhan) {
            dangMoBenhNhan = !dangMoBenhNhan;
            capNhatMenuCon(pnlMenuBenhNhan, btnBenhNhan, dangMoBenhNhan, "Bệnh nhân");
            if (dangMoBenhNhan) capNhatMauNut(btnBenhNhan);
        }
        else if (o == btnTimKiemBenhNhan) {
            capNhatMauNut(btnTimKiemBenhNhan);
            parentView.hienThiManHinh("Tìm kiếm bệnh nhân");
        }
        else if (o == btnHoSoBenhNhan) {
            capNhatMauNut(btnHoSoBenhNhan);
            parentView.hienThiManHinh("Hồ sơ bệnh nhân");
        }
        else if (o == btnKeHoachDieuTri) {
            capNhatMauNut(btnKeHoachDieuTri);
            parentView.hienThiManHinh("Kế hoạch điều trị");
        }
        else if (o == btnDieuTri) {
            dangMoDieuTri = !dangMoDieuTri;
            capNhatMenuCon(pnlMenuDieuTri, btnDieuTri, dangMoDieuTri, "Phòng điều trị");
            if (dangMoDieuTri) capNhatMauNut(btnDieuTri);
        }
//        else if (o == btnTimKiemPhongDieuTri) {
//            capNhatMauNut(btnTimKiemPhongDieuTri);
//            parentView.hienThiManHinh("Tìm kiếm phòng điều trị");
//        }
//        else if (o == btnQuanLyPhongDieuTri) {
//            capNhatMauNut(btnQuanLyPhongDieuTri);
//            parentView.hienThiManHinh("Phòng điều trị");
//        }
        else if (o == btnDichVu) {
            dangMoDichVu = !dangMoDichVu;
            capNhatMenuCon(pnlMenuDichVu, btnDichVu, dangMoDichVu, "Dịch Vụ");
            if (dangMoDichVu) capNhatMauNut(btnDichVu);
        }

        else if (o == btnTimKiemDichVu) {
            capNhatMauNut(btnTimKiemDichVu);
            parentView.hienThiManHinh("Tìm kiếm dịch vụ");
        }
        else if (o == btnCapNhatDichVu) {
            capNhatMauNut(btnCapNhatDichVu);
            parentView.hienThiManHinh("Dịch vụ");
        }
        else if (o == btnVatTu) {
            dangMoVatTu = !dangMoVatTu;
            capNhatMenuCon(pnlMenuVatTu, btnVatTu, dangMoVatTu, "Vật tư");
            if (dangMoVatTu) capNhatMauNut(btnVatTu);
        }

        else if (o == btnTimKiemVatTu) {
            capNhatMauNut(btnTimKiemVatTu);
            parentView.hienThiManHinh("Tìm kiếm vật tư");
        }
        else if (o == btnCapNhatVatTu) {
            capNhatMauNut(btnCapNhatVatTu);
            parentView.hienThiManHinh("Vật tư");
        }
        else if (o == btnHoaDon) {
            dangMoHoaDon = !dangMoHoaDon;
            capNhatMenuCon(pnlMenuHoaDon, btnHoaDon, dangMoHoaDon, "Hóa Đơn");
            if (dangMoHoaDon) capNhatMauNut(btnHoaDon);
        }
        else if (o == btnTaoHoaDon) {
            capNhatMauNut(btnTaoHoaDon);
            parentView.hienThiManHinh("Tạo hóa đơn");
        }
        else if (o == btnTraCuuHoaDon) {
            capNhatMauNut(btnTraCuuHoaDon);
            parentView.hienThiManHinh("Tra cứu hóa đơn");
        }
        else if (o == btnKhuyenMai) {
            capNhatMauNut(btnKhuyenMai);
            parentView.hienThiManHinh("Khuyến mãi");
        }
        else if (o == btnHoTro) {
            capNhatMauNut(btnHoTro);
            parentView.hienThiManHinh("Hỗ trợ");
        }
        else if (o == btnThoat) {
            int luaChon = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn đăng xuất?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION);

            if (luaChon == JOptionPane.YES_OPTION) {
                parentView.dangXuat();
            }
        }
    }

    public void capNhatThongTinNguoiDung(String tenNguoiDung, String vaiTro) {
        lblTenNguoiDung.setText(tenNguoiDung);
        lblVaiTro.setText(vaiTro);
    }

    private void taoNutDangXuat() {
        pnlDangXuat = new JPanel(new BorderLayout());
        pnlDangXuat.setBackground(mauMenu);
        pnlDangXuat.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        btnThoat = taoNutMenu("Đăng xuất", FontAwesomeSolid.SIGN_OUT_ALT);
        btnThoat.setBackground(mauDo);
        btnThoat.setForeground(mauTrang);

        pnlDangXuat.add(btnThoat, BorderLayout.CENTER);
    }
}