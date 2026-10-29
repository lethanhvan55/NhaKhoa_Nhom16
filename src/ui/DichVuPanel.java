package ui;

import dao.DichVu_DAO;
import model.DichVu;
import utils.GiaoDienStyle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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

    private JButton btnThem;      
    private JButton btnLuu;      
    private JButton btnXoa;      
    private JButton btnHuyBo;
    private JButton btnQuanLyVatTu; // Nút mới thêm để mở Dialog vật tư

    private boolean dangThem = false;
    private DichVu_DAO dv_dao = new DichVu_DAO();
    private DecimalFormat df = new DecimalFormat("#,###");

    public DichVuPanel() {
        setLayout(new BorderLayout(15, 0));
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
        pnlBenPhai.setPreferredSize(new Dimension(390, 0));

        add(pnlBenTrai, BorderLayout.CENTER);
        add(pnlBenPhai, BorderLayout.EAST);
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
        pnl.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 225, 232)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(MAU_TRANG);

        JLabel lblTieuDe = taoTieuDe("Danh sách dịch vụ nha khoa");
        btnThem = taoNut("+ Thêm dịch vụ", MAU_NAVY, 120);

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlTieuDe.add(btnThem, BorderLayout.EAST);

        String[] cot = {
                "Mã dịch vụ",
                "Tên dịch vụ",
                "Đơn giá (VNĐ)",
                "Thời gian",
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
        tblDichVu.setSelectionBackground(new Color(204, 245, 238));
        tblDichVu.setSelectionForeground(MAU_NAVY);
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
        pnl.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 225, 232)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin chi tiết dịch vụ");

        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBackground(MAU_TRANG);
        pnlForm.setBorder(new EmptyBorder(5, 0, 5, 0));

        txtMaDichVu = taoOThongTin("");
        txtTenDichVu = taoOThongTinNhap("");
        txtDonGia = taoOThongTinNhap("");
        txtThoiGian = taoOThongTinNhap("");

        cboTrangThai = new JComboBox<>(new String[]{"Đang cung cấp", "Ngừng cung cấp"});
        cboTrangThai.setFont(new Font("Arial", Font.PLAIN, 13));
        cboTrangThai.setBackground(MAU_TRANG);

        pnlForm.add(taoDongNhap("Mã dịch vụ:", txtMaDichVu));
        pnlForm.add(Box.createVerticalStrut(10));
        pnlForm.add(taoDongNhap("Tên dịch vụ:", txtTenDichVu));
        pnlForm.add(Box.createVerticalStrut(10));
        pnlForm.add(taoDongNhap("Đơn giá:", txtDonGia));
        pnlForm.add(Box.createVerticalStrut(10));
        pnlForm.add(taoDongNhap("Thời gian:", txtThoiGian));
        pnlForm.add(Box.createVerticalStrut(10));
        pnlForm.add(taoDongNhap("Trạng thái:", cboTrangThai));
        pnlForm.add(Box.createVerticalGlue());

        JScrollPane scrForm = new JScrollPane(pnlForm);
        scrForm.setBorder(BorderFactory.createEmptyBorder());

        // Sắp xếp lại 4 nút thành 2 hàng, 2 cột (GridLayout 2 hàng, 2 cột, khoảng cách 8px)
        JPanel pnlNut = new JPanel(new GridLayout(2, 2, 8, 8));
        pnlNut.setBackground(MAU_TRANG);
        pnlNut.setBorder(new EmptyBorder(10, 0, 0, 0));

        btnLuu = taoNut("Lưu", MAU_TEAL, 0);
        btnQuanLyVatTu = taoNut("Quản lý vật tư", new Color(34, 115, 170), 0); // Màu xanh dương đậm
        btnXoa = taoNut("Xóa", MAU_DO, 0);
        btnHuyBo = taoNut("Hủy bỏ", new Color(110, 120, 135), 0);

        pnlNut.add(btnLuu);
        pnlNut.add(btnQuanLyVatTu);
        pnlNut.add(btnXoa);
        pnlNut.add(btnHuyBo);

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(scrForm, BorderLayout.CENTER);
        pnl.add(pnlNut, BorderLayout.SOUTH);

        return pnl;
    }

    private JPanel taoDongNhap(String nhan, Component control) {
        JPanel pnl = new JPanel(new BorderLayout(6, 0));
        pnl.setBackground(MAU_TRANG);
        
        JLabel lbl = new JLabel(nhan);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setPreferredSize(new Dimension(110, 28));
        
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
        btnQuanLyVatTu.addActionListener(this); // Đăng ký sự kiện nút Quản lý vật tư
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnThem)) {
            taiDanhSachDichVuTuCSDL();
            clearForm();
            dangThem = true;
            setEditableForm(true);

            int max = 0;
            for (int i = 0; i < modelDichVu.getRowCount(); i++) {
                try {
                    String ma = String.valueOf(modelDichVu.getValueAt(i, 0));
                    int so = Integer.parseInt(ma.replaceAll("\\D+", ""));
                    if (so > max) {
                        max = so;
                    }
                } catch (Exception ignored) {}
            }
            txtMaDichVu.setText("DV" + String.format("%04d", max + 1));
            txtTenDichVu.requestFocus();
        } else if (source.equals(btnLuu)) {
            xuLyLuuDichVu();
        } else if (source.equals(btnXoa)) {
            xuLyXoaDichVu();
        } else if (source.equals(btnHuyBo)) {
            clearForm();
            setEditableForm(false);
            tblDichVu.clearSelection();
        } else if (source.equals(btnQuanLyVatTu)) {
            xuLyMoDialogVatTu(); // Mở Dialog quản lý vật tư tiêu hao
        }
    }

    private void xuLyMoDialogVatTu() {
        String maDV = txtMaDichVu.getText().trim();
        String tenDV = txtTenDichVu.getText().trim();

        if (maDV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc nhập dịch vụ trước khi quản lý vật tư!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Nếu đang ở trạng thái thêm mới mà chưa lưu vào DB, nhắc người dùng lưu trước
        if (dangThem) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn cần lưu dịch vụ mới này trước khi cấu hình vật tư. Bạn có muốn lưu ngay bây giờ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                xuLyLuuDichVu();
                // Nếu sau khi lưu mà vẫn đang ở chế độ thêm mới (do lỗi), thì dừng lại
                if (dangThem) return;
            } else {
                return;
            }
        }

        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        ChiTietVatTuDichVuDialog dialog = new ChiTietVatTuDichVuDialog(parentWindow, maDV, tenDV);
        dialog.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblDichVu.getSelectedRow();
        if (row >= 0) {
            dangThem = false;
            setEditableForm(true); 
            txtMaDichVu.setText(String.valueOf(modelDichVu.getValueAt(row, 0)));
            txtTenDichVu.setText(String.valueOf(modelDichVu.getValueAt(row, 1)));
            String donGiaStr = String.valueOf(modelDichVu.getValueAt(row, 2)).replace(",", "").replace(" VNĐ", "").trim();
            txtDonGia.setText(donGiaStr);
            
            txtThoiGian.setText(String.valueOf(modelDichVu.getValueAt(row, 3)));
            cboTrangThai.setSelectedItem(String.valueOf(modelDichVu.getValueAt(row, 4)));
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private void taiDanhSachDichVuTuCSDL() {
        modelDichVu.setRowCount(0);
        try {
            List<DichVu> danhSach = dv_dao.getAllDichVu();
            for (DichVu dv : danhSach) {
                modelDichVu.addRow(new Object[]{
                    dv.getMaDV(),
                    dv.getTenDV(),
                    df.format(dv.getDonGia()),
                    dv.getThoiGianDuKien(),
                    dv.getTrangThai()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void xuLyLuuDichVu() {
        try {
            String maDV = txtMaDichVu.getText().trim();
            String tenDV = txtTenDichVu.getText().trim();
            
            double donGia;
            try {
                String giaStr = txtDonGia.getText().trim().replace(",", "");
                donGia = Double.parseDouble(giaStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtDonGia.requestFocus();
                return;
            }

            String thoiGian = txtThoiGian.getText().trim();
            String trangThai = cboTrangThai.getSelectedItem().toString();
            DichVu dv = new DichVu(maDV, tenDV, donGia, thoiGian, trangThai);

            if (dangThem) {
                boolean kq = dv_dao.create(dv);
                if (kq) {
                    JOptionPane.showMessageDialog(this, "Thêm dịch vụ mới thành công!");
                    dangThem = false; // Đã thêm thành công, chuyển trạng thái về bình thường
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại (Trùng mã dịch vụ hoặc lỗi CSDL)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                boolean kq = dv_dao.update(dv);
                if (kq) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin dịch vụ thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            taiDanhSachDichVuTuCSDL();
            setEditableForm(false);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void xuLyXoaDichVu() {
        int row = tblDichVu.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa khỏi bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dịch vụ này khỏi bảng hiển thị?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            modelDichVu.removeRow(row);
            clearForm();
            setEditableForm(false);
            JOptionPane.showMessageDialog(this, "Đã xóa dịch vụ khỏi bảng thành công!");
        }
    }

    private void setEditableForm(boolean editable) {
        txtTenDichVu.setEditable(editable);
        txtDonGia.setEditable(editable);
        txtThoiGian.setEditable(editable);
        cboTrangThai.setEnabled(editable);
        btnLuu.setEnabled(editable);
        btnQuanLyVatTu.setEnabled(editable); // Kích hoạt nút quản lý vật tư khi form mở chỉnh sửa
        btnXoa.setEnabled(!dangThem && editable);
    }

    private void clearForm() {
        txtMaDichVu.setText("");
        txtTenDichVu.setText("");
        txtDonGia.setText("");
        txtThoiGian.setText("");
        cboTrangThai.setSelectedIndex(0);
        dangThem = false;
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
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (chieuRong > 0) {
            btn.setPreferredSize(new Dimension(chieuRong, 32));
        } else {
            btn.setPreferredSize(new Dimension(0, 34));
        }
        return btn;
    }
}