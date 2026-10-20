
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LichLamViec {
    private String maLich;
    private PhongDieuTri maPhong;
    private NhanVien maNhanVien;
    private LocalDate ngayLam;
    private String caLam;
    private LocalDateTime gioBatDau;
    private LocalDateTime gioKetThuc;
    private String trangThai;

    public LichLamViec() {
    }

    

    public LichLamViec(String maLich, PhongDieuTri maPhong, NhanVien maNhanVien, LocalDate ngayLam, String caLam,
			LocalDateTime gioBatDau, LocalDateTime gioKetThuc, String trangThai) {
		super();
		this.maLich = maLich;
		this.maPhong = maPhong;
		this.maNhanVien = maNhanVien;
		this.ngayLam = ngayLam;
		this.caLam = caLam;
		this.gioBatDau = gioBatDau;
		this.gioKetThuc = gioKetThuc;
		this.trangThai = trangThai;
	}



	public String getMaLich() {
        return maLich;
    }

    public void setMaLich(String maLich) {
        this.maLich = maLich;
    }

    public LocalDate getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(LocalDate ngayLam) {
        this.ngayLam = ngayLam;
    }

    public String getCaLam() {
        return caLam;
    }

    public void setCaLam(String caLam) {
        this.caLam = caLam;
    }

    public LocalDateTime getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(LocalDateTime gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public LocalDateTime getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(LocalDateTime gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
