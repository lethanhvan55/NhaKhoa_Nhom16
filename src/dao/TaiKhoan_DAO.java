package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import model.NhanVien;
import model.TaiKhoan;

public class TaiKhoan_DAO {

    /**
     * Kiểm tra thông tin đăng nhập
     */
    public TaiKhoan checkLogin(String username, String password) {
        TaiKhoan tk = null;
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT maNV, tenDangNhap, matKhau, quyen, trangThai "
                    + "FROM TaiKhoan "
                    + "WHERE tenDangNhap = ? AND matKhau = ? AND trangThai = N'Hoạt động'";

            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();

            if (rs.next()) {
                tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("tenDangNhap").trim());
                tk.setMatKhau(rs.getString("matKhau").trim());
                tk.setQuyen(rs.getString("quyen").trim());
                tk.setTrangThai(rs.getString("trangThai").trim());

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("maNV").trim());
                tk.setMaNV(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return tk;
    }

    /**
     * Lấy toàn bộ danh sách tài khoản đổ lên bảng
     */
    public List<TaiKhoan> getAllTaiKhoan() {
        List<TaiKhoan> danhSach = new ArrayList<>();
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "SELECT maNV, tenDangNhap, matKhau, quyen, trangThai FROM TaiKhoan";

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("tenDangNhap").trim());
                tk.setMatKhau(rs.getString("matKhau") != null ? rs.getString("matKhau").trim() : "");
                tk.setQuyen(rs.getString("quyen") != null ? rs.getString("quyen").trim() : "");
                tk.setTrangThai(rs.getString("trangThai") != null ? rs.getString("trangThai").trim() : "");

                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("maNV") != null ? rs.getString("maNV").trim() : "");
                tk.setMaNV(nv);

                danhSach.add(tk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    /**
     * Thêm tài khoản mới
     */
    public boolean themTaiKhoan(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (tenDangNhap, matKhau, maNV, quyen, trangThai) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, tk.getTenDangNhap());
            pst.setString(2, tk.getMatKhau());

            if (tk.getMaNV() != null && tk.getMaNV().getMaNV() != null && !tk.getMaNV().getMaNV().isEmpty()) {
                pst.setString(3, tk.getMaNV().getMaNV());
            } else {
                pst.setNull(3, java.sql.Types.VARCHAR);
            }

            pst.setString(4, tk.getQuyen());
            pst.setString(5, tk.getTrangThai());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật thông tin tài khoản
     */
    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET matKhau = ?, maNV = ?, quyen = ?, trangThai = ? WHERE tenDangNhap = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, tk.getMatKhau());

            if (tk.getMaNV() != null && tk.getMaNV().getMaNV() != null && !tk.getMaNV().getMaNV().isEmpty()) {
                pst.setString(2, tk.getMaNV().getMaNV());
            } else {
                pst.setNull(2, java.sql.Types.VARCHAR);
            }

            pst.setString(3, tk.getQuyen());
            pst.setString(4, tk.getTrangThai());
            pst.setString(5, tk.getTenDangNhap());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa tài khoản theo tên đăng nhập
     */
    public boolean xoaTaiKhoan(String tenDangNhap) {
        String sql = "DELETE FROM TaiKhoan WHERE tenDangNhap = ?";
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, tenDangNhap);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}