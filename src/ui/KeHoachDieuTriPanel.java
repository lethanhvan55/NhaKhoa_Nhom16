package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class KeHoachDieuTriPanel extends JPanel implements ActionListener, MouseListener {

    private Color mauChuDao = GiaoDienStyle.MAU_NAVY;
    private Color mauNen = GiaoDienStyle.MAU_NEN;
    private Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JPanel pnlTieuDe;
    private JPanel pnlChiTiet;
    private JPanel pnlThongTin;
    private JPanel pnlDichVu;

    private JLabel lblTongChiPhi;

    // Các thành phần tìm kiếm và lọc (Dùng JDateChooser cho Từ ngày & Đến ngày)
    private JTextField txtTimKiem;
    private JComboBox<String> cboTrangThai;
    private JDateChooser dcTuNgay;
    private JDateChooser dcDenNgay;

    // Các nhãn hiển thị thông tin chi tiết ở panel bên trái
    private JLabel lblGiaTriMaKH, lblGiaTriMaBN, lblGiaTriTenBN, lblGiaTriChanDoan;
    private JLabel lblGiaTriNgayLap, lblGiaTriNgayBD, lblGiaTriNgayKT, lblGiaTriChiPhi, lblGiaTriTrangThai;

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

    public KeHoachDieuTriPanel() {
        khoiTaoGiaoDien();
        dangKySuKien();
        loadDanhSachKeHoachTuCSDL();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(12, 12));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoTieuDe();

        JPanel pnlDanhSach = taoKhuVucDanhSach();
        pnlChiTiet = taoKhuVucChiTiet();

        JSplitPane splNoiDung = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                pnlDanhSach,
                pnlChiTiet
        );

        splNoiDung.setResizeWeight(0.55);
        splNoiDung.setDividerLocation(0.55);
        splNoiDung.setContinuousLayout(true);
        splNoiDung.setOneTouchExpandable(false);
        splNoiDung.setBorder(null);

        add(splNoiDung, BorderLayout.CENTER);
    }

    private void taoTieuDe() {
        pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(mauNen);

        btnThem = GiaoDienStyle.taoNut(
                "+ Thêm kế hoạch điều trị",
                GiaoDienStyle.MAU_TEAL,
                210
        );

        JPanel pnlNutRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pnlNutRight.setBackground(mauNen);
        pnlNutRight.add(btnThem);

        pnlTieuDe.add(pnlNutRight, BorderLayout.EAST);
        add(pnlTieuDe, BorderLayout.NORTH);
    }

    private JPanel taoKhuVucDanhSach() {
        JPanel pnlDanhSach = new JPanel(new BorderLayout(8, 8));
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
                                "Tìm kiếm và bộ lọc",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 14),
                                mauChuDao
                        )
                )
        );

        JPanel pnlLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        pnlLoc.setBackground(mauTrang);

        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(150, 32));

        cboTrangThai = new JComboBox<>(new String[]{
                "Tất cả trạng thái", "Chờ duyệt", "Đang điều trị", "Hoàn thành", "Đã hủy"
        });
        cboTrangThai.setPreferredSize(new Dimension(135, 32));

        // Tích hợp JDateChooser cho Từ ngày và Đến ngày
        dcTuNgay = new JDateChooser();
        dcTuNgay.setDateFormatString("dd/MM/yyyy");
        dcTuNgay.setPreferredSize(new Dimension(120, 32));
        dcTuNgay.setDate(new Date());

        dcDenNgay = new JDateChooser();
        dcDenNgay.setDateFormatString("dd/MM/yyyy");
        dcDenNgay.setPreferredSize(new Dimension(120, 32));
        dcDenNgay.setDate(new Date());

        btnTimKiem = GiaoDienStyle.taoNut("Tìm kiếm", mauChuDao, 105);
        btnLamMoi = GiaoDienStyle.taoNut("Làm mới", GiaoDienStyle.MAU_PHU, 95);

        pnlLoc.add(new JLabel("Mã KH / BN:"));
        pnlLoc.add(txtTimKiem);
        pnlLoc.add(new JLabel("Trạng thái:"));
        pnlLoc.add(cboTrangThai);
        pnlLoc.add(new JLabel("Từ:"));
        pnlLoc.add(dcTuNgay);
        pnlLoc.add(new JLabel("Đến:"));
        pnlLoc.add(dcDenNgay);
        pnlLoc.add(btnTimKiem);
        pnlLoc.add(btnLamMoi);

        pnlBoLoc.add(pnlLoc, BorderLayout.CENTER);
        return pnlBoLoc;
    }

    private JPanel taoBangKeHoach() {
        JPanel pnlBang = new JPanel(new BorderLayout(5, 5));
        pnlBang.setBackground(mauTrang);
        pnlBang.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Danh sách kế hoạch điều trị",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 14),
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
        tblKeHoach.setRowHeight(35);

        int[] doRong = {100, 75, 160, 120, 115, 115, 140, 120};
        for (int i = 0; i < doRong.length; i++) {
            tblKeHoach.getColumnModel().getColumn(i).setPreferredWidth(doRong[i]);
        }

        JScrollPane scrKeHoach = new JScrollPane(tblKeHoach);
        scrKeHoach.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrKeHoach.getViewport().setBackground(Color.WHITE);

        pnlBang.add(scrKeHoach, BorderLayout.CENTER);
        return pnlBang;
    }

    private JPanel taoKhuVucChiTiet() {
        JPanel pnlNoiDung = new JPanel(new BorderLayout(12, 0));
        pnlNoiDung.setBackground(mauNen);

        pnlThongTin = new JPanel(new GridLayout(0, 2, 8, 10));
        pnlThongTin.setBackground(mauTrang);
        pnlThongTin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Khởi tạo các nhãn giá trị chi tiết
        lblGiaTriMaKH = new JLabel("KH0001");
        lblGiaTriMaBN = new JLabel("BN0001");
        lblGiaTriTenBN = new JLabel("Nguyễn Văn An");
        lblGiaTriChanDoan = new JLabel("Sâu răng");
        lblGiaTriNgayLap = new JLabel("01/03/2025");
        lblGiaTriNgayBD = new JLabel("01/03/2025");
        lblGiaTriNgayKT = new JLabel("15/03/2025");
        lblGiaTriChiPhi = new JLabel("2.500.000 VNĐ");
        lblGiaTriTrangThai = new JLabel("Hoàn thành");

        themDongThongTin("Mã kế hoạch:", lblGiaTriMaKH);
        themDongThongTin("Mã bệnh nhân:", lblGiaTriMaBN);
        themDongThongTin("Bệnh nhân:", lblGiaTriTenBN);
        themDongThongTin("Chẩn đoán:", lblGiaTriChanDoan);
        themDongThongTin("Ngày lập:", lblGiaTriNgayLap);
        themDongThongTin("Ngày bắt đầu:", lblGiaTriNgayBD);
        themDongThongTin("Ngày kết thúc:", lblGiaTriNgayKT);
        themDongThongTin("Chi phí dự kiến:", lblGiaTriChiPhi);
        themDongThongTin("Trạng thái:", lblGiaTriTrangThai);

        // Đã xóa nút "Chi tiết", chỉ còn nút "Sửa" và "Xóa"
        JPanel pnlNutChiTiet = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        pnlNutChiTiet.setBackground(mauTrang);

        btnSua = GiaoDienStyle.taoNut("Sửa", GiaoDienStyle.MAU_TEAL, 100);
        btnXoa = GiaoDienStyle.taoNut("Xóa", GiaoDienStyle.MAU_DO, 100);

        pnlNutChiTiet.add(btnSua);
        pnlNutChiTiet.add(btnXoa);

        JPanel pnlThongTinChung = new JPanel(new BorderLayout(5, 8));
        pnlThongTinChung.setBackground(mauTrang);
        pnlThongTinChung.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Thông tin chi tiết",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 14),
                                mauChuDao
                        )
                )
        );

        pnlThongTinChung.add(pnlThongTin, BorderLayout.CENTER);
        pnlThongTinChung.add(pnlNutChiTiet, BorderLayout.SOUTH);

        // Bảng dịch vụ trong kế hoạch
        pnlDichVu = new JPanel(new BorderLayout(5, 8));
        pnlDichVu.setBackground(mauTrang);
        pnlDichVu.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Dịch vụ trong kế hoạch",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 14),
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
        tblDichVu.setRowHeight(30);

        int[] doRongDichVu = {40, 65, 140, 40, 100, 110};
        for (int i = 0; i < doRongDichVu.length; i++) {
            tblDichVu.getColumnModel().getColumn(i).setPreferredWidth(doRongDichVu[i]);
        }

        JScrollPane scrDichVu = new JScrollPane(tblDichVu);
        scrDichVu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrDichVu.getViewport().setBackground(Color.WHITE);

        lblTongChiPhi = new JLabel("Tổng chi phí dự kiến: 0 VNĐ  ", SwingConstants.RIGHT);
        lblTongChiPhi.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongChiPhi.setForeground(mauChuDao);
        lblTongChiPhi.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        pnlDichVu.add(scrDichVu, BorderLayout.CENTER);
        pnlDichVu.add(lblTongChiPhi, BorderLayout.SOUTH);

        JSplitPane splChiTiet = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                pnlThongTinChung,
                pnlDichVu
        );

        splChiTiet.setResizeWeight(0.45);
        splChiTiet.setContinuousLayout(true);
        splChiTiet.setOneTouchExpandable(false);
        splChiTiet.setBorder(null);

        pnlNoiDung.add(splChiTiet, BorderLayout.CENTER);
        return pnlNoiDung;
    }

    private void themDongThongTin(String ten, JLabel lblGiaTri) {
        JLabel lblTen = new JLabel(ten);
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTen.setForeground(new Color(60, 80, 100));

        lblGiaTri.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblGiaTri.setForeground(new Color(30, 40, 50));

        pnlThongTin.add(lblTen);
        pnlThongTin.add(lblGiaTri);
    }

    // =========================================================================
    // ĐĂNG KÝ SỰ KIỆN
    // =========================================================================
    private void dangKySuKien() {
        btnThem.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);

        tblKeHoach.addMouseListener(this);
    }

    // =========================================================================
    // XỬ LÝ SỰ KIỆN ACTIONLISTENER (NÚT BẤM)
    // =========================================================================
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

    // =========================================================================
    // XỬ LÝ SỰ KIỆN MOUSELISTENER (CHỌN DÒNG TRÊN BẢNG)
    // =========================================================================
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

    // =========================================================================
    // CÁC HÀM KHUNG NGHIỆP VỤ & KẾT NỐI DAO / SQL SERVER (STUBS)
    // =========================================================================

    private void loadDanhSachKeHoachTuCSDL() {
        modelKeHoach.setRowCount(0);
        try {
            // TODO: Viết code gọi DAO để lấy danh sách từ SQL Server
            // List<KeHoachDieuTri> list = keHoachDao.getAll();
            // for(KeHoachDieuTri kh : list) { modelKeHoach.addRow(...); }

            // Dữ liệu mẫu giả lập chạy thử giao diện
            Object[][] duLieuMau = {
                    {"KH0001", "BN0001", "Nguyễn Văn An", "Sâu răng", "01/03/2025", "15/03/2025", "2.500.000", "Hoàn thành"},
                    {"KH0002", "BN0002", "Trần Thị Bích", "Niềng răng", "05/01/2025", "05/01/2027", "25.000.000", "Đang điều trị"},
                    {"KH0003", "BN0001", "Nguyễn Văn An", "Viêm nướu", "10/06/2025", "20/06/2025", "1.200.000", "Hoàn thành"}
            };
            for (Object[] row : duLieuMau) {
                modelKeHoach.addRow(row);
            }

            if (modelKeHoach.getRowCount() > 0) {
                tblKeHoach.setRowSelectionInterval(0, 0);
                hienThiChiTietLenPanel(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDanhSachDichVuTheoKeHoach(String maKH) {
        modelDichVu.setRowCount(0);
        try {
            // TODO: Viết code gọi DAO lấy danh sách dịch vụ theo mã kế hoạch từ SQL Server
            if (maKH.equals("KH0002")) {
                modelDichVu.addRow(new Object[]{"1", "DV001", "Khám và tư vấn", "1", "500.000", "500.000"});
                modelDichVu.addRow(new Object[]{"2", "DV002", "Niềng răng", "1", "20.000.000", "20.000.000"});
                modelDichVu.addRow(new Object[]{"3", "DV003", "Chụp X-Quang", "1", "4.500.000", "4.500.000"});
                lblTongChiPhi.setText("Tổng chi phí dự kiến: 25.000.000 VNĐ  ");
            } else {
                modelDichVu.addRow(new Object[]{"1", "DV001", "Khám tổng quát", "1", "500.000", "500.000"});
                modelDichVu.addRow(new Object[]{"2", "DV004", "Trám răng", "1", "2.000.000", "2.000.000"});
                lblTongChiPhi.setText("Tổng chi phí dự kiến: 2.500.000 VNĐ  ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hienThiChiTietLenPanel(int row) {
        String maKH = String.valueOf(modelKeHoach.getValueAt(row, 0));
        lblGiaTriMaKH.setText(maKH);
        lblGiaTriMaBN.setText(String.valueOf(modelKeHoach.getValueAt(row, 1)));
        lblGiaTriTenBN.setText(String.valueOf(modelKeHoach.getValueAt(row, 2)));
        lblGiaTriChanDoan.setText(String.valueOf(modelKeHoach.getValueAt(row, 3)));
        lblGiaTriNgayLap.setText("01/01/2025");
        lblGiaTriNgayBD.setText(String.valueOf(modelKeHoach.getValueAt(row, 4)));
        lblGiaTriNgayKT.setText(String.valueOf(modelKeHoach.getValueAt(row, 5)));
        lblGiaTriChiPhi.setText(String.valueOf(modelKeHoach.getValueAt(row, 6)) + " VNĐ");
        lblGiaTriTrangThai.setText(String.valueOf(modelKeHoach.getValueAt(row, 7)));

        loadDanhSachDichVuTheoKeHoach(maKH);
    }

    private void xuLyThemKeHoach() {
        // Lấy window cha
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Mở Form Dialog
        FormKeHoachDieuTriDialog dialog = new FormKeHoachDieuTriDialog(parentFrame);
        dialog.setVisible(true);

        // Nạp lại bảng danh sách sau khi đóng dialog
        loadDanhSachKeHoachTuCSDL();
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tuNgay = (dcTuNgay.getDate() != null) ? sdf.format(dcTuNgay.getDate()) : "";
        String denNgay = (dcDenNgay.getDate() != null) ? sdf.format(dcDenNgay.getDate()) : "";

        // TODO: Thực hiện truy vấn lọc dữ liệu từ CSDL dựa trên từ khóa, trạng thái và khoảng thời gian
        JOptionPane.showMessageDialog(this, "Đang tìm kiếm từ khóa: " + tuKhoa + " từ " + tuNgay + " đến " + denNgay);
    }

    private void xuLyLamMoi() {
        txtTimKiem.setText("");
        cboTrangThai.setSelectedIndex(0);
        dcTuNgay.setDate(new Date());
        dcDenNgay.setDate(new Date());
        loadDanhSachKeHoachTuCSDL();
    }

    private void xuLySuaKeHoach() {
        int row = tblKeHoach.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kế hoạch cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // TODO: Mở form chỉnh sửa kế hoạch điều trị
        JOptionPane.showMessageDialog(this, "Chỉnh sửa kế hoạch: " + lblGiaTriMaKH.getText());
    }

    private void xuLyXoaKeHoach() {
        int row = tblKeHoach.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kế hoạch cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa kế hoạch này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maKH = lblGiaTriMaKH.getText();
            // TODO: Gọi DAO thực hiện DELETE trong SQL Server
            modelKeHoach.removeRow(row);
            JOptionPane.showMessageDialog(this, "Đã xóa kế hoạch thành công.");
        }
    }
}