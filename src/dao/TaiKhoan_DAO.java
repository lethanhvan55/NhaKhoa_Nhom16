package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connectDB.ConnectDB;
import model.NhanVien;
import model.TaiKhoan;

public class TaiKhoan_DAO {

    /**
     * Kiểm tra thông tin đăng nhập
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return Đối tượng TaiKhoan nếu đúng, null nếu sai
     */
    public TaiKhoan checkLogin(String username, String password) {
        TaiKhoan tk = null;
        Connection con = ConnectDB.getInstance().getConnection(); // Hoặc ConnectDB.getConnection() tùy theo cách viết class ConnectDB của bạn
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Khớp với cấu trúc bảng TaiKhoan trong QL_NK (trangThai là NVARCHAR)
            String sql = "SELECT maNV, tenDangNhap, matKhau, quyen, trangThai "
                       + "FROM TaiKhoan "
                       + "WHERE tenDangNhap = ? AND matKhau = ? AND trangThai = N'Hoạt động'";
                       
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();

            if (rs.next()) {
                tk = new TaiKhoan();
           //     tk.setMaNV(rs.getString("maNV")); 
                tk.setTenDangNhap(rs.getString("tenDangNhap"));
                tk.setMatKhau(rs.getString("matKhau"));
                tk.setQuyen(rs.getString("quyen"));
                tk.setTrangThai(rs.getString("trangThai")); 
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
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
}