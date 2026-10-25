package dao;

import model.VatTu;
import connectDB.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VatTuDAO {

    // 1. Lấy toàn bộ danh sách vật tư (SELECT ALL)
    public List<VatTu> getAll() throws SQLException {
        List<VatTu> danhSach = new ArrayList<>();

        String sql = "SELECT MaVT, TenVatTu, DonViTinh, SoLuongTon, "
                + "SoLuongToiThieu, HanSuDung, HinhAnh, MoTa "
                + "FROM VatTu";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                VatTu vt = new VatTu();

                vt.setMaVT(rs.getString("MaVT"));
                vt.setTenVatTu(rs.getString("TenVatTu"));
                vt.setDonViTinh(rs.getString("DonViTinh"));
                vt.setSoLuongTon(rs.getInt("SoLuongTon"));
                vt.setSoLuongToiThieu(rs.getInt("SoLuongToiThieu"));

                Date hanSuDung = rs.getDate("HanSuDung");
                if (hanSuDung != null) {
                    vt.setHanSuDung(hanSuDung.toLocalDate());
                } else {
                    vt.setHanSuDung(null);
                }

                vt.setHinhAnh(rs.getString("HinhAnh"));
                vt.setMoTa(rs.getString("MoTa"));

                danhSach.add(vt);
            }
        }

        return danhSach;
    }

    // 2. Tìm vật tư theo mã (SELECT BY ID)
    public VatTu getById(String maVT) throws SQLException {
        String sql = "SELECT MaVT, TenVatTu, DonViTinh, SoLuongTon, "
                + "SoLuongToiThieu, HanSuDung, HinhAnh, MoTa "
                + "FROM VatTu WHERE MaVT = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maVT);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    VatTu vt = new VatTu();

                    vt.setMaVT(rs.getString("MaVT"));
                    vt.setTenVatTu(rs.getString("TenVatTu"));
                    vt.setDonViTinh(rs.getString("DonViTinh"));
                    vt.setSoLuongTon(rs.getInt("SoLuongTon"));
                    vt.setSoLuongToiThieu(rs.getInt("SoLuongToiThieu"));

                    Date hanSuDung = rs.getDate("HanSuDung");
                    vt.setHanSuDung(
                            hanSuDung != null ? hanSuDung.toLocalDate() : null
                    );

                    vt.setHinhAnh(rs.getString("HinhAnh"));
                    vt.setMoTa(rs.getString("MoTa"));

                    return vt;
                }
            }
        }

        return null;
    }

    // 3. Thêm mới vật tư (INSERT)
    public boolean insert(VatTu vt) throws SQLException {
        String sql = "INSERT INTO VatTu "
                + "(MaVT, TenVatTu, DonViTinh, SoLuongTon, "
                + "SoLuongToiThieu, HanSuDung, HinhAnh, MoTa) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vt.getMaVT());
            ps.setString(2, vt.getTenVatTu());
            ps.setString(3, vt.getDonViTinh());
            ps.setInt(4, vt.getSoLuongTon());
            ps.setInt(5, vt.getSoLuongToiThieu());

            if (vt.getHanSuDung() != null) {
                ps.setDate(6, Date.valueOf(vt.getHanSuDung()));
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }

            ps.setString(7, vt.getHinhAnh());
            ps.setString(8, vt.getMoTa());

            return ps.executeUpdate() > 0;
        }
    }

    // 4. Cập nhật thông tin vật tư (UPDATE)
    public boolean update(VatTu vt) throws SQLException {
        String sql = "UPDATE VatTu SET "
                + "TenVatTu = ?, DonViTinh = ?, SoLuongTon = ?, "
                + "SoLuongToiThieu = ?, HanSuDung = ?, "
                + "HinhAnh = ?, MoTa = ? "
                + "WHERE MaVT = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vt.getTenVatTu());
            ps.setString(2, vt.getDonViTinh());
            ps.setInt(3, vt.getSoLuongTon());
            ps.setInt(4, vt.getSoLuongToiThieu());

            if (vt.getHanSuDung() != null) {
                ps.setDate(5, Date.valueOf(vt.getHanSuDung()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }

            ps.setString(6, vt.getHinhAnh());
            ps.setString(7, vt.getMoTa());
            ps.setString(8, vt.getMaVT());

            return ps.executeUpdate() > 0;
        }
    }

    // 5. Xóa vật tư theo mã (DELETE)
    public boolean delete(String maVT) throws SQLException {
        String sql = "DELETE FROM VatTu WHERE MaVT = ?";

        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maVT);

            return ps.executeUpdate() > 0;
        }
    }
    public List<VatTu> timKiem(String ma, String ten, String danhMuc, String trangThai) throws SQLException {
        List<VatTu> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT MaVT, TenVatTu, DonViTinh, SoLuongTon, SoLuongToiThieu, HanSuDung, HinhAnh, MoTa FROM VatTu WHERE 1=1");

        if (ma != null && !ma.trim().isEmpty()) {
            sql.append(" AND MaVT LIKE ?");
        }
        if (ten != null && !ten.trim().isEmpty()) {
            sql.append(" AND TenVatTu LIKE ?");
        }
        if (danhMuc != null && !danhMuc.equals("Tất cả danh mục")) {
            sql.append(" AND DonViTinh = ?");
        }

        try (Connection con = connectDB.ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int index = 1;
            if (ma != null && !ma.trim().isEmpty()) {
                ps.setString(index++, "%" + ma.trim() + "%");
            }
            if (ten != null && !ten.trim().isEmpty()) {
                ps.setString(index++, "%" + ten.trim() + "%");
            }
            if (danhMuc != null && !danhMuc.equals("Tất cả danh mục")) {
                ps.setString(index++, danhMuc);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VatTu vt = new VatTu();
                    vt.setMaVT(rs.getString("MaVT"));
                    vt.setTenVatTu(rs.getString("TenVatTu"));
                    vt.setDonViTinh(rs.getString("DonViTinh"));
                    vt.setSoLuongTon(rs.getInt("SoLuongTon"));
                    vt.setSoLuongToiThieu(rs.getInt("SoLuongToiThieu"));
                    java.sql.Date hsd = rs.getDate("HanSuDung");
                    vt.setHanSuDung(hsd != null ? hsd.toLocalDate() : null);
                    vt.setHinhAnh(rs.getString("HinhAnh"));
                    vt.setMoTa(rs.getString("MoTa"));

                    // Xác định trạng thái kho
                    int ton = vt.getSoLuongTon();
                    int min = vt.getSoLuongToiThieu();
                    String tt = "Còn hàng";
                    if (ton <= 0) {
                        tt = "Hết hàng";
                    } else if (ton <= min) {
                        tt = "Tồn thấp";
                    }

                    // Lọc theo trạng thái kho chọn trên giao diện
                    if (trangThai == null || trangThai.equals("Tất cả") || tt.equalsIgnoreCase(trangThai)) {
                        danhSach.add(vt);
                    }
                }
            }
        }
        return danhSach;
    }
}


