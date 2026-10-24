package ui;

import dao.VatTuDAO;
import model.VatTu;
import utils.GiaoDienStyle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    // Khởi tạo DAO và các định dạng hiển thị
    private VatTuDAO vtDAO = new VatTuDAO();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DecimalFormat currencyFormatter = new DecimalFormat("#,### VNĐ");

    public VatTuPanel() {
        setLayout(new BorderLayout());
        setBackground(MAU_NEN);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachVatTuTuCSDL();
        setEditableForm(false);
    }

    // ==================== GIAO DIEN CHINH ====================

    private void taoGiaoDien() {
        JPanel pnlBenTrai = taoBenTrai();
        JPanel pnlBenPhai = taoBenPhai();

        JPanel pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBackground(MAU_NEN);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // Khung danh mục vật tư chiếm 2 phần (bên trái)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.66;
        gbc.insets = new Insets(0, 0, 0, 0);
        pnlMain.add(pnlBenTrai, gbc);

        // Khung thông tin chi tiết chiếm 1 phần (bên phải, nhỏ bằng 1/2 khung trái)
        gbc.gridx = 1;
        gbc.weightx = 0.33;
        gbc.insets = new Insets(0, 15, 0, 0);
        pnlMain.add(pnlBenPhai, gbc);

        add(pnlMain, BorderLayout.CENTER);
    }

    // ==================== KHUNG DANH MỤC VẬT TƯ ====================

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
        pnlLuoianhVatTu.setBackground(MAU_TRANG);
        pnlLuoianhVatTu.setBorder(new EmptyBorder(5, 0, 5, 0));

        JScrollPane scroll = new JScrollPane(pnlLuoianhVatTu);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(MAU_TRANG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        pnlCard.add(scroll, BorderLayout.CENTER);
        pnlBenTrai.add(pnlCard, BorderLayout.CENTER);

        return pnlBenTrai;
    }

    // Tải dữ liệu từ cơ sở dữ liệu lên các thẻ card
    private void taiDanhSachVatTuTuCSDL() {
        pnlLuoianhVatTu.removeAll();

        try {
            List<VatTu> list = vtDAO.getAll();
            for (VatTu vt : list) {
                int ton = vt.getSoLuongTon();
                int min = vt.getSoLuongToiThieu();
                String trangThai = "Còn hàng";
                Color mauNenAnh = new Color(230, 245, 235);

                if (ton <= 0) {
                    trangThai = "Hết hàng";
                    mauNenAnh = new Color(253, 235, 235);
                } else if (ton <= min) {
                    trangThai = "Tồn thấp";
                    mauNenAnh = new Color(255, 245, 225);
                }

                // Dùng moTa hoặc tạm thời hiển thị giá (tùy chỉnh nếu có trường giá riêng)
                String giaStr = "ĐVT: " + vt.getDonViTinh();
                JPanel card = productCard(
                        vt.getMaVT(),
                        vt.getTenVatTu(),
                        vt.getDonViTinh(),
                        String.valueOf(ton),
                        giaStr,
                        trangThai,
                        mauNenAnh,
                        vt.getDonViTinh()
                );

                // Sự kiện bấm vào thẻ card để đổ thông tin lên form chi tiết
                card.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        hienThiChiTiet(vt);
                    }
                });

                pnlLuoianhVatTu.add(card);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi tải danh sách vật tư từ cơ sở dữ liệu!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        pnlLuoianhVatTu.revalidate();
        pnlLuoianhVatTu.repaint();
    }

    private void hienThiChiTiet(VatTu vt) {
        txtMaVT.setText(vt.getMaVT());
        txtTenVT.setText(vt.getTenVatTu());
        cboDonViTinh.setSelectedItem(vt.getDonViTinh());
        txtSoLuongTon.setText(String.valueOf(vt.getSoLuongTon()));
        txtTonToiThieu.setText(String.valueOf(vt.getSoLuongToiThieu()));
        txtGiaBan.setText("0"); // Hoặc lấy giá nếu model có trường giá
        txtHanSuDung.setText(vt.getHanSuDung() != null ? vt.getHanSuDung().format(dateFormatter) : "");
        txtNhaCungCap.setText(vt.getMoTa() != null ? vt.getMoTa() : "");

        dangThem = false;
        setEditableForm(true);
    }

    // ==================== THẺ VẬT TƯ ====================

    private JPanel productCard(
            String code,
            String name,
            String unit,
            String stock,
            String price,
            String status,
            Color imageBg,
            String illustrationText) {

        JPanel product = new JPanel(new BorderLayout(0, 6));
        product.setBackground(MAU_TRANG);
        product.setBorder(new CompoundBorder(
                new LineBorder(new Color(226, 231, 238)),
                new EmptyBorder(8, 8, 8, 8)
        ));

        ProductIllustration image =
                new ProductIllustration(imageBg, illustrationText);
        image.setPreferredSize(new Dimension(185, 95));
        product.add(image, BorderLayout.NORTH);

        JLabel productName = new JLabel(
                "<html><div style='width:170px'><b>"
                        + name + "</b></div></html>"
        );
        productName.setFont(new Font("Arial", Font.PLAIN, 12));
        productName.setForeground(TEXT);

        JLabel productCode = new JLabel(
                "Mã: " + code + " • Tồn: " + stock
        );
        productCode.setFont(new Font("Arial", Font.PLAIN, 11));
        productCode.setForeground(new Color(125, 135, 148));

        JLabel productPrice = new JLabel(price);
        productPrice.setFont(new Font("Arial", Font.BOLD, 12));
        productPrice.setForeground(MAU_NAVY);

        JLabel stockStatus = new JLabel("● " + status);
        stockStatus.setFont(new Font("Arial", Font.BOLD, 11));

        if (status.equals("Còn hàng")) {
            stockStatus.setForeground(new Color(18, 135, 94));
        } else if (status.equals("Tồn thấp")) {
            stockStatus.setForeground(new Color(173, 123, 25));
        } else {
            stockStatus.setForeground(MAU_DO);
        }

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

    // ==================== HÌNH MINH HỌA VẬT TƯ ====================

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
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int h = getHeight();

            g2.setColor(new Color(100, 115, 130));
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString(text, 15, h / 2 + 5);

            g2.dispose();
        }
    }

    // ==================== KHUNG BÊN PHẢI (CHI TIẾT) ====================

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
                BorderFactory.createLineBorder(
                        new Color(220, 225, 232)
                ),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTieuDe = taoTieuDe("Thông tin chi tiết vật tư");

        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBackground(MAU_TRANG);
        pnlForm.setBorder(new EmptyBorder(5, 5, 5, 5));

        txtMaVT = taoOThongTin("");
        txtTenVT = taoOThongTinNhap("");

        cboDonViTinh = new JComboBox<>(
                new String[]{"Hộp", "Cái", "Bộ", "Lọ", "Ống", "Thỏi", "Tuýp", "Gói"}
        );
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

        pnlForm.add(taoDongNhap("Giá bán:", txtGiaBan));
        pnlForm.add(Box.createVerticalStrut(8));

        pnlForm.add(taoDongNhap("Hạn sử dụng:", txtHanSuDung));
        pnlForm.add(Box.createVerticalStrut(8));

        pnlForm.add(taoDongNhap("Nhà cung cấp:", txtNhaCungCap));
        pnlForm.add(Box.createVerticalGlue());

        JScrollPane scrForm = new JScrollPane(pnlForm);
        scrForm.setBorder(BorderFactory.createEmptyBorder());
        scrForm.getVerticalScrollBar().setUnitIncrement(16);
        scrForm.getViewport().setBackground(MAU_TRANG);

        JPanel pnlNut = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 5, 5)
        );
        pnlNut.setBackground(MAU_TRANG);

        btnLuu = taoNut("Lưu", MAU_TEAL, 65);
        btnXoa = taoNut("Xóa", MAU_DO, 65);
        btnHuyBo = taoNut(
                "Hủy",
                new Color(110, 120, 135),
                65
        );

        pnlNut.add(btnLuu);
        pnlNut.add(btnXoa);
        pnlNut.add(btnHuyBo);

        pnl.add(lblTieuDe, BorderLayout.NORTH);
        pnl.add(scrForm, BorderLayout.CENTER);
        pnl.add(pnlNut, BorderLayout.SOUTH);

        return pnl;
    }

    // ==================== CÁC DÒNG NHẬP LIỆU ====================

    private JPanel taoDongNhap(String nhan, Component control) {
        JPanel pnl = new JPanel(new BorderLayout(5, 0));
        pnl.setBackground(MAU_TRANG);

        JLabel lbl = new JLabel(nhan);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setPreferredSize(new Dimension(95, 28));

        pnl.add(lbl, BorderLayout.WEST);
        pnl.add(control, BorderLayout.CENTER);
        pnl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        return pnl;
    }

    // ==================== SỰ KIỆN NÚT ====================

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
            clearForm();

            // Sinh ngẫu nhiên 4 chữ số an toàn khớp với CHECK constraint trong SQL (VTXXXX)
            int randomCode = (int) (Math.random() * 9000) + 1000;
            txtMaVT.setText("VT" + randomCode);

            setEditableForm(true);
            txtTenVT.requestFocus();

        } else if (source.equals(btnLuu)) {
            xuLyLuuVatTu();

        } else if (source.equals(btnXoa)) {
            xuLyXoaVatTu();

        } else if (source.equals(btnHuyBo)) {
            clearForm();
            dangThem = false;
            setEditableForm(false);
        }
    }

    // ==================== XỬ LÝ LƯU (INSERT / UPDATE) ====================

    private void xuLyLuuVatTu() {
        String maVT = txtMaVT.getText().trim();
        String tenVT = txtTenVT.getText().trim();
        String dvt = cboDonViTinh.getSelectedItem().toString();

        if (tenVT.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập tên vật tư!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            int soLuongTon = txtSoLuongTon.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtSoLuongTon.getText().trim());
            int tonToiThieu = txtTonToiThieu.getText().trim().isEmpty() ? 5 : Integer.parseInt(txtTonToiThieu.getText().trim());

            // Xử lý ngày tháng an toàn hơn, hỗ trợ cả dd/MM/yyyy và yyyy-MM-dd
            LocalDate hsd = null;
            String dateStr = txtHanSuDung.getText().trim();
            if (!dateStr.isEmpty()) {
                try {
                    hsd = LocalDate.parse(dateStr, dateFormatter);
                } catch (Exception e1) {
                    try {
                        hsd = LocalDate.parse(dateStr); // Thử định dạng chuẩn YYYY-MM-DD nếu người dùng nhập kiểu khác
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(this, "Hạn sử dụng không đúng định dạng dd/MM/yyyy!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            String moTa = txtNhaCungCap.getText().trim();
            String hinhAnh = "images/" + maVT.toLowerCase() + ".png";

            VatTu vt = new VatTu(maVT, tenVT, dvt, soLuongTon, tonToiThieu, hsd, moTa, hinhAnh);

            boolean success;
            if (dangThem) {
                success = vtDAO.insert(vt);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Thêm vật tư mới thành công!");
                }
            } else {
                success = vtDAO.update(vt);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin vật tư thành công!");
                }
            }

            taiDanhSachVatTuTuCSDL();
            clearForm();
            dangThem = false;
            setEditableForm(false);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Số lượng tồn và tồn tối thiểu phải là kiểu số nguyên hợp lệ!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi lưu dữ liệu vào cơ sở dữ liệu: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================== XỬ LÝ XÓA (DELETE) ====================

    private void xuLyXoaVatTu() {
        String maVT = txtMaVT.getText().trim();

        if (maVT.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn vật tư cần xóa từ danh mục!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Xác nhận xóa vật tư mã [" + maVT + "] khỏi cơ sở dữ liệu?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = vtDAO.delete(maVT);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Đã xóa vật tư thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy vật tư để xóa trong CSDL!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }

                clearForm();
                dangThem = false;
                setEditableForm(false);
                taiDanhSachVatTuTuCSDL();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Lỗi khi xóa dữ liệu từ cơ sở dữ liệu (Vật tư có thể đang được sử dụng ở chi tiết phiếu nhập)!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // ==================== TRẠNG THÁI FORM ====================

    private void setEditableForm(boolean editable) {
        txtMaVT.setEditable(false);
        txtTenVT.setEditable(editable);
        cboDonViTinh.setEnabled(editable);
        txtSoLuongTon.setEditable(editable);
        txtTonToiThieu.setEditable(editable);
        txtGiaBan.setEditable(editable);
        txtHanSuDung.setEditable(editable);
        txtNhaCungCap.setEditable(editable);

        btnLuu.setEnabled(editable);
        btnXoa.setEnabled(!dangThem && !txtMaVT.getText().trim().isEmpty());
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

    // ==================== CÁC THÀNH PHẦN DÙNG CHUNG ====================

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
                BorderFactory.createLineBorder(
                        new Color(225, 229, 235)
                ),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        return txt;
    }

    private JTextField taoOThongTinNhap(String noiDung) {
        JTextField txt = new JTextField(noiDung);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setBackground(MAU_TRANG);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 229, 235)
                ),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        return txt;
    }

    private JButton taoNut(
            String noiDung,
            Color mauNen,
            int chieuRong) {

        JButton btn = new JButton(noiDung);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mauNen);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(chieuRong, 30));

        return btn;
    }
}