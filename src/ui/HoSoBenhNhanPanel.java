package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class HoSoBenhNhanPanel extends JPanel implements ActionListener, MouseListener {

    private Color mauChuDao = GiaoDienStyle.MAU_NAVY;
    private Color mauNen = GiaoDienStyle.MAU_NEN;
    private Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JTable tblBenhNhan;
    private DefaultTableModel dtm;

    // Form chi tiết bệnh nhân
    private JTextField txtMaBN;
    private JTextField txtHoTen;
    private JDateChooser dcNgaySinh;
    private JComboBox<String> cboGioiTinhChiTiet;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private JTextField txtDiaChi;
    private JTextArea txtGhiChu;

    // Các nút chức năng
    private JButton btnThemMoi, btnSua, btnXoa; // Nút dưới bảng
    private JButton btnLuu, btnHuyBo;         // Nút dưới form chi tiết

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private boolean dangThem = false; // Cờ phân biệt trạng thái Thêm mới hay Cập nhật

    public HoSoBenhNhanPanel() {
        khoiTaoGiaoDien();
        dangKySuKien();
        loadDanhSachBenhNhanTuCSDL();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(10, 10));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel pnlDanhSach = taoDanhSachBenhNhan();
        JPanel pnlChiTiet = taoChiTietBenhNhan();

        JSplitPane splNoiDung = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, pnlDanhSach, pnlChiTiet
        );

        splNoiDung.setResizeWeight(0.55);
        splNoiDung.setDividerLocation(650);
        splNoiDung.setContinuousLayout(true);
        splNoiDung.setBorder(null);

        add(splNoiDung, BorderLayout.CENTER);
    }

    private JPanel taoDanhSachBenhNhan() {
        JPanel pnlDanhSach = new JPanel(new BorderLayout(8, 8));
        pnlDanhSach.setBackground(mauTrang);
        pnlDanhSach.setBorder(BorderFactory.createTitledBorder("Danh sách bệnh nhân"));

        String[] cot = {"Mã BN", "Họ tên", "Ngày sinh", "Giới tính", "SĐT", "Địa chỉ"};

        dtm = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblBenhNhan = new JTable(dtm);
        GiaoDienStyle.dinhDangBang(tblBenhNhan);
        tblBenhNhan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrBang = new JScrollPane(tblBenhNhan);
        scrBang.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Thanh công cụ chứa các nút Thêm, Sửa, Xóa phía dưới bảng
        JPanel pnlThaoTacNhanh = new JPanel();
        pnlThaoTacNhanh.setBackground(mauTrang);
        btnThemMoi = GiaoDienStyle.taoNut("Thêm mới", GiaoDienStyle.MAU_NAVY, 110);
        btnSua = GiaoDienStyle.taoNut("Sửa", GiaoDienStyle.MAU_TEAL, 90);
        btnXoa = GiaoDienStyle.taoNut("Xóa", GiaoDienStyle.MAU_DO, 90);

        pnlThaoTacNhanh.add(btnThemMoi);
        pnlThaoTacNhanh.add(btnSua);
        pnlThaoTacNhanh.add(btnXoa);

        pnlDanhSach.add(scrBang, BorderLayout.CENTER);
        pnlDanhSach.add(pnlThaoTacNhanh, BorderLayout.SOUTH);

        return pnlDanhSach;
    }

    private JPanel taoChiTietBenhNhan() {
        JPanel pnlChiTiet = new JPanel(new BorderLayout(5, 5));
        pnlChiTiet.setBackground(mauTrang);
        pnlChiTiet.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));

        JTabbedPane tabThongTin = new JTabbedPane();
        tabThongTin.addTab("Thông tin bệnh nhân", taoTabThongTin());
        tabThongTin.addTab("Hồ sơ bệnh án", taoTabTam("Danh sách hồ sơ bệnh án"));
        tabThongTin.addTab("Kế hoạch điều trị", taoTabTam("Kế hoạch điều trị của bệnh nhân"));
        tabThongTin.addTab("Lịch hẹn", taoTabTam("Lịch hẹn của bệnh nhân"));

        // Khu vực nút lưu / hủy ở đáy form chi tiết
        JPanel pnlNut = new JPanel();
        pnlNut.setBackground(mauTrang);
        btnLuu = GiaoDienStyle.taoNut("Lưu", GiaoDienStyle.MAU_TEAL, 110);
        btnHuyBo = GiaoDienStyle.taoNut("Hủy bỏ", GiaoDienStyle.MAU_DO, 110);

        pnlNut.add(btnLuu);
        pnlNut.add(btnHuyBo);

        pnlChiTiet.add(tabThongTin, BorderLayout.CENTER);
        pnlChiTiet.add(pnlNut, BorderLayout.SOUTH);

        return pnlChiTiet;
    }

    private JPanel taoTabThongTin() {
        JPanel pnlThongTin = new JPanel(new GridBagLayout());
        pnlThongTin.setBackground(mauTrang);

        txtMaBN = new JTextField();
        txtMaBN.setEditable(false); // Mã tự sinh không cho sửa trực tiếp
        txtHoTen = new JTextField();

        dcNgaySinh = new JDateChooser();
        dcNgaySinh.setDateFormatString("dd/MM/yyyy");

        cboGioiTinhChiTiet = new JComboBox<String>(new String[]{"Nam", "Nữ"});

        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();

        txtGhiChu = new JTextArea(8, 25);
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        txtGhiChu.setFont(new Font("Arial", Font.PLAIN, 13));

        themTruong(pnlThongTin, "Mã bệnh nhân:", txtMaBN, 0);
        themTruong(pnlThongTin, "Họ và tên:", txtHoTen, 1);
        themTruong(pnlThongTin, "Ngày sinh:", dcNgaySinh, 2);
        themTruong(pnlThongTin, "Giới tính:", cboGioiTinhChiTiet, 3);
        themTruong(pnlThongTin, "Số điện thoại:", txtSDT, 4);
        themTruong(pnlThongTin, "Email:", txtEmail, 5);
        themTruong(pnlThongTin, "Địa chỉ:", txtDiaChi, 6);
        themTruong(pnlThongTin, "Ghi chú:", new JScrollPane(txtGhiChu), 7);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 8; gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlThongTin.add(new JPanel(), gbc);

        JScrollPane scrThongTin = new JScrollPane(pnlThongTin);
        scrThongTin.setBorder(BorderFactory.createEmptyBorder());

        JPanel pnlChuaThongTin = new JPanel(new BorderLayout());
        pnlChuaThongTin.setBackground(mauTrang);
        pnlChuaThongTin.add(scrThongTin, BorderLayout.CENTER);

        return pnlChuaThongTin;
    }

    private void themTruong(JPanel pnl, String ten, Component control, int hang) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = hang;
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel lbl = new JLabel(ten);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        pnl.add(lbl, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (ten.equals("Ghi chú:")) {
            control.setPreferredSize(new Dimension(250, 150));
        } else {
            control.setPreferredSize(new Dimension(170, 30));
        }

        pnl.add(control, gbc);
    }

    private JPanel taoTabTam(String noiDung) {
        JPanel pnlTam = new JPanel(new BorderLayout());
        pnlTam.setBackground(mauTrang);

        JLabel lblNoiDung = new JLabel(noiDung);
        lblNoiDung.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNoiDung.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnlTam.add(lblNoiDung, BorderLayout.NORTH);
        return pnlTam;
    }

    // =========================================================================
    // ĐĂNG KÝ SỰ KIỆN
    // =========================================================================
    private void dangKySuKien() {
        btnThemMoi.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLuu.addActionListener(this);
        btnHuyBo.addActionListener(this);

        tblBenhNhan.addMouseListener(this);
    }

    // =========================================================================
    // XỬ LÝ SỰ KIỆN ACTIONLISTENER
    // =========================================================================
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnThemMoi)) {
            xuLyChuanBiThem();
        } else if (source.equals(btnSua)) {
            xuLyChuanBiSua();
        } else if (source.equals(btnXoa)) {
            xuLyXoaBenhNhan();
        } else if (source.equals(btnLuu)) {
            xuLyLuuThongTin();
        } else if (source.equals(btnHuyBo)) {
            clearForm();
        }
    }

    // =========================================================================
    // XỬ LÝ SỰ KIỆN CHUỘT (MOUSELISTENER) TRÊN BẢNG
    // =========================================================================
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblBenhNhan.getSelectedRow();
        if (row >= 0) {
            hienThiThongTinLenForm(row);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // =========================================================================
    // CÁC HÀM KHUNG NGHIỆP VỤ (SẴN SÀNG VIẾT CODE DAO / SQL SERVER)
    // =========================================================================

    private void loadDanhSachBenhNhanTuCSDL() {
        dtm.setRowCount(0);
        try {
            // TODO: Viết code gọi DAO để lấy danh sách từ SQL Server
            // List<BenhNhan> list = benhNhanDao.getAll();
            // for(BenhNhan bn : list) { ... dtm.addRow(...); }

            // Dữ liệu mẫu giả lập
            Object[][] duLieuMau = {
                    {"BN0001", "Nguyễn Văn An", "20/05/2003", "Nam", "0912345678", "Gò Vấp"},
                    {"BN0002", "Trần Thị Bích", "15/08/2002", "Nữ", "0987654321", "Quận 12"},
                    {"BN0003", "Lê Minh Hoàng", "12/04/2000", "Nam", "0901234567", "Tân Bình"},
                    {"BN0004", "Phạm Thu Hà", "30/09/1999", "Nữ", "0934567890", "Gò Vấp"},
                    {"BN0005", "Đỗ Minh Quân", "02/01/2001", "Nam", "0978123456", "Hóc Môn"},
                    {"BN0006", "Võ Ngọc Mai", "18/11/2004", "Nữ", "0967891234", "Bình Thạnh"}
            };
            for (Object[] row : duLieuMau) {
                dtm.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xuLyChuanBiThem() {
        dangThem = true;
        clearForm();
        // TODO: Sinh mã bệnh nhân tự động từ CSDL (Ví dụ: BN0007)
        txtMaBN.setText("BN0007");
        JOptionPane.showMessageDialog(this, "Đang ở chế độ Thêm mới bệnh nhân.");
    }

    private void xuLyChuanBiSua() {
        int row = tblBenhNhan.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bệnh nhân cần sửa trên bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        dangThem = false;
        JOptionPane.showMessageDialog(this, "Đang ở chế độ Cập nhật thông tin bệnh nhân.");
    }

    private void xuLyLuuThongTin() {
        String maBN = txtMaBN.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();

        if (hoTen.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Họ tên và Số điện thoại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (dangThem) {
            // TODO: Gọi DAO thực hiện INSERT vào SQL Server
            JOptionPane.showMessageDialog(this, "Đã thêm bệnh nhân mới thành công!");
        } else {
            // TODO: Gọi DAO thực hiện UPDATE vào SQL Server
            JOptionPane.showMessageDialog(this, "Đã cập nhật thông tin thành công!");
        }

        loadDanhSachBenhNhanTuCSDL();
        clearForm();
    }

    private void xuLyXoaBenhNhan() {
        int row = tblBenhNhan.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bệnh nhân cần xóa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa bệnh nhân này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maBN = String.valueOf(dtm.getValueAt(row, 0));
            // TODO: Gọi DAO thực hiện DELETE trong SQL Server

            dtm.removeRow(row);
            clearForm();
            JOptionPane.showMessageDialog(this, "Đã xóa bệnh nhân thành công.");
        }
    }

    private void hienThiThongTinLenForm(int row) {
        txtMaBN.setText(String.valueOf(dtm.getValueAt(row, 0)));
        txtHoTen.setText(String.valueOf(dtm.getValueAt(row, 1)));

        try {
            String ngaySinhStr = String.valueOf(dtm.getValueAt(row, 2));
            dcNgaySinh.setDate(sdf.parse(ngaySinhStr));
        } catch (Exception e) {
            dcNgaySinh.setDate(null);
        }

        cboGioiTinhChiTiet.setSelectedItem(String.valueOf(dtm.getValueAt(row, 3)));
        txtSDT.setText(String.valueOf(dtm.getValueAt(row, 4)));
        txtDiaChi.setText(String.valueOf(dtm.getValueAt(row, 5)));
    }

    private void clearForm() {
        tblBenhNhan.clearSelection();
        txtMaBN.setText("");
        txtHoTen.setText("");
        dcNgaySinh.setDate(new Date());
        cboGioiTinhChiTiet.setSelectedIndex(0);
        txtSDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtGhiChu.setText("");
        dangThem = false;
        txtHoTen.requestFocus();
    }
}