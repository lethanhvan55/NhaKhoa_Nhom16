package dao;

import connectDB.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietVatTu_DichVu_DAO {

    // Lấy tất cả vật tư kèm theo số lượng đang dùng của một dịch vụ cụ thể (nếu có)
    public List<Object[]> getDanhSachVatTuVaSuDung(String maDV) {
        List<Object[]> danhSach = new ArrayList<>();
        String sql = "SELECT vt.MaVT, vt.TenVatTu, vt.DonViTinh, vt.SoLuongTon, "
                + "ISNULL(ct.SoLuongSuDung, 0) as SoLuongSuDung, "
                + "CAST(CASE WHEN ct.MaDV IS NOT NULL THEN 1 ELSE 0 END AS BIT) as Chon "
                + "FROM VatTu vt "
                + "LEFT JOIN ChiTietVatTu_DichVu ct ON vt.MaVT = ct.MaVT AND ct.MaDV = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, maDV);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                            rs.getBoolean("Chon"),          // Cột 0: Checkbox chọn
                            rs.getString("MaVT").trim(),    // Cột 1: Mã vật tư
                            rs.getString("TenVatTu"),       // Cột 2: Tên vật tư
                            rs.getString("DonViTinh"),      // Cột 3: ĐVT
                            rs.getInt("SoLuongTon"),        // Cột 4: Tồn kho
                            rs.getInt("SoLuongSuDung")      // Cột 5: Số lượng sử dụng cho dịch vụ
                    };
                    danhSach.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    // Lưu danh sách vật tư của dịch vụ (Xóa cũ, thêm mới các dòng được check)
    public boolean capNhatVatTuChoDichVu(String maDV, List<Object[]> selectedList) {
        Connection con = null;
        try {
            con = ConnectDB.getInstance().getConnection();
            con.setAutoCommit(false); // Bật Transaction

            // 1. Xóa các liên kết cũ của dịch vụ
            String deleteSql = "DELETE FROM ChiTietVatTu_DichVu WHERE MaDV = ?";
            try (PreparedStatement pstDel = con.prepareStatement(deleteSql)) {
                pstDel.setString(1, maDV);
                pstDel.executeUpdate();
            }

            // 2. Thêm lại các dòng được check chọn
            String insertSql = "INSERT INTO ChiTietVatTu_DichVu (MaDV, MaVT, SoLuongSuDung) VALUES (?, ?, ?)";
            try (PreparedStatement pstIns = con.prepareStatement(insertSql)) {
                for (Object[] item : selectedList) {
                    boolean isSelected = (boolean) item[0];
                    if (isSelected) {
                        String maVT = (String) item[1];
                        int soLuong = (int) item[5];

                        pstIns.setString(1, maDV);
                        pstIns.setString(2, maVT);
                        pstIns.setInt(3, soLuong);
                        pstIns.addBatch();
                    }
                }
                pstIns.executeBatch();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }
}