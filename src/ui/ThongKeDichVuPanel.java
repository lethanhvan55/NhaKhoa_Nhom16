package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ThongKeDichVuPanel extends JPanel implements ActionListener, MouseListener {

    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;

    private JComboBox<String> cboKieuThongKe; 
    private JComboBox<String> cboLoaiDichVu;
    private JButton btnBaoCao;
    private JButton btnReset;
    private JButton btnXuatExcel;

    private JLabel lblTongLuotDung;
    private JLabel lblTongDoanhThu;
    private JLabel lblDichVuHot;
    private JLabel lblTyTrongCaoNhat;

    private JTable tblThongKe;
    private DefaultTableModel dtm;

    private JComboBox<String> cboKieuBieuDo;
    private JPanel pnlVungBieuDo;

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

        JSplitPane splNoiDung = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlBang, pnlBieuDo);
        splNoiDung.setResizeWeight(0.58);
        splNoiDung.setDividerLocation(620);
        splNoiDung.setContinuousLayout(true);
        splNoiDung.setBorder(null);

        pnlTrungTam.add(splNoiDung, BorderLayout.CENTER);

        add(pnlTrungTam, BorderLayout.CENTER);
    }

    private JPanel taoThanhBoLoc() {
        JPanel pnlFilter = new JPanel(new BorderLayout(10, 0));
        pnlFilter.setBackground(MAU_TRANG);
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JPanel pnlLeftControls = new JPanel();
        pnlLeftControls.setLayout(new BoxLayout(pnlLeftControls, BoxLayout.X_AXIS));
        pnlLeftControls.setBackground(MAU_TRANG);

        JLabel lblKieuTK = new JLabel("Thống kê theo:");
        lblKieuTK.setFont(new Font("Arial", Font.BOLD, 12));
        cboKieuThongKe = new JComboBox<>(new String[]{"Theo ngày", "Theo tháng", "Theo năm"});
        cboKieuThongKe.setPreferredSize(new Dimension(130, 30));
        cboKieuThongKe.setMaximumSize(new Dimension(130, 30));

        JLabel lblLoaiDV = new JLabel(" Dịch vụ:");
        lblLoaiDV.setFont(new Font("Arial", Font.BOLD, 12));
        cboLoaiDichVu = new JComboBox<>(new String[]{"Tất cả loại dịch vụ", "Khám & Tư vấn", "Điều trị răng", "Phẫu thuật", "Thẩm mỹ"});
        cboLoaiDichVu.setPreferredSize(new Dimension(140, 30));
        cboLoaiDichVu.setMaximumSize(new Dimension(150, 30));

        btnBaoCao = GiaoDienStyle.taoNut("Thống kê", MAU_NAVY, 90);
        btnReset = GiaoDienStyle.taoNut("Làm mới", new Color(108, 117, 125), 85);

        pnlLeftControls.add(lblKieuTK);
        pnlLeftControls.add(Box.createHorizontalStrut(6));
        pnlLeftControls.add(cboKieuThongKe);
        pnlLeftControls.add(Box.createHorizontalStrut(12));
        pnlLeftControls.add(lblLoaiDV);
        pnlLeftControls.add(Box.createHorizontalStrut(6));
        pnlLeftControls.add(cboLoaiDichVu);
        pnlLeftControls.add(Box.createHorizontalStrut(12));
        pnlLeftControls.add(btnBaoCao);
        pnlLeftControls.add(Box.createHorizontalStrut(6));
        pnlLeftControls.add(btnReset);

        btnXuatExcel = GiaoDienStyle.taoNut("Xuất báo cáo", MAU_TEAL, 105);

        pnlFilter.add(pnlLeftControls, BorderLayout.WEST);
        pnlFilter.add(btnXuatExcel, BorderLayout.EAST);

        return pnlFilter;
    }

    private JPanel taoHangThongSoKPI() {
        JPanel pnlKPI = new JPanel(new GridLayout(1, 4, 10, 0));
        pnlKPI.setBackground(MAU_NEN);
        pnlKPI.setPreferredSize(new Dimension(0, 75));

        lblTongLuotDung = new JLabel("0 lượt", SwingConstants.LEFT);
        lblTongDoanhThu = new JLabel("0 VNĐ", SwingConstants.LEFT);
        lblDichVuHot = new JLabel("---", SwingConstants.LEFT);
        lblTyTrongCaoNhat = new JLabel("0%", SwingConstants.LEFT);

        pnlKPI.add(taoTheKPIDon("TỔNG LƯỢT SỬ DỤNG", lblTongLuotDung, new Color(41, 128, 185)));
        pnlKPI.add(taoTheKPIDon("TỔNG DOANH THU DV", lblTongDoanhThu, new Color(39, 174, 96)));
        pnlKPI.add(taoTheKPIDon("DỊCH VỤ DÙNG NHIỀU NHẤT", lblDichVuHot, new Color(142, 68, 173)));
        pnlKPI.add(taoTheKPIDon("TỶ TRỌNG CAO NHẤT", lblTyTrongCaoNhat, new Color(211, 84, 0)));

        return pnlKPI;
    }

    private JPanel taoTheKPIDon(String tieuDe, JLabel lblGiaTri, Color mauNhat) {
        JPanel pnlCard = new JPanel(new BorderLayout(5, 2));
        pnlCard.setBackground(MAU_TRANG);
        pnlCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, mauNhat),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(225, 230, 235)),
                        new EmptyBorder(8, 10, 8, 10)
                )
        ));

        JLabel lblTitle = new JLabel(tieuDe);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 10));
        lblTitle.setForeground(new Color(120, 130, 140));

        lblGiaTri.setFont(new Font("Arial", Font.BOLD, 15));
        lblGiaTri.setForeground(MAU_NAVY);

        pnlCard.add(lblTitle, BorderLayout.NORTH);
        pnlCard.add(lblGiaTri, BorderLayout.CENTER);

        return pnlCard;
    }

    private JPanel taoKhuVucBangThongKe() {
        JPanel pnl = new JPanel(new BorderLayout(8, 8));
        pnl.setBackground(MAU_TRANG);
        pnl.setBorder(BorderFactory.createTitledBorder("Chi tiết thống kê từng dịch vụ"));

        String[] cot = {"Mã DV", "Tên dịch vụ", "Số lượt", "Doanh thu (VNĐ)", "Tỷ trọng"};

        dtm = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblThongKe = new JTable(dtm);
        GiaoDienStyle.dinhDangBang(tblThongKe);
        tblThongKe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblThongKe.setRowHeight(30);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tblThongKe.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tblThongKe.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblThongKe.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        JScrollPane scr = new JScrollPane(tblThongKe);
        scr.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        pnl.add(scr, BorderLayout.CENTER);
        return pnl;
    }

    private JPanel taoKhuVucBieuDo() {
        JPanel pnl = new JPanel(new BorderLayout(8, 8));
        pnl.setBackground(MAU_TRANG);
        pnl.setBorder(BorderFactory.createTitledBorder("Trực quan hóa top dịch vụ"));
        pnl.setPreferredSize(new Dimension(380, 0));

        JPanel pnlTopControl = new JPanel(new BorderLayout(5, 0));
        pnlTopControl.setBackground(MAU_TRANG);
        pnlTopControl.setBorder(new EmptyBorder(5, 10, 0, 10));

        JLabel lblChonBD = new JLabel("Tiêu chí top 5:");
        lblChonBD.setFont(new Font("Arial", Font.BOLD, 12));
        cboKieuBieuDo = new JComboBox<>(new String[]{"Theo lượt sử dụng", "Theo doanh thu"});
        cboKieuBieuDo.setPreferredSize(new Dimension(160, 28));

        pnlTopControl.add(lblChonBD, BorderLayout.WEST);
        pnlTopControl.add(cboKieuBieuDo, BorderLayout.CENTER);

        pnlVungBieuDo = new VEBieuDoCot();
        pnlVungBieuDo.setBackground(MAU_TRANG);

        pnl.add(pnlTopControl, BorderLayout.NORTH);
        pnl.add(pnlVungBieuDo, BorderLayout.CENTER);

        return pnl;
    }

    private class VEBieuDoCot extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.setColor(MAU_NAVY);
            g2.drawString("TOP 5 DỊCH VỤ NỔI BẬT", 15, 20);

            String[] names = {"---", "---", "---", "---", "---"};
            int[] values = {0, 0, 0, 0, 0};
            int maxValue = 100;

            int startY = 50;
            int barHeight = 22;
            int gap = 18;
            int maxBarWidth = width - 150;

            for (int i = 0; i < names.length; i++) {
                int currentY = startY + i * (barHeight + gap);

                g2.setFont(new Font("Arial", Font.PLAIN, 11));
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(names[i], 15, currentY + 15);

                int barW = maxValue > 0 ? (int) (((double) values[i] / maxValue) * maxBarWidth) : 0;

                g2.setColor(i == 0 ? MAU_NAVY : MAU_TEAL);
                g2.fillRect(110, currentY, Math.max(barW, 5), barHeight);

                g2.setFont(new Font("Arial", Font.BOLD, 11));
                g2.setColor(Color.BLACK);
                g2.drawString(values[i] + " lượt", 120 + barW, currentY + 15);
            }
        }
    }

    private void dangKySuKien() {
        btnBaoCao.addActionListener(this);
        btnReset.addActionListener(this);
        btnXuatExcel.addActionListener(this);
        cboKieuBieuDo.addActionListener(this);
        tblThongKe.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnBaoCao)) {
            thucHienThongKeTuCSDL();
        } else if (source.equals(btnReset)) {
            xuLyResetBoLoc();
        } else if (source.equals(btnXuatExcel)) {
            xuLyXuatExcel();
        } else if (source.equals(cboKieuBieuDo)) {
            pnlVungBieuDo.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblThongKe.getSelectedRow();
        if (row >= 0) {
            String maDV = String.valueOf(dtm.getValueAt(row, 0));
            String tenDV = String.valueOf(dtm.getValueAt(row, 1));
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private void taiDuLieuThongKeTuCSDL() {
        dtm.setRowCount(0);

        try {
            String kieuTK = cboKieuThongKe.getSelectedItem().toString();
            String loaiDV = cboLoaiDichVu.getSelectedItem().toString();

            // TODO: Truyền kieuTK và loaiDV vào DAO để truy vấn CSDL theo lựa chọn (Theo ngày, Theo tháng, Theo năm)

            lblTongLuotDung.setText("0 lượt");
            lblTongDoanhThu.setText("0 VNĐ");
            lblDichVuHot.setText("---");
            lblTyTrongCaoNhat.setText("0%");

            pnlVungBieuDo.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu thống kê dịch vụ từ cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void thucHienThongKeTuCSDL() {
        String kieuTK = cboKieuThongKe.getSelectedItem().toString();
        taiDuLieuThongKeTuCSDL();
        JOptionPane.showMessageDialog(this, "Đã cập nhật báo cáo theo chế độ: " + kieuTK);
    }

    private void xuLyResetBoLoc() {
        cboKieuThongKe.setSelectedIndex(0);
        cboLoaiDichVu.setSelectedIndex(0);
        taiDuLieuThongKeTuCSDL();
        JOptionPane.showMessageDialog(this, "Đã làm mới bộ lọc thống kê!");
    }

    private void xuLyXuatExcel() {
        try {
            JOptionPane.showMessageDialog(this, "Đã xuất báo cáo thống kê dịch vụ ra file Excel thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}