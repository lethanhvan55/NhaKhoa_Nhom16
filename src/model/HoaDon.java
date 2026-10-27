
package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HoaDon {
    private String maHD;
    private BenhNhan maBenhNhan;
    private NhanVien maNV;
    private LichHen maLichHen;
    private LocalDate ngayLap;
    private double chietKhau;
    private double thueVAT;
    private KhuyenMai maKM;
    private String trangThai;

    public HoaDon() {
    }

    public HoaDon(String maHD, BenhNhan maBenhNhan, NhanVien maNV, LichHen maLichHen, LocalDate ngayLap, double chietKhau, KhuyenMai maKM, double thueVAT, String trangThai) {
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

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        if (ngayLap == null || ngayLap.isAfter(LocalDate.now())) {
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
        if (chietKhau <0){
            throw new IllegalArgumentException("VAT không hợp lệ");
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

    public double tinhTienGiam(double tongTienDichVu) {
        return tongTienDichVu * chietKhau / 100.0;
    }

    public double tinhTienSauGiam(double tongTienDichVu) {
        return tongTienDichVu - tinhTienGiam(tongTienDichVu);
    }

    public double tinhTienVAT(double tongTienDichVu) {
        return tinhTienSauGiam(tongTienDichVu) * thueVAT / 100.0;
    }

    public double tinhTongTien(double tongTienDichVu) {
        return tinhTienSauGiam(tongTienDichVu) + tinhTienVAT(tongTienDichVu);
    }


}
