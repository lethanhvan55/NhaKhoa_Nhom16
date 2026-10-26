package dao;

import connectDB.ConnectDB;
import model.NhanVien;

import java.sql.*;
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

        if (con == null) {
            System.err.println("Chưa kết nối database!");
            return list;
        }

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

        if (con == null) {
            System.err.println("[NhanVien_DAO] Chưa kết nối database!");
            return list;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String kw = "%" + (keyword == null ? "" : keyword.trim()) + "%";

            ps.setString(1, kw);
            ps.setString(2, kw);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(docNhanVien(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] search lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public boolean insert(NhanVien nv) {
        if (nv == null || nv.getMaNV() == null
                || nv.getMaNV().trim().isEmpty()) {
            System.err.println("[NhanVien_DAO] Mã nhân viên không hợp lệ!");
            return false;
        }

        String sql = "INSERT INTO NhanVien "
                + "(MaNV, HoTen, SoDT, NgayVaoLam, GioiTinh, "
                + "ChucVu, TrangThai, Email, DiaChi) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = ConnectDB.getConnection();

        if (con == null) {
            System.err.println("[NhanVien_DAO] Chưa kết nối database!");
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNV().trim());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getSoDT());

            if (nv.getNgayVaoLam() != null) {
                ps.setDate(4, java.sql.Date.valueOf(nv.getNgayVaoLam()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            if (nv.getGioiTinh() != null) {
                ps.setBoolean(5, nv.getGioiTinh());
            } else {
                ps.setNull(5, Types.BIT);
            }

            ps.setString(6, nv.getChucVu());
            ps.setString(7, nv.getTrangThai());
            ps.setString(8, nv.getEmail());
            ps.setString(9, nv.getDiaChi());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] insert lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhanVien nv) {
        if (nv == null || nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            System.err.println("[NhanVien_DAO] Mã nhân viên không hợp lệ!");
            return false;
        }

        String sql = "UPDATE NhanVien SET " + "HoTen = ?, SoDT = ?, NgayVaoLam = ?, GioiTinh = ?, " + "ChucVu = ?, TrangThai = ?, Email = ?, DiaChi = ? " + "WHERE MaNV = ?";

        Connection con = ConnectDB.getConnection();

        if (con == null) {System.err.println("[NhanVien_DAO] Chưa kết nối database!");return false;}

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getSoDT());

            if (nv.getNgayVaoLam() != null) { ps.setDate(3, Date.valueOf(nv.getNgayVaoLam()));}
            else { ps.setDate(3, Date.valueOf(LocalDate.now()));}

            if (nv.getGioiTinh() != null) { ps.setBoolean(4, nv.getGioiTinh());}
            else {ps.setNull(4, Types.BIT);}

            ps.setString(5, nv.getChucVu());
            ps.setString(6, nv.getTrangThai());
            ps.setString(7, nv.getEmail());
            ps.setString(8, nv.getDiaChi());
            ps.setString(9, nv.getMaNV());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) { System.err.println("[NhanVien_DAO] update lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {System.err.println("[NhanVien_DAO] Mã nhân viên cần xóa không hợp lệ!");
            return false;
        }

        String sql = "DELETE FROM NhanVien WHERE MaNV = ?";

        Connection con = ConnectDB.getConnection();

        if (con == null) { System.err.println("[NhanVien_DAO] Chưa kết nối database!");
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] delete lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ================= SINH MÃ NHÂN VIÊN =================
    public String sinhMaNVMoi() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaNV, 3, 4) AS INT)) AS SoLonNhat "
                + "FROM NhanVien "
                + "WHERE MaNV LIKE 'NV[0-9][0-9][0-9][0-9]'";

        Connection con = ConnectDB.getConnection();

        if (con == null) {
            System.err.println("[NhanVien_DAO] Chưa kết nối database!");
            return "";
        }

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int soLonNhat = rs.getInt("SoLonNhat");

                if (rs.wasNull()) {
                    soLonNhat = 0;
                }

                if (soLonNhat >= 9999) {
                    System.err.println("[NhanVien_DAO] Đã đạt giới hạn mã NV9999!");
                    return "";
                }

                return String.format("NV%04d", soLonNhat + 1);
            }

        } catch (SQLException e) {
            System.err.println("[NhanVien_DAO] sinhMaNVMoi lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        return "";
    }

    // ================= ĐỌC DỮ LIỆU NHÂN VIÊN =================
    private NhanVien docNhanVien(ResultSet rs) throws SQLException {
        Date ngaySQL = rs.getDate("NgayVaoLam");

        boolean gt = rs.getBoolean("GioiTinh");
        Boolean gioiTinh = rs.wasNull() ? null : gt;

        return new NhanVien(
                rs.getString("MaNV"),
                rs.getString("HoTen"),
                rs.getString("SoDT"),
                ngaySQL != null ? ngaySQL.toLocalDate() : null,
                gioiTinh,
                rs.getString("ChucVu"),
                rs.getString("TrangThai"),
                rs.getString("Email"),
                rs.getString("DiaChi")
        );
    }
}