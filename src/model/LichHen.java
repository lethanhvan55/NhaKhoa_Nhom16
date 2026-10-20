
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LichHen {
    private String maLichHen;
    private BenhNhan maBenhNhan;
    private DichVu maDichVu;
    private PhongDieuTri maPhong;
    private NhanVien maNV;
    private LocalDate ngayHen;
    private LocalDateTime gioHen;
    private String trangThai;
    private String ghiChu;

    public LichHen() {
    }

    
    public LichHen(String maLichHen, BenhNhan maBenhNhan, DichVu maDichVu, PhongDieuTri maPhong, NhanVien maNV,
			LocalDate ngayHen, LocalDateTime gioHen, String trangThai, String ghiChu) {
		super();
		this.maLichHen = maLichHen;
		this.maBenhNhan = maBenhNhan;
		this.maDichVu = maDichVu;
		this.maPhong = maPhong;
		this.maNV = maNV;
		this.ngayHen = ngayHen;
		this.gioHen = gioHen;
		this.trangThai = trangThai;
		this.ghiChu = ghiChu;
	}


	public void setMaBenhNhan(BenhNhan maBenhNhan) {
		this.maBenhNhan = maBenhNhan;
	}


	public void setMaDichVu(DichVu maDichVu) {
		this.maDichVu = maDichVu;
	}


	public void setMaPhong(PhongDieuTri maPhong) {
		this.maPhong = maPhong;
	}


	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}


	public String getMaLichHen() {
        return maLichHen;
    }

    public void setMaLichHen(String maLichHen) {
        this.maLichHen = maLichHen;
    }

    public LocalDate getNgayHen() {
        return ngayHen;
    }

    public void setNgayHen(LocalDate ngayHen) {
        this.ngayHen = ngayHen;
    }

    public LocalDateTime getGioHen() {
        return gioHen;
    }

    public void setGioHen(LocalDateTime gioHen) {
        this.gioHen = gioHen;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
