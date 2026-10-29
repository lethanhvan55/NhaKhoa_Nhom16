package utils;

public final class PhienDangNhap {

    private static String maNV;

    private PhienDangNhap() {
    }

    public static void setMaNV(String maNhanVien) {
        maNV = maNhanVien;
    }

    public static String getMaNV() {
        return maNV;
    }
}