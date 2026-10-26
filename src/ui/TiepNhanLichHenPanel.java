package ui;

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

    private JButton addBtn;
    private JButton saveBtn;
    private JButton deleteBtn;
    private JButton cancelBtn;

    private final SimpleDateFormat sdf =
            new SimpleDateFormat("dd/MM/yyyy");

    // ==================== CONSTRUCTOR ====================

    public TiepNhanLichHenPanel() {
        setLayout(new BorderLayout());
        setBackground(UIHelper.BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createContent(), BorderLayout.CENTER);

        addBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        table.addMouseListener(this);

        layDanhSachBacSiTuCSDL();
        layDanhSachLichHenTuCSDL();
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

        addBtn = new JButton("Thêm lịch hẹn");
        UIHelper.styleButton(
                addBtn, true, FontAwesomeSolid.CALENDAR_PLUS
        );

        toolbar.add(addBtn, BorderLayout.EAST);
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

        maBenhNhanField = new JTextField();
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
        formGrid.add(createFieldWrapper("Mã bệnh nhân *", maBenhNhanField));
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

        // Đã tăng chiều rộng nút Làm Mới lên 115px để vừa khít chữ
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

    // ==================== LÀM MỚI FORM ====================

    public void clearForm() {
        table.clearSelection();

        codeField.setText(
                String.format("LH%03d", model.getRowCount() + 1)
        );

        maBenhNhanField.setText("");
        patientField.setText("");
        phoneField.setText("");

        dateChooser.setDate(new Date());

        cboHour.setSelectedIndex(0);
        cboMinute.setSelectedIndex(0);

        if (dentistCombo.getItemCount() > 0) {
            dentistCombo.setSelectedIndex(0);
        }

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

        if (source == addBtn) {
            clearForm();
        } else if (source == saveBtn) {
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

    // ==================== DỮ LIỆU BÁC SĨ ====================

    private void layDanhSachBacSiTuCSDL() {
        dentistCombo.removeAllItems();

        dentistCombo.addItem("Chọn bác sĩ phụ trách");
        dentistCombo.addItem("BS. Nguyễn Văn Tuấn");
        dentistCombo.addItem("BS. Trần Thị Hương");
        dentistCombo.addItem("BS. Lê Minh Hoàng");
    }

    // ==================== DỮ LIỆU LỊCH HẸN MẪU ====================

    private void layDanhSachLichHenTuCSDL() {
        model.setRowCount(0);
        capNhatTongSo();
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

        if (maBN.isEmpty() || benhNhan.isEmpty()
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