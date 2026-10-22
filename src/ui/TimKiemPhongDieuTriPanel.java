
        package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
public class TimKiemPhongDieuTriPanel extends JPanel {

    private JTextField txtMaPhong;
    private JTextField txtTenPhong;
    private JTextField txtMaGhe;
    private JTextField txtViTri;

    private JComboBox<String> cboTrangThai;
    private JComboBox<String> cboLoaiPhong;

    private JTable table;
    private DefaultTableModel tableModel;

    private final Color mauNen = new Color(243, 246, 252);
    private final Color mauXanhDam = new Color(35, 65, 95);
    private final Color mauXanh = new Color(35, 120, 230);
    private final Color mauXanhLa = new Color(22, 160, 110);
    private final Color mauVien = new Color(220, 225, 232);

    public TimKiemPhongDieuTriPanel() {

        setLayout(new BorderLayout(0, 0));
        setBackground(mauNen);

        // =============================================
        // 1. TIÊU ĐỀ
        // =============================================

        JPanel pnlTieuDe = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 25, 16));

        pnlTieuDe.setBackground(Color.WHITE);
        pnlTieuDe.setPreferredSize(new Dimension(0, 76));

        JLabel lblTieuDe = new JLabel(
                " Tìm kiếm phòng điều trị");

        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 27));
        lblTieuDe.setForeground(mauXanhDam);

        pnlTieuDe.add(lblTieuDe);

        add(pnlTieuDe, BorderLayout.NORTH);

        // =============================================
        // 2. KHU VỰC NỘI DUNG
        // =============================================

        JPanel pnlNoiDung = new JPanel(
                new BorderLayout(0, 10));

        pnlNoiDung.setBackground(mauNen);

        pnlNoiDung.setBorder(
                BorderFactory.createEmptyBorder(12, 20, 20, 20));

        JPanel pnlDieuKien = taoKhungDieuKien();
        JPanel pnlKetQua = taoKhungKetQua();

        // Thu gọn khung điều kiện tìm kiếm
        pnlDieuKien.setPreferredSize(
                new Dimension(0, 195));

        // Khung kết quả tự động chiếm phần diện tích còn lại
        pnlNoiDung.add(pnlDieuKien, BorderLayout.NORTH);
        pnlNoiDung.add(pnlKetQua, BorderLayout.CENTER);

        add(pnlNoiDung, BorderLayout.CENTER);
    }

    // =============================================
    // 3. KHUNG ĐIỀU KIỆN TÌM KIẾM
    // =============================================

    private JPanel taoKhungDieuKien() {

        JPanel pnlDieuKien = new JPanel(
                new BorderLayout(0, 7));

        pnlDieuKien.setBackground(Color.WHITE);

        pnlDieuKien.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(mauVien),
                        BorderFactory.createEmptyBorder(
                                10, 16, 8, 16)));

        JLabel lblDieuKien = new JLabel(
                "Điều kiện tìm kiếm");

        lblDieuKien.setFont(
                new Font("Arial", Font.BOLD, 20));

        lblDieuKien.setForeground(
                new Color(45, 55, 65));

        pnlDieuKien.add(
                lblDieuKien, BorderLayout.NORTH);

        // Các trường nhập liệu
        txtMaPhong = new JTextField();
        txtTenPhong = new JTextField();
        txtMaGhe = new JTextField();
        txtViTri = new JTextField();

        cboTrangThai = new JComboBox<>(new String[] {
                "Tất cả",
                "Trống",
                "Đang sử dụng",
                "Bảo trì"
        });

        cboLoaiPhong = new JComboBox<>(new String[] {
                "Tất cả",
                "Phòng khám",
                "Phòng điều trị",
                "Phòng phẫu thuật"
        });

        // 3 cột, 2 hàng
        JPanel pnlTruongNhap = new JPanel(
                new GridLayout(2, 3, 14, 6));

        pnlTruongNhap.setBackground(Color.WHITE);

        pnlTruongNhap.add(
                createField("Mã phòng", txtMaPhong));

        pnlTruongNhap.add(
                createField("Tên phòng", txtTenPhong));

        pnlTruongNhap.add(
                createField("Trạng thái", cboTrangThai));

        pnlTruongNhap.add(
                createField("Mã ghế", txtMaGhe));

        pnlTruongNhap.add(
                createField("Vị trí", txtViTri));

        pnlTruongNhap.add(
                createField("Loại phòng", cboLoaiPhong));

        pnlDieuKien.add(
                pnlTruongNhap, BorderLayout.CENTER);

        // Nút tìm kiếm và làm mới
        JPanel pnlNut = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0));

        pnlNut.setBackground(Color.WHITE);

        JButton btnTimKiem = createButton(
                "Tìm kiếm", mauXanh, 150, 36);

        JButton btnLamMoi = createButton(
                "Làm mới", mauXanhLa, 150, 36);

        pnlNut.add(btnTimKiem);
        pnlNut.add(btnLamMoi);

        pnlDieuKien.add(
                pnlNut, BorderLayout.SOUTH);

        btnTimKiem.addActionListener(e -> timKiemPhong());
        btnLamMoi.addActionListener(e -> lamMoi());

        return pnlDieuKien;
    }

    // =============================================
    // 4. KHUNG KẾT QUẢ TÌM KIẾM
    // =============================================

    private JPanel taoKhungKetQua() {

        JPanel pnlKetQua = new JPanel(
                new BorderLayout(0, 12));

        pnlKetQua.setBackground(Color.WHITE);

        pnlKetQua.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(mauVien),
                        BorderFactory.createEmptyBorder(
                                15, 18, 14, 18)));

        JLabel lblKetQua = new JLabel(
                "Kết quả tìm kiếm");

        lblKetQua.setFont(
                new Font("Arial", Font.BOLD, 20));

        lblKetQua.setForeground(
                new Color(45, 55, 65));

        pnlKetQua.add(
                lblKetQua, BorderLayout.NORTH);

        String[] headers = {
                "STT",
                "MÃ PHÒNG",
                "TÊN PHÒNG",
                "MÃ GHẾ",
                "LOẠI PHÒNG",
                "VỊ TRÍ",
                "TRẠNG THÁI"
        };

        tableModel = new DefaultTableModel(headers, 0) {

            @Override
            public boolean isCellEditable(
                    int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        table.setRowHeight(38);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setForeground(new Color(45, 55, 65));
        table.setBackground(Color.WHITE);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        table.setSelectionBackground(
                new Color(215, 232, 250));

        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(mauVien);
        table.setShowGrid(true);
        table.setFillsViewportHeight(true);

        table.setIntercellSpacing(
                new Dimension(1, 1));

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12));

        table.getTableHeader().setBackground(
                new Color(247, 248, 250));

        table.getTableHeader().setForeground(
                new Color(70, 85, 100));

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 45));

        table.getTableHeader().setReorderingAllowed(false);

        // Độ rộng các cột
        table.getColumnModel().getColumn(0)
                .setPreferredWidth(60);

        table.getColumnModel().getColumn(1)
                .setPreferredWidth(120);

        table.getColumnModel().getColumn(2)
                .setPreferredWidth(145);

        table.getColumnModel().getColumn(3)
                .setPreferredWidth(110);

        table.getColumnModel().getColumn(4)
                .setPreferredWidth(130);

        table.getColumnModel().getColumn(5)
                .setPreferredWidth(120);

        table.getColumnModel().getColumn(6)
                .setPreferredWidth(130);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder());

        scrollPane.getViewport().setBackground(Color.WHITE);

        pnlKetQua.add(
                scrollPane, BorderLayout.CENTER);

        // Dòng cuối khung kết quả
        JPanel pnlCuoi = new JPanel(
                new BorderLayout());

        pnlCuoi.setBackground(Color.WHITE);

        JLabel lblHuongDan = new JLabel(
                "Vui lòng chọn một phòng trong bảng");

        lblHuongDan.setFont(
                new Font("Arial", Font.PLAIN, 13));

        lblHuongDan.setForeground(
                new Color(135, 140, 150));

        JButton btnXemChiTiet = createButton(
                "Xem chi tiết", mauXanh, 145, 36);

        btnXemChiTiet.addActionListener(e -> xemChiTiet());

        pnlCuoi.add(
                lblHuongDan, BorderLayout.WEST);

        JPanel pnlNutChiTiet = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 0, 0));

        pnlNutChiTiet.setBackground(Color.WHITE);
        pnlNutChiTiet.add(btnXemChiTiet);

        pnlCuoi.add(
                pnlNutChiTiet, BorderLayout.EAST);

        pnlKetQua.add(
                pnlCuoi, BorderLayout.SOUTH);

        return pnlKetQua;
    }

    // =============================================
    // 5. TẠO TRƯỜNG NHẬP LIỆU
    // =============================================

    private JPanel createField(
            String label, JComponent input) {

        JPanel panel = new JPanel(
                new BorderLayout(0, 4));

        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label);

        lbl.setFont(
                new Font("Arial", Font.BOLD, 13));

        lbl.setForeground(
                new Color(55, 65, 75));

        input.setFont(
                new Font("Arial", Font.PLAIN, 14));

        input.setPreferredSize(
                new Dimension(100, 30));

        if (input instanceof JTextField) {
            input.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(mauVien),
                            BorderFactory.createEmptyBorder(
                                    4, 8, 4, 8)));
        }

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(input, BorderLayout.CENTER);

        return panel;
    }

    // =============================================
    // 6. TẠO NÚT
    // =============================================

    private JButton createButton(
            String text,
            Color background,
            int width,
            int height) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 13));

        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR));

        button.setPreferredSize(
                new Dimension(width, height));

        button.setMargin(
                new Insets(4, 8, 4, 8));

        return button;
    }

    // =============================================
    // 7. TÌM KIẾM DỮ LIỆU MINH HỌA
    // =============================================

    private void timKiemPhong() {

        String maPhong = txtMaPhong.getText()
                .trim().toLowerCase();

        String tenPhong = txtTenPhong.getText()
                .trim().toLowerCase();

        String maGhe = txtMaGhe.getText()
                .trim().toLowerCase();

        String viTri = txtViTri.getText()
                .trim().toLowerCase();

        String trangThai =
                (String) cboTrangThai.getSelectedItem();

        String loaiPhong =
                (String) cboLoaiPhong.getSelectedItem();

        tableModel.setRowCount(0);

        // Dữ liệu minh họa giao diện
        Object[][] duLieuMau = {};

        int stt = 1;

        for (Object[] dong : duLieuMau) {

            String ma = dong[0].toString().toLowerCase();
            String ten = dong[1].toString().toLowerCase();
            String ghe = dong[2].toString().toLowerCase();
            String loai = dong[3].toString();
            String viTriPhong = dong[4].toString().toLowerCase();
            String trangThaiPhong = dong[5].toString();

            boolean hopLe =
                    ma.contains(maPhong)
                            && ten.contains(tenPhong)
                            && ghe.contains(maGhe)
                            && viTriPhong.contains(viTri)
                            && ("Tất cả".equals(trangThai)
                            || trangThaiPhong.equals(trangThai))
                            && ("Tất cả".equals(loaiPhong)
                            || loai.equals(loaiPhong));

            if (hopLe) {
                tableModel.addRow(new Object[] {
                        stt++,
                        dong[0],
                        dong[1],
                        dong[2],
                        dong[3],
                        dong[4],
                        dong[5]
                });
            }
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không tìm thấy phòng điều trị phù hợp.",
                    "Kết quả tìm kiếm",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // =============================================
    // 8. LÀM MỚI
    // =============================================

    private void lamMoi() {

        txtMaPhong.setText("");
        txtTenPhong.setText("");
        txtMaGhe.setText("");
        txtViTri.setText("");

        cboTrangThai.setSelectedIndex(0);
        cboLoaiPhong.setSelectedIndex(0);

        tableModel.setRowCount(0);
        table.clearSelection();
    }

    // =============================================
    // 9. XEM CHI TIẾT
    // =============================================

    private void xemChiTiet() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn một phòng trong bảng!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        int row = table.convertRowIndexToModel(selectedRow);

        String thongTin =
                "Mã phòng: "
                        + tableModel.getValueAt(row, 1)
                        + "\nTên phòng: "
                        + tableModel.getValueAt(row, 2)
                        + "\nMã ghế: "
                        + tableModel.getValueAt(row, 3)
                        + "\nLoại phòng: "
                        + tableModel.getValueAt(row, 4)
                        + "\nVị trí: "
                        + tableModel.getValueAt(row, 5)
                        + "\nTrạng thái: "
                        + tableModel.getValueAt(row, 6);

        JOptionPane.showMessageDialog(
                this,
                thongTin,
                "Chi tiết phòng điều trị",
                JOptionPane.INFORMATION_MESSAGE);
    }
}