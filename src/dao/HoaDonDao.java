
package dao;

import connectDB.ConnectDB;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//tvan sửa
import java.util.LinkedHashMap;

public class HoaDonDao {
    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();

        String sql = "SELECT hd.MaHD, hd.MaBenhNhan, bn.HoTen AS TenBenhNhan, bn.SoDT, " +
                "hd.MaNV, nv.HoTen AS TenNhanVien, hd.MaLichHen, hd.NgayLap, " +
                "hd.ChietKhau, hd.VAT, hd.MaKM, hd.TrangThai " +
                "FROM HoaDon hd " +
                "JOIN BenhNhan bn ON hd.MaBenhNhan = bn.MaBenhNhan " +
                "JOIN NhanVien nv ON hd.MaNV = nv.MaNV " +
                "ORDER BY hd.MaHD DESC";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BenhNhan bn = new BenhNhan();
                bn.setMaBenhNhan(rs.getString("MaBenhNhan").trim());
                bn.setHoTen(rs.getString("TenBenhNhan"));
                bn.setSoDT(rs.getString("SoDT"));

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV").trim());
                nv.setHoTen(rs.getString("TenNhanVien"));

                LichHen lh = new LichHen();
                lh.setMaLichHen(rs.getString("MaLichHen").trim());

                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD").trim());
                hd.setMaBenhNhan(bn);
                hd.setMaNV(nv);
                hd.setMaLichHen(lh);

                Timestamp ngayLap = rs.getTimestamp("NgayLap");
                hd.setNgayLap(ngayLap == null ? null : ngayLap.toLocalDateTime());

                hd.setChietKhau(rs.getDouble("ChietKhau"));
                hd.setThueVAT(rs.getDouble("VAT") / 100.0);
                hd.setTrangThai(rs.getString("TrangThai"));

                String maKM = rs.getString("MaKM");
                if (maKM != null) {
                    KhuyenMai km = new KhuyenMai();
                    km.setMaKM(maKM.trim());
                    hd.setMaKM(km);
                }

                list.add(hd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public HoaDon getByMaHD(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            return null;
        }

        List<HoaDon> danhSach = getAll();

        for (HoaDon hd : danhSach) {
            if (hd.getMaHD() != null && hd.getMaHD().trim().equalsIgnoreCase(maHD.trim())) {
                return hd;
            }
        }

        return null;
    }
    public List<HoaDon> search(String keyword) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.MaHD, hd.MaBenhNhan, bn.HoTen AS TenBenhNhan, bn.SoDT, " +
                "hd.MaNV, nv.HoTen AS TenNhanVien, hd.MaLichHen, hd.NgayLap, " +
                "hd.ChietKhau, hd.VAT, hd.MaKM, hd.TrangThai " +
                "FROM HoaDon hd " +
                "JOIN BenhNhan bn ON hd.MaBenhNhan = bn.MaBenhNhan " +
                "JOIN NhanVien nv ON hd.MaNV = nv.MaNV " +
                "WHERE hd.MaHD LIKE ? OR hd.MaBenhNhan LIKE ? OR bn.HoTen LIKE ? " +
                "ORDER BY hd.MaHD DESC";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String tuKhoa = "%" + (keyword == null ? "" : keyword.trim()) + "%";
            ps.setString(1, tuKhoa);
            ps.setString(2, tuKhoa);
            ps.setString(3, tuKhoa);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BenhNhan bn = new BenhNhan();
                    bn.setMaBenhNhan(rs.getString("MaBenhNhan").trim());
                    bn.setHoTen(rs.getString("TenBenhNhan"));
                    bn.setSoDT(rs.getString("SoDT"));

                    NhanVien nv = new NhanVien();
                    nv.setMaNV(rs.getString("MaNV").trim());
                    nv.setHoTen(rs.getString("TenNhanVien"));

                    LichHen lh = new LichHen();
                    lh.setMaLichHen(rs.getString("MaLichHen").trim());

                    HoaDon hd = new HoaDon();
                    hd.setMaHD(rs.getString("MaHD").trim());
                    hd.setMaBenhNhan(bn);
                    hd.setMaNV(nv);
                    hd.setMaLichHen(lh);

                    Timestamp ngayLap = rs.getTimestamp("NgayLap");
                    hd.setNgayLap(ngayLap == null ? null : ngayLap.toLocalDateTime());
                    hd.setChietKhau(rs.getDouble("ChietKhau"));
                    hd.setThueVAT(rs.getDouble("VAT") / 100.0);
                    hd.setTrangThai(rs.getString("TrangThai"));

                    String maKM = rs.getString("MaKM");
                    if (maKM != null) {
                        KhuyenMai km = new KhuyenMai();
                        km.setMaKM(maKM.trim());
                        hd.setMaKM(km);
                    }

                    list.add(hd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public String sinhMaHDMoi(Connection con) throws SQLException {
        String sql = "SELECT MAX(TRY_CONVERT(INT, SUBSTRING(MaHD, 3, LEN(MaHD)))) AS SoLonNhat FROM HoaDon";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int soMoi = rs.getInt("SoLonNhat") + 1;
                return String.format("HD%04d", soMoi);
            }
        }

        return "HD0001";
    }

    public List<LichHen> getDanhSachLichHenDaCheckIn() {
        List<LichHen> danhSach = new ArrayList<>();

        String sql = "SELECT lh.MaLichHen, lh.MaBenhNhan, bn.HoTen, bn.SoDT, "
                + "lh.MaDichVu, dv.TenDV, dv.DonGia, dv.ThoiGianDuKien, dv.TrangThai AS TrangThaiDV, "
                + "lh.NgayHen, lh.GioHen, lh.GhiChu, lh.TrangThai AS TrangThaiLH "
                + "FROM LichHen lh "
                + "JOIN BenhNhan bn ON lh.MaBenhNhan = bn.MaBenhNhan "
                + "JOIN DichVu dv ON lh.MaDichVu = dv.MaDV "
                + "WHERE lh.TrangThai = N'Đang chờ khám' "
                + "AND NOT EXISTS (SELECT 1 FROM HoaDon hd WHERE hd.MaLichHen = lh.MaLichHen) "
                + "ORDER BY lh.NgayHen, lh.GioHen";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LichHen lh = new LichHen();

                lh.setMaLichHen(rs.getString("MaLichHen").trim());

                BenhNhan bn = new BenhNhan();
                bn.setMaBenhNhan(rs.getString("MaBenhNhan").trim());
                bn.setHoTen(rs.getString("HoTen"));
                bn.setSoDT(rs.getString("SoDT"));
                lh.setMaBenhNhan(bn);

                DichVu dv = new DichVu();
                dv.setMaDV(rs.getString("MaDichVu").trim());
                dv.setTenDV(rs.getString("TenDV"));
                dv.setDonGia(rs.getDouble("DonGia"));

                String thoiGian = rs.getString("ThoiGianDuKien");
                dv.setThoiGianDuKien(
                        thoiGian == null || thoiGian.trim().isEmpty()
                                ? "Chưa xác định"
                                : thoiGian
                );
                dv.setTrangThai(rs.getString("TrangThaiDV"));
                lh.setMaDichVu(dv);

                java.sql.Date ngayHen = rs.getDate("NgayHen");
                if (ngayHen != null) {
                    lh.setNgayHen(ngayHen.toLocalDate());
                }

                Timestamp gioHen = rs.getTimestamp("GioHen");
                if (gioHen != null) {
                    lh.setGioHen(gioHen.toLocalDateTime());
                }

                lh.setGhiChu(rs.getString("GhiChu"));
                lh.setTrangThai(rs.getString("TrangThaiLH"));

                danhSach.add(lh);
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Lỗi lấy danh sách lịch hẹn đã check-in: " + e.getMessage(), e
            );
        }

        return danhSach;
    }
    public String luuHoaDonVaHoanThanhLichHen(String maLichHen, String maNV, String maKM, double chietKhau, double vat,
                                              Map<String, Integer> dsDichVu) {

        Connection con = null;

        try {
            con = ConnectDB.getInstance().getConnection();
            con.setAutoCommit(false);

            String maHD = sinhMaHDMoi(con);
            String maBenhNhan;

            String sqlKiemTra = "SELECT MaBenhNhan FROM LichHen "
                    + "WHERE MaLichHen = ? AND TrangThai = N'Đang chờ khám'";

            try (PreparedStatement ps = con.prepareStatement(sqlKiemTra)) {
                ps.setString(1, maLichHen.trim());

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Lịch hẹn không hợp lệ hoặc không còn ở trạng thái chờ khám.");
                    }
                    maBenhNhan = rs.getString("MaBenhNhan").trim();
                }
            }

            String sqlHD = "INSERT INTO HoaDon "
                    + "(MaHD, MaBenhNhan, MaNV, MaLichHen, NgayLap, ChietKhau, VAT, MaKM, TrangThai) "
                    + "VALUES (?, ?, ?, ?, SYSDATETIME(), ?, ?, ?, N'Đã thanh toán')";

            try (PreparedStatement ps = con.prepareStatement(sqlHD)) {
                ps.setString(1, maHD);
                ps.setString(2, maBenhNhan);
                ps.setString(3, maNV.trim());
                ps.setString(4, maLichHen.trim());
                ps.setDouble(5, chietKhau);
                ps.setDouble(6, vat);

                if (maKM == null || maKM.trim().isEmpty()) {
                    ps.setNull(7, java.sql.Types.CHAR);
                } else {
                    ps.setString(7, maKM.trim());
                }

                ps.executeUpdate();
            }

            if (dsDichVu == null || dsDichVu.isEmpty()) {
                throw new SQLException("Hóa đơn phải có ít nhất một dịch vụ.");
            }

            String sqlCT = "INSERT INTO ChiTietHoaDon (MaHD, MaDV, SoLuong, DonGia) "
                    + "SELECT ?, MaDV, ?, DonGia FROM DichVu "
                    + "WHERE MaDV = ? AND TrangThai = N'Đang cung cấp'";

            try (PreparedStatement ps = con.prepareStatement(sqlCT)) {
                for (Map.Entry<String, Integer> entry : dsDichVu.entrySet()) {
                    String maDV = entry.getKey();
                    Integer soLuong = entry.getValue();

                    if (maDV == null || maDV.trim().isEmpty() || soLuong == null || soLuong <= 0) {throw new SQLException("Mã dịch vụ hoặc số lượng không hợp lệ.");}

                    ps.setString(1, maHD);
                    ps.setInt(2, soLuong);
                    ps.setString(3, maDV.trim());

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Dịch vụ " + maDV + " không tồn tại hoặc đã ngừng cung cấp.");
                    }
                }
            }

            if (maKM != null && !maKM.trim().isEmpty()) {
                KhuyenMai_DAO khuyenMaiDAO = new KhuyenMai_DAO();
                if (!khuyenMaiDAO.giamSoLuongAtomic(con, maKM.trim())) {
                    throw new SQLException("Khuyến mãi đã hết lượt, hết hạn hoặc không còn áp dụng.");
                }
            }

            String sqlLichHen = "UPDATE LichHen SET TrangThai = N'Hoàn thành' "
                    + "WHERE MaLichHen = ? AND TrangThai = N'Đang chờ khám'";

            try (PreparedStatement ps = con.prepareStatement(sqlLichHen)) {
                ps.setString(1, maLichHen.trim());

                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Không thể cập nhật trạng thái lịch hẹn.");
                }
            }

            con.commit();
            return maHD;

        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.addSuppressed(ex);
                }
            }

            throw new RuntimeException("Lưu hóa đơn thất bại: " + e.getMessage(), e);

        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
// tvan sửa
    // Thống kê doanh thu theo tháng
    public Map<Integer, Double> getDoanhThuTheoThang(int nam) {
        Map<Integer, Double> ketQua = new java.util.LinkedHashMap<>();

        String sql = "SELECT MONTH(hd.NgayLap) AS Thang, " +
                "SUM(ct.SoLuong * ct.DonGia) AS DoanhThu " +
                "FROM HoaDon hd " +
                "JOIN ChiTietHoaDon ct ON hd.MaHD = ct.MaHD " +
                "WHERE hd.TrangThai = N'Đã thanh toán' " +
                "AND hd.NgayLap >= ? AND hd.NgayLap < ? " +
                "GROUP BY MONTH(hd.NgayLap) " +
                "ORDER BY Thang";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(
                    LocalDate.of(nam, 1, 1)));

            ps.setDate(2, java.sql.Date.valueOf(
                    LocalDate.of(nam + 1, 1, 1)));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ketQua.put(
                            rs.getInt("Thang"),
                            rs.getDouble("DoanhThu"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Lỗi thống kê doanh thu theo tháng: " + e.getMessage(), e);
        }

        return ketQua;
    }


    // Thống kê doanh thu theo năm
    public Map<Integer, Double> getDoanhThuTheoNam(
            int tuNam, int denNam) {

        Map<Integer, Double> ketQua = new java.util.LinkedHashMap<>();

        String sql = "SELECT YEAR(hd.NgayLap) AS Nam, " +
                "SUM(ct.SoLuong * ct.DonGia) AS DoanhThu " +
                "FROM HoaDon hd " +
                "JOIN ChiTietHoaDon ct ON hd.MaHD = ct.MaHD " +
                "WHERE hd.TrangThai = N'Đã thanh toán' " +
                "AND hd.NgayLap >= ? AND hd.NgayLap < ? " +
                "GROUP BY YEAR(hd.NgayLap) " +
                "ORDER BY Nam";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(
                    LocalDate.of(tuNam, 1, 1)));

            ps.setDate(2, java.sql.Date.valueOf(
                    LocalDate.of(denNam + 1, 1, 1)));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ketQua.put(
                            rs.getInt("Nam"),
                            rs.getDouble("DoanhThu"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Lỗi thống kê doanh thu theo năm: " + e.getMessage(), e);
        }

        return ketQua;
    }


    // Thống kê doanh thu từng dịch vụ trong khoảng thời gian
    public List<Object[]> getChiTietDoanhThu(
            LocalDate tuNgay, LocalDate denNgay) {

        List<Object[]> danhSach = new ArrayList<>();

        String sql = "SELECT dv.MaDV, dv.TenDV, " +
                "SUM(ct.SoLuong) AS SoLuot, " +
                "SUM(ct.SoLuong * ct.DonGia) AS DoanhThu " +
                "FROM HoaDon hd " +
                "JOIN ChiTietHoaDon ct ON hd.MaHD = ct.MaHD " +
                "JOIN DichVu dv ON ct.MaDV = dv.MaDV " +
                "WHERE hd.TrangThai = N'Đã thanh toán' " +
                "AND hd.NgayLap >= ? " +
                "AND hd.NgayLap < DATEADD(DAY, 1, ?) " +
                "GROUP BY dv.MaDV, dv.TenDV " +
                "ORDER BY DoanhThu DESC";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(tuNgay));
            ps.setDate(2, java.sql.Date.valueOf(denNgay));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    danhSach.add(new Object[]{
                            rs.getString("MaDV"),
                            rs.getString("TenDV"),
                            rs.getInt("SoLuot"),
                            rs.getDouble("DoanhThu")
                    });
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Lỗi lấy chi tiết doanh thu: " + e.getMessage(), e);
        }

        return danhSach;
    }
    // thong ke theo ngay
    public Map<LocalDate, Double> getDoanhThuTheoNgay() throws SQLException {
        Map<LocalDate, Double> ketQua = new LinkedHashMap<>();

        LocalDate ngayKetThuc = LocalDate.now();
        LocalDate ngayBatDau = ngayKetThuc.minusDays(6);

        for (LocalDate ngay = ngayBatDau; !ngay.isAfter(ngayKetThuc); ngay = ngay.plusDays(1)) {
            ketQua.put(ngay, 0.0);
        }

        String sql = """
        SELECT CAST(hd.NgayLap AS DATE) AS Ngay,
               SUM(cthd.SoLuong * cthd.DonGia) AS DoanhThu
        FROM HoaDon hd
        INNER JOIN ChiTietHoaDon cthd ON hd.MaHD = cthd.MaHD
        WHERE hd.TrangThai = N'Đã thanh toán'
          AND hd.NgayLap >= ?
          AND hd.NgayLap < DATEADD(DAY, 1, ?)
        GROUP BY CAST(hd.NgayLap AS DATE)
        ORDER BY Ngay
        """;

        try (PreparedStatement ps = ConnectDB.getConnection().prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(ngayBatDau));
            ps.setDate(2, java.sql.Date.valueOf(ngayKetThuc));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDate ngay = rs.getDate("Ngay").toLocalDate();
                    double doanhThu = rs.getDouble("DoanhThu");
                    ketQua.put(ngay, doanhThu);
                }
            }
        }

        return ketQua;
    }
}
