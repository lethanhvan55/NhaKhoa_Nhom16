package dao;

import connectDB.ConnectDB;
import model.DichVu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class DichVu_DAO {
	public List<DichVu> getAllDichVu() {
        List<DichVu> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM DichVu";        
        try (Connection con = ConnectDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String maDV = rs.getString("MaDV");
                String tenDV = rs.getString("TenDV");
                double donGia = rs.getDouble("DonGia");
                String thoiGianDuKien = rs.getString("ThoiGianDuKien");
                String trangThai = rs.getString("TrangThai");

                DichVu dv = new DichVu(maDV, tenDV, donGia, thoiGianDuKien, trangThai);
                danhSach.add(dv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public boolean create(DichVu dv) {
        String sql = "INSERT INTO DichVu (MaDV, TenDV, DonGia, ThoiGianDuKien, TrangThai) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, dv.getMaDV());
            pstmt.setString(2, dv.getTenDV());
            pstmt.setDouble(3, dv.getDonGia());
            pstmt.setString(4, dv.getThoiGianDuKien());
            pstmt.setString(5, dv.getTrangThai());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(DichVu dv) {
        String sql = "UPDATE DichVu SET TenDV = ?, DonGia = ?, ThoiGianDuKien = ?, TrangThai = ? WHERE MaDV = ?";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, dv.getTenDV());
            pstmt.setDouble(2, dv.getDonGia());
            pstmt.setString(3, dv.getThoiGianDuKien());
            pstmt.setString(4, dv.getTrangThai());
            pstmt.setString(5, dv.getMaDV());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maDV) {
        String sql = "DELETE FROM DichVu WHERE MaDV = ?";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, maDV);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<DichVu> timKiemNangCao(String maDV, String tenDV, String trangThai, Double giaTu, Double giaDen) {
        List<DichVu> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM DichVu");
        List<Object> params = new ArrayList<>();
        List<String> dieuKien = new ArrayList<>();

        if (maDV != null && !maDV.trim().isEmpty()) {
            dieuKien.add("MaDV LIKE ?");
            params.add("%" + maDV.trim() + "%");
        }
        if (tenDV != null && !tenDV.trim().isEmpty()) {
            dieuKien.add("TenDV LIKE ?");
            params.add("%" + tenDV.trim() + "%");
        }
        if (trangThai != null && !trangThai.equals("Tất cả") && !trangThai.trim().isEmpty()) {
            dieuKien.add("TrangThai = ?");
            params.add(trangThai.trim());
        }
        if (giaTu != null) {
            dieuKien.add("DonGia >= ?");
            params.add(giaTu);
        }
        if (giaDen != null) {
            dieuKien.add("DonGia <= ?");
            params.add(giaDen);
        }
        if (dieuKien.isEmpty()) {
            return danhSach; 
        }
        sql.append(" WHERE ").append(String.join(" OR ", dieuKien));

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("MaDV");
                    String name = rs.getString("TenDV");
                    double donGia = rs.getDouble("DonGia");
                    String thoiGian = rs.getString("ThoiGianDuKien");
                    String status = rs.getString("TrangThai");

                    DichVu dv = new DichVu(id, name, donGia, thoiGian, status);
                    danhSach.add(dv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
    
    public List<Map<String, Object>> getThongKeDichVu(
            String kieuTK, String trangThai, Date ngayBatDau, Date ngayKetThuc) {

        List<Map<String, Object>> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT dv.MaDV, dv.TenDV, ")
           .append("       COALESCE(SUM(CASE WHEN hd.MaHD IS NOT NULL THEN ct.SoLuong ELSE 0 END), 0) AS SoLuotDung, ")
           .append("       COALESCE(SUM(CASE WHEN hd.MaHD IS NOT NULL THEN CAST(ct.SoLuong AS DECIMAL(18,2)) * ct.DonGia ELSE 0 END), 0) AS DoanhThu ")
           .append("FROM DichVu dv ")
           .append("LEFT JOIN ChiTietHoaDon ct ON ct.MaDV = dv.MaDV ")
           .append("LEFT JOIN HoaDon hd ON hd.MaHD = ct.MaHD ")
           .append("    AND hd.TrangThai = N'Đã thanh toán' ")
           .append("    AND hd.NgayLap >= ? AND hd.NgayLap < ? ")
           .append("WHERE 1 = 1 ");

        if (trangThai != null && !trangThai.trim().isEmpty()
                && !"Tất cả trạng thái".equals(trangThai)) {
            sql.append("AND dv.TrangThai = ? ");
        }

        sql.append("GROUP BY dv.MaDV, dv.TenDV ")
           .append("ORDER BY SoLuotDung DESC, DoanhThu DESC, dv.TenDV ASC");

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            ps.setTimestamp(1, new Timestamp(ngayBatDau.getTime()));
            ps.setTimestamp(2, new Timestamp(ngayKetThuc.getTime()));
            if (trangThai != null && !trangThai.trim().isEmpty()
                    && !"Tất cả trạng thái".equals(trangThai)) {
                ps.setNString(3, trangThai);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("MaDV", rs.getString("MaDV").trim());
                    row.put("TenDV", rs.getString("TenDV"));
                    row.put("SoLuotDung", rs.getInt("SoLuotDung"));
                    row.put("DoanhThu", rs.getBigDecimal("DoanhThu").doubleValue());
                    danhSach.add(row);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Không thể truy vấn thống kê dịch vụ: " + e.getMessage(), e);
        }
        return danhSach;
    }
    public List<Map<String, Object>> getThongKeDichVu(String kieuTK, String trangThai) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date end = (Date) cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date start = (Date) cal.getTime();
        return getThongKeDichVu(kieuTK, trangThai,
                new Date(((Date) end).getTime()),
                new Date(((Date) start).getTime()));
    }
}
