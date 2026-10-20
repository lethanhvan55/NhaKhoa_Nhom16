
package model;

public class DichVu {
    private String maDV;
    private String tenDV;
    private double donGia;
    private int thoiGianDuKien;
    private String trangThai;

    public DichVu() {
    }

    public DichVu(String maDV, String tenDV, double donGia,int thoiGianDuKien, String trangThai) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.donGia = donGia;
        this.thoiGianDuKien = thoiGianDuKien;
        this.trangThai = trangThai;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getThoiGianDuKien() {
        return thoiGianDuKien;
    }

    public void setThoiGianDuKien(int thoiGianDuKien) {
        this.thoiGianDuKien = thoiGianDuKien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
