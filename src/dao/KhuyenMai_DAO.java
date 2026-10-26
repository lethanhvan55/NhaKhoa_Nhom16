package dao;

import connectDB.ConnectDB; 
import model.KhuyenMai;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMai_DAO {
    public List<KhuyenMai> getAllKhuyenMai() {
        List<KhuyenMai> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai";
        
        try {
            Connection con = ConnectDB.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String maKM = rs.getString("MaKM");
                String tenKM = rs.getString("TenKM");
                Date ngayBatDau = rs.getDate("NgayBatDau");
                Date ngayKetThuc = rs.getDate("NgayKetThuc");
                String dieuKien = rs.getString("DieuKien");
                double chietKhau = rs.getDouble("ChietKhau");
                String trangThai = rs.getString("TrangThai");

                KhuyenMai km = new KhuyenMai(maKM, tenKM, ngayBatDau, ngayKetThuc, 
                                             dieuKien, chietKhau, trangThai);
                danhSach.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public boolean create(KhuyenMai km) {
        String sql = "INSERT INTO KhuyenMai (MaKM, TenKM, NgayBatDau, NgayKetThuc, DieuKien, ChietKhau, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            pstmt.setString(1, km.getMaKM());
            pstmt.setString(2, km.getTenKM());
            pstmt.setDate(3, new Date(km.getNgayBatDau().getTime()));
            pstmt.setDate(4, new Date(km.getNgayKetThuc().getTime()));
            pstmt.setString(5, km.getDieuKien()); 
            pstmt.setDouble(6, km.getChietKhau());
            pstmt.setString(7, km.getTrangThai());

            int n = pstmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean update(KhuyenMai km) {
        String sql = "UPDATE KhuyenMai SET TenKM = ?, NgayBatDau = ?, NgayKetThuc = ?, DieuKien = ?, ChietKhau = ?, TrangThai = ? WHERE MaKM = ?";
        
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            pstmt.setString(1, km.getTenKM());
            pstmt.setDate(2, new Date(km.getNgayBatDau().getTime()));
            pstmt.setDate(3, new Date(km.getNgayKetThuc().getTime()));
            pstmt.setString(4, km.getDieuKien()); 
            pstmt.setDouble(5, km.getChietKhau());
            pstmt.setString(6, km.getTrangThai());
            pstmt.setString(7, km.getMaKM());

            int n = pstmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean delete(String maKM) {
        String sql = "DELETE FROM KhuyenMai WHERE MaKM = ?";
        
        try {
			Connection con = ConnectDB.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maKM);

            int n = pstmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<KhuyenMai> timKiemKhuyenMai(String tuKhoa) {
        List<KhuyenMai> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai WHERE MaKM LIKE ?";
        
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            String searchPattern = "%" + tuKhoa + "%";
            pstmt.setString(1, searchPattern);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String maKM = rs.getString("MaKM");
                String tenKM = rs.getString("TenKM");
                Date ngayBatDau = rs.getDate("NgayBatDau");
                Date ngayKetThuc = rs.getDate("NgayKetThuc");
                String dieuKien = rs.getString("DieuKien"); 
                double chietKhau = rs.getDouble("ChietKhau");
                String trangThai = rs.getString("TrangThai");

                KhuyenMai km = new KhuyenMai(maKM, tenKM, ngayBatDau, ngayKetThuc, 
                                             dieuKien, chietKhau, trangThai);
                danhSach.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}