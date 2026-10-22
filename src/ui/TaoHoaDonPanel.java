package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class TaoHoaDonPanel extends JPanel implements ActionListener, MouseListener {

    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;
    private final Color MAU_DO = GiaoDienStyle.MAU_DO;

    private JTable tblLichHen;
    private JTable tblDichVu;
    private DefaultTableModel modelLichHen;
    private DefaultTableModel modelDichVu;

    private JTextField txtMaLichHen;
    private JTextField txtMaBenhNhan;
    private JTextField txtTenKhachHang;
    private JTextField txtSoDienThoai;
    private JTextField txtNgayHen;
    private JTextField txtGioHen;
    private JTextField txtSoKhach;
    private JTextField txtGhiChu;

    private JTextField txtMaGiamGia;
    private JTextField txtTongTien;
    private JTextField txtChietKhau;
    private JTextField txtThue;
    private JTextField txtTienCoc;
    private JTextField txtCanThanhToan;

    private JButton btnApDungMa;
    private JButton btnTienMat;
    private JButton btnChuyenKhoan;
    private JButton btnThanhToan;

    public TaoHoaDonPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachLichHenTuCSDL();
    }

    private void taoGiaoDien() {
        JPanel pnlBenTrai = taoBenTrai();
        JPanel pnlBenPhai = taoBenPhai();

        JSplitPane splitTong = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                pnlBenTrai,
                pnlBenPhai
        );

        splitTong.setResizeWeight(0.57);
        splitTong.setDividerLocation(650);
        splitTong.setDividerSize(8);
        splitTong.setBorder(null);
        splitTong.setBackground(MAU_NEN);

        add(splitTong, BorderLayout.CENTER);
    }


    private JPanel taoBenTrai() {
        JPanel pnlBenTrai = new JPanel(new BorderLayout(0, 0));
        pnlBenTrai.setBackground(MAU_NEN);

        JPanel pnlDanhSach = taoKhuVucDanhSachLichHen();
        JPanel pnlDichVu = taoKhuVucDichVu();

        JSplitPane splitDuoi = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                pnlDanhSach,
                pnlDichVu
        );

        splitDuoi.setResizeWeight(0.50);
        splitDuoi.setDividerLocation(340);
        splitDuoi.setDividerSize(7);
        splitDuoi.setBorder(null);
        splitDuoi.setBackground(MAU_NEN);

        pnlBenTrai.add(splitDuoi, BorderLayout.CENTER);

        return pnlBenTrai;
    }

    private JPanel taoKhuVucDanhSachLichHen() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Danh sách hóa đơn thanh toán");

        String[] cot = {
                "Mã lịch hẹn",
                "Tên khách hàng",
                "Số điện thoại",
                "Ngày hẹn",
                "Giờ",
                "Trạng thái"
        };

        modelLichHen = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLichHen = new JTable(modelLichHen);
        tblLichHen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblLichHen.setRowHeight(32);
        tblLichHen.setFont(new Font("Arial", Font.PLAIN, 12));
        tblLichHen.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblLichHen.getTableHeader().setBackground(MAU_NAVY);
        tblLichHen.getTableHeader().setForeground(Color.WHITE);
        tblLichHen.setSelectionBackground(new Color(220, 235, 250));
        tblLichHen.setSelectionForeground(Color.BLACK);
        GiaoDienStyle.dinhDangBang(tblLichHen);

        JScrollPane scrDanhSach = new JScrollPane(tblLichHen);
        scrDanhSach.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(scrDanhSach, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoKhuVucDichVu() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Dịch vụ trong lịch hẹn");

        String[] cot = {
                "STT",
                "Tên dịch vụ",
                "Số lượng",
                "Đơn giá",
                "Thành tiền"
        };

        modelDichVu = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(modelDichVu);
        tblDichVu.setRowHeight(30);
        tblDichVu.setFont(new Font("Arial", Font.PLAIN, 12));
        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblDichVu.getTableHeader().setBackground(MAU_NAVY);
        tblDichVu.getTableHeader().setForeground(Color.WHITE);
        GiaoDienStyle.dinhDangBang(tblDichVu);

        JScrollPane scrDichVu = new JScrollPane(tblDichVu);
        scrDichVu.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(scrDichVu, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoBenPhai() {
        JPanel pnlBenPhai = new JPanel(new BorderLayout(0, 10));
        pnlBenPhai.setBackground(MAU_NEN);

        JPanel pnlThongTin = taoThongTinKhachHang();
        JPanel pnlThanhToan = taoKhuVucThanhToan();

        JSplitPane splitPhai = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                pnlThongTin,
                pnlThanhToan
        );

        splitPhai.setResizeWeight(0.48);
        splitPhai.setDividerLocation(300);
        splitPhai.setDividerSize(7);
        splitPhai.setBorder(null);
        splitPhai.setBackground(MAU_NEN);

        pnlBenPhai.add(splitPhai, BorderLayout.CENTER);

        return pnlBenPhai;
    }

    private JPanel taoThongTinKhachHang() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin khách hàng");

        JPanel pnlThongTin = new JPanel(new GridLayout(0, 2, 10, 9));
        pnlThongTin.setBackground(MAU_TRANG);

        txtMaLichHen = taoOThongTin("");
        txtMaBenhNhan = taoOThongTin("");
        txtTenKhachHang = taoOThongTin("");
        txtSoDienThoai = taoOThongTin("");
        txtNgayHen = taoOThongTin("");
        txtGioHen = taoOThongTin("");
        txtSoKhach = taoOThongTin("");
        txtGhiChu = taoOThongTin("");

        themTruong(pnlThongTin, "Mã lịch hẹn", txtMaLichHen);
        themTruong(pnlThongTin, "Mã bệnh nhân", txtMaBenhNhan);
        themTruong(pnlThongTin, "Tên khách hàng", txtTenKhachHang);
        themTruong(pnlThongTin, "Số điện thoại", txtSoDienThoai);
        themTruong(pnlThongTin, "Ngày hẹn", txtNgayHen);
        themTruong(pnlThongTin, "Giờ hẹn", txtGioHen);
        themTruong(pnlThongTin, "Số khách", txtSoKhach);
        themTruong(pnlThongTin, "Ghi chú", txtGhiChu);

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(pnlThongTin, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoKhuVucThanhToan() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 8));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin hóa đơn");

        JPanel pnlMaGiamGia = new JPanel(new BorderLayout(6, 0));
        pnlMaGiamGia.setBackground(MAU_TRANG);

        txtMaGiamGia = new JTextField();
        txtMaGiamGia.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMaGiamGia.setPreferredSize(new Dimension(100, 32));
        txtMaGiamGia.setToolTipText("Nhập mã giảm giá");

        btnApDungMa = taoNut("Áp dụng", MAU_TEAL);

        pnlMaGiamGia.add(txtMaGiamGia, BorderLayout.CENTER);
        pnlMaGiamGia.add(btnApDungMa, BorderLayout.EAST);

        JPanel pnlChiTietTien = new JPanel(new GridLayout(0, 2, 8, 7));
        pnlChiTietTien.setBackground(MAU_TRANG);

        txtTongTien = taoOThongTin("0 VNĐ");
        txtChietKhau = taoOThongTin("0 VNĐ");
        txtThue = taoOThongTin("0 VNĐ");
        txtTienCoc = taoOThongTin("0 VNĐ");
        txtCanThanhToan = taoOThongTin("0 VNĐ");

        themDongTien(pnlChiTietTien, "Tổng tiền dịch vụ", txtTongTien);
        themDongTien(pnlChiTietTien, "Chiết khấu", txtChietKhau);
        themDongTien(pnlChiTietTien, "Thuế VAT (10%)", txtThue);
        themDongTien(pnlChiTietTien, "Tiền đã đặt cọc", txtTienCoc);

        JPanel pnlTongThanhToan = new JPanel(new BorderLayout(8, 0));
        pnlTongThanhToan.setBackground(new Color(235, 242, 250));
        pnlTongThanhToan.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 215, 235)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblCanThanhToan = new JLabel("CẦN THANH TOÁN");
        lblCanThanhToan.setFont(new Font("Arial", Font.BOLD, 13));
        lblCanThanhToan.setForeground(MAU_NAVY);

        txtCanThanhToan.setFont(new Font("Arial", Font.BOLD, 16));
        txtCanThanhToan.setForeground(MAU_DO);
        txtCanThanhToan.setBackground(new Color(235, 242, 250));
        txtCanThanhToan.setBorder(BorderFactory.createEmptyBorder());

        pnlTongThanhToan.add(lblCanThanhToan, BorderLayout.WEST);
        pnlTongThanhToan.add(txtCanThanhToan, BorderLayout.EAST);

        JPanel pnlPhuongThuc = new JPanel(new GridLayout(1, 2, 8, 0));
        pnlPhuongThuc.setBackground(MAU_TRANG);

        btnTienMat = taoNut("Tiền mặt", MAU_TEAL);
        btnChuyenKhoan = taoNut("Chuyển khoản", MAU_NAVY);

        btnTienMat.setPreferredSize(new Dimension(150, 40));
        btnChuyenKhoan.setPreferredSize(new Dimension(150, 40));

        pnlPhuongThuc.add(btnTienMat);
        pnlPhuongThuc.add(btnChuyenKhoan);

        btnThanhToan = taoNut("THANH TOÁN & LẬP HÓA ĐƠN", new Color(230, 126, 34));
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 15));
        btnThanhToan.setPreferredSize(new Dimension(200, 44));

        JPanel pnlNutThanhToan = new JPanel(new BorderLayout());
        pnlNutThanhToan.setBackground(MAU_TRANG);
        pnlNutThanhToan.add(btnThanhToan, BorderLayout.CENTER);

        JPanel pnlNoiDung = new JPanel();
        pnlNoiDung.setLayout(new BoxLayout(pnlNoiDung, BoxLayout.Y_AXIS));
        pnlNoiDung.setBackground(MAU_TRANG);

        pnlNoiDung.add(pnlMaGiamGia);
        pnlNoiDung.add(Box.createVerticalStrut(8));
        pnlNoiDung.add(pnlChiTietTien);
        pnlNoiDung.add(Box.createVerticalStrut(8));
        pnlNoiDung.add(pnlTongThanhToan);
        pnlNoiDung.add(Box.createVerticalStrut(10));
        pnlNoiDung.add(pnlPhuongThuc);
        pnlNoiDung.add(Box.createVerticalStrut(10));
        pnlNoiDung.add(pnlNutThanhToan);

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(pnlNoiDung, BorderLayout.CENTER);

        return pnl;
    }

    private void dangKySuKien() {
        tblLichHen.addMouseListener(this);
        btnApDungMa.addActionListener(this);
        btnTienMat.addActionListener(this);
        btnChuyenKhoan.addActionListener(this);
        btnThanhToan.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnApDungMa)) {
            xuLyApDungMaGiamGia();
        } else if (source.equals(btnTienMat) || source.equals(btnChuyenKhoan)) {
            // Xử lý chọn phương thức thanh toán nếu cần lưu trạng thái
            JOptionPane.showMessageDialog(this, "Đã chọn phương thức thanh toán.");
        } else if (source.equals(btnThanhToan)) {
            xuLyLuuHoaDonVaoCSDL();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(tblLichHen)) {
            int row = tblLichHen.getSelectedRow();
            if (row >= 0) {
                String maLH = String.valueOf(modelLichHen.getValueAt(row, 0));
                taiThongTinChiTietLichHenTuCSDL(maLH);
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // ==================== CÁC HÀM KHUNG GỌI DAO / CSDL ====================

    private void taiDanhSachLichHenTuCSDL() {
        modelLichHen.setRowCount(0);
        try {
            // TODO: Kết nối DAO truy vấn danh sách lịch hẹn từ Cơ sở dữ liệu SQL Server
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void taiThongTinChiTietLichHenTuCSDL(String maLH) {
        try {
            // TODO: Gọi DAO truy vấn thông tin chi tiết khách hàng và dịch vụ theo maLH từ CSDL
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void xuLyApDungMaGiamGia() {
        String maGiamGia = txtMaGiamGia.getText().trim();
        if (maGiamGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã giảm giá!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            // TODO: Kiểm tra mã giảm giá từ CSDL và tính toán lại tiền chiết khấu, cần thanh toán
            // KhuyenMai km = khuyenMaiDao.getByMa(maGiamGia);
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void xuLyLuuHoaDonVaoCSDL() {
        String maLH = txtMaLichHen.getText().trim();
        if (maLH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch hẹn cần lập hóa đơn!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán và lưu hóa đơn vào cơ sở dữ liệu?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // TODO: Gọi DAO thực hiện các thao tác CSDL trong Transaction:
                // 1. INSERT vào bảng HOADON
                // 2. INSERT vào bảng CHITIETHOADON
                // 3. UPDATE trạng thái LICHHEN thành 'Đã hoàn thành' hoặc 'Đã thanh toán'
                // hoaDonDao.luuHoaDon(...);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn vào CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private JPanel taoPanelTrang() {
        JPanel pnl = new JPanel();
        pnl.setBackground(MAU_TRANG);
        return pnl;
    }

    private JLabel taoTieuDe(String noiDung) {
        JLabel lbl = new JLabel(noiDung);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setForeground(MAU_NAVY);
        return lbl;
    }

    private JTextField taoOThongTin(String noiDung) {
        JTextField txt = new JTextField(noiDung);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setEditable(false);
        txt.setBackground(new Color(248, 249, 252));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 229, 235)),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return txt;
    }

    private void themTruong(JPanel pnl, String tenTruong, JTextField txt) {
        JLabel lbl = new JLabel(tenTruong);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(new Color(75, 85, 99));

        pnl.add(lbl);
        pnl.add(txt);
    }

    private void themDongTien(JPanel pnl, String tenDong, JTextField txt) {
        JLabel lbl = new JLabel(tenDong);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(new Color(75, 85, 99));

        txt.setHorizontalAlignment(SwingConstants.RIGHT);

        pnl.add(lbl);
        pnl.add(txt);
    }

    private JButton taoNut(String noiDung, Color mauNen) {
        JButton btn = new JButton(noiDung);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mauNen);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 34));

        return btn;
    }
}