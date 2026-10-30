
package dao;

import connectDB.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ThongKeKhachHang_Dao {

    /**
     * Thống kê số lần khám (số hóa đơn đã thanh toán)
     * và tổng chi tiêu của từng bệnh nhân.
     */
    public List<Map<String, Object>> getThongKeKhachHang(
            LocalDate tuNgay, LocalDate denNgay) throws SQLException {

        List<Map<String, Object>> danhSach = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("bn.MaBenhNhan AS MaKhachHang, ");
        sql.append("bn.HoTen AS TenKhachHang, ");
        sql.append("COUNT(DISTINCT hd.MaHD) AS SoLanKham, ");
        sql.append(
                "COALESCE(SUM(ct.SoLuong * ct.DonGia * " +
                        "(1 - COALESCE(hd.ChietKhau, 0) / 100.0)), 0) AS TongChiTieu "
        );

        sql.append("FROM BenhNhan bn ");
        sql.append("INNER JOIN HoaDon hd ");
        sql.append("ON bn.MaBenhNhan = hd.MaBenhNhan ");
        sql.append("LEFT JOIN ChiTietHoaDon ct ");
        sql.append("ON hd.MaHD = ct.MaHD ");

        sql.append("WHERE hd.TrangThai = N'Đã thanh toán' ");

        if (tuNgay != null) {
            sql.append("AND hd.NgayLap >= ? ");
        }

        if (denNgay != null) {
            sql.append("AND hd.NgayLap < DATEADD(DAY, 1, ?) ");
        }

        sql.append("GROUP BY bn.MaBenhNhan, bn.HoTen ");
        sql.append("ORDER BY TongChiTieu DESC ");

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql.toString())) {

            int index = 1;

            if (tuNgay != null) {
                pst.setDate(
                        index++,
                        java.sql.Date.valueOf(tuNgay)
                );
            }

            if (denNgay != null) {
                pst.setDate(
                        index,
                        java.sql.Date.valueOf(denNgay)
                );
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();

                    row.put(
                            "MaKhachHang",
                            rs.getString("MaKhachHang").trim()
                    );

                    row.put(
                            "TenKhachHang",
                            rs.getString("TenKhachHang")
                    );

                    row.put(
                            "SoLanKham",
                            rs.getInt("SoLanKham")
                    );

                    row.put(
                            "TongChiTieu",
                            rs.getDouble("TongChiTieu")
                    );

                    danhSach.add(row);
                }
            }
        }

        return danhSach;
    }
}
