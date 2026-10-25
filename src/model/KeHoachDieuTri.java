package model;

import java.time.LocalDate;

public class KeHoachDieuTri {
    private String maKeHoach;
    private BenhNhan maBenhNhan;
    private String chanDoan;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private double chiPhiDuKien;
    private String trangThai;

    public KeHoachDieuTri() {
    }

    public KeHoachDieuTri(String maKeHoach, BenhNhan maBenhNhan, String chanDoan, 
                          LocalDate ngayBatDau, LocalDate ngayKetThuc, double chiPhiDuKien, String trangThai) {
        setMaKeHoach(maKeHoach);
        setMaBenhNhan(maBenhNhan);
        setChanDoan(chanDoan);
        setNgayBatDau(ngayBatDau);
        setNgayKetThuc(ngayKetThuc);
        setChiPhiDuKien(chiPhiDuKien);
        setTrangThai(trangThai);
    }

    public String getMaKeHoach() {
        return maKeHoach;
    }

    public void setMaKeHoach(String maKeHoach) {
        if (maKeHoach == null || maKeHoach.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã kế hoạch không được để trống!");
        }
        this.maKeHoach = maKeHoach.trim();
    }

    public BenhNhan getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(BenhNhan maBenhNhan) {
        if (maBenhNhan == null || maBenhNhan.getMaBenhNhan() == null || maBenhNhan.getMaBenhNhan().trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn thông tin bệnh nhân!");
        }
        this.maBenhNhan = maBenhNhan;
    }

    public String getChanDoan() {
        return chanDoan;
    }

    public void setChanDoan(String chanDoan) {
        if (chanDoan == null || chanDoan.trim().isEmpty()) {
            throw new IllegalArgumentException("Chẩn đoán bệnh không được để trống!");
        }
        this.chanDoan = chanDoan.trim();
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        if (ngayBatDau == null) {
            throw new IllegalArgumentException("Ngày bắt đầu không được để trống!");
        }
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        if (ngayKetThuc == null) {
            throw new IllegalArgumentException("Ngày kết thúc không được để trống!");
        }
        if (this.ngayBatDau != null && ngayKetThuc.isBefore(this.ngayBatDau)) {
            throw new IllegalArgumentException("Ngày kết thúc không được trước ngày bắt đầu!");
        }
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getChiPhiDuKien() {
        return chiPhiDuKien;
    }

    public void setChiPhiDuKien(double chiPhiDuKien) {
        if (chiPhiDuKien < 0) {
            throw new IllegalArgumentException("Chi phí dự kiến không được âm!");
        }
        this.chiPhiDuKien = chiPhiDuKien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        if (trangThai == null || trangThai.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái kế hoạch không được để trống!");
        }
        this.trangThai = trangThai.trim();
    }
}