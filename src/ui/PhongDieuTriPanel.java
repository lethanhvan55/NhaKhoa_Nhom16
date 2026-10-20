package ui;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PhongDieuTriPanel extends JPanel {
    private static final Color NAVY = new Color(21, 48, 78);
    private static final Color NAVY_HOVER = new Color(29, 66, 105);
    private static final Color BLUE = new Color(31, 105, 210);
    private static final Color BG = new Color(245, 249, 253);
    private static final Color BORDER = new Color(218, 228, 238);
    private static final Color TEXT = new Color(35, 55, 78);
    private static final Color MUTED = new Color(105, 123, 142);

    public PhongDieuTriPanel() {
    	setLayout(new BorderLayout());
        setBackground(BG);

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }
    

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setBorder(new MatteBorder(0, 0, 0, 1, new Color(35, 70, 105)));

        JPanel brand = new JPanel();
        brand.setBackground(NAVY);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        brand.setBorder(new EmptyBorder(22, 18, 25, 10));
        JLabel logo = new JLabel("♧ FAMI DENTAL");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("SansSerif", Font.BOLD, 23));
        JLabel slogan = new JLabel("   Nụ cười khỏe - Cuộc sống đẹp");
        slogan.setForeground(new Color(220, 233, 246));
        slogan.setFont(new Font("SansSerif", Font.PLAIN, 11));
        brand.add(logo);
        brand.add(Box.createVerticalStrut(5));
        brand.add(slogan);
        sidebar.add(brand, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setBackground(NAVY);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(0, 7, 0, 7));
        String[] items = {
            "⌂   Trang chủ", "♟   Bệnh nhân", "▦   Lịch hẹn",
            "✦   Dịch vụ", "▣   Hồ sơ bệnh án", "▤   Hóa đơn",
            "◇   Vật tư", "♜   Khuyến mãi", "▣   Phòng điều trị",
            "▦   Lịch làm việc", "⚙   Quản lý"
        };
        for (String item : items) {
            boolean active = item.contains("Phòng điều trị");
            JPanel navItem = new JPanel(new BorderLayout());
            navItem.setBackground(active ? BLUE : NAVY);
            navItem.setBorder(new EmptyBorder(13, 13, 13, 8));
            navItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
            navItem.setPreferredSize(new Dimension(245, 48));
            JLabel label = new JLabel(item);
            label.setFont(new Font("SansSerif", Font.PLAIN, 14));
            label.setForeground(Color.WHITE);
            navItem.add(label, BorderLayout.CENTER);
            menu.add(navItem);
            menu.add(Box.createVerticalStrut(4));
        }
        sidebar.add(menu, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(NAVY);
        bottom.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, new Color(45, 77, 108)),
            new EmptyBorder(10, 18, 10, 10)
        ));
        JLabel logout = new JLabel("⇦   Đăng xuất");
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bottom.add(logout, BorderLayout.CENTER);
        sidebar.add(bottom, BorderLayout.SOUTH);
        return sidebar;
    }
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 76));
        header.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));

        JLabel title = new JLabel("  ☰   ◇  Quản lý phòng điều trị");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(TEXT);
        header.add(title, BorderLayout.WEST);

        JPanel user = new JPanel(new FlowLayout(FlowLayout.RIGHT, 13, 18));
        user.setBackground(Color.WHITE);
        JLabel bell = new JLabel("♧");
        bell.setFont(new Font("SansSerif", Font.BOLD, 22));
        bell.setForeground(TEXT);
        JLabel avatar = new JLabel("◉");
        avatar.setFont(new Font("SansSerif", Font.BOLD, 25));
        avatar.setForeground(new Color(116, 136, 158));
        JLabel userName = new JLabel("<html><b>NV001</b><br><span style='font-size:10px'>Lễ tân</span></html>");
        userName.setForeground(TEXT);
        user.add(bell);
        user.add(avatar);
        user.add(userName);
        user.add(new JLabel("⌄  "));
        header.add(user, BorderLayout.EAST);
        return header;
    }

    private JPanel createContent() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);
        outer.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel left = new JPanel(new BorderLayout(0, 12));
        left.setBackground(BG);
        left.add(createSearchPanel(), BorderLayout.NORTH);
        left.add(createTablePanel(), BorderLayout.CENTER);

        JPanel right = createFormPanel();
        right.setPreferredSize(new Dimension(420, 0));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.69);
        split.setDividerLocation(0.69);
        split.setDividerSize(8);
        split.setBorder(null);
        split.setBackground(BG);
        split.setContinuousLayout(true);
        outer.add(split, BorderLayout.CENTER);
        return outer;
    }
    private JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(12, 12, 12, 12)));
        return p;
    }

    private JPanel createSearchPanel() {
        JPanel p = card();
        p.setLayout(new BorderLayout(0, 10));

        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        searchRow.setOpaque(false);
        JTextField search = new JTextField("Tìm kiếm mã phòng / tên phòng / vị trí...");
        search.setForeground(MUTED);
        styleTextField(search);
        searchRow.add(search, BorderLayout.CENTER);

        JButton add = new JButton("+  Thêm phòng");
        styleButton(add, true);
        add.setPreferredSize(new Dimension(130, 39));
        searchRow.add(add, BorderLayout.EAST);
        p.add(searchRow, BorderLayout.NORTH);

        JPanel filters = new JPanel(new GridLayout(1, 2, 10, 0));
        filters.setOpaque(false);
        filters.add(createFilter("Loại phòng", new String[]{
            "Tất cả", "Phòng khám", "Phòng VIP", "Phòng tiểu phẫu",
            "Phòng X-Quang", "Phòng xét nghiệm", "Phòng tư vấn"
        }));
        filters.add(createFilter("Trạng thái", new String[]{
            "Tất cả", "Đang hoạt động", "Bảo trì", "Ngừng sử dụng"
        }));
        p.add(filters, BorderLayout.CENTER);
        return p;
    }
    private JPanel createFilter(String label, String[] options) {
        JPanel wrap = new JPanel(new BorderLayout(0, 5));
        wrap.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(TEXT);
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(100, 38));
        combo.setBorder(new LineBorder(BORDER));
        wrap.add(l, BorderLayout.NORTH);
        wrap.add(combo, BorderLayout.CENTER);
        return wrap;
    }

    private JPanel createTablePanel() {
        JPanel p = card();
        p.setLayout(new BorderLayout(0, 10));

        JLabel title = new JLabel("Danh sách phòng điều trị");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT);
        p.add(title, BorderLayout.NORTH);

        String[] columns = {"STT", "Mã phòng", "Tên phòng", "Loại phòng", "Vị trí", "Trạng thái"};
        Object[][] data = {
            {"1", "PDT001", "Phòng VIP 01", "Phòng VIP", "Tầng 1", "Đang hoạt động"},
            {"2", "PDT002", "Phòng khám 01", "Phòng khám", "Tầng 1", "Đang hoạt động"},
            {"3", "PDT003", "Phòng tiểu phẫu", "Tiểu phẫu", "Tầng 2", "Đang hoạt động"},
            {"4", "PDT004", "Phòng X-Quang", "X-Quang", "Tầng 2", "Bảo trì"},
            {"5", "PDT005", "Phòng thường", "Phòng khám", "Tầng 1", "Đang hoạt động"},
            {"6", "PDT006", "Phòng xét nghiệm", "Xét nghiệm", "Tầng 2", "Đang hoạt động"},
            {"7", "PDT007", "Phòng VIP 02", "Phòng VIP", "Tầng 3", "Đang hoạt động"},
            {"8", "PDT008", "Phòng vật tư", "Kho / vật tư", "Tầng 1", "Ngừng sử dụng"},
            {"9", "PDT009", "Phòng khám 02", "Phòng khám", "Tầng 1", "Đang hoạt động"},
            {"10", "PDT010", "Phòng tư vấn", "Tư vấn", "Tầng 3", "Đang hoạt động"}
        };
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setRowHeight(43);
        table.setGridColor(new Color(225, 232, 239));
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(222, 239, 255));
        table.setSelectionForeground(TEXT);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setBackground(new Color(239, 246, 252));
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(28);
        table.getColumnModel().getColumn(1).setPreferredWidth(40);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(105);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(BORDER));
        scroll.getViewport().setBackground(Color.WHITE);
        p.add(scroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        JLabel total = new JLabel("Tổng số phòng: 10");
        total.setForeground(MUTED);
        footer.add(total, BorderLayout.WEST);

        JPanel pages = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pages.setOpaque(false);
        for (String s : new String[]{"‹", "1", "2", "›"}) {
            JButton b = new JButton(s);
            stylePageButton(b, s.equals("1"));
            pages.add(b);
        }
        footer.add(pages, BorderLayout.EAST);
        p.add(footer, BorderLayout.SOUTH);
        return p;
    }
    private JPanel createFormPanel() {
        JPanel p = card();
        p.setLayout(new BorderLayout(0, 10));

        JLabel title = new JLabel("◇  Thông tin phòng điều trị");
        title.setFont(new Font("SansSerif", Font.BOLD, 17));
        title.setForeground(TEXT);
        p.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JTextField code = new JTextField("PDT011");
        code.setEditable(false);
        code.setBackground(new Color(242, 247, 252));
        addField(form, "Mã phòng", code);
        addField(form, "Tên phòng *", new JTextField());
        addCombo(form, "Loại phòng *", new String[]{
            "Chọn loại phòng", "Phòng khám", "Phòng VIP", "Phòng tiểu phẫu",
            "Phòng X-Quang", "Phòng xét nghiệm", "Phòng tư vấn"
        });
        addField(form, "Vị trí *", new JTextField());
        addCombo(form, "Trạng thái *", new String[]{
            "Chọn trạng thái", "Đang hoạt động", "Bảo trì", "Ngừng sử dụng"
        });
        addField(form, "Số ghế nha khoa", new JTextField());
        addField(form, "Thiết bị trong phòng", new JTextField());

        JLabel noteLabel = new JLabel("Ghi chú");
        noteLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        noteLabel.setForeground(TEXT);
        noteLabel.setBorder(new EmptyBorder(7, 0, 5, 0));
        noteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(noteLabel);

        JTextArea note = new JTextArea();
        note.setFont(new Font("SansSerif", Font.PLAIN, 12));
        note.setForeground(TEXT);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        note.setBorder(new EmptyBorder(8, 9, 8, 9));
        JScrollPane noteScroll = new JScrollPane(note);
        noteScroll.setBorder(new LineBorder(BORDER));
        noteScroll.setPreferredSize(new Dimension(100, 90));
        noteScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        noteScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(noteScroll);

        JPanel reminder = new JPanel(new BorderLayout(5, 5));
        reminder.setBackground(new Color(232, 243, 255));
        reminder.setBorder(new CompoundBorder(new LineBorder(new Color(205, 225, 245)),
                new EmptyBorder(9, 10, 9, 10)));
        JLabel reminderTitle = new JLabel("ⓘ  Lưu ý");
        reminderTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        reminderTitle.setForeground(TEXT);
        JLabel reminderText = new JLabel("<html>• Nhập đầy đủ thông tin phòng điều trị.<br>"
                + "• Kiểm tra trạng thái trước khi sử dụng.<br>"
                + "• Phòng bảo trì không được bố trí điều trị.</html>");
        reminderText.setFont(new Font("SansSerif", Font.PLAIN, 11));
        reminderText.setForeground(TEXT);
        reminder.add(reminderTitle, BorderLayout.NORTH);
        reminder.add(reminderText, BorderLayout.CENTER);
        reminder.setAlignmentX(Component.LEFT_ALIGNMENT);
        reminder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 88));
        form.add(Box.createVerticalStrut(10));
        form.add(reminder);
        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scroll, BorderLayout.CENTER);

        JPanel actions = new JPanel(new GridLayout(1, 2, 8, 0));
        actions.setOpaque(false);
        JButton save = new JButton("▣  Lưu");
        JButton cancel = new JButton("×  Hủy");
        styleButton(save, true);
        styleButton(cancel, false);
        actions.add(save);
        actions.add(cancel);
        p.add(actions, BorderLayout.SOUTH);
        return p;
    }

    private void addField(JPanel parent, String label, JTextField field) {
        JPanel wrap = new JPanel(new BorderLayout(0, 5));
        wrap.setBackground(Color.WHITE);
        wrap.setBorder(new EmptyBorder(3, 0, 5, 0));
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(TEXT);
        styleTextField(field);
        wrap.add(l, BorderLayout.NORTH);
        wrap.add(field, BorderLayout.CENTER);
        wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 61));
        parent.add(wrap);
    }
    private void addCombo(JPanel parent, String label, String[] options) {
        JPanel wrap = new JPanel(new BorderLayout(0, 5));
        wrap.setBackground(Color.WHITE);
        wrap.setBorder(new EmptyBorder(3, 0, 5, 0));
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(TEXT);
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(100, 37));
        combo.setBorder(new LineBorder(BORDER));
        wrap.add(l, BorderLayout.NORTH);
        wrap.add(combo, BorderLayout.CENTER);
        wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 61));
        parent.add(wrap);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 12));
        if (field.getForeground() == null) field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setBorder(new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(7, 9, 7, 9)));
        field.setPreferredSize(new Dimension(100, 39));
    }

    private void styleButton(JButton button, boolean primary) {
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(primary ? Color.WHITE : new Color(34, 92, 150));
        button.setBackground(primary ? BLUE : Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(new CompoundBorder(
                new LineBorder(primary ? BLUE : new Color(165, 193, 218)),
                new EmptyBorder(8, 8, 8, 8)));
        button.setFocusPainted(false);
    }
    private void stylePageButton(JButton button, boolean selected) {
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(selected ? Color.WHITE : TEXT);
        button.setBackground(selected ? BLUE : Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(new LineBorder(BORDER));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(32, 32));
    }

    
}

