
package model;

import java.time.LocalDate;

public class NhanVien {
    private String maNV;
    private String hoTen;
    private String soDT;
    private LocalDate ngayVaoLam;
    private Boolean gioiTinh;
    private String chucVu;
    private String trangThai;
    private String email;
    private String diaChi;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, String soDT, LocalDate ngayVaoLam, Boolean gioiTinh, String chucVu, String trangThai, String email, String diaChi) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.ngayVaoLam = ngayVaoLam;
        this.gioiTinh = gioiTinh;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
        this.email = email;
        this.diaChi = diaChi;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNV='" + maNV + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", soDT='" + soDT + '\'' +
                ", ngayVaoLam=" + ngayVaoLam +
                ", gioiTinh=" + gioiTinh +
                ", chucVu='" + chucVu + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", email='" + email + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}
