
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
        if (maHoSo == null || !maHoSo.matches("^HS\\d{4}$")) {
            throw new IllegalArgumentException("Mã hồ sơ phải có dạng HSXXXX");
        }
        this.maHoSo = maHoSo;
    }

    public BenhNhan getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(BenhNhan maBenhNhan) {
        if (maBenhNhan == null) {
            throw new IllegalArgumentException("Bệnh nhân không được để trống");
        }
        this.maBenhNhan = maBenhNhan;
    }

    public NhanVien getMaNV() {
        return maNV;
    }

    public void setMaNV(NhanVien maNV) {
        if (maNV == null) {
            throw new IllegalArgumentException("Nhân viên không được để trống");
        }
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
        if (ngayKham == null || ngayKham.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Ngày khám không được để trống và không được lớn hơn ngày hiện tại");
        }
        this.ngayKham = ngayKham;
    }

    public String getTinhTrangRangMieng() {
        return tinhTrangRangMieng;
    }

    public void setTinhTrangRangMieng(String tinhTrangRangMieng) {
        if (tinhTrangRangMieng == null || tinhTrangRangMieng.trim().isEmpty()) {
            throw new IllegalArgumentException("Tình trạng răng miệng không được để trống");
        }
        if (tinhTrangRangMieng.length() > 255) {
            throw new IllegalArgumentException("Tình trạng răng miệng không được vượt quá 255 ký tự");
        }
        this.tinhTrangRangMieng = tinhTrangRangMieng;
    }

    public String getChanDoan() {
        return chanDoan;
    }

    public void setChanDoan(String chanDoan) {
        if (chanDoan == null || chanDoan.trim().isEmpty()) {
            throw new IllegalArgumentException("Chẩn đoán không được để trống");
        }
        if (chanDoan.length() > 255) {
            throw new IllegalArgumentException("Chẩn đoán không được vượt quá 255 ký tự");
        }
        this.chanDoan = chanDoan;
    }

    public String getPhuongPhapDieuTri() {
        return phuongPhapDieuTri;
    }

    public void setPhuongPhapDieuTri(String phuongPhapDieuTri) {
        if (phuongPhapDieuTri == null || phuongPhapDieuTri.trim().isEmpty()) {
            throw new IllegalArgumentException("Phương pháp điều trị không được để trống");
        }
        if (phuongPhapDieuTri.length() > 255) {
            throw new IllegalArgumentException("Phương pháp điều trị không được vượt quá 255 ký tự");
        }
        this.phuongPhapDieuTri = phuongPhapDieuTri;
    }

    public String getTienSuBenh() {
        return tienSuBenh;
    }

    public void setTienSuBenh(String tienSuBenh) {
        if (tienSuBenh != null && tienSuBenh.length() > 255) {
            throw new IllegalArgumentException("Tiền sử bệnh không được vượt quá 255 ký tự");
        }
        this.tienSuBenh = tienSuBenh;
    }

    public String getDiUng() {
        return diUng;
    }

    public void setDiUng(String diUng) {
        if (diUng != null && diUng.length() > 255) {
            throw new IllegalArgumentException("Dị ứng không được vượt quá 255 ký tự");
        }
        this.diUng = diUng;
    }

    public String getBenhLyNen() {
        return benhLyNen;
    }

    public void setBenhLyNen(String benhLyNen) {
        if (benhLyNen != null && benhLyNen.length() > 255) {
            throw new IllegalArgumentException("Bệnh lý nền không được vượt quá 255 ký tự");
        }
        this.benhLyNen = benhLyNen;
    }

    public String getNguoiLienHe() {
        return nguoiLienHe;
    }

    public void setNguoiLienHe(String nguoiLienHe) {
        if (nguoiLienHe != null && nguoiLienHe.length() > 40) {
            throw new IllegalArgumentException("Người liên hệ không được vượt quá 40 ký tự");
        }
        this.nguoiLienHe = nguoiLienHe;
    }

    public String getSoDTNLH() {
        return soDTNLH;
    }

    public void setSoDTNLH(String soDTNLH) {
        if (soDTNLH != null && !soDTNLH.trim().isEmpty()
                && !soDTNLH.matches("^0[3-9]\\d{8}$")) {
            throw new IllegalArgumentException("Số điện thoại người liên hệ không hợp lệ");
        }
        this.soDTNLH = soDTNLH;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        if (ghiChu != null && ghiChu.length() > 255) {
            throw new IllegalArgumentException("Ghi chú không được vượt quá 255 ký tự");
        }
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
