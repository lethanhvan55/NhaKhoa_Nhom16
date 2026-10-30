
package ui;

import model.HoaDon;
import utils.JavaFXRuntime;
import controllers.HoaDonInController;

import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HoaDonInPanel {

    private static VBox taoGiaoDienHoaDon(HoaDon hd) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                HoaDonInPanel.class.getResource("/FXML/HoaDonIn.fxml")
        );

        VBox hoaDonUI = loader.load();

        HoaDonInController ctrl = loader.getController();
        ctrl.setData(hd);

        return hoaDonUI;
    }

    private static void thucHienFX(Runnable hanhDong) {
        JavaFXRuntime.chay(hanhDong);
    }

    public static void inHoaDonFXML(HoaDon hd) {
        if (hd == null) {
            System.err.println("Không có dữ liệu hóa đơn để in!");
            return;
        }

        thucHienFX(() -> {
            try {
                VBox hoaDonUI = taoGiaoDienHoaDon(hd);

                Stage stage = new Stage();
                stage.setTitle("In hóa đơn - " + hd.getMaHD());
                stage.setScene(new Scene(hoaDonUI));

                PrinterJob job = PrinterJob.createPrinterJob();

                if (job == null) {
                    System.err.println("Không khởi tạo được PrinterJob!");
                    return;
                }

                job.getJobSettings().setJobName(hd.getMaHD());

                boolean success = job.showPrintDialog(stage)
                        && job.printPage(hoaDonUI);

                if (success) {
                    job.endJob();
                    System.out.println("In hóa đơn " + hd.getMaHD() + " thành công!");
                } else {
                    job.cancelJob();
                    System.out.println("Đã hủy hoặc in hóa đơn thất bại.");
                }

            } catch (Exception e) {
                System.err.println("Lỗi khi in hóa đơn:");
                e.printStackTrace();
            }
        });
    }

    public static void inHoaDonNhanh(HoaDon hd) {
        if (hd == null) {
            System.err.println("Không có dữ liệu hóa đơn để in!");
            return;
        }

        thucHienFX(() -> {
            try {
                VBox hoaDonUI = taoGiaoDienHoaDon(hd);

                PrinterJob job = PrinterJob.createPrinterJob();

                if (job != null) {
                    job.getJobSettings().setJobName(hd.getMaHD());

                    boolean success = job.printPage(hoaDonUI);

                    if (success) {
                        job.endJob();
                        System.out.println("In nhanh hóa đơn " + hd.getMaHD() + " thành công!");
                    } else {
                        job.cancelJob();
                        System.err.println("In nhanh hóa đơn thất bại!");
                    }
                } else {
                    System.err.println("Không có máy in mặc định!");
                }

            } catch (Exception e) {
                System.err.println("Lỗi khi in nhanh hóa đơn:");
                e.printStackTrace();
            }
        });
    }

    public static void previewHoaDon(HoaDon hd) {
        if (hd == null) {
            System.err.println("Không có dữ liệu hóa đơn để xem trước!");
            return;
        }

        thucHienFX(() -> {
            try {
                VBox root = taoGiaoDienHoaDon(hd);

                Stage stage = new Stage();
                stage.setTitle("Xem trước hóa đơn - " + hd.getMaHD());
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

                System.out.println("Đã hiển thị xem trước hóa đơn " + hd.getMaHD());

            } catch (Exception e) {
                System.err.println("Lỗi khi xem trước hóa đơn:");
                e.printStackTrace();
            }
        });
    }
}
