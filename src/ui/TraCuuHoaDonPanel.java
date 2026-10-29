
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import dao.ChiTietHoaDon_DAO;
import dao.HoaDonDao;
import model.ChiTietHoaDon;
import model.HoaDon;
import utils.GiaoDienStyle;

public class TraCuuHoaDonPanel extends JPanel implements ActionListener, MouseListener {

    private HoaDonDao hoadonDAO;
    private Color mauChuDao = GiaoDienStyle.MAU_NAVY;
    private Color mauNen = GiaoDienStyle.MAU_NEN;
    private Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JTextField txtTimKiem;
    private JComboBox<String> cboTrangThai;
    private JDateChooser dchTuNgay;
    private JDateChooser dchDenNgay;

    private JTable tblHoaDon;
    private JTable tblDichVu;
    private DefaultTableModel modelHoaDon;
    private DefaultTableModel modelDichVu;

    private JLabel lblMaHoaDon;
    private JLabel lblMaLichHen;
    private JLabel lblTenBenhNhan;
    private JLabel lblTenNhanVienChinh;
    private JLabel lblNgayLap;
    private JLabel lblChietKhau;
    private JLabel lblTrangThai;
    private JLabel lblSoDT;
    private JLabel lblTongTien;

    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnInHoaDon;
    private ChiTietHoaDon_DAO chiTietHoaDonDao ;
    private NumberFormat dinhDangTien = NumberFormat.getNumberInstance(Locale.of("vi", "VN"));

    public TraCuuHoaDonPanel() {
        hoadonDAO = new HoaDonDao();
        chiTietHoaDonDao = new ChiTietHoaDon_DAO();
        khoiTaoGiaoDien();
        dangKySuKien();
        taiDanhSachHoaDon();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(8, 8));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pnlBoLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        pnlBoLoc.setBackground(mauTrang);
        pnlBoLoc.setBorder(BorderFactory.createTitledBorder("Tìm kiếm và bộ lọc"));

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

        btnTimKiem = taoNut("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(95, 27));
        pnlBoLoc.add(btnTimKiem);

        btnLamMoi = taoNut("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(95, 27));
        pnlBoLoc.add(btnLamMoi);

        String[] cotHoaDon = {"Mã hóa đơn", "Mã Bệnh Nhân", "Mã lịch hẹn", "Ngày lập", "Tổng tiền", "Chiết khấu", "Thanh toán", "Trạng thái"};

        Object[][] duLieuHoaDon = {};

        modelHoaDon = new DefaultTableModel(duLieuHoaDon, cotHoaDon) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblHoaDon = new JTable(modelHoaDon);
        GiaoDienStyle.dinhDangBang(tblHoaDon);
        tblHoaDon.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblHoaDon.setRowHeight(28);

        JPanel pnlBang = new JPanel(new BorderLayout());
        pnlBang.setBackground(mauNen);
        pnlBang.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn"));
        pnlBang.add(new JScrollPane(tblHoaDon), BorderLayout.CENTER);

        JPanel pnlThongTin = new JPanel(new GridLayout(0, 2, 8, 8));
        pnlThongTin.setBackground(mauTrang);
        pnlThongTin.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        lblMaHoaDon = themThongTin(pnlThongTin, "Mã hóa đơn:");
        // lblMaLichHen = themThongTin(pnlThongTin, "Mã lịch hẹn:");
        lblTenBenhNhan = themThongTin(pnlThongTin, "Tên bệnh nhân:");
        lblTenNhanVienChinh = themThongTin(pnlThongTin, "Tên nhân viên :");
        lblNgayLap = themThongTin(pnlThongTin, "Ngày lập:");
        lblChietKhau = themThongTin(pnlThongTin, "Chiết khấu:");
        lblTrangThai = themThongTin(pnlThongTin, "Trạng thái:");
        lblSoDT = themThongTin(pnlThongTin, "Số điện thoại:");

        String[] cotDichVu = {"STT", "Tên dịch vụ", "Số lượng", "Đơn giá", "Thành tiền"};

        modelDichVu = new DefaultTableModel(cotDichVu, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(modelDichVu);
        GiaoDienStyle.dinhDangBang(tblDichVu);
        tblDichVu.setRowHeight(26);

        JPanel pnlDichVu = new JPanel(new BorderLayout(5, 5));
        pnlDichVu.setBackground(mauTrang);
        pnlDichVu.setBorder(BorderFactory.createTitledBorder("Danh sách dịch vụ"));
        pnlDichVu.add(new JScrollPane(tblDichVu), BorderLayout.CENTER);

        lblTongTien = new JLabel("Tổng tiền dịch vụ: 0 VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTien.setForeground(mauChuDao);

        JPanel pnlTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTongTien.setBackground(mauTrang);
        pnlTongTien.add(lblTongTien);
        pnlDichVu.add(pnlTongTien, BorderLayout.SOUTH);

        JPanel pnlChiTiet = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlChiTiet.setBackground(mauNen);
        pnlChiTiet.add(new JScrollPane(pnlThongTin));
        pnlChiTiet.add(pnlDichVu);

        btnInHoaDon = taoNut("In hóa đơn");
        btnInHoaDon.setPreferredSize(new Dimension(120, 32));

        JPanel pnlNut = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlNut.setBackground(mauNen);
        pnlNut.add(btnInHoaDon);

        JPanel pnlDuoi = new JPanel(new BorderLayout(5, 5));
        pnlDuoi.setBackground(mauNen);
        pnlDuoi.setPreferredSize(new Dimension(0, 280));
        pnlDuoi.add(pnlChiTiet, BorderLayout.CENTER);
        pnlDuoi.add(pnlNut, BorderLayout.SOUTH);

        JPanel pnlNoiDung = new JPanel(new BorderLayout(8, 8));
        pnlNoiDung.setBackground(mauNen);
        pnlNoiDung.add(pnlBoLoc, BorderLayout.NORTH);
        pnlNoiDung.add(pnlBang, BorderLayout.CENTER);
        pnlNoiDung.add(pnlDuoi, BorderLayout.SOUTH);

        add(pnlNoiDung, BorderLayout.CENTER);
    }

    private JLabel themThongTin(JPanel pnl, String ten) {
        JLabel lblTen = new JLabel(ten);
        lblTen.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel lblGiaTri = new JLabel("");
        lblGiaTri.setFont(new Font("Arial", Font.PLAIN, 12));

        pnl.add(lblTen);
        pnl.add(lblGiaTri);

        return lblGiaTri;
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

    private void dangKySuKien() {
        btnTimKiem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnInHoaDon.addActionListener(this);

        tblHoaDon.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object nguon = e.getSource();

        if (nguon == btnTimKiem) {
            xuLyTimKiem();
        } else if (nguon == btnLamMoi) {
            xuLyLamMoi();
        } else if (nguon == btnInHoaDon) {
            xuLyInHoaDon();
        }
    }
    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        List<HoaDon> ds = hoadonDAO.search(tuKhoa);
        doDuLieuHoaDon(ds);
    }
    private void doDuLieuHoaDon(List<HoaDon> ds) {
        modelHoaDon.setRowCount(0);
        for (HoaDon hd : ds) {
            String maBN = hd.getMaBenhNhan() == null ? "" : hd.getMaBenhNhan().getMaBenhNhan();
            String maLH = hd.getMaLichHen() == null ? "" : hd.getMaLichHen().getMaLichHen();
            String ngayLap = hd.getNgayLap() == null ? "" : hd.getNgayLap().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            double tongTien = 0;
            try {
                tongTien = chiTietHoaDonDao.tinhTongTien(hd.getMaHD());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            double canThanhToan = hd.tinhTienSauGiam(tongTien) * (1 + hd.getThueVAT());
            modelHoaDon.addRow(new Object[] {
                    hd.getMaHD(),
                    maBN,
                    maLH,
                    ngayLap,
                    dinhDangTien.format(tongTien),
                    dinhDangTien.format(hd.tinhTienGiamGia(tongTien)),
                    dinhDangTien.format(canThanhToan),
                    hd.getTrangThai()
            });
        }
    }


    private void xuLyLamMoi() {
        txtTimKiem.setText("");
        cboTrangThai.setSelectedIndex(0);
        dchTuNgay.setDate(null);
        dchDenNgay.setDate(null);
        xoaThongTinChiTiet();
        taiDanhSachHoaDon();
    }



    private void xuLyInHoaDon() {

    }

    private void taiDanhSachHoaDon() {
        List<HoaDon> ds = hoadonDAO.search("");
        doDuLieuHoaDon(ds);
    }
    private void taiChiTietHoaDon(String maHD) {
        try {
            List<HoaDon> dsHoaDon = hoadonDAO.getAll();
            HoaDon hd = null;

            for (HoaDon hoaDon : dsHoaDon) {
                if (hoaDon.getMaHD().equals(maHD)) {
                    hd = hoaDon;
                    break;
                }
            }

            if (hd == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn.");
                return;
            }

            lblMaHoaDon.setText(hd.getMaHD());
            lblTenBenhNhan.setText(hd.getMaBenhNhan().getHoTen());
            lblTenNhanVienChinh.setText(hd.getMaNV().getHoTen());
            lblNgayLap.setText(
                    hd.getNgayLap() == null
                            ? ""
                            : hd.getNgayLap().format(
                            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    )
            );
            lblChietKhau.setText(dinhDangTien.format(hd.getChietKhau()) + "%");
            lblTrangThai.setText(hd.getTrangThai());
            lblSoDT.setText(hd.getMaBenhNhan().getSoDT());

            List<ChiTietHoaDon> dsChiTiet =
                    chiTietHoaDonDao.getChiTietHoaDon(maHD);

            modelDichVu.setRowCount(0);

            int stt = 1;
            for (ChiTietHoaDon ct : dsChiTiet) {
                double thanhTien = ct.getSoLuong() * ct.getDonGia();

                modelDichVu.addRow(new Object[] {
                        stt++,
                        ct.getMaDV().getTenDV(),
                        ct.getSoLuong(),
                        dinhDangTien.format(ct.getDonGia()),
                        dinhDangTien.format(thanhTien)
                });
            }

            double tongTien = chiTietHoaDonDao.tinhTongTien(maHD);
            double tienSauGiam = hd.tinhTienSauGiam(tongTien);
            double tongThanhToan = tienSauGiam * (1 + hd.getThueVAT());

            lblTongTien.setText(
                    "Tổng tiền dịch vụ: " + dinhDangTien.format(tongTien) + " VNĐ"
                            + " | Thanh toán: " + dinhDangTien.format(tongThanhToan) + " VNĐ"
            );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải chi tiết hóa đơn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }



    private void xoaThongTinChiTiet() {
        lblMaHoaDon.setText("");
        lblTenBenhNhan.setText("");
        lblTenNhanVienChinh.setText("");
        lblNgayLap.setText("");
        lblChietKhau.setText("");
        lblTrangThai.setText("");
        lblSoDT.setText("");
        modelDichVu.setRowCount(0);
        lblTongTien.setText("Tổng tiền dịch vụ: 0 VNĐ");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            return;
        }
        String maHD = modelHoaDon.getValueAt(row, 0).toString();
        taiChiTietHoaDon(maHD);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}