
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class KeHoachDieuTriPanel extends JPanel {

    private Color mauChuDao = GiaoDienStyle.MAU_NAVY;
    private Color mauNen = GiaoDienStyle.MAU_NEN;
    private Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JPanel pnlTieuDe;
    private JPanel pnlLoc;
    private JPanel pnlChiTiet;
    private JPanel pnlThongTin;
    private JPanel pnlDichVu;

    private JLabel lblTieuDe;
    private JLabel lblTongChiPhi;

    private JTextField txtTimKiem;
    private JTextField txtTuNgay;
    private JTextField txtDenNgay;

    private JComboBox<String> cboTrangThai;

    private JButton btnThem;
    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnXem;
    private JButton btnSua;

    private JTable tblKeHoach;
    private JTable tblDichVu;

    public KeHoachDieuTriPanel() {
        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(10, 10));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoTieuDe();

        JPanel pnlDanhSach = taoKhuVucDanhSach();
        pnlChiTiet = taoKhuVucChiTiet();

        JSplitPane splNoiDung = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                pnlDanhSach,
                pnlChiTiet
        );

        splNoiDung.setResizeWeight(0.58);
        splNoiDung.setDividerLocation(0.58);
        splNoiDung.setContinuousLayout(true);
        splNoiDung.setOneTouchExpandable(false);
        splNoiDung.setBorder(null);

        add(splNoiDung, BorderLayout.CENTER);
    }

    private void taoTieuDe() {
        pnlTieuDe = new JPanel(new BorderLayout(10, 0));
        pnlTieuDe.setBackground(mauNen);

        lblTieuDe = new JLabel("QUẢN LÝ KẾ HOẠCH ĐIỀU TRỊ");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));
        lblTieuDe.setForeground(mauChuDao);

        btnThem = GiaoDienStyle.taoNut(
                "+ Thêm kế hoạch điều trị",
                GiaoDienStyle.MAU_TEAL,
                210
        );

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlTieuDe.add(btnThem, BorderLayout.EAST);

        add(pnlTieuDe, BorderLayout.NORTH);
    }

    private JPanel taoKhuVucDanhSach() {
        JPanel pnlDanhSach = new JPanel(new BorderLayout(8, 8));
        pnlDanhSach.setBackground(mauNen);

        pnlDanhSach.add(taoBoLoc(), BorderLayout.NORTH);
        pnlDanhSach.add(taoBangKeHoach(), BorderLayout.CENTER);

        return pnlDanhSach;
    }

    private JPanel taoBoLoc() {
        JPanel pnlBoLoc = new JPanel(new BorderLayout());
        pnlBoLoc.setBackground(mauTrang);
        pnlBoLoc.setBorder(
                BorderFactory.createTitledBorder("Tìm kiếm và bộ lọc")
        );

        pnlLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pnlLoc.setBackground(mauTrang);

        txtTimKiem = new JTextField(15);
        txtTimKiem.setPreferredSize(new Dimension(170, 30));

        cboTrangThai = new JComboBox<String>(new String[]{
            "Tất cả trạng thái",
            "Chờ duyệt",
            "Đang điều trị",
            "Hoàn thành",
            "Đã hủy"
        });

        cboTrangThai.setPreferredSize(new Dimension(145, 30));

        txtTuNgay = new JTextField("01/01/2025", 9);
        txtDenNgay = new JTextField("31/12/2025", 9);

        txtTuNgay.setPreferredSize(new Dimension(105, 30));
        txtDenNgay.setPreferredSize(new Dimension(105, 30));

        btnTimKiem = GiaoDienStyle.taoNut(
                "Tìm kiếm", mauChuDao, 110
        );

        btnLamMoi = GiaoDienStyle.taoNut(
                "Làm mới", GiaoDienStyle.MAU_PHU, 100
        );

        pnlLoc.add(new JLabel("Mã KH / Mã BN / Tên BN:"));
        pnlLoc.add(txtTimKiem);
        pnlLoc.add(new JLabel("Trạng thái:"));
        pnlLoc.add(cboTrangThai);
        pnlLoc.add(new JLabel("Từ ngày:"));
        pnlLoc.add(txtTuNgay);
        pnlLoc.add(new JLabel("Đến ngày:"));
        pnlLoc.add(txtDenNgay);
        pnlLoc.add(btnTimKiem);
        pnlLoc.add(btnLamMoi);

        pnlBoLoc.add(pnlLoc, BorderLayout.CENTER);

        return pnlBoLoc;
    }

    private JPanel taoBangKeHoach() {
        JPanel pnlBang = new JPanel(new BorderLayout(5, 5));
        pnlBang.setBackground(mauTrang);
        pnlBang.setBorder(
                BorderFactory.createTitledBorder(
                        "Danh sách kế hoạch điều trị"
                )
        );

        String[] cot = {
            "Mã kế hoạch",
            "Mã BN",
            "Họ tên bệnh nhân",
            "Chẩn đoán",
            "Ngày bắt đầu",
            "Ngày kết thúc",
            "Chi phí dự kiến",
            "Trạng thái"
        };

        Object[][] duLieu = {
            {"KH0001", "BN0001", "Nguyễn Văn An", "Sâu răng",
                "01/03/2025", "15/03/2025", "2.500.000", "Hoàn thành"},
            {"KH0002", "BN0002", "Trần Thị Bích", "Niềng răng",
                "05/01/2025", "05/01/2027", "25.000.000", "Đang điều trị"},
            {"KH0003", "BN0001", "Nguyễn Văn An", "Viêm nướu",
                "10/06/2025", "20/06/2025", "1.200.000", "Hoàn thành"},
            {"KH0004", "BN0004", "Phạm Thu Hà", "Trám răng",
                "12/04/2025", "15/04/2025", "800.000", "Hoàn thành"},
            {"KH0005", "BN0001", "Nguyễn Văn An", "Cạo vôi răng",
                "20/05/2026", "20/05/2026", "500.000", "Chờ duyệt"},
            {"KH0006", "BN0005", "Đỗ Minh Quân", "Nhổ răng",
                "15/08/2025", "15/08/2025", "700.000", "Đã hủy"}
        };

        tblKeHoach = taoBang(cot, duLieu);
        tblKeHoach.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        tblKeHoach.setAutoResizeMode(
                JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
        );
        tblKeHoach.setRowHeight(30);

        int[] doRong = {
            100, 75, 160, 120, 115, 115, 140, 120
        };

        for (int i = 0; i < doRong.length; i++) {
            tblKeHoach.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(doRong[i]);
        }

        JScrollPane scrKeHoach = new JScrollPane(tblKeHoach);
        scrKeHoach.setMinimumSize(new Dimension(500, 150));

       /* JLabel lblPhanTrang = new JLabel(
                "Dữ liệu mẫu: 6 kế hoạch",
                SwingConstants.RIGHT
        );

        lblPhanTrang.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ); */

        pnlBang.add(scrKeHoach, BorderLayout.CENTER);
     //   pnlBang.add(lblPhanTrang, BorderLayout.SOUTH);

        return pnlBang;
    }

    private JPanel taoKhuVucChiTiet() {
        JPanel pnlNoiDung = new JPanel(new BorderLayout(12, 0));
        pnlNoiDung.setBackground(mauNen);

        pnlThongTin = new JPanel(new GridLayout(0, 2, 8, 12));
        pnlThongTin.setBackground(mauTrang);
        pnlThongTin.setBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );

        themThongTin("Mã kế hoạch:", "KH0002");
        themThongTin("Mã bệnh nhân:", "BN0002");
        themThongTin("Bệnh nhân:", "Trần Thị Bích");
        themThongTin("Chẩn đoán:", "Niềng răng");
        themThongTin("Ngày lập:", "05/01/2025");
        themThongTin("Ngày bắt đầu:", "05/01/2025");
        themThongTin("Ngày kết thúc:", "05/01/2027");
        themThongTin("Chi phí dự kiến:", "25.000.000 VNĐ");
        themThongTin("Trạng thái:", "Đang điều trị");

        JPanel pnlNutChiTiet = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 8)
        );
        pnlNutChiTiet.setBackground(mauTrang);

        btnXem = GiaoDienStyle.taoNut(
                "Xem chi tiết", mauChuDao, 120
        );

        btnSua = GiaoDienStyle.taoNut(
                "Sửa kế hoạch", GiaoDienStyle.MAU_TEAL, 120
        );

        pnlNutChiTiet.add(btnXem);
        pnlNutChiTiet.add(btnSua);

        JPanel pnlThongTinChung = new JPanel(
                new BorderLayout(5, 8)
        );
        pnlThongTinChung.setBackground(mauTrang);
        pnlThongTinChung.setBorder(
                BorderFactory.createTitledBorder(
                        "Chi tiết kế hoạch điều trị"
                )
        );

        pnlThongTinChung.add(
                pnlThongTin, BorderLayout.CENTER
        );
        pnlThongTinChung.add(
                pnlNutChiTiet, BorderLayout.SOUTH
        );

        pnlDichVu = new JPanel(new BorderLayout(5, 8));
        pnlDichVu.setBackground(mauTrang);
        pnlDichVu.setBorder(
                BorderFactory.createTitledBorder(
                        "Dịch vụ trong kế hoạch"
                )
        );

        String[] cotDichVu = {
            "STT", "Mã DV", "Tên dịch vụ",
            "SL", "Đơn giá", "Thành tiền"
        };

        Object[][] duLieuDichVu = {
            {"1", "DV001", "Khám và tư vấn", "1", "500.000", "500.000"},
            {"2", "DV002", "Niềng răng", "1", "20.000.000", "20.000.000"},
            {"3", "DV003", "Chụp X-Quang", "1", "4.500.000", "4.500.000"}
        };

        tblDichVu = taoBang(cotDichVu, duLieuDichVu);
        tblDichVu.setAutoResizeMode(
                JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
        );
        tblDichVu.setRowHeight(28);

        int[] doRongDichVu = {
            40, 65, 140, 40, 100, 110
        };

        for (int i = 0; i < doRongDichVu.length; i++) {
            tblDichVu.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(doRongDichVu[i]);
        }

        JScrollPane scrDichVu = new JScrollPane(tblDichVu);
        scrDichVu.setMinimumSize(new Dimension(250, 150));

        lblTongChiPhi = new JLabel(
                "Tổng chi phí dự kiến: 25.000.000 VNĐ",
                SwingConstants.RIGHT
        );

        lblTongChiPhi.setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        lblTongChiPhi.setForeground(mauChuDao);
        lblTongChiPhi.setBorder(
                BorderFactory.createEmptyBorder(10, 5, 10, 10)
        );

        pnlDichVu.add(scrDichVu, BorderLayout.CENTER);
        pnlDichVu.add(lblTongChiPhi, BorderLayout.SOUTH);

        JSplitPane splChiTiet = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                pnlThongTinChung,
                pnlDichVu
        );

        splChiTiet.setResizeWeight(0.45);
        splChiTiet.setContinuousLayout(true);
        splChiTiet.setOneTouchExpandable(false);
        splChiTiet.setBorder(null);

        pnlNoiDung.add(splChiTiet, BorderLayout.CENTER);

        return pnlNoiDung;
    }

    private void themThongTin(String ten, String giaTri) {
        JLabel lblTen = new JLabel(ten);
        lblTen.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel lblGiaTri = new JLabel(giaTri);
        lblGiaTri.setFont(new Font("Arial", Font.PLAIN, 12));

        pnlThongTin.add(lblTen);
        pnlThongTin.add(lblGiaTri);
    }

    private JTable taoBang(String[] cot, Object[][] duLieu) {
        DefaultTableModel dtm = new DefaultTableModel(
                duLieu, cot
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tbl = new JTable(dtm);
        GiaoDienStyle.dinhDangBang(tbl);

        return tbl;
    }
}
