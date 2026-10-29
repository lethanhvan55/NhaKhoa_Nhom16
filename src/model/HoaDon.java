
package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HoaDon {
    private String maHD;
    private BenhNhan maBenhNhan;
    private NhanVien maNV;
    private LichHen maLichHen;
    private LocalDateTime ngayLap;
    private double chietKhau;
    private double thueVAT = 0.1;
    private KhuyenMai maKM;
    private String trangThai;

    public HoaDon() {
    }

    public HoaDon(String maHD, BenhNhan maBenhNhan, NhanVien maNV, LichHen maLichHen, LocalDateTime ngayLap, double chietKhau, KhuyenMai maKM, double thueVAT, String trangThai) {
        this.maHD = maHD;
        this.maBenhNhan = maBenhNhan;
        this.maNV = maNV;
        this.maLichHen = maLichHen;
        this.ngayLap = ngayLap;
        this.chietKhau = chietKhau;
        this.maKM = maKM;
        this.thueVAT = thueVAT;
        this.trangThai = trangThai;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        if (maHD == null || !maHD.matches("^HD\\d{4}$")) {
            throw new IllegalArgumentException("Mã HD phải dạng HDXXXX");
        }
        this.maHD = maHD;
    }

    public BenhNhan getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(BenhNhan maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public NhanVien getMaNV() {
        return maNV;
    }

    public void setMaNV(NhanVien maNV) {
        this.maNV = maNV;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) {
        if (ngayLap == null || ngayLap.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Ngày lập không được null va không được sau ngày hiện tại");
        }
        this.ngayLap = ngayLap;
    }

    public double getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(double chietKhau) {
        if (chietKhau <0){
            throw new IllegalArgumentException("ChietKhau không hợp lệ");
        }
        this.chietKhau = chietKhau;
    }

    public double getThueVAT() {
        return thueVAT;
    }

    public void setThueVAT(double thueVAT) {
        if (thueVAT < 0 || thueVAT > 1) {
            throw new IllegalArgumentException("VAT phải nằm trong khoảng từ 0 đến 1.");
        }
        this.thueVAT = thueVAT;
    }

    public KhuyenMai getMaKM() {
        return maKM;
    }

    public void setMaKM(KhuyenMai maKM) {
        this.maKM = maKM;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LichHen getMaLichHen() {
        return maLichHen;
    }

    public void setMaLichHen(LichHen maLichHen) {
        this.maLichHen = maLichHen;
    }
    public double tinhTongTien(List<ChiTietHoaDon> danhSachChiTiet) {
        double tongTien = 0;

        for (ChiTietHoaDon chiTiet : danhSachChiTiet) {
            tongTien += chiTiet.tinhThanhTien();
        }

        return tongTien;
    }
    public double tinhTienGiamGia(double tongTien) {
        return tongTien * chietKhau / 100;
    }

    public double tinhTienSauGiam(double tongTien) {
        double tienGiamGia = tinhTienGiamGia(tongTien);
        return Math.max(0, tongTien - tienGiamGia);
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHD='" + maHD + '\'' +
                ", maBenhNhan=" + maBenhNhan +
                ", maNV=" + maNV +
                ", maLichHen=" + maLichHen +
                ", ngayLap=" + ngayLap +
                ", chietKhau=" + chietKhau +
                ", thueVAT=" + thueVAT +
                ", maKM=" + maKM +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
