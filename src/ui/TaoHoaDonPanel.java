package ui;

import dao.DichVu_DAO;
import dao.HoaDonDao;
import dao.KhuyenMai_DAO;
import dao.LichHenDAO;
import model.*;
import utils.GiaoDienStyle;
import utils.PhienDangNhap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TaoHoaDonPanel extends JPanel implements ActionListener, MouseListener {

    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;
    private final Color MAU_DO = GiaoDienStyle.MAU_DO;

    private JTable tblLichHen;
    private JTable tblDichVu;
    private DefaultTableModel modelLichHen;
    private DefaultTableModel modelDichVu;

    //private JTextField txtMaLichHen;
    // private JTextField txtMaBenhNhan;
    private JTextField txtTenKhachHang;
    private JTextField txtSoDienThoai;
    private JTextField txtNgayHen;
    private JTextField txtGioHen;
    // private JTextField txtSoKhach;
    private JTextField txtGhiChu;

    private JTextField txtMaGiamGia;
    private JTextField txtTongTien;
    private JTextField txtChietKhau;
    private JTextField txtThue;
    //  private JTextField txtTienCoc;
    private JTextField txtCanThanhToan;

    private JButton btnApDungMa;
    private JButton btnTienMat;
    private JButton btnChuyenKhoan;
    private JButton btnThanhToan;
    private JButton btnThemDichVu;
    private DichVu_DAO dichVuDAO;

    private final Map<String, DichVu> dichVuDaChon = new LinkedHashMap<>();
    //  private final Map<String, Integer> soLuongDaChon = new LinkedHashMap<>();
    private final Map<String, Integer> soLuongDaChon = new LinkedHashMap<>();
    private final Map<String, Map<String, DichVu>> dichVuTheoLichHen = new LinkedHashMap<>();
    private final Map<String, Map<String, Integer>> soLuongTheoLichHen = new LinkedHashMap<>();
    private HoaDonDao hoaDonDao;
    private LichHenDAO lichHenDAO;
    private String maLichHenDangChon = null;
    private KhuyenMai_DAO khuyenMaiDAO;
    private KhuyenMai khuyenMaiDangApDung;
    private double tienGiamGia = 0;
    private String phuongThucThanhToan = "";
    private long soTienKhachDua = 0;
    private long tienThua = 0;

    public TaoHoaDonPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        dichVuDAO = new DichVu_DAO();
        hoaDonDao = new HoaDonDao();
        khuyenMaiDAO = new KhuyenMai_DAO();
        lichHenDAO = new LichHenDAO();

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachLichHenTuCSDL();


    }

    private void taoGiaoDien() {
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


    private JPanel taoBenTrai() {
        JPanel pnlBenTrai = new JPanel(new BorderLayout(0, 0));
        pnlBenTrai.setBackground(MAU_NEN);

        JPanel pnlDanhSach = taoKhuVucDanhSachLichHen();
        JPanel pnlDichVu = taoKhuVucDichVu();

        JSplitPane splitDuoi = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                pnlDanhSach,
                pnlDichVu
        );

        splitDuoi.setResizeWeight(0.50);
        splitDuoi.setDividerLocation(340);
        splitDuoi.setDividerSize(7);
        splitDuoi.setBorder(null);
        splitDuoi.setBackground(MAU_NEN);

        pnlBenTrai.add(splitDuoi, BorderLayout.CENTER);

        return pnlBenTrai;
    }

    private JPanel taoKhuVucDanhSachLichHen() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Danh sách hóa đơn thanh toán");

        String[] cot = {
                "Mã lịch hẹn",
                "Tên khách hàng",
                "Số điện thoại",
                "Ngày hẹn",
                "Giờ",
                "Trạng thái"
        };

        modelLichHen = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLichHen = new JTable(modelLichHen);
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

        // JLabel lblTieuDe = taoTieuDe("Dịch vụ trong lịch hẹn");
        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(MAU_TRANG);

        JLabel lblTieuDe = taoTieuDe("Dịch vụ trong lịch hẹn");

        btnThemDichVu = taoNut("+ Thêm dịch vụ", MAU_TEAL);
        btnThemDichVu.setPreferredSize(new Dimension(145, 32));

        pnlTieuDe.add(lblTieuDe, BorderLayout.CENTER);
        pnlTieuDe.add(btnThemDichVu, BorderLayout.EAST);

        pnl.add(pnlTieuDe, BorderLayout.NORTH);
        String[] cot = {
                "STT",
                "Tên dịch vụ",
                "Số lượng",
                "Đơn giá",
                "Thành tiền"
        };

        modelDichVu = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(modelDichVu);
        tblDichVu.setRowHeight(30);
        tblDichVu.setFont(new Font("Arial", Font.PLAIN, 12));
        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblDichVu.getTableHeader().setBackground(MAU_NAVY);
        tblDichVu.getTableHeader().setForeground(Color.WHITE);
        GiaoDienStyle.dinhDangBang(tblDichVu);

        JScrollPane scrDichVu = new JScrollPane(tblDichVu);
        scrDichVu.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(pnlTieuDe, BorderLayout.NORTH);
        pnl.add(scrDichVu, BorderLayout.CENTER);

        return pnl;
    }

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

        //    txtMaLichHen = taoOThongTin("");
        //    txtMaBenhNhan = taoOThongTin("");
        txtTenKhachHang = taoOThongTin("");
        txtSoDienThoai = taoOThongTin("");
        txtNgayHen = taoOThongTin("");
        txtGioHen = taoOThongTin("");
        //     txtSoKhach = taoOThongTin("");
        txtGhiChu = taoOThongTin("");

        //   themTruong(pnlThongTin, "Mã lịch hẹn", txtMaLichHen);
        //   themTruong(pnlThongTin, "Mã bệnh nhân", txtMaBenhNhan);
        themTruong(pnlThongTin, "Tên khách hàng", txtTenKhachHang);
        themTruong(pnlThongTin, "Số điện thoại", txtSoDienThoai);
        themTruong(pnlThongTin, "Ngày hẹn", txtNgayHen);
        themTruong(pnlThongTin, "Giờ hẹn", txtGioHen);
        //  themTruong(pnlThongTin, "Số khách", txtSoKhach);
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

        btnApDungMa = taoNut("Áp dụng", MAU_TEAL);

        pnlMaGiamGia.add(txtMaGiamGia, BorderLayout.CENTER);
        pnlMaGiamGia.add(btnApDungMa, BorderLayout.EAST);

        JPanel pnlChiTietTien = new JPanel(new GridLayout(0, 2, 8, 7));
        pnlChiTietTien.setBackground(MAU_TRANG);

        txtTongTien = taoOThongTin("0 VNĐ");
        txtChietKhau = taoOThongTin("0 VNĐ");
        txtThue = taoOThongTin("0 VNĐ");
        // txtTienCoc = taoOThongTin("0 VNĐ");
        txtCanThanhToan = taoOThongTin("0 VNĐ");

        themDongTien(pnlChiTietTien, "Tổng tiền dịch vụ", txtTongTien);
        themDongTien(pnlChiTietTien, "Chiết khấu", txtChietKhau);
        themDongTien(pnlChiTietTien, "Thuế VAT (10%)", txtThue);
        //   themDongTien(pnlChiTietTien, "Tiền đã đặt cọc", txtTienCoc);

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

        btnTienMat = taoNut("Tiền mặt", MAU_TEAL);
        btnChuyenKhoan = taoNut("Chuyển khoản", MAU_NAVY);

        btnTienMat.setPreferredSize(new Dimension(150, 40));
        btnChuyenKhoan.setPreferredSize(new Dimension(150, 40));

        pnlPhuongThuc.add(btnTienMat);
        pnlPhuongThuc.add(btnChuyenKhoan);

        btnThanhToan = taoNut("THANH TOÁN & LẬP HÓA ĐƠN", new Color(230, 126, 34));
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 15));
        btnThanhToan.setPreferredSize(new Dimension(200, 44));

        JPanel pnlNutThanhToan = new JPanel(new BorderLayout());
        pnlNutThanhToan.setBackground(MAU_TRANG);
        pnlNutThanhToan.add(btnThanhToan, BorderLayout.CENTER);

        JPanel pnlNoiDung = new JPanel();
        pnlNoiDung.setLayout(new BoxLayout(pnlNoiDung, BoxLayout.Y_AXIS));
        pnlNoiDung.setBackground(MAU_TRANG);

        pnlNoiDung.add(pnlMaGiamGia);
        pnlNoiDung.add(Box.createVerticalStrut(8));
        pnlNoiDung.add(pnlChiTietTien);
        pnlNoiDung.add(Box.createVerticalStrut(8));
        pnlNoiDung.add(pnlTongThanhToan);
        pnlNoiDung.add(Box.createVerticalStrut(10));
        pnlNoiDung.add(pnlPhuongThuc);
        pnlNoiDung.add(Box.createVerticalStrut(10));
        pnlNoiDung.add(pnlNutThanhToan);

        //  pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(pnlNoiDung, BorderLayout.CENTER);

        return pnl;
    }
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
        txt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(225, 229, 235)), BorderFactory.createEmptyBorder(5, 7, 5, 7)));
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

    private void dangKySuKien() {
        tblLichHen.addMouseListener(this);
        btnApDungMa.addActionListener(this);
        btnTienMat.addActionListener(this);
        btnChuyenKhoan.addActionListener(this);
        btnThanhToan.addActionListener(this);
        btnThemDichVu.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnApDungMa)) {
            xuLyApDungMaGiamGia();
        } else if (source.equals(btnTienMat)) {
            moDialogTienMat();
        } else if (source.equals(btnChuyenKhoan)) {
            moDialogChuyenKhoan();
        } else if (source.equals(btnThanhToan)) {
            xuLyLuuHoaDonVaoCSDL();
        } else if (source.equals(btnThemDichVu)) {
            moDialogThemDichVu();

        }
    }

    public void refreshData() {
        taiDanhSachLichHenTuCSDL();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(tblLichHen)) {
            int row = tblLichHen.getSelectedRow();
            if (row >= 0) {
                maLichHenDangChon = String.valueOf(modelLichHen.getValueAt(row, 0)).trim();
                taiThongTinChiTietLichHenTuCSDL(maLichHenDangChon);
                taiDichVuTheoLichHen(maLichHenDangChon);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // ==================== CÁC HÀM KHUNG GỌI DAO / CSDL ====================

    private void taiDichVuTheoLichHen(String maLH) {
        dichVuDaChon.clear();
        soLuongDaChon.clear();
        modelDichVu.setRowCount(0);

        if (dichVuTheoLichHen.containsKey(maLH)) {
            dichVuDaChon.putAll(dichVuTheoLichHen.get(maLH));
            soLuongDaChon.putAll(soLuongTheoLichHen.getOrDefault(maLH, new LinkedHashMap<>()));
        } else {
            List<DichVu> danhSach = lichHenDAO.getDanhSachDichVuTheoLichHen(maLH);

            if (danhSach != null) {
                for (DichVu dv : danhSach) {
                    if (dv == null || dv.getMaDV() == null) {
                        continue;
                    }

                    String maDV = dv.getMaDV().trim();
                    dichVuDaChon.put(maDV, dv);
                    soLuongDaChon.put(maDV, 1);
                }
            }

            luuDichVuTamTheoLichHen(maLH);
        }

        capNhatBangDichVu();
    }

    private void luuDichVuTamTheoLichHen(String maLH) {
        if (maLH == null || maLH.isEmpty()) {
            return;
        }
        dichVuTheoLichHen.put(maLH, new LinkedHashMap<>(dichVuDaChon));
        soLuongTheoLichHen.put(maLH, new LinkedHashMap<>(soLuongDaChon));
    }

    private void moDialogThemDichVu() {
        if (!kiemTraDuLieuThanhToan()) {
            return;
        }

        ChonDichVuDialog dialog = new ChonDichVuDialog();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        DichVu dvDuocChon = dialog.getDichVuDuocChon();

        if (dvDuocChon == null) {
            return;
        }

        String maDV = dvDuocChon.getMaDV().trim();
        int soLuong = dialog.getSoLuongDuocChon();

        if (dichVuDaChon.containsKey(maDV)) {
            int soLuongCu = soLuongDaChon.getOrDefault(maDV, 0);
            soLuongDaChon.put(maDV, soLuongCu + soLuong);
        } else {
            dichVuDaChon.put(maDV, dvDuocChon);
            soLuongDaChon.put(maDV, soLuong);
        }

        luuDichVuTamTheoLichHen(maLichHenDangChon);
        capNhatBangDichVu();
    }


    private void capNhatBangDichVu() {
        modelDichVu.setRowCount(0);

        List<ChiTietHoaDon> danhSachChiTiet = new ArrayList<>();
        int stt = 1;

        for (Map.Entry<String, DichVu> entry : dichVuDaChon.entrySet()) {
            DichVu dv = entry.getValue();
            int soLuong = soLuongDaChon.getOrDefault(entry.getKey(), 0);
            double donGia = dv.getDonGia();

            if (soLuong <= 0) {
                continue;
            }

            ChiTietHoaDon chiTiet = new ChiTietHoaDon(null, dv, soLuong, donGia);
            danhSachChiTiet.add(chiTiet);

            double thanhTien = chiTiet.tinhThanhTien();

            modelDichVu.addRow(new Object[]{stt, dv.getTenDV(), soLuong, String.format("%,.0f VNĐ", donGia), String.format("%,.0f VNĐ", thanhTien)
            });

            stt++;
        }

        HoaDon hoaDonTam = new HoaDon();
        double tongTien = hoaDonTam.tinhTongTien(danhSachChiTiet);

        if (khuyenMaiDangApDung != null) {
            if (tongTien < khuyenMaiDangApDung.getGiaTriToiThieu()) {
                khuyenMaiDangApDung = null;
                tienGiamGia = 0;
                txtMaGiamGia.setText("");
                JOptionPane.showMessageDialog(this, "Hóa đơn không còn đủ điều kiện áp dụng mã khuyến mãi trước đó.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                hoaDonTam.setChietKhau(khuyenMaiDangApDung.getChietKhau());
                tienGiamGia = hoaDonTam.tinhTienGiamGia(tongTien);
            }
        } else {
            hoaDonTam.setChietKhau(0);
            tienGiamGia = 0;
        }

        double thue = hoaDonTam.tinhTienVAT(tongTien);
        double canThanhToan = hoaDonTam.tinhTongThanhToan(tongTien);

        txtTongTien.setText(String.format("%,.0f VNĐ", tongTien));
        txtChietKhau.setText(String.format("%,.0f VNĐ", tienGiamGia));
        txtThue.setText(String.format("%,.0f VNĐ", thue));
        txtCanThanhToan.setText(String.format("%,.0f VNĐ", canThanhToan));
    }

    private void taiDanhSachLichHenTuCSDL() {
        modelLichHen.setRowCount(0);

        List<LichHen> danhSach = hoaDonDao.getDanhSachLichHenDaCheckIn();

        for (LichHen lh : danhSach) {
            if (lh == null) {
                continue;
            }

            String tenBenhNhan = "";
            String soDT = "";

            if (lh.getMaBenhNhan() != null) {
                tenBenhNhan = lh.getMaBenhNhan().getHoTen() == null ? "" : lh.getMaBenhNhan().getHoTen();
                soDT = lh.getMaBenhNhan().getSoDT() == null ? "" : lh.getMaBenhNhan().getSoDT();
            }
            String gioHen = "";
            if (lh.getGioHen() != null) {
                gioHen = lh.getGioHen().format(DateTimeFormatter.ofPattern("HH:mm"));
            }

            modelLichHen.addRow(new Object[]{
                    lh.getMaLichHen(),
                    tenBenhNhan,
                    soDT,
                    lh.getNgayHen() == null ? "" : lh.getNgayHen(),
                    gioHen,
                    lh.getTrangThai()
            });
        }
    }
    private void taiThongTinChiTietLichHenTuCSDL(String maLH) {
        try {
            LichHen lh = lichHenDAO.getThongTinLichHen(maLH);

            if (lh == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin lịch hẹn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (lh.getMaBenhNhan() != null) {
                txtTenKhachHang.setText(lh.getMaBenhNhan().getHoTen() == null ? "" : lh.getMaBenhNhan().getHoTen());
                txtSoDienThoai.setText(lh.getMaBenhNhan().getSoDT() == null ? "" : lh.getMaBenhNhan().getSoDT());
            } else {
                txtTenKhachHang.setText("");
                txtSoDienThoai.setText("");
            }

            txtNgayHen.setText(lh.getNgayHen() == null ? "" : lh.getNgayHen().toString());
            txtGioHen.setText(lh.getGioHen() == null ? "" : lh.getGioHen().format(DateTimeFormatter.ofPattern("HH:mm")));
            txtGhiChu.setText(lh.getGhiChu() == null ? "" : lh.getGhiChu());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải thông tin lịch hẹn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    private double tinhTongTienDichVu() {
        List<ChiTietHoaDon> danhSachChiTiet = new ArrayList<>();

        for (Map.Entry<String, DichVu> entry : dichVuDaChon.entrySet()) {
            DichVu dv = entry.getValue();
            int soLuong = soLuongDaChon.getOrDefault(entry.getKey(), 0);

            if (soLuong > 0) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon(null, dv, soLuong, dv.getDonGia());
                danhSachChiTiet.add(chiTiet);
            }
        }

        HoaDon hoaDonTam = new HoaDon();
        return hoaDonTam.tinhTongTien(danhSachChiTiet);
    }

    private void xuLyApDungMaGiamGia() {
        String maNhap = txtMaGiamGia.getText().trim();

        if (maLichHenDangChon == null || maLichHenDangChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch hẹn trước!", "Thông báo", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            KhuyenMai km = khuyenMaiDAO.getByMaApDung(maNhap);
            if (km == null) {
                JOptionPane.showMessageDialog(this, "Mã khuyến mãi không tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate homNay = LocalDate.now();

            if (!"Đang áp dụng".equalsIgnoreCase(km.getTrangThai())) {
                JOptionPane.showMessageDialog(this, "Khuyến mãi hiện không ở trạng thái áp dụng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (km.getNgayBatDau() == null || km.getNgayKetThuc() == null || homNay.isBefore(km.getNgayBatDau()) || homNay.isAfter(km.getNgayKetThuc())) {
                JOptionPane.showMessageDialog(this, "Khuyến mãi chưa bắt đầu hoặc đã hết hạn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (km.getSoLuong() <= 0) {
                JOptionPane.showMessageDialog(this, "Khuyến mãi đã hết lượt sử dụng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double tongTien = tinhTongTienDichVu();
            if (tongTien < km.getGiaTriToiThieu()) {
                JOptionPane.showMessageDialog(this, String.format("Hóa đơn chưa đạt giá trị tối thiểu %.0f VNĐ.", km.getGiaTriToiThieu()), "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            khuyenMaiDangApDung = km;

            HoaDon hoaDonTam = new HoaDon();
            hoaDonTam.setChietKhau(khuyenMaiDangApDung.getChietKhau());
            tienGiamGia = hoaDonTam.tinhTienGiamGia(tongTien);

            capNhatBangDichVu();

            JOptionPane.showMessageDialog(
                    this,
                    "Áp dụng khuyến mãi thành công!\n"
                            + "Tên khuyến mãi: " + km.getTenKM() + "\n"
                            + "Mức giảm: " + km.getChietKhau() + "%\n"
                            + "Số tiền giảm: "
                            + String.format("%,.0f VNĐ", tienGiamGia),
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra khuyến mãi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyLuuHoaDonVaoCSDL() {
        if (!kiemTraDuLieuThanhToan()) {
            return;
        }
        if (dichVuDaChon.isEmpty() || soLuongDaChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một dịch vụ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (phuongThucThanhToan == null || phuongThucThanhToan.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phương thức thanh toán!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (tinhTongTienDichVu() <= 0) {
            JOptionPane.showMessageDialog(this, "Tổng tiền dịch vụ phải lớn hơn 0!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Map<String, Integer> dsDichVuLuu = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : soLuongDaChon.entrySet()) {
            if (entry.getValue() == null || entry.getValue() <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng dịch vụ phải lớn hơn 0!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            dsDichVuLuu.put(entry.getKey(), entry.getValue());
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán và lưu hóa đơn vào cơ sở dữ liệu?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            double chietKhauPhanTram = 0;

            if (khuyenMaiDangApDung != null) {
                chietKhauPhanTram = khuyenMaiDangApDung.getChietKhau();
            }

            String maKM = khuyenMaiDangApDung == null ? null : khuyenMaiDangApDung.getMaKM();
            String maNV = PhienDangNhap.getMaNV();

            if (maNV == null || maNV.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Không xác định được mã nhân viên đang đăng nhập. Vui lòng đăng nhập lại.",
                        "Lỗi phiên đăng nhập",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            String maHD = hoaDonDao.luuHoaDonVaHoanThanhLichHen(maLichHenDangChon, maNV, maKM, chietKhauPhanTram, new HoaDon().getThueVAT(), dsDichVuLuu);

            HoaDon hoaDonVuaLuu = hoaDonDao.getByMaHD(maHD);

            JOptionPane.showMessageDialog(this, "Lập hóa đơn thành công!\nMã hóa đơn: " + maHD, "Thành công", JOptionPane.INFORMATION_MESSAGE);

            if (hoaDonVuaLuu != null) {
                HoaDonInPanel.previewHoaDon(hoaDonVuaLuu);
            } else {
                JOptionPane.showMessageDialog(this, "Đã lưu hóa đơn nhưng không lấy được thông tin để xem trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }

            lamMoiForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn vào CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void moDialogTienMat() {
        if (!kiemTraDuLieuThanhToan()) {
            return;
        }

        double tongTien = tinhTongTienDichVu();

        if (tongTien <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một dịch vụ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        HoaDon hoaDonTam = new HoaDon();
        hoaDonTam.setChietKhau(khuyenMaiDangApDung == null ? 0 : khuyenMaiDangApDung.getChietKhau());

        double chietKhau = hoaDonTam.tinhTienGiamGia(tongTien);
        double thue = hoaDonTam.tinhTienVAT(tongTien);

        ThanhToanTienMatDialog dialog =
                new ThanhToanTienMatDialog(SwingUtilities.getWindowAncestor(this), tongTien, chietKhau, thue);

        dialog.setVisible(true);

        if (dialog.isDaXacNhan()) {
            phuongThucThanhToan = "Tiền mặt";
            soTienKhachDua = dialog.getSoTienKhachDua();
            tienThua = dialog.getTienThua();

            JOptionPane.showMessageDialog(this, "Đã xác nhận thông tin thu tiền mặt.\n" + "Cần thanh toán: " + String.format("%,d VNĐ", dialog.getCanThanhToan())
                    + "\nTiền khách đưa: "
                    + String.format("%,d VNĐ", soTienKhachDua)
                    + "\nTiền thừa: "
                    + String.format("%,d VNĐ", tienThua)
            );
        } else {
            phuongThucThanhToan = "";
            soTienKhachDua = 0;
            tienThua = 0;
        }
    }

    private void moDialogChuyenKhoan() {
        if (!kiemTraDuLieuThanhToan()) {
            return;
        }

        double tongTien = tinhTongTienDichVu();

        if (tongTien <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một dịch vụ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        HoaDon hoaDonTam = new HoaDon();
        hoaDonTam.setChietKhau(khuyenMaiDangApDung == null ? 0 : khuyenMaiDangApDung.getChietKhau());

        double chietKhau = hoaDonTam.tinhTienGiamGia(tongTien);
        double thue = hoaDonTam.tinhTienVAT(tongTien);
        double canThanhToan = hoaDonTam.tinhTongThanhToan(tongTien);

        int xacNhan = JOptionPane.showConfirmDialog(this,
                "Tổng tiền dịch vụ: "
                        + String.format("%,.0f VNĐ", tongTien)
                        + "\nChiết khấu: "
                        + String.format("%,.0f VNĐ", chietKhau)
                        + "\nThuế VAT: "
                        + String.format("%,.0f VNĐ", thue)
                        + "\nCần thanh toán: "
                        + String.format("%,.0f VNĐ", canThanhToan)
                        + "\n\nMở mã QR thanh toán?",
                "Xác nhận chuyển khoản",
                JOptionPane.YES_NO_OPTION
        );

        if (xacNhan != JOptionPane.YES_OPTION) {
            return;
        }
        phuongThucThanhToan = "Chuyển khoản";
        soTienKhachDua = 0;
        tienThua = 0;
        String maThamChieu = "Thanh Toan" + maLichHenDangChon;
        QRThanhToan.hienThiQRPanel(this, canThanhToan, maThamChieu,
                () -> JOptionPane.showMessageDialog(this, "Hóa đơn đã thanh toán."));
    }

    private boolean kiemTraDuLieuThanhToan() {
        if (maLichHenDangChon == null || maLichHenDangChon.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch hẹn trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    private void lamMoiForm() {
        if (maLichHenDangChon != null) {
            dichVuTheoLichHen.remove(maLichHenDangChon);
            soLuongTheoLichHen.remove(maLichHenDangChon);
        }

        dichVuDaChon.clear();
        soLuongDaChon.clear();

        khuyenMaiDangApDung = null;
        tienGiamGia = 0;
        maLichHenDangChon = null;
        phuongThucThanhToan = "";
        soTienKhachDua = 0;
        tienThua = 0;

        txtMaGiamGia.setText("");
        txtTenKhachHang.setText("");
        txtSoDienThoai.setText("");
        txtNgayHen.setText("");
        txtGioHen.setText("");
        txtGhiChu.setText("");
        modelDichVu.setRowCount(0);
        txtTongTien.setText("0 VNĐ");
        txtChietKhau.setText("0 VNĐ");
        txtThue.setText("0 VNĐ");
        txtCanThanhToan.setText("0 VNĐ");
        taiDanhSachLichHenTuCSDL();
    }
}