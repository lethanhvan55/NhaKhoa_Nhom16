package ui;

import utils.GiaoDienStyle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class HoSoBenhNhanPanel extends JPanel implements ActionListener, MouseListener {

    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;
    private final Color MAU_DO = GiaoDienStyle.MAU_DO;

    private JTable tblBenhNhan;
    private DefaultTableModel modelBenhNhan;

    private JTextField txtMaBN;
    private JTextField txtHoTen;
    private JTextField txtNgaySinh;
    private JComboBox<String> cboGioiTinh;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private JTextField txtDiaChi;
    private JTextField txtGhiChu;

    private JButton btnThem;   
    private JButton btnLuu;    
    private JButton btnXoa;     
    private JButton btnHuyBo;  

    private boolean dangThem = false;

    public HoSoBenhNhanPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachBenhNhanTuCSDL();
        setEditableForm(false);
    }

    private void taoGiaoDien() {
        JPanel pnlBenTrai = taoBenTrai();
        JPanel pnlBenPhai = taoBenPhai();
       
        pnlBenPhai.setPreferredSize(new Dimension(420, 0));

        JSplitPane splitTong = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                pnlBenTrai,
                pnlBenPhai
        );

        splitTong.setResizeWeight(0.52);
        splitTong.setDividerLocation(500);
        splitTong.setDividerSize(8);
        splitTong.setBorder(null);
        splitTong.setBackground(MAU_NEN);

        add(splitTong, BorderLayout.CENTER);
    }

    private JPanel taoBenTrai() {
        JPanel pnlBenTrai = new JPanel(new BorderLayout(0, 10));
        pnlBenTrai.setBackground(MAU_NEN);

        JPanel pnlDanhSach = taoKhuVucDanhSachBenhNhan();
        pnlBenTrai.add(pnlDanhSach, BorderLayout.CENTER);

        return pnlBenTrai;
    }

    private JPanel taoKhuVucDanhSachBenhNhan() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(MAU_TRANG);

        JLabel lblTieuDe = taoTieuDe("Danh sách hồ sơ bệnh nhân");
        btnThem = taoNut("Thêm bệnh nhân", MAU_NAVY, 130);

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlTieuDe.add(btnThem, BorderLayout.EAST);

        String[] cot = {
                "Mã BN",
                "Họ tên",
                "Ngày sinh",
                "Giới tính",
                "SĐT",
                "Địa chỉ"
        };

        modelBenhNhan = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblBenhNhan = new JTable(modelBenhNhan);
        tblBenhNhan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblBenhNhan.setRowHeight(32);
        tblBenhNhan.setFont(new Font("Arial", Font.PLAIN, 12));
        tblBenhNhan.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblBenhNhan.getTableHeader().setBackground(MAU_NAVY);
        tblBenhNhan.getTableHeader().setForeground(Color.WHITE);
        tblBenhNhan.setSelectionBackground(new Color(220, 235, 250));
        tblBenhNhan.setSelectionForeground(Color.BLACK);
        GiaoDienStyle.dinhDangBang(tblBenhNhan);

        JScrollPane scrDanhSach = new JScrollPane(tblBenhNhan);
        scrDanhSach.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(pnlTieuDe, BorderLayout.NORTH);
        pnl.add(scrDanhSach, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoBenPhai() {
        JPanel pnlBenPhai = new JPanel(new BorderLayout(0, 10));
        pnlBenPhai.setBackground(MAU_NEN);

        JPanel pnlThongTin = taoThongTinBenhNhan();
        pnlBenPhai.add(pnlThongTin, BorderLayout.CENTER);

        return pnlBenPhai;
    }

    private JPanel taoThongTinBenhNhan() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin chi tiết bệnh nhân");

        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBackground(MAU_TRANG);
        pnlForm.setBorder(new EmptyBorder(5, 5, 5, 5));

        txtMaBN = taoOThongTin("");
        txtHoTen = taoOThongTinNhap("");
        txtNgaySinh = taoOThongTinNhap("");

        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboGioiTinh.setFont(new Font("Arial", Font.PLAIN, 13));
        cboGioiTinh.setBackground(MAU_TRANG);

        txtSDT = taoOThongTinNhap("");
        txtEmail = taoOThongTinNhap("");
        txtDiaChi = taoOThongTinNhap("");
        txtGhiChu = taoOThongTinNhap("");

        pnlForm.add(taoDongNhap("Mã bệnh nhân:", txtMaBN));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Họ và tên:", txtHoTen));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Ngày sinh:", txtNgaySinh));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Giới tính:", cboGioiTinh));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Số điện thoại:", txtSDT));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Email:", txtEmail));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Địa chỉ:", txtDiaChi));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Ghi chú:", txtGhiChu));
        pnlForm.add(Box.createVerticalGlue());

        JScrollPane scrForm = new JScrollPane(pnlForm);
        scrForm.setBorder(BorderFactory.createEmptyBorder());

        JPanel pnlNut = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlNut.setBackground(MAU_TRANG);

        btnLuu = taoNut("Lưu", MAU_TEAL, 95);
        btnXoa = taoNut("Xóa", MAU_DO, 95);
        btnHuyBo = taoNut("Hủy bỏ", new Color(110, 120, 135), 95);

        pnlNut.add(btnLuu);
        pnlNut.add(btnXoa);
        pnlNut.add(btnHuyBo);

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(scrForm, BorderLayout.CENTER);
        pnl.add(pnlNut, BorderLayout.SOUTH);

        return pnl;
    }

    private JPanel taoDongNhap(String nhan, Component control) {
        JPanel pnl = new JPanel(new BorderLayout(8, 0));
        pnl.setBackground(MAU_TRANG);
        
        JLabel lbl = new JLabel(nhan);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setPreferredSize(new Dimension(115, 28));
        
        control.setPreferredSize(new Dimension(180, 28));

        pnl.add(lbl, BorderLayout.WEST);
        pnl.add(control, BorderLayout.CENTER);
        pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        
        return pnl;
    }

    private void dangKySuKien() {
        tblBenhNhan.addMouseListener(this);
        btnThem.addActionListener(this);
        btnLuu.addActionListener(this);
        btnXoa.addActionListener(this);
        btnHuyBo.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnThem)) {
            dangThem = true;
            setEditableForm(true);
            clearForm();
            txtMaBN.setText("BN0007");
            txtHoTen.requestFocus();
        } else if (source.equals(btnLuu)) {
            xuLyLuuBenhNhan();
        } else if (source.equals(btnXoa)) {
            xuLyXoaBenhNhan();
        } else if (source.equals(btnHuyBo)) {
            clearForm();
            setEditableForm(false);
            tblBenhNhan.clearSelection();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblBenhNhan.getSelectedRow();
        if (row >= 0) {
            dangThem = false;
            setEditableForm(true);
            txtMaBN.setText(String.valueOf(modelBenhNhan.getValueAt(row, 0)));
            txtHoTen.setText(String.valueOf(modelBenhNhan.getValueAt(row, 1)));
            txtNgaySinh.setText(String.valueOf(modelBenhNhan.getValueAt(row, 2)));
            cboGioiTinh.setSelectedItem(String.valueOf(modelBenhNhan.getValueAt(row, 3)));
            txtSDT.setText(String.valueOf(modelBenhNhan.getValueAt(row, 4)));
            txtDiaChi.setText(String.valueOf(modelBenhNhan.getValueAt(row, 5)));
            txtEmail.setText("benhnhan@gmail.com");
            txtGhiChu.setText("Khám định kỳ tổng quát.");
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private void taiDanhSachBenhNhanTuCSDL() {
        modelBenhNhan.setRowCount(0);
        try {
            // TODO: Kết nối DAO lấy dữ liệu từ SQL Server
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void xuLyLuuBenhNhan() {
        if (txtHoTen.getText().trim().isEmpty() || txtSDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ họ tên và số điện thoại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // TODO: Gọi DAO Insert/Update vào SQL Server
        JOptionPane.showMessageDialog(this, dangThem ? "Thêm mới bệnh nhân thành công!" : "Cập nhật thông tin bệnh nhân thành công!");
        taiDanhSachBenhNhanTuCSDL();
        setEditableForm(false);
        clearForm();
    }

    private void xuLyXoaBenhNhan() {
        int row = tblBenhNhan.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bệnh nhân cần xóa từ bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa hồ sơ bệnh nhân này khỏi CSDL?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Gọi DAO Delete trong SQL Server
            modelBenhNhan.removeRow(row);
            JOptionPane.showMessageDialog(this, "Đã xóa hồ sơ bệnh nhân thành công!");
            clearForm();
            setEditableForm(false);
        }
    }

    private void setEditableForm(boolean editable) {
        txtHoTen.setEditable(editable);
        txtNgaySinh.setEditable(editable);
        cboGioiTinh.setEnabled(editable);
        txtSDT.setEditable(editable);
        txtEmail.setEditable(editable);
        txtDiaChi.setEditable(editable);
        txtGhiChu.setEditable(editable);
        btnLuu.setEnabled(editable);
        btnXoa.setEnabled(!dangThem && editable);
    }

    private void clearForm() {
        txtMaBN.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtSDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtGhiChu.setText("");
    }

    private JPanel taoPanelTrang() {
        JPanel pnl = new JPanel();
        pnl.setBackground(MAU_TRANG);
        return pnl;
    }

    private JLabel taoTieuDe(String noiDung) {
        JLabel lbl = new JLabel(noiDung);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setForeground(MAU_NAVY);
        return lbl;
    }

    private JTextField taoOThongTin(String noiDung) {
        JTextField txt = new JTextField(noiDung);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setEditable(false);
        txt.setBackground(new Color(248, 249, 252));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 229, 235)),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return txt;
    }

    private JTextField taoOThongTinNhap(String noiDung) {
        JTextField txt = new JTextField(noiDung);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setBackground(MAU_TRANG);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 229, 235)),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return txt;
    }

    private JButton taoNut(String noiDung, Color mauNen, int chieuRong) {
        JButton btn = new JButton(noiDung);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mauNen);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(chieuRong, 32));
        return btn;
    }
}