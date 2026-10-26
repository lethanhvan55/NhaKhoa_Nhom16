package dao;

import connectDB.ConnectDB;
import model.BenhNhan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BenhNhan_DAO {
    public List<BenhNhan> getAll() {
        List<BenhNhan> ds = new ArrayList<>();
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "SELECT * FROM BenhNhan";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String maBN = rs.getString("MaBenhNhan");
                String hoTen = rs.getString("HoTen");
                BenhNhan bn = new BenhNhan();
                bn.setMaBenhNhan(maBN);
                bn.setHoTen(hoTen);
                // bn.setNgaySinh(rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toLocalDate() : null);
                // bn.setGioiTinh(rs.getBoolean("GioiTinh"));
                // bn.setSoDT(rs.getString("SoDT"));
                // bn.setEmail(rs.getString("Email"));
                // bn.setDiaChi(rs.getString("DiaChi"));

                ds.add(bn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}