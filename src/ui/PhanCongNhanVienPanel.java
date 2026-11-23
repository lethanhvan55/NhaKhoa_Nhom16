
package ui;

import connectDB.ConnectDB;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

    // ComboBox nhân viên và chức vụ
    private JComboBox<NhanVienComboItem> cboNhanVien;
    private JComboBox<String> cboChucVu;

    private JComboBox<String> cboCaLamViec;
    private JComboBox<String> cboTrangThai;

    private JTable tblPhanCong;
    private DefaultTableModel mdlPhanCong;
    private TableRowSorter<DefaultTableModel> boLoc;

    private final SimpleDateFormat dinhDangNgay =
            new SimpleDateFormat("dd/MM/yyyy");

    // =====================================================
    // CLASS LƯU THÔNG TIN NHÂN VIÊN TRONG COMBOBOX
    // =====================================================

    private static class NhanVienComboItem {

        private String maNV;
        private String hoTen;
        private String chucVu;

        public NhanVienComboItem(
                String maNV,
                String hoTen,
                String chucVu) {

            this.maNV = maNV;
            this.hoTen = hoTen;
            this.chucVu = chucVu;
        }

        public String getMaNV() {
            return maNV;
        }

        public String getChucVu() {
            return chucVu;
        }

        @Override
        public String toString() {

            if (maNV.isEmpty()) {
                return hoTen;
            }

            return maNV + " - " + hoTen;
        }
    }

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public PhanCongNhanVienPanel() {

        dinhDangNgay.setLenient(false);

        setLayout(new BorderLayout(15, 18));
        setBackground(MAU_NEN);
        setBorder(new EmptyBorder(18, 18, 18, 18));

        add(taoNoiDung(), BorderLayout.CENTER);

        taiDanhSachTuCSDL();

        txtMaPhanCong.setText(taoMaPhanCongTuDong());

        // Khi quay lại màn hình phân công thì tải lại nhân viên
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentShown(ComponentEvent e) {

                taiDuLieuNhanVienVaoCombo();
            }
        });
    }

    // =====================================================
    // KẾT NỐI CSDL
    // =====================================================

    private Connection getConnection() throws Exception {

        Connection con = ConnectDB.getConnection();

        if (con == null || con.isClosed()) {
            throw new SQLException(
                    "Không thể thiết lập kết nối tới CSDL."
            );
        }

        return con;
    }

    // =====================================================
    // GIAO DIỆN CHÍNH
    // =====================================================

    private JPanel taoNoiDung() {

        JPanel pnl = new JPanel(new BorderLayout(12, 14));
        pnl.setOpaque(false);

        pnl.add(taoFormPhanCong(), BorderLayout.SOUTH);
        pnl.add(taoDanhSach(), BorderLayout.CENTER);

        return pnl;
    }

    // =====================================================
    // FORM PHÂN CÔNG
    // =====================================================

    private JPanel taoFormPhanCong() {

        JPanel pnlForm = taoKhung();
        pnlForm.setLayout(new BorderLayout(10, 12));

        JLabel lblTieuDe = taoNhan(
                "THÔNG TIN PHÂN CÔNG CA LÀM VIỆC", 15
        );

        pnlForm.add(lblTieuDe, BorderLayout.NORTH);

        // Mã phân công
        txtMaPhanCong = new JTextField();
        txtMaPhanCong.setEditable(false);
        txtMaPhanCong.setBackground(
                new Color(240, 244, 248)
        );

        // ComboBox nhân viên
        cboNhanVien = new JComboBox<>();

        cboNhanVien.addItem(
                new NhanVienComboItem(
                        "", "Chọn nhân viên", ""
                )
        );

        // ComboBox chức vụ
        cboChucVu = new JComboBox<>();

        cboChucVu.addItem("Chọn nhân viên trước");

        // Không cho người dùng tự chọn chức vụ
        cboChucVu.setEditable(false);
        cboChucVu.setEnabled(false);

        // Khi chọn nhân viên thì tự động hiển thị chức vụ
        cboNhanVien.addActionListener(e ->
                capNhatChucVuTheoNhanVien()
        );

        // Tải danh sách nhân viên từ CSDL
        taiDuLieuNhanVienVaoCombo();

        // ComboBox ca làm việc
        cboCaLamViec = new JComboBox<>(new String[] {
                "Chọn ca làm việc",
                "Ca sáng (07:30 - 11:30)",
                "Ca chiều (13:30 - 17:30)",
                "Ca tối (18:00 - 21:00)"
        });

        // Ngày phân công
        txtNgayPhanCong = new JTextField(
                dinhDangNgay.format(new Date())
        );

        txtNgayPhanCong.setEditable(false);

        JButton btnChonNgay = taoNutIcon("Chọn ngày");
        btnChonNgay.setPreferredSize(
                new Dimension(105, 32)
        );

        btnChonNgay.addActionListener(e -> chonNgay());

        JPanel pnlNgay = new JPanel(
                new BorderLayout(6, 0)
        );

        pnlNgay.setOpaque(false);
        pnlNgay.add(txtNgayPhanCong, BorderLayout.CENTER);
        pnlNgay.add(btnChonNgay, BorderLayout.EAST);

        // Trạng thái
        cboTrangThai = new JComboBox<>(new String[] {
                "Chưa làm",
                "Đã phân công",
                "Đang làm việc",
                "Hoàn thành ca",
                "Nghỉ phép"
        });

        // Ghi chú
        txtGhiChu = new JTextField();

        // Bố trí các trường
        JPanel pnlTruong = new JPanel(
                new GridLayout(4, 2, 18, 10)
        );

        pnlTruong.setOpaque(false);

        pnlTruong.add(
                taoTruong("Mã phân công", txtMaPhanCong)
        );

        pnlTruong.add(
                taoTruong("Ngày phân công", pnlNgay)
        );

        pnlTruong.add(
                taoTruong("Nhân viên", cboNhanVien)
        );

        pnlTruong.add(
                taoTruong("Chức vụ", cboChucVu)
        );

        pnlTruong.add(
                taoTruong("Ca làm việc", cboCaLamViec)
        );

        pnlTruong.add(
                taoTruong("Trạng thái", cboTrangThai)
        );

        pnlTruong.add(
                taoTruong("Ghi chú", txtGhiChu)
        );

        pnlForm.add(pnlTruong, BorderLayout.CENTER);

        // Các nút chức năng
        JPanel pnlNut = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 0)
        );

        pnlNut.setOpaque(false);

        JButton btnThem = taoNut(
                "Thêm phân công",
                new Color(15, 150, 137)
        );

        JButton btnSua = taoNut(
                "Cập nhật", MAU_XANH
        );

        JButton btnXoa = taoNut(
                "Xóa", new Color(205, 72, 77)
        );

        JButton btnLamMoi = taoNut(
                "Làm mới", new Color(100, 116, 139)
        );

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

    // =====================================================
    // DANH SÁCH PHÂN CÔNG
    // =====================================================

    private JPanel taoDanhSach() {

        JPanel pnl = taoKhung();
        pnl.setLayout(new BorderLayout(10, 12));

        JLabel lblTieuDe = taoNhan(
                "DANH SÁCH PHÂN CÔNG THEO CA", 15
        );

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
            public boolean isCellEditable(
                    int row, int column) {

                return false;
            }
        };

        tblPhanCong = new JTable(mdlPhanCong);

        tblPhanCong.setRowHeight(32);
        tblPhanCong.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        tblPhanCong.setForeground(MAU_CHU);

        tblPhanCong.setSelectionBackground(
                new Color(215, 235, 250)
        );

        tblPhanCong.setSelectionForeground(MAU_CHU);
        tblPhanCong.setGridColor(MAU_VIEN);
        tblPhanCong.setShowVerticalLines(false);
        tblPhanCong.setFillsViewportHeight(true);

        tblPhanCong.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        tblPhanCong.getTableHeader().setBackground(MAU_CHU);
        tblPhanCong.getTableHeader().setForeground(Color.WHITE);

        tblPhanCong.getTableHeader().setPreferredSize(
                new Dimension(0, 36)
        );

        boLoc = new TableRowSorter<>(mdlPhanCong);
        tblPhanCong.setRowSorter(boLoc);

        // Chọn dòng trên bảng
        tblPhanCong.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()
                            && tblPhanCong.getSelectedRow() >= 0) {

                        hienThiDongDuocChon();
                    }
                });

        JScrollPane scr = new JScrollPane(tblPhanCong);
        scr.setBorder(BorderFactory.createEmptyBorder());

        // Thanh tìm kiếm
        txtTimKiem = new JTextField(20);
        txtTimKiem.setPreferredSize(
                new Dimension(220, 32)
        );

        JButton btnTimKiem = taoNut(
                "Tìm kiếm", MAU_XANH
        );

        JButton btnBoLoc = taoNut(
                "Bỏ lọc", new Color(100, 116, 139)
        );

        JPanel pnlTimKiem = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 0)
        );

        pnlTimKiem.setOpaque(false);

        pnlTimKiem.add(new JLabel("Tìm kiếm:"));
        pnlTimKiem.add(txtTimKiem);
        pnlTimKiem.add(btnTimKiem);
        pnlTimKiem.add(btnBoLoc);

        JPanel pnlDauBang = new JPanel(
                new BorderLayout(5, 10)
        );

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

    // =====================================================
    // TẢI NHÂN VIÊN TỪ BẢNG NHANVIEN
    // =====================================================

    private void taiDuLieuNhanVienVaoCombo() {

        // Lưu mã nhân viên đang được chọn
        String maNVDangChon = "";

        if (cboNhanVien.getSelectedItem()
                instanceof NhanVienComboItem) {

            NhanVienComboItem nvHienTai =
                    (NhanVienComboItem)
                            cboNhanVien.getSelectedItem();

            maNVDangChon = nvHienTai.getMaNV();
        }

        cboNhanVien.removeAllItems();

        cboNhanVien.addItem(
                new NhanVienComboItem(
                        "", "Chọn nhân viên", ""
                )
        );

        String sql =
                "SELECT MaNV, HoTen, ChucVu " +
                        "FROM NhanVien ORDER BY MaNV";

        try (Connection conn = getConnection();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                String maNV = rs.getString("MaNV");
                String hoTen = rs.getString("HoTen");
                String chucVu = rs.getString("ChucVu");

                maNV = maNV == null ? "" : maNV.trim();
                hoTen = hoTen == null ? "" : hoTen.trim();
                chucVu = chucVu == null ? "" : chucVu.trim();

                NhanVienComboItem item =
                        new NhanVienComboItem(
                                maNV, hoTen, chucVu
                        );

                cboNhanVien.addItem(item);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Không thể tải danh sách nhân viên: "
                            + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        // Khôi phục nhân viên đang chọn
        if (!maNVDangChon.isEmpty()) {

            for (int i = 1;
                 i < cboNhanVien.getItemCount();
                 i++) {

                NhanVienComboItem item =
                        cboNhanVien.getItemAt(i);

                if (item.getMaNV().equals(maNVDangChon)) {

                    cboNhanVien.setSelectedIndex(i);
                    break;
                }
            }
        }

        capNhatChucVuTheoNhanVien();
    }

    // =====================================================
    // TỰ ĐỘNG HIỂN THỊ CHỨC VỤ THEO NHÂN VIÊN
    // =====================================================

    private void capNhatChucVuTheoNhanVien() {

        if (cboChucVu == null) {
            return;
        }

        cboChucVu.removeAllItems();

        NhanVienComboItem nv =
                (NhanVienComboItem)
                        cboNhanVien.getSelectedItem();

        if (nv == null || nv.getMaNV().isEmpty()) {

            cboChucVu.addItem("Chọn nhân viên trước");
            return;
        }

        String chucVu = nv.getChucVu();

        if (chucVu == null || chucVu.trim().isEmpty()) {

            cboChucVu.addItem("Chưa cập nhật chức vụ");

        } else {

            cboChucVu.addItem(chucVu);
        }
    }

    // =====================================================
    // TẢI DANH SÁCH PHÂN CÔNG
    // =====================================================

    private void taiDanhSachTuCSDL() {

        mdlPhanCong.setRowCount(0);

        String sql =
                "SELECT llv.MaPhanCong, llv.NgayPhanCong, " +
                        "llv.CaLamViec, llv.MaNhanVien, nv.HoTen, " +
                        "llv.TrangThai, llv.GhiChu " +
                        "FROM LichLamViec llv " +
                        "JOIN NhanVien nv ON llv.MaNhanVien = nv.MaNV";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String maPC = rs.getString("MaPhanCong");

                Date ngayPC = rs.getDate("NgayPhanCong");

                String ngay = ngayPC == null
                        ? ""
                        : dinhDangNgay.format(ngayPC);

                String ca = rs.getString("CaLamViec");

                String maNV = rs.getString("MaNhanVien");
                String hoTen = rs.getString("HoTen");

                String hienThiNV =
                        (maNV == null ? "" : maNV.trim())
                                + " - "
                                + (hoTen == null ? "" : hoTen.trim());

                String trangThai = rs.getString("TrangThai");
                String ghiChu = rs.getString("GhiChu");

                mdlPhanCong.addRow(new Object[] {
                        maPC,
                        ngay,
                        ca,
                        hienThiNV,
                        trangThai,
                        ghiChu
                });
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải danh sách phân công: "
                            + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // SINH MÃ PHÂN CÔNG TỰ ĐỘNG
    // =====================================================

    private String taoMaPhanCongTuDong() {

        String maMoi = "PC0001";

        String sql =
                "SELECT TOP 1 MaPhanCong " +
                        "FROM LichLamViec " +
                        "ORDER BY MaPhanCong DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {

                String maCu = rs.getString("MaPhanCong");

                int so = Integer.parseInt(
                        maCu.substring(2)
                ) + 1;

                maMoi = String.format("PC%04d", so);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return maMoi;
    }

    // =====================================================
    // THÊM PHÂN CÔNG
    // =====================================================

    private void themPhanCong() {

        if (!kiemTraDuLieu()) {
            return;
        }

        String maPC = txtMaPhanCong.getText().trim();

        NhanVienComboItem nvChon =
                (NhanVienComboItem)
                        cboNhanVien.getSelectedItem();

        String maNV = nvChon.getMaNV();

        String caLam =
                cboCaLamViec.getSelectedItem().toString();

        String trangThai =
                cboTrangThai.getSelectedItem().toString();

        String ghiChu = txtGhiChu.getText().trim();

        String sql =
                "INSERT INTO LichLamViec " +
                        "(MaPhanCong, NgayPhanCong, CaLamViec, " +
                        "MaNhanVien, TrangThai, GhiChu) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, maPC);

            pstmt.setDate(
                    2,
                    new java.sql.Date(
                            dinhDangNgay.parse(
                                    txtNgayPhanCong.getText()
                            ).getTime()
                    )
            );

            pstmt.setString(3, caLam);
            pstmt.setString(4, maNV);
            pstmt.setString(5, trangThai);

            pstmt.setString(
                    6,
                    ghiChu.isEmpty() ? null : ghiChu
            );

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Thêm phân công vào CSDL thành công!"
            );

            taiDanhSachTuCSDL();
            lamMoiForm();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi thêm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // CẬP NHẬT PHÂN CÔNG
    // =====================================================

    private void suaPhanCong() {

        int dongView = tblPhanCong.getSelectedRow();

        if (dongView < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn phân công cần cập nhật."
            );

            return;
        }

        if (!kiemTraDuLieu()) {
            return;
        }

        String maPC = txtMaPhanCong.getText().trim();

        NhanVienComboItem nvChon =
                (NhanVienComboItem)
                        cboNhanVien.getSelectedItem();

        String maNV = nvChon.getMaNV();

        String caLam =
                cboCaLamViec.getSelectedItem().toString();

        String trangThai =
                cboTrangThai.getSelectedItem().toString();

        String ghiChu = txtGhiChu.getText().trim();

        String sql =
                "UPDATE LichLamViec SET " +
                        "NgayPhanCong = ?, CaLamViec = ?, " +
                        "MaNhanVien = ?, TrangThai = ?, GhiChu = ? " +
                        "WHERE MaPhanCong = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setDate(
                    1,
                    new java.sql.Date(
                            dinhDangNgay.parse(
                                    txtNgayPhanCong.getText()
                            ).getTime()
                    )
            );

            pstmt.setString(2, caLam);
            pstmt.setString(3, maNV);
            pstmt.setString(4, trangThai);

            pstmt.setString(
                    5,
                    ghiChu.isEmpty() ? null : ghiChu
            );

            pstmt.setString(6, maPC);

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Cập nhật thành công!"
            );

            taiDanhSachTuCSDL();
            lamMoiForm();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi cập nhật: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // XÓA PHÂN CÔNG
    // =====================================================

    private void xoaPhanCong() {

        int dongView = tblPhanCong.getSelectedRow();

        if (dongView < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn phân công cần xóa."
            );

            return;
        }

        int luaChon = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa phân công này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (luaChon == JOptionPane.YES_OPTION) {

            String maPC = txtMaPhanCong.getText().trim();

            String sql =
                    "DELETE FROM LichLamViec " +
                            "WHERE MaPhanCong = ?";

            try (Connection conn = getConnection();
                 PreparedStatement pstmt =
                         conn.prepareStatement(sql)) {

                pstmt.setString(1, maPC);

                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Đã xóa thành công!"
                );

                taiDanhSachTuCSDL();
                lamMoiForm();

            } catch (Exception e) {

                e.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Lỗi khi xóa: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // =====================================================
    // GIAO DIỆN HỖ TRỢ
    // =====================================================

    private JPanel taoKhung() {

        JPanel pnl = new JPanel();

        pnl.setBackground(Color.WHITE);

        pnl.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(MAU_VIEN),
                        new EmptyBorder(14, 14, 14, 14)
                )
        );

        return pnl;
    }

    private JLabel taoNhan(String noiDung, int coChu) {

        JLabel lbl = new JLabel(noiDung);

        lbl.setFont(
                new Font("Arial", Font.BOLD, coChu)
        );

        lbl.setForeground(MAU_CHU);

        return lbl;
    }

    private JPanel taoTruong(
            String ten,
            JComponent input) {

        JPanel pnl = new JPanel(
                new BorderLayout(4, 5)
        );

        pnl.setOpaque(false);

        JLabel lbl = taoNhan(ten, 12);

        input.setPreferredSize(
                new Dimension(200, 32)
        );

        pnl.add(lbl, BorderLayout.NORTH);
        pnl.add(input, BorderLayout.CENTER);

        return pnl;
    }

    private JButton taoNut(String ten, Color mau) {

        JButton btn = new JButton(ten);

        btn.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        btn.setForeground(Color.WHITE);
        btn.setBackground(mau);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);

        btn.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        btn.setPreferredSize(
                new Dimension(135, 34)
        );

        return btn;
    }

    private JButton taoNutIcon(String ten) {

        JButton btn = new JButton(ten);

        btn.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        btn.setForeground(MAU_CHU);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);

        btn.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return btn;
    }

    // =====================================================
    // CHỌN NGÀY PHÂN CÔNG
    // =====================================================

    private void chonNgay() {

        JSpinner spnNgay = new JSpinner(
                new SpinnerDateModel(
                        new Date(),
                        null,
                        null,
                        java.util.Calendar.DAY_OF_MONTH
                )
        );

        spnNgay.setEditor(
                new JSpinner.DateEditor(
                        spnNgay, "dd/MM/yyyy"
                )
        );

        int luaChon = JOptionPane.showConfirmDialog(
                this,
                spnNgay,
                "Chọn ngày phân công",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (luaChon == JOptionPane.OK_OPTION) {

            Date ngay = (Date) spnNgay.getValue();

            txtNgayPhanCong.setText(
                    dinhDangNgay.format(ngay)
            );
        }
    }

    // =====================================================
    // KIỂM TRA DỮ LIỆU
    // =====================================================

    private boolean kiemTraDuLieu() {

        NhanVienComboItem nv =
                (NhanVienComboItem)
                        cboNhanVien.getSelectedItem();

        if (nv == null || nv.getMaNV().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn nhân viên.",
                    "Thiếu thông tin",
                    JOptionPane.WARNING_MESSAGE
            );

            cboNhanVien.requestFocusInWindow();

            return false;
        }

        if (cboCaLamViec.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn ca làm việc.",
                    "Thiếu thông tin",
                    JOptionPane.WARNING_MESSAGE
            );

            cboCaLamViec.requestFocusInWindow();

            return false;
        }

        return true;
    }

    // =====================================================
    // HIỂN THỊ THÔNG TIN KHI CHỌN DÒNG TRÊN BẢNG
    // =====================================================

    private void hienThiDongDuocChon() {

        int dongView = tblPhanCong.getSelectedRow();

        if (dongView < 0) {
            return;
        }

        int dong =
                tblPhanCong.convertRowIndexToModel(dongView);

        txtMaPhanCong.setText(
                mdlPhanCong.getValueAt(dong, 0).toString()
        );

        txtNgayPhanCong.setText(
                mdlPhanCong.getValueAt(dong, 1).toString()
        );

        cboCaLamViec.setSelectedItem(
                mdlPhanCong.getValueAt(dong, 2).toString()
        );

        // Lấy mã và tên nhân viên trên bảng
        String tenNVTable =
                mdlPhanCong.getValueAt(dong, 3).toString();

        // Tìm đúng nhân viên trong ComboBox
        boolean timThay = false;

        for (int i = 1;
             i < cboNhanVien.getItemCount();
             i++) {

            NhanVienComboItem item =
                    cboNhanVien.getItemAt(i);

            if (item.toString().equals(tenNVTable)) {

                cboNhanVien.setSelectedIndex(i);

                timThay = true;

                break;
            }
        }

        if (!timThay) {
            cboNhanVien.setSelectedIndex(0);
        }

        cboTrangThai.setSelectedItem(
                mdlPhanCong.getValueAt(dong, 4).toString()
        );

        Object ghiChu =
                mdlPhanCong.getValueAt(dong, 5);

        txtGhiChu.setText(
                ghiChu != null ? ghiChu.toString() : ""
        );
    }

    // =====================================================
    // TÌM KIẾM
    // =====================================================

    private void timKiem() {

        String tuKhoa = txtTimKiem.getText().trim();

        if (tuKhoa.isEmpty()) {

            boLoc.setRowFilter(null);

        } else {

            boLoc.setRowFilter(
                    javax.swing.RowFilter.regexFilter(
                            "(?i)" +
                                    java.util.regex.Pattern.quote(tuKhoa)
                    )
            );
        }
    }

    // =====================================================
    // LÀM MỚI FORM
    // =====================================================

    private void lamMoiForm() {

        txtMaPhanCong.setText(
                taoMaPhanCongTuDong()
        );

        // Tải lại danh sách nhân viên mới nhất
        taiDuLieuNhanVienVaoCombo();

        cboNhanVien.setSelectedIndex(0);

        cboChucVu.removeAllItems();
        cboChucVu.addItem("Chọn nhân viên trước");

        cboCaLamViec.setSelectedIndex(0);

        txtNgayPhanCong.setText(
                dinhDangNgay.format(new Date())
        );

        cboTrangThai.setSelectedIndex(0);

        txtGhiChu.setText("");

        tblPhanCong.clearSelection();
    }
 // ==================== HÀM LÀM MỚI DỮ LIỆU TỰ ĐỘNG ====================
    public void refreshData() {
        taiDanhSachTuCSDL();
        taiDuLieuNhanVienVaoCombo();
        if (txtMaPhanCong != null && tblPhanCong.getSelectedRow() < 0) {
            txtMaPhanCong.setText(taoMaPhanCongTuDong());
        }
    }
}
