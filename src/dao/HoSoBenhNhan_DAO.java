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
            System.err.println("[HoSoBenhNhan_DAO] Chưa kết nối database!");
            return list;
        }

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(docHoSoBenhNhan(rs));
            }

        } catch (SQLException e) {
            System.err.println("[HoSoBenhNhan_DAO] getAll lỗi: " + e.getMessage());
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
            System.err.println("[HoSoBenhNhan_DAO] Chưa kết nối database!");
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
            System.err.println("[HoSoBenhNhan_DAO] Tìm kiếm lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    // ================= THÊM HỒ SƠ BỆNH NHÂN =================
    public boolean insert(HoSoBenhNhan hs) {

        if (hs == null) {
            System.err.println("[HoSoBenhNhan_DAO] Hồ sơ không hợp lệ!");
            return false;
        }

        if (hs.getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan().trim().isEmpty()) {

            System.err.println("[HoSoBenhNhan_DAO] Mã bệnh nhân không được để trống!");
            return false;
        }

        if (hs.getMaNV() == null
                || hs.getMaNV().getMaNV() == null
                || hs.getMaNV().getMaNV().trim().isEmpty()) {

            System.err.println("[HoSoBenhNhan_DAO] Mã nhân viên không được để trống!");
            return false;
        }

        String maMoi = sinhMaHoSoMoi();

        if (maMoi == null || maMoi.isEmpty()) {
            System.err.println("[HoSoBenhNhan_DAO] Không tạo được mã hồ sơ!");
            return false;
        }

        String sql = "INSERT INTO HoSoBenhNhan "
                + "(MaHoSo, MaBenhNhan, MaNV, MaKeHoach, NgayKham, "
                + "TinhTrangRangMieng, ChanDoan, PhuongPhapDieuTri, "
                + "TienSuBenh, DiUng, BenhLyNen, NguoiLienHe, SoDTNLH, GhiChu) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = ConnectDB.getConnection();

        if (con == null) {
            System.err.println("[HoSoBenhNhan_DAO] Chưa kết nối database!");
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMoi);
            ps.setString(2, hs.getMaBenhNhan().getMaBenhNhan());
            ps.setString(3, hs.getMaNV().getMaNV());

            if (hs.getMaKeHoach() == null) {
                ps.setNull(4, Types.CHAR);
            } else {
                ps.setString(4, hs.getMaKeHoach().getMaKeHoach());
            }

            ps.setDate(5, Date.valueOf(hs.getNgayKham()));

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
            System.err.println("[HoSoBenhNhan_DAO] Insert lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ================= CẬP NHẬT HỒ SƠ BỆNH NHÂN =================
    public boolean update(HoSoBenhNhan hs) {

        if (hs == null || hs.getMaHoSo() == null
                || hs.getMaHoSo().trim().isEmpty()) {
            System.err.println("[HoSoBenhNhan_DAO] Mã hồ sơ không hợp lệ!");
            return false;
        }

        if (hs.getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan().trim().isEmpty()) {

            System.err.println("[HoSoBenhNhan_DAO] Mã bệnh nhân không được để trống!");
            return false;
        }

        if (hs.getMaNV() == null
                || hs.getMaNV().getMaNV() == null
                || hs.getMaNV().getMaNV().trim().isEmpty()) {

            System.err.println("[HoSoBenhNhan_DAO] Mã nhân viên không được để trống!");
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
            System.err.println("[HoSoBenhNhan_DAO] Chưa kết nối database!");
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
            System.err.println("[HoSoBenhNhan_DAO] Update lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ================= XÓA HỒ SƠ BỆNH NHÂN =================
    public boolean delete(String maHoSo) {

        if (maHoSo == null || maHoSo.trim().isEmpty()) {
            System.err.println("[HoSoBenhNhan_DAO] Mã hồ sơ cần xóa không hợp lệ!");
            return false;
        }

        String sql = "DELETE FROM HoSoBenhNhan WHERE MaHoSo = ?";

        Connection con = ConnectDB.getConnection();

        if (con == null) {
            System.err.println("[HoSoBenhNhan_DAO] Chưa kết nối database!");
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHoSo.trim());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[HoSoBenhNhan_DAO] Delete lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ================= SINH MÃ HỒ SƠ MỚI =================
    public String sinhMaHoSoMoi() {

        String sql = "SELECT MAX(CAST(SUBSTRING(MaHoSo, 3, 4) AS INT)) AS SoLonNhat "
                + "FROM HoSoBenhNhan "
                + "WHERE MaHoSo LIKE 'HS[0-9][0-9][0-9][0-9]'";

        Connection con = ConnectDB.getConnection();

        if (con == null) {
            System.err.println("[HoSoBenhNhan_DAO] Chưa kết nối database!");
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
                    System.err.println("[HoSoBenhNhan_DAO] Đã đạt giới hạn mã HS9999!");
                    return "";
                }

                return String.format("HS%04d", soLonNhat + 1);
            }

        } catch (SQLException e) {
            System.err.println("[HoSoBenhNhan_DAO] Sinh mã hồ sơ lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        return "";
    }

    // ================= KIỂM TRA DỮ LIỆU =================
    private boolean kiemTraDuLieu(HoSoBenhNhan hs) {

        if (hs.getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan() == null
                || hs.getMaBenhNhan().getMaBenhNhan().trim().isEmpty()) {

            System.err.println("[HoSoBenhNhan_DAO] Mã bệnh nhân không được để trống!");
            return false;
        }

// Kiểm tra nhân viên
        if (hs.getMaNV() == null
                || hs.getMaNV().getMaNV() == null
                || hs.getMaNV().getMaNV().trim().isEmpty()) {

            System.err.println("[HoSoBenhNhan_DAO] Mã nhân viên không được để trống!");
            return false;
        }

        if (hs.getNgayKham() == null) {
            System.err.println("[HoSoBenhNhan_DAO] Ngày khám không được để trống!");
            return false;
        }

        if (hs.getNgayKham().isAfter(LocalDate.now())) {
            System.err.println("[HoSoBenhNhan_DAO] Ngày khám không được lớn hơn ngày hiện tại!");
            return false;
        }

        if (hs.getTinhTrangRangMieng() == null
                || hs.getTinhTrangRangMieng().trim().isEmpty()) {
            System.err.println("[HoSoBenhNhan_DAO] Tình trạng răng miệng không được để trống!");
            return false;
        }

        if (hs.getChanDoan() == null || hs.getChanDoan().trim().isEmpty()) {
            System.err.println("[HoSoBenhNhan_DAO] Chẩn đoán không được để trống!");
            return false;
        }

        if (hs.getPhuongPhapDieuTri() == null
                || hs.getPhuongPhapDieuTri().trim().isEmpty()) {
            System.err.println("[HoSoBenhNhan_DAO] Phương pháp điều trị không được để trống!");
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