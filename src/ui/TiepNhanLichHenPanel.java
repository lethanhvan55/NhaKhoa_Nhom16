package ui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TiepNhanLichHenPanel extends JPanel {
    private static final Color NAVY = new Color(21, 48, 78);
    private static final Color NAVY_HOVER = new Color(29, 66, 105);
    private static final Color BLUE = new Color(31, 105, 210);
    private static final Color BG = new Color(245, 249, 253);
    private static final Color BORDER = new Color(218, 228, 238);
    private static final Color TEXT = new Color(35, 55, 78);
    private static final Color MUTED = new Color(105, 123, 142);

    private JTextField codeField, patient, phone, date, time, dentist, searchField;
    private JComboBox<String> service, status;
    private JTable table;
    private DefaultTableModel model;
    private JLabel total;
    private boolean adding = true;

    public TiepNhanLichHenPanel() {
        setLayout(new BorderLayout());
        setBackground(BG);

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 76));
        header.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));

        JLabel title = new JLabel("  ☰  Quản lý lịch hẹn");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(TEXT);
        header.add(title, BorderLayout.WEST);

        JPanel user = new JPanel(new FlowLayout(FlowLayout.RIGHT, 13, 18));
        user.setBackground(Color.WHITE);
     /*   JLabel bell = new JLabel("♧");
        bell.setFont(new Font("SansSerif", Font.BOLD, 22));
        bell.setForeground(TEXT); */
       // JLabel avatar = new JLabel("◉");
      //  avatar.setFont(new Font("SansSerif", Font.BOLD, 25));
      //  avatar.setForeground(new Color(116, 136, 158));
     /*   JLabel userName = new JLabel("<html><b>NV001</b><br><span style='font-size:10px'>Lễ tân</span></html>");
        userName.setForeground(TEXT);
        user.add(bell);
        user.add(avatar);
        user.add(userName);
        user.add(new JLabel("⌄  "));
        header.add(user, BorderLayout.EAST); */
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
        searchField = new JTextField("Tìm kiếm mã lịch hẹn / tên bệnh nhân / SĐT...");
        searchField.setForeground(MUTED);
        styleTextField(searchField);
        searchRow.add(searchField, BorderLayout.CENTER);

        JButton add = new JButton("+  Thêm lịch hẹn");
        styleButton(add, true);
        add.setPreferredSize(new Dimension(140, 39));
        searchRow.add(add, BorderLayout.EAST);
        p.add(searchRow, BorderLayout.NORTH);

        JPanel filters = new JPanel(new GridLayout(1, 2, 10, 0));
        filters.setOpaque(false);
        filters.add(createFilter("Dịch vụ", new String[]{
            "Tất cả", "Khám tổng quát", "Lấy cao răng", "Trám răng", "Tẩy trắng răng"
        }));
        filters.add(createFilter("Trạng thái", new String[]{
            "Tất cả", "Chờ xác nhận", "Đã xác nhận", "Đã hoàn thành", "Đã hủy"
        }));
        p.add(filters, BorderLayout.CENTER);

        add.addActionListener(e -> clearForm());
        searchField.addActionListener(e -> filter());
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

        JLabel title = new JLabel("Danh sách lịch hẹn");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT);
        p.add(title, BorderLayout.NORTH);

        String[] cols = {"STT", "Mã lịch hẹn", "Tên bệnh nhân", "Số điện thoại", "Ngày hẹn", "Giờ hẹn", "Bác sĩ", "Dịch vụ", "Trạng thái"};
        Object[][] rows = {
            {"1", "LH001", "Nguyễn Minh Anh", "0901234567", "18/10/2026", "08:00", "BS. Tuấn", "Khám tổng quát", "Đã xác nhận"},
            {"2", "LH002", "Trần Gia Hân", "0912345678", "19/10/2026", "09:00", "BS. Hương", "Lấy cao răng", "Chờ xác nhận"},
            {"3", "LH003", "Lê Hoàng Nam", "0923456789", "20/10/2026", "10:30", "BS. Tuấn", "Trám răng", "Đã xác nhận"}
        };
        model = new DefaultTableModel(rows, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
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
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) loadRow();
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(BORDER));
        scroll.getViewport().setBackground(Color.WHITE);
        p.add(scroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        total = new JLabel("Tổng số lịch hẹn: " + model.getRowCount());
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

        JLabel title = new JLabel("◇  Thông tin lịch hẹn");
        title.setFont(new Font("SansSerif", Font.BOLD, 17));
        title.setForeground(TEXT);
        p.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        codeField = new JTextField("LH004");
        codeField.setEditable(false);
        codeField.setBackground(new Color(242, 247, 252));
        addField(form, "Mã lịch hẹn", codeField);
        
        patient = new JTextField();
        addField(form, "Tên bệnh nhân *", patient);
        
        phone = new JTextField();
        addField(form, "Số điện thoại", phone);
        
        date = new JTextField();
        addField(form, "Ngày hẹn * (dd/MM/yyyy)", date);
        
        time = new JTextField();
        addField(form, "Giờ hẹn *", time);
        
        dentist = new JTextField();
        addField(form, "Bác sĩ phụ trách *", dentist);
        
        service = addCombo(form, "Dịch vụ", new String[]{
            "Chọn dịch vụ", "Khám tổng quát", "Lấy cao răng", "Trám răng", "Tẩy trắng răng"
        });
        
        status = addCombo(form, "Trạng thái", new String[]{
            "Chọn trạng thái", "Chờ xác nhận", "Đã xác nhận", "Đã hoàn thành", "Đã hủy"
        });

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

        save.addActionListener(e -> saveRow());
        cancel.addActionListener(e -> clearForm());
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

    private JComboBox<String> addCombo(JPanel parent, String label, String[] options) {
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
        return combo;
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

    private void clearForm() {
        adding = true;
        table.clearSelection();
        codeField.setText(String.format("LH%03d", model.getRowCount() + 1));
        patient.setText("");
        phone.setText("");
        date.setText("");
        time.setText("");
        dentist.setText("");
        service.setSelectedIndex(0);
        status.setSelectedIndex(0);
    }

    private void loadRow() {
        int r = table.convertRowIndexToModel(table.getSelectedRow());
        adding = false;
        codeField.setText(String.valueOf(model.getValueAt(r, 2)));
        patient.setText(String.valueOf(model.getValueAt(r, 3)));
        phone.setText(String.valueOf(model.getValueAt(r, 4)));
        date.setText(String.valueOf(model.getValueAt(r, 5)));
        time.setText(String.valueOf(model.getValueAt(r, 6)));
        dentist.setText(String.valueOf(model.getValueAt(r, 7)));
        service.setSelectedItem(String.valueOf(model.getValueAt(r, 8)));
        status.setSelectedItem(String.valueOf(model.getValueAt(r, 9)));
    }

    private void saveRow() {
        if (patient.getText().trim().isEmpty() || date.getText().trim().isEmpty() || time.getText().trim().isEmpty() || dentist.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object[] row = new Object[]{"□", model.getRowCount() + 1, codeField.getText(), patient.getText().trim(), phone.getText().trim(), date.getText().trim(), time.getText().trim(), dentist.getText().trim(), service.getSelectedItem(), status.getSelectedItem()};
        if (!adding && table.getSelectedRow() >= 0) {
            int r = table.convertRowIndexToModel(table.getSelectedRow());
            for (int c = 2; c < row.length; c++) model.setValueAt(row[c], r, c);
        } else {
            model.addRow(row);
        }
        total.setText("Tổng số lịch hẹn: " + model.getRowCount());
        clearForm();
    }

    private void filter() {
        String q = searchField.getText().trim().toLowerCase();
        if (q.isEmpty() || q.equals("tìm kiếm mã lịch hẹn / tên bệnh nhân / sđt...")) {
            table.clearSelection();
            return;
        }
        for (int r = 0; r < model.getRowCount(); r++) {
            for (int c = 1; c < model.getColumnCount(); c++) {
                if (String.valueOf(model.getValueAt(r, c)).toLowerCase().contains(q)) {
                    table.setRowSelectionInterval(r, r);
                    table.scrollRectToVisible(table.getCellRect(r, 0, true));
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp.");
    }
}