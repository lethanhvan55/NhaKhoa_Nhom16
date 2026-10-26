package model;

import java.time.LocalDate;

public class KhuyenMai {
    private String maKM;
    private String maThayThe;
    private String tenKM;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private double giaTriToiThieu;
    private double chietKhau;
    private int soLuong;
    private String trangThai;

    public KhuyenMai() {
    }

    public KhuyenMai(String maKM, String maThayThe, String tenKM, LocalDate ngayBatDau, LocalDate ngayKetThuc, double giaTriToiThieu, double chietKhau, int soLuong, String trangThai) {
        this.maKM = maKM;
        this.maThayThe = maThayThe;
        this.tenKM = tenKM;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.giaTriToiThieu = giaTriToiThieu;
        this.chietKhau = chietKhau;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        if (maKM == null || !maKM.matches("^KM\\d{4}$")) {
            throw new IllegalArgumentException("Mã khuyến mãi sai định dạng");
        }
        this.maKM = maKM;
    }

    public String getMaThayThe() {
        return maThayThe;
    }

    public void setMaThayThe(String maThayThe) {
        if (maThayThe == null || maThayThe.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã thay thế không được trống");
        }
        this.maThayThe = maThayThe;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        if (tenKM == null || tenKM.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khuyến mãi không được trống");
        }
        this.tenKM = tenKM;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        if (ngayBatDau == null) {
            throw new IllegalArgumentException("Ngày bắt đầu không được rỗng");
        }
        if (ngayKetThuc != null && ngayKetThuc.isBefore(ngayBatDau)) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau hoặc bằng ngày bắt đầu");
        }
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        if (ngayKetThuc == null) {
            throw new IllegalArgumentException("Ngày kết thúc không được rỗng");
        }
        if (ngayBatDau != null && ngayKetThuc.isBefore(ngayBatDau)) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau hoặc bằng ngày bắt đầu");
        }
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getGiaTriToiThieu() {
        return giaTriToiThieu;
    }

    public void setGiaTriToiThieu(double giaTriToiThieu) {
        if (giaTriToiThieu < 0) {
            throw new IllegalArgumentException("Giá trị tối thiểu không được âm");
        }
        this.giaTriToiThieu = giaTriToiThieu;
    }

    public double getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(double chietKhau) {
        if (chietKhau < 0 || chietKhau > 100) {
            throw new IllegalArgumentException("Chiết khấu phải từ 0 đến 100%");
        }
        this.chietKhau = chietKhau;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không được âm");
        }
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "maKM='" + maKM + '\'' +
                ", maThayThe='" + maThayThe + '\'' +
                ", tenKM='" + tenKM + '\'' +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", giaTriToiThieu=" + giaTriToiThieu +
                ", chietKhau=" + chietKhau +
                ", soLuong=" + soLuong +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
