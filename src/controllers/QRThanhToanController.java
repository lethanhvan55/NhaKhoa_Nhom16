package controllers;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import ui.QRThanhToan;

public class QRThanhToanController {


    @FXML
    private Label lblMaHD;

    @FXML
    private Label lblSoTien;

    @FXML
    private Label lblTrangThai;

    @FXML
    private Button btnXacNhan;

    @FXML
    private Button btnHuy;

    @FXML
    private WebView webQR;

    private Runnable onPaymentConfirmed;

    public void setThongTin(double tongTien, String maHD) {
        lblMaHD.setText("Mã hóa đơn: " + maHD);
        lblSoTien.setText(
                String.format("Số tiền: %,.0f VNĐ", tongTien)
        );

        lblTrangThai.setText("Đang tải mã QR...");

        String url = QRThanhToan.taoURLQR(
                tongTien,
                "Thanh toan " + maHD
        );

        webQR.setPrefSize(280, 280);
        webQR.setMinSize(280, 280);
        webQR.setMaxSize(280, 280);
        webQR.setZoom(0.43);

        webQR.getEngine().getLoadWorker().stateProperty().addListener(
                (observable, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        lblTrangThai.setText("Đã tải mã QR.");
                        System.out.println("Tải QR thành công.");
                    } else if (newState == Worker.State.FAILED) {
                        lblTrangThai.setText("Không tải được QR.");

                        Throwable loi = webQR.getEngine()
                                .getLoadWorker()
                                .getException();

                        if (loi != null) {
                            loi.printStackTrace();
                        }
                    }
                }
        );

        webQR.getEngine().load(url);
    }

    public void setOnPaymentConfirmed(Runnable onPaymentConfirmed) {
        this.onPaymentConfirmed = onPaymentConfirmed;
    }

    @FXML
    public void initialize() {
        btnXacNhan.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận thanh toán");
            alert.setHeaderText("Xác nhận giao dịch");
            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if (onPaymentConfirmed != null) {
                    onPaymentConfirmed.run();
                }

                dongCuaSo();
            }
        });

        btnHuy.setOnAction(e -> dongCuaSo());
    }

    private void dongCuaSo() {
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
    }
}