package ui;

import dao.LichHenDAO;
import utils.UIHelper;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ChonBenhNhanCoLichHen_Dialog extends JDialog {

    private JTable tblLichHen;
    private DefaultTableModel modelLichHen;
    private JTextField txtTimKiem;

    private final LichHenDAO dao = new LichHenDAO();
    private final LocalDate ngayHen;

    private String maLichHenChon;
    private String maBenhNhanChon;
    private String tenBenhNhanChon;

    private final DateTimeFormatter dinhDangNgay =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final DateTimeFormatter dinhDangGio =
            DateTimeFormatter.ofPattern("HH:mm");

    public ChonBenhNhanCoLichHen_Dialog(JFrame parent, LocalDate ngayHen) {

        super(parent, "Chọn bệnh nhân theo lịch hẹn", true);

        this.ngayHen = ngayHen;

        setSize(950, 560);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        add(createContent(), BorderLayout.CENTER);

        loadDanhSachLichHen("");
    }

    private JPanel createContent() {

        JPanel p = new JPanel(new BorderLayout(0, 12));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));
        p.setBackground(Color.WHITE);

        // Khu vực tìm kiếm
        JPanel pTop = new JPanel(new BorderLayout(10, 0));
        pTop.setOpaque(false);

        JLabel lblNgay = new JLabel(
                "Danh sách lịch hẹn ngày: "
                        + ngayHen.format(dinhDangNgay)
        );
        lblNgay.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNgay.setForeground(UIHelper.NAVY);

        JPanel pTimKiem = new JPanel(new BorderLayout(8, 0));
        pTimKiem.setOpaque(false);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTimKiem.setPreferredSize(new Dimension(250, 36));
        txtTimKiem.putClientProperty(
                "JTextField.placeholderText",
                "Nhập mã lịch hẹn, mã BN hoặc họ tên..."
        );

        JButton btnTim = new JButton("Tìm");
        UIHelper.styleButton(btnTim, true, FontAwesomeSolid.SEARCH);
        btnTim.setPreferredSize(new Dimension(90, 36));

        pTimKiem.add(txtTimKiem, BorderLayout.CENTER);
        pTimKiem.add(btnTim, BorderLayout.EAST);

        pTop.add(lblNgay, BorderLayout.WEST);
        pTop.add(pTimKiem, BorderLayout.EAST);

        // Bảng lịch hẹn
        String[] cols = {
                "Mã lịch hẹn",
                "Mã BN",
                "Họ tên bệnh nhân",
                "Ngày hẹn",
                "Giờ hẹn",
                "Bác sĩ",
                "Trạng thái"
        };

        modelLichHen = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLichHen = new JTable(modelLichHen);
        tblLichHen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblLichHen.setForeground(UIHelper.TEXT);
        tblLichHen.setBackground(Color.WHITE);
        tblLichHen.setRowHeight(38);
        tblLichHen.setGridColor(new Color(230, 240, 250));
        tblLichHen.setShowVerticalLines(false);
        tblLichHen.setSelectionBackground(new Color(220, 235, 252));
        tblLichHen.setSelectionForeground(UIHelper.NAVY);
        tblLichHen.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tblLichHen.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );
        tblLichHen.getTableHeader().setForeground(Color.WHITE);
        tblLichHen.getTableHeader().setBackground(UIHelper.NAVY);
        tblLichHen.getTableHeader().setPreferredSize(
                new Dimension(0, 38)
        );
        tblLichHen.getTableHeader().setReorderingAllowed(false);

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        tblLichHen.getColumnModel().getColumn(0)
                .setCellRenderer(centerRenderer);

        tblLichHen.getColumnModel().getColumn(1)
                .setCellRenderer(centerRenderer);

        tblLichHen.getColumnModel().getColumn(3)
                .setCellRenderer(centerRenderer);

        tblLichHen.getColumnModel().getColumn(4)
                .setCellRenderer(centerRenderer);

        tblLichHen.getColumnModel().getColumn(6)
                .setCellRenderer(centerRenderer);

        // Thiết lập độ rộng từng cột
        tblLichHen.getColumnModel().getColumn(0).setPreferredWidth(110);
        tblLichHen.getColumnModel().getColumn(1).setPreferredWidth(75);
        tblLichHen.getColumnModel().getColumn(2).setPreferredWidth(180);
        tblLichHen.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblLichHen.getColumnModel().getColumn(4).setPreferredWidth(75);
        tblLichHen.getColumnModel().getColumn(5).setPreferredWidth(160);
        tblLichHen.getColumnModel().getColumn(6).setPreferredWidth(110);

        tblLichHen.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(tblLichHen);
        scroll.setBorder(new LineBorder(UIHelper.BORDER, 1, true));
        scroll.getViewport().setBackground(Color.WHITE);

        // Các nút chức năng
        JPanel pBottom = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        pBottom.setOpaque(false);

        JButton btnChon = new JButton("Chọn");
        JButton btnHuy = new JButton("Hủy");

        UIHelper.styleButton(btnChon, true, FontAwesomeSolid.CHECK);
        UIHelper.styleButton(btnHuy, false, FontAwesomeSolid.TIMES);

        btnHuy.setForeground(new Color(215, 38, 56));

        btnChon.setPreferredSize(new Dimension(100, 36));
        btnHuy.setPreferredSize(new Dimension(90, 36));

        pBottom.add(btnChon);
        pBottom.add(btnHuy);

        p.add(pTop, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        p.add(pBottom, BorderLayout.SOUTH);

        // Sự kiện
        btnTim.addActionListener(e ->
                loadDanhSachLichHen(txtTimKiem.getText().trim())
        );

        txtTimKiem.addActionListener(e ->
                loadDanhSachLichHen(txtTimKiem.getText().trim())
        );

        btnChon.addActionListener(e -> xuLyChonLichHen());
        btnHuy.addActionListener(e -> dispose());

        tblLichHen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    xuLyChonLichHen();
                }
            }
        });

        return p;
    }

    private void loadDanhSachLichHen(String tuKhoa) {

        modelLichHen.setRowCount(0);

        try {
            List<Map<String, Object>> ds =
                    dao.getDanhSachLichHenTheoNgay(ngayHen, tuKhoa);

            for (Map<String, Object> lh : ds) {

                String maLichHen = String.valueOf(
                        lh.get("MaLichHen")
                );

                String maBN = String.valueOf(
                        lh.get("MaBenhNhan")
                );

                String tenBN = lh.get("TenBenhNhan") == null
                        ? ""
                        : lh.get("TenBenhNhan").toString();

                LocalDate ngay = (LocalDate) lh.get("NgayHen");

                String ngayHienThi = ngay == null
                        ? ""
                        : ngay.format(dinhDangNgay);

                java.time.LocalDateTime gio =
                        (java.time.LocalDateTime) lh.get("GioHen");

                String gioHienThi = gio == null
                        ? ""
                        : gio.format(dinhDangGio);

                String bacSi = lh.get("TenBacSi") == null
                        ? ""
                        : lh.get("TenBacSi").toString();

                String trangThai = lh.get("TrangThai") == null
                        ? ""
                        : lh.get("TrangThai").toString();

                modelLichHen.addRow(new Object[]{
                        maLichHen,
                        maBN,
                        tenBN,
                        ngayHienThi,
                        gioHienThi,
                        bacSi,
                        trangThai
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi tải danh sách lịch hẹn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void xuLyChonLichHen() {

        int selectedRow = tblLichHen.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn một lịch hẹn!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int row = tblLichHen.convertRowIndexToModel(selectedRow);

        maLichHenChon = String.valueOf(
                modelLichHen.getValueAt(row, 0)
        );

        maBenhNhanChon = String.valueOf(
                modelLichHen.getValueAt(row, 1)
        );

        tenBenhNhanChon = String.valueOf(
                modelLichHen.getValueAt(row, 2)
        );

        dispose();
    }

    public String getMaLichHenChon() {
        return maLichHenChon;
    }

    public String getMaBenhNhanChon() {
        return maBenhNhanChon;
    }

    public String getTenBenhNhanChon() {
        return tenBenhNhanChon;
    }
}