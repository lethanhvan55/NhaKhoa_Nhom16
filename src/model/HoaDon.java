
package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HoaDon {
    private String maHD;
    private BenhNhan maBenhNhan;
    private NhanVien maNV;
    private LocalDate ngayLap;
    private BigDecimal chietKhau;
    private KhuyenMai maKM;
    private String trangThai;

    public HoaDon() {
    }

    public HoaDon(String maHD, BenhNhan maBenhNhan, NhanVien maNV, LocalDate ngayLap, BigDecimal chietKhau,
			KhuyenMai maKM, String trangThai) {
		super();
		this.maHD = maHD;
		this.maBenhNhan = maBenhNhan;
		this.maNV = maNV;
		this.ngayLap = ngayLap;
		this.chietKhau = chietKhau;
		this.maKM = maKM;
		this.trangThai = trangThai;
	}



	public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
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

	public void setMaKM(KhuyenMai maKM) {
		this.maKM = maKM;
	}

	public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public BigDecimal getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(BigDecimal chietKhau) {
        this.chietKhau = chietKhau;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
