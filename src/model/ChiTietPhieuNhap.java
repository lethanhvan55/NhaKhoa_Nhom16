
package model;

import java.time.LocalDate;

public class ChiTietPhieuNhap {
    private PhieuNhapHang maPhieuNhap;
    private VatTu maVatTu;
    private int soLuong;
    private double donGia;
    private LocalDate hanSuDung;

    public ChiTietPhieuNhap() {
    }

    public ChiTietPhieuNhap(PhieuNhapHang maPhieuNhap, VatTu maVatTu,int soLuong, double donGia, LocalDate hanSuDung) {
        this.maPhieuNhap = maPhieuNhap;
        this.maVatTu = maVatTu;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.hanSuDung = hanSuDung;
    }

    public PhieuNhapHang getMaPhieuNhap() {
		return maPhieuNhap;
	}

	public void setMaPhieuNhap(PhieuNhapHang maPhieuNhap) {
		this.maPhieuNhap = maPhieuNhap;
	}

	public VatTu getMaVatTu() {
		return maVatTu;
	}

	public void setMaVatTu(VatTu maVatTu) {
		this.maVatTu = maVatTu;
	}

	public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }
}
