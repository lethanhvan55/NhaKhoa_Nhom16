package utils;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class UIHelper {
    // Bảng màu y tế cao cấp
    public static final Color NAVY = new Color(22, 67, 120);     // Xanh hải quân
    public static final Color BLUE = new Color(35, 105, 180);    // Xanh dương tươi
    public static final Color TEAL = new Color(15, 150, 137);    // Xanh ngọc / cổ vịt nổi bật
    public static final Color BG = new Color(242, 246, 250);     // Nền xám nhạt dịu mắt
    public static final Color BORDER = new Color(210, 225, 240); // Viền mảnh
    public static final Color TEXT = new Color(35, 55, 75);      // Chữ chính
    public static final Color MUTED = new Color(110, 125, 140);  // Chữ phụ

    public static JPanel createCardPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(16, 16, 16, 16)));
        return p;
    }

    public static JLabel createIconLabel(FontAwesomeSolid iconEnum, int size, Color color) {
        JLabel label = new JLabel();
        if (iconEnum != null) {
            label.setIcon(FontIcon.of(iconEnum, size, color));
        }
        return label;
    }

    public static void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT);
        field.setBorder(new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(6, 10, 6, 10)));
        field.setPreferredSize(new Dimension(100, 36));
        field.putClientProperty("JComponent.roundRect", true);
    }

    public static void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(140, 36));
        combo.setBorder(new LineBorder(BORDER, 1, true));
    }

    public static void styleButton(JButton button, boolean primary, FontAwesomeSolid iconEnum) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(primary ? Color.WHITE : TEXT);
        button.setBackground(primary ? BLUE : Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(new CompoundBorder(
                new LineBorder(primary ? BLUE : BORDER, 1, true),
                new EmptyBorder(6, 14, 6, 14)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (iconEnum != null) {
            Color iconColor = primary ? Color.WHITE : NAVY;
            button.setIcon(FontIcon.of(iconEnum, 14, iconColor));
            button.setIconTextGap(8);
        }

        button.putClientProperty("JButton.buttonType", "roundRect");
    }
}