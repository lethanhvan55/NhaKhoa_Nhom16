
package model;

import java.time.LocalDate;

public class HoSoBenhNhan {
    private String maHoSo;
    private BenhNhan maBenhNhan;
    private NhanVien maNV;
    private KeHoachDieuTri maKeHoach;
    private LocalDate ngayKham;
    private String tinhTrangRangMieng;
    private String chanDoan;
    private String phuongPhapDieuTri;
    private String tienSuBenh;
    private String diUng;
    private String benhLyNen;
    private String nguoiLienHe;
    private String soDTNLH;
    private String ghiChu;

    public HoSoBenhNhan() {
    }

    public HoSoBenhNhan(String maHoSo, BenhNhan maBenhNhan, NhanVien maNV, KeHoachDieuTri maKeHoach, LocalDate ngayKham,
                        String tinhTrangRangMieng, String chanDoan, String phuongPhapDieuTri, String tienSuBenh, String diUng,
                        String benhLyNen, String nguoiLienHe, String soDTNLH, String ghiChu) {
        this.maHoSo = maHoSo;
        this.maBenhNhan = maBenhNhan;
        this.maNV = maNV;
        this.maKeHoach = maKeHoach;
        this.ngayKham = ngayKham;
        this.tinhTrangRangMieng = tinhTrangRangMieng;
        this.chanDoan = chanDoan;
        this.phuongPhapDieuTri = phuongPhapDieuTri;
        this.tienSuBenh = tienSuBenh;
        this.diUng = diUng;
        this.benhLyNen = benhLyNen;
        this.nguoiLienHe = nguoiLienHe;
        this.soDTNLH = soDTNLH;
        this.ghiChu = ghiChu;
    }

    public String getMaHoSo() {
        return maHoSo;
    }

    public void setMaHoSo(String maHoSo) {
        this.maHoSo = maHoSo;
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

    public KeHoachDieuTri getMaKeHoach() {
        return maKeHoach;
    }

    public void setMaKeHoach(KeHoachDieuTri maKeHoach) {
        this.maKeHoach = maKeHoach;
    }

    public LocalDate getNgayKham() {
        return ngayKham;
    }

    public void setNgayKham(LocalDate ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getTinhTrangRangMieng() {
        return tinhTrangRangMieng;
    }

    public void setTinhTrangRangMieng(String tinhTrangRangMieng) {
        this.tinhTrangRangMieng = tinhTrangRangMieng;
    }

    public String getChanDoan() {
        return chanDoan;
    }

    public void setChanDoan(String chanDoan) {
        this.chanDoan = chanDoan;
    }

    public String getPhuongPhapDieuTri() {
        return phuongPhapDieuTri;
    }

    public void setPhuongPhapDieuTri(String phuongPhapDieuTri) {
        this.phuongPhapDieuTri = phuongPhapDieuTri;
    }

    public String getTienSuBenh() {
        return tienSuBenh;
    }

    public void setTienSuBenh(String tienSuBenh) {
        this.tienSuBenh = tienSuBenh;
    }

    public String getDiUng() {
        return diUng;
    }

    public void setDiUng(String diUng) {
        this.diUng = diUng;
    }

    public String getBenhLyNen() {
        return benhLyNen;
    }

    public void setBenhLyNen(String benhLyNen) {
        this.benhLyNen = benhLyNen;
    }

    public String getNguoiLienHe() {
        return nguoiLienHe;
    }

    public void setNguoiLienHe(String nguoiLienHe) {
        this.nguoiLienHe = nguoiLienHe;
    }

    public String getSoDTNLH() {
        return soDTNLH;
    }

    public void setSoDTNLH(String soDTNLH) {
        this.soDTNLH = soDTNLH;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "HoSoBenhNhan [maHoSo=" + maHoSo + ", maBenhNhan=" + maBenhNhan + ", maNV=" + maNV + ", maKeHoach="
                + maKeHoach + ", ngayKham=" + ngayKham + ", tinhTrangRangMieng=" + tinhTrangRangMieng + ", chanDoan="
                + chanDoan + ", phuongPhapDieuTri=" + phuongPhapDieuTri + ", tienSuBenh=" + tienSuBenh + ", diUng="
                + diUng + ", benhLyNen=" + benhLyNen + ", nguoiLienHe=" + nguoiLienHe + ", soDTNLH=" + soDTNLH
                + ", ghiChu=" + ghiChu + "]";
    }

}
