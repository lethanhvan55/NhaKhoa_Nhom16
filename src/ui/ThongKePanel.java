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

public class ThongKePanel extends JPanel {

    private static final Color MAU_CHU =new Color(35, 65, 95);

    private static final Color MAU_XANH =new Color(15, 150, 137);

    private static final Color MAU_NEN =new Color(243, 247, 251);

    private static final Color MAU_XANH_DUONG =new Color(47, 105, 180);

    private static final Color MAU_CAM =new Color(230, 150, 55);

    private static final Color MAU_TIM =new Color(120, 90, 180);

    private JDateChooser dcTuNgay;
    private JDateChooser dcDenNgay;

    private JButton btnThongKe;
    private JButton btnXuatBaoCao;

    private JTable tblDichVu;

    public ThongKePanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(MAU_NEN);
        setBorder(new EmptyBorder(20, 20, 20, 20));

       add(taoTieuDe(), BorderLayout.NORTH);
        add(taoNoiDung(), BorderLayout.CENTER);
    }

    private JPanel taoTieuDe() {
        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setOpaque(false);

        JLabel lblMoTa = new JLabel( "Tổng quan hoạt động");

        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 15));

        lblMoTa.setForeground(MAU_CHU);

        pnlTieuDe.add(lblMoTa, BorderLayout.WEST);
        pnlTieuDe.add(taoBoLoc(), BorderLayout.EAST);

        return pnlTieuDe;
    } 

    private JPanel taoBoLoc() {
        JPanel pnlBoLoc = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));

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

        btnThongKe.addActionListener(
                new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(
                    java.awt.event.ActionEvent e) {
                xuLyThongKe();
            }
        });

        btnXuatBaoCao.addActionListener(
                new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(
                    java.awt.event.ActionEvent e) {
                JOptionPane.showMessageDialog(ThongKePanel.this, "Chức năng xuất báo cáo chưa được xử lý.","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
        });

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

    private void xuLyThongKe() {
        Date tuNgay = dcTuNgay.getDate();
        Date denNgay = dcDenNgay.getDate();

        if (tuNgay == null || denNgay == null) {
            JOptionPane.showMessageDialog( this,"Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!","Thông báo",JOptionPane.WARNING_MESSAGE);
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

        SimpleDateFormat dinhDang = new SimpleDateFormat("dd/MM/yyyy");

        JOptionPane.showMessageDialog(
                this,
                "Khoảng thời gian thống kê:\nTừ ngày: "
                        + dinhDang.format(tuNgay)
                        + "\nĐến ngày: "
                        + dinhDang.format(denNgay),
                "Thông tin thống kê",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel taoNoiDung() {
        JPanel pnlNoiDung = new JPanel(new BorderLayout(15, 15));

        pnlNoiDung.setOpaque(false);

        // Bỏ pnlTongQuan, chỉ giữ lại phần biểu đồ ở phía trên
        JPanel pnlBieuDo = new JPanel(new GridLayout(1, 2, 15, 0));

        pnlBieuDo.setOpaque(false);
        pnlBieuDo.add(taoBieuDoDoanhThu());
        pnlBieuDo.add(taoBieuDoDichVu());

        JPanel pnlDuoi = taoBangDichVu();

        pnlNoiDung.add(pnlBieuDo, BorderLayout.NORTH);
        pnlNoiDung.add(pnlDuoi, BorderLayout.CENTER);

        return pnlNoiDung;
    }

    private JPanel taoBieuDoDoanhThu() {JPanel pnlBieuDo = taoKhung("DOANH THU THEO THÁNG");

        pnlBieuDo.add(new BieuDoCotPanel(),BorderLayout.CENTER);

        return pnlBieuDo;
    }

    private JPanel taoBieuDoDichVu() { JPanel pnlBieuDo = taoKhung( "TỶ LỆ SỬ DỤNG DỊCH VỤ");

        pnlBieuDo.add( new BieuDoTronPanel(),BorderLayout.CENTER);
        return pnlBieuDo;
    }

    private JPanel taoBangDichVu() {
        JPanel pnlBang = taoKhung("CHI TIẾT THỐNG KÊ DỊCH VỤ");

        String[] cot = {"STT","Mã dịch vụ", "Tên dịch vụ","Số lượt", "Doanh thu","Tỷ trọng"};

        Object[][] duLieu = { {1, "DV001", "Cạo vôi răng",65, "19.500.000 đ", "20%"},
        		{2, "DV002", "Trám răng",48, "24.000.000 đ", "24%"},
                {3, "DV003", "Chụp X-quang",35, "10.500.000 đ", "11%"},
                {4, "DV004", "Nhổ răng",22, "11.000.000 đ", "11%"},
                {5, "DV005", "Niềng răng",16, "33.000.000 đ", "34%"}
        };

        DefaultTableModel model = new DefaultTableModel(duLieu, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(model);

        tblDichVu.setRowHeight(32);
        tblDichVu.setFont(new Font("Arial", Font.PLAIN, 13));

        tblDichVu.setSelectionBackground(new Color(204, 245, 238));

        tblDichVu.setSelectionForeground(MAU_CHU);

        tblDichVu.setGridColor(new Color(230, 235, 242));

        tblDichVu.setShowVerticalLines(false);

        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        tblDichVu.getTableHeader().setBackground(MAU_CHU);
        tblDichVu.getTableHeader().setForeground(Color.WHITE);

        tblDichVu.getTableHeader().setPreferredSize(new Dimension(0, 36));

        JScrollPane scrDichVu = new JScrollPane(tblDichVu);

        scrDichVu.setBorder(BorderFactory.createEmptyBorder());

        pnlBang.add(scrDichVu, BorderLayout.CENTER);

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

    private class BieuDoCotPanel extends JPanel {

        private final int[] giaTri = {45, 55, 48, 72, 65, 85,78, 95, 82, 110, 100, 125};

        private final String[] thang = { "T1", "T2", "T3", "T4", "T5", "T6","T7", "T8", "T9", "T10", "T11", "T12"};

        public BieuDoCotPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(400, 230));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            int trai = 42;
            int tren = 20;
            int duoi = 35;

            int rong = getWidth() - trai - 15;
            int cao = getHeight() - tren - duoi;

            if (rong <= 0 || cao <= 0) {
                g2.dispose();
                return;
            }

            g2.setFont(new Font("Arial", Font.PLAIN, 10));

            for (int i = 0; i <= 4; i++) {
                int y = tren + cao * i / 4;

                g2.setColor(new Color(232, 237, 243));

                g2.drawLine(trai, y, trai + rong, y);

                g2.setColor(new Color(110, 125, 140));

                g2.drawString(String.valueOf(150 - i * 30),5, y + 4);
            }

            int buoc = rong / 12;
            int rongCot = Math.max(4, buoc - 8);

            for (int i = 0; i < giaTri.length; i++) {
                int x = trai + i * buoc + 4;
                int h = cao * giaTri[i] / 150;
                int y = tren + cao - h;
                g2.setColor(MAU_XANH);
                g2.fillRoundRect(x, y, rongCot, h, 7, 7);
                g2.setColor(new Color(90, 110, 130));
                g2.drawString(thang[i],x, tren + cao + 18);
            }

            g2.setColor(new Color(110, 125, 140));
            g2.drawString("Doanh thu (triệu đồng)",trai, 12);
            g2.dispose();
        }
    }

    private class BieuDoTronPanel extends JPanel {

        public BieuDoTronPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(400, 230));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int[] giaTri = {35, 25, 20, 12, 8};

            String[] ten = {"Cạo vôi răng","Trám răng","Chụp X-quang","Nhổ răng","Niềng răng"};

            Color[] mau = {MAU_XANH,MAU_XANH_DUONG,MAU_CAM,MAU_TIM,new Color(225, 100, 125)};

            int kichThuoc = Math.min(
                    165,
                    Math.min( getHeight() - 20,getWidth() / 2));
            if (kichThuoc <= 0) {
                g2.dispose();
                return;
            }

            int x = 15;

            int y = Math.max(5, (getHeight() - kichThuoc) / 2);

            int gocBatDau = 90;

            for (int i = 0; i < giaTri.length; i++) {
                int goc = giaTri[i] * 360 / 100;
                g2.setColor(mau[i]);
                g2.fillArc(x, y, kichThuoc, kichThuoc,gocBatDau, -goc);
                gocBatDau -= goc;
            }
            int xChu = x + kichThuoc + 15;
            int yChu = Math.max(25, getHeight() / 2 - 45);
            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            for (int i = 0; i < ten.length; i++) {
                int yy = yChu + i * 24;
                g2.setColor(mau[i]);
                g2.fillRoundRect(xChu, yy - 9, 12, 12, 3, 3);
                g2.setColor(MAU_CHU);
                g2.drawString( ten[i] + " (" + giaTri[i] + "%)", xChu + 19, yy + 1);
            }

            g2.dispose();
        }
    }
}