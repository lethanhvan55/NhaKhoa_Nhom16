
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class DichVuPanel extends JPanel {

    private final Color NAVY = new Color(18, 39, 70);
    private final Color BLUE = new Color(27, 119, 235);
    private final Color BG = new Color(243, 246, 250);
    private static final Color TEXT = new Color(45, 55, 68);

    private JTable table;

    private JTextField txtTuNgay;
    private JTextField txtDenNgay;
    private JTextField txtMaDichVu;
    private JTextField txtTenDichVu;
    private JTextField txtDonGia;
    private JTextField txtNgayDuKien;

    private JComboBox<String> cboLocTrangThai;
    private JComboBox<String> cboTrangThai;

    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnCapNhat;
    private JButton btnXoa;

    public DichVuPanel() {
        setLayout(new BorderLayout());
        setBackground(BG);

        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(17, 22, 17, 24));

        JLabel title = new JLabel("Quản lý dịch vụ");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(NAVY);
        title.setIcon(FontIcon.of(FontAwesomeSolid.TOOTH, 22, NAVY));

        JLabel user = new JLabel("NV001  |  Lễ tân");
        user.setFont(new Font("Arial", Font.BOLD, 13));
        user.setForeground(TEXT);
        user.setIcon(FontIcon.of(FontAwesomeSolid.USER, 15, TEXT));

        header.add(title, BorderLayout.WEST);
        header.add(user, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(18, 0));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel left = new JPanel(new BorderLayout(0, 16));
        left.setBackground(BG);
        left.add(createFilters(), BorderLayout.NORTH);
        left.add(createTablePanel(), BorderLayout.CENTER);

        JPanel right = createFormPanel();
        right.setPreferredSize(new Dimension(345, 0));

        content.add(left, BorderLayout.CENTER);
        content.add(right, BorderLayout.EAST);

        main.add(content, BorderLayout.CENTER);

        return main;
    }

    private JPanel whiteCard() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(226, 231, 238)),
                        new EmptyBorder(16, 16, 16, 16)
                )
        );
        return panel;
    }

    private JPanel createFilters() {
        JPanel card = whiteCard();
        card.setLayout(new GridLayout(1, 3, 12, 0));
        card.setPreferredSize(new Dimension(0, 92));

        card.add(filterField("Từ ngày", "2026-10-01"));
        card.add(filterField("Đến ngày", "2026-10-31"));

        JPanel status = new JPanel(new BorderLayout(0, 6));
        status.setOpaque(false);
        status.add(fieldLabel("Trạng thái"), BorderLayout.NORTH);

        cboLocTrangThai = new JComboBox<String>(
                new String[]{
                    "Tất cả",
                    "Đang cung cấp",
                    "Ngừng cung cấp"
                }
        );

        styleCombo(cboLocTrangThai);
        status.add(cboLocTrangThai, BorderLayout.CENTER);
        card.add(status);

        return card;
    }

    private JPanel filterField(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);
        panel.add(fieldLabel(label), BorderLayout.NORTH);

        JTextField field = new JTextField(value);
        styleField(field);

        if (label.equals("Từ ngày")) {
            txtTuNgay = field;
        } else {
            txtDenNgay = field;
        }

        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel card = whiteCard();
        card.setLayout(new BorderLayout(0, 12));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);

        JLabel title = new JLabel("Danh sách dịch vụ");
        title.setFont(new Font("Arial", Font.BOLD, 17));
        title.setForeground(TEXT);

        JLabel total = new JLabel("Tổng số dịch vụ: 5");
        total.setFont(new Font("Arial", Font.PLAIN, 12));
        total.setForeground(new Color(100, 110, 122));
        total.setBorder(new EmptyBorder(7, 10, 7, 10));
        total.setOpaque(true);
        total.setBackground(new Color(244, 246, 249));

        titleRow.add(title, BorderLayout.WEST);
        titleRow.add(total, BorderLayout.EAST);
        card.add(titleRow, BorderLayout.NORTH);

        String[] columns = {
            "STT",
            "MÃ DỊCH VỤ",
            "TÊN DỊCH VỤ",
            "ĐƠN GIÁ",
            "NGÀY DỰ KIẾN",
            "TRẠNG THÁI"
        };

        Object[][] data = {
            {"1", "DV0001", "Khám tổng quát", "500.000 đ",
                "04/10/2026", "Đang cung cấp"},
            {"2", "DV0002", "Lấy cao răng", "300.000 đ",
                "04/10/2026", "Đang cung cấp"},
            {"3", "DV0003", "Tẩy trắng răng", "1.500.000 đ",
                "03/10/2026", "Đang cung cấp"},
            {"4", "DV0004", "Nhổ răng khôn", "1.200.000 đ",
                "02/10/2026", "Ngừng cung cấp"},
            {"5", "DV0005", "Trám răng thẩm mỹ", "400.000 đ",
                "01/10/2026", "Đang cung cấp"}
        };

        table = new JTable(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setRowHeight(48);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setForeground(TEXT);
        table.setGridColor(new Color(238, 241, 245));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(239, 247, 255));
        table.setSelectionForeground(TEXT);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 44));
        header.setFont(new Font("Arial", Font.BOLD, 11));
        header.setForeground(new Color(105, 115, 126));
        header.setBackground(new Color(248, 249, 251));
        header.setReorderingAllowed(false);

        int[] widths = {45, 90, 155, 100, 110, 125};

        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(widths[i]);
        }

        table.getColumnModel()
                .getColumn(1)
                .setCellRenderer(new BlueTextRenderer());

        table.getColumnModel()
                .getColumn(5)
                .setCellRenderer(new StatusRenderer());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        card.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        JLabel showing = new JLabel("Hiển thị 1 - 5 của 5 dịch vụ");
        showing.setForeground(new Color(130, 140, 150));
        showing.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel pages = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pages.setOpaque(false);

        JButton previous = makeButton("", Color.WHITE, TEXT);
        previous.setIcon(
                FontIcon.of(FontAwesomeSolid.ANGLE_LEFT, 14, TEXT)
        );

        JButton current = makeButton("1", BLUE, Color.WHITE);

        JButton next = makeButton("", Color.WHITE, TEXT);
        next.setIcon(
                FontIcon.of(FontAwesomeSolid.ANGLE_RIGHT, 14, TEXT)
        );

        previous.setPreferredSize(new Dimension(32, 30));
        current.setPreferredSize(new Dimension(32, 30));
        next.setPreferredSize(new Dimension(32, 30));

        pages.add(previous);
        pages.add(current);
        pages.add(next);

        bottom.add(showing, BorderLayout.WEST);
        bottom.add(pages, BorderLayout.EAST);

        card.add(bottom, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createFormPanel() {
        JPanel card = whiteCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Thông tin dịch vụ");
        heading.setFont(new Font("Arial", Font.BOLD, 17));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(heading);
        card.add(Box.createVerticalStrut(18));

        txtMaDichVu = addFormField(
                card, "Mã dịch vụ *", "DV0001"
        );

        txtTenDichVu = addFormField(
                card, "Tên dịch vụ *", "Khám tổng quát"
        );

        txtDonGia = addFormField(
                card, "Đơn giá hiện hành (VNĐ) *", "500000"
        );

        txtNgayDuKien = addFormField(
                card, "Ngày dự kiến *", "2026-10-04"
        );

        JLabel statusLabel = fieldLabel("Trạng thái dịch vụ *");
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(statusLabel);
        card.add(Box.createVerticalStrut(6));

        cboTrangThai = new JComboBox<String>(
                new String[]{
                    "Đang cung cấp",
                    "Ngừng cung cấp"
                }
        );

        styleCombo(cboTrangThai);
        cboTrangThai.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 36)
        );
        cboTrangThai.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(cboTrangThai);
        card.add(Box.createVerticalStrut(18));

        btnCapNhat = makeButton(
                "Cập nhật dịch vụ", BLUE, Color.WHITE
        );

        btnCapNhat.setIcon(
                FontIcon.of(FontAwesomeSolid.SAVE, 15, Color.WHITE)
        );
        btnCapNhat.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCapNhat.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38)
        );

        card.add(btnCapNhat);
        card.add(Box.createVerticalStrut(10));

        JPanel actions = new JPanel(new GridLayout(1, 2, 8, 0));
        actions.setOpaque(false);

        btnLamMoi = makeButton("Làm mới", Color.WHITE, TEXT);
        btnLamMoi.setIcon(
                FontIcon.of(FontAwesomeSolid.SYNC_ALT, 13, TEXT)
        );
        btnLamMoi.setBorder(
                new LineBorder(new Color(210, 216, 224))
        );

        btnXoa = makeButton(
                "Xóa dịch vụ", Color.WHITE, new Color(190, 65, 65)
        );
        btnXoa.setIcon(
                FontIcon.of(
                        FontAwesomeSolid.TRASH_ALT,
                        13,
                        new Color(190, 65, 65)
                )
        );
        btnXoa.setBorder(
                new LineBorder(new Color(224, 190, 190))
        );

        actions.add(btnLamMoi);
        actions.add(btnXoa);
        actions.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 36)
        );
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(actions);
        card.add(Box.createVerticalGlue());

        return card;
    }

    private JTextField addFormField(
            JPanel parent, String label, String value) {

        JLabel fieldName = fieldLabel(label);
        fieldName.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(fieldName);
        parent.add(Box.createVerticalStrut(6));

        JTextField field = new JTextField(value);
        styleField(field);

        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 36)
        );
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(field);
        parent.add(Box.createVerticalStrut(16));

        return field;
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
        field.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(211, 217, 225)),
                        new EmptyBorder(7, 10, 7, 10)
                )
        );
        field.setPreferredSize(new Dimension(120, 36));
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(new Font("Arial", Font.PLAIN, 13));
        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);
        combo.setBorder(
                new LineBorder(new Color(211, 217, 225))
        );
        combo.setPreferredSize(new Dimension(120, 36));
    }

    private JButton makeButton(
            String text, Color background, Color foreground) {

        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                new LineBorder(
                        background.equals(Color.WHITE)
                                ? new Color(215, 220, 227)
                                : background
                )
        );

        return button;
    }

    private static class BlueTextRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focus,
                int row,
                int column) {

            JLabel label = (JLabel) super
                    .getTableCellRendererComponent(
                            table, value, selected, focus, row, column
                    );

            label.setBorder(new EmptyBorder(0, 6, 0, 4));
            label.setForeground(
                    selected ? new Color(45, 55, 68)
                            : new Color(42, 113, 190)
            );
            label.setFont(new Font("Arial", Font.BOLD, 12));

            return label;
        }
    }

    private static class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focus,
                int row,
                int column) {

            JLabel label = (JLabel) super
                    .getTableCellRendererComponent(
                            table, value, selected, focus, row, column
                    );

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 10));
            label.setOpaque(true);
            label.setBorder(new EmptyBorder(4, 3, 4, 3));

            if (String.valueOf(value).contains("Ngừng")) {
                label.setForeground(new Color(161, 59, 75));
                label.setBackground(new Color(255, 229, 233));
            } else {
                label.setForeground(new Color(16, 125, 91));
                label.setBackground(new Color(211, 250, 233));
            }

            if (selected) {
                label.setBackground(new Color(239, 247, 255));
                label.setForeground(TEXT);
            }

            return label;
        }
    }
}
