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

public class DichVuPanel extends JPanel implements ActionListener, MouseListener {

    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;
    private final Color MAU_DO = GiaoDienStyle.MAU_DO;

    private JTable tblDichVu;
    private DefaultTableModel modelDichVu;

    private JTextField txtMaDichVu;
    private JTextField txtTenDichVu;
    private JTextField txtDonGia;
    private JTextField txtThoiGian;
    private JComboBox<String> cboTrangThai;
    private JTextField txtMoTa;

    private JButton btnThem;      
    private JButton btnLuu;      
    private JButton btnXoa;      
    private JButton btnHuyBo;    

    private boolean dangThem = false;

    public DichVuPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachDichVuTuCSDL();
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

        JPanel pnlDanhSach = taoKhuVucDanhSachDichVu();
        pnlBenTrai.add(pnlDanhSach, BorderLayout.CENTER);

        return pnlBenTrai;
    }

    private JPanel taoKhuVucDanhSachDichVu() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(MAU_TRANG);

        JLabel lblTieuDe = taoTieuDe("Danh sách dịch vụ nha khoa");
        btnThem = taoNut("Thêm dịch vụ", MAU_NAVY, 115);

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlTieuDe.add(btnThem, BorderLayout.EAST);

        String[] cot = {
                "Mã dịch vụ",
                "Tên dịch vụ",
                "Đơn giá (VNĐ)",
                "Thời gian dự kiến",
                "Trạng thái"
        };

        modelDichVu = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(modelDichVu);
        tblDichVu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblDichVu.setRowHeight(32);
        tblDichVu.setFont(new Font("Arial", Font.PLAIN, 12));
        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblDichVu.getTableHeader().setBackground(MAU_NAVY);
        tblDichVu.getTableHeader().setForeground(Color.WHITE);
        tblDichVu.setSelectionBackground(new Color(220, 235, 250));
        tblDichVu.setSelectionForeground(Color.BLACK);
        GiaoDienStyle.dinhDangBang(tblDichVu);

        JScrollPane scrDanhSach = new JScrollPane(tblDichVu);
        scrDanhSach.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(pnlTieuDe, BorderLayout.NORTH);
        pnl.add(scrDanhSach, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoBenPhai() {
        JPanel pnlBenPhai = new JPanel(new BorderLayout(0, 10));
        pnlBenPhai.setBackground(MAU_NEN);

        JPanel pnlThongTin = taoThongTinDichVu();
        pnlBenPhai.add(pnlThongTin, BorderLayout.CENTER);

        return pnlBenPhai;
    }

    private JPanel taoThongTinDichVu() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin chi tiết dịch vụ");

        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBackground(MAU_TRANG);
        pnlForm.setBorder(new EmptyBorder(5, 5, 5, 5));

        txtMaDichVu = taoOThongTin("");
        txtTenDichVu = taoOThongTinNhap("");
        txtDonGia = taoOThongTinNhap("");
        txtThoiGian = taoOThongTinNhap("");

        cboTrangThai = new JComboBox<>(new String[]{"Đang cung cấp", "Ngừng cung cấp"});
        cboTrangThai.setFont(new Font("Arial", Font.PLAIN, 13));
        cboTrangThai.setBackground(MAU_TRANG);

        txtMoTa = taoOThongTinNhap("");

        pnlForm.add(taoDongNhap("Mã dịch vụ:", txtMaDichVu));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Tên dịch vụ:", txtTenDichVu));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Đơn giá (VNĐ):", txtDonGia));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Thời gian dự kiến:", txtThoiGian));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Trạng thái:", cboTrangThai));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Mô tả:", txtMoTa));
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
        tblDichVu.addMouseListener(this);
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
            txtMaDichVu.setText("DV0005");
            txtTenDichVu.requestFocus();
        } else if (source.equals(btnLuu)) {
            xuLyLuuDichVu();
        } else if (source.equals(btnXoa)) {
            xuLyXoaDichVu();
        } else if (source.equals(btnHuyBo)) {
            clearForm();
            setEditableForm(false);
            tblDichVu.clearSelection();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblDichVu.getSelectedRow();
        if (row >= 0) {
            dangThem = false;
            setEditableForm(true); 
            txtMaDichVu.setText(String.valueOf(modelDichVu.getValueAt(row, 0)));
            txtTenDichVu.setText(String.valueOf(modelDichVu.getValueAt(row, 1)));
            txtDonGia.setText(String.valueOf(modelDichVu.getValueAt(row, 2)));
            txtThoiGian.setText(String.valueOf(modelDichVu.getValueAt(row, 3)));
            cboTrangThai.setSelectedItem(String.valueOf(modelDichVu.getValueAt(row, 4)));
            txtMoTa.setText("Mô tả chi tiết dịch vụ nha khoa.");
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private void taiDanhSachDichVuTuCSDL() {
        modelDichVu.setRowCount(0);
        try {
         
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void xuLyLuuDichVu() {
        if (txtTenDichVu.getText().trim().isEmpty() || txtDonGia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên dịch vụ và đơn giá!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, dangThem ? "Thêm dịch vụ mới thành công!" : "Cập nhật thông tin dịch vụ thành công!");
        taiDanhSachDichVuTuCSDL();
        setEditableForm(false);
        clearForm();
    }

    private void xuLyXoaDichVu() {
        int row = tblDichVu.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa từ bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa dịch vụ này khỏi CSDL?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            modelDichVu.removeRow(row);
            JOptionPane.showMessageDialog(this, "Đã xóa dịch vụ thành công!");
            clearForm();
            setEditableForm(false);
        }
    }

    private void setEditableForm(boolean editable) {
        txtTenDichVu.setEditable(editable);
        txtDonGia.setEditable(editable);
        txtThoiGian.setEditable(editable);
        cboTrangThai.setEnabled(editable);
        txtMoTa.setEditable(editable);
        btnLuu.setEnabled(editable);
        btnXoa.setEnabled(!dangThem && editable);
    }

    private void clearForm() {
        txtMaDichVu.setText("");
        txtTenDichVu.setText("");
        txtDonGia.setText("");
        txtThoiGian.setText("");
        cboTrangThai.setSelectedIndex(0);
        txtMoTa.setText("");
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