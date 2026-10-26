package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        JLabel label = new JLabel("", JLabel.CENTER);

        if (value != null) {
            try {
                Image img = new ImageIcon(getClass().getResource(value.toString()))
                        .getImage()
                        .getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                label.setText("No Img");
            }
        }

        return label;
    }
}