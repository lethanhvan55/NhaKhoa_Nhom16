package dao;

import connectDB.ConnectDB;
import model.NhanVien;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NhanVien_DAO {

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();

        String sql = "SELECT MaNV, HoTen, SoDT, NgayVaoLam, GioiTinh, "
                + "ChucVu, TrangThai, Email, DiaChi "
                + "FROM NhanVien ORDER BY MaNV";

        Connection con = ConnectDB.getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(docNhanVien(rs));
            }

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] getAll lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<NhanVien> getNhanVienTheoTen(String keyword) {
        List<NhanVien> list = new ArrayList<>();

        String sql = "SELECT MaNV, HoTen, SoDT, NgayVaoLam, GioiTinh, "
                + "ChucVu, TrangThai, Email, DiaChi "
                + "FROM NhanVien "
                + "WHERE HoTen LIKE ? OR MaNV LIKE ? "
                + "ORDER BY MaNV";

        Connection con = ConnectDB.getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String kw = "%" + keyword.trim() + "%";

            ps.setString(1, kw);
            ps.setString(2, kw);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(docNhanVien(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] Tìm kiếm lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public boolean insert(NhanVien nv) {
        String sql = "INSERT INTO NhanVien "
                + "(HoTen, SoDT, NgayVaoLam, GioiTinh, "
                + "ChucVu, TrangThai, Email, DiaChi) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = ConnectDB.getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getSoDT());
            ps.setDate(3, Date.valueOf(nv.getNgayVaoLam()));
            ps.setBoolean(4, nv.getGioiTinh());
            ps.setString(5, nv.getChucVu());
            ps.setString(6, nv.getTrangThai());
            ps.setString(7, nv.getEmail());
            ps.setString(8, nv.getDiaChi());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] insert lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhanVien nv) {
        String sql = "UPDATE NhanVien SET "
                + "HoTen = ?, SoDT = ?, NgayVaoLam = ?, GioiTinh = ?, "
                + "ChucVu = ?, TrangThai = ?, Email = ?, DiaChi = ? "
                + "WHERE MaNV = ?";

        Connection con = ConnectDB.getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getSoDT());

            LocalDate ngayVaoLam = nv.getNgayVaoLam() != null
                    ? nv.getNgayVaoLam()
                    : LocalDate.now();

            ps.setDate(3, Date.valueOf(ngayVaoLam));
            ps.setBoolean(4, nv.getGioiTinh());
            ps.setString(5, nv.getChucVu());
            ps.setString(6, nv.getTrangThai());
            ps.setString(7, nv.getEmail());
            ps.setString(8, nv.getDiaChi());
            ps.setString(9, nv.getMaNV());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] update lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV = ?";

        Connection con = ConnectDB.getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] delete lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String sinhMaNVMoi() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaNV, 3, 4) AS INT)) AS SoLonNhat "
                + "FROM NhanVien "
                + "WHERE MaNV LIKE 'NV[0-9][0-9][0-9][0-9]'";

        Connection con = ConnectDB.getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            int soLonNhat = rs.getInt("SoLonNhat");

            if (rs.wasNull()) {
                soLonNhat = 0;
            }

            if (soLonNhat >= 9999) {
                return "";
            }

            return String.format("NV%04d", soLonNhat + 1);

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] sinhMaNVMoi lỗi: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    private NhanVien docNhanVien(ResultSet rs) throws SQLException {
        Date ngaySQL = rs.getDate("NgayVaoLam");

        return new NhanVien(
                rs.getString("MaNV"),
                rs.getString("HoTen"),
                rs.getString("SoDT"),
                ngaySQL != null ? ngaySQL.toLocalDate() : null,
                rs.getBoolean("GioiTinh"),
                rs.getString("ChucVu"),
                rs.getString("TrangThai"),
                rs.getString("Email"),
                rs.getString("DiaChi")
        );
    }
}
