package ui;

import connectDB.ConnectDB;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PhanCongNhanVienPanel extends JPanel {

    private final Color MAU_CHU = new Color(35, 70, 110);
    private final Color MAU_XANH = new Color(35, 105, 180);
    private final Color MAU_NEN = new Color(245, 247, 250);
    private final Color MAU_VIEN = new Color(224, 232, 241);

    private JTextField txtMaPhanCong;
    private JTextField txtNgayPhanCong;
    private JTextField txtGhiChu;
    private JTextField txtTimKiem;

    private JComboBox<String> cboNhanVien;
    private JComboBox<String> cboCaLamViec;
    private JComboBox<String> cboTrangThai;

    private JTable tblPhanCong;
    private DefaultTableModel mdlPhanCong;
    private TableRowSorter<DefaultTableModel> boLoc;

    private final SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");

    public PhanCongNhanVienPanel() {
        dinhDangNgay.setLenient(false);

        setLayout(new BorderLayout(15, 18));
        setBackground(MAU_NEN);
        setBorder(new EmptyBorder(18, 18, 18, 18));

       // add(taoTieuDe(), BorderLayout.NORTH);
        add(taoNoiDung(), BorderLayout.CENTER);

        // Tải dữ liệu từ CSDL lên bảng và sinh mã tự động khi mở panel
        taiDanhSachTuCSDL();
        txtMaPhanCong.setText(taoMaPhanCongTuDong());
    }

    // Gọi kết nối từ class ConnectDB của bạn
    private Connection getConnection() throws Exception {
        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Không thể thiết lập kết nối tới CSDL.");
        }
        return con;
    }

 /*   private JPanel taoTieuDe() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setOpaque(false);

        JLabel lblTieuDe = new JLabel("PHÂN CÔNG NHÂN VIÊN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 25));
        lblTieuDe.setForeground(MAU_CHU);

        JLabel lblMoTa = new JLabel("Quản lý lịch làm việc cho nhân viên (Sáng, Chiều, Tối)");
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 13));
        lblMoTa.setForeground(new Color(105, 120, 140));

        JPanel pnlText = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlText.setOpaque(false);
        pnlText.add(lblTieuDe);
        pnlText.add(lblMoTa);

        pnl.add(pnlText, BorderLayout.WEST);
        return pnl;
    }
*/
    private JPanel taoNoiDung() {
        JPanel pnl = new JPanel(new BorderLayout(12, 14));
        pnl.setOpaque(false);

        pnl.add(taoFormPhanCong(), BorderLayout.SOUTH);
        pnl.add(taoDanhSach(), BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoFormPhanCong() {
        JPanel pnlForm = taoKhung();
        pnlForm.setLayout(new BorderLayout(10, 12));

        JLabel lblTieuDe = taoNhan("THÔNG TIN PHÂN CÔNG CA LÀM VIỆC", 15);
        pnlForm.add(lblTieuDe, BorderLayout.NORTH);

        txtMaPhanCong = new JTextField();
        txtMaPhanCong.setEditable(false);
        txtMaPhanCong.setBackground(new Color(240, 244, 248));

        cboNhanVien = new JComboBox<>();
        cboNhanVien.addItem("Chọn nhân viên");
        taiDuLieuNhanVienVaoCombo();

        cboCaLamViec = new JComboBox<>(new String[] {
                "Chọn ca làm việc",
                "Ca sáng (07:30 - 11:30)",
                "Ca chiều (13:30 - 17:30)",
                "Ca tối (18:00 - 21:00)"
        });

        txtNgayPhanCong = new JTextField(dinhDangNgay.format(new Date()));
        txtNgayPhanCong.setEditable(false);

        JButton btnChonNgay = taoNutIcon("Chọn ngày");
        btnChonNgay.setPreferredSize(new Dimension(85, 32));
        btnChonNgay.addActionListener(e -> chonNgay());

        JPanel pnlNgay = new JPanel(new BorderLayout(6, 0));
        pnlNgay.setOpaque(false);
        pnlNgay.add(txtNgayPhanCong, BorderLayout.CENTER);
        pnlNgay.add(btnChonNgay, BorderLayout.EAST);

        cboTrangThai = new JComboBox<>(new String[] {
                "Chưa làm",
                "Đã phân công",
                "Đang làm việc",
                "Hoàn thành ca",
                "Nghỉ phép"
        });

        txtGhiChu = new JTextField();

        JPanel pnlTruong = new JPanel(new GridLayout(3, 2, 18, 10));
        pnlTruong.setOpaque(false);

        pnlTruong.add(taoTruong("Mã phân công", txtMaPhanCong));
        pnlTruong.add(taoTruong("Ngày phân công", pnlNgay));
        pnlTruong.add(taoTruong("Nhân viên", cboNhanVien));
        pnlTruong.add(taoTruong("Ca làm việc", cboCaLamViec));
        pnlTruong.add(taoTruong("Trạng thái", cboTrangThai));
        pnlTruong.add(taoTruong("Ghi chú", txtGhiChu));

        pnlForm.add(pnlTruong, BorderLayout.CENTER);

        JPanel pnlNut = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pnlNut.setOpaque(false);

        JButton btnThem = taoNut("Thêm phân công", new Color(15, 150, 137));
        JButton btnSua = taoNut("Cập nhật", MAU_XANH);
        JButton btnXoa = taoNut("Xóa", new Color(205, 72, 77));
        JButton btnLamMoi = taoNut("Làm mới", new Color(100, 116, 139));

        pnlNut.add(btnThem);
        pnlNut.add(btnSua);
        pnlNut.add(btnXoa);
        pnlNut.add(btnLamMoi);

        pnlForm.add(pnlNut, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> themPhanCong());
        btnSua.addActionListener(e -> suaPhanCong());
        btnXoa.addActionListener(e -> xoaPhanCong());
        btnLamMoi.addActionListener(e -> lamMoiForm());

        return pnlForm;
    }

    private JPanel taoDanhSach() {
        JPanel pnl = taoKhung();
        pnl.setLayout(new BorderLayout(10, 12));

        JLabel lblTieuDe = taoNhan("DANH SÁCH PHÂN CÔNG THEO CA", 15);

        String[] cot = {
                "Mã PC",
                "Ngày phân công",
                "Ca làm việc",
                "Nhân viên",
                "Trạng thái",
                "Ghi chú"
        };

        mdlPhanCong = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblPhanCong = new JTable(mdlPhanCong);
        tblPhanCong.setRowHeight(32);
        tblPhanCong.setFont(new Font("Arial", Font.PLAIN, 12));
        tblPhanCong.setForeground(MAU_CHU);
        tblPhanCong.setSelectionBackground(new Color(215, 235, 250));
        tblPhanCong.setSelectionForeground(MAU_CHU);
        tblPhanCong.setGridColor(MAU_VIEN);
        tblPhanCong.setShowVerticalLines(false);
        tblPhanCong.setAutoCreateRowSorter(true);
        tblPhanCong.setFillsViewportHeight(true);

        tblPhanCong.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblPhanCong.getTableHeader().setBackground(MAU_CHU);
        tblPhanCong.getTableHeader().setForeground(Color.WHITE);
        tblPhanCong.getTableHeader().setPreferredSize(new Dimension(0, 36));

        boLoc = new TableRowSorter<>(mdlPhanCong);
        tblPhanCong.setRowSorter(boLoc);

        tblPhanCong.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblPhanCong.getSelectedRow() >= 0) {
                hienThiDongDuocChon();
            }
        });

        JScrollPane scr = new JScrollPane(tblPhanCong);
        scr.setBorder(BorderFactory.createEmptyBorder());

        txtTimKiem = new JTextField(20);
        txtTimKiem.setPreferredSize(new Dimension(220, 32));

        JButton btnTimKiem = taoNut("Tìm kiếm", MAU_XANH);
        JButton btnBoLoc = taoNut("Bỏ lọc", new Color(100, 116, 139));

        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlTimKiem.setOpaque(false);
        pnlTimKiem.add(new JLabel("Tìm kiếm:"));
        pnlTimKiem.add(txtTimKiem);
        pnlTimKiem.add(btnTimKiem);
        pnlTimKiem.add(btnBoLoc);

        JPanel pnlDauBang = new JPanel(new BorderLayout(5, 10));
        pnlDauBang.setOpaque(false);
        pnlDauBang.add(lblTieuDe, BorderLayout.NORTH);
        pnlDauBang.add(pnlTimKiem, BorderLayout.CENTER);

        btnTimKiem.addActionListener(e -> timKiem());
        txtTimKiem.addActionListener(e -> timKiem());

        btnBoLoc.addActionListener(e -> {
            txtTimKiem.setText("");
            boLoc.setRowFilter(null);
        });

        pnl.add(pnlDauBang, BorderLayout.NORTH);
        pnl.add(scr, BorderLayout.CENTER);

        return pnl;
    }

    // ==================== CÁC HÀM THAO TÁC CSDL ====================

    private void taiDuLieuNhanVienVaoCombo() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MaNV, HoTen FROM NhanVien")) {

            if (rs != null) {
                while (rs.next()) {
                    String maNV = rs.getString("MaNV").trim();
                    String hoTen = rs.getString("HoTen").trim();
                    cboNhanVien.addItem(maNV + " - " + hoTen);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void taiDanhSachTuCSDL() {
        mdlPhanCong.setRowCount(0);
        String sql = "SELECT llv.MaPhanCong, llv.NgayPhanCong, llv.CaLamViec, " +
                "llv.MaNhanVien, nv.HoTen, llv.TrangThai, llv.GhiChu " +
                "FROM LichLamViec llv JOIN NhanVien nv ON llv.MaNhanVien = nv.MaNV";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs != null) {
                while (rs.next()) {
                    String maPC = rs.getString("MaPhanCong");
                    String ngay = dinhDangNgay.format(rs.getDate("NgayPhanCong"));
                    String ca = rs.getString("CaLamViec");
                    String hienThiNV = rs.getString("MaNhanVien").trim() + " - " + rs.getString("HoTen").trim();
                    String trangThai = rs.getString("TrangThai");
                    String ghiChu = rs.getString("GhiChu");

                    mdlPhanCong.addRow(new Object[] { maPC, ngay, ca, hienThiNV, trangThai, ghiChu });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String taoMaPhanCongTuDong() {
        String maMoi = "PC0001";
        String sql = "SELECT TOP 1 MaPhanCong FROM LichLamViec ORDER BY MaPhanCong DESC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs != null && rs.next()) {
                String maCu = rs.getString("MaPhanCong");
                int so = Integer.parseInt(maCu.substring(2)) + 1;
                maMoi = String.format("PC%04d", so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maMoi;
    }

    private void themPhanCong() {
        if (!kiemTraDuLieu()) return;

        String maPC = txtMaPhanCong.getText().trim();
        String selectedNV = (String) cboNhanVien.getSelectedItem();
        String maNV = selectedNV.substring(0, selectedNV.indexOf(" - ")).trim();
        String caLam = (String) cboCaLamViec.getSelectedItem();
        String trangThai = (String) cboTrangThai.getSelectedItem();
        String ghiChu = txtGhiChu.getText().trim();

        String sql = "INSERT INTO LichLamViec (MaPhanCong, NgayPhanCong, CaLamViec, MaNhanVien, TrangThai, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maPC);
            pstmt.setDate(2, new java.sql.Date(dinhDangNgay.parse(txtNgayPhanCong.getText()).getTime()));
            pstmt.setString(3, caLam);
            pstmt.setString(4, maNV);
            pstmt.setString(5, trangThai);
            pstmt.setString(6, ghiChu.isEmpty() ? null : ghiChu);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm phân công vào CSDL thành công!");
            taiDanhSachTuCSDL();
            lamMoiForm();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaPhanCong() {
        int dongView = tblPhanCong.getSelectedRow();
        if (dongView < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phân công cần cập nhật.");
            return;
        }

        if (!kiemTraDuLieu()) return;

        String maPC = txtMaPhanCong.getText().trim();
        String selectedNV = (String) cboNhanVien.getSelectedItem();
        String maNV = selectedNV.substring(0, selectedNV.indexOf(" - ")).trim();
        String caLam = (String) cboCaLamViec.getSelectedItem();
        String trangThai = (String) cboTrangThai.getSelectedItem();
        String ghiChu = txtGhiChu.getText().trim();

        String sql = "UPDATE LichLamViec SET NgayPhanCong = ?, CaLamViec = ?, MaNhanVien = ?, TrangThai = ?, GhiChu = ? WHERE MaPhanCong = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, new java.sql.Date(dinhDangNgay.parse(txtNgayPhanCong.getText()).getTime()));
            pstmt.setString(2, caLam);
            pstmt.setString(3, maNV);
            pstmt.setString(4, trangThai);
            pstmt.setString(5, ghiChu.isEmpty() ? null : ghiChu);
            pstmt.setString(6, maPC);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            taiDanhSachTuCSDL();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaPhanCong() {
        int dongView = tblPhanCong.getSelectedRow();
        if (dongView < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phân công cần xóa.");
            return;
        }

        int luaChon = JOptionPane.showConfirmDialog(
                this, "Bạn có chắc chắn muốn xóa phân công này không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
        );

        if (luaChon == JOptionPane.YES_OPTION) {
            String maPC = txtMaPhanCong.getText().trim();
            String sql = "DELETE FROM LichLamViec WHERE MaPhanCong = ?";

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, maPC);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                taiDanhSachTuCSDL();
                lamMoiForm();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ==================== CÁC HÀM HỖ TRỢ GIAO DIỆN ====================

    private JPanel taoKhung() {
        JPanel pnl = new JPanel();
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(MAU_VIEN),
                new EmptyBorder(14, 14, 14, 14)
        ));
        return pnl;
    }

    private JLabel taoNhan(String noiDung, int coChu) {
        JLabel lbl = new JLabel(noiDung);
        lbl.setFont(new Font("Arial", Font.BOLD, coChu));
        lbl.setForeground(MAU_CHU);
        return lbl;
    }

    private JPanel taoTruong(String ten, JComponent input) {
        JPanel pnl = new JPanel(new BorderLayout(4, 5));
        pnl.setOpaque(false);

        JLabel lbl = taoNhan(ten, 12);
        input.setPreferredSize(new Dimension(200, 32));
        pnl.add(lbl, BorderLayout.NORTH);
        pnl.add(input, BorderLayout.CENTER);

        return pnl;
    }

    private JButton taoNut(String ten, Color mau) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mau);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(135, 34));
        return btn;
    }

    private JButton taoNutIcon(String ten) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setForeground(MAU_CHU);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void chonNgay() {
        JSpinner spnNgay = new JSpinner(
                new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH)
        );
        spnNgay.setEditor(new JSpinner.DateEditor(spnNgay, "dd/MM/yyyy"));

        int luaChon = JOptionPane.showConfirmDialog(
                this, spnNgay, "Chọn ngày phân công",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (luaChon == JOptionPane.OK_OPTION) {
            Date ngay = (Date) spnNgay.getValue();
            txtNgayPhanCong.setText(dinhDangNgay.format(ngay));
        }
    }

    private boolean kiemTraDuLieu() {
        if (cboNhanVien.getSelectedIndex() == 0 || cboCaLamViec.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn nhân viên và ca làm việc.",
                    "Thiếu thông tin",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        return true;
    }

    private void hienThiDongDuocChon() {
        int dongView = tblPhanCong.getSelectedRow();
        if (dongView < 0) return;

        int dong = tblPhanCong.convertRowIndexToModel(dongView);

        txtMaPhanCong.setText(mdlPhanCong.getValueAt(dong, 0).toString());
        txtNgayPhanCong.setText(mdlPhanCong.getValueAt(dong, 1).toString());
        cboCaLamViec.setSelectedItem(mdlPhanCong.getValueAt(dong, 2).toString());

        String tenNVTable = mdlPhanCong.getValueAt(dong, 3).toString();
        for (int i = 1; i < cboNhanVien.getItemCount(); i++) {
            if (cboNhanVien.getItemAt(i).equals(tenNVTable)) {
                cboNhanVien.setSelectedIndex(i);
                break;
            }
        }

        cboTrangThai.setSelectedItem(mdlPhanCong.getValueAt(dong, 4).toString());
        txtGhiChu.setText(mdlPhanCong.getValueAt(dong, 5) != null ? mdlPhanCong.getValueAt(dong, 5).toString() : "");
    }

    private void timKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        if (tuKhoa.isEmpty()) {
            boLoc.setRowFilter(null);
        } else {
            boLoc.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(tuKhoa)));
        }
    }

    private void lamMoiForm() {
        txtMaPhanCong.setText(taoMaPhanCongTuDong());
        cboNhanVien.setSelectedIndex(0);
        cboCaLamViec.setSelectedIndex(0);
        txtNgayPhanCong.setText(dinhDangNgay.format(new Date()));
        cboTrangThai.setSelectedIndex(0);
        txtGhiChu.setText("");
        tblPhanCong.clearSelection();
    }
}