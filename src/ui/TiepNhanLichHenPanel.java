package ui;

import dao.LichHenDAO;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TiepNhanLichHenPanel extends JPanel
        implements ActionListener, MouseListener {

    private JTextField codeField;
    private JTextField maBenhNhanField;
    private JTextField patientField;
    private JComboBox<String> roomCombo;
    private JButton btnChonBenhNhan;

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

    private LichHenDAO lichHenDAO = new LichHenDAO();
    private DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm");

    public TiepNhanLichHenPanel() {
        setLayout(new BorderLayout());
        setBackground(UIHelper.BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createContent(), BorderLayout.CENTER);

        saveBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnChonBenhNhan.addActionListener(this);

        table.addMouseListener(this);

        refreshData();
    }

    // ==================== HÀM CÔNG KHAI ĐỂ GỌI LÀM MỚI TỪ BÊN NGOÀI ====================
    public void refreshData() {
        layDanhSachBacSiTuCSDL();
        layDanhSachDichVuTuCSDL();
        layDanhSachPhongTuCSDL();
        layDanhSachLichHenTuCSDL();
    }

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
        JLabel subTitle = new JLabel("Quản lý tiếp nhận và lịch hẹn phòng khám");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subTitle.setForeground(UIHelper.MUTED);
        toolbar.add(subTitle, BorderLayout.WEST);
        p.add(toolbar, BorderLayout.NORTH);

        String[] cols = {
                "STT", "Mã LH", "Mã bệnh nhân", "Tên bệnh nhân", "Phòng điều trị",
                "Ngày hẹn", "Giờ hẹn", "Bác sĩ", "Dịch vụ", "Trạng thái"
        };

        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
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

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(UIHelper.NAVY);
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        int[] columnWidths = {45, 80, 110, 160, 130, 105, 80, 160, 140, 120};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        int[] centerColumns = {0, 1, 2, 5, 6};
        for (int column : centerColumns) {
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }

        JScrollPane scroll = new JScrollPane(table);
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

        codeField = new JTextField();
        codeField.setEditable(false);
        codeField.setBackground(new Color(245, 248, 252));

        JPanel pnlMaBN = new JPanel(new BorderLayout(4, 0));
        pnlMaBN.setBackground(Color.WHITE);
        maBenhNhanField = new JTextField();
        maBenhNhanField.setEditable(false);
        maBenhNhanField.setBackground(new Color(245, 248, 252));
        btnChonBenhNhan = new JButton("Chọn / Thêm BN");
        btnChonBenhNhan.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btnChonBenhNhan.setBackground(UIHelper.NAVY);
        btnChonBenhNhan.setForeground(Color.WHITE);
        pnlMaBN.add(maBenhNhanField, BorderLayout.CENTER);
        pnlMaBN.add(btnChonBenhNhan, BorderLayout.EAST);

        patientField = new JTextField();
        patientField.setEditable(false);
        patientField.setBackground(new Color(245, 248, 252));

        roomCombo = new JComboBox<>();
        UIHelper.styleComboBox(roomCombo);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateChooser.setBackground(Color.WHITE);
        dateChooser.setDate(new Date());

        JPanel timePanel = new JPanel(new GridLayout(1, 2, 6, 0));
        timePanel.setBackground(Color.WHITE);
        cboHour = new JComboBox<>(new String[]{"07", "08", "09", "10", "11", "13", "14", "15", "16", "17", "18", "19"});
        UIHelper.styleComboBox(cboHour);
        cboMinute = new JComboBox<>(new String[]{"00", "15", "30", "45"});
        UIHelper.styleComboBox(cboMinute);
        timePanel.add(cboHour);
        timePanel.add(cboMinute);

        dentistCombo = new JComboBox<>();
        UIHelper.styleComboBox(dentistCombo);
        serviceCombo = new JComboBox<>();
        UIHelper.styleComboBox(serviceCombo);
        statusCombo = new JComboBox<>(new String[]{"Chọn trạng thái", "Đã đặt", "Đang chờ khám", "Hoàn thành", "Hủy", "Không đến"});
        UIHelper.styleComboBox(statusCombo);

        formGrid.add(createFieldWrapper("Mã lịch hẹn", codeField));
        formGrid.add(createFieldWrapper("Mã bệnh nhân *", pnlMaBN));
        formGrid.add(createFieldWrapper("Tên bệnh nhân", patientField));

        formGrid.add(createFieldWrapper("Phòng điều trị *", roomCombo));
        formGrid.add(createFieldWrapper("Ngày hẹn *", dateChooser));
        formGrid.add(createFieldWrapper("Giờ hẹn *", timePanel));

        formGrid.add(createFieldWrapper("Bác sĩ phụ trách *", dentistCombo));
        formGrid.add(createFieldWrapper("Dịch vụ *", serviceCombo));
        formGrid.add(createFieldWrapper("Trạng thái", statusCombo));

        p.add(formGrid, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
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

    private String sinhMaTuDong() {
        try {
            List<model.LichHen> ds = lichHenDAO.getDanhSachLichHenModel();
            if (ds == null || ds.isEmpty()) return "LH000001";
            int max = 0;
            for (model.LichHen lh : ds) {
                try {
                    int so = Integer.parseInt(lh.getMaLichHen().replaceAll("\\D+", ""));
                    if (so > max) max = so;
                } catch (Exception ignored) {}
            }
            return String.format("LH%06d", max + 1);
        } catch (Exception e) {
            return "LH000001";
        }
    }

    public void clearForm() {
        table.clearSelection();
        codeField.setText(sinhMaTuDong());
        maBenhNhanField.setText("");
        patientField.setText("");
        if (roomCombo.getItemCount() > 0) roomCombo.setSelectedIndex(0);
        dateChooser.setDate(new Date());
        cboHour.setSelectedIndex(0);
        cboMinute.setSelectedIndex(0);
        if (dentistCombo.getItemCount() > 0) dentistCombo.setSelectedIndex(0);
        if (serviceCombo.getItemCount() > 0) serviceCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
    }

    private void hienThiThongTinLenForm() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return;

        int r = table.convertRowIndexToModel(selectedRow);
        codeField.setText(String.valueOf(model.getValueAt(r, 1)));
        maBenhNhanField.setText(String.valueOf(model.getValueAt(r, 2)));
        patientField.setText(String.valueOf(model.getValueAt(r, 3)));
        
        setSelectedComboBoxByText(roomCombo, String.valueOf(model.getValueAt(r, 4)));

        try {
            dateChooser.setDate(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(model.getValueAt(r, 5))));
        } catch (Exception ex) {
            dateChooser.setDate(new Date());
        }

        try {
            String[] parts = String.valueOf(model.getValueAt(r, 6)).split(":");
            if (parts.length == 2) {
                cboHour.setSelectedItem(parts[0]);
                cboMinute.setSelectedItem(parts[1]);
            }
        } catch (Exception ex) {
            cboHour.setSelectedIndex(0);
            cboMinute.setSelectedIndex(0);
        }

        setSelectedComboBoxByText(dentistCombo, String.valueOf(model.getValueAt(r, 7)));
        setSelectedComboBoxByText(serviceCombo, String.valueOf(model.getValueAt(r, 8)));
        statusCombo.setSelectedItem(String.valueOf(model.getValueAt(r, 9)));
    }

    private void setSelectedComboBoxByText(JComboBox<String> combo, String text) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).contains(text)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == saveBtn) {
            xuLyLuuLichHen();
        } else if (source == deleteBtn) {
            xuLyXoaLichHen();
        } else if (source == cancelBtn) {
            clearForm();
        } else if (source == btnChonBenhNhan) {
            xuLyMoDialogChonBenhNhan();
        }
    }

    private void xuLyMoDialogChonBenhNhan() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        Chon_ThemBNDialog dialog = new Chon_ThemBNDialog(parent);
        dialog.setVisible(true);

        String maBN = dialog.getMaBenhNhanChon();
        String tenBN = dialog.getTenBenhNhanChon();
        if (maBN != null && !maBN.isEmpty()) {
            maBenhNhanField.setText(maBN);
            patientField.setText(tenBN);
        }
    }

    @Override public void mouseClicked(MouseEvent e) { hienThiThongTinLenForm(); }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private void layDanhSachBacSiTuCSDL() {
        dentistCombo.removeAllItems();
        dentistCombo.addItem("Chọn bác sĩ phụ trách");
        try {
            for (Map<String, String> ns : lichHenDAO.getDanhSachNhaSi()) {
                dentistCombo.addItem(ns.get("HoTen") + " (" + ns.get("MaNV") + ")");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void layDanhSachDichVuTuCSDL() {
        serviceCombo.removeAllItems();
        serviceCombo.addItem("Chọn dịch vụ");
        try {
            for (Map<String, String> dv : lichHenDAO.getDanhSachDichVu()) {
                serviceCombo.addItem(dv.get("TenDV") + " (" + dv.get("MaDV") + ")");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void layDanhSachPhongTuCSDL() {
        roomCombo.removeAllItems();
        roomCombo.addItem("Chọn phòng điều trị");
        try {
            for (Map<String, String> p : lichHenDAO.getDanhSachPhongDieuTri()) {
                roomCombo.addItem(p.get("TenPhong") + " (" + p.get("MaPhong") + ")");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void layDanhSachLichHenTuCSDL() {
        model.setRowCount(0);
        try {
            int stt = 1;
            for (Map<String, Object> map : lichHenDAO.getAllLichHen()) {
                LocalDate ngay = (LocalDate) map.get("NgayHen");
                LocalDateTime gio = (LocalDateTime) map.get("GioHen");
                model.addRow(new Object[]{
                        stt++,
                        map.get("MaLichHen"),
                        map.get("MaBenhNhan"),
                        map.get("TenBenhNhan"),
                        map.get("TenPhong"),
                        ngay != null ? ngay.format(dtfDate) : "",
                        gio != null ? gio.format(dtfTime) : "",
                        map.get("TenBacSi"),
                        map.get("TenDV"),
                        map.get("TrangThai")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
        capNhatTongSo();
        codeField.setText(sinhMaTuDong());
    }

    private void xuLyLuuLichHen() {
        String maLH = codeField.getText().trim();
        String maBN = maBenhNhanField.getText().trim();
        
        if (maBN.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc thêm bệnh nhân trước khi lưu lịch hẹn!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhong = extractMa(roomCombo);
        String maNV = extractMa(dentistCombo);
        String maDV = extractMa(serviceCombo);

        LocalDate ngayHen = dateChooser.getDate() != null
                ? dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : LocalDate.now();

        LocalDateTime gioHen = LocalDateTime.of(
                ngayHen.getYear(), ngayHen.getMonth(), ngayHen.getDayOfMonth(),
                Integer.parseInt((String) cboHour.getSelectedItem()),
                Integer.parseInt((String) cboMinute.getSelectedItem())
        );

        String trangThai = statusCombo.getSelectedIndex() == 0 ? "Đã đặt" : String.valueOf(statusCombo.getSelectedItem());

        if (roomCombo.getSelectedIndex() == 0 || dentistCombo.getSelectedIndex() == 0 || serviceCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ Phòng, Bác sĩ và Dịch vụ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean kq;
            if (table.getSelectedRow() >= 0) {
                kq = lichHenDAO.capNhatLichHen(maLH, maBN, maDV, maPhong, maNV, ngayHen, gioHen, trangThai, patientField.getText());
                JOptionPane.showMessageDialog(this, kq ? "Cập nhật lịch hẹn thành công." : "Cập nhật thất bại!");
            } else {
                kq = lichHenDAO.themLichHen(maLH, maBN, maDV, maPhong, maNV, ngayHen, gioHen, trangThai, patientField.getText());
                JOptionPane.showMessageDialog(this, kq ? "Thêm lịch hẹn thành công." : "Thêm thất bại (Trùng mã lịch hẹn)!");
            }
            if (kq) {
                layDanhSachLichHenTuCSDL();
                clearForm();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String extractMa(JComboBox<String> combo) {
        if (combo.getSelectedIndex() <= 0) return "";
        String val = String.valueOf(combo.getSelectedItem());
        if (val.contains("(") && val.contains(")")) {
            return val.substring(val.lastIndexOf("(") + 1, val.lastIndexOf(")")).trim();
        }
        return "";
    }

    private void xuLyXoaLichHen() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch hẹn cần xóa từ bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa lịch hẹn này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maLH = String.valueOf(model.getValueAt(table.convertRowIndexToModel(selectedRow), 1));
            try {
                if (lichHenDAO.xoaLichHen(maLH)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa lịch hẹn thành công.");
                    layDanhSachLichHenTuCSDL();
                    clearForm();
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void capNhatTongSo() {
        totalLabel.setText("Tổng số: " + model.getRowCount() + " lịch hẹn");
    }
}