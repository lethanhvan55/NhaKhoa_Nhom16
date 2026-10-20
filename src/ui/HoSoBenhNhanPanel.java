
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class HoSoBenhNhanPanel extends JPanel {

    private Color mauChuDao = GiaoDienStyle.MAU_NAVY;
    private Color mauNen = GiaoDienStyle.MAU_NEN;
    private Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JTable tblBenhNhan;
    private DefaultTableModel dtm;

    private JTextField txtTimKiem;
    private JComboBox<String> cboGioiTinh;
    private JDateChooser dcTuNgay;
    private JDateChooser dcDenNgay;

    private JTextField txtMaBN;
    private JTextField txtHoTen;
    private JDateChooser dcNgaySinh;
    private JComboBox<String> cboGioiTinhChiTiet;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private JTextField txtDiaChi;
    private JTextArea txtGhiChu;

    public HoSoBenhNhanPanel() {
        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(10, 10));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

   /*     JLabel lblTieuDe = new JLabel("QUẢN LÝ HỒ SƠ BỆNH NHÂN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));
        lblTieuDe.setForeground(mauChuDao);
        lblTieuDe.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        add(lblTieuDe, BorderLayout.NORTH); */

        JPanel pnlDanhSach = taoDanhSachBenhNhan();
        JPanel pnlChiTiet = taoChiTietBenhNhan();

        JSplitPane splNoiDung = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT, pnlDanhSach, pnlChiTiet
        );

        splNoiDung.setResizeWeight(0.55);
        splNoiDung.setDividerLocation(650);
        splNoiDung.setContinuousLayout(true);
        splNoiDung.setBorder(null);

        add(splNoiDung, BorderLayout.CENTER);
    }

    private JPanel taoDanhSachBenhNhan() {
        JPanel pnlDanhSach = new JPanel(new BorderLayout(8, 8));
        pnlDanhSach.setBackground(mauTrang);
        pnlDanhSach.setBorder(BorderFactory.createTitledBorder("Danh sách bệnh nhân"));

        String[] cot = {"Mã BN", "Họ tên", "Ngày sinh", "Giới tính", "SĐT", "Địa chỉ"};

        Object[][] duLieu = {
            {"BN0001", "Nguyễn Văn An", "20/05/2003", "Nam", "0912345678", "Gò Vấp"},
            {"BN0002", "Trần Thị Bích", "15/08/2002", "Nữ", "0987654321", "Quận 12"},
            {"BN0003", "Lê Minh Hoàng", "12/04/2000", "Nam", "0901234567", "Tân Bình"},
            {"BN0004", "Phạm Thu Hà", "30/09/1999", "Nữ", "0934567890", "Gò Vấp"},
            {"BN0005", "Đỗ Minh Quân", "02/01/2001", "Nam", "0978123456", "Hóc Môn"},
            {"BN0006", "Võ Ngọc Mai", "18/11/2004", "Nữ", "0967891234", "Bình Thạnh"}
        };

        dtm = new DefaultTableModel(duLieu, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblBenhNhan = new JTable(dtm);
        GiaoDienStyle.dinhDangBang(tblBenhNhan);
        tblBenhNhan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrBang = new JScrollPane(tblBenhNhan);
        scrBang.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        pnlDanhSach.add(scrBang, BorderLayout.CENTER);
        pnlDanhSach.add(taoKhungTimKiem(), BorderLayout.SOUTH);

        return pnlDanhSach;
    }

    private JPanel taoKhungTimKiem() {
        JPanel pnlLoc = new JPanel(new GridBagLayout());
        pnlLoc.setBackground(mauTrang);
        pnlLoc.setBorder(BorderFactory.createTitledBorder("Tìm kiếm và lọc bệnh nhân"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(180, 30));

        cboGioiTinh = new JComboBox<String>(new String[]{"Tất cả", "Nam", "Nữ"});

        dcTuNgay = new JDateChooser();
        dcTuNgay.setDateFormatString("dd/MM/yyyy");
        dcTuNgay.setPreferredSize(new Dimension(150, 30));

        dcDenNgay = new JDateChooser();
        dcDenNgay.setDateFormatString("dd/MM/yyyy");
        dcDenNgay.setPreferredSize(new Dimension(150, 30));

        javax.swing.JButton btnTimKiem = GiaoDienStyle.taoNut(
            "Tìm kiếm", GiaoDienStyle.MAU_NAVY, 120
        );

        javax.swing.JButton btnLamMoi = GiaoDienStyle.taoNut(
            "Làm mới", GiaoDienStyle.MAU_PHU, 120
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        pnlLoc.add(new JLabel("Mã BN / Họ tên / SĐT:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        pnlLoc.add(txtTimKiem, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        pnlLoc.add(new JLabel("Giới tính:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        pnlLoc.add(cboGioiTinh, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        pnlLoc.add(new JLabel("Ngày sinh từ:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        pnlLoc.add(dcTuNgay, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        pnlLoc.add(new JLabel("Đến ngày:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        pnlLoc.add(dcDenNgay, gbc);

        JPanel pnlNut = new JPanel();
        pnlNut.setBackground(mauTrang);
        pnlNut.add(btnTimKiem);
        pnlNut.add(btnLamMoi);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        pnlLoc.add(pnlNut, gbc);

        return pnlLoc;
    }

    private JPanel taoChiTietBenhNhan() {
        JPanel pnlChiTiet = new JPanel(new BorderLayout(5, 5));
        pnlChiTiet.setBackground(mauTrang);
        pnlChiTiet.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));

        JTabbedPane tabThongTin = new JTabbedPane();

        tabThongTin.addTab("Thông tin bệnh nhân", taoTabThongTin());
        tabThongTin.addTab("Hồ sơ bệnh án", taoTabTam("Danh sách hồ sơ bệnh án"));
        tabThongTin.addTab("Kế hoạch điều trị", taoTabTam("Kế hoạch điều trị của bệnh nhân"));
        tabThongTin.addTab("Lịch hẹn", taoTabTam("Lịch hẹn của bệnh nhân"));

        JPanel pnlNut = new JPanel();
        pnlNut.setBackground(mauTrang);

        pnlNut.add(GiaoDienStyle.taoNut("Lưu", GiaoDienStyle.MAU_TEAL, 110));
        pnlNut.add(GiaoDienStyle.taoNut("Xóa", GiaoDienStyle.MAU_DO, 110));

        pnlChiTiet.add(tabThongTin, BorderLayout.CENTER);
        pnlChiTiet.add(pnlNut, BorderLayout.SOUTH);

        return pnlChiTiet;
    }

    private JPanel taoTabThongTin() {
        JPanel pnlThongTin = new JPanel(new GridBagLayout());
        pnlThongTin.setBackground(mauTrang);

        txtMaBN = new JTextField();
        txtHoTen = new JTextField();

        dcNgaySinh = new JDateChooser();
        dcNgaySinh.setDateFormatString("dd/MM/yyyy");

        cboGioiTinhChiTiet = new JComboBox<String>(new String[]{"Nam", "Nữ"});

        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();

        txtGhiChu = new JTextArea(8, 25);
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        txtGhiChu.setFont(new Font("Arial", Font.PLAIN, 13));

        themTruong(pnlThongTin, "Mã bệnh nhân:", txtMaBN, 0);
        themTruong(pnlThongTin, "Họ và tên:", txtHoTen, 1);
        themTruong(pnlThongTin, "Ngày sinh:", dcNgaySinh, 2);
        themTruong(pnlThongTin, "Giới tính:", cboGioiTinhChiTiet, 3);
        themTruong(pnlThongTin, "Số điện thoại:", txtSDT, 4);
        themTruong(pnlThongTin, "Email:", txtEmail, 5);
        themTruong(pnlThongTin, "Địa chỉ:", txtDiaChi, 6);
        themTruong(pnlThongTin, "Ghi chú:", new JScrollPane(txtGhiChu), 7);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlThongTin.add(new JPanel(), gbc);

        JScrollPane scrThongTin = new JScrollPane(pnlThongTin);
        scrThongTin.setBorder(BorderFactory.createEmptyBorder());

        JPanel pnlChuaThongTin = new JPanel(new BorderLayout());
        pnlChuaThongTin.setBackground(mauTrang);
        pnlChuaThongTin.add(scrThongTin, BorderLayout.CENTER);

        return pnlChuaThongTin;
    }

    private void themTruong(JPanel pnl, String ten, Component control, int hang) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = hang;
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel lbl = new JLabel(ten);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        pnl.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (ten.equals("Ghi chú:")) {
            control.setPreferredSize(new Dimension(250, 150));
        } else {
            control.setPreferredSize(new Dimension(170, 30));
        }

        pnl.add(control, gbc);
    }

    private JPanel taoTabTam(String noiDung) {
        JPanel pnlTam = new JPanel(new BorderLayout());
        pnlTam.setBackground(mauTrang);

        JLabel lblNoiDung = new JLabel(noiDung);
        lblNoiDung.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNoiDung.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnlTam.add(lblNoiDung, BorderLayout.NORTH);

        return pnlTam;
    }
}
