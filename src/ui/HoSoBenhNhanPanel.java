package ui;

import dao.HoSoBenhNhan_DAO;
import model.HoSoBenhNhan;
import model.BenhNhan;
import model.NhanVien;
import model.KeHoachDieuTri;
import utils.GiaoDienStyle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class HoSoBenhNhanPanel extends JPanel implements ActionListener, MouseListener {

    private final Color NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color BG = GiaoDienStyle.MAU_NEN;
    private final Color WHITE = GiaoDienStyle.MAU_TRANG;
    private final Color MUTED = GiaoDienStyle.MAU_PHU;
    private final Color BLUE = GiaoDienStyle.MAU_TEAL;
    private final Color RED = GiaoDienStyle.MAU_DO;

    private JTable tblHoSo;
    private DefaultTableModel modelTable;
    private JTextField txtTimKiem;

    private JTextField txtMaHoSo;
    private JTextField txtMaBN;
    private JTextField txtMaNV;
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

    //    private JButton btnTimKiem;
//    private JButton btnLamMoi;
    private JButton btnThem;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuy;

    private final HoSoBenhNhan_DAO hoSoDAO = new HoSoBenhNhan_DAO();

    private boolean dangThem = false;

    public HoSoBenhNhanPanel() {
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

        // Chia giao diện thành phần trên và phần dưới
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

// Đặt vị trí thanh chia sau khi giao diện được hiển thị
        SwingUtilities.invokeLater(() -> {
            splitPane.setDividerLocation(0.45);
        });
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
                "Mã bệnh nhân",
                "Mã nhân viên",
                "Mã kế hoạch",
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

        JScrollPane scrollTable = new JScrollPane(tblHoSo);

        scrollTable.setBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230))
        );

        scrollTable.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        scrollTable.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        panel.add(scrollTable, BorderLayout.CENTER);

//        JPanel pnlTimKiem = new JPanel(new BorderLayout());
//        pnlTimKiem.setBackground(WHITE);
//
//        btnLamMoi = GiaoDienStyle.taoNut("Làm mới", NAVY, 13);
//        btnLamMoi.setText("Làm mới");
//        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
//        btnLamMoi.setPreferredSize(new Dimension(100, 36));
//        btnLamMoi.setMinimumSize(new Dimension(100, 36));
//
//        JPanel pnlNutLamMoi = new JPanel(
//                new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 5)
//        );
//        pnlNutLamMoi.setBackground(WHITE);
//        pnlNutLamMoi.add(btnLamMoi);
//
//        pnlTimKiem.add(pnlNutLamMoi, BorderLayout.CENTER);
//
//        panel.add(pnlTimKiem, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel taoPanelChiTiet() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel lblChiTiet = new JLabel("Thông tin hồ sơ");
        lblChiTiet.setFont(new Font("Arial", Font.BOLD, 17));
        lblChiTiet.setForeground(NAVY);

        panel.add(lblChiTiet, BorderLayout.NORTH);

        // Khởi tạo các trường nhập liệu
        txtMaHoSo = new JTextField();
        txtMaBN = new JTextField();
        txtMaNV = new JTextField();
        txtMaKeHoach = new JTextField();

        dcNgayKham = new JDateChooser();
        dcNgayKham.setDateFormatString("dd/MM/yyyy");
        dcNgayKham.setPreferredSize(new Dimension(150, 30));

        txtTinhTrangRangMieng = taoTextArea(2);
        txtChanDoan = taoTextArea(2);
        txtPhuongPhapDieuTri = taoTextArea(2);
        txtTienSuBenh = taoTextArea(2);
        txtDiUng = taoTextArea(2);
        txtBenhLyNen = taoTextArea(2);
        txtNguoiLienHe = taoTextArea(2);
        txtSoDTNLH = new JTextField();
        txtGhiChu = taoTextArea(2);

        // Chia thành hai cột trái và phải
        JPanel pnlForm = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlForm.setBackground(WHITE);

        // Bên trái
        JPanel pnlTrai = new JPanel(new GridBagLayout());
        pnlTrai.setBackground(WHITE);

        themTruong(pnlTrai, 0, "Mã hồ sơ:", txtMaHoSo);
        themTruong(pnlTrai, 1, "Mã nhân viên:", txtMaNV);
        themTruong(pnlTrai, 2, "Ngày khám:", dcNgayKham);
        themTruong(pnlTrai, 3, "Tình trạng răng miệng:",
                taoScroll(txtTinhTrangRangMieng));
        themTruong(pnlTrai, 4, "Phương pháp điều trị:",
                taoScroll(txtPhuongPhapDieuTri));
        themTruong(pnlTrai, 5, "Dị ứng:", taoScroll(txtDiUng));
        themTruong(pnlTrai, 6, "Người liên hệ:", taoScroll(txtNguoiLienHe));

        // Bên phải
        JPanel pnlPhai = new JPanel(new GridBagLayout());
        pnlPhai.setBackground(WHITE);

        themTruong(pnlPhai, 0, "Mã bệnh nhân:", txtMaBN);
        themTruong(pnlPhai, 1, "Mã kế hoạch:", txtMaKeHoach);
        themTruong(pnlPhai, 2, "SĐT người liên hệ:", txtSoDTNLH);
        themTruong(pnlPhai, 3, "Chẩn đoán:", taoScroll(txtChanDoan));
        themTruong(pnlPhai, 4, "Tiền sử bệnh:", taoScroll(txtTienSuBenh));
        themTruong(pnlPhai, 5, "Bệnh lý nền:", taoScroll(txtBenhLyNen));
        themTruong(pnlPhai, 6, "Ghi chú:", taoScroll(txtGhiChu));

        pnlForm.add(pnlTrai);
        pnlForm.add(pnlPhai);

        panel.add(pnlForm, BorderLayout.CENTER);

        // Các nút thao tác
        JPanel pnlNut = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5)
        );
        pnlNut.setBackground(WHITE);

        btnThem = GiaoDienStyle.taoNut("Thêm", BLUE, 13);
        btnLuu = GiaoDienStyle.taoNut(
                "Lưu", new Color(40, 150, 90), 13
        );
        btnXoa = GiaoDienStyle.taoNut("Xóa", RED, 13);
        btnHuy = GiaoDienStyle.taoNut("Hủy", MUTED, 13);

        JButton[] dsNut = {btnThem, btnLuu, btnXoa, btnHuy};

        for (JButton nut : dsNut) {
            nut.setFont(new Font("Arial", Font.BOLD, 13));
            nut.setPreferredSize(new Dimension(100, 36));
            nut.setMinimumSize(new Dimension(90, 36));
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
        scrollPane.setPreferredSize(new Dimension(180, 60));
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

        // Nhãn
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        JLabel label = new JLabel(ten);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label, gbc);

        // Ô nhập liệu
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(component, gbc);
    }

    private void dangKySuKien() {
        // btnLamMoi.addActionListener(this);
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
            luuHoSo();
        } else if (source == btnXoa) {
            xoaHoSo();
        } else if (source == btnHuy) {
            xoaForm();
            datTrangThaiBanDau();
            tblHoSo.clearSelection();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblHoSo.getSelectedRow();

        if (row >= 0) {
            hienThiChiTiet(row);

            // Nếu đang thêm mới thì không chuyển sang chế độ cập nhật
            if (!dangThem) {
                btnThem.setEnabled(true);
                btnLuu.setEnabled(true);
                btnXoa.setEnabled(true);
            }
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

    private void taiDanhSachHoSo() {
        modelTable.setRowCount(0);

        try {
            List<HoSoBenhNhan> danhSach = hoSoDAO.getAllHoSoBenhNhan();

            for (HoSoBenhNhan hs : danhSach) {
                themDongVaoBang(hs);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể tải danh sách hồ sơ bệnh nhân!\n" + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void themDongVaoBang(HoSoBenhNhan hs) {
        Object[] row = {
                hs.getMaHoSo(),

                hs.getMaBenhNhan() == null
                        ? ""
                        : hs.getMaBenhNhan().getMaBenhNhan(),

                hs.getMaNV() == null
                        ? ""
                        : hs.getMaNV().getMaNV(),

                hs.getMaKeHoach() == null
                        ? ""
                        : hs.getMaKeHoach().getMaKeHoach(),

                hs.getNgayKham() == null
                        ? ""
                        : hs.getNgayKham().toString(),

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

    private void timKiemHoSo() {
        String keyword = txtTimKiem.getText().trim();

        if (keyword.isEmpty()) {
            taiDanhSachHoSo();
            return;
        }

        modelTable.setRowCount(0);

        try {
            List<HoSoBenhNhan> danhSach =
                    hoSoDAO.timKiemHoSoBenhNhan(keyword);

            for (HoSoBenhNhan hs : danhSach) {
                themDongVaoBang(hs);
            }

            if (danhSach.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Không tìm thấy hồ sơ phù hợp.",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tìm kiếm hồ sơ thất bại!\n" + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void hienThiChiTiet(int row) {
        if (row < 0 || row >= tblHoSo.getRowCount()) {
            return;
        }

        int modelRow = tblHoSo.convertRowIndexToModel(row);

        txtMaHoSo.setText(layGiaTriBang(modelRow, 0));
        txtMaBN.setText(layGiaTriBang(modelRow, 1));
        txtMaNV.setText(layGiaTriBang(modelRow, 2));
        txtMaKeHoach.setText(layGiaTriBang(modelRow, 3));

        String ngayKham = layGiaTriBang(modelRow, 4);

        try {
            if (!ngayKham.isEmpty()) {
                LocalDate localDate = LocalDate.parse(ngayKham);
                Date date = Date.from(
                        localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
                );
                dcNgayKham.setDate(date);
            } else {
                dcNgayKham.setDate(null);
            }
        } catch (Exception e) {
            dcNgayKham.setDate(null);
        }

        txtTinhTrangRangMieng.setText(layGiaTriBang(modelRow, 5));
        txtChanDoan.setText(layGiaTriBang(modelRow, 6));
        txtPhuongPhapDieuTri.setText(layGiaTriBang(modelRow, 7));
        txtTienSuBenh.setText(layGiaTriBang(modelRow, 8));
        txtDiUng.setText(layGiaTriBang(modelRow, 9));
        txtBenhLyNen.setText(layGiaTriBang(modelRow, 10));
        txtNguoiLienHe.setText(layGiaTriBang(modelRow, 11));
        txtSoDTNLH.setText(layGiaTriBang(modelRow, 12));
        txtGhiChu.setText(layGiaTriBang(modelRow, 13));

        txtMaHoSo.setEditable(false);
    }

    private String layGiaTriBang(int row, int column) {
        Object value = modelTable.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }
    private void themHoSo() {
        // Kiểm tra dữ liệu đã nhập
        if (!kiemTraDuLieu()) {
            return;
        }

        // Sinh mã hồ sơ mới nếu chưa có
        if (txtMaHoSo.getText().trim().isEmpty()) {
            String maHoSoMoi = hoSoDAO.sinhMaHoSoMoi();

            if (maHoSoMoi.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể sinh mã hồ sơ mới!"
                );
                return;
            }

            txtMaHoSo.setText(maHoSoMoi);
        }

        dangThem = true;

        // Lưu dữ liệu hồ sơ
        luuHoSo();
    }
    private void luuHoSo() {
        if (!kiemTraDuLieu()) {
            return;
        }

        LocalDate ngayKham = dcNgayKham.getDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        String maKeHoach = txtMaKeHoach.getText().trim();

        String maBN = txtMaBN.getText().trim();
        String maNV = txtMaNV.getText().trim();
        String maKH = txtMaKeHoach.getText().trim();

        BenhNhan benhNhan = new BenhNhan();
        benhNhan.setMaBenhNhan(maBN);

        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(maNV);

        KeHoachDieuTri keHoach = null;

        if (!maKH.isEmpty()) {
            keHoach = new KeHoachDieuTri();
            keHoach.setMaKeHoach(maKH);
        }

//        LocalDate ngayKham = dcNgayKham.getDate()
//                .toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate();

        HoSoBenhNhan hs = new HoSoBenhNhan(
                txtMaHoSo.getText().trim(),
                benhNhan,
                nhanVien,
                keHoach,
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

        boolean thanhCong;

        try {
            if (dangThem) {
                thanhCong = hoSoDAO.insert(hs);
            } else {
                thanhCong = hoSoDAO.update(hs);
            }

            if (thanhCong) {
                String thongBao = dangThem
                        ? "Thêm hồ sơ bệnh nhân thành công!"
                        : "Cập nhật hồ sơ bệnh nhân thành công!";

                JOptionPane.showMessageDialog(
                        this,
                        thongBao,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                taiDanhSachHoSo();
                xoaForm();
                datTrangThaiBanDau();
                tblHoSo.clearSelection();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể lưu hồ sơ. Vui lòng kiểm tra dữ liệu!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lưu hồ sơ thất bại!\n" + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean kiemTraDuLieu() {
        if (txtMaBN.getText().trim().isEmpty()) {
            hienThiCanhBao("Vui lòng nhập mã bệnh nhân.");
            txtMaBN.requestFocusInWindow();
            return false;
        }

        if (txtMaNV.getText().trim().isEmpty()) {
            hienThiCanhBao("Vui lòng nhập mã nhân viên.");
            txtMaNV.requestFocusInWindow();
            return false;
        }

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
            hienThiCanhBao("Số điện thoại người liên hệ phải gồm 10 chữ số.");
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

    private void xoaHoSo() {
        String maHoSo = txtMaHoSo.getText().trim();

        if (maHoSo.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn hồ sơ cần xóa.",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa hồ sơ " + maHoSo + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean thanhCong = hoSoDAO.delete(maHoSo);

            if (thanhCong) {
                JOptionPane.showMessageDialog(
                        this,
                        "Xóa hồ sơ thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                taiDanhSachHoSo();
                xoaForm();
                datTrangThaiBanDau();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể xóa hồ sơ. Hồ sơ có thể đang được tham chiếu ở bảng khác.",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Xóa hồ sơ thất bại!\n" + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void xoaForm() {
        txtMaHoSo.setText("");
        txtMaBN.setText("");
        txtMaNV.setText("");
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
    }

    private void datTrangThaiBanDau() {
        dangThem = false;

        txtMaHoSo.setEditable(false);

        btnThem.setEnabled(true);
        btnLuu.setEnabled(false);
        btnXoa.setEnabled(false);
    }
}