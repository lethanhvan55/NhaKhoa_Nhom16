package model;

import model.DichVu;
import model.VatTu;

public class ChiTietVatTu_DichVu {
    private VatTu vatTu;
    private DichVu dichVu;
    private int soLuongSuDung;

    public ChiTietVatTu_DichVu() {
    }

    public ChiTietVatTu_DichVu(VatTu vatTu, DichVu dichVu, int soLuongSuDung) {
        this.vatTu = vatTu;
        this.dichVu = dichVu;
        this.soLuongSuDung = soLuongSuDung;
    }

    public VatTu getVatTu() {
        return vatTu;
    }

    public void setVatTu(VatTu vatTu) {
        this.vatTu = vatTu;
    }

    public DichVu getDichVu() {
        return dichVu;
    }

    public void setDichVu(DichVu dichVu) {
        this.dichVu = dichVu;
    }

    public int getSoLuongSuDung() {
        return soLuongSuDung;
    }

    public void setSoLuongSuDung(int soLuongSuDung) {
        if (soLuongSuDung <= 0) {
            throw new IllegalArgumentException("Số lượng sử dụng phải lớn hơn 0");
        }
        this.soLuongSuDung = soLuongSuDung;
    }
}
