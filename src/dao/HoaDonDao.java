package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import connectDB.ConnectDB;
public class HoaDonDao {
    public List<Map<String, Object>> getDanhSachLichHenDaCheckIn() {
        List<Map<String, Object>> danhSach = new ArrayList<>();
        String sql = "SELECT lh.MaLichHen, lh.MaBenhNhan, bn.HoTen, bn.SoDT, lh.MaDichVu, dv.TenDV, dv.DonGia, lh.MaPhong, lh.MaNV, lh.NgayHen, lh.GioHen, lh.TrangThai " +
                "FROM LichHen lh " +
                "JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan " +
                "JOIN DichVu dv ON lh.MaDichVu = dv.MaDV " +
                "WHERE lh.TrangThai = N'Đang chờ khám' " +
                "AND NOT EXISTS (SELECT 1 FROM HoaDon hd WHERE hd.MaLichHen = lh.MaLichHen) " +
                "ORDER BY lh.NgayHen, lh.GioHen";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("MaLichHen", rs.getString("MaLichHen").trim());
                row.put("MaBenhNhan", rs.getString("MaBenhNhan").trim());
                row.put("HoTen", rs.getString("HoTen"));
                row.put("SoDT", rs.getString("SoDT"));
                row.put("MaDichVu", rs.getString("MaDichVu").trim());
                row.put("TenDV", rs.getString("TenDV"));
                row.put("DonGia", rs.getBigDecimal("DonGia"));
                row.put("MaPhong", rs.getString("MaPhong").trim());
                row.put("MaNV", rs.getString("MaNV").trim());
                row.put("NgayHen", rs.getDate("NgayHen"));
                row.put("GioHen", rs.getTimestamp("GioHen"));
                row.put("TrangThai", rs.getString("TrangThai"));
                danhSach.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
    public List<Map<String, Object>> getDanhSachDichVuTheoLichHen(String maLichHen) {
        List<Map<String, Object>> danhSach = new ArrayList<>();

        String sql = "SELECT dv.MaDV, dv.TenDV, dv.DonGia, dv.TrangThai " +
                "FROM LichHen lh " +
                "JOIN DichVu dv ON lh.MaDichVu = dv.MaDV " +
                "WHERE lh.MaLichHen = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLichHen);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();

                    row.put("MaDV", rs.getString("MaDV"));
                    row.put("TenDV", rs.getString("TenDV"));
                    row.put("DonGia", rs.getDouble("DonGia"));
                    row.put("TrangThai", rs.getString("TrangThai"));

                    danhSach.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi lấy dịch vụ theo lịch hẹn: " + e.getMessage(), e);
        }

        return danhSach;
    }
}
