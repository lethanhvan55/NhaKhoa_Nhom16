package ui;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
public class KhuyenMaiPanel extends JPanel {
    private final Color NAVY = new Color(18, 39, 70);
    private final Color BLUE = new Color(27, 119, 235);
    private final Color BG = new Color(243, 246, 250);
    private final Color TEXT = new Color(45, 55, 68);

    public KhuyenMaiPanel() {
       setLayout(new BorderLayout());
       setBackground(BG);
       add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel side = new JPanel(new BorderLayout());
        side.setPreferredSize(new Dimension(245, 0));
        side.setBackground(NAVY);
        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(22, 12, 15, 12));

        JLabel brand = new JLabel("🦷  FAMI DENTAL");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel tagline = new JLabel("    Nụ cười khỏe - Cuộc sống đẹp");
        tagline.setForeground(new Color(180, 195, 215));
        tagline.setFont(new Font("Arial", Font.PLAIN, 11));
        menu.add(brand);
        menu.add(Box.createVerticalStrut(4));
        menu.add(tagline);
        menu.add(Box.createVerticalStrut(35));

        String[] menus = {
            "⌂  Trang chủ", "♟  Bệnh nhân", "▦  Lịch hẹn",
            "▤  Dịch vụ", "▣  Hồ sơ bệnh án", "▤  Hóa đơn",
            "▣  Vật tư", "◇  Khuyến mãi", "⚙  Quản lý"
        };

        for (String text : menus) {
            boolean selected = text.contains("Khuyến mãi");
            JLabel item = new JLabel(text);
            item.setOpaque(true);
            item.setForeground(Color.WHITE);
            item.setFont(new Font("Arial", selected ? Font.BOLD : Font.PLAIN, 15));
            item.setBorder(new EmptyBorder(12, 12, 12, 8));
            item.setBackground(selected ? BLUE : NAVY);
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 47));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            menu.add(item);
            menu.add(Box.createVerticalStrut(5));
        }
        JLabel logout = new JLabel("⇥  Đăng xuất");
        logout.setForeground(new Color(220, 230, 242));
        logout.setFont(new Font("Arial", Font.BOLD, 14));
        logout.setBorder(new CompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(43, 62, 88)),
            new EmptyBorder(18, 24, 18, 10)
        ));
        side.add(menu, BorderLayout.CENTER);
        side.add(logout, BorderLayout.SOUTH);
        return side;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(17, 24, 17, 24));

        JLabel title = new JLabel("📣  Quản lý chương trình khuyến mãi");
        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(new Color(35, 45, 58));

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        userInfo.setOpaque(false);
        JLabel system = new JLabel("●  Hệ thống hoạt động");
        system.setForeground(new Color(75, 110, 105));
        system.setFont(new Font("Arial", Font.PLAIN, 12));
        JLabel user = new JLabel("NV001   Lễ tân / Thu ngân");
        user.setFont(new Font("Arial", Font.BOLD, 12));
        user.setForeground(TEXT);
        userInfo.add(system);
        userInfo.add(new JSeparator(SwingConstants.VERTICAL));
        userInfo.add(user);

        header.add(title, BorderLayout.WEST);
        header.add(userInfo, BorderLayout.EAST);
        main.add(header, BorderLayout.NORTH);
        JPanel content = new JPanel(new BorderLayout(22, 0));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(22, 22, 22, 22));

        JPanel left = new JPanel(new BorderLayout(0, 18));
        left.setBackground(BG);
        left.add(createSearchPanel(), BorderLayout.NORTH);
        left.add(createPromotionTable(), BorderLayout.CENTER);

        JPanel right = createPromotionForm();
        right.setPreferredSize(new Dimension(385, 0));

        content.add(left, BorderLayout.CENTER);
        content.add(right, BorderLayout.EAST);
        main.add(content, BorderLayout.CENTER);
        return main;
    }
    private JPanel createCard() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(
            new LineBorder(new Color(226, 231, 238)),
            new EmptyBorder(16, 16, 16, 16)
        ));
        return p;
    }

    private JPanel createSearchPanel() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(12, 14));

        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);
        JTextField search = new JTextField("⌕  Tìm theo mã, tên KM, dịch vụ...");
        styleField(search);
        top.add(search, BorderLayout.CENTER);

        JPanel filterWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        filterWrap.setOpaque(false);
        JLabel filterLabel = new JLabel("Lọc:");
        filterLabel.setForeground(new Color(100, 110, 122));
        JComboBox<String> filter = new JComboBox<>(new String[]{
            "Tất cả trạng thái", "Đang áp dụng", "Hết hạn"
        });
        styleCombo(filter);
        filter.setPreferredSize(new Dimension(145, 36));
        JButton create = makeButton("+  Tạo mới", BLUE, Color.WHITE);
        create.setPreferredSize(new Dimension(112, 36));
        filterWrap.add(filterLabel);
        filterWrap.add(filter);
        filterWrap.add(create);

        top.add(filterWrap, BorderLayout.EAST);
        card.add(top, BorderLayout.CENTER);
        return card;
    }
    private JPanel createPromotionTable() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 14));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);
        JLabel title = new JLabel("☷  Danh sách khuyến mãi   ③");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(TEXT);
        JLabel hint = new JLabel("ⓘ Nhấp vào dòng để chỉnh sửa");
        hint.setFont(new Font("Arial", Font.ITALIC, 11));
        hint.setForeground(new Color(150, 158, 168));
        titleRow.add(title, BorderLayout.WEST);
        titleRow.add(hint, BorderLayout.EAST);
        card.add(titleRow, BorderLayout.NORTH);

        String[] columns = {
            "STT", "MÃ KM", "TÊN CHƯƠNG TRÌNH", "CHIẾT KHẤU",
            "DỊCH VỤ ÁP DỤNG", "THỜI HẠN", "TRẠNG THÁI"};
        Object[][] data = {
            {"1", "KM0001", "Tri ân khách hàng tháng 10", "10%",
             "Tất cả dịch vụ", "01/10/2026 - 31/10/2026", "Đang áp dụng"},
            {"2", "KM0002", "Giảm giá tẩy trắng răng mùa hè", "15%",
             "Tẩy trắng răng", "01/06/2026 - 30/06/2026", "Hết hạn"},
            {"3", "KM0003", "Ưu đãi niềng răng sinh viên", "20%",
             "Chỉnh nha - Niềng răng", "01/09/2026 - 30/11/2026", "Đang áp dụng"}
        };
        JTable table = new JTable(data, columns) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowHeight(70);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setForeground(TEXT);
        table.setGridColor(new Color(238, 241, 245));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(239, 247, 255));
        table.setSelectionForeground(TEXT);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 54));
        header.setFont(new Font("Arial", Font.BOLD, 10));
        header.setForeground(new Color(105, 115, 126));
        header.setBackground(new Color(248, 249, 251));
        header.setReorderingAllowed(false);

        int[] widths = {35, 70, 155, 65, 115, 125, 100};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
        table.getColumnModel().getColumn(1).setCellRenderer(new CodeRenderer());
        table.getColumnModel().getColumn(6).setCellRenderer(new PromotionStatusRenderer());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        card.add(scroll, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        JLabel showing = new JLabel("Hiển thị dữ liệu thực tế hệ thống FAMI DENTAL");
        showing.setForeground(new Color(130, 140, 150));
        showing.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel pages = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        pages.setOpaque(false);
        JButton prev = new JButton("‹");
        JButton next = new JButton("›");
        stylePageButton(prev, false);
        stylePageButton(next, false);
        JLabel page = new JLabel("Trang 1/1");
        page.setFont(new Font("Arial", Font.PLAIN, 12));
        pages.add(prev);
        pages.add(page);
        pages.add(next);
        bottom.add(showing, BorderLayout.WEST);
        bottom.add(pages, BorderLayout.EAST);
        card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createPromotionForm() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(Color.WHITE);
        outer.setBorder(new LineBorder(new Color(226, 231, 238)));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(NAVY);
        titleBar.setBorder(new EmptyBorder(16, 16, 16, 16));
        JLabel title = new JLabel("▧  Thông tin khuyến mãi");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        JLabel badge = new JLabel("TẠO MỚI");
        badge.setForeground(new Color(180, 210, 245));
        badge.setFont(new Font("Arial", Font.BOLD, 10));
        badge.setBorder(new CompoundBorder(
            new LineBorder(new Color(60, 90, 130)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        titleBar.add(title, BorderLayout.WEST);
        titleBar.add(badge, BorderLayout.EAST);
        outer.add(titleBar, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(20, 18, 18, 18));

        addFormField(form, "Mã khuyến mãi *", "KM0004", true,
            "Mã tự động");
        addFormField(form, "Tên khuyến mãi *", "", false,
            "Ví dụ: Tri ân khách hàng tháng 10");
        addFormField(form, "Tỷ lệ chiết khấu (%) *", "", false,
            "Từ 0 đến 100%");

        addFormField(form, "Dịch vụ áp dụng *", "", false,
            "Tất cả dịch vụ / Niềng răng / Tẩy trắng...");
        JPanel dates = new JPanel(new GridLayout(1, 2, 12, 0));
        dates.setOpaque(false);
        dates.add(dateField("Ngày bắt đầu *", "2026-10-17"));
        dates.add(dateField("Ngày kết thúc *", "2026-11-17"));
        dates.setMaximumSize(new Dimension(Integer.MAX_VALUE, 76));
        dates.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(dates);
        form.add(Box.createVerticalStrut(16));

        JLabel statusLabel = fieldLabel("Trạng thái *");
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(statusLabel);
        form.add(Box.createVerticalStrut(6));
        JComboBox<String> status = new JComboBox<>(new String[]{
            "Đang áp dụng", "Chưa bắt đầu", "Hết hạn"
        });
        styleCombo(status);
        status.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        status.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(status);
        form.add(Box.createVerticalStrut(18));

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(235, 238, 242));
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        form.add(separator);
        form.add(Box.createVerticalStrut(14));
        JPanel buttons = new JPanel(new BorderLayout(10, 0));
        buttons.setOpaque(false);
        JButton save = makeButton("▣  Lưu dữ liệu", BLUE, Color.WHITE);
        JButton reset = makeButton("⟲  Làm mới", new Color(244, 246, 249), TEXT);
        save.setPreferredSize(new Dimension(0, 40));
        reset.setPreferredSize(new Dimension(100, 40));
        buttons.add(save, BorderLayout.CENTER);
        buttons.add(reset, BorderLayout.EAST);
        buttons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(buttons);

        outer.add(form, BorderLayout.CENTER);
        return outer;
    }
    private void addFormField(JPanel parent, String label, String value,
            boolean disabled, String placeholder) {
JLabel l = fieldLabel(label);
l.setAlignmentX(Component.LEFT_ALIGNMENT);
parent.add(l);
parent.add(Box.createVerticalStrut(6));

JTextField field = new JTextField(value);
if (value.isEmpty()) {
field.setToolTipText(placeholder);
}
styleField(field);
if (disabled) {
field.setEditable(false);
field.setBackground(new Color(245, 246, 248));
field.setFont(new Font("Arial", Font.BOLD, 12));
}
field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
field.setAlignmentX(Component.LEFT_ALIGNMENT);
parent.add(field);
parent.add(Box.createVerticalStrut(16));
}

private JPanel dateField(String label, String value) {
JPanel p = new JPanel(new BorderLayout(0, 6));
p.setOpaque(false);
p.add(fieldLabel(label), BorderLayout.NORTH);
JTextField field = new JTextField(value);
styleField(field);
p.add(field, BorderLayout.CENTER);
return p;
}
private JLabel fieldLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Arial", Font.BOLD, 12));
    label.setForeground(new Color(75, 84, 95));
    return label;
}

private void styleField(JTextField field) {
    field.setFont(new Font("Arial", Font.PLAIN, 13));
    field.setForeground(TEXT);
    field.setBackground(Color.WHITE);
    field.setBorder(new CompoundBorder(
        new LineBorder(new Color(211, 217, 225)),
        new EmptyBorder(7, 10, 7, 10)
    ));
    field.setPreferredSize(new Dimension(120, 36));
}

private void styleCombo(JComboBox<String> combo) {
    combo.setFont(new Font("Arial", Font.PLAIN, 12));
    combo.setForeground(TEXT);
    combo.setBackground(Color.WHITE);
    combo.setBorder(new LineBorder(new Color(211, 217, 225)));
    combo.setPreferredSize(new Dimension(120, 36));
}

private JButton makeButton(String text, Color background, Color foreground) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.setForeground(foreground);
    button.setBackground(background);
    button.setFocusPainted(false);
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setBorder(new LineBorder(
        background.equals(Color.WHITE) ? new Color(215, 220, 227) : background
    ));
    return button;
}
private void stylePageButton(JButton button, boolean active) {
    button.setPreferredSize(new Dimension(30, 30));
    button.setFocusPainted(false);
    button.setBackground(active ? BLUE : Color.WHITE);
    button.setForeground(active ? Color.WHITE : TEXT);
}

private static class CodeRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean selected,
            boolean focus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(
            table, value, selected, focus, row, column);
        label.setBorder(new EmptyBorder(0, 5, 0, 3));
        label.setForeground(new Color(42, 113, 190));
        label.setFont(new Font("Arial", Font.BOLD, 11));
        return label;
    }
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

        if (String.valueOf(value).contains("Hết hạn")) {
            label.setForeground(new Color(161, 59, 75));
            label.setBackground(new Color(255, 229, 233));
        } else {
            label.setForeground(new Color(16, 125, 91));
            label.setBackground(new Color(220, 249, 239));
        }
        return label;
    }
}


}
