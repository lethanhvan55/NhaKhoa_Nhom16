
package model;

import java.time.LocalDate;

public class VatTu {
    private String maVT;
    private String tenVatTu;
    private String donViTinh;
    private int soLuongTon;
    private int soLuongToiThieu;
    private LocalDate hanSuDung;
    private String hinhAnh;
    private String moTa;
    

    public VatTu() {
    }

    
    public VatTu(String maVT, String tenVatTu, String donViTinh, int soLuongTon, int soLuongToiThieu,
			LocalDate hanSuDung, String moTa, String hinhAnh) {
		super();
		this.maVT = maVT;
		this.tenVatTu = tenVatTu;
		this.donViTinh = donViTinh;
		this.soLuongTon = soLuongTon;
		this.soLuongToiThieu = soLuongToiThieu;
		this.hanSuDung = hanSuDung;
		this.moTa = moTa;
		this.hinhAnh = hinhAnh;
	}


	public String getMaVT() {
        return maVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }

    public String getTenVatTu() {
        return tenVatTu;
    }

    public void setTenVatTu(String tenVatTu) {
        this.tenVatTu = tenVatTu;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getSoLuongToiThieu() {
        return soLuongToiThieu;
    }

    public void setSoLuongToiThieu(int soLuongToiThieu) {
        this.soLuongToiThieu = soLuongToiThieu;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }


	public String getHinhAnh() {
		return hinhAnh;
	}


	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
    
}
