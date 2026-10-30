
package controllers;

import dao.ChiTietHoaDon_DAO;
import model.HoaDon;
import model.ChiTietHoaDon;
import model.BenhNhan;
import model.NhanVien;
import model.DichVu;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class HoaDonInController {

    @FXML
    private Label lblMaHD;

    @FXML
    private Label lblNgayLap;

    @FXML
    private Label lblTenBN;

    @FXML
    private Label lblSoDT;

    @FXML
    private Label lblNhanVien;

    @FXML
    private VBox vboxChiTiet;

    @FXML
    private Label lblTongTien;

    @FXML
    private Label lblGiamGia;

    @FXML
    private Label lblThue;

    @FXML
    private Label lblTongTT;

    private final ChiTietHoaDon_DAO chiTietDAO = new ChiTietHoaDon_DAO();

    private final NumberFormat tienVietNam =
            NumberFormat.getNumberInstance(new Locale("vi", "VN"));

    private final DateTimeFormatter dinhDangNgay =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {
        tienVietNam.setMaximumFractionDigits(0);
    }

    public void setData(HoaDon hd) {
        if (hd == null) {
            return;
        }

        lblMaHD.setText(hd.getMaHD());

        if (hd.getNgayLap() != null) {
            lblNgayLap.setText(hd.getNgayLap().format(dinhDangNgay));
        } else {
            lblNgayLap.setText("");
        }

        BenhNhan benhNhan = hd.getMaBenhNhan();

        if (benhNhan != null) {
            lblTenBN.setText(benhNhan.getHoTen());
            lblSoDT.setText(benhNhan.getSoDT());
        } else {
            lblTenBN.setText("");
            lblSoDT.setText("");
        }

        NhanVien nhanVien = hd.getMaNV();

        if (nhanVien != null) {
            lblNhanVien.setText(nhanVien.getHoTen());
        } else {
            lblNhanVien.setText("");
        }

        hienThiChiTiet(hd);
    }

    private void hienThiChiTiet(HoaDon hd) {
        vboxChiTiet.getChildren().clear();

        try {
            List<ChiTietHoaDon> danhSach =
                    chiTietDAO.getChiTietHoaDon(hd.getMaHD());

            for (ChiTietHoaDon ct : danhSach) {
                DichVu dichVu = ct.getMaDV();

                String tenDichVu = dichVu != null
                        ? dichVu.getTenDV()
                        : "";

                HBox dong = new HBox(8);
                dong.setAlignment(Pos.CENTER_LEFT);
                dong.setPadding(new Insets(8, 0, 8, 0));
                dong.setStyle("-fx-border-color: transparent transparent #dddddd transparent;");

                Label lblTenDV = new Label(tenDichVu);
                lblTenDV.setPrefWidth(250);
                lblTenDV.setWrapText(true);

                Label lblSoLuong = new Label(String.valueOf(ct.getSoLuong()));
                lblSoLuong.setPrefWidth(55);
                lblSoLuong.setAlignment(Pos.CENTER);

                Label lblDonGia = new Label(
                        tienVietNam.format(ct.getDonGia())
                );
                lblDonGia.setPrefWidth(100);
                lblDonGia.setAlignment(Pos.CENTER_RIGHT);

                Label lblThanhTien = new Label(
                        tienVietNam.format(ct.tinhThanhTien())
                );
                lblThanhTien.setPrefWidth(110);
                lblThanhTien.setAlignment(Pos.CENTER_RIGHT);

                dong.getChildren().addAll(
                        lblTenDV,
                        lblSoLuong,
                        lblDonGia,
                        lblThanhTien
                );

                vboxChiTiet.getChildren().add(dong);
            }

            double tongTien = hd.tinhTongTien(danhSach);
            hienThiTongTien(hd, tongTien);

        } catch (SQLException e) {
            e.printStackTrace();
            lblTongTien.setText("Không thể tải chi tiết hóa đơn");
        }
    }

    private void hienThiTongTien(HoaDon hd, double tongTien) {
        double tienGiam = hd.tinhTienGiamGia(tongTien);
        double tienVAT = hd.tinhTienVAT(tongTien);
        double tongThanhToan = hd.tinhTongThanhToan(tongTien);

        lblTongTien.setText(tienVietNam.format(tongTien) + " VNĐ");
        lblGiamGia.setText(tienVietNam.format(tienGiam) + " VNĐ");
        lblThue.setText(tienVietNam.format(tienVAT) + " VNĐ");
        lblTongTT.setText(tienVietNam.format(tongThanhToan) + " VNĐ");
    }
}
