package ui;

import dao.KhuyenMai_DAO;
import model.KhuyenMai;
import utils.GiaoDienStyle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class KhuyenMaiPanel extends JPanel implements ActionListener, MouseListener {

    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;
    private final Color MAU_DO = GiaoDienStyle.MAU_DO;
    private final Color MAU_O_KHOA = new Color(248, 249, 252);

    private JTable tblKhuyenMai;
    private DefaultTableModel modelKhuyenMai;

    private JTextField txtTimKiem;
    private JButton btnTimKiem;

    private JTextField txtMaKM;
    private JTextField txtTenKM;
    private JTextField txtChietKhau;
    private JTextField txtDieuKien; 
    private JDateChooser dcNgayBD;
    private JDateChooser dcNgayKT;
    private JComboBox<String> cboTrangThai;

    private JButton btnThem;
    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnHuyBo;

    private KhuyenMai_DAO km_dao = new KhuyenMai_DAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private boolean dangThem = false;

    public KhuyenMaiPanel() {
        setLayout(new BorderLayout(15, 0));
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachKhuyenMaiTuCSDL();
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
        pnlBenTrai.add(taoKhuVucDanhSachKhuyenMai(), BorderLayout.CENTER);
        return pnlBenTrai;
    }

    private JPanel taoKhuVucDanhSachKhuyenMai() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel pnlTieuDeVaTimKiem = new JPanel(new BorderLayout(0, 10));
        pnlTieuDeVaTimKiem.setBackground(MAU_TRANG);

        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(MAU_TRANG);

        JLabel lblTieuDe = taoTieuDe("Danh sách khuyến mãi");
        btnThem = taoNut("Thêm khuyến mãi", MAU_NAVY, 140);

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlTieuDe.add(btnThem, BorderLayout.EAST);

        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlTimKiem.setBackground(MAU_TRANG);
        pnlTimKiem.setBorder(new EmptyBorder(0, 0, 5, 0));

        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(250, 32));
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(211, 217, 225)),
                BorderFactory.createEmptyBorder(4, 7, 4, 7)
        ));

        btnTimKiem = taoNut("Tìm kiếm", MAU_TEAL, 95);

        pnlTimKiem.add(new JLabel("Tìm kiếm (Mã):"));
        pnlTimKiem.add(txtTimKiem);
        pnlTimKiem.add(btnTimKiem);

        pnlTieuDeVaTimKiem.add(pnlTieuDe, BorderLayout.NORTH);
        pnlTieuDeVaTimKiem.add(pnlTimKiem, BorderLayout.CENTER);

        String[] cot = {
                "Mã KM",
                "Tên chương trình",
                "Chiết khấu",
                "Thời hạn",
                "Điều kiện",
                "Trạng thái"
        };

        modelKhuyenMai = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblKhuyenMai = new JTable(modelKhuyenMai);
        tblKhuyenMai.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblKhuyenMai.setRowHeight(32);
        tblKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 12));
        tblKhuyenMai.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblKhuyenMai.getTableHeader().setBackground(MAU_NAVY);
        tblKhuyenMai.getTableHeader().setForeground(Color.WHITE);
        tblKhuyenMai.setSelectionBackground(new Color(220, 235, 250));
        tblKhuyenMai.setSelectionForeground(Color.BLACK);
        GiaoDienStyle.dinhDangBang(tblKhuyenMai);

        int[] doRong = {60, 180, 70, 160, 100, 100};
        for (int i = 0; i < doRong.length; i++) {
            tblKhuyenMai.getColumnModel().getColumn(i).setPreferredWidth(doRong[i]);
        }

        tblKhuyenMai.getColumnModel().getColumn(5).setCellRenderer(new PromotionStatusRenderer());

        JScrollPane scrDanhSach = new JScrollPane(tblKhuyenMai);
        scrDanhSach.setBorder(BorderFactory.createEmptyBorder());

        pnl.add(pnlTieuDeVaTimKiem, BorderLayout.NORTH);
        pnl.add(scrDanhSach, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoBenPhai() {
        JPanel pnlBenPhai = new JPanel(new BorderLayout(0, 10));
        pnlBenPhai.setBackground(MAU_NEN);
        pnlBenPhai.add(taoThongTinKhuyenMai(), BorderLayout.CENTER);
        return pnlBenPhai;
    }

    private JPanel taoThongTinKhuyenMai() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin chi tiết khuyến mãi");

        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBackground(MAU_TRANG);
        pnlForm.setBorder(new EmptyBorder(5, 5, 5, 5));

        txtMaKM = taoOThongTin("");
        txtTenKM = taoOThongTinNhap("");
        txtChietKhau = taoOThongTinNhap("");
        txtDieuKien = taoOThongTinNhap("");

        dcNgayBD = new JDateChooser();
        dcNgayBD.setDateFormatString("dd/MM/yyyy");
        dcNgayBD.setFont(new Font("Arial", Font.PLAIN, 13));
        dcNgayBD.setBackground(MAU_TRANG);

        dcNgayKT = new JDateChooser();
        dcNgayKT.setDateFormatString("dd/MM/yyyy");
        dcNgayKT.setFont(new Font("Arial", Font.PLAIN, 13));
        dcNgayKT.setBackground(MAU_TRANG);

        cboTrangThai = new JComboBox<>(new String[]{"Đang áp dụng", "Chưa bắt đầu", "Hết hạn"});
        cboTrangThai.setFont(new Font("Arial", Font.PLAIN, 13));
        cboTrangThai.setBackground(MAU_TRANG);

        pnlForm.add(taoDongNhap("Mã khuyến mãi:", txtMaKM));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Tên khuyến mãi:", txtTenKM));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Chiết khấu (%):", txtChietKhau));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Điều kiện:", txtDieuKien));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Ngày bắt đầu:", dcNgayBD));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Ngày kết thúc:", dcNgayKT));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Trạng thái:", cboTrangThai));
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
        txt.setBackground(MAU_O_KHOA);
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
        btn.setPreferredSize(new Dimension(chieuRong, 32));
        return btn;
    }

    private void dangKySuKien() {
        tblKhuyenMai.addMouseListener(this);
        btnThem.addActionListener(this);
        btnLuu.addActionListener(this);
        btnXoa.addActionListener(this);
        btnHuyBo.addActionListener(this);
        btnTimKiem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnThem)) {
            xuLyChuanBiThem();
        } else if (source.equals(btnLuu)) {
            xuLyLuuKhuyenMai();
        } else if (source.equals(btnXoa)) {
            xuLyXoaKhuyenMai();
        } else if (source.equals(btnTimKiem)) {
            xuLyTimKiem();
        } else if (source.equals(btnHuyBo)) {
            clearForm();
            setEditableForm(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblKhuyenMai.getSelectedRow();
        if (row >= 0) {
            dangThem = false;
            setEditableForm(true);
            hienThiThongTinLenForm(row);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private void taiDanhSachKhuyenMaiTuCSDL() {
        modelKhuyenMai.setRowCount(0);
        try {
            List<KhuyenMai> danhSach = km_dao.getAllKhuyenMai();
            for (KhuyenMai km : danhSach) {
                String chietKhauStr = String.valueOf(km.getChietKhau()).replaceAll("\\.0$", "") + "%";
                String thoiHan = sdf.format(km.getNgayBatDau()) + " - " + sdf.format(km.getNgayKetThuc());
                
                modelKhuyenMai.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenKM(),
                    chietKhauStr,
                    thoiHan,
                    km.getDieuKien(),
                    km.getTrangThai()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        try {
            List<KhuyenMai> danhSach = km_dao.timKiemKhuyenMai(tuKhoa);
            modelKhuyenMai.setRowCount(0);
            
            for (KhuyenMai km : danhSach) {
                String chietKhauStr = String.valueOf(km.getChietKhau()).replaceAll("\\.0$", "") + "%";
                String thoiHan = sdf.format(km.getNgayBatDau()) + " - " + sdf.format(km.getNgayKetThuc());
                
                modelKhuyenMai.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenKM(),
                    chietKhauStr,
                    thoiHan,
                    km.getDieuKien(),
                    km.getTrangThai()
                });
            }
            
            if (danhSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã khuyến mãi này không tồn tại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                taiDanhSachKhuyenMaiTuCSDL(); 
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void xuLyChuanBiThem() {
        taiDanhSachKhuyenMaiTuCSDL();
        
        clearForm();
        dangThem = true;
        setEditableForm(true);

        int max = 0;
        for (int i = 0; i < modelKhuyenMai.getRowCount(); i++) {
            try {
                String ma = String.valueOf(modelKhuyenMai.getValueAt(i, 0));
                int so = Integer.parseInt(ma.replaceAll("\\D+", ""));
                if (so > max) {
                    max = so;
                }
            } catch (Exception ignored) {}
        }
        txtMaKM.setText("KM" + String.format("%04d", max + 1));
        dcNgayBD.setDate(new Date());
        txtTenKM.requestFocus();
    }

    private void xuLyLuuKhuyenMai() {
        try {
            String maKM = txtMaKM.getText().trim();
            String tenKM = txtTenKM.getText().trim();
            
            double chietKhau = 0;
            try {
                chietKhau = Double.parseDouble(txtChietKhau.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Chiết khấu phải là dạng số (ví dụ: 10 hoặc 12.5)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                txtChietKhau.requestFocus();
                return;
            }

            Date ngayBD = dcNgayBD.getDate();
            Date ngayKT = dcNgayKT.getDate();
            String dieuKien = txtDieuKien.getText().trim();
            String trangThai = cboTrangThai.getSelectedItem().toString();
            KhuyenMai km = new KhuyenMai(maKM, tenKM, ngayBD, ngayKT, dieuKien, chietKhau, trangThai);

            if (dangThem) {
                boolean kq = km_dao.create(km);
                if (kq) {
                    JOptionPane.showMessageDialog(this, "Thêm khuyến mãi mới thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại (Trùng mã hoặc lỗi CSDL)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                int row = tblKhuyenMai.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi trên bảng trước khi lưu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean kq = km_dao.update(km);
                if (kq) {
                    JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            taiDanhSachKhuyenMaiTuCSDL();
            clearForm();
            setEditableForm(false);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void xuLyXoaKhuyenMai() {
        int row = tblKhuyenMai.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa từ bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khuyến mãi này khỏi bảng hiển thị?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            modelKhuyenMai.removeRow(row);
            clearForm();
            setEditableForm(false);
            JOptionPane.showMessageDialog(this, "Đã xóa khuyến mãi khỏi bảng thành công!");
        }
    }

    private void hienThiThongTinLenForm(int row) {
        txtMaKM.setText(String.valueOf(modelKhuyenMai.getValueAt(row, 0)));
        txtTenKM.setText(String.valueOf(modelKhuyenMai.getValueAt(row, 1)));
        txtChietKhau.setText(String.valueOf(modelKhuyenMai.getValueAt(row, 2)).replace("%", ""));
        
        String maKM = String.valueOf(modelKhuyenMai.getValueAt(row, 0));
        List<KhuyenMai> ds = km_dao.getAllKhuyenMai();
        for (KhuyenMai km : ds) {
            if (km.getMaKM().equals(maKM)) {
                txtDieuKien.setText(km.getDieuKien() != null ? km.getDieuKien() : "");
                break;
            }
        }

        String[] ngay = String.valueOf(modelKhuyenMai.getValueAt(row, 3)).split(" - ");
        try {
            dcNgayBD.setDate(sdf.parse(ngay[0].trim()));
            dcNgayKT.setDate(sdf.parse(ngay[1].trim()));
        } catch (Exception ex) {
            dcNgayBD.setDate(null);
            dcNgayKT.setDate(null);
        }
        cboTrangThai.setSelectedItem(String.valueOf(modelKhuyenMai.getValueAt(row, 5)));
    }

    private void setEditableForm(boolean editable) {
        txtTenKM.setEditable(editable);
        txtChietKhau.setEditable(editable);
        txtDieuKien.setEditable(editable);
        dcNgayBD.setEnabled(editable);
        dcNgayKT.setEnabled(editable);
        cboTrangThai.setEnabled(editable);

        Color nen = editable ? MAU_TRANG : MAU_O_KHOA;
        txtTenKM.setBackground(nen);
        txtChietKhau.setBackground(nen);
        txtDieuKien.setBackground(nen);

        btnLuu.setEnabled(editable);
        btnXoa.setEnabled(!dangThem && editable);
    }

    private void clearForm() {
        tblKhuyenMai.clearSelection();
        txtMaKM.setText("");
        txtTenKM.setText("");
        txtChietKhau.setText("");
        txtDieuKien.setText("");
        dcNgayBD.setDate(null);
        dcNgayKT.setDate(null);
        cboTrangThai.setSelectedIndex(0);
        dangThem = false;
    }

    private static class PromotionStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean selected,
                boolean focus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, selected, focus, row, column);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 10));
            label.setOpaque(true);
            label.setBorder(new EmptyBorder(5, 3, 5, 3));

            String trangThai = String.valueOf(value);
            if (trangThai.contains("Hết hạn")) {
                label.setForeground(new Color(161, 59, 75));
                label.setBackground(new Color(255, 229, 233));
            } else if (trangThai.contains("Chưa bắt đầu")) {
                label.setForeground(new Color(180, 110, 10));
                label.setBackground(new Color(255, 243, 205));
            } else {
                label.setForeground(new Color(16, 125, 91));
                label.setBackground(new Color(220, 249, 239));
            }
            return label;
        }
    }
}