
package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

public class GiaoDienStyle {

    public static final Color MAU_NAVY = new Color(32, 54, 83);
    public static final Color MAU_TEAL = new Color(13, 148, 136);
    public static final Color MAU_NEN = new Color(241, 245, 249);
    public static final Color MAU_TRANG = Color.WHITE;
    public static final Color MAU_VIEN = new Color(220, 228, 237);
    public static final Color MAU_CHU = new Color(51, 65, 85);
    public static final Color MAU_PHU = new Color(100, 116, 139);
    public static final Color MAU_XANH_LA = new Color(22, 163, 74);
    public static final Color MAU_XANH_DUONG = new Color(37, 99, 235);
    public static final Color MAU_VANG = new Color(217, 119, 6);
    public static final Color MAU_DO = new Color(220, 68, 68);

    private GiaoDienStyle() {
    }

    public static JButton taoNut(
            String tenNut,
            Color mauNen,
            int chieuRong) {

        JButton btn = new JButton(tenNut);

        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mauNen);
        btn.setPreferredSize(new Dimension(chieuRong, 36));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        );

        return btn;
    }

    public static void dinhDangBang(JTable tbl) {
        tbl.setFont(new Font("Arial", Font.PLAIN, 13));
        tbl.setForeground(MAU_CHU);
        tbl.setBackground(MAU_TRANG);
        tbl.setRowHeight(32);
        tbl.setGridColor(MAU_VIEN);
        tbl.setShowVerticalLines(false);
        tbl.setShowHorizontalLines(true);
        tbl.setSelectionBackground(new Color(204, 251, 241));
        tbl.setSelectionForeground(MAU_NAVY);
        tbl.setFillsViewportHeight(true);

        JTableHeader tblHeader = tbl.getTableHeader();
        tblHeader.setFont(new Font("Arial", Font.BOLD, 12));
        tblHeader.setBackground(MAU_NAVY);
        tblHeader.setForeground(Color.WHITE);
        tblHeader.setPreferredSize(new Dimension(100, 38));
        tblHeader.setReorderingAllowed(false);
    }
}
