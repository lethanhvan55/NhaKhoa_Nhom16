
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class HoTroPanel extends JPanel {

    private final Color mauNen = new Color(245, 248, 252);
    private final Color mauChu = new Color(39, 55, 77);
    private final Color mauChinh = new Color(45, 112, 190);
    private final Color mauVien = new Color(224, 231, 239);
    private final Color mauThe = Color.WHITE;

    private JPanel pnlCauTraLoi;
    private JTextArea txtCauTraLoi;

    public HoTroPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(mauNen);
        setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));

        JPanel pnlTieuDe = taoPhanTieuDe();
        JPanel pnlChucNang = taoPhanChucNang();
        JPanel pnlCauHoi = taoPhanCauHoi();
        JPanel pnlLienHe = taoPhanLienHe();

        JPanel pnlTren = new JPanel();
        pnlTren.setLayout(new BoxLayout(pnlTren, BoxLayout.Y_AXIS));
        pnlTren.setBackground(mauNen);
        pnlTren.add(pnlTieuDe);
        pnlTren.add(Box.createVerticalStrut(16));
        pnlTren.add(pnlChucNang);

        JPanel pnlGiua = new JPanel(new BorderLayout());
        pnlGiua.setBackground(mauNen);
        pnlGiua.add(pnlCauHoi, BorderLayout.CENTER);

        JPanel pnlDuoi = new JPanel(new BorderLayout());
        pnlDuoi.setBackground(mauNen);
        pnlDuoi.add(pnlLienHe, BorderLayout.CENTER);

        add(pnlTren, BorderLayout.NORTH);
        add(pnlGiua, BorderLayout.CENTER);
        add(pnlDuoi, BorderLayout.SOUTH);
    }

    private JPanel taoPhanTieuDe() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(mauNen);

        JLabel lblTieuDe = new JLabel("TRUNG TÂM HỖ TRỢ");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 26));
        lblTieuDe.setForeground(mauChu);
        lblTieuDe.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblMoTa = new JLabel(
            "Hướng dẫn sử dụng hệ thống quản lý nha khoa"
        );
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMoTa.setForeground(new Color(105, 118, 135));
        lblMoTa.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(lblTieuDe);
        panel.add(Box.createVerticalStrut(7));
        panel.add(lblMoTa);

        return panel;
    }

    private JPanel taoPhanChucNang() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 14, 0));
        panel.setBackground(mauNen);
        panel.setPreferredSize(new Dimension(0, 145));
        panel.setMinimumSize(new Dimension(0, 145));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 145));

        panel.add(taoTheChucNang(
            "Bệnh nhân",
            "Tra cứu và quản lý thông tin bệnh nhân",
            "Xem hướng dẫn",
            "Bệnh nhân"
        ));

        panel.add(taoTheChucNang(
            "Lịch hẹn",
            "Theo dõi và quản lý lịch khám",
            "Xem hướng dẫn",
            "Lịch hẹn"
        ));

        panel.add(taoTheChucNang(
            "Hóa đơn",
            "Tra cứu và quản lý thanh toán",
            "Xem hướng dẫn",
            "Hóa đơn"
        ));

        return panel;
    }

    private JPanel taoTheChucNang(
        String tieuDe,
        String moTa,
        String tenNut,
        String loai
    ) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(mauThe);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(mauVien),
            BorderFactory.createEmptyBorder(14, 15, 14, 15)
        ));

        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 16));
        lblTieuDe.setForeground(mauChu);

        JLabel lblMoTa = new JLabel(
            "<html><div style='width:190px'>"
            + moTa
            + "</div></html>"
        );
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMoTa.setForeground(new Color(100, 113, 130));

        JButton btnHuongDan = new JButton(tenNut);
        btnHuongDan.setFont(new Font("Arial", Font.BOLD, 12));
        btnHuongDan.setForeground(mauChinh);
        btnHuongDan.setBackground(new Color(235, 243, 253));
        btnHuongDan.setFocusPainted(false);
        btnHuongDan.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        btnHuongDan.setBorder(
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );

        btnHuongDan.addActionListener(e -> hienThiHuongDan(loai));

        JPanel pnlThongTin = new JPanel();
        pnlThongTin.setLayout(
            new BoxLayout(pnlThongTin, BoxLayout.Y_AXIS)
        );
        pnlThongTin.setBackground(mauThe);
        pnlThongTin.add(lblTieuDe);
        pnlThongTin.add(Box.createVerticalStrut(7));
        pnlThongTin.add(lblMoTa);

        panel.add(pnlThongTin, BorderLayout.CENTER);

        JPanel pnlNut = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlNut.setBackground(mauThe);
        pnlNut.add(btnHuongDan);

        panel.add(pnlNut, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel taoPhanCauHoi() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(mauThe);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(mauVien),
            BorderFactory.createEmptyBorder(16, 18, 16, 18)
        ));

        JLabel lblTieuDe = new JLabel("CÂU HỎI THƯỜNG GẶP");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 17));
        lblTieuDe.setForeground(mauChu);

        JPanel pnlDanhSach = new JPanel();
        pnlDanhSach.setLayout(
            new BoxLayout(pnlDanhSach, BoxLayout.Y_AXIS)
        );
        pnlDanhSach.setBackground(mauThe);

        themCauHoi(
            pnlDanhSach,
            "1. Làm thế nào để thêm bệnh nhân?",
            "Mở chức năng Bệnh nhân, chọn Thêm bệnh nhân, "
            + "nhập đầy đủ thông tin bắt buộc rồi lưu."
        );

        themCauHoi(
            pnlDanhSach,
            "2. Làm thế nào để đặt lịch hẹn?",
            "Mở chức năng Lịch hẹn, chọn Thêm lịch hẹn, "
            + "chọn bệnh nhân, thời gian khám và thông tin liên quan."
        );

        themCauHoi(
            pnlDanhSach,
            "3. Làm thế nào để xem hồ sơ bệnh nhân?",
            "Đăng nhập bằng tài khoản có quyền Nha sĩ, "
            + "mở Hồ sơ bệnh nhân và tìm bệnh nhân cần xem."
        );

        themCauHoi(
            pnlDanhSach,
            "4. Làm thế nào để lập hóa đơn?",
            "Mở chức năng Hóa đơn, chọn thông tin khám hoặc dịch vụ "
            + "theo quy trình của phòng khám, kiểm tra số tiền rồi lưu."
        );

        themCauHoi(
            pnlDanhSach,
            "5. Không đăng nhập được thì phải làm sao?",
            "Kiểm tra tên đăng nhập và mật khẩu. Nếu vẫn không được, "
            + "liên hệ quản lý để kiểm tra tài khoản."
        );

        JScrollPane scrDanhSach = new JScrollPane(pnlDanhSach);
        scrDanhSach.setBorder(null);
        scrDanhSach.getVerticalScrollBar().setUnitIncrement(14);
        scrDanhSach.setPreferredSize(new Dimension(0, 200));

        txtCauTraLoi = new JTextArea(
            "Chọn một câu hỏi phía trên để xem hướng dẫn chi tiết."
        );
        txtCauTraLoi.setFont(new Font("Arial", Font.PLAIN, 13));
        txtCauTraLoi.setForeground(mauChu);
        txtCauTraLoi.setBackground(new Color(245, 248, 252));
        txtCauTraLoi.setLineWrap(true);
        txtCauTraLoi.setWrapStyleWord(true);
        txtCauTraLoi.setEditable(false);
        txtCauTraLoi.setFocusable(false);
        txtCauTraLoi.setBorder(
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );
        txtCauTraLoi.setRows(3);

        pnlCauTraLoi = new JPanel(new BorderLayout());
        pnlCauTraLoi.setBackground(new Color(245, 248, 252));
        pnlCauTraLoi.setBorder(
            BorderFactory.createLineBorder(mauVien)
        );
        pnlCauTraLoi.add(txtCauTraLoi, BorderLayout.CENTER);

        JPanel pnlPhanDuoi = new JPanel(new BorderLayout(0, 8));
        pnlPhanDuoi.setBackground(mauThe);

        JLabel lblTraLoi = new JLabel("Hướng dẫn chi tiết");
        lblTraLoi.setFont(new Font("Arial", Font.BOLD, 14));
        lblTraLoi.setForeground(mauChu);

        pnlPhanDuoi.add(lblTraLoi, BorderLayout.NORTH);
        pnlPhanDuoi.add(pnlCauTraLoi, BorderLayout.CENTER);

        panel.add(lblTieuDe, BorderLayout.NORTH);
        panel.add(scrDanhSach, BorderLayout.CENTER);
        panel.add(pnlPhanDuoi, BorderLayout.SOUTH);

        return panel;
    }

    private void themCauHoi(
        JPanel panel,
        String cauHoi,
        String cauTraLoi
    ) {
        JButton btn = new JButton(cauHoi);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setForeground(mauChu);
        btn.setBackground(Color.WHITE);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                    0, 0, 1, 0, mauVien
                ),
                BorderFactory.createEmptyBorder(11, 5, 11, 5)
            )
        );
        btn.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 44)
        );
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        btn.addActionListener(e -> {
            txtCauTraLoi.setText(cauTraLoi);
            txtCauTraLoi.setCaretPosition(0);
        });

        panel.add(btn);
    }

    private JPanel taoPhanLienHe() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(new Color(232, 241, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(207, 223, 243)),
            BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        JPanel pnlThongTin = new JPanel();
        pnlThongTin.setLayout(
            new BoxLayout(pnlThongTin, BoxLayout.Y_AXIS)
        );
        pnlThongTin.setBackground(new Color(232, 241, 252));

        JLabel lblTieuDe = new JLabel("THÔNG TIN LIÊN HỆ");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 16));
        lblTieuDe.setForeground(mauChu);

        JLabel lblDonVi = new JLabel(
            "Bộ phận hỗ trợ hệ thống nha khoa"
        );
        lblDonVi.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDonVi.setForeground(mauChu);

        JLabel lblDienThoai = new JLabel(
            "Điện thoại: Cập nhật số điện thoại phòng khám"
        );
        lblDienThoai.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDienThoai.setForeground(mauChu);

        JLabel lblEmail = new JLabel(
            "Email: Cập nhật email hỗ trợ"
        );
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEmail.setForeground(mauChu);

        pnlThongTin.add(lblTieuDe);
        pnlThongTin.add(Box.createVerticalStrut(6));
        pnlThongTin.add(lblDonVi);
        pnlThongTin.add(Box.createVerticalStrut(5));
        pnlThongTin.add(lblDienThoai);
        pnlThongTin.add(Box.createVerticalStrut(4));
        pnlThongTin.add(lblEmail);

        JButton btnLienHe = new JButton("Liên hệ quản lý");
        btnLienHe.setFont(new Font("Arial", Font.BOLD, 12));
        btnLienHe.setForeground(Color.WHITE);
        btnLienHe.setBackground(mauChinh);
        btnLienHe.setFocusPainted(false);
        btnLienHe.setBorder(
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );
        btnLienHe.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        btnLienHe.addActionListener(e ->
            JOptionPane.showMessageDialog(
                this,
                "Vui lòng liên hệ quản lý phòng khám "
                + "để được hỗ trợ.",
                "Thông tin hỗ trợ",
                JOptionPane.INFORMATION_MESSAGE
            )
        );

        panel.add(pnlThongTin, BorderLayout.CENTER);
        panel.add(btnLienHe, BorderLayout.EAST);

        return panel;
    }

    private void hienThiHuongDan(String loai) {
        String noiDung;

        switch (loai) {
            case "Bệnh nhân":
                noiDung =
                    "HƯỚNG DẪN QUẢN LÝ BỆNH NHÂN\n\n"
                    + "1. Mở chức năng Bệnh nhân.\n"
                    + "2. Tìm kiếm theo mã hoặc thông tin bệnh nhân.\n"
                    + "3. Chọn Thêm để tạo hồ sơ bệnh nhân mới.\n"
                    + "4. Nhập thông tin bắt buộc và lưu lại.\n"
                    + "5. Chọn bệnh nhân để xem hoặc cập nhật "
                    + "thông tin theo quyền được cấp.";
                break;

            case "Lịch hẹn":
                noiDung =
                    "HƯỚNG DẪN QUẢN LÝ LỊCH HẸN\n\n"
                    + "1. Mở chức năng Lịch hẹn.\n"
                    + "2. Chọn Thêm lịch hẹn.\n"
                    + "3. Chọn bệnh nhân, ngày giờ khám "
                    + "và thông tin liên quan.\n"
                    + "4. Kiểm tra lịch trước khi lưu.\n"
                    + "5. Theo dõi hoặc cập nhật trạng thái lịch hẹn.";
                break;

            case "Hóa đơn":
                noiDung =
                    "HƯỚNG DẪN QUẢN LÝ HÓA ĐƠN\n\n"
                    + "1. Mở chức năng Hóa đơn.\n"
                    + "2. Chọn hóa đơn cần tra cứu hoặc tạo mới.\n"
                    + "3. Kiểm tra bệnh nhân, dịch vụ và số tiền.\n"
                    + "4. Xác nhận thông tin trước khi lưu.\n"
                    + "5. Chỉ thực hiện thao tác được cấp quyền.";
                break;

            default:
                noiDung = "Chưa có hướng dẫn cho chức năng này.";
        }

        JTextArea txt = new JTextArea(noiDung);
        txt.setFont(new Font("Arial", Font.PLAIN, 14));
        txt.setEditable(false);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setCaretPosition(0);
        txt.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JScrollPane scroll = new JScrollPane(txt);
        scroll.setPreferredSize(new Dimension(470, 300));

        JOptionPane.showMessageDialog(
            this,
            scroll,
            "Hướng dẫn sử dụng",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
