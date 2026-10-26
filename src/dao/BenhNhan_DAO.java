package dao;

import connectDB.ConnectDB;
import model.BenhNhan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BenhNhan_DAO {

    // Lấy danh sách bệnh nhân
    public List<BenhNhan> getAll() {
        List<BenhNhan> ds = new ArrayList<>();

        String sql = "SELECT * FROM BenhNhan";

        try (
                Connection con = ConnectDB.getInstance().getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                BenhNhan bn = new BenhNhan();

                bn.setMaBenhNhan(rs.getString("MaBenhNhan"));
                bn.setHoTen(rs.getString("HoTen"));
                bn.setNgaySinh(
                        rs.getDate("NgaySinh") != null
                                ? rs.getDate("NgaySinh").toLocalDate()
                                : null
                );
                bn.setGioiTinh(rs.getBoolean("GioiTinh"));
                bn.setSoDT(rs.getString("SoDT"));
                bn.setEmail(rs.getString("Email"));
                bn.setDiaChi(rs.getString("DiaChi"));

                ds.add(bn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }

    // Thêm bệnh nhân mới
    public boolean insert(BenhNhan bn) {
        if (bn == null) {
            System.err.println("Bệnh nhân không hợp lệ!");
            return false;
        }

        if (bn.getMaBenhNhan() == null
                || bn.getMaBenhNhan().trim().isEmpty()) {
            System.err.println("Mã bệnh nhân không được để trống!");
            return false;
        }

        if (bn.getHoTen() == null
                || bn.getHoTen().trim().isEmpty()) {
            System.err.println("Họ tên không được để trống!");
            return false;
        }

        if (bn.getNgaySinh() == null) {
            System.err.println("Ngày sinh không được để trống!");
            return false;
        }

        if (bn.getNgaySinh().isAfter(java.time.LocalDate.now())) {
            System.err.println("Ngày sinh không được ở tương lai!");
            return false;
        }

        if (bn.getSoDT() == null
                || !bn.getSoDT().matches("^0[3-9]\\d{8}$")) {
            System.err.println("Số điện thoại không hợp lệ!");
            return false;
        }

        String sql =
                "INSERT INTO BenhNhan "
                        + "(MaBenhNhan, HoTen, NgaySinh, GioiTinh, SoDT, Email, DiaChi) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = ConnectDB.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, bn.getMaBenhNhan().trim());
            ps.setString(2, bn.getHoTen().trim());
            ps.setDate(3, java.sql.Date.valueOf(bn.getNgaySinh()));
            ps.setBoolean(4, bn.isGioiTinh());
            ps.setString(5, bn.getSoDT().trim());

            if (bn.getEmail() == null
                    || bn.getEmail().trim().isEmpty()) {
                ps.setNull(6, java.sql.Types.VARCHAR);
            } else {
                ps.setString(6, bn.getEmail().trim());
            }

            if (bn.getDiaChi() == null
                    || bn.getDiaChi().trim().isEmpty()) {
                ps.setNull(7, java.sql.Types.NVARCHAR);
            } else {
                ps.setString(7, bn.getDiaChi().trim());
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi thêm bệnh nhân: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public String sinhMaBenhNhanMoi() {
        String sql =
                "SELECT ISNULL(MAX(CAST(RIGHT(MaBenhNhan, 4) AS INT)), 0) + 1 "
                        + "FROM BenhNhan "
                        + "WHERE MaBenhNhan LIKE 'BN[0-9][0-9][0-9][0-9]'";

        try (
                Connection con = ConnectDB.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                int so = rs.getInt(1);

                if (so > 9999) {
                    System.err.println("Đã vượt quá số lượng mã bệnh nhân!");
                    return null;
                }

                return String.format("BN%04d", so);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi sinh mã bệnh nhân: "
                    + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
