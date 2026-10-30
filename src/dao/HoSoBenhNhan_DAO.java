package dao;

import connectDB.ConnectDB;
import model.HoSoBenhNhan;
import model.BenhNhan;
import model.NhanVien;
import model.KeHoachDieuTri;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoSoBenhNhan_DAO {

    // ================= LẤY TẤT CẢ HỒ SƠ BỆNH NHÂN =================
    public List<HoSoBenhNhan> getAllHoSoBenhNhan() {
        List<HoSoBenhNhan> list = new ArrayList<>();

        String sql = "SELECT MaHoSo, MaBenhNhan, MaNV, MaKeHoach, NgayKham, "
                + "TinhTrangRangMieng, ChanDoan, PhuongPhapDieuTri, "
                + "TienSuBenh, DiUng, BenhLyNen, NguoiLienHe, SoDTNLH, GhiChu "
                + "FROM HoSoBenhNhan ORDER BY MaHoSo";

        Connection con = ConnectDB.getConnection();

        if (con == null) {
            return list;
        }

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(docHoSoBenhNhan(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= TÌM KIẾM HỒ SƠ BỆNH NHÂN =================
    public List<HoSoBenhNhan> timKiemHoSoBenhNhan(String keyword) {
        List<HoSoBenhNhan> list = new ArrayList<>();

        String sql = "SELECT MaHoSo, MaBenhNhan, MaNV, MaKeHoach, NgayKham, "
                + "TinhTrangRangMieng, ChanDoan, PhuongPhapDieuTri, "
                + "TienSuBenh, DiUng, BenhLyNen, NguoiLienHe, SoDTNLH, GhiChu "
                + "FROM HoSoBenhNhan "
                + "WHERE MaHoSo LIKE ? "
                + "OR MaBenhNhan LIKE ? "
                + "OR MaNV LIKE ? "
                + "OR MaKeHoach LIKE ? "
                + "OR TinhTrangRangMieng LIKE ? "
                + "OR ChanDoan LIKE ? "
                + "OR PhuongPhapDieuTri LIKE ? "
                + "OR TienSuBenh LIKE ? "
                + "OR DiUng LIKE ? "
                + "OR BenhLyNen LIKE ? "
                + "OR NguoiLienHe LIKE ? "
                + "OR SoDTNLH LIKE ? "
                + "OR GhiChu LIKE ? "
                + "ORDER BY MaHoSo";

        Connection con = ConnectDB.getConnection();

        if (con == null) {
            return list;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String kw = "%" + (keyword == null ? "" : keyword.trim()) + "%";

            for (int i = 1; i <= 13; i++) {
                ps.setString(i, kw);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(docHoSoBenhNhan(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= THÊM HỒ SƠ BỆNH NHÂN =================
    public boolean insert(HoSoBenhNhan hs) {
        if (hs == null) {
            return false;
        }

        if (hs.getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan().trim().isEmpty()) {
            return false;
        }

        if (hs.getMaNV() == null
                || hs.getMaNV().getMaNV() == null
                || hs.getMaNV().getMaNV().trim().isEmpty()) {
            return false;
        }

        if (hs.getNgayKham() == null) {
            return false;
        }

        if (hs.getNgayKham().isAfter(java.time.LocalDate.now())) {
            return false;
        }

        if (hs.getTinhTrangRangMieng() == null
                || hs.getTinhTrangRangMieng().trim().isEmpty()
                || hs.getChanDoan() == null
                || hs.getChanDoan().trim().isEmpty()
                || hs.getPhuongPhapDieuTri() == null
                || hs.getPhuongPhapDieuTri().trim().isEmpty()) {
            return false;
        }

        String maMoi = sinhMaHoSoMoi();

        if (maMoi == null || maMoi.trim().isEmpty()) {
            return false;
        }

        String sql =
                "INSERT INTO HoSoBenhNhan "
                        + "(MaHoSo, MaBenhNhan, MaNV, MaKeHoach, NgayKham, "
                        + "TinhTrangRangMieng, ChanDoan, PhuongPhapDieuTri, "
                        + "TienSuBenh, DiUng, BenhLyNen, NguoiLienHe, SoDTNLH, GhiChu) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = ConnectDB.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, maMoi);
            ps.setString(2, hs.getMaBenhNhan().getMaBenhNhan().trim());
            ps.setString(3, hs.getMaNV().getMaNV().trim());

            if (hs.getMaKeHoach() == null
                    || hs.getMaKeHoach().getMaKeHoach() == null
                    || hs.getMaKeHoach().getMaKeHoach().trim().isEmpty()) {
                ps.setNull(4, java.sql.Types.CHAR);
            } else {
                ps.setString(4, hs.getMaKeHoach().getMaKeHoach().trim());
            }

            ps.setDate(5, java.sql.Date.valueOf(hs.getNgayKham()));

            ps.setString(6, hs.getTinhTrangRangMieng().trim());
            ps.setString(7, hs.getChanDoan().trim());
            ps.setString(8, hs.getPhuongPhapDieuTri().trim());

            ps.setString(9, hs.getTienSuBenh());
            ps.setString(10, hs.getDiUng());
            ps.setString(11, hs.getBenhLyNen());
            ps.setString(12, hs.getNguoiLienHe());
            ps.setString(13, hs.getSoDTNLH());
            ps.setString(14, hs.getGhiChu());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                hs.setMaHoSo(maMoi);
                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= CẬP NHẬT HỒ SƠ BỆNH NHÂN =================
    public boolean update(HoSoBenhNhan hs) {

        if (hs == null || hs.getMaHoSo() == null
                || hs.getMaHoSo().trim().isEmpty()) {
            return false;
        }

        if (hs.getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan().trim().isEmpty()) {
            return false;
        }

        if (hs.getMaNV() == null
                || hs.getMaNV().getMaNV() == null
                || hs.getMaNV().getMaNV().trim().isEmpty()) {
            return false;
        }

        String sql = "UPDATE HoSoBenhNhan SET "
                + "MaBenhNhan = ?, MaNV = ?, MaKeHoach = ?, NgayKham = ?, "
                + "TinhTrangRangMieng = ?, ChanDoan = ?, PhuongPhapDieuTri = ?, "
                + "TienSuBenh = ?, DiUng = ?, BenhLyNen = ?, "
                + "NguoiLienHe = ?, SoDTNLH = ?, GhiChu = ? "
                + "WHERE MaHoSo = ?";

        Connection con = ConnectDB.getConnection();

        if (con == null) {
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hs.getMaBenhNhan().getMaBenhNhan());
            ps.setString(2, hs.getMaNV().getMaNV());

            if (hs.getMaKeHoach() == null) {
                ps.setNull(3, Types.CHAR);
            } else {
                ps.setString(3, hs.getMaKeHoach().getMaKeHoach());
            }

            ps.setDate(4, Date.valueOf(hs.getNgayKham()));

            ps.setString(5, hs.getTinhTrangRangMieng().trim());
            ps.setString(6, hs.getChanDoan().trim());
            ps.setString(7, hs.getPhuongPhapDieuTri().trim());

            ps.setString(8, hs.getTienSuBenh());
            ps.setString(9, hs.getDiUng());
            ps.setString(10, hs.getBenhLyNen());
            ps.setString(11, hs.getNguoiLienHe());
            ps.setString(12, hs.getSoDTNLH());
            ps.setString(13, hs.getGhiChu());

            ps.setString(14, hs.getMaHoSo().trim());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= XÓA HỒ SƠ BỆNH NHÂN =================
    public boolean delete(String maHoSo) {

        if (maHoSo == null || maHoSo.trim().isEmpty()) {
            return false;
        }

        String sql = "DELETE FROM HoSoBenhNhan WHERE MaHoSo = ?";

        Connection con = ConnectDB.getConnection();

        if (con == null) {
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHoSo.trim());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= SINH MÃ HỒ SƠ MỚI =================
    public String sinhMaHoSoMoi() {
        String sql =
                "SELECT ISNULL(MAX(CAST(RIGHT(MaHoSo, 4) AS INT)), 0) + 1 "
                        + "FROM HoSoBenhNhan "
                        + "WHERE MaHoSo LIKE 'HS[0-9][0-9][0-9][0-9]'";

        try (
                Connection con = ConnectDB.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                int so = rs.getInt(1);

                if (so > 9999) {
                    return null;
                }

                return String.format("HS%04d", so);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<HoSoBenhNhan> getHoSoTheoMaBenhNhan(String maBenhNhan) {
        List<HoSoBenhNhan> list = new ArrayList<>();

        String sql = "SELECT MaHoSo, MaBenhNhan, MaNV, MaKeHoach, NgayKham, "
                + "TinhTrangRangMieng, ChanDoan, PhuongPhapDieuTri, "
                + "TienSuBenh, DiUng, BenhLyNen, NguoiLienHe, SoDTNLH, GhiChu "
                + "FROM HoSoBenhNhan "
                + "WHERE MaBenhNhan = ? "
                + "ORDER BY NgayKham DESC";

        Connection con = ConnectDB.getInstance().getConnection();

        if (con == null) {
            return list;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maBenhNhan);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(docHoSoBenhNhan(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    // ================= KIỂM TRA DỮ LIỆU =================
    private boolean kiemTraDuLieu(HoSoBenhNhan hs) {

        if (hs.getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan().trim().isEmpty()) {
            return false;
        }

// Kiểm tra nhân viên
        if (hs.getMaNV() == null
                || hs.getMaNV().getMaNV() == null
                || hs.getMaNV().getMaNV().trim().isEmpty()) {
            return false;
        }

        if (hs.getNgayKham() == null) {
            return false;
        }

        if (hs.getNgayKham().isAfter(LocalDate.now())) {
            return false;
        }

        if (hs.getTinhTrangRangMieng() == null
                || hs.getTinhTrangRangMieng().trim().isEmpty()) {
            return false;
        }

        if (hs.getChanDoan() == null || hs.getChanDoan().trim().isEmpty()) {
            return false;
        }

        if (hs.getPhuongPhapDieuTri() == null
                || hs.getPhuongPhapDieuTri().trim().isEmpty()) {
            return false;
        }

        return true;
    }

    // ================= ĐỌC DỮ LIỆU TỪ RESULTSET =================
    private HoSoBenhNhan docHoSoBenhNhan(ResultSet rs) throws SQLException {

        Date ngaySQL = rs.getDate("NgayKham");

        // Tạo đối tượng bệnh nhân từ mã trong database
        BenhNhan benhNhan = new BenhNhan();
        benhNhan.setMaBenhNhan(rs.getString("MaBenhNhan"));

        // Tạo đối tượng nhân viên từ mã trong database
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(rs.getString("MaNV"));

        // Mã kế hoạch có thể NULL
        String maKH = rs.getString("MaKeHoach");
        KeHoachDieuTri keHoach = null;

        if (maKH != null && !maKH.trim().isEmpty()) {
            keHoach = new KeHoachDieuTri();
            keHoach.setMaKeHoach(maKH);
        }

        return new HoSoBenhNhan(
                rs.getString("MaHoSo"),
                benhNhan,
                nhanVien,
                keHoach,
                ngaySQL != null ? ngaySQL.toLocalDate() : null,
                rs.getString("TinhTrangRangMieng"),
                rs.getString("ChanDoan"),
                rs.getString("PhuongPhapDieuTri"),
                rs.getString("TienSuBenh"),
                rs.getString("DiUng"),
                rs.getString("BenhLyNen"),
                rs.getString("NguoiLienHe"),
                rs.getString("SoDTNLH"),
                rs.getString("GhiChu")
        );
    }
}