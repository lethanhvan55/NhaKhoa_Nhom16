package dao;

import connectDB.ConnectDB;
import model.ChiTietHoaDon;
import model.DichVu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDon_DAO {

    public List<ChiTietHoaDon> getChiTietHoaDon(String maHD) throws SQLException {
        List<ChiTietHoaDon> danhSach = new ArrayList<>();

        String sql = "SELECT ct.MaHD, ct.MaDV, dv.TenDV, ct.SoLuong, ct.DonGia " +
                "FROM ChiTietHoaDon ct " +
                "JOIN DichVu dv ON ct.MaDV = dv.MaDV " +
                "WHERE ct.MaHD = ? ORDER BY dv.TenDV";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHD);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DichVu dv = new DichVu();
                    dv.setMaDV(rs.getString("MaDV").trim());
                    dv.setTenDV(rs.getString("TenDV"));

                    int soLuong = rs.getInt("SoLuong");
                    double donGia = rs.getDouble("DonGia");

                    ChiTietHoaDon ct = new ChiTietHoaDon(
                            null,
                            dv,
                            soLuong,
                            donGia
                    );

                    danhSach.add(ct);
                }
            }
        }

        return danhSach;
    }

    public double tinhTongTien(String maHD) throws SQLException {
        String sql = "SELECT COALESCE(SUM(SoLuong * DonGia), 0) AS TongTien " +
                "FROM ChiTietHoaDon WHERE MaHD = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHD);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("TongTien");
                }
            }
        }

        return 0;
    }
}