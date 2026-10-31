package ui;

import dao.DichVu_DAO;
import utils.GiaoDienStyle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ThongKeDichVuPanel extends JPanel implements ActionListener {
    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;

    private JComboBox<String> cboKieuThongKe;
    private JComboBox<String> cboTrangThai;
    private JComboBox<String> cboKieuBieuDo;
    private JSpinner spnTuNgay;
    private JSpinner spnDenNgay;
    private JButton btnBaoCao, btnReset, btnXuatExcel;
    private JLabel lblTongLuotDung, lblTongDoanhThu, lblDichVuHot, lblTyTrongCaoNhat;
    private JTable tblThongKe;
    private DefaultTableModel dtm;
    private JPanel pnlVungBieuDo;
    private final DichVu_DAO tk_dao = new DichVu_DAO();
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    private List<Map<String, Object>> danhSachHienTai = new ArrayList<>();

    public ThongKeDichVuPanel() {
        khoiTaoGiaoDien();
        dangKySuKien();
        taiDuLieuThongKeTuCSDL();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(10, 10));
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(taoThanhBoLoc(), BorderLayout.NORTH);

        JPanel pnlTrungTam = new JPanel(new BorderLayout(10, 10));
        pnlTrungTam.setBackground(MAU_NEN);
        pnlTrungTam.add(taoHangThongSoKPI(), BorderLayout.NORTH);

        JPanel pnlBang = taoKhuVucBangThongKe();
        JPanel pnlBieuDo = taoKhuVucBieuDo();
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlBang, pnlBieuDo);
        split.setResizeWeight(0.58);
        split.setDividerLocation(620);
        split.setContinuousLayout(true);
        split.setBorder(null);
        pnlTrungTam.add(split, BorderLayout.CENTER);
        add(pnlTrungTam, BorderLayout.CENTER);
    }

    private JSpinner taoDateSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
        spinner.setPreferredSize(new Dimension(112, 30));
        return spinner;
    }

    private JPanel taoThanhBoLoc() {
        // Dùng GridLayout(2, 1) để chia thành 2 hàng rõ ràng, tránh bị tràn màn hình
        JPanel pnl = new JPanel(new GridLayout(2, 1, 5, 6));
        pnl.setBackground(MAU_TRANG);
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                new EmptyBorder(8, 12, 8, 12)));
        JPanel pnlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlRow1.setBackground(MAU_TRANG);

        cboKieuThongKe = new JComboBox<>(new String[]{"Theo ngày", "Theo tháng", "Theo năm", "Tùy chọn khoảng ngày"});
        cboTrangThai = new JComboBox<>(new String[]{"Tất cả trạng thái", "Đang cung cấp", "Ngừng cung cấp"});
        spnTuNgay = taoDateSpinner();
        spnDenNgay = taoDateSpinner();

        pnlRow1.add(taoNhan("Thống kê:")); pnlRow1.add(cboKieuThongKe);
        pnlRow1.add(taoNhan("Từ ngày:")); pnlRow1.add(spnTuNgay);
        pnlRow1.add(taoNhan("Đến ngày:")); pnlRow1.add(spnDenNgay);
        pnlRow1.add(taoNhan("Trạng thái:")); pnlRow1.add(cboTrangThai);
        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlRow2.setBackground(MAU_TRANG);

        btnBaoCao = GiaoDienStyle.taoNut("Thống kê", MAU_NAVY, 100);
        btnReset = GiaoDienStyle.taoNut("Làm mới", new Color(108, 117, 125), 90);
        btnXuatExcel = GiaoDienStyle.taoNut("Xuất Excel (CSV)", MAU_TEAL, 130);

        pnlRow2.add(btnBaoCao); 
        pnlRow2.add(btnReset); 
        pnlRow2.add(btnXuatExcel);

        pnl.add(pnlRow1);
        pnl.add(pnlRow2);
        return pnl;
    }

    private JLabel taoNhan(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 11));
        return label;
    }

    private JPanel taoHangThongSoKPI() {
        JPanel pnl = new JPanel(new GridLayout(1, 4, 10, 0));
        pnl.setBackground(MAU_NEN);
        pnl.setPreferredSize(new Dimension(0, 75));
        lblTongLuotDung = new JLabel("0 lượt");
        lblTongDoanhThu = new JLabel("0 VNĐ");
        lblDichVuHot = new JLabel("---");
        lblTyTrongCaoNhat = new JLabel("0%");
        pnl.add(taoTheKPI("TỔNG LƯỢT SỬ DỤNG", lblTongLuotDung, new Color(41, 128, 185)));
        pnl.add(taoTheKPI("TỔNG DOANH THU", lblTongDoanhThu, new Color(39, 174, 96)));
        pnl.add(taoTheKPI("DỊCH VỤ DÙNG NHIỀU NHẤT", lblDichVuHot, new Color(142, 68, 173)));
        pnl.add(taoTheKPI("TỶ TRỌNG DOANH THU CAO NHẤT", lblTyTrongCaoNhat, new Color(211, 84, 0)));
        return pnl;
    }

    private JPanel taoTheKPI(String title, JLabel value, Color accent) {
        JPanel card = new JPanel(new BorderLayout(5, 2));
        card.setBackground(MAU_TRANG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
                BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(225, 230, 235)),
                        new EmptyBorder(8, 10, 8, 10))));
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Arial", Font.BOLD, 10));
        lbl.setForeground(new Color(120, 130, 140));
        value.setFont(new Font("Arial", Font.BOLD, 15));
        value.setForeground(MAU_NAVY);
        card.add(lbl, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        return card;
    }

    private JPanel taoKhuVucBangThongKe() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(MAU_TRANG);
        pnl.setBorder(BorderFactory.createTitledBorder("Chi tiết thống kê từng dịch vụ"));
        String[] cols = {"Mã DV", "Tên dịch vụ", "Số lượt", "Doanh thu (VNĐ)", "Tỷ trọng"};
        dtm = new DefaultTableModel(null, cols) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblThongKe = new JTable(dtm);
        GiaoDienStyle.dinhDangBang(tblThongKe);
        tblThongKe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblThongKe.setRowHeight(30);
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        for (int i = 2; i <= 4; i++) tblThongKe.getColumnModel().getColumn(i).setCellRenderer(right);
        pnl.add(new JScrollPane(tblThongKe), BorderLayout.CENTER);
        return pnl;
    }

    private JPanel taoKhuVucBieuDo() {
        JPanel pnl = new JPanel(new BorderLayout(8, 8));
        pnl.setBackground(MAU_TRANG);
        pnl.setBorder(BorderFactory.createTitledBorder("Trực quan hóa Top 5 dịch vụ"));
        pnl.setPreferredSize(new Dimension(380, 0));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        top.setBackground(MAU_TRANG);
        top.add(taoNhan("Tiêu chí:"));
        cboKieuBieuDo = new JComboBox<>(new String[]{"Theo lượt sử dụng", "Theo doanh thu"});
        cboKieuBieuDo.setPreferredSize(new Dimension(165, 28));
        top.add(cboKieuBieuDo);
        pnlVungBieuDo = new VEBieuDoCot();
        pnlVungBieuDo.setBackground(MAU_TRANG);
        pnl.add(top, BorderLayout.NORTH);
        pnl.add(pnlVungBieuDo, BorderLayout.CENTER);
        return pnl;
    }

    private class VEBieuDoCot extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.setColor(MAU_NAVY);
                g2.drawString("TOP 5 DỊCH VỤ NỔI BẬT", 12, 20);
                if (danhSachHienTai.isEmpty()) {
                    g2.setColor(Color.GRAY); g2.drawString("Chưa có dữ liệu hiển thị", 15, 55); return;
                }
                boolean revenue = cboKieuBieuDo.getSelectedIndex() == 1;
                List<Map<String, Object>> topList = new ArrayList<>(danhSachHienTai);
                topList.sort((a, b) -> Double.compare(layGiaTri(b, revenue), layGiaTri(a, revenue)));
                int limit = Math.min(5, topList.size());
                double max = 0;
                for (int i = 0; i < limit; i++) max = Math.max(max, layGiaTri(topList.get(i), revenue));
                int labelWidth = 112;
                int valueWidth = 82;
                int barArea = Math.max(20, getWidth() - labelWidth - valueWidth - 20);
                int barHeight = 23, gap = 17, startY = 42;
                for (int i = 0; i < limit; i++) {
                    Map<String, Object> item = topList.get(i);
                    int y = startY + i * (barHeight + gap);
                    String name = String.valueOf(item.get("TenDV"));
                    if (name.length() > 17) name = name.substring(0, 14) + "...";
                    g2.setFont(new Font("Arial", Font.PLAIN, 10));
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawString(name, 10, y + 15);
                    double val = layGiaTri(item, revenue);
                    int barW = max > 0 ? (int) (val / max * barArea) : 0;
                    g2.setColor(i == 0 ? MAU_NAVY : MAU_TEAL);
                    g2.fillRoundRect(labelWidth, y, Math.max(barW, 2), barHeight, 5, 5);
                    g2.setFont(new Font("Arial", Font.BOLD, 9));
                    g2.setColor(Color.BLACK);
                    String value = revenue ? df.format(val) + " đ" : df.format(val) + " lượt";
                    int valueX = Math.min(labelWidth + barW + 5, getWidth() - valueWidth);
                    g2.drawString(value, valueX, y + 15);
                }
            } finally { g2.dispose(); }
        }
    }

    private double layGiaTri(Map<String, Object> item, boolean revenue) {
        Object value = item.get(revenue ? "DoanhThu" : "SoLuotDung");
        return value instanceof Number ? ((Number) value).doubleValue() : 0;
    }

    private void dangKySuKien() {
        btnBaoCao.addActionListener(this);
        btnReset.addActionListener(this);
        btnXuatExcel.addActionListener(this);
        cboKieuBieuDo.addActionListener(this);
    }

    @Override public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnBaoCao) thucHienThongKeTuCSDL();
        else if (src == btnReset) xuLyResetBoLoc();
        else if (src == btnXuatExcel) xuLyXuatExcel();
        else if (src == cboKieuBieuDo) pnlVungBieuDo.repaint();
    }

    private Date[] tinhKhoangThoiGian() {
        Calendar from = Calendar.getInstance();
        from.setTime((Date) spnTuNgay.getValue());
        Calendar start = (Calendar) from.clone();
        Calendar end = (Calendar) from.clone();
        String mode = String.valueOf(cboKieuThongKe.getSelectedItem());

        if ("Theo ngày".equals(mode)) {
            start.set(Calendar.HOUR_OF_DAY, 0); start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0); start.set(Calendar.MILLISECOND, 0);
            end = (Calendar) start.clone(); end.add(Calendar.DAY_OF_MONTH, 1);
        } else if ("Theo tháng".equals(mode)) {
            start.set(Calendar.DAY_OF_MONTH, 1);
            start.set(Calendar.HOUR_OF_DAY, 0); start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0); start.set(Calendar.MILLISECOND, 0);
            end = (Calendar) start.clone(); end.add(Calendar.MONTH, 1);
        } else if ("Theo năm".equals(mode)) {
            start.set(Calendar.MONTH, Calendar.JANUARY); start.set(Calendar.DAY_OF_MONTH, 1);
            start.set(Calendar.HOUR_OF_DAY, 0); start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0); start.set(Calendar.MILLISECOND, 0);
            end = (Calendar) start.clone(); end.add(Calendar.YEAR, 1);
        } else {
            start.setTime((Date) spnTuNgay.getValue());
            end.setTime((Date) spnDenNgay.getValue());
            start.set(Calendar.HOUR_OF_DAY, 0); start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0); start.set(Calendar.MILLISECOND, 0);
            end.set(Calendar.HOUR_OF_DAY, 0); end.set(Calendar.MINUTE, 0);
            end.set(Calendar.SECOND, 0); end.set(Calendar.MILLISECOND, 0);
            if (end.before(start)) throw new IllegalArgumentException("Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu.");
            end.add(Calendar.DAY_OF_MONTH, 1);
        }
        return new Date[]{start.getTime(), end.getTime()};
    }

    private void taiDuLieuThongKeTuCSDL() {
        try {
            Date[] range = tinhKhoangThoiGian();
            java.sql.Date sqlStart = new java.sql.Date(range[0].getTime());
            java.sql.Date sqlEnd = new java.sql.Date(range[1].getTime());
            String mode = String.valueOf(cboKieuThongKe.getSelectedItem());
            String status = String.valueOf(cboTrangThai.getSelectedItem());
            danhSachHienTai = tk_dao.getThongKeDichVu(mode, status, sqlStart, sqlEnd);
            capNhatBangVaKPI();
            pnlVungBieuDo.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi thống kê", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void capNhatBangVaKPI() {
        dtm.setRowCount(0);
        long totalCount = 0;
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for (Map<String, Object> item : danhSachHienTai) {
            int count = ((Number) item.get("SoLuotDung")).intValue();
            BigDecimal revenue = BigDecimal.valueOf(((Number) item.get("DoanhThu")).doubleValue());
            totalCount += count;
            totalRevenue = totalRevenue.add(revenue);
        }
        for (Map<String, Object> item : danhSachHienTai) {
            int count = ((Number) item.get("SoLuotDung")).intValue();
            double revenue = ((Number) item.get("DoanhThu")).doubleValue();
            double percent = totalRevenue.signum() > 0 ? revenue / totalRevenue.doubleValue() * 100 : 0;
            dtm.addRow(new Object[]{item.get("MaDV"), item.get("TenDV"), df.format(count),
                    df.format(revenue), String.format(java.util.Locale.US, "%.2f%%", percent)});
        }
        lblTongLuotDung.setText(df.format(totalCount) + " lượt");
        lblTongDoanhThu.setText(df.format(totalRevenue) + " VNĐ");
        if (!danhSachHienTai.isEmpty()) {
            Map<String, Object> topByCount = danhSachHienTai.get(0);
            lblDichVuHot.setText(String.valueOf(topByCount.get("TenDV")));
            double maxRevenue = 0;
            for (Map<String, Object> item : danhSachHienTai) {
                maxRevenue = Math.max(maxRevenue, ((Number) item.get("DoanhThu")).doubleValue());
            }
            double percent = totalRevenue.signum() > 0 ? maxRevenue / totalRevenue.doubleValue() * 100 : 0;
            lblTyTrongCaoNhat.setText(String.format(java.util.Locale.US, "%.1f%%", percent));
        } else {
            lblDichVuHot.setText("---");
            lblTyTrongCaoNhat.setText("0%");
        }
    }

    private void thucHienThongKeTuCSDL() {
        taiDuLieuThongKeTuCSDL();
    }

    private void xuLyResetBoLoc() {
        cboKieuThongKe.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        cboKieuBieuDo.setSelectedIndex(0);
        Date today = new Date();
        spnTuNgay.setValue(today);
        spnDenNgay.setValue(today);
        taiDuLieuThongKeTuCSDL();
    }

    /** Xuất CSV UTF-8 có BOM để Excel mở đúng tiếng Việt. */
    private void xuLyXuatExcel() {
        if (dtm.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất báo cáo.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Lưu báo cáo thống kê dịch vụ");
        chooser.setSelectedFile(new File("BaoCaoThongKeDichVu.csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) file = new File(file.getAbsolutePath() + ".csv");

        try (PrintWriter pw = new PrintWriter(file, StandardCharsets.UTF_8.name())) {
            pw.write('\uFEFF');
            pw.println("BÁO CÁO THỐNG KÊ DỊCH VỤ NHA KHOA");
            pw.println("Kiểu thống kê," + csv(cboKieuThongKe.getSelectedItem()));
            pw.println("Trạng thái dịch vụ," + csv(cboTrangThai.getSelectedItem()));
            pw.println("Thời điểm xuất," + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            pw.println();
            pw.println("Mã dịch vụ,Tên dịch vụ,Số lượt sử dụng,Doanh thu (VNĐ),Tỷ trọng");
            for (int i = 0; i < dtm.getRowCount(); i++) {
                for (int j = 0; j < dtm.getColumnCount(); j++) {
                    if (j > 0) pw.print(',');
                    pw.print(csv(dtm.getValueAt(i, j)));
                }
                pw.println();
            }
            JOptionPane.showMessageDialog(this, "Xuất báo cáo thành công:\n" + file.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể xuất báo cáo: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String csv(Object value) {
        String s = value == null ? "" : String.valueOf(value);
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }
}
