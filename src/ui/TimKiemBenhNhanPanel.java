package ui;

import utils.GiaoDienStyle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import dao.BenhNhan_DAO;
import model.BenhNhan;
import java.util.List;

import dao.HoSoBenhNhan_DAO;
import dao.KeHoachDieuTri_DAO;
import model.HoSoBenhNhan;
import model.KeHoachDieuTri;

public class TimKiemBenhNhanPanel extends JPanel implements ActionListener, MouseListener {

    private final Color mauNen = GiaoDienStyle.MAU_NEN;
    private final Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JTable tblBenhNhan;
    private DefaultTableModel dtm;

    private JTable tblHoSoBenhAn;
    private DefaultTableModel dtmHoSoBenhAn;

    private JTable tblKeHoachDieuTri;
    private DefaultTableModel dtmKeHoachDieuTri;

    private JTextField txtTimKiem;
    private JComboBox<String> cboTimGioiTinh;
    private JButton btnTimKiem, btnLamMoi;

    private JTextField txtMaBN, txtHoTen, txtNgaySinh, txtGioiTinhChiTiet, txtSDT, txtEmail,txtDiaChi;
    private JTextArea txtGhiChu;
    // Các ô thông tin hồ sơ bệnh án
    private JTextField txtMaHoSo, txtMaBenhNhanHS, txtMaNV, txtMaKeHoachHS;
    private JTextField txtNgayKham, txtTinhTrangRangMieng, txtChanDoanHS;
    private JTextField txtPhuongPhapDieuTriHS, txtTienSuBenh, txtDiUng;
    private JTextField txtBenhLyNen, txtNguoiLienHe, txtSoDTNLH;
    private JTextArea txtGhiChuHS;

    // Các ô thông tin kế hoạch điều trị
    private JTextField txtMaKeHoach, txtMaBenhNhanKH, txtChanDoanKH;
    private JTextField txtNgayBatDau, txtNgayKetThuc;
    private JTextField txtChiPhiDuKien, txtTrangThai;

    private BenhNhan_DAO benhNhanDAO = new BenhNhan_DAO();
    private HoSoBenhNhan_DAO hoSoDAO = new HoSoBenhNhan_DAO();
    private KeHoachDieuTri_DAO keHoachDAO = new KeHoachDieuTri_DAO();

    public TimKiemBenhNhanPanel() {
        khoiTaoGiaoDien();
        dangKySuKien();
        loadDuLieuTimKiem();
    }
    public void capNhatDuLieu() {
        // Tải lại danh sách bệnh nhân từ database
        loadDuLieuTimKiem();

        // Nếu bảng có bệnh nhân thì chọn bệnh nhân đầu tiên
        if (dtm.getRowCount() > 0) {
            tblBenhNhan.setRowSelectionInterval(0, 0);
            hienThiThongTinBenhNhan(0);
        } else {
            // Không có bệnh nhân thì xóa toàn bộ thông tin
            xoaThongTinBenhNhan();
        }
    }
    private void hienThiThongTinBenhNhan(int row) {
        if (row < 0 || row >= dtm.getRowCount()) {
            return;
        }

        String maBenhNhan = String.valueOf(dtm.getValueAt(row, 0));

        // Tab 1: Thông tin chung
        txtMaBN.setText(maBenhNhan);
        txtHoTen.setText(String.valueOf(dtm.getValueAt(row, 1)));
        txtNgaySinh.setText(String.valueOf(dtm.getValueAt(row, 2)));
        txtGioiTinhChiTiet.setText(String.valueOf(dtm.getValueAt(row, 3)));
        txtSDT.setText(String.valueOf(dtm.getValueAt(row, 4)));
        txtEmail.setText(String.valueOf(dtm.getValueAt(row, 5)));
        txtDiaChi.setText(String.valueOf(dtm.getValueAt(row, 6)));
        txtGhiChu.setText("Bệnh nhân khám định kỳ tại phòng khám.");
        // Tab 2 và tab 3: Hồ sơ bệnh án, kế hoạch điều trị
        hienThiHoSoVaKeHoach(maBenhNhan);
    }

    private void xoaThongTinBenhNhan() {
        // Tab 1
        txtMaBN.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtGioiTinhChiTiet.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtGhiChu.setText("");

        // Tab 2 và tab 3
        hienThiHoSoVaKeHoach(null);
    }
    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(10, 10));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(taoThanhBoLocMoRong(), BorderLayout.NORTH);
        JPanel pnlDanhSach = taoBangKetQua();
        JPanel pnlChiTiet = taoCacTabChiTiet();

        JSplitPane splNoiDung = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlDanhSach, pnlChiTiet);
        splNoiDung.setResizeWeight(0.50);
        splNoiDung.setDividerLocation(650);
        splNoiDung.setContinuousLayout(true);
        splNoiDung.setBorder(null);

        add(splNoiDung, BorderLayout.CENTER);
    }

    private JPanel taoThanhBoLocMoRong() {
        JPanel pnlFilter = new JPanel(new GridBagLayout());
        pnlFilter.setBackground(mauTrang);
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Bộ lọc tìm kiếm bệnh nhân"),
                new EmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        pnlFilter.add(new JLabel("Nhập nội dung:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(200, 30));
        txtTimKiem.setToolTipText(
                "Nhập mã bệnh nhân, họ tên hoặc số điện thoại"
        );

        pnlFilter.add(txtTimKiem, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        pnlFilter.add(new JLabel("Giới tính:"), gbc);

        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        cboTimGioiTinh = new JComboBox<>(
                new String[]{"Tất cả", "Nam", "Nữ"}
        );
        cboTimGioiTinh.setPreferredSize(new Dimension(130, 30));

        pnlFilter.add(cboTimGioiTinh, gbc);

        // Các nút chức năng
        JPanel pnlNut = new JPanel();
        pnlNut.setBackground(mauTrang);

        btnTimKiem = GiaoDienStyle.taoNut(
                "Tìm kiếm", GiaoDienStyle.MAU_NAVY, 110
        );

        btnLamMoi = GiaoDienStyle.taoNut(
                "Làm mới", GiaoDienStyle.MAU_TEAL, 100
        );

        pnlNut.add(btnTimKiem);
        pnlNut.add(btnLamMoi);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        pnlFilter.add(pnlNut, gbc);

        return pnlFilter;
    }

    private JPanel taoBangKetQua() {
        JPanel pnl = new JPanel(new BorderLayout(8, 8));
        pnl.setBackground(mauTrang);
        pnl.setBorder(BorderFactory.createTitledBorder("Kết quả tìm kiếm bệnh nhân"));

        String[] cot = {"Mã BN", "Họ tên", "Ngày sinh", "Giới tính", "SĐT","Email", "Địa chỉ"};
        dtm = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblBenhNhan = new JTable(dtm);
        GiaoDienStyle.dinhDangBang(tblBenhNhan);
        tblBenhNhan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scr = new JScrollPane(tblBenhNhan);
        scr.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        pnl.add(scr, BorderLayout.CENTER);
        return pnl;
    }

    private JPanel taoCacTabChiTiet() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(mauTrang);
        pnl.setBorder(BorderFactory.createTitledBorder(
                "Thông tin chi tiết bệnh nhân"));

        JTabbedPane tabThongTin = new JTabbedPane();

        tabThongTin.addTab("Thông tin chung", taoTabThongTinChung());
        tabThongTin.addTab("Hồ sơ bệnh án", taoTabHoSoBenhAn());
        tabThongTin.addTab("Kế hoạch điều trị", taoTabKeHoachDieuTri());

        pnl.add(tabThongTin, BorderLayout.CENTER);

        return pnl;
    }

    private JScrollPane taoTabThongTinChung() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(mauTrang);

        txtMaBN = taoOChiTiet();
        txtHoTen = taoOChiTiet();
        txtNgaySinh = taoOChiTiet();
        txtGioiTinhChiTiet = taoOChiTiet();
        txtSDT = taoOChiTiet();
        txtEmail = taoOChiTiet(); // BỔ SUNG DÒNG NÀY
        txtDiaChi = taoOChiTiet();

        txtGhiChu = new JTextArea(4, 20);
        txtGhiChu.setEditable(false);
        txtGhiChu.setBackground(new Color(248, 249, 252));
        txtGhiChu.setLineWrap(true);

        themDongXem(pnl, "Mã bệnh nhân:", txtMaBN, 0);
        themDongXem(pnl, "Họ và tên:", txtHoTen, 1);
        themDongXem(pnl, "Ngày sinh:", txtNgaySinh, 2);
        themDongXem(pnl, "Giới tính:", txtGioiTinhChiTiet, 3);
        themDongXem(pnl, "Số điện thoại:", txtSDT, 4);
        themDongXem(pnl, "Email:", txtEmail, 5);
        themDongXem(pnl, "Địa chỉ:", txtDiaChi, 6);
        themDongXem(pnl, "Ghi chú:", new JScrollPane(txtGhiChu), 7);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weighty = 1;
        pnl.add(new JPanel(), gbc);

        return new JScrollPane(pnl);
    }

    private JTextField taoOChiTiet() {
        JTextField txt = new JTextField();
        txt.setEditable(false);
        txt.setBackground(new Color(248, 249, 252));
        return txt;
    }

    private void themDongXem(JPanel pnl, String ten, Component comp, int hang) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = hang;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnl.add(new JLabel(ten), gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        comp.setPreferredSize(new Dimension(200, comp instanceof JScrollPane ? 70 : 28));
        pnl.add(comp, gbc);
    }

    private JPanel taoTabNoiDungTam(String text) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(mauTrang);
        JLabel lbl = new JLabel(text);
        lbl.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnl.add(lbl, BorderLayout.NORTH);
        return pnl;
    }
    private JScrollPane taoTabHoSoBenhAn() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(mauTrang);

        txtMaHoSo = taoOChiTiet();
        txtMaBenhNhanHS = taoOChiTiet();
        txtMaNV = taoOChiTiet();
        txtMaKeHoachHS = taoOChiTiet();
        txtNgayKham = taoOChiTiet();

        txtTinhTrangRangMieng = taoOChiTiet();
        txtChanDoanHS = taoOChiTiet();
        txtPhuongPhapDieuTriHS = taoOChiTiet();
        txtTienSuBenh = taoOChiTiet();
        txtDiUng = taoOChiTiet();
        txtBenhLyNen = taoOChiTiet();
        txtNguoiLienHe = taoOChiTiet();
        txtSoDTNLH = taoOChiTiet();

        txtGhiChuHS = new JTextArea(3, 20);
        txtGhiChuHS.setEditable(false);
        txtGhiChuHS.setBackground(new Color(248, 249, 252));
        txtGhiChuHS.setLineWrap(true);
        txtGhiChuHS.setWrapStyleWord(true);

        themDongXem(pnl, "Mã hồ sơ:", txtMaHoSo, 0);
        themDongXem(pnl, "Mã bệnh nhân:", txtMaBenhNhanHS, 1);
        themDongXem(pnl, "Mã nhân viên:", txtMaNV, 2);
        themDongXem(pnl, "Mã kế hoạch:", txtMaKeHoachHS, 3);
        themDongXem(pnl, "Ngày khám:", txtNgayKham, 4);

        themDongXem(pnl, "Tình trạng răng miệng:", txtTinhTrangRangMieng, 5);
        themDongXem(pnl, "Chẩn đoán:", txtChanDoanHS, 6);
        themDongXem(pnl, "Phương pháp điều trị:", txtPhuongPhapDieuTriHS, 7);
        themDongXem(pnl, "Tiền sử bệnh:", txtTienSuBenh, 8);
        themDongXem(pnl, "Dị ứng:", txtDiUng, 9);
        themDongXem(pnl, "Bệnh lý nền:", txtBenhLyNen, 10);
        themDongXem(pnl, "Người liên hệ:", txtNguoiLienHe, 11);
        themDongXem(pnl, "SĐT người liên hệ:", txtSoDTNLH, 12);
        themDongXem(pnl, "Ghi chú:", new JScrollPane(txtGhiChuHS), 13);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.weighty = 1;
        pnl.add(new JPanel(), gbc);

        return new JScrollPane(pnl);
    }

    private JScrollPane taoTabKeHoachDieuTri() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(mauTrang);

        txtMaKeHoach = taoOChiTiet();
        txtMaBenhNhanKH = taoOChiTiet();
        txtChanDoanKH = taoOChiTiet();
        txtNgayBatDau = taoOChiTiet();
        txtNgayKetThuc = taoOChiTiet();
        txtChiPhiDuKien = taoOChiTiet();
        txtTrangThai = taoOChiTiet();

        themDongXem(pnl, "Mã kế hoạch:", txtMaKeHoach, 0);
        themDongXem(pnl, "Mã bệnh nhân:", txtMaBenhNhanKH, 1);
        themDongXem(pnl, "Chẩn đoán:", txtChanDoanKH, 2);
        themDongXem(pnl, "Ngày bắt đầu:", txtNgayBatDau, 3);
        themDongXem(pnl, "Ngày kết thúc:", txtNgayKetThuc, 4);
        themDongXem(pnl, "Chi phí dự kiến:", txtChiPhiDuKien, 5);
        themDongXem(pnl, "Trạng thái:", txtTrangThai, 6);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weighty = 1;
        pnl.add(new JPanel(), gbc);

        return new JScrollPane(pnl);
    }
    private void hienThiHoSoVaKeHoach(String maBenhNhan) {

        // Xóa thông tin hồ sơ bệnh án
        txtMaHoSo.setText("");
        txtMaBenhNhanHS.setText("");
        txtMaNV.setText("");
        txtMaKeHoachHS.setText("");
        txtNgayKham.setText("");
        txtTinhTrangRangMieng.setText("");
        txtChanDoanHS.setText("");
        txtPhuongPhapDieuTriHS.setText("");
        txtTienSuBenh.setText("");
        txtDiUng.setText("");
        txtBenhLyNen.setText("");
        txtNguoiLienHe.setText("");
        txtSoDTNLH.setText("");
        txtGhiChuHS.setText("");

        // Xóa thông tin kế hoạch điều trị
        txtMaKeHoach.setText("");
        txtMaBenhNhanKH.setText("");
        txtChanDoanKH.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtChiPhiDuKien.setText("");
        txtTrangThai.setText("");

        if (maBenhNhan == null || maBenhNhan.trim().isEmpty()) {
            return;
        }

        // ================= HỒ SƠ BỆNH ÁN =================
        List<HoSoBenhNhan> dsHoSo =
                hoSoDAO.getHoSoTheoMaBenhNhan(maBenhNhan);

        if (dsHoSo != null && !dsHoSo.isEmpty()) {
            HoSoBenhNhan hs = dsHoSo.get(0);

            txtMaHoSo.setText(hs.getMaHoSo());
            txtMaBenhNhanHS.setText(maBenhNhan);

            if (hs.getMaNV() != null) {
                txtMaNV.setText(hs.getMaNV().getMaNV());
            }

            if (hs.getMaKeHoach() != null) {
                txtMaKeHoachHS.setText(
                        hs.getMaKeHoach().getMaKeHoach()
                );
            }

            txtNgayKham.setText(
                    hs.getNgayKham() != null
                            ? hs.getNgayKham().toString() : ""
            );

            txtTinhTrangRangMieng.setText(
                    hs.getTinhTrangRangMieng() != null
                            ? hs.getTinhTrangRangMieng() : ""
            );

            txtChanDoanHS.setText(
                    hs.getChanDoan() != null
                            ? hs.getChanDoan() : ""
            );

            txtPhuongPhapDieuTriHS.setText(
                    hs.getPhuongPhapDieuTri() != null
                            ? hs.getPhuongPhapDieuTri() : ""
            );

            txtTienSuBenh.setText(
                    hs.getTienSuBenh() != null
                            ? hs.getTienSuBenh() : ""
            );

            txtDiUng.setText(
                    hs.getDiUng() != null
                            ? hs.getDiUng() : ""
            );

            txtBenhLyNen.setText(
                    hs.getBenhLyNen() != null
                            ? hs.getBenhLyNen() : ""
            );

            txtNguoiLienHe.setText(
                    hs.getNguoiLienHe() != null
                            ? hs.getNguoiLienHe() : ""
            );

            txtSoDTNLH.setText(
                    hs.getSoDTNLH() != null
                            ? hs.getSoDTNLH() : ""
            );

            txtGhiChuHS.setText(
                    hs.getGhiChu() != null
                            ? hs.getGhiChu() : ""
            );
        }

        // ================= KẾ HOẠCH ĐIỀU TRỊ =================
        List<KeHoachDieuTri> dsKeHoach =
                keHoachDAO.getKeHoachTheoMaBenhNhan(maBenhNhan);

        if (dsKeHoach != null && !dsKeHoach.isEmpty()) {
            KeHoachDieuTri kh = dsKeHoach.get(0);

            txtMaKeHoach.setText(kh.getMaKeHoach());
            txtMaBenhNhanKH.setText(maBenhNhan);

            txtChanDoanKH.setText(
                    kh.getChanDoan() != null
                            ? kh.getChanDoan() : ""
            );

            txtNgayBatDau.setText(
                    kh.getNgayBatDau() != null
                            ? kh.getNgayBatDau().toString() : ""
            );

            txtNgayKetThuc.setText(
                    kh.getNgayKetThuc() != null
                            ? kh.getNgayKetThuc().toString() : ""
            );

            txtChiPhiDuKien.setText(
                    String.valueOf(kh.getChiPhiDuKien())
            );

            txtTrangThai.setText(
                    kh.getTrangThai() != null
                            ? kh.getTrangThai() : ""
            );
        }

        // In kết quả để kiểm tra dữ liệu
        System.out.println("Mã bệnh nhân: " + maBenhNhan);
        System.out.println("Số hồ sơ: "
                + (dsHoSo == null ? 0 : dsHoSo.size()));
        System.out.println("Số kế hoạch: "
                + (dsKeHoach == null ? 0 : dsKeHoach.size()));
    }
    private void dangKySuKien() {
        btnTimKiem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        tblBenhNhan.addMouseListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnTimKiem)) {
            String tuKhoa = txtTimKiem.getText().trim().toLowerCase();
            String gioiTinh = cboTimGioiTinh.getSelectedItem().toString();

            // Tìm kiếm và cập nhật danh sách bệnh nhân
            loadDuLieuTimKiem(tuKhoa, gioiTinh);

            // Sau khi tìm kiếm, hiển thị thông tin của bệnh nhân đầu tiên
            if (dtm.getRowCount() > 0) {
                tblBenhNhan.setRowSelectionInterval(0, 0);

                String maBenhNhan = String.valueOf(dtm.getValueAt(0, 0));

                // Hiển thị hồ sơ bệnh án và kế hoạch điều trị
                hienThiHoSoVaKeHoach(maBenhNhan);

                // Cập nhật thông tin chung của bệnh nhân
                txtMaBN.setText(maBenhNhan);
                txtHoTen.setText(String.valueOf(dtm.getValueAt(0, 1)));
                txtNgaySinh.setText(String.valueOf(dtm.getValueAt(0, 2)));
                txtGioiTinhChiTiet.setText(String.valueOf(dtm.getValueAt(0, 3)));
                txtSDT.setText(String.valueOf(dtm.getValueAt(0, 4)));
                txtEmail.setText(String.valueOf(dtm.getValueAt(0, 5)));
                txtDiaChi.setText(String.valueOf(dtm.getValueAt(0, 6)));
            } else {
                // Không tìm thấy bệnh nhân thì xóa thông tin cũ
                txtMaBN.setText("");
                txtHoTen.setText("");
                txtNgaySinh.setText("");
                txtGioiTinhChiTiet.setText("");
                txtSDT.setText("");
                txtEmail.setText("");
                txtDiaChi.setText("");
                txtGhiChu.setText("");

                hienThiHoSoVaKeHoach(null);
            }

        } else if (e.getSource().equals(btnLamMoi)) {
            txtTimKiem.setText("");
            cboTimGioiTinh.setSelectedIndex(0);

            loadDuLieuTimKiem();

            // Làm mới xong, hiển thị hồ sơ và kế hoạch của bệnh nhân đầu tiên
            if (dtm.getRowCount() > 0) {
                tblBenhNhan.setRowSelectionInterval(0, 0);

                String maBenhNhan = String.valueOf(dtm.getValueAt(0, 0));
                hienThiHoSoVaKeHoach(maBenhNhan);

                txtMaBN.setText(maBenhNhan);
                txtHoTen.setText(String.valueOf(dtm.getValueAt(0, 1)));
                txtNgaySinh.setText(String.valueOf(dtm.getValueAt(0, 2)));
                txtGioiTinhChiTiet.setText(String.valueOf(dtm.getValueAt(0, 3)));
                txtSDT.setText(String.valueOf(dtm.getValueAt(0, 4)));
                txtEmail.setText(String.valueOf(dtm.getValueAt(0, 5)));
                txtDiaChi.setText(String.valueOf(dtm.getValueAt(0, 6)));
            } else {
                hienThiHoSoVaKeHoach(null);
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblBenhNhan.getSelectedRow();

        if (row >= 0) {
            row = tblBenhNhan.convertRowIndexToModel(row);
            hienThiThongTinBenhNhan(row);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // Tải toàn bộ danh sách bệnh nhân
    private void loadDuLieuTimKiem() {
        loadDuLieuTimKiem("", "Tất cả");
    }

    // Tìm kiếm theo mã, họ tên, số điện thoại và giới tính
    private void loadDuLieuTimKiem(String tuKhoa, String gioiTinh) {
        dtm.setRowCount(0);

        List<BenhNhan> ds = benhNhanDAO.getAll();

        for (BenhNhan bn : ds) {
            String maBN = bn.getMaBenhNhan() == null
                    ? "" : bn.getMaBenhNhan().toLowerCase();

            String hoTen = bn.getHoTen() == null
                    ? "" : bn.getHoTen().toLowerCase();

            String sdt = bn.getSoDT() == null
                    ? "" : bn.getSoDT();

            // Tìm theo mã bệnh nhân, họ tên hoặc số điện thoại
            boolean khopTuKhoa = maBN.contains(tuKhoa)
                    || hoTen.contains(tuKhoa)
                    || sdt.contains(tuKhoa);

            // Lọc theo giới tính
            boolean khopGioiTinh = gioiTinh.equals("Tất cả")
                    || (gioiTinh.equals("Nam") && bn.isGioiTinh())
                    || (gioiTinh.equals("Nữ") && !bn.isGioiTinh());

            if (khopTuKhoa && khopGioiTinh) {
                dtm.addRow(new Object[]{
                        bn.getMaBenhNhan(),
                        bn.getHoTen(),
                        bn.getNgaySinh(),
                        bn.isGioiTinh() ? "Nam" : "Nữ",
                        bn.getSoDT(),
                        bn.getEmail(),
                        bn.getDiaChi()
                });
            }
        }
    }
}