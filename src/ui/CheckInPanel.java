
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CheckInPanel extends JPanel {

    private JTextField txtMaLichHen;
    private JTextField txtMaBenhNhan;
    private JTextField txtTenBenhNhan;
    private JTextField txtNgayHen;
    private JTextField txtGioHen;
    private JTextField txtBacSi;
    private JTextField txtDichVu;

    private JTable tblChoCheckIn;

    private Color mauChu = new Color(35, 70, 110);
    private Color mauNen = new Color(245, 247, 250);
    private Color mauTrang = Color.WHITE;
    private Color mauTeal = new Color(0, 150, 136);

    public CheckInPanel() {
        setLayout(new BorderLayout(0, 15));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        taoTieuDe();
        taoNoiDung();
    }

    private void taoTieuDe() {
        JPanel pnlTieuDe = new JPanel(new BorderLayout());
        pnlTieuDe.setOpaque(false);

        JLabel lblTieuDe = new JLabel("CHECK IN BỆNH NHÂN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(mauChu);

        JLabel lblMoTa = new JLabel("Xác nhận bệnh nhân đã đến phòng khám theo lịch hẹn");
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 13));
        lblMoTa.setForeground(Color.GRAY);

        JPanel pnlText = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlText.setOpaque(false);
        pnlText.add(lblTieuDe);
        pnlText.add(lblMoTa);

        pnlTieuDe.add(pnlText, BorderLayout.CENTER);
        pnlTieuDe.setPreferredSize(new Dimension(100, 65));

        add(pnlTieuDe, BorderLayout.NORTH);
    }

    private void taoNoiDung() {
        JPanel pnlNoiDung = new JPanel(new BorderLayout(15, 15));
        pnlNoiDung.setOpaque(false);

        JPanel pnlTraCuu = taoPanel("TRA CỨU LỊCH HẸN");

        JPanel pnlNhapMa = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlNhapMa.setOpaque(false);

        JLabel lblMaLichHen = new JLabel("Mã lịch hẹn:");
        lblMaLichHen.setFont(new Font("Arial", Font.BOLD, 13));

        txtMaLichHen = new JTextField(15);
        txtMaLichHen.setPreferredSize(new Dimension(180, 32));

        JButton btnTraCuu = taoNut("Tra cứu", mauChu);

        pnlNhapMa.add(lblMaLichHen);
        pnlNhapMa.add(txtMaLichHen);
        pnlNhapMa.add(btnTraCuu);

        pnlTraCuu.add(pnlNhapMa, BorderLayout.CENTER);

        JPanel pnlThongTin = taoPanel("THÔNG TIN BỆNH NHÂN");

        JPanel pnlForm = new JPanel(new GridLayout(4, 4, 12, 15));
        pnlForm.setOpaque(false);

        txtMaBenhNhan = taoONhap();
        txtTenBenhNhan = taoONhap();
        txtNgayHen = taoONhap();
        txtGioHen = taoONhap();
        txtBacSi = taoONhap();
        txtDichVu = taoONhap();

        themTruong(pnlForm, "Mã bệnh nhân:", txtMaBenhNhan);
        themTruong(pnlForm, "Tên bệnh nhân:", txtTenBenhNhan);
        themTruong(pnlForm, "Ngày hẹn:", txtNgayHen);
        themTruong(pnlForm, "Giờ hẹn:", txtGioHen);
        themTruong(pnlForm, "Bác sĩ phụ trách:", txtBacSi);
        themTruong(pnlForm, "Dịch vụ:", txtDichVu);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel lblChoCheckIn = new JLabel("Chưa check in");
        lblChoCheckIn.setFont(new Font("Arial", Font.BOLD, 13));
        lblChoCheckIn.setForeground(new Color(220, 140, 0));

        pnlForm.add(lblTrangThai);
        pnlForm.add(lblChoCheckIn);

        pnlThongTin.add(pnlForm, BorderLayout.CENTER);

        JPanel pnlNut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlNut.setOpaque(false);

        JButton btnLamMoi = taoNut("Làm mới", new Color(108, 117, 125));
        JButton btnCheckIn = taoNut("XÁC NHẬN CHECK IN", mauTeal);

        btnCheckIn.setPreferredSize(new Dimension(210, 42));
        btnCheckIn.setFont(new Font("Arial", Font.BOLD, 14));

        pnlNut.add(btnLamMoi);
        pnlNut.add(btnCheckIn);

        pnlThongTin.add(pnlNut, BorderLayout.SOUTH);

        JPanel pnlDanhSach = taoPanel("DANH SÁCH CHỜ CHECK IN");

        String[] tieuDe = {"Mã lịch hẹn", "Mã bệnh nhân", "Tên bệnh nhân", "Giờ hẹn", "Bác sĩ", "Trạng thái"};

        Object[][] duLieu = {
            {"LH0001", "BN0001", "Nguyễn Văn An", "08:00", "BS. Minh", "Chưa check in"},
            {"LH0002", "BN0002", "Trần Thị Bình", "09:00", "BS. Hùng", "Chưa check in"},
            {"LH0003", "BN0003", "Lê Văn Cường", "10:30", "BS. Minh", "Chưa check in"}
        };

        DefaultTableModel model = new DefaultTableModel(duLieu, tieuDe) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblChoCheckIn = new JTable(model);
        tblChoCheckIn.setRowHeight(32);
        tblChoCheckIn.setFont(new Font("Arial", Font.PLAIN, 13));
        tblChoCheckIn.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 13)
        );
        tblChoCheckIn.getTableHeader().setBackground(mauChu);
        tblChoCheckIn.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrBang = new JScrollPane(tblChoCheckIn);
        scrBang.setBorder(BorderFactory.createEmptyBorder());

        pnlDanhSach.add(scrBang, BorderLayout.CENTER);

        pnlNoiDung.add(pnlTraCuu, BorderLayout.NORTH);
        pnlNoiDung.add(pnlThongTin, BorderLayout.CENTER);
        pnlNoiDung.add(pnlDanhSach, BorderLayout.SOUTH);

        pnlTraCuu.setPreferredSize(new Dimension(100, 100));
        pnlThongTin.setPreferredSize(new Dimension(100, 270));
        pnlDanhSach.setPreferredSize(new Dimension(100, 220));

        add(pnlNoiDung, BorderLayout.CENTER);

        btnTraCuu.addActionListener(e -> {
            String ma = txtMaLichHen.getText().trim();

            for (int i = 0; i < tblChoCheckIn.getRowCount(); i++) {
                if (tblChoCheckIn.getValueAt(i, 0).toString().equalsIgnoreCase(ma)) {
                    txtMaLichHen.setText( tblChoCheckIn.getValueAt(i, 0).toString());
                    txtMaBenhNhan.setText(tblChoCheckIn.getValueAt(i, 1).toString());
                    txtTenBenhNhan.setText(tblChoCheckIn.getValueAt(i, 2).toString() );
                    txtGioHen.setText(tblChoCheckIn.getValueAt(i, 3).toString());
                    txtBacSi.setText(tblChoCheckIn.getValueAt(i, 4).toString());
                    txtNgayHen.setText("20/10/2026");
                    txtDichVu.setText("Khám tổng quát");
                    return;
                }
            }
        });

        tblChoCheckIn.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { int dong = tblChoCheckIn.getSelectedRow();

                if (dong >= 0) {
                    txtMaLichHen.setText(tblChoCheckIn.getValueAt(dong, 0).toString());
                    txtMaBenhNhan.setText(tblChoCheckIn.getValueAt(dong, 1).toString());
                    txtTenBenhNhan.setText(tblChoCheckIn.getValueAt(dong, 2).toString());
                    txtGioHen.setText(tblChoCheckIn.getValueAt(dong, 3).toString());
                    txtBacSi.setText(tblChoCheckIn.getValueAt(dong, 4).toString());
                    txtNgayHen.setText("20/10/2026");
                    txtDichVu.setText("Khám tổng quát");
                }
            }
        });

        btnLamMoi.addActionListener(e -> {
            txtMaLichHen.setText("");
            txtMaBenhNhan.setText("");
            txtTenBenhNhan.setText("");
            txtNgayHen.setText("");
            txtGioHen.setText("");
            txtBacSi.setText("");
            txtDichVu.setText("");
            tblChoCheckIn.clearSelection();
        });

        btnCheckIn.addActionListener(e -> {int dong = tblChoCheckIn.getSelectedRow();

            if (dong >= 0) { 
            tblChoCheckIn.setValueAt("Đã check in", dong, 5);
            }
        });
    }

    private JPanel taoPanel(String tieuDe) {
        JPanel pnl = new JPanel(new BorderLayout(0, 12));
        pnl.setBackground(mauTrang);
        pnl.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(225, 230, 235)),BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 15));
        lblTieuDe.setForeground(mauChu);

        pnl.add(lblTieuDe, BorderLayout.NORTH);

        return pnl;
    }

    private JTextField taoONhap() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setEditable(false);
        return txt;
    }

    private void themTruong(JPanel pnl,String ten,JTextField txt) {
        JLabel lbl = new JLabel(ten);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));

        pnl.add(lbl);
        pnl.add(txt);
    }

    private JButton taoNut(String ten, Color mau) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mau);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(145, 35));
        return btn;
    }
}
