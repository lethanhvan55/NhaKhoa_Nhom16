package ui;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.net.URL;
public class VatTuPanel extends JPanel {
    private final Color NAVY = new Color(35, 68, 105);
    private final Color BLUE = new Color(35, 119, 224);
    private final Color BG = new Color(243, 246, 251);
    private final Color TEXT = new Color(45, 55, 68);

    public VatTuPanel() {
        setLayout(new BorderLayout());
        setBackground(BG);
        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(17, 24, 17, 24));
        JLabel title = new JLabel("   Quản lý vật tư");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(35, 45, 58));
        JLabel user = new JLabel("   NV001   Lễ tân");
        user.setFont(new Font("Arial", Font.BOLD, 13)); user.setForeground(TEXT);
        header.add(title, BorderLayout.WEST); header.add(user, BorderLayout.EAST);
        main.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(18, 0));
        content.setBackground(BG); content.setBorder(new EmptyBorder(22, 22, 22, 22));
        JPanel left = new JPanel(new BorderLayout(0, 18)); left.setBackground(BG);
        left.add(createSearchFilterPanel(), BorderLayout.NORTH);
        left.add(createProductPanel(), BorderLayout.CENTER);
        JPanel right = createInfoPanel(); right.setPreferredSize(new Dimension(370, 0));
        content.add(left, BorderLayout.CENTER); content.add(right, BorderLayout.EAST);
        main.add(content, BorderLayout.CENTER);
        return main;
    }

    private JPanel createCard() {
        JPanel panel = new JPanel(); panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(new LineBorder(new Color(226, 231, 238)),
            new EmptyBorder(16, 16, 16, 16)));
        return panel;
    }
    private JPanel createSearchFilterPanel() {
        JPanel card = createCard(); card.setLayout(new BorderLayout(0, 16));
        JPanel searchRow = new JPanel(new BorderLayout(12, 0)); searchRow.setOpaque(false);
        JTextField search = new JTextField("  Tìm kiếm mã vật tư, tên vật tư...");
        styleField(search); searchRow.add(search, BorderLayout.CENTER);
        JButton addButton = makeButton("  Thêm vật tư", BLUE, Color.WHITE);
        addButton.setPreferredSize(new Dimension(150, 38));
        searchRow.add(addButton, BorderLayout.EAST); card.add(searchRow, BorderLayout.NORTH);
        JPanel filters = new JPanel(new GridLayout(1, 3, 12, 0)); filters.setOpaque(false);
        filters.add(filterCombo("Danh mục vật tư", new String[]{"Tất cả danh mục", "Vật tư tiêu hao", "Thuốc nha khoa", "Dụng cụ"}));
        filters.add(filterCombo("Trạng thái kho", new String[]{"Tất cả", "Còn hàng", "Tồn thấp", "Hết hàng"}));
        filters.add(filterCombo("Sắp xếp theo", new String[]{"Tên vật tư", "Giá tăng dần", "Giá giảm dần"}));
        card.add(filters, BorderLayout.CENTER); return card;
    }

    private JPanel filterCombo(String label, String[] options) {
        JPanel p = new JPanel(new BorderLayout(0, 5)); p.setOpaque(false);
        p.add(fieldLabel(label), BorderLayout.NORTH);
        JComboBox<String> combo = new JComboBox<>(options); styleCombo(combo);
        p.add(combo, BorderLayout.CENTER); return p;
    }

    private JPanel createProductPanel() {
        JPanel card = createCard(); card.setLayout(new BorderLayout(0, 14));
        JPanel titleRow = new JPanel(new BorderLayout()); titleRow.setOpaque(false);
        JLabel title = new JLabel("Vật tư nha khoa");
        title.setFont(new Font("Arial", Font.BOLD, 18)); title.setForeground(TEXT);
        JLabel total = new JLabel("  5 sản phẩm  ");
        total.setFont(new Font("Arial", Font.PLAIN, 12)); total.setForeground(new Color(100, 110, 122));
        total.setOpaque(true); total.setBackground(new Color(244, 246, 249));
        total.setBorder(new EmptyBorder(7, 8, 7, 8));
        titleRow.add(title, BorderLayout.WEST); titleRow.add(total, BorderLayout.EAST);
        card.add(titleRow, BorderLayout.NORTH);

        JPanel products = new JPanel(new GridLayout(0, 2, 12, 12)); products.setOpaque(false);
        products.add(productCard("VT0001", "Găng tay y tế Nitrile", "Hộp 100 chiếc", "125.000 đ",
            "Còn hàng", new Color(222, 239, 255), "GĂNG TAY", "/img/gangtay.jpg"));
        products.add(productCard("VT0002", "Khẩu trang y tế 4 lớp", "Hộp 50 chiếc", "45.000 đ",
            "Tồn thấp", new Color(229, 245, 239), "KHẨU TRANG", "/img/khautrang.jpg"));
        products.add(productCard("VT0003", "Thuốc tê Lidocaine 2%", "Hộp 50 ống", "180.000 đ",
            "Hết hàng", new Color(255, 235, 230), "THUỐC TÊ", "/img/thuocte.jpg"));
        products.add(productCard("VT0004", "Kim tiêm nha khoa 30G", "Hộp 100 chiếc", "95.000 đ",
            "Tồn thấp", new Color(250, 241, 213), "KIM TIÊM", "/img/kimtiem.jpg"));
        products.add(productCard("VT0005", "Composite trám răng A2", "Thỏi 4 g", "220.000 đ",
            "Còn hàng", new Color(239, 232, 255), "COMPOSITE", "/img/composite.jpg"));
        JScrollPane scroll = new JScrollPane(products);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        card.add(scroll, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new BorderLayout()); bottom.setOpaque(false);
        JLabel showing = new JLabel("Hiển thị 5 vật tư");
        showing.setForeground(new Color(130, 140, 150)); showing.setFont(new Font("Arial", Font.PLAIN, 12));
        bottom.add(showing, BorderLayout.WEST);
        JButton more = makeButton("Xem thêm vật tư  ", Color.WHITE, BLUE);
        more.setBorder(new LineBorder(new Color(210, 220, 232)));
        bottom.add(more, BorderLayout.EAST); card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    private JPanel productCard(String code, String name, String unit, String price,
            String status, Color imageBg, String illustrationText, String imagePath) {
        JPanel product = new JPanel(new BorderLayout(0, 8));
        product.setBackground(Color.WHITE);
        product.setBorder(new CompoundBorder(new LineBorder(new Color(226, 231, 238)),
            new EmptyBorder(8, 8, 10, 8)));
        ProductIllustration image = new ProductIllustration(imageBg, illustrationText, imagePath);
        image.setPreferredSize(new Dimension(190, 112));
        product.add(image, BorderLayout.NORTH);

        JLabel productName = new JLabel("<html><div style='width:190px'><b>" + name + "</b></div></html>");
        productName.setFont(new Font("Arial", Font.PLAIN, 13)); productName.setForeground(TEXT);
        JLabel productCode = new JLabel(code + "  •  " + unit);
        productCode.setFont(new Font("Arial", Font.PLAIN, 11)); productCode.setForeground(new Color(125, 135, 148));
        JLabel productPrice = new JLabel(price);
        productPrice.setFont(new Font("Arial", Font.BOLD, 15)); productPrice.setForeground(BLUE);
        JLabel stockStatus = new JLabel("● " + status);
        stockStatus.setFont(new Font("Arial", Font.BOLD, 11));
        if (status.equals("Còn hàng")) stockStatus.setForeground(new Color(18, 135, 94));
        else if (status.equals("Tồn thấp")) stockStatus.setForeground(new Color(173, 123, 25));
        else stockStatus.setForeground(new Color(190, 65, 75));

        JPanel details = new JPanel();
        details.setOpaque(false); details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        productName.setAlignmentX(Component.LEFT_ALIGNMENT); productCode.setAlignmentX(Component.LEFT_ALIGNMENT);
        details.add(productName); details.add(Box.createVerticalStrut(5));
        details.add(productCode); details.add(Box.createVerticalStrut(8));
        JPanel priceRow = new JPanel(new BorderLayout()); priceRow.setOpaque(false);
        priceRow.add(productPrice, BorderLayout.WEST); priceRow.add(stockStatus, BorderLayout.EAST);
        details.add(priceRow); product.add(details, BorderLayout.CENTER);
        return product;
    }
    private static class ProductIllustration extends JPanel {
        private final Color bg;
        private final String text;
        private final Image productImage;

        public ProductIllustration(Color bg, String text, String imagePath) {
            this.bg = bg;
            this.text = text;
            Image loaded = null;
            URL url = imagePath == null ? null : VatTuPanel.class.getResource(imagePath);
            if (url != null) loaded = new ImageIcon(url).getImage();
            else System.err.println("Không tìm thấy ảnh: " + imagePath);
            this.productImage = loaded;
            setBackground(bg);
            setPreferredSize(new Dimension(190, 112));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC
            );

            g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
            );

            int w = getWidth();
            int h = getHeight();

            if (productImage != null) {
                
                g2.drawImage(productImage, 0, 0, w, h, this);
            } else {
                g2.setColor(new Color(100, 100, 100));
                g2.drawString("Chưa có hình ảnh", 10, h / 2);
            }

            g2.dispose();
        }
    }
    private JPanel createInfoPanel() {
        JPanel card = createCard(); card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JPanel titleRow = new JPanel(new BorderLayout()); titleRow.setOpaque(false);
        JLabel title = new JLabel("▌ Thông tin vật tư");
        title.setFont(new Font("Arial", Font.BOLD, 17)); title.setForeground(TEXT);
        JButton edit = new JButton("Chỉnh sửa");
        edit.setFont(new Font("Arial", Font.PLAIN, 12)); edit.setForeground(new Color(60, 120, 175));
        edit.setBackground(new Color(240, 247, 253)); edit.setBorder(new LineBorder(new Color(205, 220, 235)));
        titleRow.add(title, BorderLayout.WEST); titleRow.add(edit, BorderLayout.EAST);
        titleRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34)); titleRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleRow); card.add(Box.createVerticalStrut(18));
        addInfoField(card, "Mã vật tư *", "VT0001", true);
        addInfoField(card, "Tên vật tư *", "Găng tay y tế Nitrile", false);
        addInfoField(card, "Đơn vị tính *", "Hộp", false);
        JPanel quantityRow = new JPanel(new GridLayout(1, 2, 12, 0)); quantityRow.setOpaque(false);
        quantityRow.add(numberField("Số lượng tồn *", "150"));
        quantityRow.add(numberField("Tồn tối thiểu *", "20"));
        quantityRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62)); quantityRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(quantityRow); card.add(Box.createVerticalStrut(15));
        addInfoField(card, "Giá bán *", "125000", false);
        addInfoField(card, "Hạn sử dụng (Có thể để trống)", "2027-12-31", false);
        JLabel descLabel = fieldLabel("Mô tả vật tư"); descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(descLabel); card.add(Box.createVerticalStrut(6));
        JTextArea description = new JTextArea("Găng tay y tế không bột, kích thước M, dùng một lần.");
        description.setFont(new Font("Arial", Font.PLAIN, 13)); description.setLineWrap(true); description.setWrapStyleWord(true);
        description.setBorder(new CompoundBorder(new LineBorder(new Color(211, 217, 225)),
            new EmptyBorder(8, 10, 8, 10)));
        description.setRows(3); description.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        description.setAlignmentX(Component.LEFT_ALIGNMENT); card.add(description); card.add(Box.createVerticalStrut(18));
        JButton update = makeButton("  Cập nhật thông tin", BLUE, Color.WHITE);
        update.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); update.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(update); card.add(Box.createVerticalStrut(10));
        JPanel actions = new JPanel(new GridLayout(1, 2, 8, 0)); actions.setOpaque(false);
        JButton refresh = makeButton("  Làm mới", Color.WHITE, TEXT);
        refresh.setBorder(new LineBorder(new Color(210, 216, 224)));
        JButton delete = makeButton("  Xóa vật tư", Color.WHITE, new Color(190, 65, 65));
        delete.setBorder(new LineBorder(new Color(224, 190, 190)));
        actions.add(refresh); actions.add(delete);
        actions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38)); actions.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(actions); card.add(Box.createVerticalGlue());
        return card;
    }
    private void addInfoField(JPanel parent, String label, String value, boolean disabled) {
        JLabel l = fieldLabel(label); l.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(l); parent.add(Box.createVerticalStrut(6));
        JTextField field = new JTextField(value); styleField(field);
        if (disabled) {
            field.setEditable(false); field.setBackground(new Color(245, 246, 248));
            field.setFont(new Font("Arial", Font.BOLD, 12));
        }
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setAlignmentX(Component.LEFT_ALIGNMENT); parent.add(field); parent.add(Box.createVerticalStrut(15));
    }

    private JPanel numberField(String label, String value) {
        JPanel p = new JPanel(new BorderLayout(0, 5)); p.setOpaque(false);
        p.add(fieldLabel(label), BorderLayout.NORTH);
        JTextField field = new JTextField(value); styleField(field); p.add(field, BorderLayout.CENTER);
        return p;
    }

    private JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12)); label.setForeground(new Color(75, 84, 95));
        return label;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 13)); field.setForeground(TEXT); field.setBackground(Color.WHITE);
        field.setBorder(new CompoundBorder(new LineBorder(new Color(211, 217, 225)),
            new EmptyBorder(7, 10, 7, 10)));
        field.setPreferredSize(new Dimension(120, 36));
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(new Font("Arial", Font.PLAIN, 12)); combo.setForeground(TEXT); combo.setBackground(Color.WHITE);
        combo.setBorder(new LineBorder(new Color(211, 217, 225)));
        combo.setPreferredSize(new Dimension(120, 36));
    }
    private JButton makeButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12)); button.setForeground(foreground);
        button.setBackground(background); button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new LineBorder(background.equals(Color.WHITE) ? new Color(215, 220, 227) : background));
        return button;
    }
}