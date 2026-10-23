
package model;

//import java.math.BigDecimal;
import java.time.LocalDate;

public class KhuyenMai {
    private String maKM;
    private String maThayThe;
    private String tenKM;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String dichVuApDung;
    private String dieuKien;
    private double chietKhau;
    private String trangThai;

    public KhuyenMai() {
    }

    public KhuyenMai(String maKM, String maThayThe, String tenKM, LocalDate ngayBatDau, LocalDate ngayKetThuc, String dichVuApDung, String dieuKien, double chietKhau, String trangThai) {
        this.maKM = maKM;
        this.maThayThe = maThayThe;
        this.tenKM = tenKM;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.dichVuApDung = dichVuApDung;
        this.dieuKien = dieuKien;
        this.chietKhau = chietKhau;
        this.trangThai = trangThai;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getMaThayThe() {
        return maThayThe;
    }

    public void setMaThayThe(String maThayThe) {
        this.maThayThe = maThayThe;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getDichVuApDung() {
        return dichVuApDung;
    }

    public void setDichVuApDung(String dichVuApDung) {
        this.dichVuApDung = dichVuApDung;
    }

    public String getDieuKien() {
        return dieuKien;
    }

    public void setDieuKien(String dieuKien) {
        this.dieuKien = dieuKien;
    }

    public double getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(double chietKhau) {
        this.chietKhau = chietKhau;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "maKM='" + maKM + '\'' +
                ", maThayThe='" + maThayThe + '\'' +
                ", tenKM='" + tenKM + '\'' +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", dichVuApDung='" + dichVuApDung + '\'' +
                ", dieuKien='" + dieuKien + '\'' +
                ", chietKhau=" + chietKhau +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
