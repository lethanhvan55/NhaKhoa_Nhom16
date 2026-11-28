
package dao;
import connectDB.ConnectDB;
import model.BenhNhan;
import model.DichVu;
import model.LichHen;
import model.NhanVien;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class LichHenDAO {
    private void kiemTraNgayHenHopLe(LocalDate ngayHen) {
        if (ngayHen == null) {
            throw new IllegalArgumentException("Ngày hẹn không được để trống.");
        }
        if (ngayHen.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày hẹn phải lớn hơn hoặc bằng ngày hiện tại (" + LocalDate.now() + ").");
        }
    }
    private int chuyenGioiTinhSangSo(String gioiTinh) {
        if (gioiTinh == null) return 0;
        String gt = gioiTinh.trim();
        return ("1".equals(gt) || "Nam".equalsIgnoreCase(gt)) ? 1 : 0;
    }
    private String trimSafe(String val) {
        return val == null ? "" : val.trim();
    }
    public boolean themLichHen(String maLichHen, String maBenhNhan, String maDichVu, String maPhong, String maNV, LocalDate ngayHen, LocalDateTime gioHen, String trangThai, String ghiChu) throws SQLException {
        kiemTraNgayHenHopLe(ngayHen);
        if (gioHen == null) throw new IllegalArgumentException("Giờ hẹn không được để trống.");
        if (!ngayHen.equals(gioHen.toLocalDate())) throw new IllegalArgumentException("Ngày và giờ hẹn không khớp.");
        String sql = """
            INSERT INTO LichHen
            (MaLichHen, MaBenhNhan, MaDichVu, MaPhong, MaNV, NgayHen, GioHen, TrangThai, GhiChu)
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
            SELECT lh.*, bn.HoTen AS TenBenhNhan, p.TenPhong, nv.HoTen AS TenBacSi, dv.TenDV
            FROM LichHen lh
            LEFT JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan
            LEFT JOIN PhongDieuTri p ON lh.MaPhong = p.MaPhong
            LEFT JOIN NhanVien nv ON lh.MaNV = nv.MaNV
            LEFT JOIN DichVu dv ON lh.MaDichVu = dv.MaDV
            WHERE lh.TrangThai IN (N'Đã đặt', N'Đang chờ khám', N'Hủy', N'Không đến')
            ORDER BY lh.NgayHen ASC, lh.GioHen ASC
            """;
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("MaLichHen", trimSafe(rs.getString("MaLichHen")));
                row.put("MaBenhNhan", trimSafe(rs.getString("MaBenhNhan")));
                row.put("TenBenhNhan", trimSafe(rs.getString("TenBenhNhan")));
                row.put("TenPhong", trimSafe(rs.getString("TenPhong")));
                row.put("TenBacSi", trimSafe(rs.getString("TenBacSi")));
                row.put("TenDV", trimSafe(rs.getString("TenDV")));
                Date ngayHen = rs.getDate("NgayHen");
                row.put("NgayHen", ngayHen != null ? ngayHen.toLocalDate() : null);
                Timestamp gioHen = rs.getTimestamp("GioHen");
                row.put("GioHen", gioHen != null ? gioHen.toLocalDateTime() : null);
                row.put("TrangThai", rs.getString("TrangThai"));
                row.put("GhiChu", rs.getString("GhiChu"));
                danhSach.add(row);
            }
        }
        return danhSach;
    }
    public boolean capNhatLichHen(String maLichHen, String maBenhNhan, String maDichVu, String maPhong, String maNV, LocalDate ngayHen, LocalDateTime gioHen, String trangThai, String ghiChu) throws SQLException {
        kiemTraNgayHenHopLe(ngayHen);
        if (gioHen == null) throw new IllegalArgumentException("Giờ hẹn không được để trống.");
        if (!ngayHen.equals(gioHen.toLocalDate())) throw new IllegalArgumentException("Ngày và giờ hẹn không khớp.");
        String sql = """
            UPDATE LichHen
            SET MaBenhNhan = ?, MaDichVu = ?, MaPhong = ?, MaNV = ?,
                NgayHen = ?, GioHen = ?, TrangThai = ?, GhiChu = ?
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
    public List<LichHen> getDanhSachLichHenTheoNgay(LocalDate ngayHen, String tuKhoa) {
        List<LichHen> danhSach = new ArrayList<>();

        if (ngayHen == null) {
            throw new IllegalArgumentException("Ngày tìm kiếm không được để trống.");
        }

        String sql = "SELECT lh.MaLichHen, lh.MaBenhNhan, "
                + "bn.HoTen AS TenBenhNhan, "
                + "lh.NgayHen, lh.GioHen, "
                + "lh.MaNV, nv.HoTen AS TenBacSi, "
                + "lh.TrangThai "
                + "FROM LichHen lh "
                + "LEFT JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan "
                + "LEFT JOIN NhanVien nv ON lh.MaNV = nv.MaNV "
                + "WHERE lh.NgayHen = ? ";

        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            sql += "AND (lh.MaLichHen LIKE ? "
                    + "OR lh.MaBenhNhan LIKE ? "
                    + "OR bn.HoTen LIKE ?) ";
        }

        sql += "ORDER BY lh.GioHen ASC";

        Connection con = ConnectDB.getInstance().getConnection();

        if (con == null) {
            return danhSach;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int index = 1;

            ps.setDate(index++, Date.valueOf(ngayHen));

            if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
                String keyword = "%" + tuKhoa.trim() + "%";

                ps.setString(index++, keyword);
                ps.setString(index++, keyword);
                ps.setString(index++, keyword);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LichHen lh = new LichHen();

                    lh.setMaLichHen(rs.getString("MaLichHen"));

                    // Thông tin bệnh nhân
                    BenhNhan bn = new BenhNhan();
                    bn.setMaBenhNhan(rs.getString("MaBenhNhan"));
                    bn.setHoTen(rs.getString("TenBenhNhan"));
                    lh.setMaBenhNhan(bn);

                    // Thông tin ngày hẹn
                    Date ngay = rs.getDate("NgayHen");
                    if (ngay != null) {
                        lh.setNgayHen(ngay.toLocalDate());
                    }

                    // Thông tin giờ hẹn
                    Timestamp gio = rs.getTimestamp("GioHen");
                    if (gio != null) {
                        lh.setGioHen(gio.toLocalDateTime());
                    }

                    // Thông tin bác sĩ
                    if (rs.getString("MaNV") != null) {
                        NhanVien nv = new NhanVien();
                        nv.setMaNV(rs.getString("MaNV"));
                        nv.setHoTen(rs.getString("TenBacSi"));
                        lh.setMaNV(nv);
                    }

                    lh.setTrangThai(rs.getString("TrangThai"));

                    danhSach.add(lh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSach;
    }

    public List<Map<String, String>> getDanhSachTatCaBenhNhan() throws SQLException {
        List<Map<String, String>> ds = new ArrayList<>();
        String sql = "SELECT MaBenhNhan, HoTen, NgaySinh, GioiTinh, SoDT, Email, DiaChi FROM BenhNhan";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> bn = new LinkedHashMap<>();
                bn.put("MaBenhNhan", trimSafe(rs.getString("MaBenhNhan")));
                bn.put("HoTen", trimSafe(rs.getString("HoTen")));
                Date ngaySinh = rs.getDate("NgaySinh");
                bn.put("NgaySinh", ngaySinh != null ? ngaySinh.toString() : "");
                bn.put("GioiTinh", rs.getInt("GioiTinh") == 1 ? "Nam" : "Nữ");
                bn.put("SoDienThoai", trimSafe(rs.getString("SoDT")));
                bn.put("Email", trimSafe(rs.getString("Email")));
                bn.put("DiaChi", trimSafe(rs.getString("DiaChi")));
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
            ps.setInt(4, chuyenGioiTinhSangSo(gioiTinh));
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
    public boolean capNhatThongTinBenhNhan(String maBN, String hoTen, LocalDate ngaySinh, String gioiTinh, String sdt, String email, String diaChi) throws SQLException {
        String sql = """
            UPDATE BenhNhan
            SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, SoDT = ?, Email = ?, DiaChi = ?
            WHERE MaBenhNhan = ?
            """;
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hoTen.trim());
            ps.setDate(2, Date.valueOf(ngaySinh));
            ps.setInt(3, chuyenGioiTinhSangSo(gioiTinh));
            ps.setString(4, sdt.trim());
            if (email == null || email.trim().isEmpty()) {
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(5, email.trim());
            }
            if (diaChi == null || diaChi.trim().isEmpty()) {
                ps.setNull(6, Types.NVARCHAR);
            } else {
                ps.setString(6, diaChi.trim());
            }
            ps.setString(7, maBN.trim());
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
    public List<Map<String, String>> getDanhSachNhaSiTrongCaLam(LocalDate ngayLam, String caLamViec, String maPhong) throws SQLException {
        List<Map<String, String>> ds = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT DISTINCT nv.MaNV, nv.HoTen, llv.CaLamViec
            FROM LichLamViec llv
            JOIN NhanVien nv ON llv.MaNhanVien = nv.MaNV
            WHERE nv.ChucVu = N'Nha sĩ'
              AND llv.TrangThai IN (N'Đã phân công', N'Đang làm việc')
            """);
        if (ngayLam != null) sql.append(" AND llv.NgayPhanCong = ?");
        if (caLamViec != null && !caLamViec.trim().isEmpty()) sql.append(" AND llv.CaLamViec = ?");
        sql.append(" ORDER BY nv.MaNV");
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int idx = 1;
            if (ngayLam != null) ps.setDate(idx++, Date.valueOf(ngayLam));
            if (caLamViec != null && !caLamViec.trim().isEmpty()) ps.setString(idx++, caLamViec.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> bs = new LinkedHashMap<>();
                    bs.put("MaNV", trimSafe(rs.getString("MaNV")));
                    bs.put("HoTen", trimSafe(rs.getString("HoTen")));
                    bs.put("CaLamViec", trimSafe(rs.getString("CaLamViec")));
                    ds.add(bs);
                }
            }
        }
        return ds;
    }
    public List<Map<String, String>> getDanhSachNhaSiTrongNgay(LocalDate ngayHen) throws SQLException {
        List<Map<String, String>> ds = new ArrayList<>();
        if (ngayHen == null) return ds;
        String sql = """
            SELECT DISTINCT nv.MaNV, nv.HoTen, llv.CaLamViec
            FROM LichLamViec llv
            JOIN NhanVien nv ON llv.MaNhanVien = nv.MaNV
            WHERE nv.ChucVu = N'Nha sĩ'
              AND llv.NgayPhanCong = ?
              AND llv.TrangThai IN (N'Đã phân công', N'Đang làm việc')
            ORDER BY nv.MaNV ASC, llv.CaLamViec ASC
            """;
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(ngayHen));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> bs = new LinkedHashMap<>();
                    bs.put("MaNV", trimSafe(rs.getString("MaNV")));
                    bs.put("HoTen", trimSafe(rs.getString("HoTen")));
                    bs.put("CaLamViec", trimSafe(rs.getString("CaLamViec")));
                    ds.add(bs);
                }
            }
        }
        return ds;
    }
    public List<Map<String, String>> getDanhSachNhaSiCoLichLamTheoThoiGian(LocalDate ngayHen, LocalTime gioHen) throws SQLException {
        List<Map<String, String>> ds = new ArrayList<>();
        if (ngayHen == null || gioHen == null) return ds;
        String sql = """
            SELECT DISTINCT nv.MaNV, nv.HoTen, llv.CaLamViec
            FROM LichLamViec llv
            JOIN NhanVien nv ON llv.MaNhanVien = nv.MaNV
            WHERE nv.ChucVu = N'Nha sĩ'
              AND llv.NgayPhanCong = ?
              AND llv.TrangThai IN (N'Đã phân công', N'Đang làm việc')
            ORDER BY nv.MaNV ASC
            """;
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(ngayHen));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String caLam = trimSafe(rs.getString("CaLamViec"));
                    if (!gioHenNamTrongCaLam1(gioHen, caLam)) continue;
                    Map<String, String> bs = new LinkedHashMap<>();
                    bs.put("MaNV", trimSafe(rs.getString("MaNV")));
                    bs.put("HoTen", trimSafe(rs.getString("HoTen")));
                    bs.put("CaLamViec", caLam);
                    ds.add(bs);
                }
            }
        }
        return ds;
    }

    public boolean kiemTraGioHenNamTrongCaLamSi(
            LocalDate ngayHen, LocalTime gioHen, String maNV) throws SQLException {
        if (ngayHen == null || gioHen == null || maNV == null || maNV.trim().isEmpty()) {
            return true;
        }
        String sql = """
            SELECT CaLamViec
            FROM LichLamViec
            WHERE LTRIM(RTRIM(MaNhanVien)) = ?
              AND NgayPhanCong = ?
              AND LTRIM(RTRIM(TrangThai)) <> N'Nghỉ phép'
            """;
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV.trim());
            ps.setDate(2, Date.valueOf(ngayHen));
            try (ResultSet rs = ps.executeQuery()) {
                boolean coPhanCong = false;
                while (rs.next()) {
                    coPhanCong = true;
                    if (gioHenNamTrongCaLam1(gioHen, trimSafe(rs.getString("CaLamViec")))) {
                        return true;
                    }
                }
                return !coPhanCong;
            }
        }
    }

    private boolean gioHenNamTrongCaLam1(LocalTime gioHen, String caLamViec) {
        if (gioHen == null) {
            return true;
        }
        LocalTime[] khoangGio = tachKhoangGioCaLam1(caLamViec);
        if (khoangGio != null) {
            return !gioHen.isBefore(khoangGio[0]) && !gioHen.isAfter(khoangGio[1]);
        }
        return false;
    }

    private LocalTime[] tachKhoangGioCaLam1(String caLamViec) {
        if (caLamViec == null || caLamViec.trim().isEmpty()) {
            return null;
        }
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("(\\d{1,2}:\\d{2})\\s*(?:-|–|—|~|đến|to)\\s*(\\d{1,2}:\\d{2})", java.util.regex.Pattern.CASE_INSENSITIVE)
                .matcher(caLamViec.trim());
        if (m.find()) {
            LocalTime start = parseGioLinhHoat(m.group(1));
            LocalTime end = parseGioLinhHoat(m.group(2));
            if (start != null && end != null) {
                return new LocalTime[]{start, end};
            }
        }
        return null;
    }

    private LocalTime parseGioLinhHoat(String raw) {
        try {
            String[] parts = raw.trim().split(":");
            if (parts.length != 2) return null;
            return LocalTime.of(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
        } catch (Exception e) {
            return null;
        }
    }
    
    private boolean gioHenNamTrongCaLam(LocalTime gioHen, String caLamViec) {
        if (gioHen == null) return false;
        LocalTime[] khoangGio = tachKhoangGioCaLam1(caLamViec);
        if (khoangGio == null) return false;
        return !gioHen.isBefore(khoangGio[0]) && !gioHen.isAfter(khoangGio[1]);
    }
    private LocalTime[] tachKhoangGioCaLam(String caLamViec) {
        if (caLamViec == null || caLamViec.trim().isEmpty()) return null;
        String ca = caLamViec.trim();
        int openIdx = ca.indexOf('(');
        int closeIdx = ca.indexOf(')');
        if (openIdx >= 0 && closeIdx > openIdx) {
            String khungGio = ca.substring(openIdx + 1, closeIdx);
            String[] parts = khungGio.split("-");
            if (parts.length == 2) {
                try {
                    return new LocalTime[]{
                        LocalTime.parse(parts[0].trim()),
                        LocalTime.parse(parts[1].trim())
                    };
                } catch (Exception ignored) {
                    return null;
                }
            }
        }
        String lower = ca.toLowerCase();
        if (lower.contains("sáng")) {
            return new LocalTime[]{LocalTime.of(7, 30), LocalTime.of(11, 30)};
        }
        if (lower.contains("chiều")) {
            return new LocalTime[]{LocalTime.of(13, 30), LocalTime.of(17, 30)};
        }
        if (lower.contains("tối")) {
            return new LocalTime[]{LocalTime.of(18, 0), LocalTime.of(21, 0)};
        }
        return null;
    }
    private boolean kiemTraCotTonTai(String tenBang, String tenCot) {
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenBang);
            ps.setString(2, tenCot);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    public List<Map<String, String>> getDanhSachNhaSi() throws SQLException {
        return getMapDanhSach("SELECT MaNV, HoTen FROM NhanVien WHERE ChucVu = N'Nha sĩ'", "MaNV", "HoTen");
    }
    public List<Map<String, String>> getDanhSachDichVu() throws SQLException {
        return getMapDanhSach("SELECT MaDV, TenDV FROM DichVu", "MaDV", "TenDV");
    }
    public List<Map<String, String>> getDanhSachPhongDieuTri() throws SQLException {
        return getMapDanhSach("SELECT MaPhong, TenPhong FROM PhongDieuTri", "MaPhong", "TenPhong");
    }
    private List<Map<String, String>> getMapDanhSach(String sql, String key1, String key2) throws SQLException {
        List<Map<String, String>> ds = new ArrayList<>();
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> item = new LinkedHashMap<>();
                item.put(key1, trimSafe(rs.getString(key1)));
                item.put(key2, trimSafe(rs.getString(key2)));
                ds.add(item);
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
                lh.setMaLichHen(trimSafe(rs.getString("MaLichHen")));
                ds.add(lh);
            }
        }
        return ds;
    }
    public List<Map<String, Object>> getDanhSachChoCheckIn(String tuKhoa, LocalDate ngayTimKiem) throws SQLException {
        List<Map<String, Object>> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT lh.*, bn.HoTen AS TenBenhNhan, p.TenPhong, nv.HoTen AS TenBacSi, dv.TenDV
            FROM LichHen lh
            LEFT JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan
            LEFT JOIN PhongDieuTri p ON lh.MaPhong = p.MaPhong
            LEFT JOIN NhanVien nv ON lh.MaNV = nv.MaNV
            LEFT JOIN DichVu dv ON lh.MaDichVu = dv.MaDV
            WHERE 1 = 1
            """);
        if (ngayTimKiem != null) sql.append(" AND lh.NgayHen = ?");
        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            sql.append(" AND (lh.MaLichHen LIKE ? OR lh.MaBenhNhan LIKE ? OR bn.HoTen LIKE ?)");
        }
        sql.append(" ORDER BY lh.NgayHen ASC, lh.GioHen ASC");
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int index = 1;
            if (ngayTimKiem != null) ps.setDate(index++, Date.valueOf(ngayTimKiem));
            if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
                String searchPattern = "%" + tuKhoa.trim() + "%";
                ps.setString(index++, searchPattern);
                ps.setString(index++, searchPattern);
                ps.setString(index++, searchPattern);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("MaLichHen", trimSafe(rs.getString("MaLichHen")));
                    row.put("MaBenhNhan", trimSafe(rs.getString("MaBenhNhan")));
                    row.put("TenBenhNhan", trimSafe(rs.getString("TenBenhNhan")));
                    row.put("TenPhong", trimSafe(rs.getString("TenPhong")));
                    row.put("TenBacSi", trimSafe(rs.getString("TenBacSi")));
                    row.put("TenDV", trimSafe(rs.getString("TenDV")));
                    Date ngayHen = rs.getDate("NgayHen");
                    row.put("NgayHen", ngayHen != null ? ngayHen.toLocalDate() : null);
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
    public boolean capNhatTrangThaiCheckIn(String maLichHen, String trangThaiMoi) throws SQLException {
        if (!"Đang chờ khám".equals(trangThaiMoi)) {
            throw new IllegalArgumentException("Trạng thái check-in không hợp lệ.");
        }
        String sql = "UPDATE LichHen SET TrangThai = N'Đang chờ khám' WHERE MaLichHen = ? AND TrangThai = N'Đã đặt'";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLichHen);
            return ps.executeUpdate() > 0;
        }
    }
    public List<DichVu> getDanhSachDichVuTheoLichHen(String maLichHen) {
        List<DichVu> danhSach = new ArrayList<>();
        String sql = """
            SELECT dv.MaDV, dv.TenDV, dv.DonGia, dv.ThoiGianDuKien, dv.TrangThai
            FROM LichHen lh
            JOIN DichVu dv ON lh.MaDichVu = dv.MaDV
            WHERE lh.MaLichHen = ?
            """;
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trimSafe(maLichHen));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DichVu dv = new DichVu();
                    dv.setMaDV(trimSafe(rs.getString("MaDV")));
                    dv.setTenDV(rs.getString("TenDV"));
                    dv.setDonGia(rs.getDouble("DonGia"));
                    String thoiGian = rs.getString("ThoiGianDuKien");
                    dv.setThoiGianDuKien(thoiGian == null || thoiGian.trim().isEmpty() ? "Chưa xác định" : thoiGian);
                    dv.setTrangThai(rs.getString("TrangThai"));
                    danhSach.add(dv);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy dịch vụ theo lịch hẹn: " + e.getMessage(), e);
        }
        return danhSach;
    }
    public LichHen getThongTinLichHen(String maLichHen) {
        String sql = """
            SELECT lh.MaLichHen, lh.MaBenhNhan, bn.HoTen, bn.SoDT,
                   lh.NgayHen, lh.GioHen, lh.GhiChu, lh.TrangThai
            FROM LichHen lh
            JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan
            WHERE lh.MaLichHen = ?
            """;
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trimSafe(maLichHen));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LichHen lh = new LichHen();
                    lh.setMaLichHen(trimSafe(rs.getString("MaLichHen")));
                    BenhNhan bn = new BenhNhan();
                    bn.setMaBenhNhan(trimSafe(rs.getString("MaBenhNhan")));
                    bn.setHoTen(rs.getString("HoTen"));
                    bn.setSoDT(rs.getString("SoDT"));
                    lh.setMaBenhNhan(bn);
                    Date ngayHen = rs.getDate("NgayHen");
                    if (ngayHen != null) lh.setNgayHen(ngayHen.toLocalDate());
                    Timestamp gioHen = rs.getTimestamp("GioHen");
                    if (gioHen != null) lh.setGioHen(gioHen.toLocalDateTime());
                    lh.setGhiChu(rs.getString("GhiChu"));
                    lh.setTrangThai(rs.getString("TrangThai"));
                    return lh;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy thông tin lịch hẹn: " + e.getMessage(), e);
        }
        return null;
    }
}
