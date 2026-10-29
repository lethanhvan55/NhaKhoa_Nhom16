package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import dao.KeHoachDieuTri_DAO;
import model.KeHoachDieuTri;
import model.DichVu;
import utils.GiaoDienStyle;

public class KeHoachDieuTriPanel extends JPanel implements ActionListener, MouseListener {

    private Color mauChuDao = GiaoDienStyle.MAU_NAVY;
    private Color mauNen = GiaoDienStyle.MAU_NEN;
    private Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JPanel pnlChiTiet;
    private JPanel pnlThongTin;
    private JPanel pnlDichVu;

    private JLabel lblTongChiPhi;

    private JTextField txtTimKiem;
    private JComboBox<String> cboTrangThai;
    private JDateChooser dcTuNgay;
    private JDateChooser dcDenNgay;

    private JLabel lblGiaTriMaKH, lblGiaTriMaBN, lblGiaTriTenBN, lblGiaTriChanDoan;
    private JLabel lblGiaTriNgayBD, lblGiaTriNgayKT, lblGiaTriChiPhi, lblGiaTriTrangThai;

    private JButton btnThem;
    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnSua;
    private JButton btnXoa;

    private JTable tblKeHoach;
    private DefaultTableModel modelKeHoach;

    private JTable tblDichVu;
    private DefaultTableModel modelDichVu;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private KeHoachDieuTri_DAO khDao = new KeHoachDieuTri_DAO();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public KeHoachDieuTriPanel() {
        khoiTaoGiaoDien();
        dangKySuKien();
        loadDanhSachKeHoachTuCSDL();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(0, 10));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pnlNoiDungChinh = new JPanel(new BorderLayout(0, 10));
        pnlNoiDungChinh.setBackground(mauNen);

        JPanel pnlDanhSach = taoKhuVucDanhSach();
        pnlChiTiet = taoKhuVucChiTiet();

        pnlDanhSach.setPreferredSize(new Dimension(0, 380));

        pnlNoiDungChinh.add(pnlDanhSach, BorderLayout.NORTH);
        pnlNoiDungChinh.add(pnlChiTiet, BorderLayout.CENTER);

        add(pnlNoiDungChinh, BorderLayout.CENTER);
    }

    private JPanel taoKhuVucDanhSach() {
        JPanel pnlDanhSach = new JPanel(new BorderLayout(0, 6));
        pnlDanhSach.setBackground(mauNen);

        pnlDanhSach.add(taoBoLoc(), BorderLayout.NORTH);
        pnlDanhSach.add(taoBangKeHoach(), BorderLayout.CENTER);

        return pnlDanhSach;
    }

    private JPanel taoBoLoc() {
        JPanel pnlBoLoc = new JPanel(new BorderLayout());
        pnlBoLoc.setBackground(mauTrang);
        pnlBoLoc.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Tìm kiếm và lọc kế hoạch điều trị",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 13),
                                mauChuDao
                        )
                )
        );

        JPanel pnlFormTimKiem = new JPanel(new GridBagLayout());
        pnlFormTimKiem.setBackground(mauTrang);
        pnlFormTimKiem.setBorder(new EmptyBorder(8, 12, 10, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension kichThuocChuan = new Dimension(170, 30);

        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(kichThuocChuan);
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Mã KH hoặc BN...");

        cboTrangThai = new JComboBox<>(new String[]{
                "Tất cả trạng thái", "Chờ duyệt", "Đang điều trị", "Hoàn thành", "Đã hủy"
        });
        cboTrangThai.setPreferredSize(kichThuocChuan);
        cboTrangThai.setBackground(mauTrang);

        dcTuNgay = new JDateChooser();
        dcTuNgay.setDateFormatString("dd/MM/yyyy");
        dcTuNgay.setPreferredSize(kichThuocChuan);

        dcDenNgay = new JDateChooser();
        dcDenNgay.setDateFormatString("dd/MM/yyyy");
        dcDenNgay.setPreferredSize(kichThuocChuan);

        btnThem = GiaoDienStyle.taoNut("+ Thêm kế hoạch", GiaoDienStyle.MAU_TEAL, 300);
        btnLamMoi = GiaoDienStyle.taoNut("Làm mới", GiaoDienStyle.MAU_PHU, 142);
        btnTimKiem = GiaoDienStyle.taoNut("Tìm kiếm", mauChuDao, 142);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        pnlFormTimKiem.add(new JLabel("Mã KH / BN:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.25;
        pnlFormTimKiem.add(txtTimKiem, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        pnlFormTimKiem.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.25;
        pnlFormTimKiem.add(cboTrangThai, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        pnlFormTimKiem.add(new JLabel("Từ ngày:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.25;
        pnlFormTimKiem.add(dcTuNgay, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        pnlFormTimKiem.add(new JLabel("Đến ngày:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.25;
        pnlFormTimKiem.add(dcDenNgay, gbc);

        JPanel pnlNutGroup = new JPanel(new GridBagLayout());
        pnlNutGroup.setBackground(mauTrang);
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.insets = new Insets(2, 0, 2, 0);
        gbcBtn.gridx = 0; gbcBtn.gridy = 0; gbcBtn.gridwidth = 2;
        pnlNutGroup.add(btnThem, gbcBtn);

        JPanel pnlHaiNutmDuoi = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        pnlHaiNutmDuoi.setBackground(mauTrang);
        pnlHaiNutmDuoi.add(btnLamMoi);
        pnlHaiNutmDuoi.add(btnTimKiem);

        gbcBtn.gridy = 1; gbcBtn.gridwidth = 2;
        gbcBtn.insets = new Insets(4, 0, 0, 0);
        pnlNutGroup.add(pnlHaiNutmDuoi, gbcBtn);

        gbc.gridx = 4; gbc.gridy = 0; gbc.gridheight = 2; gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlFormTimKiem.add(pnlNutGroup, gbc);

        pnlBoLoc.add(pnlFormTimKiem, BorderLayout.CENTER);
        return pnlBoLoc;
    }

    private JPanel taoBangKeHoach() {
        JPanel pnlBang = new JPanel(new BorderLayout(0, 0));
        pnlBang.setBackground(mauTrang);
        pnlBang.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Danh sách kế hoạch điều trị",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 13),
                                mauChuDao
                        )
                )
        );

        String[] cot = {
                "Mã kế hoạch", "Mã BN", "Họ tên bệnh nhân", "Chẩn đoán",
                "Ngày bắt đầu", "Ngày kết thúc", "Chi phí dự kiến", "Trạng thái"
        };

        modelKeHoach = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblKeHoach = new JTable(modelKeHoach);
        GiaoDienStyle.dinhDangBang(tblKeHoach);
        tblKeHoach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblKeHoach.setRowHeight(28);

        int[] doRong = {90, 70, 150, 130, 100, 100, 115, 105};
        for (int i = 0; i < doRong.length; i++) {
            tblKeHoach.getColumnModel().getColumn(i).setPreferredWidth(doRong[i]);
        }
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblKeHoach.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblKeHoach.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblKeHoach.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblKeHoach.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        JScrollPane scrKeHoach = new JScrollPane(tblKeHoach);
        scrKeHoach.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        scrKeHoach.getViewport().setBackground(Color.WHITE);

        pnlBang.add(scrKeHoach, BorderLayout.CENTER);
        return pnlBang;
    }

    private JPanel taoKhuVucChiTiet() {
        JPanel pnlNoiDung = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlNoiDung.setBackground(mauNen);

        pnlThongTin = new JPanel(new GridLayout(0, 2, 4, 6));
        pnlThongTin.setBackground(mauTrang);
        pnlThongTin.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        lblGiaTriMaKH = new JLabel("");
        lblGiaTriMaBN = new JLabel("");
        lblGiaTriTenBN = new JLabel("");
        lblGiaTriChanDoan = new JLabel("");
        lblGiaTriNgayBD = new JLabel("");
        lblGiaTriNgayKT = new JLabel("");
        lblGiaTriChiPhi = new JLabel("");
        lblGiaTriTrangThai = new JLabel("");

        themDongThongTin("Mã kế hoạch:", lblGiaTriMaKH);
        themDongThongTin("Mã bệnh nhân:", lblGiaTriMaBN);
        themDongThongTin("Bệnh nhân:", lblGiaTriTenBN);
        themDongThongTin("Chẩn đoán:", lblGiaTriChanDoan);
        themDongThongTin("Ngày bắt đầu:", lblGiaTriNgayBD);
        themDongThongTin("Ngày kết thúc:", lblGiaTriNgayKT);
        themDongThongTin("Chi phí dự kiến:", lblGiaTriChiPhi);
        themDongThongTin("Trạng thái:", lblGiaTriTrangThai);

        JPanel pnlNutChiTiet = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));
        pnlNutChiTiet.setBackground(mauTrang);

        btnSua = GiaoDienStyle.taoNut("Sửa", GiaoDienStyle.MAU_TEAL, 85);
        btnXoa = GiaoDienStyle.taoNut("Xóa", GiaoDienStyle.MAU_DO, 85);

        pnlNutChiTiet.add(btnSua);
        pnlNutChiTiet.add(btnXoa);

        JPanel pnlThongTinChung = new JPanel(new BorderLayout(0, 0));
        pnlThongTinChung.setBackground(mauTrang);
        pnlThongTinChung.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Thông tin chi tiết",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 13),
                                mauChuDao
                        )
                )
        );

        pnlThongTinChung.add(pnlThongTin, BorderLayout.CENTER);
        pnlThongTinChung.add(pnlNutChiTiet, BorderLayout.SOUTH);

        pnlDichVu = new JPanel(new BorderLayout(0, 0));
        pnlDichVu.setBackground(mauTrang);
        pnlDichVu.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Dịch vụ trong kế hoạch",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 13),
                                mauChuDao
                        )
                )
        );

        String[] cotDichVu = {"STT", "Mã DV", "Tên dịch vụ", "SL", "Đơn giá", "Thành tiền"};
        modelDichVu = new DefaultTableModel(null, cotDichVu) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(modelDichVu);
        GiaoDienStyle.dinhDangBang(tblDichVu);
        tblDichVu.setRowHeight(24);

        int[] doRongDichVu = {30, 55, 130, 30, 90, 100};
        for (int i = 0; i < doRongDichVu.length; i++) {
            tblDichVu.getColumnModel().getColumn(i).setPreferredWidth(doRongDichVu[i]);
        }

        JScrollPane scrDichVu = new JScrollPane(tblDichVu);
        scrDichVu.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        scrDichVu.getViewport().setBackground(Color.WHITE);

        lblTongChiPhi = new JLabel("Tổng chi phí dự kiến: 0 VNĐ  ", SwingConstants.RIGHT);
        lblTongChiPhi.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTongChiPhi.setForeground(mauChuDao);
        lblTongChiPhi.setBorder(BorderFactory.createEmptyBorder(6, 5, 6, 10));

        pnlDichVu.add(scrDichVu, BorderLayout.CENTER);
        pnlDichVu.add(lblTongChiPhi, BorderLayout.SOUTH);

        pnlNoiDung.add(pnlThongTinChung);
        pnlNoiDung.add(pnlDichVu);

        return pnlNoiDung;
    }

    private void themDongThongTin(String ten, JLabel lblGiaTri) {
        JLabel lblTen = new JLabel(ten);
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTen.setForeground(new Color(90, 105, 120));

        lblGiaTri.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblGiaTri.setForeground(new Color(30, 40, 50));

        pnlThongTin.add(lblTen);
        pnlThongTin.add(lblGiaTri);
    }

    private void dangKySuKien() {
        btnThem.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);

        tblKeHoach.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnThem)) {
            xuLyThemKeHoach();
        } else if (source.equals(btnTimKiem)) {
            xuLyTimKiem();
        } else if (source.equals(btnLamMoi)) {
            xuLyLamMoi();
        } else if (source.equals(btnSua)) {
            xuLySuaKeHoach();
        } else if (source.equals(btnXoa)) {
            xuLyXoaKeHoach();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblKeHoach.getSelectedRow();
        if (row >= 0) {
            hienThiChiTietLenPanel(row);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public void lamMoiDanhSachKeHoach() {
        loadDanhSachKeHoachTuCSDL();
    }
    private void loadDanhSachKeHoachTuCSDL() {
        modelKeHoach.setRowCount(0);
        try {
            List<KeHoachDieuTri> ds = khDao.getAll();
            for (KeHoachDieuTri kh : ds) {
                String ngayBDStr = kh.getNgayBatDau() != null ? kh.getNgayBatDau().format(dtf) : "";
                String ngayKTStr = kh.getNgayKetThuc() != null ? kh.getNgayKetThuc().format(dtf) : "";
                
                String maBNStr = "";
                String tenBNStr = "";
                if (kh.getMaBenhNhan() != null) {
                    maBNStr = kh.getMaBenhNhan().getMaBenhNhan();
                    tenBNStr = kh.getMaBenhNhan().getHoTen();
                }

                modelKeHoach.addRow(new Object[]{
                        kh.getMaKeHoach(),
                        maBNStr,
                        tenBNStr,
                        kh.getChanDoan(),
                        ngayBDStr,
                        ngayKTStr,
                        String.format("%,.0f", kh.getChiPhiDuKien()),
                        kh.getTrangThai()
                });
            }

            if (modelKeHoach.getRowCount() > 0) {
                tblKeHoach.setRowSelectionInterval(0, 0);
                hienThiChiTietLenPanel(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String trangThai = cboTrangThai.getSelectedItem().toString();
        
        Date tuNgayDate = dcTuNgay.getDate();
        Date denNgayDate = dcDenNgay.getDate();

        LocalDate tuNgay = tuNgayDate != null ? tuNgayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate denNgay = denNgayDate != null ? denNgayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

        if (tuNgay != null && denNgay != null && tuNgay.isAfter(denNgay)) {
            JOptionPane.showMessageDialog(this, "'Từ ngày' không được lớn hơn 'Đến ngày'!", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modelKeHoach.setRowCount(0);
        try {
            List<KeHoachDieuTri> ds = khDao.timKiemNangCao(tuKhoa, trangThai, tuNgay, denNgay);
            for (KeHoachDieuTri kh : ds) {
                String ngayBDStr = kh.getNgayBatDau() != null ? kh.getNgayBatDau().format(dtf) : "";
                String ngayKTStr = kh.getNgayKetThuc() != null ? kh.getNgayKetThuc().format(dtf) : "";
                String maBNStr = "";
                String tenBNStr = "";
                if (kh.getMaBenhNhan() != null) {
                    maBNStr = kh.getMaBenhNhan().getMaBenhNhan();
                    tenBNStr = kh.getMaBenhNhan().getHoTen();
                }

                modelKeHoach.addRow(new Object[]{
                        kh.getMaKeHoach(),
                        maBNStr,
                        tenBNStr,
                        kh.getChanDoan(),
                        ngayBDStr,
                        ngayKTStr,
                        String.format("%,.0f", kh.getChiPhiDuKien()),
                        kh.getTrangThai()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xuLyLamMoi() {
        txtTimKiem.setText("");
        cboTrangThai.setSelectedIndex(0);
        dcTuNgay.setDate(null);
        dcDenNgay.setDate(null);
        loadDanhSachKeHoachTuCSDL();
        txtTimKiem.requestFocus();
    }

    private void xuLyThemKeHoach() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        FormKeHoachDieuTriDialog dialog = new FormKeHoachDieuTriDialog(parentFrame);
        dialog.setVisible(true);
        loadDanhSachKeHoachTuCSDL();
    }

    private void xuLySuaKeHoach() {
        int row = tblKeHoach.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kế hoạch cần sửa từ bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maKH = String.valueOf(modelKeHoach.getValueAt(row, 0));
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        FormKeHoachDieuTriDialog dialog = new FormKeHoachDieuTriDialog(parentFrame, maKH);
        dialog.setVisible(true);
        loadDanhSachKeHoachTuCSDL();
    }

    private void xuLyXoaKeHoach() {
        int row = tblKeHoach.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kế hoạch cần xóa khỏi bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa kế hoạch này khỏi bảng hiển thị?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            modelKeHoach.removeRow(row);
            if (modelDichVu != null) {
                modelDichVu.setRowCount(0);
            }
            
            JOptionPane.showMessageDialog(this, "Đã xóa kế hoạch khỏi bảng thành công.");
        }
    }

    private void hienThiChiTietLenPanel(int row) {
        lblGiaTriMaKH.setText(String.valueOf(modelKeHoach.getValueAt(row, 0)));
        String maBN = String.valueOf(modelKeHoach.getValueAt(row, 1));
        lblGiaTriMaBN.setText(maBN);
        lblGiaTriTenBN.setText(String.valueOf(modelKeHoach.getValueAt(row, 2)));
        lblGiaTriChanDoan.setText(String.valueOf(modelKeHoach.getValueAt(row, 3)));
        lblGiaTriNgayBD.setText(String.valueOf(modelKeHoach.getValueAt(row, 4)));
        lblGiaTriNgayKT.setText(String.valueOf(modelKeHoach.getValueAt(row, 5)));
        lblGiaTriChiPhi.setText(String.valueOf(modelKeHoach.getValueAt(row, 6)) + " VNĐ");
        lblGiaTriTrangThai.setText(String.valueOf(modelKeHoach.getValueAt(row, 7)));
        
        modelDichVu.setRowCount(0);
        try {
            List<DichVu> dsDV = khDao.getDichVuTheoMaBenhNhan(maBN);
            int stt = 1;
            for (DichVu dv : dsDV) {
                int soLuong = 1;
                double donGia = dv.getDonGia();
                double thanhTien = donGia * soLuong;

                modelDichVu.addRow(new Object[]{
                        stt++,
                        dv.getMaDV(),
                        dv.getTenDV(),
                        soLuong,
                        String.format("%,.0f", donGia),
                        String.format("%,.0f", thanhTien)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        lblTongChiPhi.setText("Tổng chi phí dự kiến: " + modelKeHoach.getValueAt(row, 6) + " VNĐ  ");
    }
}