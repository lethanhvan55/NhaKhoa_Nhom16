
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import dao.ThongKeKhachHang_Dao;

public class ThongKeKhachHangPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final Color MAU_CHU = new Color(35, 65, 95);
    private static final Color MAU_XANH = new Color(15, 150, 137);
    private static final Color MAU_NEN = new Color(243, 247, 251);
    private static final Color MAU_XANH_DUONG = new Color(47, 105, 180);

    private JDateChooser dcTuNgay;
    private JDateChooser dcDenNgay;

    private JButton btnThongKe;
    private JButton btnXuatBaoCao;

    private JTable tblKhachHang;
    private DefaultTableModel modelKhachHang;

    private BieuDoDuongPanel pnlBieuDoDuong;
    private BieuDoCotPanel pnlBieuDoCot;

    private final ThongKeKhachHang_Dao thongKeDao =
            new ThongKeKhachHang_Dao();

    public ThongKeKhachHangPanel() {

        setLayout(new BorderLayout(15, 15));
        setBackground(MAU_NEN);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(taoTieuDe(), BorderLayout.NORTH);
        add(taoNoiDung(), BorderLayout.CENTER);

        // Tải dữ liệu sau khi giao diện đã được khởi tạo
        capNhatDuLieu();
    }

    // ================= TIÊU ĐỀ =================

    private JPanel taoTieuDe() {

        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setOpaque(false);

        JLabel lblMoTa = new JLabel("Tổng quan khách hàng");
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 15));
        lblMoTa.setForeground(MAU_CHU);

        pnlTieuDe.add(lblMoTa, BorderLayout.WEST);
        pnlTieuDe.add(taoBoLoc(), BorderLayout.EAST);

        return pnlTieuDe;
    }

    // ================= BỘ LỌC =================

    private JPanel taoBoLoc() {

        JPanel pnlBoLoc = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 5));

        pnlBoLoc.setOpaque(false);

        dcTuNgay = new JDateChooser();
        dcTuNgay.setDateFormatString("dd/MM/yyyy");
        dcTuNgay.setDate(layNgayDauThang());
        dcTuNgay.setPreferredSize(new Dimension(125, 34));

        dcDenNgay = new JDateChooser();
        dcDenNgay.setDateFormatString("dd/MM/yyyy");
        dcDenNgay.setDate(new Date());
        dcDenNgay.setPreferredSize(new Dimension(125, 34));

        btnThongKe = taoNut("Thống kê", MAU_XANH);
        btnXuatBaoCao = taoNut("Xuất báo cáo", MAU_XANH_DUONG);

        btnThongKe.addActionListener(e -> xuLyThongKe());

        btnXuatBaoCao.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Chức năng xuất báo cáo chưa được xử lý.",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE));

        pnlBoLoc.add(new JLabel("Từ ngày:"));
        pnlBoLoc.add(dcTuNgay);

        pnlBoLoc.add(new JLabel("Đến ngày:"));
        pnlBoLoc.add(dcDenNgay);

        pnlBoLoc.add(btnThongKe);
        pnlBoLoc.add(btnXuatBaoCao);

        return pnlBoLoc;
    }

    private Date layNgayDauThang() {

        Calendar lich = Calendar.getInstance();

        lich.set(Calendar.DAY_OF_MONTH, 1);
        lich.set(Calendar.HOUR_OF_DAY, 0);
        lich.set(Calendar.MINUTE, 0);
        lich.set(Calendar.SECOND, 0);
        lich.set(Calendar.MILLISECOND, 0);

        return lich.getTime();
    }

    private LocalDate chuyenSangLocalDate(Date ngay) {

        if (ngay == null) {
            return null;
        }

        return ngay.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // ================= XỬ LÝ THỐNG KÊ =================

    private void xuLyThongKe() {

        Date tuNgay = dcTuNgay.getDate();
        Date denNgay = dcDenNgay.getDate();

        if (tuNgay == null || denNgay == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        LocalDate ngayBatDau = chuyenSangLocalDate(tuNgay);
        LocalDate ngayKetThuc = chuyenSangLocalDate(denNgay);

        if (ngayBatDau.isAfter(ngayKetThuc)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Từ ngày không được lớn hơn đến ngày!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        capNhatDuLieu();
    }

    // ================= CẬP NHẬT TOÀN BỘ DỮ LIỆU =================

    private void capNhatDuLieu() {

        loadDuLieuBang();
        loadDuLieuBieuDoCot();
        loadDuLieuBieuDoDuong();
    }

    // ================= LẤY SỐ LƯỢNG KHÁCH HÀNG =================

    /*
     * Sử dụng hàm DAO hiện có để đếm số khách hàng
     * trong khoảng thời gian được chọn.
     */
    private int demSoLuongKhachHang(
            LocalDate tuNgay,
            LocalDate denNgay) throws Exception {

        List<Map<String, Object>> danhSach =
                thongKeDao.getThongKeKhachHang(tuNgay, denNgay);

        return danhSach == null ? 0 : danhSach.size();
    }

    // ================= DỮ LIỆU BẢNG =================

    public void loadDuLieuBang() {

        if (modelKhachHang == null) {
            return;
        }

        modelKhachHang.setRowCount(0);

        LocalDate tuNgay =
                chuyenSangLocalDate(dcTuNgay.getDate());

        LocalDate denNgay =
                chuyenSangLocalDate(dcDenNgay.getDate());

        if (tuNgay == null || denNgay == null) {
            return;
        }

        try {

            List<Map<String, Object>> danhSach =
                    thongKeDao.getThongKeKhachHang(tuNgay, denNgay);

            if (danhSach == null) {
                return;
            }

            DecimalFormat df = new DecimalFormat("#,##0");
            int stt = 1;

            for (Map<String, Object> row : danhSach) {

                String maKH = String.valueOf(
                        row.get("MaKhachHang"));

                String tenKH = String.valueOf(
                        row.get("TenKhachHang"));

                Object soLanObj = row.get("SoLanKham");
                Object tongChiTieuObj = row.get("TongChiTieu");

                int soLanKham = soLanObj instanceof Number
                        ? ((Number) soLanObj).intValue() : 0;

                double tongChiTieu = tongChiTieuObj instanceof Number
                        ? ((Number) tongChiTieuObj).doubleValue() : 0;

                modelKhachHang.addRow(new Object[] {
                        stt++,
                        maKH,
                        tenKH,
                        soLanKham,
                        df.format(tongChiTieu) + " VNĐ"
                });
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải dữ liệu thống kê khách hàng:\n"
                            + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================= BIỂU ĐỒ CỘT THEO THÁNG =================

    private void loadDuLieuBieuDoCot() {

        if (pnlBieuDoCot == null) {
            return;
        }

        Map<String, Integer> duLieu = new LinkedHashMap<>();

        LocalDate tuNgay = chuyenSangLocalDate(dcTuNgay.getDate());
        LocalDate denNgay = chuyenSangLocalDate(dcDenNgay.getDate());

        if (tuNgay == null || denNgay == null
                || tuNgay.isAfter(denNgay)) {

            pnlBieuDoCot.setDuLieu(duLieu);
            return;
        }

        try {

            YearMonth thangBatDau = YearMonth.from(tuNgay);
            YearMonth thangKetThuc = YearMonth.from(denNgay);

            YearMonth thangHienTai = thangBatDau;

            while (!thangHienTai.isAfter(thangKetThuc)) {

                LocalDate ngayDau = thangHienTai.atDay(1);
                LocalDate ngayCuoi = thangHienTai.atEndOfMonth();

                if (ngayDau.isBefore(tuNgay)) {
                    ngayDau = tuNgay;
                }

                if (ngayCuoi.isAfter(denNgay)) {
                    ngayCuoi = denNgay;
                }

                int soLuong = demSoLuongKhachHang(
                        ngayDau, ngayCuoi);

                String nhan = thangHienTai.getMonthValue() + "/"
                        + thangHienTai.getYear();

                duLieu.put(nhan, soLuong);

                thangHienTai = thangHienTai.plusMonths(1);
            }

            pnlBieuDoCot.setDuLieu(duLieu);

        } catch (Exception e) {

            e.printStackTrace();

            pnlBieuDoCot.setDuLieu(duLieu);
        }
    }

    // ================= BIỂU ĐỒ ĐƯỜNG THEO NĂM =================

    private void loadDuLieuBieuDoDuong() {

        if (pnlBieuDoDuong == null) {
            return;
        }

        Map<String, Integer> duLieuNam = new LinkedHashMap<>();

        LocalDate tuNgay = chuyenSangLocalDate(dcTuNgay.getDate());
        LocalDate denNgay = chuyenSangLocalDate(dcDenNgay.getDate());

        if (tuNgay == null || denNgay == null
                || tuNgay.isAfter(denNgay)) {

            pnlBieuDoDuong.setDuLieu(duLieuNam);
            return;
        }

        try {

            int namBatDau = tuNgay.getYear();
            int namKetThuc = denNgay.getYear();

            for (int nam = namBatDau; nam <= namKetThuc; nam++) {

                LocalDate ngayDau = LocalDate.of(nam, 1, 1);
                LocalDate ngayCuoi = LocalDate.of(nam, 12, 31);

                if (ngayDau.isBefore(tuNgay)) {
                    ngayDau = tuNgay;
                }

                if (ngayCuoi.isAfter(denNgay)) {
                    ngayCuoi = denNgay;
                }

                int soLuong = demSoLuongKhachHang(
                        ngayDau, ngayCuoi);

                duLieuNam.put(String.valueOf(nam), soLuong);
            }

            pnlBieuDoDuong.setDuLieu(duLieuNam);

        } catch (Exception e) {

            e.printStackTrace();

            pnlBieuDoDuong.setDuLieu(duLieuNam);
        }
    }

    // ================= NỘI DUNG GIAO DIỆN =================

    private JPanel taoNoiDung() {

        JPanel pnlNoiDung = new JPanel(
                new BorderLayout(15, 15));

        pnlNoiDung.setOpaque(false);

        JPanel pnlBieuDo = new JPanel(
                new java.awt.GridLayout(1, 2, 15, 0));

        pnlBieuDo.setOpaque(false);
        pnlBieuDo.setPreferredSize(new Dimension(0, 360));

        pnlBieuDoCot = new BieuDoCotPanel();
        pnlBieuDoDuong = new BieuDoDuongPanel();

        pnlBieuDo.add(taoKhung(
                "SỐ LƯỢNG KHÁCH HÀNG THEO THÁNG",
                pnlBieuDoCot));

        pnlBieuDo.add(taoKhung(
                "XU HƯỚNG KHÁCH HÀNG THEO NĂM",
                pnlBieuDoDuong));

        JPanel pnlDuoi = taoBangKhachHang();

        pnlNoiDung.add(pnlBieuDo, BorderLayout.NORTH);
        pnlNoiDung.add(pnlDuoi, BorderLayout.CENTER);

        return pnlNoiDung;
    }

    // ================= BẢNG KHÁCH HÀNG =================

    private JPanel taoBangKhachHang() {

        JPanel pnlBang = taoKhung(
                "CHI TIẾT THỐNG KÊ KHÁCH HÀNG", null);

        String[] cot = {
                "STT",
                "Mã khách hàng",
                "Tên khách hàng",
                "Số lần khám",
                "Tổng chi tiêu"
        };

        modelKhachHang = new DefaultTableModel(null, cot) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblKhachHang = new JTable(modelKhachHang);

        tblKhachHang.setRowHeight(32);
        tblKhachHang.setFont(new Font("Arial", Font.PLAIN, 13));
        tblKhachHang.setForeground(Color.BLACK);
        tblKhachHang.setBackground(Color.WHITE);

        tblKhachHang.setSelectionBackground(
                new Color(204, 245, 238));

        tblKhachHang.setSelectionForeground(MAU_CHU);
        tblKhachHang.setGridColor(new Color(230, 235, 242));
        tblKhachHang.setShowVerticalLines(false);
        tblKhachHang.setFillsViewportHeight(true);
        tblKhachHang.setAutoCreateRowSorter(true);

        tblKhachHang.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13));

        tblKhachHang.getTableHeader().setBackground(MAU_CHU);
        tblKhachHang.getTableHeader().setForeground(Color.WHITE);

        tblKhachHang.getTableHeader().setPreferredSize(
                new Dimension(0, 36));

        tblKhachHang.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrKhachHang = new JScrollPane(tblKhachHang);

        scrKhachHang.setBorder(BorderFactory.createEmptyBorder());
        scrKhachHang.getViewport().setBackground(Color.WHITE);

        pnlBang.add(scrKhachHang, BorderLayout.CENTER);

        return pnlBang;
    }

    // ================= KHUNG GIAO DIỆN =================

    private JPanel taoKhung(
            String tieuDe,
            JPanel contentPanel) {

        JPanel pnlKhung = new JPanel(
                new BorderLayout(10, 12));

        pnlKhung.setBackground(Color.WHITE);

        pnlKhung.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(224, 232, 241)),
                        new EmptyBorder(15, 15, 15, 15)));

        JLabel lblTieuDe = new JLabel(tieuDe);

        lblTieuDe.setFont(
                new Font("Arial", Font.BOLD, 14));

        lblTieuDe.setForeground(MAU_CHU);

        pnlKhung.add(lblTieuDe, BorderLayout.NORTH);

        if (contentPanel != null) {
            pnlKhung.add(contentPanel, BorderLayout.CENTER);
        }

        return pnlKhung;
    }

    // ================= NÚT BẤM =================

    private JButton taoNut(String ten, Color mau) {

        JButton btn = new JButton(ten);

        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mau);

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);

        btn.setCursor(new java.awt.Cursor(
                java.awt.Cursor.HAND_CURSOR));

        btn.setPreferredSize(new Dimension(120, 34));

        return btn;
    }

    // ================= BIỂU ĐỒ CỘT =================

    private class BieuDoCotPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        private Map<String, Integer> mapDuLieu =
                new LinkedHashMap<>();

        public BieuDoCotPanel() {

            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(400, 260));
        }

        public void setDuLieu(Map<String, Integer> duLieuMoi) {

            mapDuLieu = new LinkedHashMap<>(duLieuMoi);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int trai = 42;
            int tren = 35;
            int duoi = 45;

            int rong = getWidth() - trai - 15;
            int cao = getHeight() - tren - duoi;

            if (rong <= 0 || cao <= 0) {
                g2.dispose();
                return;
            }

            if (mapDuLieu.isEmpty()) {

                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Arial", Font.PLAIN, 12));
                g2.drawString(
                        "Không có dữ liệu",
                        trai + rong / 2 - 45,
                        tren + cao / 2);

                g2.dispose();
                return;
            }

            int giaTriMax = 1;

            for (int value : mapDuLieu.values()) {
                giaTriMax = Math.max(giaTriMax, value);
            }

            giaTriMax = ((giaTriMax / 10) + 1) * 10;

            g2.setFont(new Font("Arial", Font.PLAIN, 10));

            for (int i = 0; i <= 5; i++) {

                int y = tren + cao * i / 5;
                int nhan = giaTriMax - i * giaTriMax / 5;

                g2.setColor(new Color(232, 237, 243));
                g2.drawLine(trai, y, trai + rong, y);

                g2.setColor(new Color(110, 125, 140));
                g2.drawString(String.valueOf(nhan), 5, y + 4);
            }

            int soCot = mapDuLieu.size();
            int buoc = rong / Math.max(soCot, 1);
            int rongCot = Math.max(8, Math.min(35, buoc / 2));

            int index = 0;

            for (Map.Entry<String, Integer> entry :
                    mapDuLieu.entrySet()) {

                int value = entry.getValue();

                int x = trai + index * buoc
                        + (buoc - rongCot) / 2;

                int chieuCao = (int) (
                        (double) value / giaTriMax * cao);

                int y = tren + cao - chieuCao;

                g2.setColor(MAU_XANH);
                g2.fillRoundRect(
                        x, y, rongCot,
                        Math.max(chieuCao, 2),
                        8, 8);

                g2.setColor(new Color(90, 110, 130));

                String nhan = entry.getKey();

                int rongNhan =
                        g2.getFontMetrics().stringWidth(nhan);

                g2.drawString(
                        nhan,
                        x + rongCot / 2 - rongNhan / 2,
                        tren + cao + 17);

                if (soCot <= 12) {

                    String giaTri = String.valueOf(value);

                    int rongGiaTri =
                            g2.getFontMetrics().stringWidth(giaTri);

                    g2.setColor(MAU_CHU);

                    g2.drawString(
                            giaTri,
                            x + rongCot / 2 - rongGiaTri / 2,
                            y - 5);
                }

                index++;
            }

            g2.setColor(MAU_CHU);
            g2.drawString("Số lượng khách hàng", trai, 15);

            g2.dispose();
        }
    }

    // ================= BIỂU ĐỒ ĐƯỜNG =================

    private class BieuDoDuongPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        private Map<String, Integer> mapDuLieu =
                new LinkedHashMap<>();

        public BieuDoDuongPanel() {

            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(400, 260));
        }

        public void setDuLieu(Map<String, Integer> duLieuMoi) {

            mapDuLieu = new LinkedHashMap<>(duLieuMoi);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int trai = 42;
            int phai = 18;
            int tren = 35;
            int duoi = 38;

            int rong = getWidth() - trai - phai;
            int cao = getHeight() - tren - duoi;

            if (rong <= 0 || cao <= 0) {
                g2.dispose();
                return;
            }

            if (mapDuLieu.isEmpty()) {

                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Arial", Font.PLAIN, 12));

                g2.drawString(
                        "Không có dữ liệu",
                        trai + rong / 2 - 45,
                        tren + cao / 2);

                g2.dispose();
                return;
            }

            int giaTriMax = 1;

            for (int value : mapDuLieu.values()) {
                giaTriMax = Math.max(giaTriMax, value);
            }

            giaTriMax = ((giaTriMax / 100) + 1) * 100;

            int soMoc = 5;

            g2.setFont(new Font("Arial", Font.PLAIN, 10));

            // Vẽ lưới ngang và trục tung
            for (int i = 0; i <= soMoc; i++) {

                int y = tren + cao * i / soMoc;

                int nhan = giaTriMax
                        - i * giaTriMax / soMoc;

                g2.setColor(new Color(232, 237, 243));

                g2.drawLine(
                        trai, y,
                        trai + rong, y);

                g2.setColor(new Color(110, 125, 140));

                g2.drawString(
                        String.valueOf(nhan),
                        3, y + 4);
            }

            g2.setColor(new Color(110, 125, 140));

            g2.drawString(
                    "Số lượng khách hàng",
                    trai, 15);

            int n = mapDuLieu.size();

            int[] xPoints = new int[n];
            int[] yPoints = new int[n];

            int index = 0;

            // Tính tọa độ các điểm
            for (Map.Entry<String, Integer> entry :
                    mapDuLieu.entrySet()) {

                int x = n > 1
                        ? trai + index * rong / (n - 1)
                        : trai + rong / 2;

                int value = entry.getValue();

                int y = tren + cao
                        - (int) ((double) value / giaTriMax * cao);

                xPoints[index] = x;
                yPoints[index] = y;

                String nam = entry.getKey();

                int rongNhan =
                        g2.getFontMetrics().stringWidth(nam);

                g2.setColor(new Color(90, 110, 130));

                g2.drawString(
                        nam,
                        x - rongNhan / 2,
                        tren + cao + 20);

                index++;
            }

            // Vẽ đường nối
            g2.setColor(MAU_XANH_DUONG);
            g2.setStroke(new BasicStroke(2.5f));

            for (int i = 0; i < n - 1; i++) {

                g2.drawLine(
                        xPoints[i],
                        yPoints[i],
                        xPoints[i + 1],
                        yPoints[i + 1]);
            }

            // Vẽ điểm và số liệu
            for (int i = 0; i < n; i++) {

                g2.setColor(Color.WHITE);

                g2.fillOval(
                        xPoints[i] - 4,
                        yPoints[i] - 4,
                        8, 8);

                g2.setColor(MAU_XANH_DUONG);

                g2.drawOval(
                        xPoints[i] - 4,
                        yPoints[i] - 4,
                        8, 8);

                int value = (int) mapDuLieu.values()
                        .toArray()[i];

                String valStr = String.valueOf(value);

                int valWidth =
                        g2.getFontMetrics().stringWidth(valStr);

                g2.drawString(
                        valStr,
                        xPoints[i] - valWidth / 2,
                        yPoints[i] - 8);
            }

            g2.dispose();
        }
    }
}
