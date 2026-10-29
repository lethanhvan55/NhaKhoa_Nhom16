package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.Component;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import controllers.QRThanhToanController;

public final class QRThanhToan {

    private static final String BANK_ID = "970422";
    private static final String ACCOUNT_NO = "0999999999";
    private static final String ACCOUNT_NAME = "LE THI KIM VUI";

    private QRThanhToan() {
    }

    public static String taoURLQR(double soTien, String noiDung) {
        String encodedInfo = URLEncoder.encode(noiDung, StandardCharsets.UTF_8);

        String encodedName = URLEncoder.encode(ACCOUNT_NAME, StandardCharsets.UTF_8);

        String url = "https://img.vietqr.io/image/"
                + BANK_ID + "-" + ACCOUNT_NO + "-compact2.svg"
                + "?amount=" + Math.round(soTien)
                + "&addInfo=" + encodedInfo
                + "&accountName=" + encodedName;

        System.out.println("URL QR: " + url);

        return url;
    }
  /*  public static javafx.scene.image.Image taoImageQR(
            double soTien,
            String noiDung) {

        String url = taoURLQR(soTien, noiDung);
        return new javafx.scene.image.Image(url, true);
    }
*/
    public static void hienThiQRPanel(
            Component parent,
            double tongTien,
            String maHD,
            Runnable onPaymentConfirmed) {

        if (tongTien <= 0 || maHD == null || maHD.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Số tiền hoặc mã hóa đơn không hợp lệ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JavaFXRuntime.chay(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(QRThanhToan.class.getResource("/FXML/QRThanhToan.fxml"));
                Parent root = loader.load();
                QRThanhToanController controller = loader.getController();
                controller.setThongTin(tongTien, maHD);
                controller.setOnPaymentConfirmed(() -> {
                    if (onPaymentConfirmed != null) {
                        SwingUtilities.invokeLater(onPaymentConfirmed);
                    }
                });

                Stage stage = new Stage();
                stage.setTitle("Thanh toán qua QR");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi thanh toán QR");
                alert.setHeaderText("Không thể mở cửa sổ QR");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            }
        });
    }
}