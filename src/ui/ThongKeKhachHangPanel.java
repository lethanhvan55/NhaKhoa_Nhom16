
        package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

public class ThongKeKhachHangPanel extends JPanel {

    private static final Color MAU_CHU =
            new Color(35, 65, 95);

    private static final Color MAU_XANH =
            new Color(15, 150, 137);

    private static final Color MAU_NEN =
            new Color(243, 247, 251);

    private static final Color MAU_XANH_DUONG =
            new Color(47, 105, 180);

    private static final Color MAU_CAM =
            new Color(230, 150, 55);

    private static final Color MAU_TIM =
            new Color(120, 90, 180);

    private JDateChooser dcTuNgay;
    private JDateChooser dcDenNgay;

    private JButton btnThongKe;
    private JButton btnXuatBaoCao;

    private JTable tblKhachHang;

    public ThongKeKhachHangPanel() {

        setLayout(new BorderLayout(15, 15));
        setBackground(MAU_NEN);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(taoTieuDe(), BorderLayout.NORTH);
        add(taoNoiDung(), BorderLayout.CENTER);
    }

    // =====================================================
    // 1. TIÊU ĐỀ
    // =====================================================

    private JPanel taoTieuDe() {

        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setOpaque(false);

        JLabel lblMoTa = new JLabel(
                "Tổng quan khách hàng");

        lblMoTa.setFont(
                new Font("Arial", Font.PLAIN, 15));

        lblMoTa.setForeground(MAU_CHU);

        pnlTieuDe.add(lblMoTa, BorderLayout.WEST);
        pnlTieuDe.add(taoBoLoc(), BorderLayout.EAST);

        return pnlTieuDe;
    }

    // =====================================================
    // 2. BỘ LỌC NGÀY THỐNG KÊ
    // =====================================================

    private JPanel taoBoLoc() {

        JPanel pnlBoLoc = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 5));

        pnlBoLoc.setOpaque(false);

        dcTuNgay = new JDateChooser();
        dcTuNgay.setDateFormatString("dd/MM/yyyy");
        dcTuNgay.setDate(layNgayDauThang());
        dcTuNgay.setPreferredSize(
                new Dimension(125, 34));

        dcDenNgay = new JDateChooser();
        dcDenNgay.setDateFormatString("dd/MM/yyyy");
        dcDenNgay.setDate(new Date());
        dcDenNgay.setPreferredSize(
                new Dimension(125, 34));

        btnThongKe = taoNut("Thống kê", MAU_XANH);
        btnXuatBaoCao = taoNut(
                "Xuất báo cáo", MAU_XANH_DUONG);

        btnThongKe.addActionListener(e -> xuLyThongKe());

        btnXuatBaoCao.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        ThongKeKhachHangPanel.this,
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

    // =====================================================
    // 3. XỬ LÝ NÚT THỐNG KÊ
    // =====================================================

    private void xuLyThongKe() {

        Date tuNgay = dcTuNgay.getDate();
        Date denNgay = dcDenNgay.getDate();

        if (tuNgay == null || denNgay == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn đầy đủ ngày bắt đầu "
                            + "và ngày kết thúc!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        if (tuNgay.after(denNgay)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Từ ngày không được lớn hơn đến ngày!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        SimpleDateFormat dinhDang =
                new SimpleDateFormat("dd/MM/yyyy");

        JOptionPane.showMessageDialog(
                this,
                "Khoảng thời gian thống kê khách hàng:\n"
                        + "Từ ngày: "
                        + dinhDang.format(tuNgay)
                        + "\nĐến ngày: "
                        + dinhDang.format(denNgay),
                "Thông tin thống kê",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // =====================================================
    // 4. NỘI DUNG CHÍNH
    // =====================================================

    private JPanel taoNoiDung() {

        JPanel pnlNoiDung = new JPanel(
                new BorderLayout(15, 15));

        pnlNoiDung.setOpaque(false);

        // Hai biểu đồ phía trên
        JPanel pnlBieuDo = new JPanel(
                new GridLayout(1, 2, 15, 0));

        pnlBieuDo.setOpaque(false);
        pnlBieuDo.setPreferredSize(
                new Dimension(0, 360));

        pnlBieuDo.add(taoBieuDoKhachHang());
        pnlBieuDo.add(taoBieuDoPhanLoai());

        // Bảng chi tiết phía dưới
        JPanel pnlDuoi = taoBangKhachHang();

        pnlNoiDung.add(
                pnlBieuDo, BorderLayout.NORTH);

        pnlNoiDung.add(
                pnlDuoi, BorderLayout.CENTER);

        return pnlNoiDung;
    }

    // =====================================================
    // 5. BIỂU ĐỒ CỘT SỐ LƯỢNG KHÁCH HÀNG
    // =====================================================

    private JPanel taoBieuDoKhachHang() {

        JPanel pnlBieuDo =
                taoKhung("SỐ LƯỢNG KHÁCH HÀNG THEO THÁNG");

        pnlBieuDo.add(
                new BieuDoCotPanel(),
                BorderLayout.CENTER);

        return pnlBieuDo;
    }

    // =====================================================
    // 6. BIỂU ĐỒ TRÒN PHÂN LOẠI KHÁCH HÀNG
    // =====================================================

    private JPanel taoBieuDoPhanLoai() {

        JPanel pnlBieuDo =
                taoKhung("TỶ LỆ PHÂN LOẠI KHÁCH HÀNG");

        pnlBieuDo.add(
                new BieuDoTronPanel(),
                BorderLayout.CENTER);

        return pnlBieuDo;
    }

    // =====================================================
    // 7. BẢNG CHI TIẾT THỐNG KÊ KHÁCH HÀNG
    // =====================================================

    private JPanel taoBangKhachHang() {

        JPanel pnlBang =
                taoKhung("CHI TIẾT THỐNG KÊ KHÁCH HÀNG");

        String[] cot = {
                "STT",
                "Mã khách hàng",
                "Tên khách hàng",
                "Số lần khám",
                "Tổng chi tiêu",
                "Phân loại"
        };

        Object[][] duLieu = {
                {1, "KH001", "Nguyễn Văn An",
                        12, "19.500.000 đ", "Thân thiết"},

                {2, "KH002", "Trần Thị Bình",
                        8, "12.000.000 đ", "Thân thiết"},

                {3, "KH003", "Lê Hoàng Nam",
                        5, "6.500.000 đ", "Thường"},

                {4, "KH004", "Phạm Thu Hà",
                        15, "28.000.000 đ", "VIP"},

                {5, "KH005", "Võ Minh Tuấn",
                        3, "3.200.000 đ", "Thường"}
        };

        DefaultTableModel model =
                new DefaultTableModel(duLieu, cot) {

                    @Override
                    public boolean isCellEditable(
                            int row, int column) {
                        return false;
                    }
                };

        tblKhachHang = new JTable(model);

        tblKhachHang.setRowHeight(32);

        tblKhachHang.setFont(
                new Font("Arial", Font.PLAIN, 13));

        tblKhachHang.setSelectionBackground(
                new Color(204, 245, 238));

        tblKhachHang.setSelectionForeground(MAU_CHU);

        tblKhachHang.setGridColor(
                new Color(230, 235, 242));

        tblKhachHang.setShowVerticalLines(false);

        tblKhachHang.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13));

        tblKhachHang.getTableHeader().setBackground(
                MAU_CHU);

        tblKhachHang.getTableHeader().setForeground(
                Color.WHITE);

        tblKhachHang.getTableHeader().setPreferredSize(
                new Dimension(0, 36));

        tblKhachHang.getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane scrKhachHang =
                new JScrollPane(tblKhachHang);

        scrKhachHang.setBorder(
                BorderFactory.createEmptyBorder());

        scrKhachHang.getViewport().setBackground(
                Color.WHITE);

        pnlBang.add(
                scrKhachHang, BorderLayout.CENTER);

        return pnlBang;
    }

    // =====================================================
    // 8. TẠO KHUNG GIAO DIỆN
    // =====================================================

    private JPanel taoKhung(String tieuDe) {

        JPanel pnlKhung = new JPanel(
                new BorderLayout(10, 12));

        pnlKhung.setBackground(Color.WHITE);

        pnlKhung.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(224, 232, 241)),
                        new EmptyBorder(15, 15, 15, 15)));

        JLabel lblTieuDe = new JLabel(tieuDe);

        lblTieuDe.setFont(
                new Font("Arial", Font.BOLD, 14));

        lblTieuDe.setForeground(MAU_CHU);

        pnlKhung.add(
                lblTieuDe, BorderLayout.NORTH);

        return pnlKhung;
    }

    // =====================================================
    // 9. TẠO NÚT
    // =====================================================

    private JButton taoNut(String ten, Color mau) {

        JButton btn = new JButton(ten);

        btn.setFont(
                new Font("Arial", Font.BOLD, 12));

        btn.setForeground(Color.WHITE);
        btn.setBackground(mau);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);

        btn.setCursor(new java.awt.Cursor(
                java.awt.Cursor.HAND_CURSOR));

        btn.setPreferredSize(
                new Dimension(120, 34));

        return btn;
    }

    // =====================================================
    // 10. BIỂU ĐỒ CỘT
    // =====================================================

    private class BieuDoCotPanel extends JPanel {

        private final int[] giaTri = {
                45, 55, 48, 72, 65, 85,
                78, 95, 82, 110, 100, 125
        };

        private final String[] thang = {
                "T1", "T2", "T3", "T4",
                "T5", "T6", "T7", "T8",
                "T9", "T10", "T11", "T12"
        };

        public BieuDoCotPanel() {

            setBackground(Color.WHITE);

            setPreferredSize(
                    new Dimension(400, 260));
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int trai = 42;
            int tren = 25;
            int duoi = 35;

            int rong = getWidth() - trai - 15;
            int cao = getHeight() - tren - duoi;

            if (rong <= 0 || cao <= 0) {
                g2.dispose();
                return;
            }

            g2.setFont(
                    new Font("Arial", Font.PLAIN, 10));

            // Các đường kẻ ngang
            for (int i = 0; i <= 5; i++) {

                int y = tren + cao * i / 5;

                g2.setColor(
                        new Color(232, 237, 243));

                g2.drawLine(
                        trai, y, trai + rong, y);

                g2.setColor(
                        new Color(110, 125, 140));

                g2.drawString(
                        String.valueOf(150 - i * 30),
                        5,
                        y + 4);
            }

            int buoc = rong / 12;
            int rongCot = Math.max(4, buoc - 8);

            // Vẽ các cột
            for (int i = 0; i < giaTri.length; i++) {

                int x = trai + i * buoc
                        + (buoc - rongCot) / 2;

                int h = cao * giaTri[i] / 150;
                int y = tren + cao - h;

                g2.setColor(MAU_XANH);

                g2.fillRoundRect(
                        x, y, rongCot, h, 7, 7);

                g2.setColor(
                        new Color(90, 110, 130));

                g2.drawString(
                        thang[i],
                        x,
                        tren + cao + 18);
            }

            g2.setColor(
                    new Color(110, 125, 140));

            g2.drawString(
                    "Số lượng khách hàng",
                    trai,
                    15);

            g2.dispose();
        }
    }

    // =====================================================
    // 11. BIỂU ĐỒ TRÒN
    // =====================================================

    private class BieuDoTronPanel extends JPanel {

        public BieuDoTronPanel() {

            setBackground(Color.WHITE);

            setPreferredSize(
                    new Dimension(400, 260));
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Dữ liệu minh họa
            int[] giaTri = {
                    45, 30, 15, 10
            };

            String[] ten = {
                    "Khách hàng thường",
                    "Khách hàng thân thiết",
                    "Khách hàng VIP",
                    "Khách hàng mới"
            };

            Color[] mau = {
                    MAU_XANH,
                    MAU_XANH_DUONG,
                    MAU_CAM,
                    MAU_TIM
            };

            int kichThuoc = Math.min(
                    190,
                    Math.min(
                            getHeight() - 20,
                            getWidth() / 2));

            if (kichThuoc <= 0) {
                g2.dispose();
                return;
            }

            int x = 15;

            int y = Math.max(
                    5, (getHeight() - kichThuoc) / 2);

            int gocBatDau = 90;

            // Vẽ biểu đồ tròn
            for (int i = 0; i < giaTri.length; i++) {

                int goc = giaTri[i] * 360 / 100;

                g2.setColor(mau[i]);

                g2.fillArc(
                        x,
                        y,
                        kichThuoc,
                        kichThuoc,
                        gocBatDau,
                        -goc);

                gocBatDau -= goc;
            }

            // Chú thích bên phải
            int xChu = x + kichThuoc + 15;

            int yChu = Math.max(
                    25, getHeight() / 2 - 45);

            g2.setFont(
                    new Font("Arial", Font.PLAIN, 11));

            for (int i = 0; i < ten.length; i++) {

                int yy = yChu + i * 28;

                g2.setColor(mau[i]);

                g2.fillRoundRect(
                        xChu, yy - 9, 12, 12, 3, 3);

                g2.setColor(MAU_CHU);

                g2.drawString(
                        ten[i] + " (" + giaTri[i] + "%)",
                        xChu + 19,
                        yy + 1);
            }

            g2.dispose();
        }
    }
}
