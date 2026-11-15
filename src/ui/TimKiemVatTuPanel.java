
package ui;

import dao.VatTuDAO;
import model.VatTu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class TimKiemVatTuPanel extends JPanel implements ActionListener {

    private final Color NAVY = new Color(35, 68, 105);
    private final Color BLUE = new Color(35, 119, 224);
    private final Color BG = new Color(243, 246, 251);
    private final Color TEXT = new Color(45, 55, 68);

    // Các ô nhập liệu
    private JTextField txtTuKhoa;
    private JTextField txtGiaTu;
    private JTextField txtGiaDen;

    private JComboBox<String> cboDanhMuc;
    private JComboBox<String> cboTrangThai;

    private JTable tblVatTu;
    private DefaultTableModel model;

    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnXemChiTiet;

    private VatTuDAO vtDAO = new VatTuDAO();
    private DecimalFormat currencyFormatter =
            new DecimalFormat("#,### VNĐ");

    public TimKiemVatTuPanel() {

        setLayout(new BorderLayout());
        setBackground(BG);

        JPanel pnlChinh = new JPanel(new BorderLayout());
        pnlChinh.setBackground(BG);

        // ================= TIÊU ĐỀ =================

        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setBackground(Color.WHITE);
        pnlTieuDe.setBorder(
                new EmptyBorder(17, 22, 17, 24)
        );

        JLabel lblTieuDe = new JLabel("Tìm kiếm vật tư");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));
        lblTieuDe.setForeground(NAVY);
        lblTieuDe.setIcon(
                FontIcon.of(FontAwesomeSolid.SEARCH, 22, NAVY)
        );

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlChinh.add(pnlTieuDe, BorderLayout.NORTH);

        // ================= NỘI DUNG CHÍNH =================

        JPanel pnlNoiDung = new JPanel(
                new BorderLayout(0, 18)
        );

        pnlNoiDung.setBackground(BG);
        pnlNoiDung.setBorder(
                new EmptyBorder(18, 18, 18, 18)
        );

        // ================= KHUNG TÌM KIẾM =================

        JPanel pnlTimKiem = new JPanel(
                new BorderLayout(0, 16)
        );

        pnlTimKiem.setBackground(Color.WHITE);
        pnlTimKiem.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(226, 231, 238)),
                        new EmptyBorder(16, 16, 16, 16)
                )
        );

        JLabel lblDieuKien = new JLabel("Điều kiện tìm kiếm");
        lblDieuKien.setFont(
                new Font("Arial", Font.BOLD, 17)
        );
        lblDieuKien.setForeground(TEXT);

        pnlTimKiem.add(lblDieuKien, BorderLayout.NORTH);

        // ================= CÁC Ô TÌM KIẾM =================
        // Đưa toàn bộ 5 ô lên cùng một hàng

        JPanel pnlKhungTimKiem = new JPanel(
                new GridLayout(1, 5, 14, 0)
        );

        pnlKhungTimKiem.setBackground(Color.WHITE);

        // Khởi tạo các ô nhập liệu

        txtTuKhoa = new JTextField();

        cboDanhMuc = new JComboBox<>(
                new String[]{
                        "Tất cả danh mục",
                        "Hộp",
                        "Cái",
                        "Bộ",
                        "Lọ",
                        "Ống",
                        "Thỏi",
                        "Tuýp",
                        "Gói"
                }
        );

        cboTrangThai = new JComboBox<>(
                new String[]{
                        "Tất cả",
                        "Còn hàng",
                        "Tồn thấp",
                        "Hết hàng"
                }
        );

        txtGiaTu = new JTextField();
        txtGiaDen = new JTextField();

        // Định dạng ô nhập liệu

        dinhDangO(txtTuKhoa);
        dinhDangO(txtGiaTu);
        dinhDangO(txtGiaDen);

        dinhDangCombobox(cboDanhMuc);
        dinhDangCombobox(cboTrangThai);

        // Thêm 5 trường tìm kiếm vào cùng một hàng

        pnlKhungTimKiem.add(
                taoPanelTruong("Mã / Tên vật tư", txtTuKhoa)
        );

        pnlKhungTimKiem.add(
                taoPanelTruong("Danh mục vật tư", cboDanhMuc)
        );

        pnlKhungTimKiem.add(
                taoPanelTruong("Trạng thái kho", cboTrangThai)
        );

        pnlKhungTimKiem.add(
                taoPanelTruong("Giá từ (VNĐ)", txtGiaTu)
        );

        pnlKhungTimKiem.add(
                taoPanelTruong("Giá đến (VNĐ)", txtGiaDen)
        );

        // ================= CÁC NÚT CHỨC NĂNG =================
        // Căn giữa hai nút giống giao diện tìm kiếm nhân viên

        JPanel pnlNut = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 24, 0)
        );

        pnlNut.setBackground(Color.WHITE);

        // Nút tìm kiếm

        btnTimKiem = new JButton("Tìm kiếm");

        btnTimKiem.setIcon(
                FontIcon.of(
                        FontAwesomeSolid.SEARCH,
                        14,
                        Color.WHITE
                )
        );

        btnTimKiem.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setBackground(BLUE);
        btnTimKiem.setFocusPainted(false);

        btnTimKiem.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        btnTimKiem.setBorder(
                new LineBorder(BLUE)
        );

        btnTimKiem.setPreferredSize(
                new Dimension(275, 40)
        );

        // Nút làm mới

        btnLamMoi = new JButton("Làm mới");

        btnLamMoi.setIcon(
                FontIcon.of(
                        FontAwesomeSolid.SYNC_ALT,
                        14,
                        TEXT
                )
        );

        btnLamMoi.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        btnLamMoi.setForeground(TEXT);
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setFocusPainted(false);

        btnLamMoi.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        btnLamMoi.setBorder(
                new LineBorder(new Color(215, 220, 227))
        );

        btnLamMoi.setPreferredSize(
                new Dimension(275, 40)
        );

        // Thêm nút vào panel

        pnlNut.add(btnTimKiem);
        pnlNut.add(btnLamMoi);

        // Ghép khung tìm kiếm và các nút

        JPanel pnlNoiDungTimKiem = new JPanel(
                new BorderLayout(0, 14)
        );

        pnlNoiDungTimKiem.setOpaque(false);

        pnlNoiDungTimKiem.add(
                pnlKhungTimKiem,
                BorderLayout.CENTER
        );

        pnlNoiDungTimKiem.add(
                pnlNut,
                BorderLayout.SOUTH
        );

        pnlTimKiem.add(
                pnlNoiDungTimKiem,
                BorderLayout.CENTER
        );

        // ================= KHUNG BẢNG KẾT QUẢ =================

        JPanel pnlBang = new JPanel(
                new BorderLayout(0, 14)
        );

        pnlBang.setBackground(Color.WHITE);

        pnlBang.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(226, 231, 238)),
                        new EmptyBorder(16, 16, 16, 16)
                )
        );

        JLabel lblKetQua = new JLabel("Kết quả tìm kiếm");

        lblKetQua.setFont(
                new Font("Arial", Font.BOLD, 17)
        );

        lblKetQua.setForeground(TEXT);

        pnlBang.add(lblKetQua, BorderLayout.NORTH);

        // Các cột trong bảng

        String[] arrCot = {
                "STT",
                "MÃ VẬT TƯ",
                "TÊN VẬT TƯ",
                "DANH MỤC",
                "ĐƠN VỊ TÍNH",
                "GIÁ BÁN (VNĐ)",
                "SỐ LƯỢNG TỒN",
                "TRẠNG THÁI"
        };

        model = new DefaultTableModel(arrCot, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tblVatTu = new JTable(model);

        tblVatTu.setRowHeight(42);
        tblVatTu.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        tblVatTu.setForeground(TEXT);
        tblVatTu.setGridColor(
                new Color(238, 241, 245)
        );

        tblVatTu.setShowVerticalLines(false);

        tblVatTu.setSelectionBackground(
                new Color(239, 247, 255)
        );

        tblVatTu.setSelectionForeground(TEXT);

        // Định dạng tiêu đề bảng

        JTableHeader tblHeader = tblVatTu.getTableHeader();

        tblHeader.setPreferredSize(
                new Dimension(0, 44)
        );

        tblHeader.setFont(
                new Font("Arial", Font.BOLD, 11)
        );

        tblHeader.setForeground(
                new Color(105, 115, 126)
        );

        tblHeader.setBackground(
                new Color(248, 249, 251)
        );

        // Thanh cuộn bảng

        JScrollPane scrBang = new JScrollPane(tblVatTu);

        scrBang.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrBang.getViewport().setBackground(Color.WHITE);

        pnlBang.add(scrBang, BorderLayout.CENTER);

        // ================= PHẦN CUỐI BẢNG =================

        JPanel pnlCuoiBang = new JPanel(
                new BorderLayout()
        );

        pnlCuoiBang.setBackground(Color.WHITE);

        JLabel lblHuongDan = new JLabel(
                "Vui lòng chọn một vật tư trong bảng"
        );

        lblHuongDan.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        lblHuongDan.setForeground(
                new Color(130, 140, 150)
        );

        // Nút xem chi tiết

        btnXemChiTiet = new JButton("Xem chi tiết");

        btnXemChiTiet.setIcon(
                FontIcon.of(
                        FontAwesomeSolid.EYE,
                        14,
                        Color.WHITE
                )
        );

        btnXemChiTiet.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setBackground(BLUE);
        btnXemChiTiet.setFocusPainted(false);

        btnXemChiTiet.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        btnXemChiTiet.setBorder(
                new LineBorder(BLUE)
        );

        pnlCuoiBang.add(
                lblHuongDan,
                BorderLayout.WEST
        );

        pnlCuoiBang.add(
                btnXemChiTiet,
                BorderLayout.EAST
        );

        pnlBang.add(
                pnlCuoiBang,
                BorderLayout.SOUTH
        );

        // ================= GHÉP GIAO DIỆN =================

        pnlNoiDung.add(
                pnlTimKiem,
                BorderLayout.NORTH
        );

        pnlNoiDung.add(
                pnlBang,
                BorderLayout.CENTER
        );

        pnlChinh.add(
                pnlNoiDung,
                BorderLayout.CENTER
        );

        add(
                pnlChinh,
                BorderLayout.CENTER
        );

        // ================= ĐĂNG KÝ SỰ KIỆN =================

        btnTimKiem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXemChiTiet.addActionListener(this);

        // Tải dữ liệu ban đầu

        taiDuLieuLenBang(null);
    }

    // ================= ĐỊNH DẠNG Ô NHẬP LIỆU =================

    private void dinhDangO(JTextField txt) {

        txt.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        txt.setForeground(TEXT);
        txt.setBackground(Color.WHITE);

        txt.setPreferredSize(
                new Dimension(120, 36)
        );

        txt.setBorder(
                new LineBorder(
                        new Color(211, 217, 225)
                )
        );
    }

    // ================= ĐỊNH DẠNG COMBOBOX =================

    private void dinhDangCombobox(
            JComboBox<String> cbo
    ) {

        cbo.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        cbo.setForeground(TEXT);
        cbo.setBackground(Color.WHITE);

        cbo.setPreferredSize(
                new Dimension(120, 36)
        );

        cbo.setBorder(
                new LineBorder(
                        new Color(211, 217, 225)
                )
        );
    }

    // ================= TẠO PANEL TRƯỜNG TÌM KIẾM =================

    private JPanel taoPanelTruong(
            String nhan,
            java.awt.Component comp
    ) {

        JPanel pnl = new JPanel(
                new BorderLayout(0, 6)
        );

        pnl.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(nhan);

        lbl.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        lbl.setForeground(TEXT);

        pnl.add(lbl, BorderLayout.NORTH);
        pnl.add(comp, BorderLayout.CENTER);

        return pnl;
    }

    // ================= TẢI DỮ LIỆU LÊN BẢNG =================

    private void taiDuLieuLenBang(List<VatTu> list) {

        model.setRowCount(0);

        try {

            if (list == null) {
                list = vtDAO.getAll();
            }

            int stt = 1;

            for (VatTu vt : list) {

                int ton = vt.getSoLuongTon();
                int min = vt.getSoLuongToiThieu();

                String trangThai = "Còn hàng";

                if (ton <= 0) {

                    trangThai = "Hết hàng";

                } else if (ton <= min) {

                    trangThai = "Tồn thấp";
                }

                Object[] row = {

                        stt++,

                        vt.getMaVT(),

                        vt.getTenVatTu(),

                        vt.getMoTa() != null
                                ? vt.getMoTa()
                                : "N/A",

                        vt.getDonViTinh(),

                        currencyFormatter.format(0),

                        ton,

                        trangThai
                };

                model.addRow(row);
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi tải dữ liệu vật tư!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= XỬ LÝ SỰ KIỆN =================

    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();

        // NÚT TÌM KIẾM

        if (o.equals(btnTimKiem)) {

            String tuKhoa = txtTuKhoa.getText().trim();

            String danhMuc =
                    cboDanhMuc.getSelectedItem().toString();

            String trangThai =
                    cboTrangThai.getSelectedItem().toString();

            try {

                List<VatTu> ketQua = vtDAO.timKiem(
                        tuKhoa,
                        tuKhoa,
                        danhMuc,
                        trangThai
                );

                taiDuLieuLenBang(ketQua);

                if (ketQua.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Không tìm thấy vật tư nào phù hợp!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } catch (SQLException ex) {

                ex.printStackTrace();
            }

        }

        // NÚT LÀM MỚI

        else if (o.equals(btnLamMoi)) {

            txtTuKhoa.setText("");

            txtGiaTu.setText("");

            txtGiaDen.setText("");

            cboDanhMuc.setSelectedIndex(0);

            cboTrangThai.setSelectedIndex(0);

            taiDuLieuLenBang(null);
        }

        // NÚT XEM CHI TIẾT

        else if (o.equals(btnXemChiTiet)) {

            int row = tblVatTu.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn một vật tư trong bảng để xem chi tiết!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String maVT =
                    model.getValueAt(row, 1).toString();

            String tenVT =
                    model.getValueAt(row, 2).toString();

            String ton =
                    model.getValueAt(row, 6).toString();

            String tt =
                    model.getValueAt(row, 7).toString();

            JOptionPane.showMessageDialog(
                    this,

                    "Chi tiết vật tư:\n"
                            + "Mã: " + maVT + "\n"
                            + "Tên: " + tenVT + "\n"
                            + "Tồn kho: " + ton + "\n"
                            + "Trạng thái: " + tt,

                    "Thông tin chi tiết",

                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
