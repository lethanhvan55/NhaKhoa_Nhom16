
package dao;

import connectDB.ConnectDB;
import model.KhuyenMai;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMai_DAO {

    public List<KhuyenMai> getAll() {
        List<KhuyenMai> ds = new ArrayList<>();

        String sql = "SELECT MaKM, MaThayThe, TenKM, NgayBatDau, NgayKetThuc, "
                + "GiaTriToiThieu, ChietKhau, SoLuong, TrangThai "
                + "FROM KhuyenMai ORDER BY MaKM";

        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();

                km.setMaKM(rs.getString("MaKM"));
                km.setMaThayThe(rs.getString("MaThayThe"));
                km.setTenKM(rs.getString("TenKM"));

                Date ngayBD = rs.getDate("NgayBatDau");
                Date ngayKT = rs.getDate("NgayKetThuc");

                if (ngayBD != null) {
                    km.setNgayBatDau(ngayBD.toLocalDate());
                }

                if (ngayKT != null) {
                    km.setNgayKetThuc(ngayKT.toLocalDate());
                }

                km.setGiaTriToiThieu(rs.getDouble("GiaTriToiThieu"));
                km.setChietKhau(rs.getDouble("ChietKhau"));
                km.setSoLuong(rs.getInt("SoLuong"));
                km.setTrangThai(rs.getString("TrangThai"));

                ds.add(km);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }

    public String tuSinhMaKM() {
        String sql = "SELECT ISNULL(MAX(TRY_CONVERT(INT, SUBSTRING(MaKM, 3, 4))), 0) + 1 AS SoMoi "
                + "FROM KhuyenMai "
                + "WHERE MaKM LIKE 'KM[0-9][0-9][0-9][0-9]'";

        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int soMoi = rs.getInt("SoMoi");

                if (soMoi > 9999) {
                    rs.close();
                    ps.close();
                    return null;
                }

                String maKM = String.format("KM%04d", soMoi);

                rs.close();
                ps.close();

                return maKM;
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean them(KhuyenMai km) {
        String sql = "INSERT INTO KhuyenMai "
                + "(MaKM, MaThayThe, TenKM, NgayBatDau, NgayKetThuc, "
                + "GiaTriToiThieu, ChietKhau, SoLuong, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, km.getMaKM());
            ps.setString(2, km.getMaThayThe());
            ps.setString(3, km.getTenKM());
            ps.setDate(4, Date.valueOf(km.getNgayBatDau()));
            ps.setDate(5, Date.valueOf(km.getNgayKetThuc()));
            ps.setDouble(6, km.getGiaTriToiThieu());
            ps.setDouble(7, km.getChietKhau());
            ps.setInt(8, km.getSoLuong());
            ps.setString(9, km.getTrangThai());

            int ketQua = ps.executeUpdate();
            ps.close();

            return ketQua > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean sua(KhuyenMai km) {
        String sql = "UPDATE KhuyenMai SET "
                + "MaThayThe = ?, "
                + "TenKM = ?, "
                + "NgayBatDau = ?, "
                + "NgayKetThuc = ?, "
                + "GiaTriToiThieu = ?, "
                + "ChietKhau = ?, "
                + "SoLuong = ?, "
                + "TrangThai = ? "
                + "WHERE MaKM = ?";

        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, km.getMaThayThe());
            ps.setString(2, km.getTenKM());
            ps.setDate(3, Date.valueOf(km.getNgayBatDau()));
            ps.setDate(4, Date.valueOf(km.getNgayKetThuc()));
            ps.setDouble(5, km.getGiaTriToiThieu());
            ps.setDouble(6, km.getChietKhau());
            ps.setInt(7, km.getSoLuong());
            ps.setString(8, km.getTrangThai());
            ps.setString(9, km.getMaKM());

            int ketQua = ps.executeUpdate();
            ps.close();

            return ketQua > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoa(String maKM) {
        String sql = "DELETE FROM KhuyenMai WHERE MaKM = ?";

        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, maKM);

            int ketQua = ps.executeUpdate();
            ps.close();

            return ketQua > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<KhuyenMai> timKiem(String tuKhoa) {
        List<KhuyenMai> ds = new ArrayList<>();

        String sql = "SELECT MaKM, MaThayThe, TenKM, NgayBatDau, NgayKetThuc, "
                + "GiaTriToiThieu, ChietKhau, SoLuong, TrangThai "
                + "FROM KhuyenMai "
                + "WHERE MaKM LIKE ? OR MaThayThe LIKE ? OR TenKM LIKE ? "
                + "ORDER BY MaKM";

        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            String tuKhoaTim = "%" + tuKhoa.trim() + "%";

            ps.setString(1, tuKhoaTim);
            ps.setString(2, tuKhoaTim);
            ps.setString(3, tuKhoaTim);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();

                km.setMaKM(rs.getString("MaKM"));
                km.setMaThayThe(rs.getString("MaThayThe"));
                km.setTenKM(rs.getString("TenKM"));

                Date ngayBD = rs.getDate("NgayBatDau");
                Date ngayKT = rs.getDate("NgayKetThuc");

                if (ngayBD != null) {
                    km.setNgayBatDau(ngayBD.toLocalDate());
                }
                if (ngayKT != null) {
                    km.setNgayKetThuc(ngayKT.toLocalDate());
                }
                km.setGiaTriToiThieu(rs.getDouble("GiaTriToiThieu"));
                km.setChietKhau(rs.getDouble("ChietKhau"));
                km.setSoLuong(rs.getInt("SoLuong"));
                km.setTrangThai(rs.getString("TrangThai"));
                ds.add(km);
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }

    public boolean tonTaiMaThayThe(String maThayThe, String maKMBoQua) {
        String sql;

        if (maKMBoQua == null || maKMBoQua.trim().isEmpty()) {
            sql = "SELECT COUNT(*) FROM KhuyenMai WHERE MaThayThe = ?";
        } else {
            sql = "SELECT COUNT(*) FROM KhuyenMai WHERE MaThayThe = ? AND MaKM <> ?";
        }

        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maThayThe);
            if (maKMBoQua != null && !maKMBoQua.trim().isEmpty()) {
                ps.setString(2, maKMBoQua);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean tonTai = rs.getInt(1) > 0;
                rs.close();
                ps.close();
                return tonTai;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void capNhatTrangThaiTuNgayKetThuc() {
        String sql = "UPDATE KhuyenMai SET TrangThai = CASE WHEN NgayKetThuc < CAST(GETDATE() AS DATE) THEN N'Hết hạn' ELSE N'Đang áp dụng' END";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean giamSoLuongAtomic(String maKM) {
        String sql = "UPDATE KhuyenMai "
                + "SET SoLuong = SoLuong - 1 "
                + "WHERE MaKM = ? AND SoLuong > 0 "
                + "AND TrangThai = N'Đang áp dụng' "
                + "AND NgayBatDau <= CAST(GETDATE() AS DATE) "
                + "AND NgayKetThuc >= CAST(GETDATE() AS DATE)";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maKM);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tangSoLuongAtomic(String maKM) {
        String sql = "UPDATE KhuyenMai "
                + "SET SoLuong = SoLuong + 1 "
                + "WHERE MaKM = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maKM);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
