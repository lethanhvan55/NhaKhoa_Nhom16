package model;

public class DichVu {
    private String maDV;
    private String tenDV;
    private double donGia;
    private String thoiGianDuKien;
    private String trangThai;

    public DichVu() {
    }

    public DichVu(String maDV, String tenDV, double donGia, String thoiGianDuKien, String trangThai) {
        setMaDV(maDV);
        setTenDV(tenDV);
        setDonGia(donGia);
        setThoiGianDuKien(thoiGianDuKien);
        setTrangThai(trangThai);
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        if (maDV == null || maDV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã dịch vụ không được để trống!");
        }
        this.maDV = maDV.trim();
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        if (tenDV == null || tenDV.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên dịch vụ không được để trống!");
        }
        this.tenDV = tenDV.trim();
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        if (donGia < 0) {
            throw new IllegalArgumentException("Đơn giá dịch vụ không được âm!");
        }
        this.donGia = donGia;
    }

    public String getThoiGianDuKien() {
        return thoiGianDuKien;
    }

    public void setThoiGianDuKien(String thoiGianDuKien) {
        if (thoiGianDuKien == null || thoiGianDuKien.trim().isEmpty()) {
            throw new IllegalArgumentException("Thời gian dự kiến không được để trống!");
        }
        this.thoiGianDuKien = thoiGianDuKien.trim();
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        if (trangThai == null || trangThai.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái dịch vụ không được để trống!");
        }
        this.trangThai = trangThai.trim();
    }
}