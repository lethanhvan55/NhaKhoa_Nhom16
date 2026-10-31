package ui;

import dao.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import model.NhanVien;
import model.VatTu;
import utils.JavaFXRuntime;
import utils.PhienDangNhap;
import utils.UIHelper;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import static utils.UIHelper.*;

public class HomePanel extends JPanel implements ActionListener {

    private final Color MAU_CHU = NAVY;
    private final Color MAU_NEN = BG;
    private final Color MAU_VIEN = BORDER;

    private JLabel lblDongHo;

    private JLabel lblMaNV, lblTenNV, lblChucVu, lblCaTruc;


    private JButton btnXemTatCaThongBao;
    private JButton btnDangXuat;
    private JFXPanel pnlBieuDoDoanhThu;
    private XYChart.Series<String, Number> seriesDoanhThu;
    private JLabel lblTongBenhNhan;
    private JLabel lblLichHenHomNay;
    private JLabel lblTongKeHoach;
    private JLabel lblHoaDonChoThu;
    private JFXPanel pnlBieuDoDichVu;
    private BarChart<Number, String> bieuDoDichVu;
    private NumberAxis trucSoLuotDung;
    private CategoryAxis trucTenDichVu;
    private XYChart.Series<Number, String> seriesDichVu;
    private List<Map<String, Object>> duLieuTop5DichVu = new ArrayList<>();
    private DefaultTableModel modelThongBao;
    private Timer timerThongBao;
    private JTable tblThongBao;

    public HomePanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(MAU_NEN);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(taoKhungDongHoVaThaoTacTop(), BorderLayout.NORTH);
        add(taoNoiDungChinh(), BorderLayout.CENTER);

        khoiDongDongHo();
        dangKySuKien();
        loadDuLieuTongQuanTuCSDL();
        loadThongTinNhanVien();
        batDongBoThongBao();
    }

    private JPanel taoKhungDongHoVaThaoTacTop() {
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);
        pnlTop.setBorder(new EmptyBorder(0, 0, 5, 0));

        JLabel lblTieuDeTrang = new JLabel("BẢNG ĐIỀU HÀNH TỔNG QUAN HỆ THỐNG");
        lblTieuDeTrang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTieuDeTrang.setForeground(MAU_CHU);

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        pnlRight.setOpaque(false);

        JLabel lblIconClock = new JLabel(FontIcon.of(FontAwesomeSolid.CLOCK, 14, NAVY));
        lblDongHo = new JLabel();
        lblDongHo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDongHo.setForeground(NAVY);

    /*    btnLamMoiDuLieu = new JButton("Làm mới");
        styleButton(btnLamMoiDuLieu, true, FontAwesomeSolid.SYNC_ALT);*/

        pnlRight.add(lblIconClock);
        pnlRight.add(lblDongHo);
    /*    pnlRight.add(new JLabel(" | "));
        pnlRight.add(btnLamMoiDuLieu); */

        pnlTop.add(lblTieuDeTrang, BorderLayout.WEST);
        pnlTop.add(pnlRight, BorderLayout.EAST);

        return pnlTop;
    }

    private void khoiDongDongHo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        ActionListener updateClock = e -> lblDongHo.setText(sdf.format(new Date()));
        Timer timer = new Timer(1000, updateClock);
        timer.setInitialDelay(0);
        timer.start();
    }

    private JPanel taoNoiDungChinh() {
        JPanel pnl = new JPanel(new BorderLayout(15, 15));
        pnl.setOpaque(false);

        pnl.add(taoThongKeNoiBat(), BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlCenter.setOpaque(false);
        pnlCenter.add(taoKhuVucBieuDo());

        // Sử dụng GridLayout(2, 1, 0, 12) để ép 2 khung cột phải có chiều rộng ngang bằng nhau hoàn toàn
        JPanel pnlRightColumn = new JPanel(new GridLayout(2, 1, 0, 12));
        pnlRightColumn.setOpaque(false);
        pnlRightColumn.add(taoKhuVucThongBaoThuNho());
        pnlRightColumn.add(taoKhungThongTinNhanVienToHon());

        pnlCenter.add(pnlRightColumn);

        pnl.add(pnlCenter, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoThongKeNoiBat() {
        JPanel pnl = new JPanel(new GridLayout(1, 4, 12, 0));
        pnl.setOpaque(false);

        pnl.add(taoTheThongKe("TỔNG SỐ BỆNH NHÂN", "0", "Hồ sơ lưu trữ trên hệ thống", new Color(48, 125, 220), FontAwesomeSolid.USER_INJURED));
        pnl.add(taoTheThongKe("LỊCH HẸN HÔM NAY", "0", "Cần tiếp nhận & khám", new Color(15, 150, 137), FontAwesomeSolid.CALENDAR_CHECK));
        pnl.add(taoTheThongKe("KẾ HOẠCH ĐIỀU TRỊ", "0", "Kế hoạch đang thực hiện", new Color(140, 95, 200), FontAwesomeSolid.PROCEDURES));
        pnl.add(taoTheThongKe("HÓA ĐƠN CHỜ THU", "0", "Chưa hoàn tất thanh toán", new Color(230, 115, 35), FontAwesomeSolid.FILE_INVOICE_DOLLAR));

        return pnl;
    }

    private JPanel taoTheThongKe(String tieuDe, String giaTri, String moTa, Color mau, FontAwesomeSolid iconEnum) {
        JPanel pnl = createCardPanel();
        pnl.setLayout(new BorderLayout(5, 5));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(MAU_VIEN, 1, true),
                new EmptyBorder(12, 15, 12, 15)
        ));

        JPanel pnlHeaderCard = new JPanel(new BorderLayout());
        pnlHeaderCard.setOpaque(false);

        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTieuDe.setForeground(MUTED);

        JLabel lblIcon = UIHelper.createIconLabel(iconEnum, 20, mau);

        pnlHeaderCard.add(lblTieuDe, BorderLayout.WEST);
        pnlHeaderCard.add(lblIcon, BorderLayout.EAST);

        JLabel lblGiaTri = new JLabel(giaTri);
        lblGiaTri.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblGiaTri.setForeground(mau);

        switch (tieuDe) {
            case "TỔNG SỐ BỆNH NHÂN":
                lblTongBenhNhan = lblGiaTri;
                break;
            case "LỊCH HẸN HÔM NAY":
                lblLichHenHomNay = lblGiaTri;
                break;
            case "KẾ HOẠCH ĐIỀU TRỊ":
                lblTongKeHoach = lblGiaTri;
                break;
            case "HÓA ĐƠN CHỜ THU":
                lblHoaDonChoThu = lblGiaTri;
                break;
        }

        JLabel lblMoTa = new JLabel(moTa);
        lblMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblMoTa.setForeground(MUTED);

        pnl.add(pnlHeaderCard, BorderLayout.NORTH);
        pnl.add(lblGiaTri, BorderLayout.CENTER);
        pnl.add(lblMoTa, BorderLayout.SOUTH);
        pnl.setPreferredSize(new Dimension(180, 110));

        return pnl;
    }

    private JPanel taoKhuVucBieuDo() {
        JPanel pnl = new JPanel(new GridLayout(2, 1, 0, 12));
        pnl.setOpaque(false);

        pnl.add(taoKhungChartDoanhThu());
        pnl.add(taoKhungChartDichVu());

        return pnl;
    }


    private JPanel taoKhuVucThongBaoThuNho() {
        JPanel pnlThongBao = createCardPanel();
        pnlThongBao.setLayout(new BorderLayout(4, 4));
        pnlThongBao.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(MAU_VIEN, 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));

        JPanel pnlHeaderTB = new JPanel(new BorderLayout());
        pnlHeaderTB.setOpaque(false);

        JLabel lblTBTieuDe = new JLabel("THÔNG BÁO & NHẮC NHỞ QUAN TRỌNG");
        lblTBTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTBTieuDe.setForeground(MAU_CHU);

        btnXemTatCaThongBao = new JButton("Xem tất cả");
        styleButton(btnXemTatCaThongBao, false, FontAwesomeSolid.LIST_ALT);

        pnlHeaderTB.add(lblTBTieuDe, BorderLayout.WEST);
        pnlHeaderTB.add(btnXemTatCaThongBao, BorderLayout.EAST);
        pnlThongBao.add(pnlHeaderTB, BorderLayout.NORTH);

        String[] cot = {"STT", "Thời gian", "Nội dung thông báo", "Mức độ"};
        modelThongBao = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };


        JTable tblThongBao = new JTable(modelThongBao);
        tblThongBao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblThongBao.setForeground(TEXT);
        tblThongBao.setRowHeight(22);
        tblThongBao.setGridColor(new Color(230, 240, 250));
        tblThongBao.setShowVerticalLines(false);
        tblThongBao.setSelectionBackground(new Color(220, 235, 252));
        tblThongBao.setSelectionForeground(MAU_CHU);

        tblThongBao.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tblThongBao.getTableHeader().setBackground(MAU_CHU);
        tblThongBao.getTableHeader().setForeground(Color.WHITE);
        tblThongBao.getTableHeader().setPreferredSize(new Dimension(0, 22));

        tblThongBao.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblThongBao.getColumnModel().getColumn(1).setPreferredWidth(65);
        tblThongBao.getColumnModel().getColumn(2).setPreferredWidth(215);
        tblThongBao.getColumnModel().getColumn(3).setPreferredWidth(80);

        JScrollPane scrTB = new JScrollPane(tblThongBao);
        scrTB.setBorder(new LineBorder(MAU_VIEN, 1, true));
        scrTB.getViewport().setBackground(Color.WHITE);
        pnlThongBao.add(scrTB, BorderLayout.CENTER);

        return pnlThongBao;
    }
    private void kiemTraThongBaoLichHen() {
        try {
            LichHenDAO lichHenDAO = new LichHenDAO();
            List<Map<String, Object>> danhSach = lichHenDAO.getAllLichHen();

            LocalDateTime hienTai = LocalDateTime.now();

            for (Map<String, Object> row : danhSach) {
                String maLichHen = String.valueOf(row.get("MaLichHen"));
                String tenBenhNhan = String.valueOf(row.get("TenBenhNhan"));
                LocalDate ngayHen = (LocalDate) row.get("NgayHen");
                LocalDateTime gioHen = (LocalDateTime) row.get("GioHen");
                String trangThai = String.valueOf(row.get("TrangThai"));

                if (ngayHen == null || gioHen == null) {
                    continue;
                }

                if (!ngayHen.equals(hienTai.toLocalDate())) {
                    continue;
                }

                if ("Đang chờ khám".equals(trangThai)) {
                    themThongBao(
                            "Lịch hẹn " + maLichHen + ": Bệnh nhân " + tenBenhNhan + " đang chờ khám.",
                            "Quan trọng"
                    );
                    continue;
                }

                if (!"Đã đặt".equals(trangThai)) {
                    continue;
                }

                long soPhut = Duration.between(hienTai, gioHen).toMinutes();

                if (soPhut >= 0 && soPhut <= 60) {
                    themThongBao(
                            "Lịch hẹn " + maLichHen + ": Bệnh nhân " + tenBenhNhan
                                    + " sắp đến giờ khám (" + gioHen.toLocalTime().toString().substring(0, 5) + ").",
                            "Quan trọng"
                    );
                } else if (soPhut < 0) {
                    themThongBao(
                            "Lịch hẹn " + maLichHen + ": Bệnh nhân " + tenBenhNhan
                                    + " đã quá giờ hẹn nhưng chưa check-in.",
                            "Cảnh báo"
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void kiemTraThongBaoVatTu() {
        try {
            VatTuDAO vatTuDAO = new VatTuDAO();
            List<VatTu> danhSach = vatTuDAO.getAll();

            for (VatTu vt : danhSach) {
                int soLuongTon = vt.getSoLuongTon();
                int soLuongToiThieu = vt.getSoLuongToiThieu();

                if (soLuongTon <= 0) {
                    themThongBao("Vật tư " + vt.getTenVatTu() + " đã hết hàng.", "Cảnh báo");
                } else if (soLuongTon <= soLuongToiThieu) {
                    themThongBao(
                            "Vật tư " + vt.getTenVatTu()
                                    + " sắp hết. Số lượng tồn: " + soLuongTon
                                    + " " + vt.getDonViTinh() + ".",
                            "Quan trọng"
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void themThongBao(String noiDung, String mucDo) {
        String thoiGian = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        int stt = modelThongBao.getRowCount() + 1;
        modelThongBao.addRow(new Object[]{stt, thoiGian, noiDung, mucDo});
    }
    private void kiemTraThongBao() {
        modelThongBao.setRowCount(0);

        kiemTraThongBaoLichHen();
        kiemTraThongBaoVatTu();
    }
    private void batDongBoThongBao() {
        kiemTraThongBao();

        timerThongBao = new Timer(60000, e -> {
            kiemTraThongBao();
        });

        timerThongBao.start();
    }

    private JPanel taoKhungThongTinNhanVienToHon() {
        JPanel pnlCard = createCardPanel();
        pnlCard.setLayout(new BorderLayout(10, 10));
        pnlCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(MAU_VIEN, 1, true),
                new EmptyBorder(14, 16, 14, 16)
        ));

        JLabel lblTieuDeNV = new JLabel("THÔNG TIN NHÂN VIÊN ĐĂNG NHẬP");
        lblTieuDeNV.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTieuDeNV.setForeground(MAU_CHU);

        JPanel pnlDetails = new JPanel(new GridLayout(2, 2, 5, 4));
        pnlDetails.setOpaque(false);

        // 💡 Bỏ thông tin viết cứng, thay bằng giá trị chờ mặc định
        lblMaNV = new JLabel("Mã NV: ---");
        lblMaNV.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMaNV.setForeground(TEXT);

        lblTenNV = new JLabel("Họ tên: ---");
        lblTenNV.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTenNV.setForeground(TEAL);

        lblChucVu = new JLabel("Chức vụ: ---");
        lblChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblChucVu.setForeground(TEXT);

        lblCaTruc = new JLabel("Trạng thái: ---");
        lblCaTruc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCaTruc.setForeground(MUTED);

        pnlDetails.add(lblMaNV);
        pnlDetails.add(lblTenNV);
        pnlDetails.add(lblChucVu);
        pnlDetails.add(lblCaTruc);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(new EmptyBorder(5, 0, 0, 0));

        btnDangXuat = new JButton("Đăng xuất hệ thống");
        styleButton(btnDangXuat, false, FontAwesomeSolid.SIGN_OUT_ALT);
        pnlBottom.add(btnDangXuat);

        pnlCard.add(lblTieuDeNV, BorderLayout.NORTH);
        pnlCard.add(pnlDetails, BorderLayout.CENTER);
        pnlCard.add(pnlBottom, BorderLayout.SOUTH);

        return pnlCard;
    }

    private JPanel taoKhungChartDichVu() {
        JPanel pnl = createCardPanel();
        pnl.setLayout(new BorderLayout(5, 5));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(MAU_VIEN, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        pnlBieuDoDichVu = new JFXPanel();

        JavaFXRuntime.chay(() -> {
            trucSoLuotDung = new NumberAxis();
            trucSoLuotDung.setLabel("Số lượt sử dụng");
            trucSoLuotDung.setForceZeroInRange(true);
            trucSoLuotDung.setTickUnit(1);
            trucSoLuotDung.setMinorTickVisible(false);

            trucTenDichVu = new CategoryAxis();
            trucTenDichVu.setLabel("Tên dịch vụ");
            trucTenDichVu.setTickLabelRotation(0);

            bieuDoDichVu = new BarChart<>(trucSoLuotDung, trucTenDichVu);
            bieuDoDichVu.setTitle("TOP 5 dịch vụ được sử dụng nhiều nhất");
            bieuDoDichVu.setLegendVisible(false);
            bieuDoDichVu.setAnimated(false);
            bieuDoDichVu.setCategoryGap(12);
            bieuDoDichVu.setBarGap(3);

            seriesDichVu = new XYChart.Series<>();
            bieuDoDichVu.getData().add(seriesDichVu);

            Scene scene = new Scene(bieuDoDichVu);
            pnlBieuDoDichVu.setScene(scene);

            capNhatBieuDoDichVu();
        });

        pnl.add(pnlBieuDoDichVu, BorderLayout.CENTER);
        return pnl;
    }
    private void capNhatBieuDoDichVu() {
        if (seriesDichVu == null || bieuDoDichVu == null) {
            return;
        }

        seriesDichVu.getData().clear();

        int dem = 0;

        for (Map<String, Object> row : duLieuTop5DichVu) {
            int soLuotDung = ((Number) row.get("SoLuotDung")).intValue();

            if (soLuotDung <= 0) {
                continue;
            }

            String tenDV = String.valueOf(row.get("TenDV"));

            seriesDichVu.getData().add(
                    new XYChart.Data<>(soLuotDung, tenDV)
            );

            dem++;

            if (dem == 5) {
                break;
            }
        }

        if (dem == 0) {
            bieuDoDichVu.setTitle("Chưa có dữ liệu sử dụng dịch vụ trong ngày");
        } else {
            bieuDoDichVu.setTitle("TOP 5 dịch vụ được sử dụng nhiều nhất");
        }
    }
    private JPanel taoKhungChartDoanhThu() {
        JPanel pnl = createCardPanel();
        pnl.setLayout(new BorderLayout(5, 5));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(MAU_VIEN, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        pnlBieuDoDoanhThu = new JFXPanel();

        JavaFXRuntime.chay(() -> {
            CategoryAxis trucNgay = new CategoryAxis();
            trucNgay.setLabel("Ngày");

            NumberAxis trucDoanhThu = new NumberAxis();
            trucDoanhThu.setLabel("Doanh thu (VNĐ)");
            trucDoanhThu.setForceZeroInRange(true);

            BarChart<String, Number> bieuDo = new BarChart<>(trucNgay, trucDoanhThu);
            bieuDo.setTitle("Doanh thu theo ngày");
            bieuDo.setLegendVisible(false);
            bieuDo.setAnimated(false);
            bieuDo.setCategoryGap(15);
            bieuDo.setBarGap(3);

            seriesDoanhThu = new XYChart.Series<>();
            bieuDo.getData().add(seriesDoanhThu);

            Scene scene = new Scene(bieuDo);
            pnlBieuDoDoanhThu.setScene(scene);
        });

        pnl.add(pnlBieuDoDoanhThu, BorderLayout.CENTER);

        return pnl;
    }
    public void refreshData() {
        xuLyLamMoiTrangChu();
    }

    private void dangKySuKien() {
    //    btnLamMoiDuLieu.addActionListener(this);
        btnXemTatCaThongBao.addActionListener(this);
        btnDangXuat.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnXemTatCaThongBao)) {
            xuLyXemTatCaThongBao();
        } else if (source.equals(btnDangXuat)) {
            xuLyDangXuat();
        }
    }

    private void loadDuLieuTongQuanTuCSDL() {
        try {
            HoaDonDao hoaDonDao = new HoaDonDao();
            Map<LocalDate, Double> duLieu = hoaDonDao.getDoanhThuTheoNgay();

            JavaFXRuntime.chay(() -> {
                if (seriesDoanhThu == null) {
                    return;
                }

                seriesDoanhThu.getData().clear();

                DateTimeFormatter dinhDangNgay = DateTimeFormatter.ofPattern("dd/MM");

                for (Map.Entry<LocalDate, Double> entry : duLieu.entrySet()) {
                    String ngay = entry.getKey().format(dinhDangNgay);
                    double doanhThu = entry.getValue();

                    seriesDoanhThu.getData().add(
                            new XYChart.Data<>(ngay, doanhThu)
                    );
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể tải dữ liệu doanh thu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        try {
            ThongKeTongQuan_DAO thongKeDAO = new ThongKeTongQuan_DAO();

            lblTongBenhNhan.setText(String.valueOf(thongKeDAO.getTongSoBenhNhan()));
            lblLichHenHomNay.setText(String.valueOf(thongKeDAO.getLichHenHomNay()));
            lblTongKeHoach.setText(String.valueOf(thongKeDAO.getTongKeHoachDieuTri()));
            lblHoaDonChoThu.setText(String.valueOf(thongKeDAO.getLichHenChoKham()));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể tải dữ liệu thống kê: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        try {
            DichVu_DAO dichVuDAO = new DichVu_DAO();

            java.sql.Date ngayBatDau = java.sql.Date.valueOf(LocalDate.now());
            java.sql.Date ngayKetThuc = java.sql.Date.valueOf(LocalDate.now().plusDays(1));

            List<Map<String, Object>> danhSach = dichVuDAO.getThongKeDichVu(
                    "Ngày",
                    "Tất cả trạng thái",
                    ngayBatDau,
                    ngayKetThuc
            );

            duLieuTop5DichVu = new ArrayList<>(
                    danhSach.subList(0, Math.min(5, danhSach.size()))
            );
            JavaFXRuntime.chay(() -> capNhatBieuDoDichVu());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadThongTinNhanVien() {
        try {
            String maNV = PhienDangNhap.getMaNV();

            if (maNV == null || maNV.trim().isEmpty()) {
                lblMaNV.setText("Mã NV: Chưa xác định");
                lblTenNV.setText("Họ tên: Chưa xác định");
                lblChucVu.setText("Chức vụ: Chưa xác định");
                lblCaTruc.setText("Trạng thái: Chưa xác định");
                return;
            }

            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            NhanVien nv = nhanVienDAO.getNhanVienTheoMa(maNV);

            if (nv != null) {
                lblMaNV.setText("Mã NV: " + nv.getMaNV());
                lblTenNV.setText("Họ tên: " + nv.getHoTen());
                lblChucVu.setText("Chức vụ: " + nv.getChucVu());
                lblCaTruc.setText("Trạng thái: " + nv.getTrangThai());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể tải thông tin nhân viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void xuLyLamMoiTrangChu() {
        loadDuLieuTongQuanTuCSDL();
      //  JOptionPane.showMessageDialog(this, "Đã cập nhật dữ liệu mới nhất cho trang chủ!");
    }

    private void xuLyXemTatCaThongBao() {
        JOptionPane.showMessageDialog(this, "Mở cửa sổ xem tất cả thông báo hệ thống.");
    }

    private void xuLyDangXuat() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất khỏi hệ thống?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Đăng xuất thành công.");
        }
    }
}