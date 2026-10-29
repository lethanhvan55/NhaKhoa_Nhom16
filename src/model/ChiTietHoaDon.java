
package model;

public class ChiTietHoaDon {
    private HoaDon maHD;
    private DichVu maDV;
    private int soLuong;
    private double donGia;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(HoaDon maHD, DichVu maDV, int soLuong, double donGia) {
        this.maHD = maHD;
        this.maDV = maDV;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

	public HoaDon getMaHD() {
		return maHD;
	}

	public void setMaHD(HoaDon maHD) {
		this.maHD = maHD;
	}

	public DichVu getMaDV() {
		return maDV;
	}

	public void setMaDV(DichVu maDV) {
		this.maDV = maDV;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		if(soLuong < 0) {
			throw new IllegalArgumentException("Số lượng không được âm.");
		}

		this.soLuong = soLuong;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		if (donGia < 0) {
			throw new IllegalArgumentException("Đơn giá không được âm.");
		}
		this.donGia = donGia;
	}
	public double tinhThanhTien() {
		return soLuong * donGia;
	}

	@Override
	public String toString() {
		return "ChiTietHoaDon{" +
				"maHD=" + maHD +
				", maDV=" + maDV +
				", soLuong=" + soLuong +
				", donGia=" + donGia +
				'}';
	}
}
