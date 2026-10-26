package ui;

import dao.LichHenDAO;
import utils.UIHelper;
import com.toedter.calendar.JDateChooser;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

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
    
    // Các trường form thêm mới
    private JTextField txtMaBNMoi;
    private JTextField txtHoTenMoi;
    private JDateChooser dcNgaySinhMoi;
    private JComboBox<String> cboGioiTinh;
    private JTextField txtSDTMoi;
    private JTextField txtEmailMoi;
    private JTextField txtDiaChiMoi;

    private String maBenhNhanChon = null;
    private String tenBenhNhanChon = null;
    private LichHenDAO dao = new LichHenDAO();

    public Chon_ThemBNDialog(JFrame parent) {
        super(parent, "Quản lý Bệnh Nhân - Tiếp Nhận", true);
        setSize(780, 560);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UIHelper.BG);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.addTab("Danh sách bệnh nhân", createTabChonCu());
        tabbedPane.addTab("+ Thêm mới", createTabThemMoi());

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
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tblBenhNhan = new JTable(modelBenhNhan);
        tblBenhNhan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblBenhNhan.setForeground(UIHelper.TEXT);
        tblBenhNhan.setBackground(Color.WHITE);
        tblBenhNhan.setRowHeight(38);
        tblBenhNhan.setGridColor(new Color(230, 240, 250));
        tblBenhNhan.setShowVerticalLines(false);
        tblBenhNhan.setSelectionBackground(new Color(220, 235, 252));
        tblBenhNhan.setSelectionForeground(UIHelper.NAVY);

        tblBenhNhan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblBenhNhan.getTableHeader().setForeground(Color.WHITE);
        tblBenhNhan.getTableHeader().setBackground(UIHelper.NAVY);
        tblBenhNhan.getTableHeader().setPreferredSize(new Dimension(0, 38));
        tblBenhNhan.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblBenhNhan.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblBenhNhan.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblBenhNhan.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblBenhNhan.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

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
        } catch (Exception ignored) {}

        txtHoTenMoi = new JTextField();
        txtHoTenMoi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHoTenMoi.setPreferredSize(fieldSize);

        dcNgaySinhMoi = new JDateChooser();
        dcNgaySinhMoi.setDateFormatString("dd/MM/yyyy");
        dcNgaySinhMoi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dcNgaySinhMoi.setPreferredSize(fieldSize);
        dcNgaySinhMoi.setDate(new Date());
        JTextField dateEditor = ((JTextField) dcNgaySinhMoi.getDateEditor().getUiComponent());
        dateEditor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateEditor.setEditable(true); 

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
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        formGrid.add(createStyledLabel("Địa chỉ"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
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

        if (sdt.length() > 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại tối đa 10 chữ số theo định dạng CSDL!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
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

    private void addFormField(JPanel panel, GridBagConstraints gbc, int x, int y, String labelText, JComponent comp) {
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1;
        panel.add(createStyledLabel(labelText), gbc);
        gbc.gridx = x + 1; gbc.gridwidth = 1;
        panel.add(comp, gbc);
    }

    private JLabel createStyledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(UIHelper.TEXT);
        return lbl;
    }

    private void loadDanhSachBenhNhan(String tuKhoa) {
        modelBenhNhan.setRowCount(0);
        try {
            List<Map<String, String>> ds = dao.getDanhSachTatCaBenhNhan();
            for (Map<String, String> bn : ds) {
                String ma = bn.get("MaBenhNhan");
                String ten = bn.get("HoTen");
                String ngaySinh = bn.get("NgaySinh");
                String gioiTinh = bn.get("GioiTinh");
                String sdt = bn.get("SoDienThoai");
                String diaChi = bn.get("DiaChi");
                if (tuKhoa.isEmpty() || ten.toLowerCase().contains(tuKhoa.toLowerCase()) || sdt.contains(tuKhoa)) {
                    modelBenhNhan.addRow(new Object[]{ma, ten, ngaySinh, gioiTinh, sdt, diaChi});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMaBenhNhanChon() { return maBenhNhanChon; }
    public String getTenBenhNhanChon() { return tenBenhNhanChon; }
}