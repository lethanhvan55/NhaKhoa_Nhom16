
package dao;

import connectDB.ConnectDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LichHenDAO {

    // CREATE: Thêm lịch hẹn
    public boolean themLichHen(
            String maLichHen,
            String maBenhNhan,
            String maDichVu,
            String maPhong,
            String maNV,
            LocalDate ngayHen,
            LocalDateTime gioHen,
            String trangThai,
            String ghiChu) throws SQLException {

        String sql = """
            INSERT INTO LichHen
            (MaLichHen, MaBenhNhan, MaDichVu, MaPhong, MaNV,
             NgayHen, GioHen, TrangThai, GhiChu)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLichHen);
            ps.setString(2, maBenhNhan);
            ps.setString(3, maDichVu);
            ps.setString(4, maPhong);
            ps.setString(5, maNV);
            ps.setDate(6, Date.valueOf(ngayHen));
            ps.setTimestamp(7, Timestamp.valueOf(gioHen));
            ps.setString(8, trangThai);

            if (ghiChu == null || ghiChu.trim().isEmpty()) {
                ps.setNull(9, Types.NVARCHAR);
            } else {
                ps.setString(9, ghiChu.trim());
            }

            return ps.executeUpdate() > 0;
        }
    }

    // READ: Lấy danh sách lịch hẹn
    public List<Map<String, Object>> getAllLichHen()
            throws SQLException {

        List<Map<String, Object>> danhSach = new ArrayList<>();

        String sql = """
            SELECT MaLichHen, MaBenhNhan, MaDichVu, MaPhong, MaNV,
                   NgayHen, GioHen, TrangThai, GhiChu
            FROM LichHen
            ORDER BY NgayHen DESC, GioHen DESC
            """;

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();

                row.put("MaLichHen", rs.getString("MaLichHen").trim());
                row.put("MaBenhNhan", rs.getString("MaBenhNhan").trim());
                row.put("MaDichVu", rs.getString("MaDichVu").trim());
                row.put("MaPhong", rs.getString("MaPhong").trim());
                row.put("MaNV", rs.getString("MaNV").trim());
                row.put("NgayHen", rs.getDate("NgayHen").toLocalDate());
                row.put("GioHen",
                        rs.getTimestamp("GioHen").toLocalDateTime());
                row.put("TrangThai", rs.getString("TrangThai"));
                row.put("GhiChu", rs.getString("GhiChu"));

                danhSach.add(row);
            }
        }

        return danhSach;
    }

    // READ: Tìm lịch hẹn theo mã
    public Map<String, Object> getLichHenTheoMa(String maLichHen)
            throws SQLException {

        String sql = """
            SELECT MaLichHen, MaBenhNhan, MaDichVu, MaPhong, MaNV,
                   NgayHen, GioHen, TrangThai, GhiChu
            FROM LichHen
            WHERE MaLichHen = ?
            """;

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLichHen);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Map<String, Object> row = new LinkedHashMap<>();
                row.put("MaLichHen", rs.getString("MaLichHen").trim());
                row.put("MaBenhNhan", rs.getString("MaBenhNhan").trim());
                row.put("MaDichVu", rs.getString("MaDichVu").trim());
                row.put("MaPhong", rs.getString("MaPhong").trim());
                row.put("MaNV", rs.getString("MaNV").trim());
                row.put("NgayHen", rs.getDate("NgayHen").toLocalDate());
                row.put("GioHen",
                        rs.getTimestamp("GioHen").toLocalDateTime());
                row.put("TrangThai", rs.getString("TrangThai"));
                row.put("GhiChu", rs.getString("GhiChu"));

                return row;
            }
        }
    }

    // UPDATE: Cập nhật lịch hẹn
    public boolean capNhatLichHen(
            String maLichHen,
            String maBenhNhan,
            String maDichVu,
            String maPhong,
            String maNV,
            LocalDate ngayHen,
            LocalDateTime gioHen,
            String trangThai,
            String ghiChu) throws SQLException {

        String sql = """
            UPDATE LichHen
            SET MaBenhNhan = ?,
                MaDichVu = ?,
                MaPhong = ?,
                MaNV = ?,
                NgayHen = ?,
                GioHen = ?,
                TrangThai = ?,
                GhiChu = ?
            WHERE MaLichHen = ?
            """;

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maBenhNhan);
            ps.setString(2, maDichVu);
            ps.setString(3, maPhong);
            ps.setString(4, maNV);
            ps.setDate(5, Date.valueOf(ngayHen));
            ps.setTimestamp(6, Timestamp.valueOf(gioHen));
            ps.setString(7, trangThai);

            if (ghiChu == null || ghiChu.trim().isEmpty()) {
                ps.setNull(8, Types.NVARCHAR);
            } else {
                ps.setString(8, ghiChu.trim());
            }

            ps.setString(9, maLichHen);

            return ps.executeUpdate() > 0;
        }
    }

    // DELETE: Xóa lịch hẹn theo mã
    public boolean xoaLichHen(String maLichHen) throws SQLException {

        String sql = "DELETE FROM LichHen WHERE MaLichHen = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLichHen);

            return ps.executeUpdate() > 0;
        }
    }

    public List<Object[]> getDanhSachChoCheckIn() throws SQLException {
        List<Object[]> danhSach = new ArrayList<>();

        // Bỏ điều kiện WHERE TrangThai = N'Đã đặt' để hiện toàn bộ
        String sql = "SELECT MaLichHen, MaBenhNhan, MaDichVu, MaPhong, "
                + "MaNV, NgayHen, GioHen, TrangThai "
                + "FROM LichHen "
                + "ORDER BY NgayHen, GioHen";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                java.sql.Timestamp gioHen = rs.getTimestamp("GioHen");

                danhSach.add(new Object[] {
                        rs.getString("MaLichHen").trim(),
                        rs.getString("MaBenhNhan").trim(),
                        rs.getString("MaDichVu").trim(),
                        rs.getString("MaPhong").trim(),
                        rs.getString("MaNV").trim(),
                        rs.getDate("NgayHen"),
                        gioHen == null ? null : gioHen.toLocalDateTime(),
                        rs.getString("TrangThai")
                });
            }
        }

        return danhSach;
    }

    public Object[] timLichHenCheckIn(String maLichHen)
            throws SQLException {

        String sql = "SELECT MaLichHen, MaBenhNhan, MaDichVu, MaPhong, "
                + "MaNV, NgayHen, GioHen, TrangThai "
                + "FROM LichHen WHERE MaLichHen = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLichHen.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.sql.Timestamp gioHen = rs.getTimestamp("GioHen");

                    return new Object[] {
                            rs.getString("MaLichHen").trim(),
                            rs.getString("MaBenhNhan").trim(),
                            rs.getString("MaDichVu").trim(),
                            rs.getString("MaPhong").trim(),
                            rs.getString("MaNV").trim(),
                            rs.getDate("NgayHen"),
                            gioHen == null ? null : gioHen.toLocalDateTime(),
                            rs.getString("TrangThai")
                    };
                }
            }
        }

        return null;
    }

    public boolean checkIn(String maLichHen) throws SQLException {
        String sql = "UPDATE LichHen "
                + "SET TrangThai = N'Đang chờ khám' "
                + "WHERE MaLichHen = ? AND TrangThai = N'Đã đặt'";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLichHen.trim());
            return ps.executeUpdate() > 0;
        }
    }

}
