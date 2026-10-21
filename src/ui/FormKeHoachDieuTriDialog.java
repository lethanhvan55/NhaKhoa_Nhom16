package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class FormKeHoachDieuTriDialog extends JDialog implements ActionListener {

    private Color mauChuDao = GiaoDienStyle.MAU_NAVY;
    private Color mauNen = GiaoDienStyle.MAU_NEN;
    private Color mauTrang = GiaoDienStyle.MAU_TRANG;

    // Các trường thông tin chung
    private JTextField txtMaKH;
    private JComboBox<String> cboBenhNhan;
    private JComboBox<String> cboBacSi;
    private JTextField txtChanDoan;
    private JDateChooser dcNgayBatDau;
    private JDateChooser dcNgayKetThuc;
    private JComboBox<String> cboTrangThai;
    private JTextArea txtGhiChu;

    // Các trường chọn dịch vụ
    private JComboBox<String> cboDichVu;
    private JSpinner spnSoLuong;
    private JButton btnThemDV;
    private JButton btnXoaDV;

    // Bảng dịch vụ đã chọn
    private JTable tblDichVu;
    private DefaultTableModel modelDichVu;
    private JLabel lblTongTien;

    // Các nút lưu / hủy
    private JButton btnLuu;
    private JButton btnHuy;

    private DecimalFormat df = new DecimalFormat("#,##0");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private double tongChiPhi = 0;

    public FormKeHoachDieuTriDialog(JFrame parent) {
        super(parent, "Lập Kế Hoạch Điều Trị Mới", true); // Modal Dialog
        khoiTaoGiaoDien();
        dangKySuKien();
        loadDataCombobox();
    }

    private void khoiTaoGiaoDien() {
        setSize(850, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.setBackground(mauNen);
        pnlMain.setBorder(new EmptyBorder(15, 15, 15, 15));

        pnlMain.add(taoFormThongTinChung(), BorderLayout.NORTH);
        pnlMain.add(taoKhuVucDichVu(), BorderLayout.CENTER);
        pnlMain.add(taoPanelNutThaoTac(), BorderLayout.SOUTH);

        add(pnlMain, BorderLayout.CENTER);
    }

    // Panel 1: Thông tin chung của kế hoạch điều trị
    private JPanel taoFormThongTinChung() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(mauTrang);
        pnl.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Thông tin chung kế hoạch",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 14),
                                mauChuDao
                        )
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaKH = new JTextField("KH0007");
        txtMaKH.setEditable(false);
        txtMaKH.setPreferredSize(new Dimension(180, 30));

        cboBenhNhan = new JComboBox<>();
        cboBenhNhan.setPreferredSize(new Dimension(180, 30));

        cboBacSi = new JComboBox<>();
        cboBacSi.setPreferredSize(new Dimension(180, 30));

        txtChanDoan = new JTextField();
        txtChanDoan.setPreferredSize(new Dimension(180, 30));

        dcNgayBatDau = new JDateChooser();
        dcNgayBatDau.setDateFormatString("dd/MM/yyyy");
        dcNgayBatDau.setPreferredSize(new Dimension(180, 30));
        dcNgayBatDau.setDate(new Date());

        dcNgayKetThuc = new JDateChooser();
        dcNgayKetThuc.setDateFormatString("dd/MM/yyyy");
        dcNgayKetThuc.setPreferredSize(new Dimension(180, 30));
        dcNgayKetThuc.setDate(new Date());

        cboTrangThai = new JComboBox<>(new String[]{
                "Chờ duyệt", "Đang điều trị", "Hoàn thành", "Đã hủy"
        });
        cboTrangThai.setPreferredSize(new Dimension(180, 30));

        // Dòng 0: Mã KH & Bệnh nhân
        themTruongForm(pnl, gbc, "Mã kế hoạch:", txtMaKH, 0, 0);
        themTruongForm(pnl, gbc, "Bệnh nhân *:", cboBenhNhan, 2, 0);

        // Dòng 1: Bác sĩ & Chẩn đoán
        themTruongForm(pnl, gbc, "Bác sĩ phụ trách *:", cboBacSi, 0, 1);
        themTruongForm(pnl, gbc, "Chẩn đoán *:", txtChanDoan, 2, 1);

        // Dòng 2: Ngày bắt đầu & Ngày kết thúc
        themTruongForm(pnl, gbc, "Ngày bắt đầu *:", dcNgayBatDau, 0, 2);
        themTruongForm(pnl, gbc, "Ngày kết thúc *:", dcNgayKetThuc, 2, 2);

        // Dòng 3: Trạng thái
        themTruongForm(pnl, gbc, "Trạng thái:", cboTrangThai, 0, 3);

        return pnl;
    }

    private void themTruongForm(JPanel pnl, GridBagConstraints gbc, String label, JComponent comp, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(60, 80, 100));
        pnl.add(lbl, gbc);

        gbc.gridx = x + 1;
        gbc.weightx = 1;
        pnl.add(comp, gbc);
    }

    // Panel 2: Khu vực chọn dịch vụ và bảng hiển thị dịch vụ trong kế hoạch
    private JPanel taoKhuVucDichVu() {
        JPanel pnl = new JPanel(new BorderLayout(5, 8));
        pnl.setBackground(mauTrang);
        pnl.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Chi tiết dịch vụ điều trị",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 14),
                                mauChuDao
                        )
                )
        );

        // Thanh công cụ chọn dịch vụ + số lượng
        JPanel pnlChonDV = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        pnlChonDV.setBackground(mauTrang);

        cboDichVu = new JComboBox<>();
        cboDichVu.setPreferredSize(new Dimension(280, 32));

        spnSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spnSoLuong.setPreferredSize(new Dimension(60, 32));

        btnThemDV = GiaoDienStyle.taoNut("+ Thêm DV", GiaoDienStyle.MAU_TEAL, 105);
        btnXoaDV = GiaoDienStyle.taoNut("Xóa DV", GiaoDienStyle.MAU_DO, 90);

        pnlChonDV.add(new JLabel("Chọn dịch vụ:"));
        pnlChonDV.add(cboDichVu);
        pnlChonDV.add(new JLabel("SL:"));
        pnlChonDV.add(spnSoLuong);
        pnlChonDV.add(btnThemDV);
        pnlChonDV.add(btnXoaDV);

        pnl.add(pnlChonDV, BorderLayout.NORTH);

        // Bảng danh sách dịch vụ
        String[] cot = {"Mã DV", "Tên dịch vụ", "Số lượng", "Đơn giá (VNĐ)", "Thành tiền (VNĐ)"};
        modelDichVu = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(modelDichVu);
        GiaoDienStyle.dinhDangBang(tblDichVu);
        tblDichVu.setRowHeight(30);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tblDichVu.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblDichVu.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        JScrollPane scrTable = new JScrollPane(tblDichVu);
        scrTable.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrTable.getViewport().setBackground(Color.WHITE);
        pnl.add(scrTable, BorderLayout.CENTER);

        // Đáy panel: Hiển thị tổng tiền
        lblTongTien = new JLabel("Tổng chi phí dự kiến: 0 VNĐ  ", SwingConstants.RIGHT);
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTongTien.setForeground(mauChuDao);
        lblTongTien.setBorder(new EmptyBorder(8, 5, 8, 10));

        pnl.add(lblTongTien, BorderLayout.SOUTH);

        return pnl;
    }

    // Panel 3: Nút Lưu / Hủy ở góc dưới
    private JPanel taoPanelNutThaoTac() {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        pnl.setBackground(mauNen);

        btnLuu = GiaoDienStyle.taoNut("Lưu kế hoạch", GiaoDienStyle.MAU_TEAL, 130);
        btnHuy = GiaoDienStyle.taoNut("Hủy bỏ", GiaoDienStyle.MAU_DO, 100);

        pnl.add(btnLuu);
        pnl.add(btnHuy);

        return pnl;
    }

    private void dangKySuKien() {
        btnThemDV.addActionListener(this);
        btnXoaDV.addActionListener(this);
        btnLuu.addActionListener(this);
        btnHuy.addActionListener(this);
    }

    private void loadDataCombobox() {
        // TODO: Kết nối DAO lấy danh sách Bệnh nhân, Bác sĩ, Dịch vụ từ SQL Server
        cboBenhNhan.addItem("Chọn bệnh nhân");
        cboBenhNhan.addItem("BN0001 - Nguyễn Văn An");
        cboBenhNhan.addItem("BN0002 - Trần Thị Bích");
        cboBenhNhan.addItem("BN0003 - Lê Minh Hoàng");

        cboBacSi.addItem("Chọn bác sĩ");
        cboBacSi.addItem("BS001 - BS. Nguyễn Văn Tuấn");
        cboBacSi.addItem("BS002 - BS. Trần Thị Hương");

        cboDichVu.addItem("Chọn dịch vụ");
        cboDichVu.addItem("DV001 - Khám và tư vấn (500,000)");
        cboDichVu.addItem("DV002 - Trám răng composte (400,000)");
        cboDichVu.addItem("DV003 - Cạo vôi răng (300,000)");
        cboDichVu.addItem("DV004 - Niềng răng mắc cài (20,000,000)");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src.equals(btnThemDV)) {
            xuLyThemDichVuVaoBang();
        } else if (src.equals(btnXoaDV)) {
            xuLyXoaDichVuKhoiBang();
        } else if (src.equals(btnLuu)) {
            xuLyLuuKeHoach();
        } else if (src.equals(btnHuy)) {
            dispose(); // Đóng form
        }
    }

    private void xuLyThemDichVuVaoBang() {
        if (cboDichVu.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedDV = (String) cboDichVu.getSelectedItem();
        int soLuong = (int) spnSoLuong.getValue();

        // Tách chuỗi mẫu: "DV001 - Khám và tư vấn (500,000)"
        String maDV = selectedDV.substring(0, 5);
        String tenDV = selectedDV.substring(8, selectedDV.indexOf(" ("));
        double donGia = 0;

        if (maDV.equals("DV001")) donGia = 500000;
        else if (maDV.equals("DV002")) donGia = 400000;
        else if (maDV.equals("DV003")) donGia = 300000;
        else if (maDV.equals("DV004")) donGia = 20000000;

        double thanhTien = donGia * soLuong;

        // Kiểm tra xem dịch vụ đã có trong bảng chưa
        for (int i = 0; i < modelDichVu.getRowCount(); i++) {
            if (modelDichVu.getValueAt(i, 0).equals(maDV)) {
                int slCu = Integer.parseInt(modelDichVu.getValueAt(i, 2).toString());
                int slMoi = slCu + soLuong;
                double thanhTienMoi = donGia * slMoi;

                modelDichVu.setValueAt(slMoi, i, 2);
                modelDichVu.setValueAt(df.format(thanhTienMoi), i, 4);

                tinhLaiTongChiPhi();
                return;
            }
        }

        // Thêm dòng mới nếu chưa có
        modelDichVu.addRow(new Object[]{
                maDV, tenDV, soLuong, df.format(donGia), df.format(thanhTien)
        });

        tinhLaiTongChiPhi();
    }

    private void xuLyXoaDichVuKhoiBang() {
        int row = tblDichVu.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa khỏi bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modelDichVu.removeRow(row);
        tinhLaiTongChiPhi();
    }

    private void tinhLaiTongChiPhi() {
        tongChiPhi = 0;
        for (int i = 0; i < modelDichVu.getRowCount(); i++) {
            try {
                String thanhTienStr = modelDichVu.getValueAt(i, 4).toString().replace(",", "").replace(".", "");
                tongChiPhi += Double.parseDouble(thanhTienStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lblTongTien.setText("Tổng chi phí dự kiến: " + df.format(tongChiPhi) + " VNĐ  ");
    }

    private void xuLyLuuKeHoach() {
        if (cboBenhNhan.getSelectedIndex() == 0 || cboBacSi.getSelectedIndex() == 0 || txtChanDoan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc (*)", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modelDichVu.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Kế hoạch điều trị phải có ít nhất 1 dịch vụ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // TODO: Viết code gọi DAO thực hiện INSERT KeHoachDieuTri & ChiTietKeHoach vào SQL Server
        // Example:
        // KeHoachDieuTri kh = new KeHoachDieuTri(...);
        // if(keHoachDAO.them(kh)) { ... }

        JOptionPane.showMessageDialog(this, "Đã thêm mới kế hoạch điều trị thành công!");
        dispose(); // Đóng form dialog
    }
}