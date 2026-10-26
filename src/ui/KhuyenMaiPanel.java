package ui;

import com.toedter.calendar.JDateChooser;
import dao.KhuyenMai_DAO;
import model.KhuyenMai;
import utils.InQR;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhuyenMaiPanel extends JPanel implements ActionListener, MouseListener {

    private final Color mauNen = new Color(245, 247, 251);
    private final Color mauTrang = Color.WHITE;
    private final Color mauXanh = new Color(35, 125, 240);
    private final Color mauChu = new Color(35, 55, 75);
    private final Color mauPhu = new Color(110, 125, 145);
    private final Color mauVien = new Color(225, 232, 240);

    private JPanel pnlDanhSach;
    private JPanel pnlTheKhuyenMai;
    private JPanel pnlForm;

    private JTextField txtMaKM;
    private JTextField txtMaThayThe;
    private JTextField txtTenKM;
    private JTextField txtGiaTriToiThieu;
    private JTextField txtChietKhau;
    private JTextField txtSoLuong;
    private JTextField txtTimKiem;

    //private JComboBox<String> cboTrangThai;
    private JComboBox<String> cboLocTrangThai;

    private JDateChooser dcNgayBatDau;
    private JDateChooser dcNgayKetThuc;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JButton btnTimKiem;
    private JButton btnInQR;

    private JLabel lblTongSo;

    private final KhuyenMai_DAO km_dao = new KhuyenMai_DAO();
    private List<KhuyenMai> danhSachHienThi = new ArrayList<>();

    private final DateTimeFormatter dinhDangNgay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JTextField txtTrangThai;

    public KhuyenMaiPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(mauNen);
        setBorder(new EmptyBorder(18, 18, 18, 18));

        khoiTaoGiaoDien();
        dangKySuKien();
        docDuLieu();
        xoaTrang();
    }

    private void khoiTaoGiaoDien() {
        JPanel pnlNoiDung = new JPanel(new GridBagLayout());
        pnlNoiDung.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 0, 10);

        gbc.gridx = 0;
        gbc.weightx = 0.58;

        pnlDanhSach = taoKhung();
        pnlDanhSach.setLayout(new BorderLayout(10, 12));
        pnlDanhSach.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblDanhSach = new JLabel("Danh sách khuyến mãi");
        lblDanhSach.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblDanhSach.setForeground(mauChu);

        txtTimKiem = taoTextField();
        txtTimKiem.setToolTipText("Nhập mã hoặc tên khuyến mãi");

        btnTimKiem = taoNut("Tìm kiếm", mauXanh, Color.WHITE);

        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        lblTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTimKiem.setForeground(mauChu);

        cboLocTrangThai = new JComboBox<>(new String[]{"Tất cả trạng thái", "Đang áp dụng", "Hết hạn"});
        cboLocTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboLocTrangThai.setPreferredSize(new Dimension(160, 36));

        JLabel lblLocTrangThai = new JLabel("Trạng thái:");
        lblLocTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblLocTrangThai.setForeground(mauChu);

        JPanel pnlTimKiem = new JPanel();
        pnlTimKiem.setOpaque(false);
        pnlTimKiem.setLayout(new BoxLayout(pnlTimKiem, BoxLayout.Y_AXIS));

        JPanel pnlDongTimKiem = new JPanel(new BorderLayout(8, 0));
        pnlDongTimKiem.setOpaque(false);
        pnlDongTimKiem.add(lblTimKiem, BorderLayout.WEST);
        pnlDongTimKiem.add(txtTimKiem, BorderLayout.CENTER);
        pnlDongTimKiem.add(btnTimKiem, BorderLayout.EAST);

        JPanel pnlDongLoc = new JPanel(new BorderLayout(8, 0));
        pnlDongLoc.setOpaque(false);
        pnlDongLoc.add(lblLocTrangThai, BorderLayout.WEST);
        pnlDongLoc.add(cboLocTrangThai, BorderLayout.CENTER);

        pnlTimKiem.add(pnlDongTimKiem);
        pnlTimKiem.add(Box.createVerticalStrut(10));
        pnlTimKiem.add(pnlDongLoc);

        JPanel pnlDauDanhSach = new JPanel();
        pnlDauDanhSach.setOpaque(false);
        pnlDauDanhSach.setLayout(new BoxLayout(pnlDauDanhSach, BoxLayout.Y_AXIS));

        lblDanhSach.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlTimKiem.setAlignmentX(Component.LEFT_ALIGNMENT);

        pnlDauDanhSach.add(lblDanhSach);
        pnlDauDanhSach.add(Box.createVerticalStrut(12));
        pnlDauDanhSach.add(pnlTimKiem);

        pnlTheKhuyenMai = new JPanel();
        pnlTheKhuyenMai.setLayout(new BoxLayout(pnlTheKhuyenMai, BoxLayout.Y_AXIS));
        pnlTheKhuyenMai.setBackground(mauNen);
        pnlTheKhuyenMai.setBorder(new EmptyBorder(4, 4, 4, 4));

        JScrollPane scrDanhSach = new JScrollPane(pnlTheKhuyenMai);
        scrDanhSach.setBorder(BorderFactory.createEmptyBorder());
        scrDanhSach.getViewport().setBackground(mauNen);
        scrDanhSach.getVerticalScrollBar().setUnitIncrement(16);

        lblTongSo = new JLabel("Tổng số: 0 khuyến mãi");
        lblTongSo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTongSo.setForeground(mauPhu);

        pnlDanhSach.add(pnlDauDanhSach, BorderLayout.NORTH);
        pnlDanhSach.add(scrDanhSach, BorderLayout.CENTER);
        pnlDanhSach.add(lblTongSo, BorderLayout.SOUTH);

        pnlNoiDung.add(pnlDanhSach, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.42;
        gbc.insets = new Insets(0, 5, 0, 0);

        pnlForm = taoKhung();
        pnlForm.setLayout(new BorderLayout(10, 12));
        pnlForm.setBorder(new EmptyBorder(15, 18, 15, 18));

        JLabel lblForm = new JLabel("Thông tin khuyến mãi");
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblForm.setForeground(mauChu);

        JPanel pnlTruong = new JPanel(new GridBagLayout());
        pnlTruong.setOpaque(false);

        txtMaKM = taoTextField();
        txtMaKM.setEditable(false);
        txtMaThayThe = taoTextField();
        txtTenKM = taoTextField();
        txtGiaTriToiThieu = taoTextField();
        txtChietKhau = taoTextField();
        txtSoLuong = taoTextField();

        dcNgayBatDau = taoDateChooser();
        dcNgayKetThuc = taoDateChooser();

        txtTrangThai = taoTextField();
        txtTrangThai.setEditable(false);
        txtTrangThai.setFocusable(false);

        int dong = 0;
        themTruong(pnlTruong, dong++, "Mã khuyến mãi", txtMaKM);
        themTruong(pnlTruong, dong++, "Mã thay thế", txtMaThayThe);
        themTruong(pnlTruong, dong++, "Tên khuyến mãi", txtTenKM);
        themTruong(pnlTruong, dong++, "Ngày bắt đầu", dcNgayBatDau);
        themTruong(pnlTruong, dong++, "Ngày kết thúc", dcNgayKetThuc);
        themTruong(pnlTruong, dong++, "Giá trị tối thiểu", txtGiaTriToiThieu);
        themTruong(pnlTruong, dong++, "Chiết khấu (%)", txtChietKhau);
        themTruong(pnlTruong, dong++, "Số lượng", txtSoLuong);
        themTruong(pnlTruong, dong, "Trạng thái", txtTrangThai);

        btnThem = taoNut("Thêm", mauXanh, Color.WHITE);
        btnSua = taoNut("Sửa", new Color(240, 160, 45), Color.WHITE);
        btnXoa = taoNut("Xóa", new Color(220, 65, 65), Color.WHITE);
        btnLamMoi = taoNut("Làm mới", new Color(105, 120, 140), Color.WHITE);
        btnInQR = taoNut("In QR", new Color(80, 100, 130), Color.WHITE);

        JPanel pnlNut = new JPanel(new GridLayout(0, 2, 8, 8));
        pnlNut.setOpaque(false);
        pnlNut.add(btnThem);
        pnlNut.add(btnSua);
        pnlNut.add(btnXoa);
        pnlNut.add(btnLamMoi);
        pnlNut.add(btnInQR);

        pnlForm.add(lblForm, BorderLayout.NORTH);
        pnlForm.add(pnlTruong, BorderLayout.CENTER);
        pnlForm.add(pnlNut, BorderLayout.SOUTH);

        pnlNoiDung.add(pnlForm, gbc);
        add(pnlNoiDung, BorderLayout.CENTER);
    }

    private void dangKySuKien() {
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnInQR.addActionListener(this);
        cboLocTrangThai.addActionListener(this);
    }

    private void docDuLieu() {
        try {
            km_dao.capNhatTrangThaiTuNgayKetThuc();
            List<KhuyenMai> ds = km_dao.getAll();
            hienThiDanhSach(ds);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách khuyến mãi: " + e.getMessage());
        }
    }

    private void hienThiDanhSach(List<KhuyenMai> ds) {
        pnlTheKhuyenMai.removeAll();
        danhSachHienThi = new ArrayList<>(ds);

        if (danhSachHienThi.isEmpty()) {
            JLabel lblTrong = new JLabel("Không có khuyến mãi phù hợp");
            lblTrong.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblTrong.setForeground(mauPhu);
            lblTrong.setAlignmentX(Component.LEFT_ALIGNMENT);
            pnlTheKhuyenMai.add(lblTrong);
        } else {
            for (int i = 0; i < danhSachHienThi.size(); i++) {
                KhuyenMai km = danhSachHienThi.get(i);
                JPanel the = taoThe(km);
                pnlTheKhuyenMai.add(the);
                if (i < danhSachHienThi.size() - 1) {
                    pnlTheKhuyenMai.add(Box.createVerticalStrut(10));
                }
            }
        }

        lblTongSo.setText("Tổng số: " + danhSachHienThi.size() + " khuyến mãi");
        pnlTheKhuyenMai.revalidate();
        pnlTheKhuyenMai.repaint();
    }

    private JPanel taoThe(KhuyenMai km) {
        String ngayBD = km.getNgayBatDau() == null ? "" : km.getNgayBatDau().format(dinhDangNgay);
        String ngayKT = km.getNgayKetThuc() == null ? "" : km.getNgayKetThuc().format(dinhDangNgay);

        JPanel the = new JPanel(new BorderLayout(10, 10));
        the.setBackground(mauTrang);
        the.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(mauVien), new EmptyBorder(13, 14, 13, 14)));
        the.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));
        the.setAlignmentX(Component.LEFT_ALIGNMENT);
        the.setName(km.getMaKM());

        JPanel pnlDau = new JPanel(new BorderLayout(8, 5));
        pnlDau.setOpaque(false);

        JPanel pnlTen = new JPanel();
        pnlTen.setOpaque(false);
        pnlTen.setLayout(new BoxLayout(pnlTen, BoxLayout.Y_AXIS));

        JLabel lblTen = new JLabel(km.getTenKM());
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTen.setForeground(mauChu);

        JLabel lblMa = new JLabel(km.getMaKM());
        lblMa.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMa.setForeground(mauXanh);

        pnlTen.add(lblTen);
        pnlTen.add(Box.createVerticalStrut(5));
        pnlTen.add(lblMa);

        JLabel lblTrangThai = new JLabel(km.getTrangThai());
        lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTrangThai.setOpaque(true);

        if ("Hết hạn".equals(km.getTrangThai())) {
            lblTrangThai.setForeground(new Color(220, 53, 69));
            lblTrangThai.setBackground(new Color(255, 230, 230));
        } else {
            lblTrangThai.setForeground(mauXanh);
            lblTrangThai.setBackground(new Color(226, 247, 237));
        }

        lblTrangThai.setBorder(new EmptyBorder(5, 8, 5, 8));

        pnlDau.add(pnlTen, BorderLayout.CENTER);
        pnlDau.add(lblTrangThai, BorderLayout.EAST);

        JPanel pnlThongTin = new JPanel(new GridLayout(0, 2, 12, 7));
        pnlThongTin.setOpaque(false);
        pnlThongTin.add(taoDongThongTin("Ngày bắt đầu", ngayBD));
        pnlThongTin.add(taoDongThongTin("Ngày kết thúc", ngayKT));
        pnlThongTin.add(taoDongThongTin("Mã thay thế", km.getMaThayThe()));
        pnlThongTin.add(taoDongThongTin("Số lượng", String.valueOf(km.getSoLuong())));
        pnlThongTin.add(taoDongThongTin("Giá trị tối thiểu", String.format("%,.0f đ", km.getGiaTriToiThieu())));
        pnlThongTin.add(taoDongThongTin("Chiết khấu", String.format("%s%%", km.getChietKhau())));

        the.add(pnlDau, BorderLayout.NORTH);
        the.add(pnlThongTin, BorderLayout.CENTER);

        ganMouseListener(the);
        return the;
    }

    private void ganMouseListener(Component component) {
        component.addMouseListener(this);
        if (component instanceof Container) {
            for (Component con : ((Container) component).getComponents()) {
                ganMouseListener(con);
            }
        }
    }

    private JLabel taoDongThongTin(String ten, String giaTri) {
        JLabel lbl = new JLabel("<html><span style='color:#718096'>" + ten + ":</span> <b>" + giaTri + "</b></html>");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(mauChu);
        return lbl;
    }

    private JPanel taoKhung() {
        JPanel pnl = new JPanel();
        pnl.setBackground(mauTrang);
        pnl.setBorder(BorderFactory.createLineBorder(mauVien));
        return pnl;
    }

    private JTextField taoTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt.setPreferredSize(new Dimension(100, 36));
        txt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(mauVien), new EmptyBorder(5, 9, 5, 9)));
        return txt;
    }

    private JDateChooser taoDateChooser() {
        JDateChooser dc = new JDateChooser();
        dc.setDateFormatString("dd/MM/yyyy");
        dc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dc.setPreferredSize(new Dimension(100, 36));
        return dc;
    }

    private JButton taoNut(String ten, Color nen, Color chu) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(nen);
        btn.setForeground(chu);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(90, 36));
        return btn;
    }

    private void themTruong(JPanel pnl, int dong, String ten, JComponent truong) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = dong;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 10);

        JLabel lbl = new JLabel(ten);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(mauChu);
        pnl.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        pnl.add(truong, gbc);
    }

    public boolean validData() {
        String maKM = txtMaKM.getText().trim();
        String maThayThe = txtMaThayThe.getText().trim();
        String tenKM = txtTenKM.getText().trim();
        String giaTri = txtGiaTriToiThieu.getText().trim();
        String chietKhau = txtChietKhau.getText().trim();
        String soLuong = txtSoLuong.getText().trim();

        if (maKM.isEmpty() || !maKM.matches("^KM\\d{4}$")) {
            JOptionPane.showMessageDialog(this, "Mã khuyến mãi không hợp lệ.");
            txtMaKM.requestFocus();
            return false;
        }

        if (maThayThe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã thay thế không được để trống.");
            txtMaThayThe.requestFocus();
            return false;
        }

        if (tenKM.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khuyến mãi không được để trống.");
            txtTenKM.requestFocus();
            return false;
        }

        if (dcNgayBatDau.getDate() == null || dcNgayKetThuc.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bắt đầu và ngày kết thúc.");
            return false;
        }

        LocalDate ngayBD = layLocalDate(dcNgayBatDau.getDate());
        LocalDate ngayKT = layLocalDate(dcNgayKetThuc.getDate());

        if (ngayKT.isBefore(ngayBD)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu.");
            dcNgayKetThuc.requestFocus();
            return false;
        }

        if (giaTri.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá trị tối thiểu không được để trống.");
            txtGiaTriToiThieu.requestFocus();
            return false;
        }

        double soGiaTri;
        try {
            soGiaTri = Double.parseDouble(giaTri);
            if (!Double.isFinite(soGiaTri) || soGiaTri < 0) {
                JOptionPane.showMessageDialog(this, "Giá trị tối thiểu phải là số không âm.");
                txtGiaTriToiThieu.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá trị tối thiểu phải là số.");
            txtGiaTriToiThieu.requestFocus();
            return false;
        }

        if (chietKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chiết khấu không được để trống.");
            txtChietKhau.requestFocus();
            return false;
        }

        double soChietKhau;
        try {
            soChietKhau = Double.parseDouble(chietKhau);
            if (!Double.isFinite(soChietKhau) || soChietKhau < 0 || soChietKhau > 100) {
                JOptionPane.showMessageDialog(this, "Chiết khấu phải nằm trong khoảng từ 0 đến 100%.");
                txtChietKhau.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Chiết khấu phải là số.");
            txtChietKhau.requestFocus();
            return false;
        }

        if (soLuong.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống.");
            txtSoLuong.requestFocus();
            return false;
        }

        try {
            int soLuongKM = Integer.parseInt(soLuong);
            if (soLuongKM < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng không được âm.");
                txtSoLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên.");
            txtSoLuong.requestFocus();
            return false;
        }

        boolean dangSua = danhSachHienThi.stream().anyMatch(km -> km.getMaKM().equals(maKM));
        if (km_dao.tonTaiMaThayThe(maThayThe, dangSua ? maKM : null)) {
            JOptionPane.showMessageDialog(this, "Mã thay thế đã tồn tại.");
            txtMaThayThe.requestFocus();
            return false;
        }

        return true;
    }

    private KhuyenMai layDuLieuForm() {
        return new KhuyenMai(txtMaKM.getText().trim(), txtMaThayThe.getText().trim(), txtTenKM.getText().trim(),
                layLocalDate(dcNgayBatDau.getDate()), layLocalDate(dcNgayKetThuc.getDate()),
                Double.parseDouble(txtGiaTriToiThieu.getText().trim()),
                Double.parseDouble(txtChietKhau.getText().trim()),
                Integer.parseInt(txtSoLuong.getText().trim()), txtTrangThai.getText().trim());
    }

    private LocalDate layLocalDate(Date ngay) {
        return ngay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date layDate(LocalDate ngay) {
        return Date.from(ngay.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void xoaTrang() {
        txtMaKM.setText(km_dao.tuSinhMaKM());
        txtMaThayThe.setText("");
        txtTenKM.setText("");
        dcNgayBatDau.setDate(null);
        dcNgayKetThuc.setDate(null);
        txtGiaTriToiThieu.setText("");
        txtChietKhau.setText("");
        txtSoLuong.setText("");
        txtTrangThai.setText("Đang áp dụng");
        txtMaThayThe.requestFocus();
    }

    public void themKhuyenMai() {
        if (!validData()) {
            return;
        }

        KhuyenMai km = layDuLieuForm();

        if (km_dao.them(km)) {
            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công.");
            capNhatDanhSachTheoBoLoc();
            xoaTrang();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thất bại. Vui lòng kiểm tra dữ liệu và SQL Server.");
        }
    }

    public void suaKhuyenMai() {
        String maKM = txtMaKM.getText().trim();

        if (maKM.isEmpty() || !danhSachHienThi.stream().anyMatch(km -> km.getMaKM().equals(maKM))) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần sửa.");
            return;
        }

        if (!validData()) {
            return;
        }

        KhuyenMai km = layDuLieuForm();

        if (km_dao.sua(km)) {
            JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công.");
            capNhatDanhSachTheoBoLoc();
            xoaTrang();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thất bại.");
        }
    }

    public void xoaKhuyenMai() {
        String maKM = txtMaKM.getText().trim();

        if (maKM.isEmpty() || !danhSachHienThi.stream().anyMatch(km -> km.getMaKM().equals(maKM))) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa.");
            return;
        }

        int luaChon = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khuyến mãi " + maKM + " không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (luaChon == JOptionPane.YES_OPTION) {
            if (km_dao.xoa(maKM)) {
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công.");
                capNhatDanhSachTheoBoLoc();
                xoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thất bại.");
            }
        }
    }

    public void timKhuyenMai() {
        String tuKhoa = txtTimKiem.getText().trim();

        capNhatDanhSachTheoBoLoc();

        if (!tuKhoa.isEmpty() && danhSachHienThi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void locTheoTrangThai() {
        capNhatDanhSachTheoBoLoc();
    }

    private void capNhatDanhSachTheoBoLoc() {
        km_dao.capNhatTrangThaiTuNgayKetThuc();

        String tuKhoa = txtTimKiem.getText().trim();
        List<KhuyenMai> ds = tuKhoa.isEmpty() ? km_dao.getAll() : km_dao.timKiem(tuKhoa);
        String trangThai = cboLocTrangThai.getSelectedItem().toString();

        if (!trangThai.equals("Tất cả trạng thái")) {
            ds.removeIf(km -> !trangThai.equals(km.getTrangThai()));
        }

        hienThiDanhSach(ds);
    }

    private void chonKhuyenMai(String maKM) {
        for (KhuyenMai km : danhSachHienThi) {
            if (km.getMaKM().equals(maKM)) {
                txtMaKM.setText(km.getMaKM());
                txtMaThayThe.setText(km.getMaThayThe());
                txtTenKM.setText(km.getTenKM());
                dcNgayBatDau.setDate(km.getNgayBatDau() == null ? null : layDate(km.getNgayBatDau()));
                dcNgayKetThuc.setDate(km.getNgayKetThuc() == null ? null : layDate(km.getNgayKetThuc()));
                txtGiaTriToiThieu.setText(String.valueOf(km.getGiaTriToiThieu()));
                txtChietKhau.setText(String.valueOf(km.getChietKhau()));
                txtSoLuong.setText(String.valueOf(km.getSoLuong()));
                txtTrangThai.setText(km.getTrangThai());
                return;
            }
        }
    }
    public void inQR() {
        String maKM = txtMaKM.getText().trim();
        KhuyenMai km = null;

        for (KhuyenMai item : danhSachHienThi) {
            if (item.getMaKM().equals(maKM)) {
                km = item;
                break;
            }
        }

        if (km == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần in QR.");
            return;
        }

        String noiDung = "MA_KHUYEN_MAI: " + km.getMaKM()
                + "\nMA_THAY_THE: " + km.getMaThayThe()
                + "\nTEN_KHUYEN_MAI: " + km.getTenKM()
                + "\nCHIET_KHAU: " + km.getChietKhau() + "%"
                + "\nNGAY_BAT_DAU: " + km.getNgayBatDau()
                + "\nNGAY_KET_THUC: " + km.getNgayKetThuc()
                + "\nTRANG_THAI: " + km.getTrangThai();

        InQR.hienThiQR(this,km.getTenKM() + " - " + km.getMaKM(), noiDung, km.getMaKM());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnThem) {
            themKhuyenMai();
        } else if (o == btnSua) {
            suaKhuyenMai();
        } else if (o == btnXoa) {
            xoaKhuyenMai();
        } else if (o == btnLamMoi) {
            xoaTrang();
        } else if (o == btnTimKiem) {
            timKhuyenMai();
        } else if (o == cboLocTrangThai) {
            locTheoTrangThai();
        } else if (o == btnInQR) {
            inQR();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component component = (Component) e.getSource();

        while (component != null) {
            if (component instanceof JPanel && component.getName() != null && component.getName().matches("^KM\\d{4}$")) {
                chonKhuyenMai(component.getName());
                return;
            }
            component = component.getParent();
        }
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