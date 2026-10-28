package ui;

import connectDB.ConnectDB;
import utils.UIHelper;
import com.toedter.calendar.JDateChooser;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TiepNhanLichHenPanel extends JPanel
        implements ActionListener, MouseListener {

    // ==================== KHAI BÁO BIẾN ====================

    private JTextField codeField;
    private JTextField maBenhNhanField;
    private JTextField patientField;
    private JTextField phoneField;

    private JDateChooser dateChooser;

    private JComboBox<String> cboHour;
    private JComboBox<String> cboMinute;

    private JComboBox<String> dentistCombo;
    private JComboBox<String> serviceCombo;
    private JComboBox<String> statusCombo;

    private JTable table;
    private DefaultTableModel model;

    private JLabel totalLabel;

    private JButton saveBtn;
    private JButton deleteBtn;
    private JButton cancelBtn;

    private final SimpleDateFormat sdf =
            new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat sqlDateFormat =
            new SimpleDateFormat("yyyy-MM-dd");

    // ==================== CONSTRUCTOR ====================

    public TiepNhanLichHenPanel() {
        setLayout(new BorderLayout());
        setBackground(UIHelper.BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createContent(), BorderLayout.CENTER);

        saveBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        table.addMouseListener(this);

        // Lắng nghe sự kiện thay đổi ngày hoặc giờ để lọc bác sĩ theo ca từ CSDL
        dangKySuKienLocBacSi();

        layDanhSachLichHenTuCSDL();
    }

    // ==================== HÀM KẾT NỐI CSDL ====================

    private Connection getConnection() throws Exception {
        Connection con = ConnectDB.getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Không thể thiết lập kết nối tới CSDL.");
        }
        return con;
    }

    // ==================== BỐ CỤC TỔNG THỂ ====================

    private JPanel createContent() {
        JPanel outer = new JPanel(new BorderLayout(0, 15));
        outer.setBackground(UIHelper.BG);

        JPanel topPanel = createFormPanel();
        topPanel.setPreferredSize(new Dimension(0, 255));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(UIHelper.BG);
        bottomPanel.add(createTablePanel(), BorderLayout.CENTER);

        outer.add(topPanel, BorderLayout.NORTH);
        outer.add(bottomPanel, BorderLayout.CENTER);

        return outer;
    }

    // ==================== BẢNG DANH SÁCH LỊCH HẸN ====================

    private JPanel createTablePanel() {
        JPanel p = UIHelper.createCardPanel();
        p.setLayout(new BorderLayout(0, 12));

        p.setBorder(new CompoundBorder(
                new LineBorder(UIHelper.BORDER, 1, true),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(0, 0, 0, 0),
                        "Danh sách lịch hẹn",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        UIHelper.NAVY
                )
        ));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);

        JLabel subTitle =
                new JLabel("Quản lý tiếp nhận và lịch hẹn phòng khám");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subTitle.setForeground(UIHelper.MUTED);

        toolbar.add(subTitle, BorderLayout.WEST);
        p.add(toolbar, BorderLayout.NORTH);

        String[] cols = {
                "STT",
                "Mã LH",
                "Mã bệnh nhân",
                "Tên bệnh nhân",
                "SĐT",
                "Ngày hẹn",
                "Giờ hẹn",
                "Bác sĩ",
                "Dịch vụ",
                "Trạng thái"
        };

        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(UIHelper.TEXT);
        table.setBackground(Color.WHITE);
        table.setRowHeight(36);
        table.setGridColor(new Color(230, 240, 250));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(220, 235, 252));
        table.setSelectionForeground(UIHelper.NAVY);

        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(UIHelper.NAVY);
        table.getTableHeader().setPreferredSize(
                new Dimension(0, 36)
        );
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        int[] columnWidths = {
                45, 80, 110, 160, 110,
                105, 80, 160, 140, 120
        };

        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(columnWidths[i]);
        }

        int[] centerColumns = {0, 1, 2, 4, 5, 6};

        for (int column : centerColumns) {
            table.getColumnModel()
                    .getColumn(column)
                    .setCellRenderer(centerRenderer);
        }

        JScrollPane scroll = new JScrollPane(
                table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        scroll.setBorder(new LineBorder(UIHelper.BORDER, 1, true));
        scroll.getViewport().setBackground(Color.WHITE);

        p.add(scroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);

        totalLabel = new JLabel("Tổng số: 0 lịch hẹn");
        totalLabel.setForeground(UIHelper.MUTED);
        totalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        footer.add(totalLabel, BorderLayout.WEST);
        p.add(footer, BorderLayout.SOUTH);

        return p;
    }

    // ==================== FORM THÔNG TIN ====================

    private JPanel createFormPanel() {
        JPanel p = UIHelper.createCardPanel();
        p.setLayout(new BorderLayout(0, 8));

        p.setBorder(new CompoundBorder(
                new LineBorder(UIHelper.BORDER, 1, true),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(0, 0, 0, 0),
                        "Thông tin chi tiết",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        UIHelper.NAVY
                )
        ));

        JPanel formGrid = new JPanel(new GridLayout(3, 3, 15, 6));
        formGrid.setBackground(Color.WHITE);
        formGrid.setBorder(new EmptyBorder(2, 10, 2, 10));

        codeField = new JTextField("LH001");
        codeField.setEditable(false);
        codeField.setBackground(new Color(245, 248, 252));

        maBenhNhanField = new JTextField("BN001");
        maBenhNhanField.setEditable(false);
        maBenhNhanField.setBackground(new Color(245, 248, 252));

        patientField = new JTextField();
        phoneField = new JTextField();

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateChooser.setBackground(Color.WHITE);
        dateChooser.setDate(new Date());

        JPanel timePanel = new JPanel(new GridLayout(1, 2, 6, 0));
        timePanel.setBackground(Color.WHITE);

        cboHour = new JComboBox<>(new String[]{
                "07", "08", "09", "10", "11",
                "13", "14", "15", "16", "17", "18", "19"
        });
        UIHelper.styleComboBox(cboHour);

        cboMinute = new JComboBox<>(
                new String[]{"00", "15", "30", "45"}
        );
        UIHelper.styleComboBox(cboMinute);

        timePanel.add(cboHour);
        timePanel.add(cboMinute);

        dentistCombo = new JComboBox<>();
        UIHelper.styleComboBox(dentistCombo);

        serviceCombo = new JComboBox<>(new String[]{
                "Chọn dịch vụ",
                "Khám tổng quát",
                "Lấy cao răng",
                "Trám răng",
                "Tẩy trắng răng"
        });
        UIHelper.styleComboBox(serviceCombo);

        statusCombo = new JComboBox<>(new String[]{
                "Chọn trạng thái",
                "Chờ xác nhận",
                "Đã xác nhận",
                "Đã hoàn thành",
                "Đã hủy"
        });
        UIHelper.styleComboBox(statusCombo);

        formGrid.add(createFieldWrapper("Mã lịch hẹn", codeField));
        formGrid.add(createFieldWrapper("Mã bệnh nhân", maBenhNhanField));
        formGrid.add(createFieldWrapper("Tên bệnh nhân *", patientField));

        formGrid.add(createFieldWrapper("Số điện thoại *", phoneField));
        formGrid.add(createFieldWrapper("Ngày hẹn *", dateChooser));
        formGrid.add(createFieldWrapper("Giờ hẹn *", timePanel));

        formGrid.add(createFieldWrapper("Bác sĩ phụ trách *", dentistCombo));
        formGrid.add(createFieldWrapper("Dịch vụ *", serviceCombo));
        formGrid.add(createFieldWrapper("Trạng thái", statusCombo));

        p.add(formGrid, BorderLayout.CENTER);

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(0, 0, 0, 5));

        saveBtn = new JButton("Lưu");
        deleteBtn = new JButton("Xóa");
        cancelBtn = new JButton("Làm Mới");

        UIHelper.styleButton(saveBtn, true, FontAwesomeSolid.SAVE);
        UIHelper.styleButton(deleteBtn, false, FontAwesomeSolid.TRASH_ALT);
        UIHelper.styleButton(cancelBtn, false, FontAwesomeSolid.TIMES);

        deleteBtn.setForeground(new Color(215, 38, 56));

        saveBtn.setPreferredSize(new Dimension(85, 30));
        deleteBtn.setPreferredSize(new Dimension(85, 30));
        cancelBtn.setPreferredSize(new Dimension(115, 30));

        actions.add(saveBtn);
        actions.add(deleteBtn);
        actions.add(cancelBtn);

        p.add(actions, BorderLayout.SOUTH);

        return p;
    }

    private JPanel createFieldWrapper(String label, JComponent comp) {
        JPanel wrap = new JPanel(new BorderLayout(0, 2));
        wrap.setBackground(Color.WHITE);

        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(UIHelper.TEXT);

        wrap.add(l, BorderLayout.NORTH);
        wrap.add(comp, BorderLayout.CENTER);

        return wrap;
    }

    // ==================== XỬ LÝ LỌC BÁC SĨ TỪ BẢNG LICHCHAMVIỆC / LICHVIỆC TRONG CSDL ====================

    private void dangKySuKienLocBacSi() {
        // Lắng nghe khi đổi ngày
        dateChooser.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                capNhatDanhSachBacSiTheoCa();
            }
        });

        // Lắng nghe khi đổi giờ (để xác định ca Sáng / Chiều / Tối)
        cboHour.addActionListener(e -> capNhatDanhSachBacSiTheoCa());
    }

    private void capNhatDanhSachBacSiTheoCa() {
        dentistCombo.removeAllItems();
        dentistCombo.addItem("Chọn bác sĩ phụ trách");

        Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) return;

        String hourStr = (String) cboHour.getSelectedItem();
        if (hourStr == null) return;

        int hour = Integer.parseInt(hourStr);

        // Xác định chuỗi ca làm việc tương ứng với dữ liệu lưu trong bảng LichLamViec
        // Ca sáng: 07:00 - 11:59 | Ca chiều: 12:00 - 17:59 | Ca tối: từ 18:00 trở đi
        String caLamViecText = "";
        if (hour >= 7 && hour < 12) {
            caLamViecText = "Ca sáng (07:30 - 11:30)";
        } else if (hour >= 12 && hour < 18) {
            caLamViecText = "Ca chiều (13:30 - 17:30)";
        } else {
            caLamViecText = "Ca tối (18:00 - 21:00)";
        }

        String ngaySql = sqlDateFormat.format(selectedDate);

        // Truy vấn CSDL lấy danh sách nhân viên được phân công vào ngày và ca đó
        String sql = "SELECT nv.MaNV, nv.HoTen FROM LichLamViec llv " +
                "JOIN NhanVien nv ON llv.MaNhanVien = nv.MaNV " +
                "WHERE llv.NgayPhanCong = ? AND llv.CaLamViec = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ngaySql);
            pstmt.setString(2, caLamViecText);

            ResultSet rs = pstmt.executeQuery();
            while (rs != null && rs.next()) {
                String hoTen = rs.getString("HoTen").trim();
                dentistCombo.addItem(hoTen);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== LÀM MỚI FORM ====================

    public void clearForm() {
        table.clearSelection();

        int nextId = model.getRowCount() + 1;
        codeField.setText(String.format("LH%03d", nextId));
        maBenhNhanField.setText(String.format("BN%03d", nextId));

        patientField.setText("");
        phoneField.setText("");

        dateChooser.setDate(new Date());

        cboHour.setSelectedIndex(0);
        cboMinute.setSelectedIndex(0);

        capNhatDanhSachBacSiTheoCa();

        serviceCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);

        patientField.requestFocus();
    }

    // ==================== HIỂN THỊ DÒNG ĐƯỢC CHỌN ====================

    private void hienThiThongTinLenForm() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }

        int r = table.convertRowIndexToModel(selectedRow);

        codeField.setText(String.valueOf(model.getValueAt(r, 1)));
        maBenhNhanField.setText(String.valueOf(model.getValueAt(r, 2)));
        patientField.setText(String.valueOf(model.getValueAt(r, 3)));
        phoneField.setText(String.valueOf(model.getValueAt(r, 4)));

        try {
            String dateStr = String.valueOf(model.getValueAt(r, 5));
            dateChooser.setDate(sdf.parse(dateStr));
        } catch (Exception ex) {
            dateChooser.setDate(new Date());
        }

        try {
            String timeStr = String.valueOf(model.getValueAt(r, 6));
            String[] parts = timeStr.split(":");

            if (parts.length == 2) {
                cboHour.setSelectedItem(parts[0]);
                cboMinute.setSelectedItem(parts[1]);
            }
        } catch (Exception ex) {
            cboHour.setSelectedIndex(0);
            cboMinute.setSelectedIndex(0);
        }

        dentistCombo.setSelectedItem(
                String.valueOf(model.getValueAt(r, 7))
        );
        serviceCombo.setSelectedItem(
                String.valueOf(model.getValueAt(r, 8))
        );
        statusCombo.setSelectedItem(
                String.valueOf(model.getValueAt(r, 9))
        );
    }

    // ==================== SỰ KIỆN NÚT ====================

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == saveBtn) {
            xuLyLuuLichHen();
        } else if (source == deleteBtn) {
            xuLyXoaLichHen();
        } else if (source == cancelBtn) {
            clearForm();
        }
    }

    // ==================== SỰ KIỆN BẢNG ====================

    @Override
    public void mouseClicked(MouseEvent e) {
        hienThiThongTinLenForm();
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // ==================== DỮ LIỆU LỊCH HẸN ====================

    private void layDanhSachLichHenTuCSDL() {
        model.setRowCount(0);
        capNhatTongSo();
        clearForm();
    }

    // ==================== THÊM / SỬA ====================

    private void xuLyLuuLichHen() {
        String maLH = codeField.getText().trim();
        String maBN = maBenhNhanField.getText().trim();
        String benhNhan = patientField.getText().trim();
        String sdt = phoneField.getText().trim();

        String ngay = dateChooser.getDate() != null
                ? sdf.format(dateChooser.getDate()) : "";

        String gio = cboHour.getSelectedItem()
                + ":" + cboMinute.getSelectedItem();

        String bacSi = dentistCombo.getSelectedIndex() > 0
                ? String.valueOf(dentistCombo.getSelectedItem()) : "";

        if (benhNhan.isEmpty()
                || sdt.isEmpty() || ngay.isEmpty()
                || bacSi.isEmpty()
                || serviceCombo.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ các trường bắt buộc (*).",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
            int r = table.convertRowIndexToModel(selectedRow);

            model.setValueAt(maLH, r, 1);
            model.setValueAt(maBN, r, 2);
            model.setValueAt(benhNhan, r, 3);
            model.setValueAt(sdt, r, 4);
            model.setValueAt(ngay, r, 5);
            model.setValueAt(gio, r, 6);
            model.setValueAt(bacSi, r, 7);
            model.setValueAt(serviceCombo.getSelectedItem(), r, 8);

            model.setValueAt(
                    statusCombo.getSelectedIndex() == 0
                            ? "Chờ xác nhận"
                            : statusCombo.getSelectedItem(),
                    r, 9
            );

            JOptionPane.showMessageDialog(
                    this, "Cập nhật lịch hẹn thành công."
            );

        } else {
            Object[] rowData = {
                    model.getRowCount() + 1,
                    maLH,
                    maBN,
                    benhNhan,
                    sdt,
                    ngay,
                    gio,
                    bacSi,
                    serviceCombo.getSelectedItem(),
                    statusCombo.getSelectedIndex() == 0
                            ? "Chờ xác nhận"
                            : statusCombo.getSelectedItem()
            };

            model.addRow(rowData);

            JOptionPane.showMessageDialog(
                    this, "Thêm lịch hẹn thành công."
            );
        }

        capNhatTongSo();
        clearForm();
    }

    // ==================== XÓA LỊCH HẸN ====================

    private void xuLyXoaLichHen() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn lịch hẹn cần xóa từ bảng!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa lịch hẹn này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int r = table.convertRowIndexToModel(selectedRow);
            model.removeRow(r);

            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(i + 1, i, 0);
            }

            capNhatTongSo();
            clearForm();

            JOptionPane.showMessageDialog(
                    this, "Đã xóa lịch hẹn thành công."
            );
        }
    }

    private void capNhatTongSo() {
        totalLabel.setText(
                "Tổng số: " + model.getRowCount() + " lịch hẹn"
        );
    }
}