package ui;

import dao.BenhNhan_DAO;
import dao.HoSoBenhNhan_DAO;
import model.HoSoBenhNhan;
import model.BenhNhan;
import model.NhanVien;
import model.KeHoachDieuTri;
import utils.GiaoDienStyle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class HoSoBenhNhanPanel extends JPanel
        implements ActionListener, MouseListener {

    private final Color NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color BG = GiaoDienStyle.MAU_NEN;
    private final Color WHITE = GiaoDienStyle.MAU_TRANG;
    private final Color MUTED = GiaoDienStyle.MAU_PHU;
    private final Color BLUE = GiaoDienStyle.MAU_TEAL;
    private final Color GREEN = new Color(40, 150, 90);
    private final Color RED = GiaoDienStyle.MAU_DO;

    private JTable tblHoSo;
    private DefaultTableModel modelTable;

    // Các trường nhập liệu
    private JTextField txtMaBenhNhan;
    private JTextField txtMaKeHoach;

    private JDateChooser dcNgayKham;

    private JTextArea txtTinhTrangRangMieng;
    private JTextArea txtChanDoan;
    private JTextArea txtPhuongPhapDieuTri;
    private JTextArea txtTienSuBenh;
    private JTextArea txtDiUng;
    private JTextArea txtBenhLyNen;
    private JTextArea txtNguoiLienHe;
    private JTextField txtSoDTNLH;
    private JTextArea txtGhiChu;

    private JButton btnThem;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;

    // Mã nhân viên đang đăng nhập
    private final String maNVHienTai;

    private final HoSoBenhNhan_DAO hoSoDAO = new HoSoBenhNhan_DAO();
    private final BenhNhan_DAO benhNhanDAO = new BenhNhan_DAO();
    // Thông tin hồ sơ đang chọn
    private String maHoSoDangChon = "";
    private String maBenhNhanDangChon = "";
    private String maNhanVienDangChon = "";
    private String maKeHoachDangChon = "";

    public HoSoBenhNhanPanel(String maNVHienTai) {
        this.maNVHienTai = maNVHienTai;
        khoiTaoPanel();
    }

    public HoSoBenhNhanPanel() {
        this(null);
    }

    private void khoiTaoPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachHoSo();
        datTrangThaiBanDau();
    }

    private void taoGiaoDien() {
        JLabel lblTieuDe = new JLabel("QUẢN LÝ HỒ SƠ BỆNH NHÂN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 23));
        lblTieuDe.setForeground(NAVY);
        lblTieuDe.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(lblTieuDe, BorderLayout.NORTH);

        JPanel pnlDanhSach = taoPanelDanhSach();
        JPanel pnlChiTiet = taoPanelChiTiet();

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                pnlDanhSach,
                pnlChiTiet
        );

        splitPane.setResizeWeight(0.45);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() ->
                splitPane.setDividerLocation(0.45)
        );
    }

    private JPanel taoPanelDanhSach() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblDanhSach = new JLabel("Danh sách hồ sơ bệnh nhân");
        lblDanhSach.setFont(new Font("Arial", Font.BOLD, 17));
        lblDanhSach.setForeground(NAVY);

        panel.add(lblDanhSach, BorderLayout.NORTH);

        String[] columns = {
                "Mã hồ sơ",
                "Tên bệnh nhân",
                "Nhân viên phụ trách",
                "Mã kế hoac",
                "Ngày khám",
                "Tình trạng răng miệng",
                "Chẩn đoán",
                "Phương pháp điều trị",
                "Tiền sử bệnh",
                "Dị ứng",
                "Bệnh lý nền",
                "Người liên hệ",
                "SĐT người liên hệ",
                "Ghi chú"
        };

        modelTable = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblHoSo = new JTable(modelTable);
        tblHoSo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblHoSo.setRowHeight(28);
        tblHoSo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblHoSo.setFillsViewportHeight(true);

        GiaoDienStyle.dinhDangBang(tblHoSo);

        JScrollPane scrollTable = new JScrollPane(
                tblHoSo,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        scrollTable.setBorder(BorderFactory.createLineBorder(
                new Color(220, 225, 230)
        ));

        scrollTable.getViewport().setBackground(WHITE);

        panel.add(scrollTable, BorderLayout.CENTER);

        return panel;
    }

    private JPanel taoPanelChiTiet() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel lblChiTiet = new JLabel("Thông tin hồ sơ khám bệnh");
        lblChiTiet.setFont(new Font("Arial", Font.BOLD, 17));
        lblChiTiet.setForeground(NAVY);

        panel.add(lblChiTiet, BorderLayout.NORTH);

        // Trường mã liên kết
        txtMaBenhNhan = new JTextField();
        txtMaKeHoach = new JTextField();
        txtMaBenhNhan.setEditable(true);
        txtMaKeHoach.setEditable(false);

        txtMaBenhNhan.setToolTipText("Nhập mã bệnh nhân đã tồn tại");
        txtMaKeHoach.setToolTipText("Nhập mã kế hoạch điều trị đã tồn tại");

        dcNgayKham = new JDateChooser();
        dcNgayKham.setDateFormatString("dd/MM/yyyy");
        dcNgayKham.setPreferredSize(new Dimension(180, 30));

        txtTinhTrangRangMieng = taoTextArea(2);
        txtChanDoan = taoTextArea(2);
        txtPhuongPhapDieuTri = taoTextArea(2);
        txtTienSuBenh = taoTextArea(2);
        txtDiUng = taoTextArea(2);
        txtBenhLyNen = taoTextArea(2);
        txtNguoiLienHe = taoTextArea(2);
        txtSoDTNLH = new JTextField();
        txtGhiChu = taoTextArea(2);

        JPanel pnlForm = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlForm.setBackground(WHITE);

        JPanel pnlTrai = new JPanel(new GridBagLayout());
        pnlTrai.setBackground(WHITE);

        themTruong(pnlTrai, 0, "Tên bệnh nhân:", txtMaBenhNhan);
        themTruong(pnlTrai, 1, "Ngày khám:", dcNgayKham);
        themTruong(pnlTrai, 2, "Tình trạng răng miệng:",
                taoScroll(txtTinhTrangRangMieng));
        themTruong(pnlTrai, 3, "Phương pháp điều trị:",
                taoScroll(txtPhuongPhapDieuTri));
        themTruong(pnlTrai, 4, "Dị ứng:", taoScroll(txtDiUng));
        themTruong(pnlTrai, 5, "Người liên hệ:",
                taoScroll(txtNguoiLienHe));

        JPanel pnlPhai = new JPanel(new GridBagLayout());
        pnlPhai.setBackground(WHITE);

        themTruong(pnlPhai, 0, "Mã kế hoạch:", txtMaKeHoach);
        themTruong(pnlPhai, 1, "SĐT người liên hệ:", txtSoDTNLH);
        themTruong(pnlPhai, 2, "Chẩn đoán:", taoScroll(txtChanDoan));
        themTruong(pnlPhai, 3, "Tiền sử bệnh:", taoScroll(txtTienSuBenh));
        themTruong(pnlPhai, 4, "Bệnh lý nền:", taoScroll(txtBenhLyNen));
        themTruong(pnlPhai, 5, "Ghi chú:", taoScroll(txtGhiChu));

        pnlForm.add(pnlTrai);
        pnlForm.add(pnlPhai);

        panel.add(pnlForm, BorderLayout.CENTER);

        JPanel pnlNut = new JPanel(
                new java.awt.FlowLayout(
                        java.awt.FlowLayout.CENTER, 10, 5
                )
        );
        pnlNut.setBackground(WHITE);

        btnThem = GiaoDienStyle.taoNut("Thêm mới", BLUE, 13);
        btnLuu = GiaoDienStyle.taoNut("Lưu", GREEN, 13);
        btnXoa = GiaoDienStyle.taoNut("Xóa", RED, 13);
        btnHuy = GiaoDienStyle.taoNut("Làm mới", MUTED, 13);

        JButton[] dsNut = {btnThem, btnLuu, btnXoa, btnHuy};

        for (JButton nut : dsNut) {
            nut.setFont(new Font("Arial", Font.BOLD, 13));
            nut.setPreferredSize(new Dimension(115, 36));
            nut.setHorizontalAlignment(SwingConstants.CENTER);
            nut.setIcon(null);
        }

        pnlNut.add(btnThem);
        pnlNut.add(btnLuu);
        pnlNut.add(btnXoa);
        pnlNut.add(btnHuy);

        panel.add(pnlNut, BorderLayout.SOUTH);

        return panel;
    }

    private JTextArea taoTextArea(int soDong) {
        JTextArea textArea = new JTextArea(soDong, 15);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        return textArea;
    }

    private JScrollPane taoScroll(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(180, 55));
        return scrollPane;
    }

    private void themTruong(
            JPanel panel,
            int row,
            String ten,
            JComponent component
    ) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        JLabel label = new JLabel(ten);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(component, gbc);
    }

    private void dangKySuKien() {
        btnThem.addActionListener(this);
        btnLuu.addActionListener(this);
        btnXoa.addActionListener(this);
        btnHuy.addActionListener(this);
        tblHoSo.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnThem) {
            themHoSo();
        } else if (source == btnLuu) {
            suaHoSo();
        } else if (source == btnXoa) {
            xoaHoSo();
        } else if (source == btnHuy) {
            xoaForm();
            tblHoSo.clearSelection();
            datTrangThaiBanDau();
        }
    }

    // ===================== TẢI DANH SÁCH =====================

    private void taiDanhSachHoSo() {
        modelTable.setRowCount(0);

        List<HoSoBenhNhan> dsHoSo = hoSoDAO.getAllHoSoBenhNhan();

        if (dsHoSo == null) {
            return;
        }

        for (HoSoBenhNhan hs : dsHoSo) {
            Object[] row = {
                    hs.getMaHoSo(),
                    layTenBenhNhan(hs),
                    layTenNhanVien(hs),
                    layMaKeHoach(hs),
                    hs.getNgayKham() == null
                            ? "" : hs.getNgayKham().toString(),
                    hs.getTinhTrangRangMieng(),
                    hs.getChanDoan(),
                    hs.getPhuongPhapDieuTri(),
                    hs.getTienSuBenh() == null ? "" : hs.getTienSuBenh(),
                    hs.getDiUng() == null ? "" : hs.getDiUng(),
                    hs.getBenhLyNen() == null ? "" : hs.getBenhLyNen(),
                    hs.getNguoiLienHe() == null ? "" : hs.getNguoiLienHe(),
                    hs.getSoDTNLH() == null ? "" : hs.getSoDTNLH(),
                    hs.getGhiChu() == null ? "" : hs.getGhiChu()
            };

            modelTable.addRow(row);
        }
    }

    private String layTenBenhNhan(HoSoBenhNhan hs) {
        return hs.getMaBenhNhan() == null
                ? ""
                : hs.getMaBenhNhan().getHoTen();
    }

    private String layTenNhanVien(HoSoBenhNhan hs) {
        return hs.getMaNV() == null
                ? ""
                : hs.getMaNV().getHoTen();
    }

    private String layMaKeHoach(HoSoBenhNhan hs) {
        return hs.getMaKeHoach() == null
                ? ""
                : hs.getMaKeHoach().getMaKeHoach();
    }

    // ===================== CHỌN HỒ SƠ =====================

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblHoSo.getSelectedRow();

        if (row < 0) {
            return;
        }

        int modelRow = tblHoSo.convertRowIndexToModel(row);

        maHoSoDangChon = layGiaTriBang(modelRow, 0);
        maBenhNhanDangChon = layGiaTriBang(modelRow, 1);
        maNhanVienDangChon = layGiaTriBang(modelRow, 2);
        maKeHoachDangChon = layGiaTriBang(modelRow, 3);

        hienThiChiTiet(modelRow);

        btnThem.setEnabled(false);
        btnLuu.setEnabled(true);
        btnXoa.setEnabled(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void hienThiChiTiet(int row) {
        txtMaBenhNhan.setText(layGiaTriBang(row, 1));
        txtMaKeHoach.setText(layGiaTriBang(row, 3));

        String ngayKham = layGiaTriBang(row, 4);

        try {
            if (!ngayKham.isEmpty()) {
                LocalDate localDate = LocalDate.parse(ngayKham);

                Date date = Date.from(
                        localDate.atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                );

                dcNgayKham.setDate(date);
            } else {
                dcNgayKham.setDate(null);
            }
        } catch (Exception ex) {
            dcNgayKham.setDate(null);
        }

        txtTinhTrangRangMieng.setText(layGiaTriBang(row, 5));
        txtChanDoan.setText(layGiaTriBang(row, 6));
        txtPhuongPhapDieuTri.setText(layGiaTriBang(row, 7));
        txtTienSuBenh.setText(layGiaTriBang(row, 8));
        txtDiUng.setText(layGiaTriBang(row, 9));
        txtBenhLyNen.setText(layGiaTriBang(row, 10));
        txtNguoiLienHe.setText(layGiaTriBang(row, 11));
        txtSoDTNLH.setText(layGiaTriBang(row, 12));
        txtGhiChu.setText(layGiaTriBang(row, 13));
    }

    private String layGiaTriBang(int row, int column) {
        Object value = modelTable.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }

    // ===================== THÊM HỒ SƠ =====================

    private void themHoSo() {
        if (!kiemTraDuLieu()) {
            return;
        }

        if (maNVHienTai == null || maNVHienTai.trim().isEmpty()) {
            hienThiCanhBao("Không tìm thấy mã nhân viên đang đăng nhập!");
            return;
        }

        try {
            // Nhập thông tin bệnh nhân mới
            String hoTen = JOptionPane.showInputDialog(
                    this, "Nhập họ tên bệnh nhân:");

            if (hoTen == null || hoTen.trim().isEmpty()) {
                hienThiCanhBao("Họ tên bệnh nhân không được để trống!");
                return;
            }

            String ngaySinhStr = JOptionPane.showInputDialog(
                    this, "Nhập ngày sinh (yyyy-MM-dd):");

            if (ngaySinhStr == null || ngaySinhStr.trim().isEmpty()) {
                hienThiCanhBao("Ngày sinh không được để trống!");
                return;
            }

            java.time.LocalDate ngaySinh;

            try {
                ngaySinh = java.time.LocalDate.parse(ngaySinhStr.trim());
            } catch (java.time.format.DateTimeParseException ex) {
                hienThiCanhBao("Ngày sinh phải có định dạng yyyy-MM-dd!");
                return;
            }

            if (ngaySinh.isAfter(java.time.LocalDate.now())) {
                hienThiCanhBao("Ngày sinh không được ở tương lai!");
                return;
            }

            String[] gioiTinhs = {"Nam", "Nữ"};

            int chonGioiTinh = JOptionPane.showOptionDialog(
                    this,
                    "Chọn giới tính bệnh nhân:",
                    "Giới tính",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    gioiTinhs,
                    gioiTinhs[0]
            );

            if (chonGioiTinh < 0) {
                return;
            }

            String soDT = JOptionPane.showInputDialog(
                    this, "Nhập số điện thoại bệnh nhân:");

            if (soDT == null || !soDT.trim().matches("^0[3-9]\\d{8}$")) {
                hienThiCanhBao("Số điện thoại phải có 10 chữ số hợp lệ!");
                return;
            }

            String email = JOptionPane.showInputDialog(
                    this, "Nhập email (có thể bỏ trống):");

            String diaChi = JOptionPane.showInputDialog(
                    this, "Nhập địa chỉ (có thể bỏ trống):");

            // Tự sinh mã bệnh nhân
            String maBN = benhNhanDAO.sinhMaBenhNhanMoi();

            if (maBN == null || maBN.trim().isEmpty()) {
                hienThiCanhBao("Không thể sinh mã bệnh nhân!");
                return;
            }

            // Tạo bệnh nhân
            BenhNhan bn = new BenhNhan();
            bn.setMaBenhNhan(maBN);
            bn.setHoTen(hoTen.trim());
            bn.setNgaySinh(ngaySinh);
            bn.setGioiTinh(chonGioiTinh == 0);
            bn.setSoDT(soDT.trim());
            bn.setEmail(email == null || email.trim().isEmpty()
                    ? null : email.trim());
            bn.setDiaChi(diaChi == null || diaChi.trim().isEmpty()
                    ? null : diaChi.trim());

            // Thêm bệnh nhân trước để đảm bảo khóa ngoại
            boolean themBenhNhan = benhNhanDAO.insert(bn);

            if (!themBenhNhan) {
                hienThiCanhBao(
                        "Thêm bệnh nhân thất bại! "
                                + "Kiểm tra số điện thoại có bị trùng không."
                );
                return;
            }

            // Lấy thông tin hồ sơ
            java.util.Date ngayKhamDate = dcNgayKham.getDate();

            if (ngayKhamDate == null) {
                hienThiCanhBao("Vui lòng chọn ngày khám!");
                return;
            }

            java.time.LocalDate ngayKham = ngayKhamDate.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();

            String maKH = txtMaKeHoach.getText().trim();

            NhanVien nv = new NhanVien();
            nv.setMaNV(maNVHienTai.trim());

            KeHoachDieuTri kh = null;

            if (!maKH.isEmpty()) {
                kh = new KeHoachDieuTri();
                kh.setMaKeHoach(maKH);
            }

            // Tạo hồ sơ; DAO sẽ tự sinh MaHoSo
            HoSoBenhNhan hs = new HoSoBenhNhan(
                    null,
                    bn,
                    nv,
                    kh,
                    ngayKham,
                    txtTinhTrangRangMieng.getText().trim(),
                    txtChanDoan.getText().trim(),
                    txtPhuongPhapDieuTri.getText().trim(),
                    giaTriHoacNull(txtTienSuBenh.getText()),
                    giaTriHoacNull(txtDiUng.getText()),
                    giaTriHoacNull(txtBenhLyNen.getText()),
                    giaTriHoacNull(txtNguoiLienHe.getText()),
                    giaTriHoacNull(txtSoDTNLH.getText()),
                    giaTriHoacNull(txtGhiChu.getText())
            );

            boolean themHoSo = hoSoDAO.insert(hs);

            if (themHoSo) {
                txtMaBenhNhan.setText(maBN);

                JOptionPane.showMessageDialog(
                        this,
                        "Thêm bệnh nhân và hồ sơ thành công!"
                                + "\nMã bệnh nhân: " + maBN
                                + "\nMã hồ sơ: " + hs.getMaHoSo(),
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                taiDanhSachHoSo();
                xoaForm();
                datTrangThaiBanDau();

            } else {
                hienThiCanhBao(
                        "Bệnh nhân đã được thêm với mã " + maBN
                                + ", nhưng hồ sơ chưa thêm được. "
                                + "Kiểm tra mã nhân viên, mã kế hoạch và Console."
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi thêm hồ sơ: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== SỬA HỒ SƠ =====================

    private void suaHoSo() {
        if (maHoSoDangChon == null || maHoSoDangChon.trim().isEmpty()) {
            hienThiCanhBao("Vui lòng chọn hồ sơ cần sửa từ bảng.");
            return;
        }

        if (!kiemTraDuLieu()) {
            return;
        }

        try {
            LocalDate ngayKham = dcNgayKham.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            String maBN = txtMaBenhNhan.getText().trim();
            String maKH = txtMaKeHoach.getText().trim();

            if (maBN.isEmpty() || maKH.isEmpty()) {
                hienThiCanhBao(
                        "Mã bệnh nhân và mã kế hoạch không được để trống."
                );
                return;
            }

            BenhNhan bn = new BenhNhan();
            bn.setMaBenhNhan(maBN);

            NhanVien nv = new NhanVien();
            nv.setMaNV(maNhanVienDangChon);

            KeHoachDieuTri kh = new KeHoachDieuTri();
            kh.setMaKeHoach(maKH);

            HoSoBenhNhan hs = new HoSoBenhNhan(
                    maHoSoDangChon,
                    bn,
                    nv,
                    kh,
                    ngayKham,
                    txtTinhTrangRangMieng.getText().trim(),
                    txtChanDoan.getText().trim(),
                    txtPhuongPhapDieuTri.getText().trim(),
                    giaTriHoacNull(txtTienSuBenh.getText()),
                    giaTriHoacNull(txtDiUng.getText()),
                    giaTriHoacNull(txtBenhLyNen.getText()),
                    giaTriHoacNull(txtNguoiLienHe.getText()),
                    giaTriHoacNull(txtSoDTNLH.getText()),
                    giaTriHoacNull(txtGhiChu.getText())
            );

            boolean thanhCong = hoSoDAO.update(hs);

            if (thanhCong) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật hồ sơ thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                taiDanhSachHoSo();
                xoaForm();
                tblHoSo.clearSelection();
                datTrangThaiBanDau();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật hồ sơ thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi cập nhật hồ sơ: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== KIỂM TRA DỮ LIỆU =====================

    private boolean kiemTraDuLieu() {
        if (dcNgayKham.getDate() == null) {
            hienThiCanhBao("Vui lòng chọn ngày khám.");
            return false;
        }

        LocalDate ngayKham = dcNgayKham.getDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if (ngayKham.isAfter(LocalDate.now())) {
            hienThiCanhBao("Ngày khám không được lớn hơn ngày hiện tại.");
            return false;
        }

        if (txtTinhTrangRangMieng.getText().trim().isEmpty()) {
            hienThiCanhBao("Vui lòng nhập tình trạng răng miệng.");
            txtTinhTrangRangMieng.requestFocusInWindow();
            return false;
        }

        if (txtChanDoan.getText().trim().isEmpty()) {
            hienThiCanhBao("Vui lòng nhập chẩn đoán.");
            txtChanDoan.requestFocusInWindow();
            return false;
        }

        if (txtPhuongPhapDieuTri.getText().trim().isEmpty()) {
            hienThiCanhBao("Vui lòng nhập phương pháp điều trị.");
            txtPhuongPhapDieuTri.requestFocusInWindow();
            return false;
        }

        String soDT = txtSoDTNLH.getText().trim();

        if (!soDT.isEmpty() && !soDT.matches("\\d{10}")) {
            hienThiCanhBao(
                    "Số điện thoại người liên hệ phải gồm đúng 10 chữ số."
            );
            txtSoDTNLH.requestFocusInWindow();
            return false;
        }

        return true;
    }

    private void hienThiCanhBao(String noiDung) {
        JOptionPane.showMessageDialog(
                this,
                noiDung,
                "Dữ liệu chưa hợp lệ",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private String giaTriHoacNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }

    // ===================== XÓA HỒ SƠ =====================

    private void xoaHoSo() {
        if (maHoSoDangChon == null || maHoSoDangChon.isEmpty()) {
            hienThiCanhBao("Vui lòng chọn hồ sơ cần xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa hồ sơ "
                        + maHoSoDangChon + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean thanhCong = hoSoDAO.delete(maHoSoDangChon);

            if (thanhCong) {
                JOptionPane.showMessageDialog(
                        this,
                        "Xóa hồ sơ thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                taiDanhSachHoSo();
                xoaForm();
                tblHoSo.clearSelection();
                datTrangThaiBanDau();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể xóa hồ sơ.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Xóa hồ sơ thất bại: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== LÀM MỚI FORM =====================

    private void xoaForm() {
        txtMaBenhNhan.setText("");
        txtMaKeHoach.setText("");

        dcNgayKham.setDate(null);

        txtTinhTrangRangMieng.setText("");
        txtChanDoan.setText("");
        txtPhuongPhapDieuTri.setText("");
        txtTienSuBenh.setText("");
        txtDiUng.setText("");
        txtBenhLyNen.setText("");
        txtNguoiLienHe.setText("");
        txtSoDTNLH.setText("");
        txtGhiChu.setText("");

        maHoSoDangChon = "";
        maBenhNhanDangChon = "";
        maNhanVienDangChon = "";
        maKeHoachDangChon = "";
    }

    private void datTrangThaiBanDau() {
        btnThem.setEnabled(true);
        btnLuu.setEnabled(false);
        btnXoa.setEnabled(false);

        maHoSoDangChon = "";
        maBenhNhanDangChon = "";
        maNhanVienDangChon = "";
        maKeHoachDangChon = "";
    }
}