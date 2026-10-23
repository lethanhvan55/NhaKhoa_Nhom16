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

public class TimKiemBenhNhanPanel extends JPanel implements ActionListener, MouseListener {

    private final Color mauNen = GiaoDienStyle.MAU_NEN;
    private final Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JTable tblBenhNhan;
    private DefaultTableModel dtm;

    private JTextField txtTimMaBN;
    private JTextField txtTimHoTen;
    private JTextField txtTimSDT;
    private JComboBox<String> cboTimGioiTinh;
    private JButton btnTimKiem, btnLamMoi;

    private JTextField txtMaBN, txtHoTen, txtNgaySinh, txtGioiTinhChiTiet, txtSDT, txtDiaChi;
    private JTextArea txtGhiChu;

    public TimKiemBenhNhanPanel() {
        khoiTaoGiaoDien();
        dangKySuKien();
        loadDuLieuTimKiem();
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
                BorderFactory.createTitledBorder("Bộ lọc tìm kiếm nâng cao"),
                new EmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        pnlFilter.add(new JLabel("Mã bệnh nhân:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTimMaBN = new JTextField();
        txtTimMaBN.setPreferredSize(new Dimension(150, 28));
        pnlFilter.add(txtTimMaBN, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        pnlFilter.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTimHoTen = new JTextField();
        txtTimHoTen.setPreferredSize(new Dimension(150, 28));
        pnlFilter.add(txtTimHoTen, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        pnlFilter.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTimSDT = new JTextField();
        txtTimSDT.setPreferredSize(new Dimension(150, 28));
        pnlFilter.add(txtTimSDT, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        pnlFilter.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cboTimGioiTinh = new JComboBox<>(new String[]{"Tất cả", "Nam", "Nữ"});
        cboTimGioiTinh.setPreferredSize(new Dimension(150, 28));
        pnlFilter.add(cboTimGioiTinh, gbc);

        JPanel pnlNut = new JPanel();
        pnlNut.setBackground(mauTrang);
        btnTimKiem = GiaoDienStyle.taoNut("Tìm kiếm", GiaoDienStyle.MAU_NAVY, 110);
        btnLamMoi = GiaoDienStyle.taoNut("Làm mới", GiaoDienStyle.MAU_TEAL, 100);
        pnlNut.add(btnTimKiem);
        pnlNut.add(btnLamMoi);

        gbc.gridx = 4; gbc.gridy = 0; gbc.gridheight = 2; gbc.anchor = GridBagConstraints.CENTER;
        pnlFilter.add(pnlNut, gbc);

        return pnlFilter;
    }

    private JPanel taoBangKetQua() {
        JPanel pnl = new JPanel(new BorderLayout(8, 8));
        pnl.setBackground(mauTrang);
        pnl.setBorder(BorderFactory.createTitledBorder("Kết quả tìm kiếm bệnh nhân"));

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

        JScrollPane scr = new JScrollPane(tblBenhNhan);
        scr.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        pnl.add(scr, BorderLayout.CENTER);
        return pnl;
    }

    private JPanel taoCacTabChiTiet() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(mauTrang);
        pnl.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết bệnh nhân"));

        JTabbedPane tabThongTin = new JTabbedPane();
        tabThongTin.addTab("Thông tin chung", taoTabThongTinChung());
        tabThongTin.addTab("Hồ sơ bệnh án", taoTabNoiDungTam("Danh sách hồ sơ bệnh án..."));
        tabThongTin.addTab("Kế hoạch điều trị", taoTabNoiDungTam("Kế hoạch điều trị chi tiết..."));
        tabThongTin.addTab("Lịch hẹn", taoTabNoiDungTam("Lịch sử lịch hẹn khám..."));

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
        themDongXem(pnl, "Địa chỉ:", txtDiaChi, 5);
        themDongXem(pnl, "Ghi chú:", new JScrollPane(txtGhiChu), 6);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 7; gbc.weighty = 1;
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

    private void dangKySuKien() {
        btnTimKiem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        tblBenhNhan.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnTimKiem)) {
            String maBN = txtTimMaBN.getText().trim();
            String hoTen = txtTimHoTen.getText().trim();
            String sdt = txtTimSDT.getText().trim();
            String gioiTinh = cboTimGioiTinh.getSelectedItem().toString();

            // TODO: Truyền các giá trị maBN, hoTen, sdt, gioiTinh xuống DAO để lọc trên SQL Server
            JOptionPane.showMessageDialog(this, "Đang thực hiện tìm kiếm bệnh nhân theo bộ lọc...");
        } else if (e.getSource().equals(btnLamMoi)) {
            txtTimMaBN.setText("");
            txtTimHoTen.setText("");
            txtTimSDT.setText("");
            cboTimGioiTinh.setSelectedIndex(0);
            loadDuLieuTimKiem();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblBenhNhan.getSelectedRow();
        if (row >= 0) {
            txtMaBN.setText(String.valueOf(dtm.getValueAt(row, 0)));
            txtHoTen.setText(String.valueOf(dtm.getValueAt(row, 1)));
            txtNgaySinh.setText(String.valueOf(dtm.getValueAt(row, 2)));
            txtGioiTinhChiTiet.setText(String.valueOf(dtm.getValueAt(row, 3)));
            txtSDT.setText(String.valueOf(dtm.getValueAt(row, 4)));
            txtDiaChi.setText(String.valueOf(dtm.getValueAt(row, 5)));
            txtGhiChu.setText("Bệnh nhân khám định kỳ tại phòng khám.");
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private void loadDuLieuTimKiem() {
        dtm.setRowCount(0);
    }
}