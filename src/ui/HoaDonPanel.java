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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import utils.GiaoDienStyle;

public class HoaDonPanel extends JPanel {

    private Color mauChuDao = GiaoDienStyle.MAU_NAVY;
    private Color mauNen = GiaoDienStyle.MAU_NEN;
    private Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JTextField txtTimKiem;
    private JComboBox<String> cboTrangThai;
    private JDateChooser dchTuNgay;
    private JDateChooser dchDenNgay;

    public HoaDonPanel() {
        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(8, 8));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // BO LOC
        JPanel pnlBoLoc = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 4, 4));

        pnlBoLoc.setBackground(mauTrang);
        pnlBoLoc.setBorder(
                BorderFactory.createTitledBorder("Tìm kiếm và bộ lọc"));

        txtTimKiem = new JTextField(9);
        pnlBoLoc.add(new JLabel("Mã HĐ / Mã BN / Tên BN:"));
        pnlBoLoc.add(txtTimKiem);

        cboTrangThai = new JComboBox<>(new String[] {
                "Tất cả",
                "Đã thanh toán",
                "Chưa thanh toán"
        });
        cboTrangThai.setPreferredSize(new Dimension(120, 27));

        pnlBoLoc.add(new JLabel("Trạng thái:"));
        pnlBoLoc.add(cboTrangThai);

        dchTuNgay = new JDateChooser();
        dchTuNgay.setDateFormatString("dd/MM/yyyy");
        dchTuNgay.setPreferredSize(new Dimension(100, 27));

        pnlBoLoc.add(new JLabel("Từ ngày:"));
        pnlBoLoc.add(dchTuNgay);

        dchDenNgay = new JDateChooser();
        dchDenNgay.setDateFormatString("dd/MM/yyyy");
        dchDenNgay.setPreferredSize(new Dimension(100, 27));

        pnlBoLoc.add(new JLabel("Đến ngày:"));
        pnlBoLoc.add(dchDenNgay);

        JButton btnTimKiem = taoNut("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(95, 27));
        pnlBoLoc.add(btnTimKiem);

        // DANH SACH HOA DON
        String[] cotHoaDon = {
                "Mã hóa đơn",
                "Mã BN",
                "Tên bệnh nhân",
                "Ngày lập",
                "Tổng tiền",
                "Chiết khấu",
                "Thanh toán",
                "Trạng thái"
        };

        Object[][] duLieuHoaDon = {
                {"HD0001", "BN0001", "Nguyễn Văn An", "10/01/2025",
                        "2.000.000", "0%", "2.000.000", "Đã thanh toán"},
                {"HD0002", "BN0002", "Trần Thị Bích", "12/01/2025",
                        "3.800.000", "5%", "3.610.000", "Chưa thanh toán"},
                {"HD0003", "BN0003", "Lê Minh Hoàng", "15/01/2025",
                        "1.500.000", "0%", "1.500.000", "Đã thanh toán"},
                {"HD0004", "BN0004", "Phạm Thu Hà", "20/01/2025",
                        "900.000", "0%", "900.000", "Đã thanh toán"},
                {"HD0005", "BN0005", "Đỗ Minh Quân", "25/01/2025",
                        "2.200.000", "10%", "1.980.000", "Chưa thanh toán"}
        };

        JTable tblHoaDon = taoBang(cotHoaDon, duLieuHoaDon);
        tblHoaDon.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblHoaDon.setRowHeight(28);

        JScrollPane scrHoaDon = new JScrollPane(tblHoaDon);

        JPanel pnlBang = new JPanel(new BorderLayout());
        pnlBang.setBackground(mauNen);
        pnlBang.setBorder(
                BorderFactory.createTitledBorder("Danh sách hóa đơn"));
        pnlBang.add(scrHoaDon, BorderLayout.CENTER);

        // THONG TIN HOA DON
        JPanel pnlThongTin = new JPanel(
                new GridLayout(0, 2, 8, 8));

        pnlThongTin.setBackground(mauTrang);
        pnlThongTin.setBorder(
                BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        themThongTin(pnlThongTin, "Mã hóa đơn:", "HD0003");
        themThongTin(pnlThongTin, "Mã bệnh nhân:", "BN0003");
        themThongTin(pnlThongTin, "Tên bệnh nhân:", "Lê Minh Hoàng");
        themThongTin(pnlThongTin, "Mã nhân viên:", "NV0001");
        themThongTin(pnlThongTin, "Ngày lập:", "15/01/2025");
        themThongTin(pnlThongTin, "Chiết khấu:", "5%");
        themThongTin(pnlThongTin, "Trạng thái:", "Chưa thanh toán");
        themThongTin(pnlThongTin, "Ghi chú:", "Thanh toán tại quầy");

        // DANH SACH DICH VU
        String[] cotDichVu = {
                "STT",
                "Tên dịch vụ",
                "Số lượng",
                "Đơn giá",
                "Thành tiền"
        };

        Object[][] duLieuDichVu = {
                {"1", "Chụp X-Quang", "1", "800.000", "800.000"},
                {"2", "Cạo vôi răng", "1", "500.000", "500.000"},
                {"3", "Trám răng", "2", "1.250.000", "2.500.000"}
        };

        JTable tblDichVu = taoBang(cotDichVu, duLieuDichVu);
        tblDichVu.setRowHeight(26);

        JPanel pnlDichVu = new JPanel(new BorderLayout(5, 5));
        pnlDichVu.setBackground(mauTrang);
        pnlDichVu.setBorder(
                BorderFactory.createTitledBorder("Danh sách dịch vụ"));

        pnlDichVu.add(
                new JScrollPane(tblDichVu),
                BorderLayout.CENTER);

        JLabel lblTongTien = new JLabel(
                "Tổng tiền dịch vụ: 3.800.000 VNĐ");

        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTien.setForeground(mauChuDao);

        JPanel pnlTongTien = new JPanel(
                new FlowLayout(FlowLayout.LEFT));

        pnlTongTien.setBackground(mauTrang);
        pnlTongTien.add(lblTongTien);

        pnlDichVu.add(pnlTongTien, BorderLayout.SOUTH);

        // GHEP THONG TIN VA DICH VU
        JPanel pnlChiTiet = new JPanel(
                new GridLayout(1, 2, 10, 0));

        pnlChiTiet.setBackground(mauNen);
        pnlChiTiet.add(new JScrollPane(pnlThongTin));
        pnlChiTiet.add(pnlDichVu);

        // CHIA KHONG GIAN: DANH SACH O TREN, CHI TIET O DUOI
        JPanel pnlNoiDung = new JPanel(new BorderLayout(8, 8));
        pnlNoiDung.setBackground(mauNen);

        pnlNoiDung.add(pnlBoLoc, BorderLayout.NORTH);
        pnlNoiDung.add(pnlBang, BorderLayout.CENTER);

        JPanel pnlDuoi = new JPanel(new BorderLayout());
        pnlDuoi.setBackground(mauNen);
        pnlDuoi.setPreferredSize(new Dimension(0, 280));
        pnlDuoi.add(pnlChiTiet, BorderLayout.CENTER);

        pnlNoiDung.add(pnlDuoi, BorderLayout.SOUTH);

        add(pnlNoiDung, BorderLayout.CENTER);
    }

    private void themThongTin(
            JPanel pnl,
            String ten,
            String giaTri) {

        JLabel lblTen = new JLabel(ten);
        lblTen.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel lblGiaTri = new JLabel(giaTri);
        lblGiaTri.setFont(new Font("Arial", Font.PLAIN, 12));

        pnl.add(lblTen);
        pnl.add(lblGiaTri);
    }

    private JTable taoBang(String[] cot, Object[][] duLieu) {
        DefaultTableModel dtm = new DefaultTableModel(duLieu, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tbl = new JTable(dtm);
        GiaoDienStyle.dinhDangBang(tbl);

        return tbl;
    }

    private JButton taoNut(String ten) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(mauChuDao);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(95, 27));

        return btn;
    }
}