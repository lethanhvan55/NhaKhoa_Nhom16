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

    private JTable tblKhuyenMai;
    private DefaultTableModel modelKhuyenMai;

    private JTextField txtMaKM;
    private JTextField txtTenKM;
    private JTextField txtChietKhau;
    private JTextField txtDichVu;
    private JTextField txtNgayBD;
    private JTextField txtNgayKT;
    private JComboBox<String> cboTrangThai;

    private JButton btnLuu;
    private JButton btnLamMoi;
    private JButton btnTaoMoi;
    private boolean dangThem = false;

    public KhuyenMaiPanel() {
       setLayout(new BorderLayout());
       setBackground(BG);
       add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel content = new JPanel(new BorderLayout(20, 0));
        content.setBackground(BG);
        JPanel left = new JPanel(new BorderLayout(0, 16));
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
        JTextField search = new JTextField("Tìm theo mã, tên KM, dịch vụ...");
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
        
        btnTaoMoi = makeButton("+  Tạo mới", BLUE, Color.WHITE);
        btnTaoMoi.setPreferredSize(new Dimension(112, 36));
        btnTaoMoi.addActionListener(e -> {
            dangThem = true;
            clearForm();
            txtMaKM.setText("KM0004");
            txtTenKM.requestFocus();
        });

        filterWrap.add(filterLabel);
        filterWrap.add(filter);
        filterWrap.add(btnTaoMoi);

        top.add(filterWrap, BorderLayout.EAST);
        card.add(top, BorderLayout.CENTER);
        return card;
    }

    private JPanel createPromotionTable() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 14));
        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);
        JLabel title = new JLabel("Danh sách khuyến mãi");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(TEXT);
        titleRow.add(title, BorderLayout.WEST);
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
        
        modelKhuyenMai = new DefaultTableModel(data, columns) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblKhuyenMai = new JTable(modelKhuyenMai);
        tblKhuyenMai.setRowHeight(50);
        tblKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 12));
        tblKhuyenMai.setForeground(TEXT);
        tblKhuyenMai.setGridColor(new Color(238, 241, 245));
        tblKhuyenMai.setShowVerticalLines(false);
        tblKhuyenMai.setSelectionBackground(new Color(239, 247, 255));
        tblKhuyenMai.setSelectionForeground(TEXT);
        tblKhuyenMai.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblKhuyenMai.getSelectedRow();
                if (row >= 0) {
                    dangThem = false;
                    txtMaKM.setText(String.valueOf(modelKhuyenMai.getValueAt(row, 1)));
                    txtTenKM.setText(String.valueOf(modelKhuyenMai.getValueAt(row, 2)));
                    txtChietKhau.setText(String.valueOf(modelKhuyenMai.getValueAt(row, 3)).replace("%", ""));
                    txtDichVu.setText(String.valueOf(modelKhuyenMai.getValueAt(row, 4)));
                    
                    String thoiHan = String.valueOf(modelKhuyenMai.getValueAt(row, 5));
                    String[] dates = thoiHan.split(" - ");
                    if(dates.length == 2) {
                        txtNgayBD.setText(dates[0].trim());
                        txtNgayKT.setText(dates[1].trim());
                    }
                    cboTrangThai.setSelectedItem(String.valueOf(modelKhuyenMai.getValueAt(row, 6)));
                }
            }
        });

        JTableHeader header = tblKhuyenMai.getTableHeader();
        header.setPreferredSize(new Dimension(0, 42));
        header.setFont(new Font("Arial", Font.BOLD, 10));
        header.setForeground(new Color(105, 115, 126));
        header.setBackground(new Color(248, 249, 251));
        header.setReorderingAllowed(false);

        int[] widths = {35, 70, 155, 65, 115, 125, 100};
        for (int i = 0; i < widths.length; i++) {
            tblKhuyenMai.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
        tblKhuyenMai.getColumnModel().getColumn(1).setCellRenderer(new CodeRenderer());
        tblKhuyenMai.getColumnModel().getColumn(6).setCellRenderer(new PromotionStatusRenderer());

        JScrollPane scroll = new JScrollPane(tblKhuyenMai);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        card.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
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
        bottom.add(pages, BorderLayout.EAST);
        
        JPanel bottomWrap = new JPanel(new BorderLayout());
        bottomWrap.setOpaque(false);
        bottomWrap.setBorder(new EmptyBorder(12, 0, 0, 0));
        bottomWrap.add(bottom, BorderLayout.CENTER);
        card.add(bottomWrap, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createPromotionForm() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(Color.WHITE);
        outer.setBorder(new LineBorder(new Color(226, 231, 238)));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(NAVY);
        titleBar.setBorder(new EmptyBorder(16, 16, 16, 16));
        JLabel title = new JLabel("Thông tin khuyến mãi");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        JLabel badge = new JLabel("CHI TIẾT");
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

        txtMaKM = new JTextField("KM0001");
        txtMaKM.setEditable(false);
        txtMaKM.setBackground(new Color(245, 246, 248));
        txtMaKM.setFont(new Font("Arial", Font.BOLD, 12));
        
        txtTenKM = new JTextField("Tri ân khách hàng tháng 10");
        txtChietKhau = new JTextField("10");
        txtDichVu = new JTextField("Tất cả dịch vụ");

        addCustomField(form, "Mã khuyến mãi *", txtMaKM);
        addCustomField(form, "Tên khuyến mãi *", txtTenKM);
        addCustomField(form, "Tỷ lệ chiết khấu (%) *", txtChietKhau);
        addCustomField(form, "Dịch vụ áp dụng *", txtDichVu);
        
        JPanel dates = new JPanel(new GridLayout(1, 2, 12, 0));
        dates.setOpaque(false);
        txtNgayBD = new JTextField("01/10/2026");
        txtNgayKT = new JTextField("31/10/2026");
        dates.add(dateField("Ngày bắt đầu *", txtNgayBD));
        dates.add(dateField("Ngày kết thúc *", txtNgayKT));
        dates.setMaximumSize(new Dimension(Integer.MAX_VALUE, 76));
        dates.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(dates);
        form.add(Box.createVerticalStrut(16));

        JLabel statusLabel = fieldLabel("Trạng thái *");
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(statusLabel);
        form.add(Box.createVerticalStrut(6));
        cboTrangThai = new JComboBox<>(new String[]{
            "Đang áp dụng", "Chưa bắt đầu", "Hết hạn"
        });
        styleCombo(cboTrangThai);
        cboTrangThai.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        cboTrangThai.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(cboTrangThai);
        form.add(Box.createVerticalStrut(18));

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(235, 238, 242));
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        form.add(separator);
        form.add(Box.createVerticalStrut(14));

        JPanel buttons = new JPanel(new BorderLayout(10, 0));
        buttons.setOpaque(false);
        btnLuu = makeButton("Lưu dữ liệu", BLUE, Color.WHITE);
        btnLuu.addActionListener(e -> {
            if(txtTenKM.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên chương trình khuyến mãi!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, dangThem ? "Thêm khuyến mãi mới thành công!" : "Cập nhật khuyến mãi thành công!");
        });

        btnLamMoi = makeButton("Làm mới", new Color(244, 246, 249), TEXT);
        btnLamMoi.addActionListener(e -> clearForm());

        btnLuu.setPreferredSize(new Dimension(0, 40));
        btnLamMoi.setPreferredSize(new Dimension(100, 40));
        buttons.add(btnLuu, BorderLayout.CENTER);
        buttons.add(btnLamMoi, BorderLayout.EAST);
        buttons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(buttons);

        outer.add(form, BorderLayout.CENTER);
        return outer;
    }

    private void addCustomField(JPanel parent, String label, JTextField field) {
        JLabel l = fieldLabel(label);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(l);
        parent.add(Box.createVerticalStrut(6));
        styleField(field);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(field);
        parent.add(Box.createVerticalStrut(16));
    }

    private JPanel dateField(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setOpaque(false);
        p.add(fieldLabel(label), BorderLayout.NORTH);
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

    private void clearForm() {
        txtMaKM.setText("");
        txtTenKM.setText("");
        txtChietKhau.setText("");
        txtDichVu.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        cboTrangThai.setSelectedIndex(0);
        txtTenKM.requestFocus();
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