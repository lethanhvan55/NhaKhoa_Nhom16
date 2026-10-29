package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ThanhToanTienMatDialog extends JDialog {

    private final Color MAU_NAVY = new Color(35, 70, 110);
    private final Color MAU_TEAL = new Color(35, 150, 120);
    private final Color MAU_DO = new Color(220, 70, 60);
    private final Color MAU_TRANG = Color.WHITE;

    private JTextField txtTongTien;
    private JTextField txtChietKhau;
    private JTextField txtThue;
    private JTextField txtCanThanhToan;
    private JTextField txtTienKhachDua;
    private JTextField txtTienThua;

    private final long canThanhToan;
    private long soTienKhachDua = 0;
    private long tienThua = 0;
    private boolean daXacNhan = false;

    public ThanhToanTienMatDialog(
            Window owner,
            double tongTien,
            double chietKhau,
            double thue) {

        super(owner, "Thanh toán tiền mặt",
                Dialog.ModalityType.APPLICATION_MODAL);

        this.canThanhToan = Math.round(
                Math.max(0, tongTien - chietKhau) + thue
        );

        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel lblTieuDe = new JLabel(
                "THANH TOÁN TIỀN MẶT",
                SwingConstants.CENTER
        );
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 17));
        lblTieuDe.setForeground(MAU_NAVY);
        lblTieuDe.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 5, 10)
        );

        JPanel pnlThongTin = new JPanel(
                new GridLayout(0, 2, 10, 10)
        );
        pnlThongTin.setBackground(MAU_TRANG);
        pnlThongTin.setBorder(
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );

        txtTongTien = taoOThongTin(
                dinhDangTien(Math.round(tongTien))
        );
        txtChietKhau = taoOThongTin(
                dinhDangTien(Math.round(chietKhau))
        );
        txtThue = taoOThongTin(
                dinhDangTien(Math.round(thue))
        );
        txtCanThanhToan = taoOThongTin(
                dinhDangTien(canThanhToan)
        );
        txtTienKhachDua = taoONhapTien();
        txtTienThua = taoOThongTin("0 VNĐ");

        txtCanThanhToan.setForeground(MAU_DO);
        txtCanThanhToan.setFont(new Font("Arial", Font.BOLD, 14));

        themTruong(pnlThongTin, "Tổng tiền dịch vụ", txtTongTien);
        themTruong(pnlThongTin, "Chiết khấu", txtChietKhau);
        themTruong(pnlThongTin, "Thuế VAT (10%)", txtThue);
        themTruong(pnlThongTin, "Cần thanh toán", txtCanThanhToan);
        themTruong(pnlThongTin, "Tiền khách đưa", txtTienKhachDua);
        themTruong(pnlThongTin, "Tiền thừa", txtTienThua);

        txtTienKhachDua.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        capNhatTienThua();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        capNhatTienThua();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        capNhatTienThua();
                    }
                }
        );

        JButton btnXacNhan = taoNut("Xác nhận", MAU_TEAL);
        JButton btnHuy = taoNut("Hủy", MAU_DO);

        JPanel pnlNut = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 10)
        );
        pnlNut.setBackground(MAU_TRANG);
        pnlNut.add(btnXacNhan);
        pnlNut.add(btnHuy);

        add(lblTieuDe, BorderLayout.NORTH);
        add(pnlThongTin, BorderLayout.CENTER);
        add(pnlNut, BorderLayout.SOUTH);

        btnXacNhan.addActionListener(e -> xacNhan());
        btnHuy.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(btnXacNhan);

        setPreferredSize(new Dimension(520, 390));
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private JTextField taoOThongTin(String noiDung) {
        JTextField txt = new JTextField(noiDung);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setEditable(false);
        txt.setHorizontalAlignment(SwingConstants.RIGHT);
        txt.setBackground(new Color(248, 249, 252));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 229, 235)
                ),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return txt;
    }

    private JTextField taoONhapTien() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Arial", Font.PLAIN, 14));
        txt.setHorizontalAlignment(SwingConstants.RIGHT);
        txt.setToolTipText("Nhập số tiền khách đưa, ví dụ 500000");
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAU_TEAL),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return txt;
    }

    private void themTruong(
            JPanel pnl,
            String tenTruong,
            JTextField txt) {

        JLabel lbl = new JLabel(tenTruong);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(new Color(75, 85, 99));

        pnl.add(lbl);
        pnl.add(txt);
    }

    private JButton taoNut(String noiDung, Color mauNen) {
        JButton btn = new JButton(noiDung);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mauNen);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(110, 35));
        return btn;
    }

    private long laySoTien(String giaTri) {
        if (giaTri == null || giaTri.trim().isEmpty()) {
            return 0;
        }

        String chuSo = giaTri.replaceAll("[^0-9]", "");

        if (chuSo.isEmpty()) {
            return 0;
        }

        try {
            return Long.parseLong(chuSo);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String dinhDangTien(long soTien) {
        return String.format("%,d VNĐ", soTien);
    }

    private void capNhatTienThua() {
        long tienNhan = laySoTien(txtTienKhachDua.getText());

        if (tienNhan < 0) {
            txtTienThua.setText("Số tiền không hợp lệ");
        } else if (tienNhan < canThanhToan) {
            txtTienThua.setText(
                    "Thiếu " + dinhDangTien(canThanhToan - tienNhan)
            );
        } else {
            txtTienThua.setText(
                    dinhDangTien(tienNhan - canThanhToan)
            );
        }
    }

    private void xacNhan() {
        long tienNhan = laySoTien(txtTienKhachDua.getText());

        if (tienNhan <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập số tiền khách đưa hợp lệ!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            txtTienKhachDua.requestFocus();
            return;
        }

        if (tienNhan < canThanhToan) {
            JOptionPane.showMessageDialog(
                    this,
                    "Số tiền khách đưa chưa đủ thanh toán!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            txtTienKhachDua.requestFocus();
            return;
        }

        soTienKhachDua = tienNhan;
        tienThua = tienNhan - canThanhToan;
        daXacNhan = true;
        dispose();
    }

    public boolean isDaXacNhan() {
        return daXacNhan;
    }

    public long getSoTienKhachDua() {
        return soTienKhachDua;
    }

    public long getTienThua() {
        return tienThua;
    }

    public long getCanThanhToan() {
        return canThanhToan;
    }
}