package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VatTuPanel extends JPanel implements ActionListener {

    private final Color MAU_NAVY = GiaoDienStyle.MAU_NAVY;
    private final Color MAU_NEN = GiaoDienStyle.MAU_NEN;
    private final Color MAU_TRANG = GiaoDienStyle.MAU_TRANG;
    private final Color MAU_TEAL = GiaoDienStyle.MAU_TEAL;
    private final Color MAU_DO = GiaoDienStyle.MAU_DO;
    private final Color TEXT = new Color(45, 55, 68);

    private JTextField txtMaVT;
    private JTextField txtTenVT;
    private JComboBox<String> cboDonViTinh;
    private JTextField txtSoLuongTon;
    private JTextField txtTonToiThieu;
    private JTextField txtGiaBan;
    private JTextField txtHanSuDung;
    private JTextField txtNhaCungCap;

    private JButton btnThem;   
    private JButton btnLuu;     
    private JButton btnXoa;     
    private JButton btnHuyBo;   

    private JPanel pnlLuoianhVatTu;
    private boolean dangThem = false;

    public VatTuPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachVatTuTuCSDL();
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

        JPanel pnlCard = new JPanel(new BorderLayout(0, 10));
        pnlCard.setBackground(MAU_TRANG);
        pnlCard.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 225, 232)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setOpaque(false);

        JLabel lblTieuDe = taoTieuDe("Danh mục vật tư y tế");
        btnThem = taoNut("Thêm vật tư", MAU_NAVY, 120);

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlTieuDe.add(btnThem, BorderLayout.EAST);
        pnlCard.add(pnlTieuDe, BorderLayout.NORTH);

        pnlLuoianhVatTu = new JPanel(new GridLayout(0, 2, 12, 12));
        pnlLuoianhVatTu.setOpaque(false);

        JScrollPane scroll = new JScrollPane(pnlLuoianhVatTu);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(MAU_TRANG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        pnlCard.add(scroll, BorderLayout.CENTER);
        pnlBenTrai.add(pnlCard, BorderLayout.CENTER);

        return pnlBenTrai;
    }
    private void taiDanhSachVatTuTuCSDL() {
        pnlLuoianhVatTu.removeAll();
        
        try {
            // TODO: Gọi DAO để lấy danh sách từ cơ sở dữ liệu
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách vật tư từ cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        pnlLuoianhVatTu.revalidate();
        pnlLuoianhVatTu.repaint();
    }

    private JPanel productCard(String code, String name, String unit, String stock, String price,
            String status, Color imageBg, String illustrationText) {
        JPanel product = new JPanel(new BorderLayout(0, 6));
        product.setBackground(MAU_TRANG);
        product.setBorder(new CompoundBorder(new LineBorder(new Color(226, 231, 238)),
                new EmptyBorder(8, 8, 8, 8)));

        ProductIllustration image = new ProductIllustration(imageBg, illustrationText);
        image.setPreferredSize(new Dimension(185, 95));
        product.add(image, BorderLayout.NORTH);

        JLabel productName = new JLabel("<html><div style='width:170px'><b>" + name + "</b></div></html>");
        productName.setFont(new Font("Arial", Font.PLAIN, 12));
        productName.setForeground(TEXT);

        JLabel productCode = new JLabel("Mã: " + code + " • ĐVT: " + unit);
        productCode.setFont(new Font("Arial", Font.PLAIN, 11));
        productCode.setForeground(new Color(125, 135, 148));

        JLabel productPrice = new JLabel(price);
        productPrice.setFont(new Font("Arial", Font.BOLD, 13));
        productPrice.setForeground(MAU_NAVY);

        JLabel stockStatus = new JLabel("● " + status);
        stockStatus.setFont(new Font("Arial", Font.BOLD, 11));
        if (status.equals("Còn hàng")) stockStatus.setForeground(new Color(18, 135, 94));
        else if (status.equals("Tồn thấp")) stockStatus.setForeground(new Color(173, 123, 25));
        else stockStatus.setForeground(MAU_DO);

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        productName.setAlignmentX(Component.LEFT_ALIGNMENT);
        productCode.setAlignmentX(Component.LEFT_ALIGNMENT);

        details.add(productName);
        details.add(Box.createVerticalStrut(3));
        details.add(productCode);
        details.add(Box.createVerticalStrut(5));

        JPanel priceRow = new JPanel(new BorderLayout());
        priceRow.setOpaque(false);
        priceRow.add(productPrice, BorderLayout.WEST);
        priceRow.add(stockStatus, BorderLayout.EAST);
        details.add(priceRow);

        product.add(details, BorderLayout.CENTER);
        product.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return product;
    }

    private static class ProductIllustration extends JPanel {
        private final String text;

        public ProductIllustration(Color bg, String text) {
            setBackground(bg);
            setPreferredSize(new Dimension(185, 95));
            this.text = text;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int h = getHeight();

            g2.setColor(new Color(100, 115, 130));
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString(text, 15, h / 2 + 5);
            g2.dispose();
        }
    }

    private JPanel taoBenPhai() {
        JPanel pnlBenPhai = new JPanel(new BorderLayout(0, 10));
        pnlBenPhai.setBackground(MAU_NEN);

        JPanel pnlThongTin = taoThongTinVatTu();
        pnlBenPhai.add(pnlThongTin, BorderLayout.CENTER);

        return pnlBenPhai;
    }

    private JPanel taoThongTinVatTu() {
        JPanel pnl = taoPanelTrang();
        pnl.setLayout(new BorderLayout(0, 10));
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin chi tiết vật tư");

        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBackground(MAU_TRANG);
        pnlForm.setBorder(new EmptyBorder(5, 5, 5, 5));

        txtMaVT = taoOThongTin("");
        txtTenVT = taoOThongTinNhap("");

        cboDonViTinh = new JComboBox<>(new String[]{"Hộp", "Cái", "Bộ", "Lọ", "Ống", "Thỏi"});
        cboDonViTinh.setFont(new Font("Arial", Font.PLAIN, 13));
        cboDonViTinh.setBackground(MAU_TRANG);

        txtSoLuongTon = taoOThongTinNhap("");
        txtTonToiThieu = taoOThongTinNhap("");
        txtGiaBan = taoOThongTinNhap("");
        txtHanSuDung = taoOThongTinNhap("");
        txtNhaCungCap = taoOThongTinNhap("");

        pnlForm.add(taoDongNhap("Mã vật tư:", txtMaVT));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Tên vật tư:", txtTenVT));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Đơn vị tính:", cboDonViTinh));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Số lượng tồn:", txtSoLuongTon));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Tồn tối thiểu:", txtTonToiThieu));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Giá bán (VNĐ):", txtGiaBan));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Hạn sử dụng:", txtHanSuDung));
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(taoDongNhap("Nhà cung cấp:", txtNhaCungCap));
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
            // TODO: Gọi hàm sinh mã tự động từ CSDL (Ví dụ: vatTuDAO.phatSinhMaMoi())
            txtMaVT.setText("VT000X"); 
            txtTenVT.requestFocus();
        } else if (source.equals(btnLuu)) {
            xuLyLuuVatTu();
        } else if (source.equals(btnXoa)) {
            xuLyXoaVatTu();
        } else if (source.equals(btnHuyBo)) {
            clearForm();
            setEditableForm(false);
        }
    }

    private void xuLyLuuVatTu() {
        if (txtTenVT.getText().trim().isEmpty() || txtGiaBan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên vật tư và giá bán!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (dangThem) {
                // TODO: Gọi DAO thêm mới vào CSDL
                JOptionPane.showMessageDialog(this, "Thêm vật tư mới thành công!");
            } else {
                // TODO: Gọi DAO cập nhật thông tin trong CSDL
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin vật tư thành công!");
            }
            
            taiDanhSachVatTuTuCSDL();
            setEditableForm(false);
            clearForm();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu vào cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyXoaVatTu() {
        String maVT = txtMaVT.getText().trim();
        if (maVT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vật tư cần xóa từ danh mục!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa vật tư này khỏi cơ sở dữ liệu?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // TODO: Gọi DAO xóa khỏi CSDL
                // vatTuDAO.xoaVatTu(maVT);
                
                JOptionPane.showMessageDialog(this, "Đã xóa vật tư thành công!");
                clearForm();
                setEditableForm(false);
                taiDanhSachVatTuTuCSDL();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu từ cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setEditableForm(boolean editable) {
        txtTenVT.setEditable(editable);
        cboDonViTinh.setEnabled(editable);
        txtSoLuongTon.setEditable(editable);
        txtTonToiThieu.setEditable(editable);
        txtGiaBan.setEditable(editable);
        txtHanSuDung.setEditable(editable);
        txtNhaCungCap.setEditable(editable);
        btnLuu.setEnabled(editable);
        btnXoa.setEnabled(!dangThem && editable);
    }

    private void clearForm() {
        txtMaVT.setText("");
        txtTenVT.setText("");
        cboDonViTinh.setSelectedIndex(0);
        txtSoLuongTon.setText("");
        txtTonToiThieu.setText("");
        txtGiaBan.setText("");
        txtHanSuDung.setText("");
        txtNhaCungCap.setText("");
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
        btn.setPreferredSize(new Dimension(chieuRong, 32));
        return btn;
    }
}