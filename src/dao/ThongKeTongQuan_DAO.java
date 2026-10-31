package dao;

import connectDB.ConnectDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThongKeTongQuan_DAO {

    private int demSoLuong(String sql) throws SQLException {
        try (PreparedStatement ps = ConnectDB.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    public int getTongSoBenhNhan() throws SQLException {
        String sql = "SELECT COUNT(*) FROM BenhNhan";
        return demSoLuong(sql);
    }

    public int getLichHenHomNay() throws SQLException {
        String sql = """
                SELECT COUNT(*)
                FROM LichHen
                WHERE NgayHen = CAST(GETDATE() AS DATE)
                  AND TrangThai NOT IN (N'Hủy', N'Không đến')
                """;

        return demSoLuong(sql);
    }

    public int getTongKeHoachDieuTri() throws SQLException {
        String sql = "SELECT COUNT(*) FROM KeHoachDieuTri "
                +"WHERE TrangThai = N'Đang thực hiện'";
        return demSoLuong(sql);
    }

    public int getLichHenChoKham() throws SQLException {
        String sql = """
                SELECT COUNT(*)
                FROM LichHen
                WHERE TrangThai = N'Đang chờ khám'
                """;

        return demSoLuong(sql);
    }
}