package model;

import java.util.Date;

public class KhuyenMai {
    private String maKM;
    private String tenKM;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String dieuKien;
    private double chietKhau;
    private String trangThai;

    public KhuyenMai() {
    }

    public KhuyenMai(String maKM, String tenKM, Date ngayBatDau, Date ngayKetThuc, 
                     String dieuKien, double chietKhau, String trangThai) {
        setMaKM(maKM);
        setTenKM(tenKM);
        setNgayBatDau(ngayBatDau);
        setNgayKetThuc(ngayKetThuc);
        setDieuKien(dieuKien);
        setChietKhau(chietKhau);
        setTrangThai(trangThai);
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        if (maKM == null || maKM.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khuyến mãi không được để trống!");
        }
        this.maKM = maKM.trim();
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        if (tenKM == null || tenKM.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khuyến mãi không được để trống!");
        }
        this.tenKM = tenKM.trim();
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        if (ngayBatDau == null) {
            throw new IllegalArgumentException("Ngày bắt đầu không được để trống!");
        }
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        if (ngayKetThuc == null) {
            throw new IllegalArgumentException("Ngày kết thúc không được để trống!");
        }
        if (this.ngayBatDau != null && ngayKetThuc.before(this.ngayBatDau)) {
            throw new IllegalArgumentException("Ngày kết thúc không được trước ngày bắt đầu!");
        }
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getDieuKien() {
        return dieuKien;
    }

    public void setDieuKien(String dieuKien) {
        if (dieuKien == null || dieuKien.trim().isEmpty()) {
            throw new IllegalArgumentException("Điều kiện khuyến mãi không được để trống!");
        }
        this.dieuKien = dieuKien.trim();
    }

    public double getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(double chietKhau) {
        if (chietKhau < 0 || chietKhau > 100) {
            throw new IllegalArgumentException("Chiết khấu phải nằm trong khoảng từ 0 đến 100 (%)!");
        }
        this.chietKhau = chietKhau;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        if (trangThai == null || trangThai.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái khuyến mãi không được để trống!");
        }
        this.trangThai = trangThai.trim();
    }
}