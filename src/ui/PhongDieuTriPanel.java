package ui;

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

public class PhongDieuTriPanel extends JPanel implements ActionListener, MouseListener {

    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;
    private final Color MAU_DO = GiaoDienStyle.MAU_DO;

    private JTable tblPhong;
    private DefaultTableModel modelPhong;

    private JTextField txtMaPhong;
    private JTextField txtTenPhong;
    private JComboBox<String> cboLoaiPhong;
    private JTextField txtViTri;
    private JComboBox<String> cboTrangThai;
    private JTextField txtSoGhe;
    private JTextField txtThietBi;

    private JButton btnThem;       
    private JButton btnLuu;     
    private JButton btnXoa;      
    private JButton btnHuyBo;    

    private boolean dangThem = false;

    public PhongDieuTriPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachPhongTuCSDL();
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

        JPanel pnlDanhSach = taoKhuVucDanhSachPhong();
        pnlBenTrai.add(pnlDanhSach, BorderLayout.CENTER);

        return pnlBenTrai;
    }

    private JPanel taoKhuVucDanhSachPhong() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(MAU_TRANG);

        JLabel lblTieuDe = taoTieuDe("Danh sách phòng điều trị");
        btnThem = taoNut("Thêm phòng", MAU_NAVY, 115);

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlTieuDe.add(btnThem, BorderLayout.EAST);

        String[] cot = {
                "Mã phòng",
                "Tên phòng",
                "Loại phòng",
                "Vị trí",
                "Trạng thái"
        };

        modelPhong = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblPhong = new JTable(modelPhong);
        tblPhong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPhong.setRowHeight(32);
        tblPhong.setFont(new Font("Arial", Font.PLAIN, 12));
        tblPhong.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblPhong.getTableHeader().setBackground(MAU_NAVY);
        tblPhong.getTableHeader().setForeground(Color.WHITE);
        tblPhong.setSelectionBackground(new Color(220, 235, 250));
        tblPhong.setSelectionForeground(Color.BLACK);
        GiaoDienStyle.dinhDangBang(tblPhong);

        JScrollPane scrDanhSach = new JScrollPane(tblPhong);
        scrDanhSach.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(pnlTieuDe, BorderLayout.NORTH);
        pnl.add(scrDanhSach, BorderLayout.CENTER);

        return pnl;
    }


    private JPanel taoBenPhai() {
        JPanel pnlBenPhai = new JPanel(new BorderLayout(0, 10));
        pnlBenPhai.setBackground(MAU_NEN);

        JPanel pnlThongTin = taoThongTinPhong();
        pnlBenPhai.add(pnlThongTin, BorderLayout.CENTER);

        return pnlBenPhai;
    }

    private JPanel taoThongTinPhong() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin chi tiết phòng");

        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBackground(MAU_TRANG);
        pnlForm.setBorder(new EmptyBorder(5, 5, 5, 5));

        txtMaPhong = taoOThongTin("");
        txtTenPhong = taoOThongTinNhap("");

        cboLoaiPhong = new JComboBox<>(new String[]{"Phòng khám tổng quát", "Phòng VIP", "Tiểu phẫu", "X-Quang", "Chỉnh nha"});
        cboLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 13));
        cboLoaiPhong.setBackground(MAU_TRANG);

        txtViTri = taoOThongTinNhap("");

        cboTrangThai = new JComboBox<>(new String[]{"Đang hoạt động", "Bảo trì", "Tạm ngưng"});
        cboTrangThai.setFont(new Font("Arial", Font.PLAIN, 13));
        cboTrangThai.setBackground(MAU_TRANG);

        txtSoGhe = taoOThongTinNhap("");
        txtThietBi = taoOThongTinNhap("");

        pnlForm.add(taoDongNhap("Mã phòng:", txtMaPhong));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Tên phòng:", txtTenPhong));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Loại phòng:", cboLoaiPhong));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Vị trí:", txtViTri));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Trạng thái:", cboTrangThai));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Số ghế nha:", txtSoGhe));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Mô tả thiết bị:", txtThietBi));
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
        tblPhong.addMouseListener(this);
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
            txtMaPhong.setText("P004");
            txtTenPhong.requestFocus();
        } else if (source.equals(btnLuu)) {
            xuLyLuuPhong();
        } else if (source.equals(btnXoa)) {
            xuLyXoaPhong();
        } else if (source.equals(btnHuyBo)) {
            clearForm();
            setEditableForm(false);
            tblPhong.clearSelection();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblPhong.getSelectedRow();
        if (row >= 0) {
            dangThem = false;
            setEditableForm(true);
            txtMaPhong.setText(String.valueOf(modelPhong.getValueAt(row, 0)));
            txtTenPhong.setText(String.valueOf(modelPhong.getValueAt(row, 1)));
            cboLoaiPhong.setSelectedItem(String.valueOf(modelPhong.getValueAt(row, 2)));
            txtViTri.setText(String.valueOf(modelPhong.getValueAt(row, 3)));
            cboTrangThai.setSelectedItem(String.valueOf(modelPhong.getValueAt(row, 4)));
            txtSoGhe.setText("2");
            txtThietBi.setText("Ghế nha khoa cao cấp, Đèn điều trị");
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private void taiDanhSachPhongTuCSDL() {
        modelPhong.setRowCount(0);
        try {
            Object[][] duLieuMau = {
                    {"P001", "Phòng Khám 01", "Phòng khám tổng quát", "Tầng 1", "Đang hoạt động"},
                    {"P002", "Phòng Tiểu Phẫu", "Tiểu phẫu", "Tầng 2", "Đang hoạt động"},
                    {"P003", "Phòng Chụp X-Quang", "X-Quang", "Tầng 1", "Đang hoạt động"}
            };
            for (Object[] row : duLieuMau) {
                modelPhong.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void xuLyLuuPhong() {
        if (txtTenPhong.getText().trim().isEmpty() || txtViTri.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên phòng và vị trí!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, dangThem ? "Thêm phòng điều trị mới thành công!" : "Cập nhật thông tin phòng thành công!");
        taiDanhSachPhongTuCSDL();
        setEditableForm(false);
        clearForm();
    }

    private void xuLyXoaPhong() {
        int row = tblPhong.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần xóa từ bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa phòng điều trị này khỏi CSDL?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            modelPhong.removeRow(row);
            JOptionPane.showMessageDialog(this, "Đã xóa phòng điều trị thành công!");
            clearForm();
            setEditableForm(false);
        }
    }

    private void setEditableForm(boolean editable) {
        txtTenPhong.setEditable(editable);
        cboLoaiPhong.setEnabled(editable);
        txtViTri.setEditable(editable);
        cboTrangThai.setEnabled(editable);
        txtSoGhe.setEditable(editable);
        txtThietBi.setEditable(editable);
        btnLuu.setEnabled(editable);
        btnXoa.setEnabled(!dangThem && editable);
    }

    private void clearForm() {
        txtMaPhong.setText("");
        txtTenPhong.setText("");
        cboLoaiPhong.setSelectedIndex(0);
        txtViTri.setText("");
        cboTrangThai.setSelectedIndex(0);
        txtSoGhe.setText("");
        txtThietBi.setText("");
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