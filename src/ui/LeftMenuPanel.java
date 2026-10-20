
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

    private JPanel pnlThongTinNguoiDung;
    private JPanel pnlDanhSachManHinh;
    private JPanel pnlDangXuat;

    private JPanel pnlMenuLichHen;
    private JPanel pnlMenuNhanVien;
    private JPanel pnlMenuDieuTri;
    private JPanel pnlMenuHoaDon;

    private JLabel lblAvatar;
    private JLabel lblTenNguoiDung;
    private JLabel lblVaiTro;

    // Menu chính
    private JButton btnTrangChu;
    private JButton btnLichHen;
    private JButton btnNhanVien;
    private JButton btnNhanVienItem;
    private JButton btnDieuTri;
    private JButton btnHoaDon;
    private JButton btnTraCuu;
    private JButton btnThongKe;
    private JButton btnHoTro;
    private JButton btnThoat;

    // Các chức năng con
    private JButton btnTiepNhanLichHen;
    private JButton btnCheckIn;
    private JButton btnPhanCong;
    private JButton btnTaiKhoan;

    private JButton btnHoSoBenhNhan;
    private JButton btnKeHoachDieuTri;
    private JButton btnDichVu;
    private JButton btnPhongDieuTri;

    private JButton btnTaoHoaDon;
    private JButton btnTraCuuHoaDon;
    private JButton btnKhuyenMai;
    private JButton btnVatTu;

    private JButton nutDangChon;

    private boolean dangMoLichHen = false;
    private boolean dangMoNhanVien = false;
    private boolean dangMoDieuTri = false;
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
        pnlThongTinNguoiDung.setLayout(
                new BorderLayout(0, 10));
        pnlThongTinNguoiDung.setBackground(mauMenu);
        pnlThongTinNguoiDung.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10));

        Icon iconAvatar = FontIcon.of(
                FontAwesomeSolid.USER_CIRCLE, 65, mauTrang);

        lblAvatar = new JLabel(iconAvatar);
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);

        lblTenNguoiDung = new JLabel("Nguyễn Văn A");
        lblTenNguoiDung.setFont(
                new Font("Arial", Font.BOLD, 16));
        lblTenNguoiDung.setForeground(mauTrang);
        lblTenNguoiDung.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblVaiTro = new JLabel("Quản trị viên");
        lblVaiTro.setFont(
                new Font("Arial", Font.PLAIN, 13));
        lblVaiTro.setForeground(mauTrang);
        lblVaiTro.setHorizontalAlignment(
                SwingConstants.CENTER);

        JPanel pnlTenVaVaiTro = new JPanel(
                new GridLayout(2, 1, 0, 5));
        pnlTenVaVaiTro.setBackground(mauMenu);
        pnlTenVaVaiTro.add(lblTenNguoiDung);
        pnlTenVaVaiTro.add(lblVaiTro);

        pnlThongTinNguoiDung.add(
                lblAvatar, BorderLayout.NORTH);
        pnlThongTinNguoiDung.add(
                pnlTenVaVaiTro, BorderLayout.CENTER);
    }

    // ================= TẠO DANH SÁCH MENU =================

    private void taoDanhSachManHinh() {
        pnlDanhSachManHinh = new JPanel();
        pnlDanhSachManHinh.setLayout(
                new BoxLayout(pnlDanhSachManHinh, BoxLayout.Y_AXIS));
        pnlDanhSachManHinh.setBackground(mauMenu);
        pnlDanhSachManHinh.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Trang chủ
        btnTrangChu = taoNutMenu(
                "Trang chủ", FontAwesomeSolid.HOME);

        // 2. Quản lý lịch hẹn
        btnLichHen = taoNutMenu(
                "Quản lý lịch hẹn  ▼",
                FontAwesomeSolid.CALENDAR_ALT);

        btnTiepNhanLichHen = taoNutCon(
                "Tiếp nhận lịch hẹn",
                FontAwesomeSolid.CALENDAR_CHECK);

        btnCheckIn = taoNutCon(
                "Check In",
                FontAwesomeSolid.CLIPBOARD_CHECK);

        pnlMenuLichHen = taoPanelMenuCon();
        pnlMenuLichHen.add(btnTiepNhanLichHen);
        pnlMenuLichHen.add(btnCheckIn);
        pnlMenuLichHen.setVisible(false);

        // 3. Quản lý nhân sự
        btnNhanVien = taoNutMenu(
                "Quản lý nhân viên  ▼",
                FontAwesomeSolid.USER_TIE);

        btnPhanCong = taoNutCon(
                "Phân công nhân viên",
                FontAwesomeSolid.TASKS);

        btnTaiKhoan = taoNutCon(
                "Tài khoản",
                FontAwesomeSolid.USER_CIRCLE);

        pnlMenuNhanVien = taoPanelMenuCon();

        btnNhanVienItem = taoNutCon(
                "Nhân viên",
                FontAwesomeSolid.USERS
        );

        pnlMenuNhanVien.add(btnNhanVienItem);
        pnlMenuNhanVien.add(btnPhanCong);
        pnlMenuNhanVien.add(btnTaiKhoan);

        pnlMenuNhanVien.setVisible(false);

        // 4. Quản lý khám và điều trị
        btnDieuTri = taoNutMenu(
                "Quản lý phòng điều trị  ▼",
                FontAwesomeSolid.TOOTH);

        btnHoSoBenhNhan = taoNutCon(
                "Hồ sơ bệnh nhân",
                FontAwesomeSolid.FOLDER_OPEN);

        btnKeHoachDieuTri = taoNutCon(
                "Kế hoạch điều trị",
                FontAwesomeSolid.CLIPBOARD_LIST);

        btnDichVu = taoNutCon(
                "Dịch vụ",
                FontAwesomeSolid.TOOTH);

        btnPhongDieuTri = taoNutCon(
                "Phòng điều trị",
                FontAwesomeSolid.HOSPITAL);

        pnlMenuDieuTri = taoPanelMenuCon();
        pnlMenuDieuTri.add(btnHoSoBenhNhan);
        pnlMenuDieuTri.add(btnKeHoachDieuTri);
        pnlMenuDieuTri.add(btnDichVu);
        pnlMenuDieuTri.add(btnPhongDieuTri);
        pnlMenuDieuTri.setVisible(false);

        // 5. Quản lý hóa đơn
     // 5. Quản lý hóa đơn
        btnHoaDon = taoNutMenu(
                "Quản lý hóa đơn  ▼",
                FontAwesomeSolid.FILE_INVOICE_DOLLAR
        );

        // Class LichHen nhưng tên hiển thị là Tạo hóa đơn
        btnTaoHoaDon = taoNutCon(
                "Tạo hóa đơn",
                FontAwesomeSolid.CALENDAR_CHECK
        );

        btnTraCuuHoaDon = taoNutCon(
                "Tra cứu hóa đơn",
                FontAwesomeSolid.SEARCH
        );

        btnKhuyenMai = taoNutCon(
                "Khuyến mãi",
                FontAwesomeSolid.TAG
        );

        btnVatTu = taoNutCon(
                "Vật tư",
                FontAwesomeSolid.BOXES
        );

        pnlMenuHoaDon = taoPanelMenuCon();
        pnlMenuHoaDon.add(btnTaoHoaDon);
        pnlMenuHoaDon.add(btnTraCuuHoaDon);
        pnlMenuHoaDon.add(btnKhuyenMai);
        pnlMenuHoaDon.add(btnVatTu);

        pnlMenuHoaDon.setVisible(false);
        // 6. Các chức năng độc lập
       /* btnTraCuu = taoNutMenu(
                "Tra cứu",
                FontAwesomeSolid.SEARCH);*/

        btnThongKe = taoNutMenu(
                "Thống kê",
                FontAwesomeSolid.CHART_BAR);

        btnHoTro = taoNutMenu(
                "Hỗ trợ",
                FontAwesomeSolid.QUESTION_CIRCLE);

        // Thêm các mục theo thứ tự vào menu
        pnlDanhSachManHinh.add(btnTrangChu);

        pnlDanhSachManHinh.add(btnLichHen);
        pnlDanhSachManHinh.add(pnlMenuLichHen);

        pnlDanhSachManHinh.add(btnNhanVien);
        pnlDanhSachManHinh.add(pnlMenuNhanVien);

        pnlDanhSachManHinh.add(btnDieuTri);
        pnlDanhSachManHinh.add(pnlMenuDieuTri);

        pnlDanhSachManHinh.add(btnHoaDon);
        pnlDanhSachManHinh.add(pnlMenuHoaDon);

      //  pnlDanhSachManHinh.add(btnTraCuu);
        pnlDanhSachManHinh.add(btnThongKe);
        pnlDanhSachManHinh.add(btnHoTro);
    }

    // ================= TẠO PANEL MENU CON =================

    private JPanel taoPanelMenuCon() {
        JPanel pnl = new JPanel();
        pnl.setLayout(
                new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setBackground(mauMenu);
        pnl.setAlignmentX(LEFT_ALIGNMENT);
        pnl.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 500));
        return pnl;
    }

    // ================= TẠO NÚT MENU =================

    private JButton taoNutMenu(
            String tenNut,
            FontAwesomeSolid tenIcon) {

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

        btn.setBorder(
                BorderFactory.createEmptyBorder(10, 12, 10, 8));
        btn.setPreferredSize(new Dimension(220, 44));
        btn.setMinimumSize(new Dimension(0, 44));
        btn.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 44));
        btn.setAlignmentX(LEFT_ALIGNMENT);

        btn.addActionListener(this);

        return btn;
    }

    private JButton taoNutCon(
            String tenNut,
            FontAwesomeSolid tenIcon) {

        JButton btn = taoNutMenu(tenNut, tenIcon);
        btn.setBorder(
                BorderFactory.createEmptyBorder(10, 25, 10, 8));
        return btn;
    }

    // ================= CẬP NHẬT NÚT ĐANG CHỌN =================

    private void capNhatMauNut(JButton nutMoi) {
        if (nutDangChon != null) {
            nutDangChon.setBackground(mauMenu);
            nutDangChon.setForeground(mauTrang);
        }

        nutDangChon = nutMoi;

        if (nutDangChon != null) {
            nutDangChon.setBackground(mauXanhChon);
            nutDangChon.setForeground(mauTrang);
        }

        pnlDanhSachManHinh.revalidate();
        pnlDanhSachManHinh.repaint();
    }

    // ================= MỞ / ĐÓNG MENU CON =================

    private void capNhatMenuCon(
            JPanel panel,
            JButton nut,
            boolean dangMo,
            String tenDong,
            String tenMo) {

        panel.setVisible(dangMo);
        nut.setText(dangMo ? tenMo : tenDong);

        pnlDanhSachManHinh.revalidate();
        pnlDanhSachManHinh.repaint();
    }

    // ================= XỬ LÝ SỰ KIỆN =================

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        // Tổng quan
        if (o == btnTrangChu) {
            capNhatMauNut(btnTrangChu);
            parentView.hienThiManHinh("Trang chủ");
        }

        // Mở / đóng nhóm Lịch hẹn
        else if (o == btnLichHen) {
            dangMoLichHen = !dangMoLichHen;

            capNhatMenuCon(
                    pnlMenuLichHen,
                    btnLichHen,
                    dangMoLichHen,
                    "Quản lý lịch hẹn  ▼",
                    "Quản lý lịch hẹn  ▲");

            if (dangMoLichHen) {
                capNhatMauNut(btnLichHen);
            }
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

            capNhatMenuCon(
                    pnlMenuNhanVien,
                    btnNhanVien,
                    dangMoNhanVien,
                    "Quản lý nhân viên  ▼",
                    "Quản lý nhân viên  ▲"
            );

            if (dangMoNhanVien) {
                capNhatMauNut(btnNhanVien);
            }
        }

        // Mở / đóng nhóm Khám và điều trị
        else if (o == btnDieuTri) {
            dangMoDieuTri = !dangMoDieuTri;

            capNhatMenuCon(
                    pnlMenuDieuTri,
                    btnDieuTri,
                    dangMoDieuTri,
                    "Quản lý phòng điều trị  ▼",
                    "Quản lý phòng điều trị  ▲");

            if (dangMoDieuTri) {
                capNhatMauNut(btnDieuTri);
            }
        }

        // Mở / đóng nhóm Tài chính
        else if (o == btnHoaDon) {
            dangMoHoaDon = !dangMoHoaDon;

            capNhatMenuCon(
                    pnlMenuHoaDon,
                    btnHoaDon,
                    dangMoHoaDon,
                    "Quản lý hóa đơn  ▼",
                    "Quản lý hóa đơn  ▲"
            );

            if (dangMoHoaDon) {
                capNhatMauNut(btnHoaDon);
            }
        }
        else if (o == btnTaoHoaDon) {
            capNhatMauNut(btnTaoHoaDon);
            parentView.hienThiManHinh("Tạo hóa đơn");
        }

        else if (o == btnTraCuuHoaDon) {
            capNhatMauNut(btnTraCuuHoaDon);
            parentView.hienThiManHinh("Tra cứu hóa đơn");
        }
        // Các chức năng con
        else if (o == btnNhanVienItem) {
            capNhatMauNut(btnNhanVienItem);
            parentView.hienThiManHinh("Nhân viên");
        }

        else if (o == btnPhanCong) {
            capNhatMauNut(btnPhanCong);
            parentView.hienThiManHinh("Phân công nhân viên");
        }

        else if (o == btnTaiKhoan) {
            capNhatMauNut(btnTaiKhoan);
            parentView.hienThiManHinh("Tài khoản");
        }

        else if (o == btnHoSoBenhNhan) {
            capNhatMauNut(btnHoSoBenhNhan);
            parentView.hienThiManHinh("Hồ sơ bệnh nhân");
        }

        else if (o == btnKeHoachDieuTri) {
            capNhatMauNut(btnKeHoachDieuTri);
            parentView.hienThiManHinh("Kế hoạch điều trị");
        }

        else if (o == btnDichVu) {
            capNhatMauNut(btnDichVu);
            parentView.hienThiManHinh("Dịch vụ");
        }

        else if (o == btnPhongDieuTri) {
            capNhatMauNut(btnPhongDieuTri);
            parentView.hienThiManHinh("Phòng điều trị");
        }

        else if (o == btnHoaDon) {
            capNhatMauNut(btnHoaDon);
            parentView.hienThiManHinh("Tra cứu hóa đơn");
        }

        else if (o == btnKhuyenMai) {
            capNhatMauNut(btnKhuyenMai);
            parentView.hienThiManHinh("Khuyến mãi");
        }

        else if (o == btnVatTu) {
            capNhatMauNut(btnVatTu);
            parentView.hienThiManHinh("Vật tư");
        }


        else if (o == btnThongKe) {
            capNhatMauNut(btnThongKe);
            parentView.hienThiManHinh("Thống kê");
        }

        else if (o == btnHoTro) {
            capNhatMauNut(btnHoTro);
            parentView.hienThiManHinh("Hỗ trợ");
        }

        // Đăng xuất
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


    public void capNhatThongTinNguoiDung( String tenNguoiDung, String vaiTro) {
        lblTenNguoiDung.setText(tenNguoiDung);
        lblVaiTro.setText(vaiTro);
    }

    // ================= ĐĂNG XUẤT =================

    private void taoNutDangXuat() {
        pnlDangXuat = new JPanel(new BorderLayout());
        pnlDangXuat.setBackground(mauMenu);
        pnlDangXuat.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 20, 10));

        btnThoat = taoNutMenu(
                "Đăng xuất",
                FontAwesomeSolid.SIGN_OUT_ALT);

        btnThoat.setBackground(mauDo);
        btnThoat.setForeground(mauTrang);

        pnlDangXuat.add(btnThoat, BorderLayout.CENTER);
    }
}
