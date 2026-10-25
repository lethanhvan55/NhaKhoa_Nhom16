package dao;

import connectDB.ConnectDB;
import model.DichVu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}