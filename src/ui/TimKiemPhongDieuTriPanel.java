package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class TimKiemPhongDieuTriPanel extends JPanel{
        private JTextField txtMaPhong;
        private JTextField txtTenPhong;
        private JTextField txtMaGhe;
        private JTextField txtViTri;

        private JComboBox<String> cboTrangThai;
        private JComboBox<String> cboLoaiPhong;

        private JTable table;
        private DefaultTableModel tableModel;

        private Color mauNen = new Color(243, 246, 252);
        private Color mauXanhDam = new Color(35, 65, 95);
        private Color mauXanh = new Color(35, 120, 230);
        private Color mauVien = new Color(220, 225, 232);

        public TimKiemPhongDieuTriPanel() {

            setLayout(new BorderLayout(0, 0));
            setBackground(mauNen);

            // =============================
            // 1. TIÊU ĐỀ
            // ==============================

            JPanel pnlTieuDe = new JPanel(
                    new FlowLayout(FlowLayout.LEFT, 25, 22)
            );

            pnlTieuDe.setBackground(Color.WHITE);
            pnlTieuDe.setPreferredSize(new Dimension(0, 87));

            JLabel lblTieuDe = new JLabel(
                    "  Tìm kiếm phòng điều trị"
            );

            lblTieuDe.setFont(
                    new Font("Arial", Font.BOLD, 27)
            );

            lblTieuDe.setForeground(mauXanhDam);

            pnlTieuDe.add(lblTieuDe);

            add(pnlTieuDe, BorderLayout.NORTH);

            // ==============================
            // 2. KHU VỰC NỘI DUNG
            // ==============================

            JPanel pnlNoiDung = new JPanel(
                    new BorderLayout(0, 20)
            );

            pnlNoiDung.setBackground(mauNen);

            pnlNoiDung.setBorder(
                    BorderFactory.createEmptyBorder(22, 24, 24, 24)
            );

            // ==============================
            // 3. ĐIỀU KIỆN TÌM KIẾM
            // ==============================

            JPanel pnlDieuKien = new JPanel(
                    new BorderLayout(0, 15)
            );

            pnlDieuKien.setBackground(Color.WHITE);

            pnlDieuKien.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(mauVien),
                            BorderFactory.createEmptyBorder(18, 20, 18, 20)
                    )
            );

            JLabel lblDieuKien = new JLabel(
                    "Điều kiện tìm kiếm"
            );

            lblDieuKien.setFont(
                    new Font("Arial", Font.BOLD, 20)
            );

            lblDieuKien.setForeground(new Color(45, 55, 65));

            pnlDieuKien.add(
                    lblDieuKien,
                    BorderLayout.NORTH
            );

            // Các ô nhập thông tin
            txtMaPhong = new JTextField();
            txtTenPhong = new JTextField();
            txtMaGhe = new JTextField();
            txtViTri = new JTextField();

            cboLoaiPhong = new JComboBox<>(new String[]{
                    "Tất cả",
                    "Phòng khám",
                    "Phòng điều trị",
                    "Phòng phẫu thuật"
            });

            cboTrangThai = new JComboBox<>(new String[]{
                    "Tất cả",
                    "Trống",
                    "Đang sử dụng",
                    "Bảo trì"
            });

            // Tạo khu vực nhập liệu 3 cột, 2 hàng
            JPanel pnlTruongNhap = new JPanel(
                    new GridLayout(2, 3, 17, 15)
            );

            pnlTruongNhap.setBackground(Color.WHITE);

            pnlTruongNhap.add(
                    createField("Mã phòng", txtMaPhong)
            );

            pnlTruongNhap.add(
                    createField("Tên phòng", txtTenPhong)
            );

            pnlTruongNhap.add(
                    createField("Trạng thái", cboTrangThai)
            );

            pnlTruongNhap.add(
                    createField("Mã ghế", txtMaGhe)
            );

            pnlTruongNhap.add(
                    createField("Vị trí", txtViTri)
            );

            pnlTruongNhap.add(
                    createField("Loại phòng", cboLoaiPhong)
            );

            pnlDieuKien.add(
                    pnlTruongNhap,
                    BorderLayout.CENTER
            );

            // Các nút tìm kiếm và làm mới
            JPanel pnlNut = new JPanel(
                    new FlowLayout(FlowLayout.RIGHT, 12, 0)
            );

            pnlNut.setBackground(Color.WHITE);

            JButton btnTimKiem = createButton(
                    " Tìm kiếm",
                    mauXanh,
                    165,
                    48
            );

            JButton btnLamMoi = createButton(
                    " Làm mới",
                    Color.WHITE,
                    165,
                    48
            );

            btnLamMoi.setForeground(new Color(45, 55, 65));
            btnLamMoi.setBorder(
                    BorderFactory.createLineBorder(mauVien)
            );

            pnlNut.add(btnTimKiem);
            pnlNut.add(btnLamMoi);

            pnlDieuKien.add(
                    pnlNut,
                    BorderLayout.SOUTH
            );

            // ==============================
            // 4. KẾT QUẢ TÌM KIẾM
            // ==============================

            JPanel pnlKetQua = new JPanel(
                    new BorderLayout(0, 18)
            );

            pnlKetQua.setBackground(Color.WHITE);

            pnlKetQua.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(mauVien),
                            BorderFactory.createEmptyBorder(18, 20, 18, 20)
                    )
            );

            JLabel lblKetQua = new JLabel(
                    "Kết quả tìm kiếm"
            );

            lblKetQua.setFont(
                    new Font("Arial", Font.BOLD, 20)
            );

            lblKetQua.setForeground(new Color(45, 55, 65));

            pnlKetQua.add(
                    lblKetQua,
                    BorderLayout.NORTH
            );

            // Các cột của bảng
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
                        int row,
                        int column
                ) {
                    return false;
                }
            };

            table = new JTable(tableModel);

            table.setRowHeight(38);

            table.setFont(
                    new Font("Arial", Font.PLAIN, 13)
            );

            table.setForeground(new Color(45, 55, 65));

            table.setBackground(Color.WHITE);

            table.setSelectionBackground(
                    new Color(215, 232, 250)
            );

            table.setSelectionForeground(Color.BLACK);

            table.setGridColor(mauVien);

            table.setShowGrid(true);

            table.setFillsViewportHeight(true);

            table.setIntercellSpacing(
                    new Dimension(1, 1)
            );

            table.getTableHeader().setFont(
                    new Font("Arial", Font.BOLD, 12)
            );

            table.getTableHeader().setBackground(
                    new Color(247, 248, 250)
            );

            table.getTableHeader().setForeground(
                    new Color(90, 100, 110)
            );

            table.getTableHeader().setPreferredSize(
                    new Dimension(0, 48)
            );

            table.getTableHeader().setReorderingAllowed(false);

            JScrollPane scrollPane = new JScrollPane(table);

            scrollPane.setBorder(
                    BorderFactory.createEmptyBorder()
            );

            scrollPane.getViewport().setBackground(Color.WHITE);

            pnlKetQua.add(
                    scrollPane,
                    BorderLayout.CENTER
            );

            // ==============================
            // 5. DÒNG GHI CHÚ VÀ NÚT CHI TIẾT
            // ==============================

            JPanel pnlCuoi = new JPanel(
                    new BorderLayout()
            );

            pnlCuoi.setBackground(Color.WHITE);

            JLabel lblHuongDan = new JLabel(
                    "Vui lòng chọn một phòng trong bảng"
            );

            lblHuongDan.setFont(
                    new Font("Arial", Font.PLAIN, 13)
            );

            lblHuongDan.setForeground(
                    new Color(135, 140, 150)
            );

            JButton btnXemChiTiet = createButton(
                    "  Xem chi tiết",
                    mauXanh,
                    150,
                    36
            );

            JPanel pnlNutChiTiet = new JPanel(
                    new FlowLayout(FlowLayout.RIGHT, 0, 0)
            );

            pnlNutChiTiet.setBackground(Color.WHITE);
            pnlNutChiTiet.add(btnXemChiTiet);

            pnlCuoi.add(
                    lblHuongDan,
                    BorderLayout.WEST
            );

            pnlCuoi.add(
                    pnlNutChiTiet,
                    BorderLayout.EAST
            );

            pnlKetQua.add(
                    pnlCuoi,
                    BorderLayout.SOUTH
            );

            // ==============================
            // 6. GHÉP GIAO DIỆN
            // ==============================

            pnlNoiDung.add(
                    pnlDieuKien,
                    BorderLayout.NORTH
            );

            pnlNoiDung.add(
                    pnlKetQua,
                    BorderLayout.CENTER
            );

            add(
                    pnlNoiDung,
                    BorderLayout.CENTER
            );
        }

        // Tạo ô nhập liệu và nhãn
        private JPanel createField(
                String label,
                JComponent input
        ) {

            JPanel panel = new JPanel(
                    new BorderLayout(0, 7)
            );

            panel.setBackground(Color.WHITE);

            JLabel lbl = new JLabel(label);

            lbl.setFont(
                    new Font("Arial", Font.BOLD, 13)
            );

            lbl.setForeground(
                    new Color(55, 65, 75)
            );

            input.setFont(
                    new Font("Arial", Font.PLAIN, 14)
            );

            input.setPreferredSize(
                    new Dimension(100, 39)
            );

            if (input instanceof JTextField) {
                input.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(mauVien),
                                BorderFactory.createEmptyBorder(5, 8, 5, 8)
                        )
                );
            }

            panel.add(lbl, BorderLayout.NORTH);
            panel.add(input, BorderLayout.CENTER);

            return panel;
        }

        // Tạo nút
        private JButton createButton(
                String text,
                Color background,
                int width,
                int height
        ) {

            JButton button = new JButton(text);

            button.setFont(
                    new Font("Arial", Font.BOLD, 13)
            );

            button.setForeground(Color.WHITE);
            button.setBackground(background);

            button.setFocusPainted(false);
            button.setOpaque(true);
            button.setBorderPainted(false);

            button.setPreferredSize(
                    new Dimension(width, height)
            );

            button.setMargin(
                    new Insets(5, 10, 5, 10)
            );

            return button;
        }
    }
