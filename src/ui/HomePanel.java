
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class HomePanel extends JPanel {

    private final Color MAU_CHU = new Color(35, 70, 110);
    private final Color MAU_XANH = new Color(35, 105, 180);
    private final Color MAU_NEN = new Color(245, 247, 250);
    private final Color MAU_VIEN = new Color(224, 232, 241);

    public HomePanel() {
        setLayout(new BorderLayout(15, 18));
        setBackground(MAU_NEN);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(taoTieuDe(), BorderLayout.NORTH);
        add(taoNoiDung(), BorderLayout.CENTER);
    }

    private JPanel taoTieuDe() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setOpaque(false);
        pnl.setBorder(new EmptyBorder(0, 0, 5, 0));

        JLabel lblTieuDe = new JLabel("TRANG CHỦ");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 26));
        lblTieuDe.setForeground(MAU_CHU);

        JLabel lblMoTa = new JLabel(
            "Tổng quan hoạt động phòng khám nha khoa"
        );
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 13));
        lblMoTa.setForeground(new Color(105, 120, 140));

        JPanel pnlBenTrai = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlBenTrai.setOpaque(false);
        pnlBenTrai.add(lblTieuDe);
        pnlBenTrai.add(lblMoTa);

        JLabel lblNgay = new JLabel(
            "Hôm nay: "
            + new SimpleDateFormat("dd/MM/yyyy").format(new Date())
        );
        lblNgay.setFont(new Font("Arial", Font.BOLD, 13));
        lblNgay.setForeground(MAU_XANH);
        lblNgay.setHorizontalAlignment(SwingConstants.RIGHT);

        pnl.add(pnlBenTrai, BorderLayout.WEST);
        pnl.add(lblNgay, BorderLayout.EAST);

        return pnl;
    }

    private JPanel taoNoiDung() {
        JPanel pnl = new JPanel(new BorderLayout(15, 15));
        pnl.setOpaque(false);

        pnl.add(taoThongKe(), BorderLayout.NORTH);
        pnl.add(taoLichHen(), BorderLayout.CENTER);
        pnl.add(taoHoatDong(), BorderLayout.SOUTH);

        return pnl;
    }

    private JPanel taoThongKe() {
        JPanel pnl = new JPanel(new GridLayout(1, 4, 12, 0));
        pnl.setOpaque(false);

        pnl.add(taoTheThongKe(
            "BỆNH NHÂN",
            "128",
            "Tổng số bệnh nhân",
            new Color(48, 125, 220)
        ));

        pnl.add(taoTheThongKe(
            "LỊCH HẸN HÔM NAY",
            "18",
            "Lịch hẹn trong ngày",
            new Color(15, 150, 137)
        ));

        pnl.add(taoTheThongKe(
            "KẾ HOẠCH ĐIỀU TRỊ",
            "12",
            "Kế hoạch đang theo dõi",
            new Color(140, 95, 200)
        ));

        pnl.add(taoTheThongKe(
            "HÓA ĐƠN CHƯA THANH TOÁN",
            "5",
            "Cần kiểm tra thanh toán",
            new Color(230, 155, 45)
        ));

        return pnl;
    }

    private JPanel taoTheThongKe(
        String tieuDe,
        String giaTri,
        String moTa,
        Color mau
    ) {
        JPanel pnl = new JPanel(new BorderLayout(5, 8));
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(MAU_VIEN),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 11));
        lblTieuDe.setForeground(new Color(100, 115, 135));

        JLabel lblGiaTri = new JLabel(giaTri);
        lblGiaTri.setFont(new Font("Arial", Font.BOLD, 28));
        lblGiaTri.setForeground(mau);

        JLabel lblMoTa = new JLabel(moTa);
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 11));
        lblMoTa.setForeground(new Color(120, 130, 145));

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(lblGiaTri, BorderLayout.CENTER);
        pnl.add(lblMoTa, BorderLayout.SOUTH);

        pnl.setPreferredSize(new Dimension(180, 115));

        return pnl;
    }

    private JPanel taoLichHen() {
        JPanel pnl = new JPanel(new BorderLayout(10, 12));
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(MAU_VIEN),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = new JLabel("LỊCH HẸN HÔM NAY");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 15));
        lblTieuDe.setForeground(MAU_CHU);

        String[] cot = {
            "Mã lịch hẹn",
            "Bệnh nhân",
            "Nha sĩ",
            "Giờ hẹn",
            "Phòng",
            "Trạng thái"
        };

        DefaultTableModel model = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addRow(new Object[] {
            "LH0001", "Nguyễn Văn An",
            "Nguyễn Minh Tuấn", "08:00",
            "Phòng 1", "Đã xác nhận"
        });

        model.addRow(new Object[] {
            "LH0002", "Trần Thị Bích",
            "Trần Hoàng Nam", "09:00",
            "Phòng 2", "Đang chờ"
        });

        model.addRow(new Object[] {
            "LH0003", "Lê Minh Hoàng",
            "Lê Thị Mai", "10:30",
            "Phòng 3", "Đã xác nhận"
        });

        model.addRow(new Object[] {
            "LH0004", "Phạm Thu Hà",
            "Nguyễn Minh Tuấn", "14:00",
            "Phòng 1", "Đang chờ"
        });

        JTable tblLichHen = new JTable(model);
        tblLichHen.setRowHeight(30);
        tblLichHen.setFont(new Font("Arial", Font.PLAIN, 12));
        tblLichHen.setForeground(MAU_CHU);
        tblLichHen.setSelectionBackground(new Color(215, 235, 250));
        tblLichHen.setSelectionForeground(MAU_CHU);
        tblLichHen.setGridColor(new Color(230, 235, 242));
        tblLichHen.setShowVerticalLines(false);
        tblLichHen.setFillsViewportHeight(true);
        tblLichHen.setAutoCreateRowSorter(true);

        tblLichHen.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 12)
        );
        tblLichHen.getTableHeader().setBackground(MAU_CHU);
        tblLichHen.getTableHeader().setForeground(Color.WHITE);
        tblLichHen.getTableHeader().setPreferredSize(
            new Dimension(0, 32)
        );

        JScrollPane scr = new JScrollPane(tblLichHen);
        scr.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(scr, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoHoatDong() {
        JPanel pnl = new JPanel(new BorderLayout(10, 8));
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(MAU_VIEN),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = new JLabel("HOẠT ĐỘNG GẦN ĐÂY");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 14));
        lblTieuDe.setForeground(MAU_CHU);

        JPanel pnlNoiDung = new JPanel(new GridLayout(3, 1, 5, 5));
        pnlNoiDung.setOpaque(false);

        pnlNoiDung.add(taoDongHoatDong(
            "08:15",
            "Tiếp nhận bệnh nhân Nguyễn Văn An"
        ));

        pnlNoiDung.add(taoDongHoatDong(
            "09:00",
            "Tạo lịch hẹn mới cho Trần Thị Bích"
        ));

        pnlNoiDung.add(taoDongHoatDong("09:30","Cập nhật kế hoạch điều trị cho bệnh nhân"));

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(pnlNoiDung, BorderLayout.CENTER);

        pnl.setPreferredSize(new Dimension(500, 140));

        return pnl;
    }

    private JPanel taoDongHoatDong(String gio, String noiDung) {
        JPanel pnl = new JPanel(new BorderLayout(12, 0));
        pnl.setOpaque(false);

        JLabel lblGio = new JLabel(gio);
        lblGio.setFont(new Font("Arial", Font.BOLD, 12));
        lblGio.setForeground(MAU_XANH);
        lblGio.setPreferredSize(new Dimension(65, 22));

        JLabel lblNoiDung = new JLabel(noiDung);
        lblNoiDung.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNoiDung.setForeground(MAU_CHU);

        pnl.add(lblGio, BorderLayout.WEST);
        pnl.add(lblNoiDung, BorderLayout.CENTER);

        return pnl;
    }
}
