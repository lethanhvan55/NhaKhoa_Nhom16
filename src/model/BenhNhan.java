
package model;

import java.time.LocalDate;

public class BenhNhan {
    private String maBenhNhan;
    private String hoTen;
    private LocalDate ngaySinh;
    private boolean gioiTinh;
    private String soDT;
    private String email;
    private String diaChi;

    public BenhNhan() {
    }

    public BenhNhan(String maBenhNhan, String hoTen, LocalDate ngaySinh,boolean gioiTinh, String soDT, String email, String diaChi) {
        this.maBenhNhan = maBenhNhan;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.email = email;
        this.diaChi = diaChi;
    }

    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
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
		return "BenhNhan [maBenhNhan=" + maBenhNhan + ", hoTen=" + hoTen + ", ngaySinh=" + ngaySinh + ", gioiTinh="
				+ gioiTinh + ", soDT=" + soDT + ", email=" + email + ", diaChi=" + diaChi + "]";
	}
    
}
