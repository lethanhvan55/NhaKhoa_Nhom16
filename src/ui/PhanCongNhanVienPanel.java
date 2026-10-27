
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PhanCongNhanVienPanel extends JPanel {

    private final Color MAU_CHU = new Color(35, 70, 110);
    private final Color MAU_XANH = new Color(35, 105, 180);
    private final Color MAU_NEN = new Color(245, 247, 250);
    private final Color MAU_VIEN = new Color(224, 232, 241);

    private JTextField txtMaPhanCong;
    private JTextField txtNgayPhanCong;
    private JTextField txtGhiChu;
    private JTextField txtTimKiem;

    private JComboBox<String> cboNhaSi;
    private JComboBox<String> cboBenhNhan;
    private JComboBox<String> cboPhong;
    private JComboBox<String> cboLichHen;
    private JComboBox<String> cboTrangThai;

    private JTable tblPhanCong;
    private DefaultTableModel mdlPhanCong;
    private TableRowSorter<DefaultTableModel> boLoc;

    private final SimpleDateFormat dinhDangNgay =
        new SimpleDateFormat("dd/MM/yyyy");

    public PhanCongNhanVienPanel() {
        dinhDangNgay.setLenient(false);

        setLayout(new BorderLayout(15, 18));
        setBackground(MAU_NEN);
        setBorder(new EmptyBorder(18, 18, 18, 18));

       // add(taoTieuDe(), BorderLayout.NORTH);
        add(taoNoiDung(), BorderLayout.CENTER);
    }

 /*   private JPanel taoTieuDe() {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setOpaque(false);

        JLabel lblTieuDe = new JLabel("PHÂN CÔNG NHÂN VIÊN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 25));
        lblTieuDe.setForeground(MAU_CHU);

        JLabel lblMoTa = new JLabel(
            "Quản lý phân công nha sĩ, bệnh nhân, phòng điều trị và lịch hẹn"
        );
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 13));
        lblMoTa.setForeground(new Color(105, 120, 140));

        JPanel pnlText = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlText.setOpaque(false);
        pnlText.add(lblTieuDe);
        pnlText.add(lblMoTa);

        pnl.add(pnlText, BorderLayout.WEST);

        return pnl;
    }
*/
    private JPanel taoNoiDung() {
        JPanel pnl = new JPanel(new BorderLayout(12, 14));
        pnl.setOpaque(false);

        pnl.add(taoFormPhanCong(), BorderLayout.SOUTH);
        pnl.add(taoDanhSach(), BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoFormPhanCong() {
        JPanel pnlForm = taoKhung();
        pnlForm.setLayout(new BorderLayout(10, 12));

        JLabel lblTieuDe = taoNhan("THÔNG TIN PHÂN CÔNG", 15);
        pnlForm.add(lblTieuDe, BorderLayout.NORTH);

        txtMaPhanCong = new JTextField("PC0001");
        txtMaPhanCong.setEditable(false);
        txtMaPhanCong.setBackground(new Color(240, 244, 248));

        cboNhaSi = new JComboBox<>(new String[] {
            "Chọn nha sĩ",
            "NS0001 - Nguyễn Minh Tuấn",
            "NS0002 - Trần Hoàng Nam",
            "NS0003 - Lê Thị Mai"
        });

        cboBenhNhan = new JComboBox<>(new String[] {
            "Chọn bệnh nhân",
            "BN0001 - Nguyễn Văn An",
            "BN0002 - Trần Thị Bích",
            "BN0003 - Lê Minh Hoàng",
            "BN0004 - Phạm Thu Hà"
        });

        cboPhong = new JComboBox<>(new String[] {
            "Chọn phòng",
            "P001 - Phòng điều trị 1",
            "P002 - Phòng điều trị 2",
            "P003 - Phòng X-quang"
        });

        cboLichHen = new JComboBox<>(new String[] {
            "Chọn lịch hẹn",
            "LH0001 - 08:00 20/10/2026",
            "LH0002 - 09:00 20/10/2026",
            "LH0003 - 14:00 20/10/2026"
        });

        txtNgayPhanCong = new JTextField(
            dinhDangNgay.format(new Date())
        );
        txtNgayPhanCong.setEditable(false);
        txtNgayPhanCong.setHorizontalAlignment(SwingConstants.LEFT);

        JButton btnChonNgay = taoNutIcon("Chọn ngày");
        btnChonNgay.setPreferredSize(new Dimension(85, 32));
        btnChonNgay.addActionListener(e -> chonNgay());

        JPanel pnlNgay = new JPanel(new BorderLayout(6, 0));
        pnlNgay.setOpaque(false);
        pnlNgay.add(txtNgayPhanCong, BorderLayout.CENTER);
        pnlNgay.add(btnChonNgay, BorderLayout.EAST);

        cboTrangThai = new JComboBox<>(new String[] {
            "Chờ thực hiện",
            "Đã phân công",
            "Đang thực hiện",
            "Hoàn thành",
            "Đã hủy"
        });

        txtGhiChu = new JTextField();

        JPanel pnlTruong = new JPanel(new GridLayout(4, 2, 18, 10));
        pnlTruong.setOpaque(false);

        pnlTruong.add(taoTruong("Mã phân công", txtMaPhanCong));
        pnlTruong.add(taoTruong("Nha sĩ phụ trách", cboNhaSi));
        pnlTruong.add(taoTruong("Bệnh nhân", cboBenhNhan));
        pnlTruong.add(taoTruong("Phòng điều trị", cboPhong));
        pnlTruong.add(taoTruong("Lịch hẹn", cboLichHen));
        pnlTruong.add(taoTruong("Ngày phân công", pnlNgay));
        pnlTruong.add(taoTruong("Trạng thái", cboTrangThai));
        pnlTruong.add(taoTruong("Ghi chú", txtGhiChu));

        pnlForm.add(pnlTruong, BorderLayout.CENTER);

        JPanel pnlNut = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 0)
        );
        pnlNut.setOpaque(false);

        JButton btnThem = taoNut("Thêm phân công", new Color(15, 150, 137));
        JButton btnSua = taoNut("Cập nhật", MAU_XANH);
        JButton btnXoa = taoNut("Xóa", new Color(205, 72, 77));
        JButton btnLamMoi = taoNut(
            "Làm mới", new Color(100, 116, 139)
        );

        pnlNut.add(btnThem);
        pnlNut.add(btnSua);
        pnlNut.add(btnXoa);
        pnlNut.add(btnLamMoi);

        pnlForm.add(pnlNut, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> themPhanCong());
        btnSua.addActionListener(e -> suaPhanCong());
        btnXoa.addActionListener(e -> xoaPhanCong());
        btnLamMoi.addActionListener(e -> lamMoiForm());

        return pnlForm;
    }

    private JPanel taoDanhSach() {
        JPanel pnl = taoKhung();
        pnl.setLayout(new BorderLayout(10, 12));

        JLabel lblTieuDe = taoNhan("DANH SÁCH PHÂN CÔNG", 15);

        String[] cot = {
            "Mã PC",
            "Nha sĩ",
            "Bệnh nhân",
            "Phòng điều trị",
            "Lịch hẹn",
            "Ngày phân công",
            "Trạng thái"
        };

        mdlPhanCong = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        mdlPhanCong.addRow(new Object[] {
            "PC0001", "Nguyễn Minh Tuấn", "Nguyễn Văn An",
            "Phòng điều trị 1", "LH0001 - 08:00",
            "20/10/2026", "Đã phân công"
        });

        mdlPhanCong.addRow(new Object[] {
            "PC0002", "Trần Hoàng Nam", "Trần Thị Bích",
            "Phòng điều trị 2", "LH0002 - 09:00",
            "20/10/2026", "Chờ thực hiện"
        });

        mdlPhanCong.addRow(new Object[] {
            "PC0003", "Lê Thị Mai", "Lê Minh Hoàng",
            "Phòng X-quang", "LH0003 - 14:00",
            "20/10/2026", "Hoàn thành"
        });

        tblPhanCong = new JTable(mdlPhanCong);
        tblPhanCong.setRowHeight(32);
        tblPhanCong.setFont(new Font("Arial", Font.PLAIN, 12));
        tblPhanCong.setForeground(MAU_CHU);
        tblPhanCong.setSelectionBackground(new Color(215, 235, 250));
        tblPhanCong.setSelectionForeground(MAU_CHU);
        tblPhanCong.setGridColor(MAU_VIEN);
        tblPhanCong.setShowVerticalLines(false);
        tblPhanCong.setAutoCreateRowSorter(true);
        tblPhanCong.setFillsViewportHeight(true);

        tblPhanCong.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 12)
        );
        tblPhanCong.getTableHeader().setBackground(MAU_CHU);
        tblPhanCong.getTableHeader().setForeground(Color.WHITE);
        tblPhanCong.getTableHeader().setPreferredSize(
            new Dimension(0, 36)
        );

        boLoc = new TableRowSorter<>(mdlPhanCong);
        tblPhanCong.setRowSorter(boLoc);

        tblPhanCong.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()
                    && tblPhanCong.getSelectedRow() >= 0) {
                hienThiDongDuocChon();
            }
        });

        JScrollPane scr = new JScrollPane(tblPhanCong);
        scr.setBorder(BorderFactory.createEmptyBorder());

        txtTimKiem = new JTextField(20);
        txtTimKiem.setPreferredSize(new Dimension(220, 32));

        JButton btnTimKiem = taoNut("Tìm kiếm", MAU_XANH);
        JButton btnBoLoc = taoNut("Bỏ lọc", new Color(100, 116, 139));

        JPanel pnlTimKiem = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 0)
        );
        pnlTimKiem.setOpaque(false);
        pnlTimKiem.add(new JLabel("Tìm kiếm:"));
        pnlTimKiem.add(txtTimKiem);
        pnlTimKiem.add(btnTimKiem);
        pnlTimKiem.add(btnBoLoc);

        JPanel pnlDauBang = new JPanel(new BorderLayout(5, 10));
        pnlDauBang.setOpaque(false);
        pnlDauBang.add(lblTieuDe, BorderLayout.NORTH);
        pnlDauBang.add(pnlTimKiem, BorderLayout.CENTER);

        btnTimKiem.addActionListener(e -> timKiem());
        txtTimKiem.addActionListener(e -> timKiem());

        btnBoLoc.addActionListener(e -> {
            txtTimKiem.setText("");
            boLoc.setRowFilter(null);
        });

        pnl.add(pnlDauBang, BorderLayout.NORTH);
        pnl.add(scr, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoKhung() {
        JPanel pnl = new JPanel();
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(MAU_VIEN),
            new EmptyBorder(14, 14, 14, 14)
        ));
        return pnl;
    }

    private JLabel taoNhan(String noiDung, int coChu) {
        JLabel lbl = new JLabel(noiDung);
        lbl.setFont(new Font("Arial", Font.BOLD, coChu));
        lbl.setForeground(MAU_CHU);
        return lbl;
    }

    private JPanel taoTruong(String ten, JComponent input) {
        JPanel pnl = new JPanel(new BorderLayout(4, 5));
        pnl.setOpaque(false);

        JLabel lbl = taoNhan(ten, 12);

        input.setPreferredSize(new Dimension(200, 32));
        pnl.add(lbl, BorderLayout.NORTH);
        pnl.add(input, BorderLayout.CENTER);

        return pnl;
    }

    private JButton taoNut(String ten, Color mau) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mau);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(135, 34));
        return btn;
    }

    private JButton taoNutIcon(String ten) {
        JButton btn = new JButton(ten);
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setForeground(MAU_CHU);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void chonNgay() {
        JSpinner spnNgay = new JSpinner(
            new SpinnerDateModel(new Date(), null, null,
                java.util.Calendar.DAY_OF_MONTH)
        );
        spnNgay.setEditor(
            new JSpinner.DateEditor(spnNgay, "dd/MM/yyyy")
        );

        int luaChon = JOptionPane.showConfirmDialog(
            this,
            spnNgay,
            "Chọn ngày phân công",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (luaChon == JOptionPane.OK_OPTION) {
            Date ngay = (Date) spnNgay.getValue();
            txtNgayPhanCong.setText(dinhDangNgay.format(ngay));
        }
    }

    private boolean kiemTraDuLieu() {
        if (cboNhaSi.getSelectedIndex() == 0
                || cboBenhNhan.getSelectedIndex() == 0
                || cboPhong.getSelectedIndex() == 0
                || cboLichHen.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "Vui lòng chọn nha sĩ, bệnh nhân, phòng điều trị và lịch hẹn.",
                "Thiếu thông tin",
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        return true;
    }

    private void themPhanCong() {
        if (!kiemTraDuLieu()) {
            return;
        }

        String ma = txtMaPhanCong.getText().trim();

        for (int i = 0; i < mdlPhanCong.getRowCount(); i++) {
            if (ma.equals(mdlPhanCong.getValueAt(i, 0))) {
                ma = taoMaPhanCongMoi();
                break;
            }
        }

        mdlPhanCong.addRow(new Object[] {
            ma,
            layTen(cboNhaSi),
            layTen(cboBenhNhan),
            layTen(cboPhong),
            layTen(cboLichHen),
            txtNgayPhanCong.getText(),
            cboTrangThai.getSelectedItem()
        });

        JOptionPane.showMessageDialog(
            this,
            "Đã thêm phân công vào danh sách giao diện.\n"
            + "Dữ liệu chưa được lưu vào SQL Server."
        );

        lamMoiForm();
    }

    private String taoMaPhanCongMoi() {
        int lonNhat = 0;

        for (int i = 0; i < mdlPhanCong.getRowCount(); i++) {
            String ma = mdlPhanCong.getValueAt(i, 0).toString();

            try {
                int so = Integer.parseInt(ma.substring(2));
                if (so > lonNhat) {
                    lonNhat = so;
                }
            } catch (Exception ex) {
                // Bỏ qua mã không đúng định dạng
            }
        }

        return String.format("PC%04d", lonNhat + 1);
    }

    private String layTen(JComboBox<String> cbo) {
        String giaTri = String.valueOf(cbo.getSelectedItem());
        int viTri = giaTri.indexOf(" - ");

        if (viTri >= 0) {
            return giaTri.substring(viTri + 3);
        }

        return giaTri;
    }

    private void suaPhanCong() {
        int dongView = tblPhanCong.getSelectedRow();

        if (dongView < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Vui lòng chọn phân công cần cập nhật."
            );
            return;
        }

        if (!kiemTraDuLieu()) {
            return;
        }

        int dongModel = tblPhanCong.convertRowIndexToModel(dongView);

        mdlPhanCong.setValueAt(
            layTen(cboNhaSi), dongModel, 1
        );
        mdlPhanCong.setValueAt(
            layTen(cboBenhNhan), dongModel, 2
        );
        mdlPhanCong.setValueAt(
            layTen(cboPhong), dongModel, 3
        );
        mdlPhanCong.setValueAt(
            layTen(cboLichHen), dongModel, 4
        );
        mdlPhanCong.setValueAt(
            txtNgayPhanCong.getText(), dongModel, 5
        );
        mdlPhanCong.setValueAt(
            cboTrangThai.getSelectedItem(), dongModel, 6
        );

        JOptionPane.showMessageDialog(
            this,
            "Đã cập nhật dữ liệu trên giao diện.\n"
            + "Dữ liệu chưa được lưu vào SQL Server."
        );
    }

    private void xoaPhanCong() {
        int dongView = tblPhanCong.getSelectedRow();

        if (dongView < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Vui lòng chọn phân công cần xóa."
            );
            return;
        }

        int luaChon = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa phân công này không?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (luaChon == JOptionPane.YES_OPTION) {
            int dongModel = tblPhanCong.convertRowIndexToModel(dongView);
            mdlPhanCong.removeRow(dongModel);
            lamMoiForm();
        }
    }

    private void hienThiDongDuocChon() {
        int dongView = tblPhanCong.getSelectedRow();

        if (dongView < 0) {
            return;
        }

        int dong = tblPhanCong.convertRowIndexToModel(dongView);

        txtMaPhanCong.setText(
            mdlPhanCong.getValueAt(dong, 0).toString()
        );

        chonComboTheoTen(
            cboNhaSi, mdlPhanCong.getValueAt(dong, 1).toString()
        );
        chonComboTheoTen(
            cboBenhNhan, mdlPhanCong.getValueAt(dong, 2).toString()
        );
        chonComboTheoTen(
            cboPhong, mdlPhanCong.getValueAt(dong, 3).toString()
        );
        chonComboTheoTen(
            cboLichHen, mdlPhanCong.getValueAt(dong, 4).toString()
        );

        txtNgayPhanCong.setText(
            mdlPhanCong.getValueAt(dong, 5).toString()
        );

        cboTrangThai.setSelectedItem(
            mdlPhanCong.getValueAt(dong, 6).toString()
        );
    }

    private void chonComboTheoTen(
        JComboBox<String> cbo, String ten
    ) {
        for (int i = 1; i < cbo.getItemCount(); i++) {
            String giaTri = cbo.getItemAt(i);

            if (giaTri.contains(ten) || ten.contains(giaTri)) {
                cbo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void timKiem() {
        String tuKhoa = txtTimKiem.getText().trim();

        if (tuKhoa.isEmpty()) {
            boLoc.setRowFilter(null);
        } else {
            boLoc.setRowFilter(
                javax.swing.RowFilter.regexFilter(
                    "(?i)" + java.util.regex.Pattern.quote(tuKhoa)
                )
            );
        }
    }

    private void lamMoiForm() {
        txtMaPhanCong.setText(taoMaPhanCongMoi());
        cboNhaSi.setSelectedIndex(0);
        cboBenhNhan.setSelectedIndex(0);
        cboPhong.setSelectedIndex(0);
        cboLichHen.setSelectedIndex(0);
        txtNgayPhanCong.setText(dinhDangNgay.format(new Date()));
        cboTrangThai.setSelectedIndex(0);
        txtGhiChu.setText("");
        tblPhanCong.clearSelection();
    }
}
