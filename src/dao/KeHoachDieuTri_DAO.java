
package dao;
import connectDB.ConnectDB;
import model.BenhNhan;
import model.DichVu;
import model.KeHoachDieuTri;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class KeHoachDieuTri_DAO {
    public List<KeHoachDieuTri> getAll() {
        List<KeHoachDieuTri> ds = new ArrayList<>();
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null) return ds;
        String sql = "SELECT kh.*, bn.HoTen FROM KeHoachDieuTri kh LEFT JOIN BenhNhan bn ON kh.MaBenhNhan = bn.MaBenhNhan ORDER BY kh.MaKeHoach ASC";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BenhNhan bn = new BenhNhan();
                bn.setMaBenhNhan(rs.getString("MaBenhNhan"));
                bn.setHoTen(rs.getString("HoTen"));
                ds.add(new KeHoachDieuTri(rs.getString("MaKeHoach"), bn, rs.getString("ChanDoan"), rs.getDate("NgayBatDau") != null ? rs.getDate("NgayBatDau").toLocalDate() : null, rs.getDate("NgayKetThuc") != null ? rs.getDate("NgayKetThuc").toLocalDate() : null, rs.getDouble("ChiPhiDuKien"), rs.getString("TrangThai")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    public List<KeHoachDieuTri> timKiemNangCao(String tuKhoa, String trangThai, LocalDate tuNgay, LocalDate denNgay) {
        List<KeHoachDieuTri> ds = new ArrayList<>();
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null) return ds;
        StringBuilder sql = new StringBuilder("SELECT kh.*, bn.HoTen FROM KeHoachDieuTri kh LEFT JOIN BenhNhan bn ON kh.MaBenhNhan = bn.MaBenhNhan WHERE 1=1");
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
            for (int i = 0; i < params.size(); i++) stmt.setObject(i + 1, params.get(i));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BenhNhan bn = new BenhNhan();
                    bn.setMaBenhNhan(rs.getString("MaBenhNhan"));
                    bn.setHoTen(rs.getString("HoTen"));
                    ds.add(new KeHoachDieuTri(rs.getString("MaKeHoach"), bn, rs.getString("ChanDoan"), rs.getDate("NgayBatDau") != null ? rs.getDate("NgayBatDau").toLocalDate() : null, rs.getDate("NgayKetThuc") != null ? rs.getDate("NgayKetThuc").toLocalDate() : null, rs.getDouble("ChiPhiDuKien"), rs.getString("TrangThai")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    public boolean themKeHoach(KeHoachDieuTri kh) {
        if (kh == null) return false;
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null) return false;
        String sql = "INSERT INTO KeHoachDieuTri (MaKeHoach, MaBenhNhan, ChanDoan, NgayBatDau, NgayKetThuc, ChiPhiDuKien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, kh.getMaKeHoach());
            stmt.setString(2, kh.getMaBenhNhan() != null ? kh.getMaBenhNhan().getMaBenhNhan() : null);
            stmt.setString(3, kh.getChanDoan());
            stmt.setDate(4, kh.getNgayBatDau() != null ? Date.valueOf(kh.getNgayBatDau()) : null);
            stmt.setDate(5, kh.getNgayKetThuc() != null ? Date.valueOf(kh.getNgayKetThuc()) : null);
            stmt.setBigDecimal(6, BigDecimal.valueOf(kh.getChiPhiDuKien()));
            stmt.setString(7, kh.getTrangThai());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean suaKeHoach(KeHoachDieuTri kh) {
        if (kh == null) return false;
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null) return false;
        String sql = "UPDATE KeHoachDieuTri SET MaBenhNhan = ?, ChanDoan = ?, NgayBatDau = ?, NgayKetThuc = ?, ChiPhiDuKien = ?, TrangThai = ? WHERE MaKeHoach = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, kh.getMaBenhNhan() != null ? kh.getMaBenhNhan().getMaBenhNhan() : null);
            stmt.setString(2, kh.getChanDoan());
            stmt.setDate(3, kh.getNgayBatDau() != null ? Date.valueOf(kh.getNgayBatDau()) : null);
            stmt.setDate(4, kh.getNgayKetThuc() != null ? Date.valueOf(kh.getNgayKetThuc()) : null);
            stmt.setBigDecimal(5, BigDecimal.valueOf(kh.getChiPhiDuKien()));
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
        if (con == null || maKeHoach == null || maKeHoach.trim().isEmpty()) return false;
        String sqlXoaCT = "DELETE FROM ChiTietKeHoachDieuTri WHERE MaKeHoach = ?";
        String sqlXoaKH = "DELETE FROM KeHoachDieuTri WHERE MaKeHoach = ?";
        boolean autoCommit = true;
        try {
            autoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            try (PreparedStatement psCT = con.prepareStatement(sqlXoaCT); PreparedStatement psKH = con.prepareStatement(sqlXoaKH)) {
                psCT.setString(1, maKeHoach);
                psCT.executeUpdate();
                psKH.setString(1, maKeHoach);
                int affected = psKH.executeUpdate();
                if (affected <= 0) {
                    con.rollback();
                    return false;
                }
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<Map<String, Object>> getChiTietDichVuTheoMaKeHoach(String maKeHoach) {
        List<Map<String, Object>> ds = new ArrayList<>();
        if (maKeHoach == null || maKeHoach.trim().isEmpty()) return ds;
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null) return ds;
        String sql = "SELECT ct.MaDV, dv.TenDV, ct.SoLuong, ct.DonGia, (CAST(ct.SoLuong AS DECIMAL(18,2)) * ct.DonGia) AS ThanhTien FROM ChiTietKeHoachDieuTri ct JOIN DichVu dv ON ct.MaDV = dv.MaDV WHERE ct.MaKeHoach = ? ORDER BY ct.MaChiTiet ASC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maKeHoach.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("MaDV", rs.getString("MaDV"));
                    item.put("TenDV", rs.getString("TenDV"));
                    item.put("SoLuong", rs.getInt("SoLuong"));
                    item.put("DonGia", rs.getBigDecimal("DonGia"));
                    item.put("ThanhTien", rs.getBigDecimal("ThanhTien"));
                    ds.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    public boolean themKeHoachVaChiTiet(KeHoachDieuTri kh, List<Map<String, Object>> danhSachDV) {
        if (kh == null || danhSachDV == null || danhSachDV.isEmpty()) return false;
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null) return false;
        String sqlKH = "INSERT INTO KeHoachDieuTri (MaKeHoach, MaBenhNhan, ChanDoan, NgayBatDau, NgayKetThuc, ChiPhiDuKien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlCT = "INSERT INTO ChiTietKeHoachDieuTri (MaKeHoach, MaDV, SoLuong, DonGia, ThanhTien, TrangThai) VALUES (?, ?, ?, ?, ?, N'Chưa thực hiện')";
        boolean autoCommit = true;
        try {
            autoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            BigDecimal tongChiPhi = tinhTongChiPhiDanhSach(danhSachDV);
            try (PreparedStatement stmt = con.prepareStatement(sqlKH)) {
                stmt.setString(1, kh.getMaKeHoach());
                stmt.setString(2, kh.getMaBenhNhan() != null ? kh.getMaBenhNhan().getMaBenhNhan() : null);
                stmt.setString(3, kh.getChanDoan());
                stmt.setDate(4, kh.getNgayBatDau() != null ? Date.valueOf(kh.getNgayBatDau()) : null);
                stmt.setDate(5, kh.getNgayKetThuc() != null ? Date.valueOf(kh.getNgayKetThuc()) : null);
                stmt.setBigDecimal(6, tongChiPhi);
                stmt.setString(7, kh.getTrangThai());
                stmt.executeUpdate();
            }
            ghiChiTietBatch(con, sqlCT, kh.getMaKeHoach(), danhSachDV);
            con.commit();
            return true;
        } catch (SQLException | IllegalArgumentException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean suaKeHoachVaChiTiet(KeHoachDieuTri kh, List<Map<String, Object>> danhSachDV) {
        if (kh == null || danhSachDV == null || danhSachDV.isEmpty()) return false;
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null) return false;
        String sqlKH = "UPDATE KeHoachDieuTri SET MaBenhNhan = ?, ChanDoan = ?, NgayBatDau = ?, NgayKetThuc = ?, ChiPhiDuKien = ?, TrangThai = ? WHERE MaKeHoach = ?";
        String sqlXoaCT = "DELETE FROM ChiTietKeHoachDieuTri WHERE MaKeHoach = ?";
        String sqlCT = "INSERT INTO ChiTietKeHoachDieuTri (MaKeHoach, MaDV, SoLuong, DonGia, ThanhTien, TrangThai) VALUES (?, ?, ?, ?, ?, N'Chưa thực hiện')";
        boolean autoCommit = true;
        try {
            autoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            BigDecimal tongChiPhi = tinhTongChiPhiDanhSach(danhSachDV);
            int affected;
            try (PreparedStatement stmt = con.prepareStatement(sqlKH)) {
                stmt.setString(1, kh.getMaBenhNhan() != null ? kh.getMaBenhNhan().getMaBenhNhan() : null);
                stmt.setString(2, kh.getChanDoan());
                stmt.setDate(3, kh.getNgayBatDau() != null ? Date.valueOf(kh.getNgayBatDau()) : null);
                stmt.setDate(4, kh.getNgayKetThuc() != null ? Date.valueOf(kh.getNgayKetThuc()) : null);
                stmt.setBigDecimal(5, tongChiPhi);
                stmt.setString(6, kh.getTrangThai());
                stmt.setString(7, kh.getMaKeHoach());
                affected = stmt.executeUpdate();
            }
            if (affected <= 0) {
                con.rollback();
                return false;
            }
            try (PreparedStatement psDel = con.prepareStatement(sqlXoaCT)) {
                psDel.setString(1, kh.getMaKeHoach());
                psDel.executeUpdate();
            }
            ghiChiTietBatch(con, sqlCT, kh.getMaKeHoach(), danhSachDV);
            con.commit();
            return true;
        } catch (SQLException | IllegalArgumentException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private BigDecimal tinhTongChiPhiDanhSach(List<Map<String, Object>> danhSachDV) {
        BigDecimal tong = BigDecimal.ZERO;
        for (Map<String, Object> item : danhSachDV) {
            if (item == null) continue;
            int soLuong = parseSoLuongHopLe(item.get("SoLuong"));
            BigDecimal donGia = parseDonGiaHopLe(item.get("DonGia"));
            tong = tong.add(donGia.multiply(BigDecimal.valueOf(soLuong)));
        }
        return tong;
    }
    private void ghiChiTietBatch(Connection con, String sqlCT, String maKeHoach, List<Map<String, Object>> danhSachDV) throws SQLException {
        try (PreparedStatement psCT = con.prepareStatement(sqlCT)) {
            for (Map<String, Object> item : danhSachDV) {
                if (item == null) throw new SQLException("Chi tiết dịch vụ không hợp lệ.");
                String maDV = item.get("MaDV") != null ? String.valueOf(item.get("MaDV")).trim() : "";
                if (maDV.isEmpty()) throw new SQLException("Mã dịch vụ không được để trống.");
                int soLuong = parseSoLuongHopLe(item.get("SoLuong"));
                BigDecimal donGia = parseDonGiaHopLe(item.get("DonGia"));
                BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));
                psCT.setString(1, maKeHoach);
                psCT.setString(2, maDV);
                psCT.setInt(3, soLuong);
                psCT.setBigDecimal(4, donGia);
                psCT.setBigDecimal(5, thanhTien);
                psCT.addBatch();
            }
            psCT.executeBatch();
        }
    }
    private int parseSoLuongHopLe(Object raw) {
        if (raw == null) return 1;
        try {
            int sl = Integer.parseInt(String.valueOf(raw).trim());
            return sl > 0 ? sl : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
    private BigDecimal parseDonGiaHopLe(Object raw) {
        if (raw == null) return BigDecimal.ZERO;
        try {
            String value = String.valueOf(raw).trim().replaceAll("[^0-9,.-]", "");
            if (value.isEmpty()) return BigDecimal.ZERO;
            if (value.contains(",") && value.contains(".")) {
                if (value.lastIndexOf(",") > value.lastIndexOf(".")) {
                    value = value.replace(".", "").replace(",", ".");
                } else {
                    value = value.replace(",", "");
                }
            } else if (value.contains(",")) {
                value = value.replace(",", ".");
            }
            BigDecimal gia = new BigDecimal(value);
            return gia.signum() >= 0 ? gia : BigDecimal.ZERO;
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
    public List<KeHoachDieuTri> getKeHoachTheoMaBenhNhan(String maBenhNhan) {
        List<KeHoachDieuTri> ds = new ArrayList<>();
        if (maBenhNhan == null || maBenhNhan.trim().isEmpty()) return ds;
        String sql = "SELECT kh.*, bn.HoTen FROM KeHoachDieuTri kh LEFT JOIN BenhNhan bn ON kh.MaBenhNhan = bn.MaBenhNhan WHERE kh.MaBenhNhan = ? ORDER BY kh.NgayBatDau DESC";
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null) return ds;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maBenhNhan.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BenhNhan bn = new BenhNhan();
                    bn.setMaBenhNhan(rs.getString("MaBenhNhan"));
                    bn.setHoTen(rs.getString("HoTen"));
                    ds.add(new KeHoachDieuTri(rs.getString("MaKeHoach"), bn, rs.getString("ChanDoan"), rs.getDate("NgayBatDau") != null ? rs.getDate("NgayBatDau").toLocalDate() : null, rs.getDate("NgayKetThuc") != null ? rs.getDate("NgayKetThuc").toLocalDate() : null, rs.getDouble("ChiPhiDuKien"), rs.getString("TrangThai")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    public List<DichVu> getDichVuTuLichHenTheoKeHoachVaHoSo(String maBenhNhan, String maKeHoach) {
        List<DichVu> danhSachDichVu = new ArrayList<>();
        Connection con = ConnectDB.getInstance().getConnection();
        if (con == null || maBenhNhan == null || maBenhNhan.trim().isEmpty()) return danhSachDichVu;
        String sql = "SELECT DISTINCT dv.MaDV, dv.TenDV, dv.DonGia, dv.ThoiGianDuKien, dv.TrangThai FROM LichHen lh JOIN DichVu dv ON lh.MaDichVu = dv.MaDV LEFT JOIN HoSoBenhNhan hs ON hs.MaBenhNhan = lh.MaBenhNhan AND (? IS NOT NULL AND ? <> '' AND hs.MaKeHoach = ?) WHERE lh.MaBenhNhan = ? AND lh.TrangThai <> N'Hủy' AND ((? IS NULL OR ? = '') OR NOT EXISTS (SELECT 1 FROM HoSoBenhNhan hsCheck WHERE hsCheck.MaBenhNhan = lh.MaBenhNhan AND hsCheck.MaKeHoach = ?) OR hs.MaHoSo IS NOT NULL) ORDER BY dv.MaDV";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            String khTrim = maKeHoach == null ? "" : maKeHoach.trim();
            pstmt.setString(1, khTrim);
            pstmt.setString(2, khTrim);
            pstmt.setString(3, khTrim);
            pstmt.setString(4, maBenhNhan.trim());
            pstmt.setString(5, khTrim);
            pstmt.setString(6, khTrim);
            pstmt.setString(7, khTrim);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    danhSachDichVu.add(new DichVu(rs.getString("MaDV"), rs.getString("TenDV"), rs.getDouble("DonGia"), rs.getString("ThoiGianDuKien"), rs.getString("TrangThai")));
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
        try (Connection con = ConnectDB.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int maxMa = rs.getInt("MaxMa");
                int so = rs.wasNull() ? 1 : maxMa + 1;
                if (so > 9999) return null;
                return String.format("KH%04d", so);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
