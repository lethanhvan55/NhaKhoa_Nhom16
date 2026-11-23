package dao;

import connectDB.ConnectDB;
import model.BenhNhan;
import model.DichVu;
import model.KeHoachDieuTri;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KeHoachDieuTri_DAO {

    public List<KeHoachDieuTri> getAll() {
        List<KeHoachDieuTri> ds = new ArrayList<>();
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "SELECT kh.*, bn.HoTen FROM KeHoachDieuTri kh "
                + "LEFT JOIN BenhNhan bn ON kh.MaBenhNhan = bn.MaBenhNhan "
                + "ORDER BY kh.MaKeHoach ASC";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String maKeHoach = rs.getString("MaKeHoach");
                String maBN = rs.getString("MaBenhNhan");
                String hoTenBN = rs.getString("HoTen");
                BenhNhan benhNhan = new BenhNhan();
                benhNhan.setMaBenhNhan(maBN);
                benhNhan.setHoTen(hoTenBN);
                String chanDoan = rs.getString("ChanDoan");
                LocalDate ngayBatDau = rs.getDate("NgayBatDau") != null ? rs.getDate("NgayBatDau").toLocalDate() : null;
                LocalDate ngayKetThuc = rs.getDate("NgayKetThuc") != null ? rs.getDate("NgayKetThuc").toLocalDate() : null;
                double chiPhiDuKien = rs.getDouble("ChiPhiDuKien");
                String trangThai = rs.getString("TrangThai");

                ds.add(new KeHoachDieuTri(maKeHoach, benhNhan, chanDoan, ngayBatDau, ngayKetThuc, chiPhiDuKien, trangThai));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<KeHoachDieuTri> timKiemNangCao(String tuKhoa, String trangThai, LocalDate tuNgay, LocalDate denNgay) {
        List<KeHoachDieuTri> ds = new ArrayList<>();
        Connection con = ConnectDB.getInstance().getConnection();
        StringBuilder sql = new StringBuilder("SELECT kh.*, bn.HoTen FROM KeHoachDieuTri kh "
                + "LEFT JOIN BenhNhan bn ON kh.MaBenhNhan = bn.MaBenhNhan "
                + "WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            sql.append(" AND (kh.MaKeHoach LIKE ? OR kh.MaBenhNhan LIKE ?)");
            params.add("%" + tuKhoa.trim() + "%");
            params.add("%" + tuKhoa.trim() + "%");
        }

        if (trangThai != null && !trangThai.trim().isEmpty() && !trangThai.equalsIgnoreCase("Tất cả trạng thái")) {
            sql.append(" AND kh.TrangThai = ?");
            params.add(trangThai.trim());
        }

        if (tuNgay != null) {
            sql.append(" AND kh.NgayBatDau >= ?");
            params.add(Date.valueOf(tuNgay));
        }

        if (denNgay != null) {
            sql.append(" AND kh.NgayKetThuc <= ?");
            params.add(Date.valueOf(denNgay));
        }

        sql.append(" ORDER BY kh.MaKeHoach ASC");

        try (PreparedStatement stmt = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BenhNhan benhNhan = new BenhNhan();
                    benhNhan.setMaBenhNhan(rs.getString("MaBenhNhan"));
                    benhNhan.setHoTen(rs.getString("HoTen"));

                    ds.add(new KeHoachDieuTri(
                            rs.getString("MaKeHoach"),
                            benhNhan,
                            rs.getString("ChanDoan"),
                            rs.getDate("NgayBatDau") != null ? rs.getDate("NgayBatDau").toLocalDate() : null,
                            rs.getDate("NgayKetThuc") != null ? rs.getDate("NgayKetThuc").toLocalDate() : null,
                            rs.getDouble("ChiPhiDuKien"),
                            rs.getString("TrangThai")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean themKeHoach(KeHoachDieuTri kh) {
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "INSERT INTO KeHoachDieuTri (MaKeHoach, MaBenhNhan, ChanDoan, NgayBatDau, NgayKetThuc, ChiPhiDuKien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, kh.getMaKeHoach());
            stmt.setString(2, kh.getMaBenhNhan() != null ? kh.getMaBenhNhan().getMaBenhNhan() : null);
            stmt.setString(3, kh.getChanDoan());
            stmt.setDate(4, kh.getNgayBatDau() != null ? Date.valueOf(kh.getNgayBatDau()) : null);
            stmt.setDate(5, kh.getNgayKetThuc() != null ? Date.valueOf(kh.getNgayKetThuc()) : null);
            stmt.setDouble(6, kh.getChiPhiDuKien());
            stmt.setString(7, kh.getTrangThai());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaKeHoach(KeHoachDieuTri kh) {
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "UPDATE KeHoachDieuTri SET MaBenhNhan = ?, ChanDoan = ?, NgayBatDau = ?, NgayKetThuc = ?, ChiPhiDuKien = ?, TrangThai = ? WHERE MaKeHoach = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, kh.getMaBenhNhan() != null ? kh.getMaBenhNhan().getMaBenhNhan() : null);
            stmt.setString(2, kh.getChanDoan());
            stmt.setDate(3, kh.getNgayBatDau() != null ? Date.valueOf(kh.getNgayBatDau()) : null);
            stmt.setDate(4, kh.getNgayKetThuc() != null ? Date.valueOf(kh.getNgayKetThuc()) : null);
            stmt.setDouble(5, kh.getChiPhiDuKien());
            stmt.setString(6, kh.getTrangThai());
            stmt.setString(7, kh.getMaKeHoach());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaKeHoach(String maKeHoach) {
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "DELETE FROM KeHoachDieuTri WHERE MaKeHoach = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maKeHoach);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<KeHoachDieuTri> getKeHoachTheoMaBenhNhan(String maBenhNhan) {
        List<KeHoachDieuTri> ds = new ArrayList<>();
        String sql = "SELECT kh.*, bn.HoTen "
                + "FROM KeHoachDieuTri kh "
                + "LEFT JOIN BenhNhan bn ON kh.MaBenhNhan = bn.MaBenhNhan "
                + "WHERE kh.MaBenhNhan = ? "
                + "ORDER BY kh.NgayBatDau DESC";

        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null) {
            return ds;
        }

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maBenhNhan);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BenhNhan benhNhan = new BenhNhan();
                    benhNhan.setMaBenhNhan(rs.getString("MaBenhNhan"));
                    benhNhan.setHoTen(rs.getString("HoTen"));

                    ds.add(new KeHoachDieuTri(
                            rs.getString("MaKeHoach"),
                            benhNhan,
                            rs.getString("ChanDoan"),
                            rs.getDate("NgayBatDau") != null ? rs.getDate("NgayBatDau").toLocalDate() : null,
                            rs.getDate("NgayKetThuc") != null ? rs.getDate("NgayKetThuc").toLocalDate() : null,
                            rs.getDouble("ChiPhiDuKien"),
                            rs.getString("TrangThai")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    /**
     * Lấy dịch vụ từ lịch hẹn tương ứng dựa theo hồ sơ bệnh nhân gắn với kế hoạch điều trị
     * (nếu có hồ sơ gắn MaKeHoach thì lọc theo MaBenhNhan và khoảng thời gian hồ sơ/kế hoạch;
     * nếu chưa có hồ sơ gắn MaKeHoach thì lấy dịch vụ lịch hẹn của bệnh nhân).
     */
    public List<DichVu> getDichVuTuLichHenTheoKeHoachVaHoSo(String maBenhNhan, String maKeHoach) {
        List<DichVu> danhSachDichVu = new ArrayList<>();
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null || maBenhNhan == null || maBenhNhan.trim().isEmpty()) {
            return danhSachDichVu;
        }

        String sql = """
            SELECT DISTINCT dv.MaDV, dv.TenDV, dv.DonGia, dv.ThoiGianDuKien, dv.TrangThai
            FROM LichHen lh
            JOIN DichVu dv ON lh.MaDichVu = dv.MaDV
            LEFT JOIN HoSoBenhNhan hs ON hs.MaBenhNhan = lh.MaBenhNhan
                AND (? IS NOT NULL AND ? <> '' AND hs.MaKeHoach = ?)
            WHERE lh.MaBenhNhan = ?
              AND lh.TrangThai <> N'Hủy'
              AND (
                    (? IS NULL OR ? = '')
                    OR NOT EXISTS (
                        SELECT 1 FROM HoSoBenhNhan hsCheck
                        WHERE hsCheck.MaBenhNhan = lh.MaBenhNhan AND hsCheck.MaKeHoach = ?
                    )
                    OR hs.MaHoSo IS NOT NULL
                  )
            ORDER BY dv.MaDV
            """;

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            String khTrim = maKeHoach == null ? "" : maKeHoach.trim();
            String bnTrim = maBenhNhan.trim();

            pstmt.setString(1, khTrim);
            pstmt.setString(2, khTrim);
            pstmt.setString(3, khTrim);
            pstmt.setString(4, bnTrim);
            pstmt.setString(5, khTrim);
            pstmt.setString(6, khTrim);
            pstmt.setString(7, khTrim);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    danhSachDichVu.add(new DichVu(
                            rs.getString("MaDV"),
                            rs.getString("TenDV"),
                            rs.getDouble("DonGia"),
                            rs.getString("ThoiGianDuKien"),
                            rs.getString("TrangThai")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachDichVu;
    }

    public List<DichVu> getDichVuTheoMaBenhNhan(String maBenhNhan) {
        return getDichVuTuLichHenTheoKeHoachVaHoSo(maBenhNhan, null);
    }

    public String sinhMaKeHoachMoi() {
        String sql = "SELECT MAX(CAST(RIGHT(MaKeHoach, 4) AS INT)) AS MaxMa FROM KeHoachDieuTri";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int maxMa = rs.getInt("MaxMa");
                int so = rs.wasNull() ? 1 : maxMa + 1;
                if (so > 9999) {
                    return null;
                }
                return String.format("KH%04d", so);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}