
package model;

import java.time.LocalDate;

public class PhieuNhapHang {
    private String maPhieuNhap;
    private LocalDate ngayNhap;
    private NhanVien maNV;
    private String nhaCungCap;
    private String ghiChu;

    public PhieuNhapHang() {
    }

    public PhieuNhapHang(String maPhieuNhap, LocalDate ngayNhap,NhanVien maNV, String nhaCungCap, String ghiChu) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
        this.maNV = maNV;
        this.nhaCungCap = nhaCungCap;
        this.ghiChu = ghiChu;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public NhanVien getMaNV() {
        return maNV;
    }

    public void setMaNV(NhanVien maNV) {
        this.maNV = maNV;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
