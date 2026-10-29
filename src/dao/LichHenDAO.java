package dao;

import connectDB.ConnectDB;
import model.BenhNhan;
import model.DichVu;
import model.LichHen;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LichHenDAO {

    public boolean themLichHen(
            String maLichHen,
            String maBenhNhan,
            String maDichVu,
            String maPhong,
            String maNV,
            LocalDate ngayHen,
            LocalDateTime gioHen,
            String trangThai,
            String ghiChu) throws SQLException {

        String sql = """
            INSERT INTO LichHen
            (MaLichHen, MaBenhNhan, MaDichVu, MaPhong, MaNV,
             NgayHen, GioHen, TrangThai, GhiChu)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLichHen);
            ps.setString(2, maBenhNhan);
            ps.setString(3, maDichVu);
            ps.setString(4, maPhong);
            ps.setString(5, maNV);
            ps.setDate(6, Date.valueOf(ngayHen));
            ps.setTimestamp(7, Timestamp.valueOf(gioHen));
            ps.setString(8, trangThai);

            if (ghiChu == null || ghiChu.trim().isEmpty()) {
                ps.setNull(9, Types.NVARCHAR);
            } else {
                ps.setString(9, ghiChu.trim());
            }

            return ps.executeUpdate() > 0;
        }
    }

    public List<Map<String, Object>> getAllLichHen() throws SQLException {
        List<Map<String, Object>> danhSach = new ArrayList<>();

        String sql = """
            SELECT lh.*, 
                   bn.HoTen AS TenBenhNhan, 
                   p.TenPhong, 
                   nv.HoTen AS TenBacSi, 
                   dv.TenDV 
            FROM LichHen lh
            LEFT JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan
            LEFT JOIN PhongDieuTri p ON lh.MaPhong = p.MaPhong
            LEFT JOIN NhanVien nv ON lh.MaNV = nv.MaNV
            LEFT JOIN DichVu dv ON lh.MaDichVu = dv.MaDV
            ORDER BY lh.NgayHen ASC, lh.GioHen ASC
            """;

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("MaLichHen", rs.getString("MaLichHen").trim());
                row.put("MaBenhNhan", rs.getString("MaBenhNhan").trim());
                row.put("TenBenhNhan", rs.getString("TenBenhNhan") != null ? rs.getString("TenBenhNhan").trim() : "");
                row.put("TenPhong", rs.getString("TenPhong") != null ? rs.getString("TenPhong").trim() : "");
                row.put("TenBacSi", rs.getString("TenBacSi") != null ? rs.getString("TenBacSi").trim() : "");
                row.put("TenDV", rs.getString("TenDV") != null ? rs.getString("TenDV").trim() : "");
                row.put("NgayHen", rs.getDate("NgayHen").toLocalDate());
                Timestamp gioHen = rs.getTimestamp("GioHen");
                row.put("GioHen", gioHen != null ? gioHen.toLocalDateTime() : null);
                row.put("TrangThai", rs.getString("TrangThai"));
                row.put("GhiChu", rs.getString("GhiChu"));

                danhSach.add(row);
            }
        }
        return danhSach;
    }

    public boolean capNhatLichHen(
            String maLichHen,
            String maBenhNhan,
            String maDichVu,
            String maPhong,
            String maNV,
            LocalDate ngayHen,
            LocalDateTime gioHen,
            String trangThai,
            String ghiChu) throws SQLException {

        String sql = """
            UPDATE LichHen
            SET MaBenhNhan = ?,
                MaDichVu = ?,
                MaPhong = ?,
                MaNV = ?,
                NgayHen = ?,
                GioHen = ?,
                TrangThai = ?,
                GhiChu = ?
            WHERE MaLichHen = ?
            """;

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maBenhNhan);
            ps.setString(2, maDichVu);
            ps.setString(3, maPhong);
            ps.setString(4, maNV);
            ps.setDate(5, Date.valueOf(ngayHen));
            ps.setTimestamp(6, Timestamp.valueOf(gioHen));
            ps.setString(7, trangThai);

            if (ghiChu == null || ghiChu.trim().isEmpty()) {
                ps.setNull(8, Types.NVARCHAR);
            } else {
                ps.setString(8, ghiChu.trim());
            }

            ps.setString(9, maLichHen);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean xoaLichHen(String maLichHen) throws SQLException {
        String sql = "DELETE FROM LichHen WHERE MaLichHen = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLichHen);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Map<String, String>> getDanhSachTatCaBenhNhan() throws SQLException {
        List<Map<String, String>> ds = new ArrayList<>();
        String sql = "SELECT MaBenhNhan, HoTen, NgaySinh, GioiTinh, SoDT, DiaChi FROM BenhNhan";
        
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Map<String, String> bn = new LinkedHashMap<>();
                bn.put("MaBenhNhan", rs.getString("MaBenhNhan").trim());
                bn.put("HoTen", rs.getString("HoTen") != null ? rs.getString("HoTen").trim() : "");
                Date ngaySinh = rs.getDate("NgaySinh");
                bn.put("NgaySinh", ngaySinh != null ? ngaySinh.toString() : "");
                int gioiTinh = rs.getInt("GioiTinh");
                bn.put("GioiTinh", gioiTinh == 1 ? "Nam" : "Nữ");
                
                bn.put("SoDienThoai", rs.getString("SoDT") != null ? rs.getString("SoDT").trim() : "");
                bn.put("DiaChi", rs.getString("DiaChi") != null ? rs.getString("DiaChi").trim() : "");
                
                ds.add(bn);
            }
        }
        return ds;
    }

    public boolean themBenhNhanMoiDayDu(String maBN, String hoTen, LocalDate ngaySinh, String gioiTinh, String sdt, String email, String diaChi) throws SQLException {
        String sql = "INSERT INTO BenhNhan (MaBenhNhan, HoTen, NgaySinh, GioiTinh, SoDT, Email, DiaChi) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maBN);
            ps.setString(2, hoTen);
            ps.setDate(3, Date.valueOf(ngaySinh));
            ps.setString(4, gioiTinh); 
            ps.setString(5, sdt);
            
            if (email == null || email.trim().isEmpty()) {
                ps.setNull(6, Types.VARCHAR);
            } else {
                ps.setString(6, email.trim());
            }
            
            if (diaChi == null || diaChi.trim().isEmpty()) {
                ps.setNull(7, Types.NVARCHAR);
            } else {
                ps.setString(7, diaChi.trim());
            }
            
            return ps.executeUpdate() > 0;
        }
    }

    public String sinhMaBenhNhanTuDong() throws SQLException {
        String sql = "SELECT MAX(MaBenhNhan) FROM BenhNhan";
        try (Connection con = ConnectDB.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String maxMa = rs.getString(1);
                if (maxMa != null) {
                    int so = Integer.parseInt(maxMa.replaceAll("\\D+", ""));
                    return String.format("BN%04d", so + 1);
                }
            }
        }
        return "BN0001";
    }

    public List<Map<String, String>> getDanhSachNhaSi() throws SQLException {
        List<Map<String, String>> ds = new ArrayList<>();
        String sql = "SELECT MaNV, HoTen FROM NhanVien WHERE ChucVu = N'Nha sĩ'"; 
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> bs = new LinkedHashMap<>();
                bs.put("MaNV", rs.getString("MaNV").trim());
                bs.put("HoTen", rs.getString("HoTen").trim());
                ds.add(bs);
            }
        }
        return ds;
    }

    public List<Map<String, String>> getDanhSachDichVu() throws SQLException {
        List<Map<String, String>> ds = new ArrayList<>();
        String sql = "SELECT MaDV, TenDV FROM DichVu"; 
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> dv = new LinkedHashMap<>();
                dv.put("MaDV", rs.getString("MaDV").trim());
                dv.put("TenDV", rs.getString("TenDV").trim());
                ds.add(dv);
            }
        }
        return ds;
    }

    public List<Map<String, String>> getDanhSachPhongDieuTri() throws SQLException {
        List<Map<String, String>> ds = new ArrayList<>();
        String sql = "SELECT MaPhong, TenPhong FROM PhongDieuTri"; 
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> phong = new LinkedHashMap<>();
                phong.put("MaPhong", rs.getString("MaPhong").trim());
                phong.put("TenPhong", rs.getString("TenPhong").trim());
                ds.add(phong);
            }
        }
        return ds;
    }

    public List<LichHen> getDanhSachLichHenModel() throws SQLException {
        List<LichHen> ds = new ArrayList<>();
        String sql = "SELECT * FROM LichHen ORDER BY MaLichHen ASC";
        try (Connection con = ConnectDB.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LichHen lh = new LichHen();
                lh.setMaLichHen(rs.getString("MaLichHen").trim());
                ds.add(lh);
            }
        }
        return ds;
    }
   /* public List<Map<String, Object>> getDanhSachChoCheckIn(String tuKhoa, LocalDate ngayTimKiem) throws SQLException {
        List<Map<String, Object>> danhSach = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder("""
            SELECT lh.*, 
                   bn.HoTen AS TenBenhNhan, 
                   p.TenPhong, 
                   nv.HoTen AS TenBacSi, 
                   dv.TenDV 
            FROM LichHen lh
            LEFT JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan
            LEFT JOIN PhongDieuTri p ON lh.MaPhong = p.MaPhong
            LEFT JOIN NhanVien nv ON lh.MaNV = nv.MaNV
            LEFT JOIN DichVu dv ON lh.MaDichVu = dv.MaDV
            WHERE 1=1
            """);

        if (ngayTimKiem != null) {
            sql.append(" AND lh.NgayHen = ?");
        }
        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            sql.append(" AND (lh.MaLichHen LIKE ? OR lh.MaBenhNhan LIKE ? OR bn.HoTen LIKE ?)");
        }
        
        sql.append(" ORDER BY lh.NgayHen ASC, lh.GioHen ASC");

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            
            int index = 1;
            if (ngayTimKiem != null) {
                ps.setDate(index++, Date.valueOf(ngayTimKiem));
            }
            if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
                String searchPattern = "%" + tuKhoa.trim() + "%";
                ps.setString(index++, searchPattern);
                ps.setString(index++, searchPattern);
                ps.setString(index++, searchPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("MaLichHen", rs.getString("MaLichHen").trim());
                    row.put("MaBenhNhan", rs.getString("MaBenhNhan").trim());
                    row.put("TenBenhNhan", rs.getString("TenBenhNhan") != null ? rs.getString("TenBenhNhan").trim() : "");
                    row.put("TenPhong", rs.getString("TenPhong") != null ? rs.getString("TenPhong").trim() : "");
                    row.put("TenBacSi", rs.getString("TenBacSi") != null ? rs.getString("TenBacSi").trim() : "");
                    row.put("TenDV", rs.getString("TenDV") != null ? rs.getString("TenDV").trim() : "");
                    row.put("NgayHen", rs.getDate("NgayHen").toLocalDate());
                    Timestamp gioHen = rs.getTimestamp("GioHen");
                    row.put("GioHen", gioHen != null ? gioHen.toLocalDateTime() : null);
                    row.put("TrangThai", rs.getString("TrangThai"));
                    row.put("GhiChu", rs.getString("GhiChu"));

                    danhSach.add(row);
                }
            }
        }
        return danhSach;
    }
*/

    public List<Map<String, Object>> getDanhSachChoCheckIn(
            String tuKhoa, LocalDate ngayTimKiem) throws SQLException {

        List<Map<String, Object>> danhSach = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT lh.*,
               bn.HoTen AS TenBenhNhan,
               p.TenPhong,
               nv.HoTen AS TenBacSi,
               dv.TenDV
        FROM LichHen lh
        LEFT JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan
        LEFT JOIN PhongDieuTri p ON lh.MaPhong = p.MaPhong
        LEFT JOIN NhanVien nv ON lh.MaNV = nv.MaNV
        LEFT JOIN DichVu dv ON lh.MaDichVu = dv.MaDV
        WHERE 1 = 1
        """);

        if (ngayTimKiem != null) {
            sql.append(" AND lh.NgayHen = ?");
        }

        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            sql.append("""
             AND (
                 lh.MaLichHen LIKE ?
                 OR lh.MaBenhNhan LIKE ?
                 OR bn.HoTen LIKE ?
             )
             """);
        }

        sql.append(" ORDER BY lh.NgayHen ASC, lh.GioHen ASC");

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int index = 1;

            if (ngayTimKiem != null) {
                ps.setDate(index++, Date.valueOf(ngayTimKiem));
            }

            if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
                String searchPattern = "%" + tuKhoa.trim() + "%";

                ps.setString(index++, searchPattern);
                ps.setString(index++, searchPattern);
                ps.setString(index++, searchPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();

                    row.put("MaLichHen", rs.getString("MaLichHen").trim());
                    row.put("MaBenhNhan", rs.getString("MaBenhNhan").trim());

                    String tenBenhNhan = rs.getString("TenBenhNhan");
                    row.put("TenBenhNhan",
                            tenBenhNhan == null ? "" : tenBenhNhan.trim());

                    String tenPhong = rs.getString("TenPhong");
                    row.put("TenPhong",
                            tenPhong == null ? "" : tenPhong.trim());

                    String tenBacSi = rs.getString("TenBacSi");
                    row.put("TenBacSi",
                            tenBacSi == null ? "" : tenBacSi.trim());

                    String tenDV = rs.getString("TenDV");
                    row.put("TenDV",
                            tenDV == null ? "" : tenDV.trim());

                    row.put("NgayHen", rs.getDate("NgayHen").toLocalDate());

                    Timestamp gioHen = rs.getTimestamp("GioHen");
                    row.put("GioHen",
                            gioHen == null ? null : gioHen.toLocalDateTime());

                    row.put("TrangThai", rs.getString("TrangThai"));
                    row.put("GhiChu", rs.getString("GhiChu"));

                    danhSach.add(row);
                }
            }
        }

        return danhSach;
    }

  /*  public boolean capNhatTrangThaiCheckIn(String maLichHen, String trangThaiMoi) throws SQLException {
        String sql = "UPDATE LichHen SET TrangThai = ? WHERE MaLichHen = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trangThaiMoi);
            ps.setString(2, maLichHen);
            return ps.executeUpdate() > 0;
        }
    } */

    public boolean capNhatTrangThaiCheckIn(
            String maLichHen, String trangThaiMoi) throws SQLException {

        if (!"Đang chờ khám".equals(trangThaiMoi)) {
            throw new IllegalArgumentException("Trạng thái check-in không hợp lệ.");
        }

        String sql = """
        UPDATE LichHen
        SET TrangThai = N'Đang chờ khám'
        WHERE MaLichHen = ?
          AND TrangThai = N'Đã đặt'
        """;

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLichHen);

            return ps.executeUpdate() > 0;
        }
    }
    public List<DichVu> getDanhSachDichVuTheoLichHen(String maLichHen) {
        List<DichVu> danhSach = new ArrayList<>();

        String sql = "SELECT dv.MaDV, dv.TenDV, dv.DonGia, "
                + "dv.ThoiGianDuKien, dv.TrangThai "
                + "FROM LichHen lh "
                + "JOIN DichVu dv ON lh.MaDichVu = dv.MaDV "
                + "WHERE lh.MaLichHen = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLichHen.trim());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DichVu dv = new DichVu();

                    dv.setMaDV(rs.getString("MaDV").trim());
                    dv.setTenDV(rs.getString("TenDV"));
                    dv.setDonGia(rs.getDouble("DonGia"));

                    String thoiGian = rs.getString("ThoiGianDuKien");
                    dv.setThoiGianDuKien(
                            thoiGian == null || thoiGian.trim().isEmpty()
                                    ? "Chưa xác định"
                                    : thoiGian
                    );

                    dv.setTrangThai(rs.getString("TrangThai"));
                    danhSach.add(dv);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Lỗi lấy dịch vụ theo lịch hẹn: " + e.getMessage(), e
            );
        }

        return danhSach;
    }

    public LichHen getThongTinLichHen(String maLichHen) {
        String sql = "SELECT lh.MaLichHen, lh.MaBenhNhan, bn.HoTen, bn.SoDT, "
                + "lh.NgayHen, lh.GioHen, lh.GhiChu, lh.TrangThai "
                + "FROM LichHen lh "
                + "JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan "
                + "WHERE lh.MaLichHen = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLichHen.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LichHen lh = new LichHen();

                    lh.setMaLichHen(rs.getString("MaLichHen").trim());

                    BenhNhan bn = new BenhNhan();
                    bn.setMaBenhNhan(rs.getString("MaBenhNhan").trim());
                    bn.setHoTen(rs.getString("HoTen"));
                    bn.setSoDT(rs.getString("SoDT"));
                    lh.setMaBenhNhan(bn);

                    java.sql.Date ngayHen = rs.getDate("NgayHen");
                    if (ngayHen != null) {
                        lh.setNgayHen(ngayHen.toLocalDate());
                    }

                    Timestamp gioHen = rs.getTimestamp("GioHen");
                    if (gioHen != null) {
                        lh.setGioHen(gioHen.toLocalDateTime());
                    }

                    lh.setGhiChu(rs.getString("GhiChu"));
                    lh.setTrangThai(rs.getString("TrangThai"));

                    return lh;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Lỗi lấy thông tin lịch hẹn: " + e.getMessage(), e
            );
        }

        return null;
    }



}