package ui;

import dao.LichHenDAO;
import com.toedter.calendar.JDateChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class CheckInPanel extends JPanel implements ActionListener, ListSelectionListener {

    private JTextField txtMaLichHen;
    private JTextField txtMaBenhNhan;
    private JTextField txtTenBenhNhan;
    private JTextField txtNgayHen;
    private JTextField txtGioHen;
    private JTextField txtBacSi;
    private JTextField txtDichVu;
    private JLabel lblTrangThaiGiaTri;

    private JDateChooser dateChooserTraCuu;
    private JTable tblChoCheckIn;
    private DefaultTableModel model;

    private JButton btnTraCuu;
    private JButton btnLamMoi;
    private JButton btnCheckIn;

    private LichHenDAO lichHenDAO = new LichHenDAO();
    private DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm");

    private Color mauChu = new Color(35, 70, 110);
    private Color mauNen = new Color(245, 247, 250);
    private Color mauTrang = Color.WHITE;
    private Color mauTeal = new Color(0, 150, 136);

    public CheckInPanel() {
        setLayout(new BorderLayout(0, 15));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        //taoTieuDe();
        taoNoiDung();
        loadDuLieuBang(null, null);
    }

    public void refreshData() {
        loadDuLieuBang(null, null);
    }

 /*   private void taoTieuDe() {
        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setOpaque(false);

    //    JLabel lblTieuDe = new JLabel("CHECK IN BỆNH NHÂN");
    //    lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
    //    lblTieuDe.setForeground(mauChu);

        JLabel lblMoTa = new JLabel("Xác nhận bệnh nhân đã đến phòng khám theo lịch hẹn");
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 13));
        lblMoTa.setForeground(Color.GRAY);

        JPanel pnlText = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlText.setOpaque(false);
     //   pnlText.add(lblTieuDe);
        pnlText.add(lblMoTa);

        pnlTieuDe.add(pnlText, BorderLayout.CENTER);
        pnlTieuDe.setPreferredSize(new Dimension(100, 65));

        add(pnlTieuDe, BorderLayout.NORTH);
    }
*/
    private void taoNoiDung() {
        JPanel pnlNoiDung = new JPanel(new BorderLayout(15, 15));
        pnlNoiDung.setOpaque(false);
        
        JPanel pnlTraCuu = taoPanel("TRA CỨU LỊCH HẸN");
        JPanel pnlNhapMa = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlNhapMa.setOpaque(false);

        JLabel lblMaLichHen = new JLabel("Mã LH/BN/Tên:");
        lblMaLichHen.setFont(new Font("Arial", Font.BOLD, 13));

        txtMaLichHen = new JTextField();
        txtMaLichHen.setPreferredSize(new Dimension(140, 32));

        JLabel lblNgayTraCuu = new JLabel("Ngày hẹn:");
        lblNgayTraCuu.setFont(new Font("Arial", Font.BOLD, 13));

        dateChooserTraCuu = new JDateChooser();
        dateChooserTraCuu.setDateFormatString("dd/MM/yyyy");
        dateChooserTraCuu.setPreferredSize(new Dimension(130, 32));
        dateChooserTraCuu.setFont(new Font("Arial", Font.PLAIN, 13));

        btnTraCuu = taoNut("Tra cứu", mauChu);
        
        pnlNhapMa.add(lblMaLichHen);
        pnlNhapMa.add(txtMaLichHen);
        pnlNhapMa.add(lblNgayTraCuu);
        pnlNhapMa.add(dateChooserTraCuu);
        pnlNhapMa.add(btnTraCuu);

        pnlTraCuu.add(pnlNhapMa, BorderLayout.CENTER);

        JPanel pnlThongTin = taoPanel("THÔNG TIN BỆNH NHÂN");
        JPanel pnlForm = new JPanel(new GridLayout(4, 4, 12, 15));
        pnlForm.setOpaque(false);

        txtMaBenhNhan = taoONhap();
        txtTenBenhNhan = taoONhap();
        txtNgayHen = taoONhap();
        txtGioHen = taoONhap();
        txtBacSi = taoONhap();
        txtDichVu = taoONhap();

        themTruong(pnlForm, "Mã bệnh nhân:", txtMaBenhNhan);
        themTruong(pnlForm, "Tên bệnh nhân:", txtTenBenhNhan);
        themTruong(pnlForm, "Ngày hẹn:", txtNgayHen);
        themTruong(pnlForm, "Giờ hẹn:", txtGioHen);
        themTruong(pnlForm, "Bác sĩ phụ trách:", txtBacSi);
        themTruong(pnlForm, "Dịch vụ:", txtDichVu);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 13));

        lblTrangThaiGiaTri = new JLabel("Chưa chọn lịch");
        lblTrangThaiGiaTri.setFont(new Font("Arial", Font.BOLD, 13));
        lblTrangThaiGiaTri.setForeground(new Color(220, 140, 0));

        pnlForm.add(lblTrangThai);
        pnlForm.add(lblTrangThaiGiaTri);

        pnlThongTin.add(pnlForm, BorderLayout.CENTER);

        JPanel pnlNut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlNut.setOpaque(false);

        btnLamMoi = taoNut("Làm mới", new Color(108, 117, 125));
        btnCheckIn = taoNut("XÁC NHẬN CHECK IN", mauTeal);

        btnCheckIn.setPreferredSize(new Dimension(210, 42));
        btnCheckIn.setFont(new Font("Arial", Font.BOLD, 14));

        pnlNut.add(btnLamMoi);
        pnlNut.add(btnCheckIn);

        pnlThongTin.add(pnlNut, BorderLayout.SOUTH);

        JPanel pnlDanhSach = taoPanel("DANH SÁCH LỊCH HẸN");
        String[] tieuDe = {"Mã lịch hẹn", "Mã bệnh nhân", "Tên bệnh nhân", "Ngày hẹn", "Giờ hẹn", "Bác sĩ", "Dịch vụ", "Trạng thái"};

        model = new DefaultTableModel(null, tieuDe) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tblChoCheckIn = new JTable(model);
        tblChoCheckIn.setRowHeight(32);
        tblChoCheckIn.setFont(new Font("Arial", Font.PLAIN, 13));
        tblChoCheckIn.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblChoCheckIn.getTableHeader().setBackground(mauChu);
        tblChoCheckIn.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrBang = new JScrollPane(tblChoCheckIn);
        scrBang.setBorder(BorderFactory.createEmptyBorder());

        pnlDanhSach.add(scrBang, BorderLayout.CENTER);

        pnlNoiDung.add(pnlTraCuu, BorderLayout.NORTH);
        pnlNoiDung.add(pnlThongTin, BorderLayout.SOUTH);
        pnlNoiDung.add(pnlDanhSach, BorderLayout.CENTER);

        pnlTraCuu.setPreferredSize(new Dimension(100, 100));
        pnlThongTin.setPreferredSize(new Dimension(100, 270));
        pnlDanhSach.setPreferredSize(new Dimension(100, 220));

        add(pnlNoiDung, BorderLayout.CENTER);

        // Đăng ký sự kiện
        btnTraCuu.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnCheckIn.addActionListener(this);
        txtMaLichHen.addActionListener(this);

        tblChoCheckIn.getSelectionModel().addListSelectionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnTraCuu || source == txtMaLichHen) {
            xuLyTraCuuLichHen();
        } else if (source == btnLamMoi) {
            xoaForm();
        } else if (source == btnCheckIn) {
            xuLyXacNhanCheckIn();
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int dong = tblChoCheckIn.getSelectedRow();
            if (dong >= 0) {
                txtMaLichHen.setText(tblChoCheckIn.getValueAt(dong, 0).toString());
                txtMaBenhNhan.setText(tblChoCheckIn.getValueAt(dong, 1).toString());
                txtTenBenhNhan.setText(tblChoCheckIn.getValueAt(dong, 2).toString());
                txtNgayHen.setText(tblChoCheckIn.getValueAt(dong, 3).toString());
                txtGioHen.setText(tblChoCheckIn.getValueAt(dong, 4).toString());
                txtBacSi.setText(tblChoCheckIn.getValueAt(dong, 5).toString());
                txtDichVu.setText(tblChoCheckIn.getValueAt(dong, 6).toString());
                
                String trangThai = tblChoCheckIn.getValueAt(dong, 7).toString();
                lblTrangThaiGiaTri.setText(trangThai);
                if (trangThai.equals("Đang chờ khám")) {
                    lblTrangThaiGiaTri.setForeground(new Color(40, 167, 69)); 
                } else {
                    lblTrangThaiGiaTri.setForeground(new Color(220, 140, 0)); 
                }
            }
        }
    }

    private void xuLyTraCuuLichHen() {
        String tuKhoa = txtMaLichHen.getText().trim();
        LocalDate ngay = null;
        if (dateChooserTraCuu.getDate() != null) {
            ngay = dateChooserTraCuu.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        loadDuLieuBang(tuKhoa, ngay);
    }

    private void xuLyXacNhanCheckIn() {
        int dong = tblChoCheckIn.getSelectedRow();

        if (dong < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn lịch hẹn cần check-in từ bảng!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String maLH = tblChoCheckIn.getValueAt(dong, 0).toString();
        String trangThaiHienTai = tblChoCheckIn.getValueAt(dong, 7).toString();

        if (!"Đã đặt".equals(trangThaiHienTai)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Chỉ có thể check-in lịch hẹn có trạng thái 'Đã đặt'!",
                    "Không thể check-in",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            boolean kq = lichHenDAO.capNhatTrangThaiCheckIn(maLH, "Đang chờ khám");

            if (kq) {
                JOptionPane.showMessageDialog(
                        this,
                        "Check-in thành công! Trạng thái đã chuyển thành 'Đang chờ khám'."
                );
                loadDuLieuBang(null, null);
                xoaForm();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Check-in thất bại! Lịch hẹn có thể đã được cập nhật trước đó.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi cơ sở dữ liệu: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadDuLieuBang(String tuKhoa, LocalDate ngay) {
        model.setRowCount(0);
        try {
            List<Map<String, Object>> danhSach = lichHenDAO.getDanhSachChoCheckIn(tuKhoa, ngay);
            for (Map<String, Object> map : danhSach) {
                LocalDate ngayH = (LocalDate) map.get("NgayHen");
                LocalDateTime gioH = (LocalDateTime) map.get("GioHen");

                model.addRow(new Object[]{
                    map.get("MaLichHen"),
                    map.get("MaBenhNhan"),
                    map.get("TenBenhNhan"),
                    ngayH != null ? ngayH.format(dtfDate) : "",
                    gioH != null ? gioH.format(dtfTime) : "",
                    map.get("TenBacSi"),
                    map.get("TenDV"),
                    map.get("TrangThai")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu lịch hẹn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaForm() {
        txtMaLichHen.setText("");
        txtMaBenhNhan.setText("");
        txtTenBenhNhan.setText("");
        txtNgayHen.setText("");
        txtGioHen.setText("");
        txtBacSi.setText("");
        txtDichVu.setText("");
        lblTrangThaiGiaTri.setText("Chưa chọn lịch");
        lblTrangThaiGiaTri.setForeground(new Color(220, 140, 0));
        tblChoCheckIn.clearSelection();
    }

    private JPanel taoPanel(String tieuDe) {
        JPanel pnl = new JPanel(new BorderLayout(0, 12));
        pnl.setBackground(mauTrang);
        pnl.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 230, 235)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 15));
        lblTieuDe.setForeground(mauChu);
        pnl.add(lblTieuDe, BorderLayout.NORTH);
        return pnl;
    }

    private JTextField taoONhap() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setEditable(false);
        return txt;
    }

    private void themTruong(JPanel pnl, String ten, JTextField txt) {
        JLabel lbl = new JLabel(ten);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        pnl.add(lbl);
        pnl.add(txt);
    }

    private JButton taoNut(String ten, Color mau) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mau);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(110, 35));
        return btn;
    }
}