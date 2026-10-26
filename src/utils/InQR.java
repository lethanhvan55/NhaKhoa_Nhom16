package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class InQR {

    public static BufferedImage taoAnhQR(String noiDung, int kichThuoc) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        hints.put(EncodeHintType.MARGIN, 2);

        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix maQR = qrWriter.encode(noiDung, BarcodeFormat.QR_CODE, kichThuoc, kichThuoc, hints
        );

        return MatrixToImageWriter.toBufferedImage(maQR);
    }

    public static void hienThiQR(Component parent, String tieuDe, String noiDung, String maKM) {
        try {
            BufferedImage anhQR = taoAnhQR(noiDung, 300);

            JLabel lblTen = new JLabel(tieuDe);
            lblTen.setFont(new Font("Segoe UI", Font.BOLD, 15));
            lblTen.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel lblQR = new JLabel(new ImageIcon(anhQR));
            lblQR.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel pnlQR = new JPanel(new BorderLayout(10, 10));
            pnlQR.setBorder(new EmptyBorder(15, 15, 15, 15));
            pnlQR.add(lblTen, BorderLayout.NORTH);
            pnlQR.add(lblQR, BorderLayout.CENTER);

            int luaChon = JOptionPane.showConfirmDialog(parent, pnlQR, "Mã QR khuyến mãi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (luaChon == JOptionPane.OK_OPTION) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Lưu mã QR");
                chooser.setSelectedFile(new File("QR_" + maKM + ".png"));

                if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();

                    if (!file.getName().toLowerCase().endsWith(".png")) {
                        file = new File(file.getAbsolutePath() + ".png");
                    }

                    ImageIO.write(anhQR, "PNG", file);

                    JOptionPane.showMessageDialog(parent,"Đã lưu mã QR tại:\n" + file.getAbsolutePath()
                    );
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,"Không thể tạo mã QR: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}