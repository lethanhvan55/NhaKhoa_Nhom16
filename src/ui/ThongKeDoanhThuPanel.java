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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ThongKeDoanhThuPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final Color MAU_CHU = new Color(35, 65, 95);
    private static final Color MAU_XANH = new Color(15, 150, 137);
    private static final Color MAU_NEN = new Color(243, 247, 251);
    private static final Color MAU_XANH_DUONG = new Color(47, 105, 180);

    private JComboBox<String> cboKieuThongKe;
    private JButton btnThongKe;
    private JButton btnXuatBaoCao;

    private JTable tblDoanhThu;
    private DefaultTableModel modelDoanhThu;

    private BieuDoCotPanel pnlBieuDoCot;
    private BieuDoDuongPanel pnlBieuDoDuong;

    public ThongKeDoanhThuPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(MAU_NEN);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(taoTieuDe(), BorderLayout.NORTH);
        add(taoNoiDung(), BorderLayout.CENTER);
        capNhatDuLieuThongKe();
    }

    private JPanel taoTieuDe() {
        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setOpaque(false);

        JLabel lblMoTa = new JLabel("Tổng quan doanh thu");
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 15));
        lblMoTa.setForeground(MAU_CHU);

        pnlTieuDe.add(lblMoTa, BorderLayout.WEST);
        pnlTieuDe.add(taoBoLoc(), BorderLayout.EAST);

        return pnlTieuDe;
    }

    private JPanel taoBoLoc() {
        JPanel pnlBoLoc = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        pnlBoLoc.setOpaque(false);

        JLabel lblKieuTK = new JLabel("Thống kê theo:");
        lblKieuTK.setFont(new Font("Arial", Font.BOLD, 12));
        cboKieuThongKe = new JComboBox<>(new String[]{"Theo ngày", "Theo tháng", "Theo năm"});
        cboKieuThongKe.setPreferredSize(new Dimension(130, 34));

        btnThongKe = taoNut("Thống kê", MAU_XANH);
        btnXuatBaoCao = taoNut("Xuất báo cáo", MAU_XANH_DUONG);
        
        btnThongKe.addActionListener(this);
        btnXuatBaoCao.addActionListener(this);

        pnlBoLoc.add(lblKieuTK);
        pnlBoLoc.add(Box.createHorizontalStrut(4));
        pnlBoLoc.add(cboKieuThongKe);
        pnlBoLoc.add(Box.createHorizontalStrut(8));
        pnlBoLoc.add(btnThongKe);
        pnlBoLoc.add(btnXuatBaoCao);

        return pnlBoLoc;
    }

    private JPanel taoNoiDung() {
        JPanel pnlNoiDung = new JPanel(new BorderLayout(15, 15));
        pnlNoiDung.setOpaque(false);

        JPanel pnlBieuDo = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlBieuDo.setOpaque(false);
        pnlBieuDo.setPreferredSize(new Dimension(0, 360));

        pnlBieuDoCot = new BieuDoCotPanel();
        pnlBieuDoDuong = new BieuDoDuongPanel();

        JPanel pnlKhungCot = taoKhung("DOANH THU THEO THÁNG (Triệu VNĐ)");
        pnlKhungCot.add(pnlBieuDoCot, BorderLayout.CENTER);

        JPanel pnlKhungDuong = taoKhung("XU HƯỚNG DOANH THU THEO NĂM (Triệu VNĐ)");
        pnlKhungDuong.add(pnlBieuDoDuong, BorderLayout.CENTER);

        pnlBieuDo.add(pnlKhungCot);
        pnlBieuDo.add(pnlKhungDuong);

        JPanel pnlDuoi = taoBangDoanhThu();

        pnlNoiDung.add(pnlBieuDo, BorderLayout.NORTH);
        pnlNoiDung.add(pnlDuoi, BorderLayout.CENTER);

        return pnlNoiDung;
    }

    private JPanel taoBangDoanhThu() {
        JPanel pnlBang = taoKhung("CHI TIẾT THỐNG KÊ DOANH THU DỊCH VỤ");

        String[] cot = {
                "STT", "Mã dịch vụ", "Tên dịch vụ",
                "Số lượt sử dụng", "Tổng doanh thu", "Tỷ trọng"
        };

        modelDoanhThu = new DefaultTableModel(null, cot) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDoanhThu = new JTable(modelDoanhThu);
        tblDoanhThu.setRowHeight(32);
        tblDoanhThu.setFont(new Font("Arial", Font.PLAIN, 13));
        tblDoanhThu.setForeground(Color.BLACK);
        tblDoanhThu.setBackground(Color.WHITE);
        tblDoanhThu.setSelectionBackground(new Color(204, 245, 238));
        tblDoanhThu.setSelectionForeground(MAU_CHU);
        tblDoanhThu.setGridColor(new Color(230, 235, 242));
        tblDoanhThu.setShowVerticalLines(false);
        tblDoanhThu.setFillsViewportHeight(true);
        tblDoanhThu.setAutoCreateRowSorter(true);

        tblDoanhThu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblDoanhThu.getTableHeader().setBackground(MAU_CHU);
        tblDoanhThu.getTableHeader().setForeground(Color.WHITE);
        tblDoanhThu.getTableHeader().setPreferredSize(new Dimension(0, 36));
        tblDoanhThu.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrDoanhThu = new JScrollPane(tblDoanhThu);
        scrDoanhThu.setBorder(BorderFactory.createEmptyBorder());
        scrDoanhThu.getViewport().setBackground(Color.WHITE);

        pnlBang.add(scrDoanhThu, BorderLayout.CENTER);

        return pnlBang;
    }

    private JPanel taoKhung(String tieuDe) {
        JPanel pnlKhung = new JPanel(new BorderLayout(10, 12));
        pnlKhung.setBackground(Color.WHITE);
        pnlKhung.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(224, 232, 241)),
                        new EmptyBorder(15, 15, 15, 15)));

        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 14));
        lblTieuDe.setForeground(MAU_CHU);

        pnlKhung.add(lblTieuDe, BorderLayout.NORTH);

        return pnlKhung;
    }

    private JButton taoNut(String ten, Color mau) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mau);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 34));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnThongKe)) {
            xuLyThongKe();
        } else if (source.equals(btnXuatBaoCao)) {
            xuLyXuatBaoCao();
        }
    }

    private void xuLyThongKe() {
        String kieuTK = cboKieuThongKe.getSelectedItem().toString();
        capNhatDuLieuThongKe();
        JOptionPane.showMessageDialog(
                this,
                "Đã cập nhật báo cáo doanh thu theo chế độ: " + kieuTK,
                "Thông tin thống kê",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void xuLyXuatBaoCao() {
        JOptionPane.showMessageDialog(
                this,
                "Chức năng xuất báo cáo đang được phát triển.",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void capNhatDuLieuThongKe() {
        String kieuTK = cboKieuThongKe.getSelectedItem().toString();

        try {
            // Khung xử lý lấy dữ liệu từ CSDL thông qua DAO dựa theo kieuTK ("Theo ngày", "Theo tháng", "Theo năm")

            taiDuLieuBangDoanhThu();

            if (pnlBieuDoCot != null) {
                pnlBieuDoCot.repaint();
            }

            if (pnlBieuDoDuong != null) {
                pnlBieuDoDuong.repaint();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi tải dữ liệu thống kê từ cơ sở dữ liệu!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void taiDuLieuBangDoanhThu() {
        modelDoanhThu.setRowCount(0);
        // Khung nạp dữ liệu thực tế từ cơ sở dữ liệu vào JTable tại đây
    }

    private class BieuDoCotPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        private double[] giaTri = new double[12];
        private final String[] thang = {
                "T1", "T2", "T3", "T4", "T5", "T6",
                "T7", "T8", "T9", "T10", "T11", "T12"
        };

        public BieuDoCotPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(400, 260));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int trai = 42;
            int tren = 25;
            int duoi = 35;
            int rong = getWidth() - trai - 15;
            int cao = getHeight() - tren - duoi;

            if (rong <= 0 || cao <= 0) {
                g2.dispose();
                return;
            }

            g2.setFont(new Font("Arial", Font.PLAIN, 10));

            double maxGiaTri = 100;
            for (double val : giaTri) {
                if (val > maxGiaTri) maxGiaTri = val;
            }

            for (int i = 0; i <= 5; i++) {
                int y = tren + cao * i / 5;
                g2.setColor(new Color(232, 237, 243));
                g2.drawLine(trai, y, trai + rong, y);

                g2.setColor(new Color(110, 125, 140));
                double valY = maxGiaTri - i * (maxGiaTri / 5);
                g2.drawString(String.format("%.0f", valY), 5, y + 4);
            }

            int buoc = rong / 12;
            int rongCot = Math.max(4, buoc - 8);

            for (int i = 0; i < giaTri.length; i++) {
                int x = trai + i * buoc + (buoc - rongCot) / 2;
                int h = (int) (cao * giaTri[i] / maxGiaTri);
                int y = tren + cao - h;

                g2.setColor(MAU_XANH);
                g2.fillRoundRect(x, y, rongCot, h, 7, 7);

                g2.setColor(new Color(90, 110, 130));
                g2.drawString(
                        thang[i],
                        x + (rongCot - g2.getFontMetrics().stringWidth(thang[i])) / 2,
                        tren + cao + 18);
            }

            g2.setColor(new Color(110, 125, 140));
            g2.drawString("Doanh thu (Tr VNĐ)", trai, 15);
            g2.dispose();
        }
    }

    private class BieuDoDuongPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        private double[] giaTri = new double[0];
        private String[] nam = new String[0];

        public BieuDoDuongPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(400, 260));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int trai = 42;
            int phai = 18;
            int tren = 35;
            int duoi = 38;
            int rong = getWidth() - trai - phai;
            int cao = getHeight() - tren - duoi;

            if (rong <= 0 || cao <= 0 || giaTri.length == 0) {
                g2.setColor(new Color(110, 125, 140));
                g2.setFont(new Font("Arial", Font.ITALIC, 12));
                g2.drawString("Chưa có dữ liệu thống kê các năm", getWidth() / 3, getHeight() / 2);
                g2.dispose();
                return;
            }

            double maxGiaTri = 100;
            for (double val : giaTri) {
                if (val > maxGiaTri) maxGiaTri = val;
            }

            int soMoc = 5;
            g2.setFont(new Font("Arial", Font.PLAIN, 10));

            for (int i = 0; i <= soMoc; i++) {
                int y = tren + cao * i / soMoc;
                double nhan = maxGiaTri - i * (maxGiaTri / soMoc);

                g2.setColor(new Color(232, 237, 243));
                g2.drawLine(trai, y, trai + rong, y);

                g2.setColor(new Color(110, 125, 140));
                g2.drawString(String.format("%.0f", nhan), 3, y + 4);
            }

            g2.setColor(new Color(110, 125, 140));
            g2.drawString("Doanh thu (Tr VNĐ)", trai, 15);

            int[] toaDoX = new int[giaTri.length];
            int[] toaDoY = new int[giaTri.length];
            int buoc = giaTri.length > 1 ? rong / (giaTri.length - 1) : rong;

            for (int i = 0; i < giaTri.length; i++) {
                toaDoX[i] = trai + i * buoc;
                toaDoY[i] = tren + cao - (int) (giaTri[i] / maxGiaTri * cao);
            }

            g2.setColor(MAU_XANH);
            g2.setStroke(new java.awt.BasicStroke(2.5f, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND));

            for (int i = 0; i < giaTri.length - 1; i++) {
                g2.drawLine(toaDoX[i], toaDoY[i], toaDoX[i + 1], toaDoY[i + 1]);
            }

            for (int i = 0; i < giaTri.length; i++) {
                g2.setColor(MAU_XANH);
                g2.fill(new Ellipse2D.Double(toaDoX[i] - 4, toaDoY[i] - 4, 8, 8));

                g2.setColor(Color.WHITE);
                g2.fill(new Ellipse2D.Double(toaDoX[i] - 2, toaDoY[i] - 2, 4, 4));

                g2.setColor(MAU_XANH);
                String nhanGiaTri = String.format("%.0f", giaTri[i]);
                int rongNhan = g2.getFontMetrics().stringWidth(nhanGiaTri);
                g2.drawString(nhanGiaTri, toaDoX[i] - rongNhan / 2, toaDoY[i] - 10);

                g2.setColor(new Color(90, 110, 130));
                String nhanNam = nam[i];
                int rongNam = g2.getFontMetrics().stringWidth(nhanNam);
                g2.drawString(nam[i], toaDoX[i] - rongNam / 2, tren + cao + 20);
            }

            g2.dispose();
        }
    }
}