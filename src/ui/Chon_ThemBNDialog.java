package ui;

import com.toedter.calendar.JDateChooser;
import dao.LichHenDAO;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import utils.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Chon_ThemBNDialog extends JDialog {
    private JTabbedPane tabbedPane;
    private JTable tblBenhNhan;
    private DefaultTableModel modelBenhNhan;
    private JTextField txtTimKiem;

    // Tab Thêm mới
    private JTextField txtMaBNMoi;
    private JTextField txtHoTenMoi;
    private JDateChooser dcNgaySinhMoi;
    private JComboBox<String> cboGioiTinh;
    private JTextField txtSDTMoi;
    private JTextField txtEmailMoi;
    private JTextField txtDiaChiMoi;

    // Tab Chỉnh sửa: trên là tìm kiếm + bảng chọn, dưới là form chỉnh sửa
    private JTextField txtTimKiemSua;
    private JTable tblKetQuaSua;
    private DefaultTableModel modelKetQuaSua;
    private JTextField txtMaBNSua;
    private JTextField txtHoTenSua;
    private JDateChooser dcNgaySinhSua;
    private JComboBox<String> cboGioiTinhSua;
    private JTextField txtSDTSua;
    private JTextField txtEmailSua;
    private JTextField txtDiaChiSua;

    private String maBenhNhanChon = null;
    private String tenBenhNhanChon = null;
    private final LichHenDAO dao = new LichHenDAO();

    public Chon_ThemBNDialog(JFrame parent) {
        super(parent, "Quản lý Bệnh Nhân - Tiếp Nhận", true);
        setSize(820, 650);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UIHelper.BG);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.addTab("Danh sách bệnh nhân", createTabChonCu());
        tabbedPane.addTab("+ Thêm mới", createTabThemMoi());
        tabbedPane.addTab("Chỉnh sửa thông tin", createTabChinhSua());

        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 2) {
                loadDanhSachTimKiemSua(txtTimKiemSua != null ? txtTimKiemSua.getText().trim() : "");
            }
        });

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createTabChonCu() {
        JPanel p = new JPanel(new BorderLayout(0, 12));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));
        p.setBackground(Color.WHITE);

        JPanel pTop = new JPanel(new BorderLayout(10, 0));
        pTop.setOpaque(false);

        JLabel lblTim = new JLabel("Tìm kiếm (Tên / SĐT):");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTim.setForeground(UIHelper.TEXT);

        JPanel pNhap = new JPanel(new BorderLayout(8, 0));
        pNhap.setOpaque(false);
        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTimKiem.setPreferredSize(new Dimension(240, 36));
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Nhập tên hoặc số điện thoại...");

        JButton btnTim = new JButton("Tìm");
        UIHelper.styleButton(btnTim, true, FontAwesomeSolid.SEARCH);
        btnTim.setPreferredSize(new Dimension(90, 36));

        pNhap.add(txtTimKiem, BorderLayout.CENTER);
        pNhap.add(btnTim, BorderLayout.EAST);

        pTop.add(lblTim, BorderLayout.WEST);
        pTop.add(pNhap, BorderLayout.CENTER);

        String[] cols = {"Mã BN", "Họ tên bệnh nhân", "Ngày sinh", "Giới tính", "Số điện thoại", "Địa chỉ"};
        modelBenhNhan = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblBenhNhan = new JTable(modelBenhNhan);
        styleTable(tblBenhNhan);

        JScrollPane scroll = new JScrollPane(tblBenhNhan);
        scroll.setBorder(new LineBorder(UIHelper.BORDER, 1, true));
        scroll.getViewport().setBackground(Color.WHITE);

        loadDanhSachBenhNhan("");

        JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pBottom.setOpaque(false);

        JButton btnChon = new JButton("Chọn");
        JButton btnHuy = new JButton("Hủy");

        UIHelper.styleButton(btnChon, true, FontAwesomeSolid.CHECK);
        UIHelper.styleButton(btnHuy, false, FontAwesomeSolid.TIMES);
        btnHuy.setForeground(new Color(215, 38, 56));

        btnChon.setPreferredSize(new Dimension(100, 36));
        btnHuy.setPreferredSize(new Dimension(90, 36));

        pBottom.add(btnChon);
        pBottom.add(btnHuy);

        p.add(pTop, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        p.add(pBottom, BorderLayout.SOUTH);

        btnTim.addActionListener(e -> xuLyTimKiemBenhNhan());
        txtTimKiem.addActionListener(e -> xuLyTimKiemBenhNhan());
        btnChon.addActionListener(e -> xuLyXacNhanChonBenhNhan());
        btnHuy.addActionListener(e -> xuLyDongDialog());

        return p;
    }

    private JPanel createTabThemMoi() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(25, 30, 25, 30));
        p.setBackground(Color.WHITE);

        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        Dimension fieldSize = new Dimension(240, 38);

        txtMaBNMoi = new JTextField();
        txtMaBNMoi.setEditable(false);
        txtMaBNMoi.setBackground(new Color(245, 248, 252));
        txtMaBNMoi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtMaBNMoi.setPreferredSize(fieldSize);
        try {
            txtMaBNMoi.setText(dao.sinhMaBenhNhanTuDong());
        } catch (Exception ignored) {
        }

        txtHoTenMoi = new JTextField();
        txtHoTenMoi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHoTenMoi.setPreferredSize(fieldSize);

        dcNgaySinhMoi = new JDateChooser();
        dcNgaySinhMoi.setDateFormatString("dd/MM/yyyy");
        dcNgaySinhMoi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dcNgaySinhMoi.setPreferredSize(fieldSize);
        dcNgaySinhMoi.setDate(new Date());

        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        UIHelper.styleComboBox(cboGioiTinh);
        cboGioiTinh.setPreferredSize(fieldSize);

        txtSDTMoi = new JTextField();
        txtSDTMoi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSDTMoi.setPreferredSize(fieldSize);

        txtEmailMoi = new JTextField();
        txtEmailMoi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtEmailMoi.setPreferredSize(fieldSize);

        txtDiaChiMoi = new JTextField();
        txtDiaChiMoi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDiaChiMoi.setPreferredSize(new Dimension(560, 38));

        addFormField(formGrid, gbc, 0, 0, "Mã bệnh nhân", txtMaBNMoi);
        addFormField(formGrid, gbc, 2, 0, "Họ tên *", txtHoTenMoi);
        addFormField(formGrid, gbc, 0, 1, "Ngày sinh *", dcNgaySinhMoi);
        addFormField(formGrid, gbc, 2, 1, "Giới tính *", cboGioiTinh);
        addFormField(formGrid, gbc, 0, 2, "Số điện thoại *", txtSDTMoi);
        addFormField(formGrid, gbc, 2, 2, "Email", txtEmailMoi);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formGrid.add(createStyledLabel("Địa chỉ"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        formGrid.add(txtDiaChiMoi, gbc);

        JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pBottom.setOpaque(false);
        pBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton btnLuuMoi = new JButton("Lưu");
        JButton btnHuyMoi = new JButton("Hủy");

        UIHelper.styleButton(btnLuuMoi, true, FontAwesomeSolid.SAVE);
        UIHelper.styleButton(btnHuyMoi, false, FontAwesomeSolid.TIMES);
        btnHuyMoi.setForeground(new Color(215, 38, 56));

        btnLuuMoi.setPreferredSize(new Dimension(95, 38));
        btnHuyMoi.setPreferredSize(new Dimension(90, 38));

        pBottom.add(btnLuuMoi);
        pBottom.add(btnHuyMoi);

        p.add(formGrid, BorderLayout.CENTER);
        p.add(pBottom, BorderLayout.SOUTH);

        btnHuyMoi.addActionListener(e -> xuLyDongDialog());
        btnLuuMoi.addActionListener(e -> xuLyLuuBenhNhanMoi());

        return p;
    }

    private JPanel createTabChinhSua() {
        JPanel p = new JPanel(new BorderLayout(0, 12));
        p.setBorder(new EmptyBorder(14, 20, 14, 20));
        p.setBackground(Color.WHITE);

        // KHỐI TRÊN: Tìm kiếm + danh sách kết quả để chọn bệnh nhân cần sửa
        JPanel pTren = new JPanel(new BorderLayout(0, 8));
        pTren.setOpaque(false);
        pTren.setPreferredSize(new Dimension(0, 210));

        JPanel pTimBar = new JPanel(new BorderLayout(10, 0));
        pTimBar.setOpaque(false);

        JLabel lblTimSua = new JLabel("Tìm bệnh nhân (Mã / Tên / SĐT):");
        lblTimSua.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTimSua.setForeground(UIHelper.TEXT);

        txtTimKiemSua = new JTextField();
        txtTimKiemSua.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTimKiemSua.setPreferredSize(new Dimension(240, 34));
        txtTimKiemSua.putClientProperty("JTextField.placeholderText", "Nhập mã BN, họ tên hoặc số điện thoại để tìm...");

        JButton btnTimSua = new JButton("Tìm kiếm");
        UIHelper.styleButton(btnTimSua, true, FontAwesomeSolid.SEARCH);
        btnTimSua.setPreferredSize(new Dimension(110, 34));

        pTimBar.add(lblTimSua, BorderLayout.WEST);
        pTimBar.add(txtTimKiemSua, BorderLayout.CENTER);
        pTimBar.add(btnTimSua, BorderLayout.EAST);

        String[] colsSua = {"Mã BN", "Họ tên", "Ngày sinh", "Giới tính", "SĐT", "Email", "Địa chỉ"};
        modelKetQuaSua = new DefaultTableModel(colsSua, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblKetQuaSua = new JTable(modelKetQuaSua);
        styleTable(tblKetQuaSua);
        tblKetQuaSua.setRowHeight(30);

        JScrollPane spKetQua = new JScrollPane(tblKetQuaSua);
        spKetQua.setBorder(new LineBorder(UIHelper.BORDER, 1, true));
        spKetQua.getViewport().setBackground(Color.WHITE);

        pTren.add(pTimBar, BorderLayout.NORTH);
        pTren.add(spKetQua, BorderLayout.CENTER);

        // KHỐI DƯỚI: Form chỉnh sửa thông tin bệnh nhân được chọn
        JPanel pDuoi = new JPanel(new BorderLayout(0, 8));
        pDuoi.setOpaque(false);
        pDuoi.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(UIHelper.BORDER, 1, true),
                "Thông tin chỉnh sửa",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                UIHelper.NAVY
        ));

        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        Dimension fieldSize = new Dimension(230, 34);

        txtMaBNSua = new JTextField();
        txtMaBNSua.setEditable(false);
        txtMaBNSua.setBackground(new Color(245, 248, 252));
        txtMaBNSua.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtMaBNSua.setPreferredSize(fieldSize);

        txtHoTenSua = new JTextField();
        txtHoTenSua.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHoTenSua.setPreferredSize(fieldSize);

        dcNgaySinhSua = new JDateChooser();
        dcNgaySinhSua.setDateFormatString("dd/MM/yyyy");
        dcNgaySinhSua.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dcNgaySinhSua.setPreferredSize(fieldSize);

        cboGioiTinhSua = new JComboBox<>(new String[]{"Nam", "Nữ"});
        UIHelper.styleComboBox(cboGioiTinhSua);
        cboGioiTinhSua.setPreferredSize(fieldSize);

        txtSDTSua = new JTextField();
        txtSDTSua.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSDTSua.setPreferredSize(fieldSize);

        txtEmailSua = new JTextField();
        txtEmailSua.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtEmailSua.setPreferredSize(fieldSize);

        txtDiaChiSua = new JTextField();
        txtDiaChiSua.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDiaChiSua.setPreferredSize(new Dimension(560, 34));

        addFormField(formGrid, gbc, 0, 0, "Mã bệnh nhân", txtMaBNSua);
        addFormField(formGrid, gbc, 2, 0, "Họ tên *", txtHoTenSua);
        addFormField(formGrid, gbc, 0, 1, "Ngày sinh *", dcNgaySinhSua);
        addFormField(formGrid, gbc, 2, 1, "Giới tính *", cboGioiTinhSua);
        addFormField(formGrid, gbc, 0, 2, "Số điện thoại *", txtSDTSua);
        addFormField(formGrid, gbc, 2, 2, "Email", txtEmailSua);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formGrid.add(createStyledLabel("Địa chỉ"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        formGrid.add(txtDiaChiSua, gbc);

        JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pBottom.setOpaque(false);

        JButton btnCapNhat = new JButton("Cập nhật");
        JButton btnChonSauSua = new JButton("Cập nhật & Chọn");
        JButton btnHuySua = new JButton("Hủy");

        UIHelper.styleButton(btnCapNhat, true, FontAwesomeSolid.SAVE);
        UIHelper.styleButton(btnChonSauSua, true, FontAwesomeSolid.CHECK);
        UIHelper.styleButton(btnHuySua, false, FontAwesomeSolid.TIMES);
        btnHuySua.setForeground(new Color(215, 38, 56));

        pBottom.add(btnCapNhat);
        pBottom.add(btnChonSauSua);
        pBottom.add(btnHuySua);

        pDuoi.add(formGrid, BorderLayout.CENTER);
        pDuoi.add(pBottom, BorderLayout.SOUTH);

        p.add(pTren, BorderLayout.NORTH);
        p.add(pDuoi, BorderLayout.CENTER);

        loadDanhSachTimKiemSua("");

        btnTimSua.addActionListener(e -> loadDanhSachTimKiemSua(txtTimKiemSua.getText().trim()));
        txtTimKiemSua.addActionListener(e -> loadDanhSachTimKiemSua(txtTimKiemSua.getText().trim()));
        tblKetQuaSua.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                doDongDangChonLenFormSua();
            }
        });
        btnCapNhat.addActionListener(e -> xuLyCapNhatBenhNhan(false));
        btnChonSauSua.addActionListener(e -> xuLyCapNhatBenhNhan(true));
        btnHuySua.addActionListener(e -> xuLyDongDialog());

        return p;
    }

    private void loadDanhSachTimKiemSua(String tuKhoa) {
        if (modelKetQuaSua == null || tblKetQuaSua == null) return;
        modelKetQuaSua.setRowCount(0);
        String kw = tuKhoa == null ? "" : tuKhoa.trim().toLowerCase();
        try {
            List<Map<String, String>> ds = dao.getDanhSachTatCaBenhNhan();
            for (Map<String, String> bn : ds) {
                String ma = bn.getOrDefault("MaBenhNhan", "");
                String ten = bn.getOrDefault("HoTen", "");
                String ns = bn.getOrDefault("NgaySinh", "");
                String gt = bn.getOrDefault("GioiTinh", "");
                String sdt = bn.getOrDefault("SoDienThoai", "");
                String email = bn.getOrDefault("Email", "");
                String dc = bn.getOrDefault("DiaChi", "");
                boolean khop = kw.isEmpty()
                        || ma.toLowerCase().contains(kw)
                        || ten.toLowerCase().contains(kw)
                        || sdt.toLowerCase().contains(kw);
                if (khop) {
                    modelKetQuaSua.addRow(new Object[]{ma, ten, ns, gt, sdt, email, dc});
                }
            }
            if (modelKetQuaSua.getRowCount() > 0) {
                tblKetQuaSua.setRowSelectionInterval(0, 0);
                doDongDangChonLenFormSua();
            } else {
                xoaTrangFormSua();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách bệnh nhân: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doDongDangChonLenFormSua() {
        int r = tblKetQuaSua.getSelectedRow();
        if (r < 0) return;

        txtMaBNSua.setText(String.valueOf(modelKetQuaSua.getValueAt(r, 0)));
        txtHoTenSua.setText(String.valueOf(modelKetQuaSua.getValueAt(r, 1)));
        String nsStr = String.valueOf(modelKetQuaSua.getValueAt(r, 2));
        String gtStr = String.valueOf(modelKetQuaSua.getValueAt(r, 3));
        String sdtStr = String.valueOf(modelKetQuaSua.getValueAt(r, 4));
        String emailStr = String.valueOf(modelKetQuaSua.getValueAt(r, 5));
        String dcStr = String.valueOf(modelKetQuaSua.getValueAt(r, 6));

        cboGioiTinhSua.setSelectedItem("Nam".equalsIgnoreCase(gtStr) ? "Nam" : "Nữ");
        txtSDTSua.setText(sdtStr);
        txtEmailSua.setText("null".equals(emailStr) ? "" : emailStr);
        txtDiaChiSua.setText("null".equals(dcStr) ? "" : dcStr);

        if (nsStr != null && !nsStr.isEmpty() && !"null".equals(nsStr)) {
            try {
                LocalDate ld = LocalDate.parse(nsStr);
                dcNgaySinhSua.setDate(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } catch (Exception ignored) {
                dcNgaySinhSua.setDate(null);
            }
        } else {
            dcNgaySinhSua.setDate(null);
        }
    }

    private void xoaTrangFormSua() {
        txtMaBNSua.setText("");
        txtHoTenSua.setText("");
        dcNgaySinhSua.setDate(null);
        cboGioiTinhSua.setSelectedIndex(0);
        txtSDTSua.setText("");
        txtEmailSua.setText("");
        txtDiaChiSua.setText("");
    }

    private void xuLyCapNhatBenhNhan(boolean chonSauKhiLuu) {
        String maBN = txtMaBNSua.getText().trim();
        String hoTen = txtHoTenSua.getText().trim();
        String sdt = txtSDTSua.getText().trim();
        String email = txtEmailSua.getText().trim();
        String diaChi = txtDiaChiSua.getText().trim();
        Date nsDate = dcNgaySinhSua.getDate();

        if (maBN.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng tìm và chọn một bệnh nhân cần chỉnh sửa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (hoTen.isEmpty() || sdt.isEmpty() || nsDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc (*).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!sdt.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 chữ số.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate ngaySinh = nsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int gioiTinhDB = "Nam".equals(cboGioiTinhSua.getSelectedItem()) ? 1 : 0;

        try {
            boolean ok = dao.capNhatThongTinBenhNhan(maBN, hoTen, ngaySinh, String.valueOf(gioiTinhDB), sdt, email, diaChi);
            if (ok) {
                loadDanhSachBenhNhan(txtTimKiem != null ? txtTimKiem.getText().trim() : "");
                loadDanhSachTimKiemSua(txtTimKiemSua != null ? txtTimKiemSua.getText().trim() : "");
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin bệnh nhân thành công.");
                if (chonSauKhiLuu) {
                    maBenhNhanChon = maBN;
                    tenBenhNhanChon = hoTen;
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin bệnh nhân thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyTimKiemBenhNhan() {
        loadDanhSachBenhNhan(txtTimKiem.getText().trim());
    }

    private void xuLyXacNhanChonBenhNhan() {
        int row = tblBenhNhan.getSelectedRow();
        if (row >= 0) {
            maBenhNhanChon = String.valueOf(modelBenhNhan.getValueAt(row, 0));
            tenBenhNhanChon = String.valueOf(modelBenhNhan.getValueAt(row, 1));
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một bệnh nhân từ danh sách!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void xuLyLuuBenhNhanMoi() {
        String maBN = txtMaBNMoi.getText().trim();
        String hoTen = txtHoTenMoi.getText().trim();
        String selectedGioiTinh = (String) cboGioiTinh.getSelectedItem();
        int gioiTinhDB = "Nam".equals(selectedGioiTinh) ? 1 : 0;

        String sdt = txtSDTMoi.getText().trim();
        String email = txtEmailMoi.getText().trim();
        String diaChi = txtDiaChiMoi.getText().trim();
        Date ngaySinhDate = dcNgaySinhMoi.getDate();

        if (hoTen.isEmpty() || sdt.isEmpty() || ngaySinhDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc (*).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!sdt.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm đúng 10 chữ số!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate ngaySinh = ngaySinhDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        try {
            boolean kq = dao.themBenhNhanMoiDayDu(maBN, hoTen, ngaySinh, String.valueOf(gioiTinhDB), sdt, email, diaChi);
            if (kq) {
                maBenhNhanChon = maBN;
                tenBenhNhanChon = hoTen;
                JOptionPane.showMessageDialog(this, "Thêm bệnh nhân mới thành công.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm bệnh nhân thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyDongDialog() {
        dispose();
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(UIHelper.TEXT);
        table.setBackground(Color.WHITE);
        table.setRowHeight(36);
        table.setGridColor(new Color(230, 240, 250));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(220, 235, 252));
        table.setSelectionForeground(UIHelper.NAVY);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(UIHelper.NAVY);
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        if (table.getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        }
        if (table.getColumnCount() > 2) {
            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        }
        if (table.getColumnCount() > 3) {
            table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        }
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int x, int y, String labelText, JComponent comp) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panel.add(createStyledLabel(labelText), gbc);
        gbc.gridx = x + 1;
        gbc.gridwidth = 1;
        panel.add(comp, gbc);
    }

    private JLabel createStyledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(UIHelper.TEXT);
        return lbl;
    }

    private void loadDanhSachBenhNhan(String tuKhoa) {
        if (modelBenhNhan == null) return;
        modelBenhNhan.setRowCount(0);
        String kw = tuKhoa == null ? "" : tuKhoa.trim().toLowerCase();
        try {
            List<Map<String, String>> ds = dao.getDanhSachTatCaBenhNhan();
            for (Map<String, String> bn : ds) {
                String ma = bn.getOrDefault("MaBenhNhan", "");
                String ten = bn.getOrDefault("HoTen", "");
                String ngaySinh = bn.getOrDefault("NgaySinh", "");
                String gioiTinh = bn.getOrDefault("GioiTinh", "");
                String sdt = bn.getOrDefault("SoDienThoai", "");
                String diaChi = bn.getOrDefault("DiaChi", "");
                if (kw.isEmpty()
                        || ten.toLowerCase().contains(kw)
                        || sdt.toLowerCase().contains(kw)) {
                    modelBenhNhan.addRow(new Object[]{ma, ten, ngaySinh, gioiTinh, sdt, diaChi});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách bệnh nhân: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getMaBenhNhanChon() {
        return maBenhNhanChon;
    }

    public String getTenBenhNhanChon() {
        return tenBenhNhanChon;
    }
}