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

public class TiepNhanLichHenPanel extends JPanel implements ActionListener, MouseListener {

    // =========================================================================
    // KHAI BÁO TẤT CẢ CÁC BIẾN VÀ THÀNH PHẦN GIAO DIỆN O PHÍA TRÊN
    // =========================================================================
    private JTextField codeField;
    private JTextField patientField;
    private JTextField phoneField;
    private JDateChooser dateChooser; // Chọn ngày bằng JCalendar

    // Chọn giờ và phút bằng ComboBox
    private JComboBox<String> cboHour;
    private JComboBox<String> cboMinute;

    // Chọn Bác sĩ phụ trách bằng ComboBox (Lấy dữ liệu từ CSDL)
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

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // =========================================================================
    // HÀM KHỞI TẠO (CONSTRUCTOR)
    // =========================================================================
    public TiepNhanLichHenPanel() {
        setLayout(new BorderLayout());
        setBackground(UIHelper.BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Thêm nội dung chính vào giữa panel
        add(createContent(), BorderLayout.CENTER);

        // Đăng ký bộ lắng nghe sự kiện cho các nút bấm và bảng dữ liệu
        addBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        table.addMouseListener(this);

        // Nạp dữ liệu ban đầu từ CSDL khi giao diện khởi tạo
        layDanhSachBacSiTuCSDL();
        layDanhSachLichHenTuCSDL();
    }

    // Tạo bố cục tổng thể chia làm 2 phần: Bảng danh sách bên trái và Form bên phải
    private JPanel createContent() {
        JPanel outer = new JPanel(new BorderLayout(15, 0));
        outer.setBackground(UIHelper.BG);

        JPanel left = new JPanel(new BorderLayout(0, 12));
        left.setBackground(UIHelper.BG);
        left.add(createTablePanel(), BorderLayout.CENTER);

        JPanel right = createFormPanel();
        right.setPreferredSize(new Dimension(420, 0));

        // Dùng JSplitPane phân chia không gian linh hoạt
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.68);
        split.setDividerLocation(0.68);
        split.setDividerSize(6);
        split.setBorder(null);
        split.setBackground(UIHelper.BG);
        split.setContinuousLayout(true);

        outer.add(split, BorderLayout.CENTER);
        return outer;
    }

    // Tạo khu vực chứa bảng hiển thị danh sách lịch hẹn
    private JPanel createTablePanel() {
        JPanel p = UIHelper.createCardPanel();
        p.setLayout(new BorderLayout(0, 12));
        p.setBorder(new CompoundBorder(new LineBorder(UIHelper.BORDER, 1, true), BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0), "Danh sách lịch hẹn",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14), UIHelper.NAVY)));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);

        JLabel subTitle = new JLabel("Quản lý tiếp nhận và lịch hẹn phòng khám");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subTitle.setForeground(UIHelper.MUTED);
        toolbar.add(subTitle, BorderLayout.WEST);

        addBtn = new JButton("Thêm lịch hẹn");
        UIHelper.styleButton(addBtn, true, FontAwesomeSolid.CALENDAR_PLUS);
        toolbar.add(addBtn, BorderLayout.EAST);

        p.add(toolbar, BorderLayout.NORTH);

        String[] cols = {"STT", "Mã LH", "Tên bệnh nhân", "SĐT", "Ngày hẹn", "Giờ hẹn", "Bác sĩ", "Dịch vụ", "Trạng thái"};

        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(UIHelper.TEXT);
        table.setBackground(Color.WHITE);
        table.setRowHeight(42);
        table.setGridColor(new Color(230, 240, 250));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(220, 235, 252));
        table.setSelectionForeground(UIHelper.NAVY);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(UIHelper.NAVY);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

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

    // Tạo khu vực Form nhập liệu chi tiết thông tin lịch hẹn ở bên phải
    private JPanel createFormPanel() {
        JPanel p = UIHelper.createCardPanel();
        p.setLayout(new BorderLayout(0, 12));
        p.setBorder(new CompoundBorder(new LineBorder(UIHelper.BORDER, 1, true), BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0), "Thông tin chi tiết",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14), UIHelper.NAVY)));

        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        codeField = new JTextField("LH001");
        codeField.setEditable(false);
        codeField.setBackground(new Color(245, 248, 252));
        addField(form, "Mã lịch hẹn", codeField);

        patientField = new JTextField();
        addField(form, "Tên bệnh nhân *", patientField);

        phoneField = new JTextField();
        addField(form, "Số điện thoại *", phoneField);

        // Khởi tạo JDateChooser chọn ngày hẹn
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateChooser.setBackground(Color.WHITE);
        dateChooser.setDate(new Date());
        addCustomRow(form, "Ngày hẹn *", dateChooser);

        // Chọn Giờ & Phút bằng 2 ComboBox
        JPanel timePanel = new JPanel(new GridLayout(1, 2, 8, 0));
        timePanel.setBackground(Color.WHITE);

        cboHour = new JComboBox<>(new String[]{
                "07", "08", "09", "10", "11", "13", "14", "15", "16", "17", "18", "19"
        });
        UIHelper.styleComboBox(cboHour);

        cboMinute = new JComboBox<>(new String[]{
                "00", "15", "30", "45"
        });
        UIHelper.styleComboBox(cboMinute);

        timePanel.add(cboHour);
        timePanel.add(cboMinute);
        addCustomRow(form, "Giờ hẹn * (Giờ : Phút)", timePanel);

        // JComboBox chọn Bác sĩ phụ trách
        dentistCombo = new JComboBox<>();
        UIHelper.styleComboBox(dentistCombo);
        addCustomRow(form, "Bác sĩ phụ trách *", dentistCombo);

        serviceCombo = addCombo(form, "Dịch vụ *", new String[]{
                "Chọn dịch vụ", "Khám tổng quát", "Lấy cao răng", "Trám răng", "Tẩy trắng răng"
        });

        statusCombo = addCombo(form, "Trạng thái", new String[]{
                "Chọn trạng thái", "Chờ xác nhận", "Đã xác nhận", "Đã hoàn thành", "Đã hủy"
        });

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scroll, BorderLayout.CENTER);

        // Các nút thao tác chức năng ở đáy form: Lưu, Xóa, Hủy
        JPanel actions = new JPanel(new GridLayout(1, 3, 6, 0));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(8, 0, 0, 0));

        saveBtn = new JButton("Lưu");
        deleteBtn = new JButton("Xóa");
        cancelBtn = new JButton("Hủy");

        UIHelper.styleButton(saveBtn, true, FontAwesomeSolid.SAVE);
        UIHelper.styleButton(deleteBtn, false, FontAwesomeSolid.TRASH_ALT);
        UIHelper.styleButton(cancelBtn, false, FontAwesomeSolid.TIMES);

        deleteBtn.setForeground(new Color(215, 38, 56)); // Màu đỏ cảnh báo nút Xóa

        actions.add(saveBtn);
        actions.add(deleteBtn);
        actions.add(cancelBtn);
        p.add(actions, BorderLayout.SOUTH);

        return p;
    }

    private void addField(JPanel parent, String label, JTextField field) {
        JPanel wrap = new JPanel(new BorderLayout(0, 4));
        wrap.setBackground(Color.WHITE);
        wrap.setBorder(new EmptyBorder(0, 0, 10, 0));
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(UIHelper.TEXT);
        UIHelper.styleTextField(field);
        wrap.add(l, BorderLayout.NORTH);
        wrap.add(field, BorderLayout.CENTER);
        wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        parent.add(wrap);
    }

    private void addCustomRow(JPanel parent, String label, JComponent comp) {
        JPanel wrap = new JPanel(new BorderLayout(0, 4));
        wrap.setBackground(Color.WHITE);
        wrap.setBorder(new EmptyBorder(0, 0, 10, 0));
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(UIHelper.TEXT);
        wrap.add(l, BorderLayout.NORTH);
        wrap.add(comp, BorderLayout.CENTER);
        wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        parent.add(wrap);
    }

    private JComboBox<String> addCombo(JPanel parent, String label, String[] options) {
        JPanel wrap = new JPanel(new BorderLayout(0, 4));
        wrap.setBackground(Color.WHITE);
        wrap.setBorder(new EmptyBorder(0, 0, 10, 0));
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(UIHelper.TEXT);
        JComboBox<String> combo = new JComboBox<>(options);
        UIHelper.styleComboBox(combo);
        wrap.add(l, BorderLayout.NORTH);
        wrap.add(combo, BorderLayout.CENTER);
        wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        parent.add(wrap);
        return combo;
    }

    // Làm sạch form nhập liệu
    public void clearForm() {
        table.clearSelection();
        codeField.setText(String.format("LH%03d", model.getRowCount() + 1));
        patientField.setText("");
        phoneField.setText("");
        dateChooser.setDate(new Date());
        cboHour.setSelectedIndex(0);
        cboMinute.setSelectedIndex(0);
        if (dentistCombo.getItemCount() > 0) dentistCombo.setSelectedIndex(0);
        serviceCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        patientField.requestFocus();
    }

    // Đổ dữ liệu từ dòng được chọn trong bảng lên Form
    private void hienThiThongTinLenForm() {
        int r = table.convertRowIndexToModel(table.getSelectedRow());
        codeField.setText(String.valueOf(model.getValueAt(r, 1)));
        patientField.setText(String.valueOf(model.getValueAt(r, 2)));
        phoneField.setText(String.valueOf(model.getValueAt(r, 3)));

        try {
            String dateStr = String.valueOf(model.getValueAt(r, 4));
            dateChooser.setDate(sdf.parse(dateStr));
        } catch (Exception ex) {
            dateChooser.setDate(new Date());
        }

        try {
            String timeStr = String.valueOf(model.getValueAt(r, 5));
            String[] parts = timeStr.split(":");
            if (parts.length == 2) {
                cboHour.setSelectedItem(parts[0]);
                cboMinute.setSelectedItem(parts[1]);
            }
        } catch (Exception ex) {
            cboHour.setSelectedIndex(0);
            cboMinute.setSelectedIndex(0);
        }

        dentistCombo.setSelectedItem(String.valueOf(model.getValueAt(r, 6)));
        serviceCombo.setSelectedItem(String.valueOf(model.getValueAt(r, 7)));
        statusCombo.setSelectedItem(String.valueOf(model.getValueAt(r, 8)));
    }

    // =========================================================================
    // XỬ LÝ SỰ KIỆN NÚT BẤM (ACTIONLISTENER TRUYỀN THỐNG)
    // =========================================================================
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(addBtn)) {
            clearForm();
        } else if (source.equals(saveBtn)) {
            xuLyLuuLichHen();
        } else if (source.equals(deleteBtn)) {
            xuLyXoaLichHen();
        } else if (source.equals(cancelBtn)) {
            clearForm();
        }
    }

    // =========================================================================
    // XỬ LÝ SỰ KIỆN CHUỘT TRÊN BẢNG (MOUSELISTENER)
    // =========================================================================
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            hienThiThongTinLenForm();
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // =========================================================================
    // CÁC HÀM KHUNG XỬ LÝ NGHIỆP VỤ & KẾT NỐI CSDL (KẾT NỐI VỚI DAO/SQL)
    // =========================================================================

    /**
     * Tải danh sách Bác sĩ từ CSDL và nạp vào ComboBox dentistCombo
     */
    private void layDanhSachBacSiTuCSDL() {
        dentistCombo.removeAllItems();
        dentistCombo.addItem("Chọn bác sĩ phụ trách");

        try {
            // TODO: Viết code gọi DAO tại đây
            // NhanVien_DAO nvDAO = new NhanVien_DAO();
            // List<NhanVien> listBS = nvDAO.getDanhSachBacSi();
            // for (NhanVien bs : listBS) {
            //     dentistCombo.addItem(bs.getHoTen());
            // }

            // [Dữ liệu giả lập chạy thử]
            dentistCombo.addItem("BS. Nguyễn Văn Tuấn");
            dentistCombo.addItem("BS. Trần Thị Hương");
            dentistCombo.addItem("BS. Lê Minh Hoàng");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tải toàn bộ danh sách lịch hẹn từ CSDL và nạp vào Table Model
     */
    private void layDanhSachLichHenTuCSDL() {
        model.setRowCount(0); // Xóa dữ liệu cũ trên bảng

        try {
            // TODO: Viết code gọi DAO tại đây
            // LichHen_DAO lhDAO = new LichHen_DAO();
            // List<LichHen> listLH = lhDAO.getDanhSachLichHen();
            // int stt = 1;
            // for (LichHen lh : listLH) {
            //     model.addRow(new Object[]{
            //         stt++, lh.getMaLichHen(), lh.getTenBenhNhan(), lh.getSdt(),
            //         sdf.format(lh.getNgayHen()), lh.getGioHen(), lh.getTenBacSi(),
            //         lh.getDichVu(), lh.getTrangThai()
            //     });
            // }

            // [Dữ liệu giả lập chạy thử]
            model.addRow(new Object[]{"1", "LH001", "Nguyễn Minh Anh", "0901234567", "18/10/2026", "08:00", "BS. Nguyễn Văn Tuấn", "Khám tổng quát", "Đã xác nhận"});
            model.addRow(new Object[]{"2", "LH002", "Trần Gia Hân", "0912345678", "19/10/2026", "09:00", "BS. Trần Thị Hương", "Lấy cao răng", "Chờ xác nhận"});
            model.addRow(new Object[]{"3", "LH003", "Lê Hoàng Nam", "0923456789", "20/10/2026", "10:30", "BS. Nguyễn Văn Tuấn", "Trám răng", "Đã xác nhận"});

            totalLabel.setText("Tổng số: " + model.getRowCount() + " lịch hẹn");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xử lý nghiệp vụ Lưu thông tin Lịch hẹn (Thêm mới hoặc Cập nhật vào CSDL)
     */
    private void xuLyLuuLichHen() {
        String maLH = codeField.getText().trim();
        String benhNhan = patientField.getText().trim();
        String sdt = phoneField.getText().trim();
        String ngay = (dateChooser.getDate() != null) ? sdf.format(dateChooser.getDate()) : "";
        String gio = cboHour.getSelectedItem() + ":" + cboMinute.getSelectedItem();
        String bacSi = (dentistCombo.getSelectedIndex() > 0) ? String.valueOf(dentistCombo.getSelectedItem()) : "";

        // Kiểm tra dữ liệu đầu vào (Validation)
        if (benhNhan.isEmpty() || sdt.isEmpty() || ngay.isEmpty() ||
                bacSi.isEmpty() || serviceCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn và nhập đầy đủ các trường bắt buộc (*).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // TODO: Gọi DAO để thực hiện câu lệnh UPDATE trong SQL Server
            // LichHen lh = new LichHen(maLH, benhNhan, sdt, dateChooser.getDate(), gio, bacSi, serviceCombo.getSelectedItem().toString(), statusCombo.getSelectedItem().toString());
            // if (lhDAO.capNhatLichHen(lh)) { ... }

            int r = table.convertRowIndexToModel(selectedRow);
            model.setValueAt(maLH, r, 1);
            model.setValueAt(benhNhan, r, 2);
            model.setValueAt(sdt, r, 3);
            model.setValueAt(ngay, r, 4);
            model.setValueAt(gio, r, 5);
            model.setValueAt(bacSi, r, 6);
            model.setValueAt(serviceCombo.getSelectedItem(), r, 7);
            model.setValueAt(statusCombo.getSelectedIndex() == 0 ? "Chờ xác nhận" : statusCombo.getSelectedItem(), r, 8);
            JOptionPane.showMessageDialog(this, "Đã cập nhật lịch hẹn thành công.");
        } else {
            // TODO: Gọi DAO để thực hiện câu lệnh INSERT INTO trong SQL Server
            // LichHen lh = new LichHen(maLH, benhNhan, sdt, dateChooser.getDate(), gio, bacSi, serviceCombo.getSelectedItem().toString(), "Chờ xác nhận");
            // if (lhDAO.themLichHen(lh)) { ... }

            String trangThai = statusCombo.getSelectedIndex() == 0 ? "Chờ xác nhận" : (String) statusCombo.getSelectedItem();
            Object[] rowData = {
                    String.valueOf(model.getRowCount() + 1),
                    maLH, benhNhan, sdt, ngay, gio, bacSi, serviceCombo.getSelectedItem(), trangThai
            };
            model.addRow(rowData);
            JOptionPane.showMessageDialog(this, "Đã thêm lịch hẹn thành công.");
        }

        totalLabel.setText("Tổng số: " + model.getRowCount() + " lịch hẹn");
        clearForm();
    }

    /**
     * Xử lý nghiệp vụ Xóa Lịch hẹn khỏi CSDL
     */
    private void xuLyXoaLichHen() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch hẹn cần xóa từ bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
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
            int r = table.convertRowIndexToModel(row);
            String maLH = String.valueOf(model.getValueAt(r, 1));

            // TODO: Gọi DAO để thực hiện câu lệnh DELETE FROM trong SQL Server
            // if (lhDAO.xoaLichHen(maLH)) { ... }

            model.removeRow(r);
            totalLabel.setText("Tổng số: " + model.getRowCount() + " lịch hẹn");
            clearForm();
            JOptionPane.showMessageDialog(this, "Đã xóa lịch hẹn thành công.");
        }
    }
}