package ui;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import utils.UIHelper;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import java.time.ZoneId;

import com.toedter.calendar.JDateChooser;
import dao.NhanVien_DAO;
import model.NhanVien;

public class NhanVienPanel extends JPanel implements ActionListener, MouseListener {

    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtSoDT;
    private JTextField txtEmail;
    private JDateChooser txtNgayVaoLam;
    private JTextField txtDiaChi;

    private JComboBox<String> cboGioiTinh;
    private JComboBox<String> cboChucVu;
    private JComboBox<String> cboTrangThai;

    private JTable tblNhanVien;
    private DefaultTableModel mdlNhanVien;

    private JLabel lblTongSoNhanVien;

    private JButton btnThem;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;
    private final NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
    // true: đang thêm mới
    // false: đang chỉnh sửa nhân viên đã chọn
    private boolean dangThem = true;

    public NhanVienPanel() {
        setLayout(new BorderLayout());
        setBackground(UIHelper.BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createContent(), BorderLayout.CENTER);

        btnThem.addActionListener(this);
        btnLuu.addActionListener(this);
        btnXoa.addActionListener(this);
        btnHuy.addActionListener(this);

        tblNhanVien.addMouseListener(this);

        clearForm();
        taiDanhSachNhanVien();
    }

    // ==================== GIAO DIỆN CHÍNH ====================

    private JPanel createContent() {
        JPanel pnlNoiDung = new JPanel(new BorderLayout(0, 15));
        pnlNoiDung.setBackground(UIHelper.BG);

        JPanel pnlThongTin = createFormPanel();
        pnlThongTin.setPreferredSize(new Dimension(0, 300));

        JPanel pnlDanhSach = new JPanel(new BorderLayout());
        pnlDanhSach.setBackground(UIHelper.BG);
        pnlDanhSach.add(createTablePanel(), BorderLayout.CENTER);

        pnlNoiDung.add(pnlThongTin, BorderLayout.NORTH);
        pnlNoiDung.add(pnlDanhSach, BorderLayout.CENTER);

        return pnlNoiDung;
    }

    // ==================== BẢNG NHÂN VIÊN ====================

    private JPanel createTablePanel() {
        JPanel pnlBang = UIHelper.createCardPanel();
        pnlBang.setLayout(new BorderLayout(0, 12));

        pnlBang.setBorder(new CompoundBorder(
                new LineBorder(UIHelper.BORDER, 1, true),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(0, 0, 0, 0),
                        "Danh sách nhân viên",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        UIHelper.NAVY
                )
        ));

        JPanel pnlThanhCongCu = new JPanel(new BorderLayout());
        pnlThanhCongCu.setOpaque(false);

        JLabel lblMoTa = new JLabel(
                "Quản lý thông tin nhân sự phòng khám"
        );
        lblMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMoTa.setForeground(UIHelper.MUTED);

        pnlThanhCongCu.add(lblMoTa, BorderLayout.WEST);

        btnThem = new JButton("Thêm nhân viên");
        UIHelper.styleButton(
                btnThem,
                true,
                FontAwesomeSolid.USER_PLUS
        );

        pnlThanhCongCu.add(btnThem, BorderLayout.EAST);
        pnlBang.add(pnlThanhCongCu, BorderLayout.NORTH);

        String[] tieuDeCot = {
                "STT",
                "Mã NV",
                "Họ và tên",
                "Số điện thoại",
                "Ngày vào làm",
                "Giới tính",
                "Chức vụ",
                "Trạng thái",
                "Email",
                "Địa chỉ"
        };

        mdlNhanVien = new DefaultTableModel(tieuDeCot, 0) {
            @Override
            public boolean isCellEditable(int dong, int cot) {
                return false;
            }
        };

        tblNhanVien = new JTable(mdlNhanVien);

        tblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblNhanVien.setForeground(UIHelper.TEXT);
        tblNhanVien.setBackground(Color.WHITE);
        tblNhanVien.setRowHeight(36);
        tblNhanVien.setGridColor(new Color(230, 240, 250));
        tblNhanVien.setShowVerticalLines(false);
        tblNhanVien.setSelectionBackground(new Color(220, 235, 252));
        tblNhanVien.setSelectionForeground(UIHelper.NAVY);
        tblNhanVien.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tblNhanVien.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );
        tblNhanVien.getTableHeader().setForeground(Color.WHITE);
        tblNhanVien.getTableHeader().setBackground(UIHelper.NAVY);
        tblNhanVien.getTableHeader().setPreferredSize(
                new Dimension(0, 36)
        );
        tblNhanVien.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer boCanGiua =
                new DefaultTableCellRenderer();

        boCanGiua.setHorizontalAlignment(JLabel.CENTER);

        int[] doRongCot = {
                45, 80, 160, 115, 110,
                80, 100, 100, 170, 180
        };

        for (int i = 0; i < doRongCot.length; i++) {
            tblNhanVien.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(doRongCot[i]);
        }

        tblNhanVien.getColumnModel()
                .getColumn(0).setCellRenderer(boCanGiua);

        tblNhanVien.getColumnModel()
                .getColumn(1).setCellRenderer(boCanGiua);

        tblNhanVien.getColumnModel()
                .getColumn(3).setCellRenderer(boCanGiua);

        tblNhanVien.getColumnModel()
                .getColumn(4).setCellRenderer(boCanGiua);

        tblNhanVien.getColumnModel()
                .getColumn(5).setCellRenderer(boCanGiua);

        JScrollPane scrDanhSach = new JScrollPane(tblNhanVien);
        scrDanhSach.setBorder(
                new LineBorder(UIHelper.BORDER, 1, true)
        );
        scrDanhSach.getViewport().setBackground(Color.WHITE);

        pnlBang.add(scrDanhSach, BorderLayout.CENTER);

        JPanel pnlChan = new JPanel(new BorderLayout());
        pnlChan.setOpaque(false);

        lblTongSoNhanVien = new JLabel("Tổng số: 0 nhân viên");
        lblTongSoNhanVien.setForeground(UIHelper.MUTED);
        lblTongSoNhanVien.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        pnlChan.add(lblTongSoNhanVien, BorderLayout.WEST);
        pnlBang.add(pnlChan, BorderLayout.SOUTH);

        return pnlBang;
    }

    // ==================== FORM THÔNG TIN ====================

    private JPanel createFormPanel() {
        JPanel pnlForm = UIHelper.createCardPanel();
        pnlForm.setLayout(new BorderLayout(0, 8));

        pnlForm.setBorder(new CompoundBorder(
                new LineBorder(UIHelper.BORDER, 1, true),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(0, 0, 0, 0),
                        "Thông tin chi tiết",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        UIHelper.NAVY
                )
        ));

        JPanel pnlLuoi = new JPanel(
                new GridLayout(3, 3, 15, 8)
        );

        pnlLuoi.setBackground(Color.WHITE);
        pnlLuoi.setBorder(new EmptyBorder(5, 10, 5, 10));

        txtMaNV = new JTextField();
        txtMaNV.setEditable(false);
        txtHoTen = new JTextField();
        txtSoDT = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();

        txtNgayVaoLam = new JDateChooser();
        txtNgayVaoLam.setDateFormatString("yyyy-MM-dd");
        txtNgayVaoLam.setPreferredSize(
                new Dimension(0, 32)
        );
        txtNgayVaoLam.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        cboGioiTinh = new JComboBox<>(
                new String[]{"Chọn giới tính", "Nam", "Nữ"}
        );

        cboChucVu = new JComboBox<>(
                new String[]{
                        "Chọn chức vụ",
                        "Quản lý",
                        "Lễ tân",
                        "Nha sĩ"
                }
        );

        cboTrangThai = new JComboBox<>(
                new String[]{
                        "Chọn trạng thái",
                        "Đang làm",
                        "Tạm nghỉ"
                }
        );

        UIHelper.styleComboBox(cboGioiTinh);
        UIHelper.styleComboBox(cboChucVu);
        UIHelper.styleComboBox(cboTrangThai);

        pnlLuoi.add(
                createFieldWrapper("Mã nhân viên *", txtMaNV)
        );

        pnlLuoi.add(
                createFieldWrapper("Họ và tên *", txtHoTen)
        );

        pnlLuoi.add(
                createFieldWrapper("Số điện thoại", txtSoDT)
        );

        pnlLuoi.add(
                createFieldWrapper(
                        "Ngày vào làm * (yyyy-MM-dd)",
                        txtNgayVaoLam
                )
        );

        pnlLuoi.add(
                createFieldWrapper("Giới tính", cboGioiTinh)
        );

        pnlLuoi.add(
                createFieldWrapper("Chức vụ *", cboChucVu)
        );

        pnlLuoi.add(
                createFieldWrapper("Trạng thái *", cboTrangThai)
        );

        pnlLuoi.add(
                createFieldWrapper("Email", txtEmail)
        );

        pnlLuoi.add(
                createFieldWrapper("Địa chỉ", txtDiaChi)
        );

        pnlForm.add(pnlLuoi, BorderLayout.CENTER);

        JPanel pnlNut = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );

        pnlNut.setOpaque(false);
        pnlNut.setBorder(new EmptyBorder(0, 0, 5, 5));

        btnLuu = new JButton("Lưu");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");

        UIHelper.styleButton(
                btnLuu, true, FontAwesomeSolid.SAVE
        );

        UIHelper.styleButton(
                btnXoa, false, FontAwesomeSolid.TRASH_ALT
        );

        UIHelper.styleButton(
                btnHuy, false, FontAwesomeSolid.TIMES
        );

        btnXoa.setForeground(new Color(215, 38, 56));

        btnLuu.setPreferredSize(new Dimension(85, 30));
        btnXoa.setPreferredSize(new Dimension(85, 30));
        btnHuy.setPreferredSize(new Dimension(85, 30));

        pnlNut.add(btnLuu);
        pnlNut.add(btnXoa);
        pnlNut.add(btnHuy);

        pnlForm.add(pnlNut, BorderLayout.SOUTH);

        return pnlForm;
    }

    private JPanel createFieldWrapper(
            String tieuDe,
            JComponent thanhPhan
    ) {
        JPanel pnlTruong = new JPanel(
                new BorderLayout(0, 2)
        );

        pnlTruong.setBackground(Color.WHITE);

        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(
                new Font("Segoe UI", Font.BOLD, 11)
        );
        lblTieuDe.setForeground(UIHelper.TEXT);

        if (thanhPhan instanceof JTextField) {
            UIHelper.styleTextField((JTextField) thanhPhan);

            if (thanhPhan == txtMaNV) {
                txtMaNV.setEditable(false);
                txtMaNV.setFocusable(false);
                txtMaNV.setBackground(new Color(235, 235, 235));
            }
        }

        pnlTruong.add(lblTieuDe, BorderLayout.NORTH);
        pnlTruong.add(thanhPhan, BorderLayout.CENTER);

        return pnlTruong;
    }

    // ==================== XÓA TRẮNG FORM ====================

    public void clearForm() {
        if (tblNhanVien != null) {
            tblNhanVien.clearSelection();
        }

        txtMaNV.setText(nhanVienDAO.sinhMaNVMoi());
        txtHoTen.setText("");
        txtSoDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");

        txtNgayVaoLam.setDate(null);

        cboGioiTinh.setSelectedIndex(0);
        cboChucVu.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);

        dangThem = true;

        txtMaNV.setEditable(false);
        //  txtMaNV.requestFocusInWindow();
    }

    // ==================== HIỂN THỊ DỮ LIỆU LÊN FORM ====================

    private void hienThiThongTinLenForm() {
        int dongDuocChon = tblNhanVien.getSelectedRow();

        if (dongDuocChon < 0) {
            return;
        }

        int dong = tblNhanVien.convertRowIndexToModel(
                dongDuocChon
        );

        txtMaNV.setText(
                giaTriRongNeuNull(
                        mdlNhanVien.getValueAt(dong, 1)
                )
        );

        txtHoTen.setText(
                giaTriRongNeuNull(
                        mdlNhanVien.getValueAt(dong, 2)
                )
        );

        txtSoDT.setText(
                giaTriRongNeuNull(
                        mdlNhanVien.getValueAt(dong, 3)
                )
        );

        Object ngay = mdlNhanVien.getValueAt(dong, 4);

        if (ngay != null && !"null".equals(ngay.toString())) {
            txtNgayVaoLam.setDate(
                    java.sql.Date.valueOf(
                            LocalDate.parse(ngay.toString())
                    )
            );
        } else {
            txtNgayVaoLam.setDate(null);
        }

        String gioiTinh = giaTriRongNeuNull(
                mdlNhanVien.getValueAt(dong, 5)
        );

        if ("Nam".equals(gioiTinh)) {
            cboGioiTinh.setSelectedItem("Nam");
        } else if ("Nữ".equals(gioiTinh)) {
            cboGioiTinh.setSelectedItem("Nữ");
        } else {
            cboGioiTinh.setSelectedIndex(0);
        }

        String chucVu = giaTriRongNeuNull(
                mdlNhanVien.getValueAt(dong, 6)
        );

        cboChucVu.setSelectedItem(chucVu);

        String trangThai = giaTriRongNeuNull(
                mdlNhanVien.getValueAt(dong, 7)
        );

        cboTrangThai.setSelectedItem(trangThai);

        txtEmail.setText(
                giaTriRongNeuNull(
                        mdlNhanVien.getValueAt(dong, 8)
                )
        );

        txtDiaChi.setText(
                giaTriRongNeuNull(
                        mdlNhanVien.getValueAt(dong, 9)
                )
        );

        // Đang chỉnh sửa nhân viên đã tồn tại.
        dangThem = false;

        // Không cho sửa mã của nhân viên hiện có.
        txtMaNV.setEditable(false);
    }

    private String giaTriRongNeuNull(Object giaTri) {
        if (giaTri == null
                || "null".equals(String.valueOf(giaTri))) {
            return "";
        }

        return String.valueOf(giaTri);
    }

    // ==================== TẢI DANH SÁCH NHÂN VIÊN ====================

    private void taiDanhSachNhanVien() {
        List<NhanVien> dsNhanVien = nhanVienDAO.getAllNhanVien();

        mdlNhanVien.setRowCount(0);

        if (dsNhanVien == null) {
            lblTongSoNhanVien.setText("Tổng số: 0 nhân viên");
            return;
        }

        int stt = 1;

        for (NhanVien nv : dsNhanVien) {
            String gioiTinh;

            if (nv.getGioiTinh() == null) {
                gioiTinh = "Chưa xác định";
            } else {
                gioiTinh = nv.getGioiTinh() ? "Nam" : "Nữ";
            }

            mdlNhanVien.addRow(new Object[]{
                    stt++,
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getSoDT() == null ? "" : nv.getSoDT(),
                    nv.getNgayVaoLam(),
                    gioiTinh,
                    nv.getChucVu(),
                    nv.getTrangThai(),
                    nv.getEmail() == null ? "" : nv.getEmail(),
                    nv.getDiaChi() == null ? "" : nv.getDiaChi()
            });
        }

        lblTongSoNhanVien.setText(
                "Tổng số: " + dsNhanVien.size() + " nhân viên"
        );
    }

    // ==================== XỬ LÝ NÚT ====================

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnThem) {
            xuLyThemNhanVien();

        } else if (o == btnLuu) {
            xuLyLuuNhanVien();

        } else if (o == btnXoa) {
            xuLyXoaNhanVien();

        } else if (o == btnHuy) {
            clearForm();
        }
    }

    // ==================== CHỌN NHÂN VIÊN TRÊN BẢNG ====================

    @Override
    public void mouseClicked(MouseEvent e) {
        if (tblNhanVien.getSelectedRow() >= 0) {
            hienThiThongTinLenForm();
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

    // ==================== KIỂM TRA DỮ LIỆU ====================

    private boolean kiemTraDuLieu() {
        String hoTen = txtHoTen.getText().trim();
        String soDT = txtSoDT.getText().trim();
        String email = txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ và tên!");
            txtHoTen.requestFocusInWindow();
            return false;
        }

        if (hoTen.length() > 40) {
            JOptionPane.showMessageDialog(this, "Họ tên không được vượt quá 40 ký tự!");
            txtHoTen.requestFocusInWindow();
            return false;
        }

        if (!hoTen.matches("^[\\p{L}]+(?:[ '\\u2019-][\\p{L}]+)*$")) {
            JOptionPane.showMessageDialog(this,
                    "Họ tên chỉ được chứa chữ cái và khoảng trắng hợp lệ!");
            txtHoTen.requestFocusInWindow();
            return false;
        }

        if (soDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txtSoDT.requestFocusInWindow();
            return false;
        }

        if (!soDT.matches("0[3-9]\\d{8}")) {
            JOptionPane.showMessageDialog(this,
                    "Số điện thoại phải có 10 chữ số và bắt đầu bằng 03 đến 09!");
            txtSoDT.requestFocusInWindow();
            return false;
        }

        if (txtNgayVaoLam.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày vào làm!");
            txtNgayVaoLam.requestFocusInWindow();
            return false;
        }

        LocalDate ngayVaoLam = txtNgayVaoLam.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (ngayVaoLam.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(this,
                    "Ngày vào làm không được lớn hơn ngày hiện tại!");
            txtNgayVaoLam.requestFocusInWindow();
            return false;
        }

        if (cboGioiTinh.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            cboGioiTinh.requestFocusInWindow();
            return false;
        }

        if (cboChucVu.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ!");
            cboChucVu.requestFocusInWindow();
            return false;
        }

        if (cboTrangThai.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái!");
            cboTrangThai.requestFocusInWindow();
            return false;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email!");
            txtEmail.requestFocusInWindow();
            return false;
        }

        if (email.length() > 30
                || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this,
                    "Email không hợp lệ hoặc vượt quá 30 ký tự!");
            txtEmail.requestFocusInWindow();
            return false;
        }

        if (diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!");
            txtDiaChi.requestFocusInWindow();
            return false;
        }

        if (diaChi.length() > 60) {
            JOptionPane.showMessageDialog(this,
                    "Địa chỉ không được vượt quá 60 ký tự!");
            txtDiaChi.requestFocusInWindow();
            return false;
        }

        return true;
    }

    // ==================== THÊM / CẬP NHẬT NHÂN VIÊN ====================

    private void xuLyThemNhanVien() {
        if (!kiemTraDuLieu()) {
            return;
        }

        try {
            String maNV = txtMaNV.getText().trim();
            String hoTen = txtHoTen.getText().trim();

            String soDT = txtSoDT.getText().trim();
            String email = txtEmail.getText().trim();
            String diaChi = txtDiaChi.getText().trim();

            // Chuyển ngày từ JDateChooser sang LocalDate.
            LocalDate ngayVaoLam = txtNgayVaoLam.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // Chuyển giới tính sang Boolean.
            Boolean gioiTinh = null;

            if (cboGioiTinh.getSelectedIndex() != 0) {
                gioiTinh = cboGioiTinh.getSelectedItem()
                        .toString()
                        .equals("Nam");
            }

            String chucVu = cboChucVu.getSelectedItem().toString();
            String trangThai = cboTrangThai.getSelectedItem().toString();

            NhanVien nv = new NhanVien(
                    null,
                    hoTen,
                    soDT.isEmpty() ? null : soDT,
                    ngayVaoLam,
                    gioiTinh,
                    chucVu,
                    trangThai,
                    email.isEmpty() ? null : email,
                    diaChi.isEmpty() ? null : diaChi
            );

            // Ghi nhân viên mới xuống database.
            boolean ketQua = nhanVienDAO.insert(nv);

            if (ketQua) {
                JOptionPane.showMessageDialog(
                        this,
                        "Thêm nhân viên thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                taiDanhSachNhanVien();
                clearForm();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Thêm nhân viên thất bại! "
                                + "Hãy kiểm tra mã nhân viên và dữ liệu SQL Server.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi thêm nhân viên: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void xuLyLuuNhanVien() {
        if (dangThem || tblNhanVien.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn nhân viên trong bảng trước khi cập nhật!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!kiemTraDuLieu()) {
            return;
        }

        try {
            NhanVien nv = layNhanVienTuForm();

            boolean ketQua = nhanVienDAO.update(nv);

            if (ketQua) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật nhân viên thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                taiDanhSachNhanVien();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật nhân viên thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi cập nhật nhân viên: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================== XÓA NHÂN VIÊN ====================

    private void xuLyXoaNhanVien() {
        int dongDuocChon = tblNhanVien.getSelectedRow();

        if (dongDuocChon < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn nhân viên cần xóa từ bảng!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int dong = tblNhanVien.convertRowIndexToModel(dongDuocChon);

        String maNV = mdlNhanVien.getValueAt(dong, 1).toString();

        int xacNhan = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn ẩn nhân viên " + maNV + " khỏi bảng không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (xacNhan != JOptionPane.YES_OPTION) {
            return;
        }

        mdlNhanVien.removeRow(dong);

        lblTongSoNhanVien.setText(
                "Tổng số: " + mdlNhanVien.getRowCount() + " nhân viên"
        );

        // Xóa thông tin trên form
        clearForm();

        JOptionPane.showMessageDialog(
                this,
                "Đã xóa nhân viên khỏi bảng!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
    }
    private NhanVien layNhanVienTuForm() {
        LocalDate ngayVaoLam = txtNgayVaoLam.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Boolean gioiTinh = "Nam".equals(cboGioiTinh.getSelectedItem());

        return new NhanVien(
                txtMaNV.getText().trim(),
                txtHoTen.getText().trim(),
                txtSoDT.getText().trim(),
                ngayVaoLam,
                gioiTinh,
                cboChucVu.getSelectedItem().toString(),
                cboTrangThai.getSelectedItem().toString(),
                txtEmail.getText().trim(),
                txtDiaChi.getText().trim()
        );
    }
}