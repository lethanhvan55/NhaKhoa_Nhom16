
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

    public KeHoachDieuTri(String maKeHoach, BenhNhan maBenhNhan,String chanDoan, LocalDate ngayBatDau,LocalDate ngayKetThuc, double chiPhiDuKien,String trangThai) {
        this.maKeHoach = maKeHoach;
        this.maBenhNhan = maBenhNhan;
        this.chanDoan = chanDoan;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.chiPhiDuKien = chiPhiDuKien;
        this.trangThai = trangThai;
    }
    public String getMaKeHoach() {
        return maKeHoach;
    }

    public void setMaKeHoach(String maKeHoach) {
        this.maKeHoach = maKeHoach;
    }

    public BenhNhan getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(BenhNhan maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getChanDoan() {
        return chanDoan;
    }

    public void setChanDoan(String chanDoan) {
        this.chanDoan = chanDoan;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getChiPhiDuKien() {
        return chiPhiDuKien;
    }

    public void setChiPhiDuKien(double chiPhiDuKien) {
        this.chiPhiDuKien = chiPhiDuKien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
