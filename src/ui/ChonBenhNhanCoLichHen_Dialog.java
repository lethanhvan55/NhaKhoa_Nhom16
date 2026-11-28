package ui;

import dao.LichHenDAO;
import model.LichHen;
import utils.UIHelper;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChonBenhNhanCoLichHen_Dialog extends JDialog implements ActionListener, MouseListener {

    private JTable tblLichHen;
    private DefaultTableModel modelLichHen;

    private JTextField txtTimKiem;

    private JButton btnTim;
    private JButton btnChon;
    private JButton btnHuy;

    private final LichHenDAO dao = new LichHenDAO();

    private final LocalDate ngayHen;

    private String maLichHenChon;
    private String maBenhNhanChon;
    private String tenBenhNhanChon;

    private final DateTimeFormatter dinhDangNgay =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final DateTimeFormatter dinhDangGio =
            DateTimeFormatter.ofPattern("HH:mm");

    public ChonBenhNhanCoLichHen_Dialog(
            JFrame parent, LocalDate ngayHen) {

        super(parent, "Danh sách bệnh nhân", true);

        this.ngayHen = ngayHen;

        setSize(950, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        createContent();
        loadDanhSachLichHen("");
    }

    private void createContent() {

        JPanel pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.setBorder(new EmptyBorder(15, 15, 15, 15));
        pnlMain.setBackground(Color.WHITE);

        // ================= TIÊU ĐỀ =================

        JLabel lblTieuDe = new JLabel(
                "DANH SÁCH LỊCH HẸN NGÀY "
                        + ngayHen.format(dinhDangNgay)
        );

        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));
        lblTieuDe.setForeground(new Color(35, 70, 110));

        JPanel pnlTieuDe = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTieuDe.setBackground(Color.WHITE);
        pnlTieuDe.add(lblTieuDe);

        // ================= TÌM KIẾM =================

        JPanel pnlTimKiem = new JPanel(new BorderLayout(10, 0));
        pnlTimKiem.setBackground(Color.WHITE);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(300, 35));

        btnTim = new JButton("Tìm");
        btnTim.setFont(new Font("Arial", Font.BOLD, 13));
        btnTim.setPreferredSize(new Dimension(90, 35));

        pnlTimKiem.add(txtTimKiem, BorderLayout.CENTER);
        pnlTimKiem.add(btnTim, BorderLayout.EAST);

        JPanel pnlTop = new JPanel(new BorderLayout(10, 10));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.add(pnlTieuDe, BorderLayout.NORTH);
        pnlTop.add(pnlTimKiem, BorderLayout.SOUTH);

        pnlMain.add(pnlTop, BorderLayout.NORTH);

        // ================= BẢNG LỊCH HẸN =================

        String[] columns = {
                "Mã lịch hẹn",
                "Mã bệnh nhân",
                "Tên bệnh nhân",
                "Ngày hẹn",
                "Giờ hẹn",
                "Bác sĩ",
                "Trạng thái"
        };

        modelLichHen = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLichHen = new JTable(modelLichHen);
        tblLichHen.setFont(new Font("Arial", Font.PLAIN, 13));
        tblLichHen.setRowHeight(32);
        tblLichHen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblLichHen.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        tblLichHen.getTableHeader().setBackground(new Color(35, 70, 110));

        tblLichHen.getTableHeader().setForeground(Color.WHITE);

        tblLichHen.setShowGrid(true);
        tblLichHen.setGridColor(new Color(220, 220, 220));

        // Căn giữa nội dung bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tblLichHen.getColumnCount(); i++) {
            tblLichHen.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tblLichHen);
        scrollPane.setBorder(new LineBorder(new Color(220, 220, 220)));

        pnlMain.add(scrollPane, BorderLayout.CENTER);

        // ================= NÚT CHỨC NĂNG =================

        btnChon = new JButton("Chọn");
        btnHuy = new JButton("Hủy");

        btnChon.setFont(new Font("Arial", Font.BOLD, 13));
        btnHuy.setFont(new Font("Arial", Font.BOLD, 13));

        btnChon.setPreferredSize(new Dimension(100, 35));
        btnHuy.setPreferredSize(new Dimension(100, 35));

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        pnlButton.setBackground(Color.WHITE);
        pnlButton.add(btnChon);
        pnlButton.add(btnHuy);

        pnlMain.add(pnlButton, BorderLayout.SOUTH);

        btnTim.addActionListener(this);
        btnChon.addActionListener(this);
        btnHuy.addActionListener(this);

        txtTimKiem.addActionListener(this);

        tblLichHen.addMouseListener(this);

        setContentPane(pnlMain);
    }

    private void loadDanhSachLichHen(String tuKhoa) {

        modelLichHen.setRowCount(0);

        List<LichHen> ds = dao.getDanhSachLichHenTheoNgay(ngayHen, tuKhoa);

        for (LichHen lh : ds) {
            String maLichHen = lh.getMaLichHen();

            String maBN = "";
            String tenBN = "";

            if (lh.getMaBenhNhan() != null) {

                maBN = lh.getMaBenhNhan().getMaBenhNhan();

                tenBN = lh.getMaBenhNhan().getHoTen() == null ? "" : lh.getMaBenhNhan().getHoTen();
            }

            LocalDate ngay = lh.getNgayHen();

            String ngayHienThi = ngay == null ? "" : ngay.format(dinhDangNgay);

            LocalDateTime gio = lh.getGioHen();

            String gioHienThi = gio == null ? "" : gio.format(dinhDangGio);

            String bacSi = "";

            if (lh.getMaNV() != null && lh.getMaNV().getHoTen() != null) {

                bacSi = lh.getMaNV().getHoTen();
            }

            String trangThai = lh.getTrangThai() == null ? "" : lh.getTrangThai();

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
    }

    private void xuLyChonLichHen() {

        int row = tblLichHen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lịch hẹn.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        maLichHenChon = modelLichHen.getValueAt(row, 0).toString();

        maBenhNhanChon = modelLichHen.getValueAt(row, 1).toString();

        tenBenhNhanChon = modelLichHen.getValueAt(row, 2).toString();

        dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();

        if (o == btnTim || o == txtTimKiem) {

            loadDanhSachLichHen(txtTimKiem.getText().trim());

        } else if (o == btnChon) {

            xuLyChonLichHen();

        } else if (o == btnHuy) {

            dispose();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == tblLichHen
                && e.getClickCount() == 2) {

            xuLyChonLichHen();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
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