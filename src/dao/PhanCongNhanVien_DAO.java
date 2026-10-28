package dao;

import connectDB.ConnectDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhanCongNhanVien_DAO {

    // Lấy danh sách phân công để đổ lên bảng
    public List<Object[]> layDanhSachPhanCong() {
        List<Object[]> danhSach = new ArrayList<>();
        String sql = "SELECT llv.MaPhanCong, llv.NgayPhanCong, llv.CaLamViec, " +
                "llv.MaNhanVien, nv.HoTen, llv.TrangThai, llv.GhiChu " +
                "FROM LichLamViec llv JOIN NhanVien nv ON llv.MaNhanVien = nv.MaNV";

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs != null && rs.next()) {
                String maPC = rs.getString("MaPhanCong");
                Date ngay = rs.getDate("NgayPhanCong");
                String ca = rs.getString("CaLamViec");
                String hienThiNV = rs.getString("MaNhanVien").trim() + " - " + rs.getString("HoTen").trim();
                String trangThai = rs.getString("TrangThai");
                String ghiChu = rs.getString("GhiChu");

                danhSach.add(new Object[] { maPC, ngay, ca, hienThiNV, trangThai, ghiChu });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    // Thêm phân công
    public boolean themPhanCong(String maPC, java.sql.Date ngay, String caLam, String maNV, String trangThai, String ghiChu) {
        String sql = "INSERT INTO LichLamViec (MaPhanCong, NgayPhanCong, CaLamViec, MaNhanVien, TrangThai, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maPC);
            pstmt.setDate(2, ngay);
            pstmt.setString(3, caLam);
            pstmt.setString(4, maNV);
            pstmt.setString(5, trangThai);
            pstmt.setString(6, ghiChu.isEmpty() ? null : ghiChu);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}