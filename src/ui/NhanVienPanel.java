package ui;

import utils.UIHelper;
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

public class NhanVienPanel extends JPanel implements ActionListener, MouseListener {

    private JTextField codeField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;

    private JComboBox<String> positionCombo;
    private JComboBox<String> statusCombo;

    private JTable table;
    private DefaultTableModel model;

    private JLabel totalLabel;

    private JButton addBtn;
    private JButton saveBtn;
    private JButton deleteBtn; // Khai báo nút Xóa
    private JButton cancelBtn;

    public NhanVienPanel() {
        setLayout(new BorderLayout());
        setBackground(UIHelper.BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createContent(), BorderLayout.CENTER);

        // Đăng ký sự kiện cho các nút bấm và bảng dữ liệu
        addBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        deleteBtn.addActionListener(this); // Đăng ký sự kiện nút Xóa
        cancelBtn.addActionListener(this);
        table.addMouseListener(this);
    }

    private JPanel createContent() {
        JPanel outer = new JPanel(new BorderLayout(15, 0));
        outer.setBackground(UIHelper.BG);

        JPanel left = new JPanel(new BorderLayout(0, 12));
        left.setBackground(UIHelper.BG);
        left.add(createTablePanel(), BorderLayout.CENTER);

        JPanel right = createFormPanel();
        right.setPreferredSize(new Dimension(380, 0));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.72);
        split.setDividerLocation(0.72);
        split.setDividerSize(6);
        split.setBorder(null);
        split.setBackground(UIHelper.BG);
        split.setContinuousLayout(true);

        outer.add(split, BorderLayout.CENTER);
        return outer;
    }

    private JPanel createTablePanel() {
        JPanel p = UIHelper.createCardPanel();
        p.setLayout(new BorderLayout(0, 12));
        p.setBorder(new CompoundBorder(new LineBorder(UIHelper.BORDER, 1, true), BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0), "Danh sách nhân viên",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14), UIHelper.NAVY)));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);

        JLabel subTitle = new JLabel("Quản lý thông tin nhân sự phòng khám");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subTitle.setForeground(UIHelper.MUTED);
        toolbar.add(subTitle, BorderLayout.WEST);

        addBtn = new JButton("Thêm nhân viên");
        UIHelper.styleButton(addBtn, true, FontAwesomeSolid.USER_PLUS);
        toolbar.add(addBtn, BorderLayout.EAST);

        p.add(toolbar, BorderLayout.NORTH);

        String[] cols = {"STT", "Mã NV", "Họ và tên", "Số điện thoại", "Email", "Chức vụ", "Trạng thái"};

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
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(UIHelper.BORDER, 1, true));
        scroll.getViewport().setBackground(Color.WHITE);
        p.add(scroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        totalLabel = new JLabel("Tổng số: 0 nhân viên");
        totalLabel.setForeground(UIHelper.MUTED);
        totalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.add(totalLabel, BorderLayout.WEST);
        p.add(footer, BorderLayout.SOUTH);

        return p;
    }

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

        codeField = new JTextField();
        addField(form, "Mã nhân viên *", codeField);

        nameField = new JTextField();
        addField(form, "Họ và tên *", nameField);

        phoneField = new JTextField();
        addField(form, "Số điện thoại *", phoneField);

        emailField = new JTextField();
        addField(form, "Email", emailField);

        positionCombo = addCombo(form, "Chức vụ *", new String[]{
                "Chọn chức vụ", "Lễ tân", "Bác sĩ", "Phụ tá", "Quản lý"
        });

        statusCombo = addCombo(form, "Trạng thái", new String[]{
                "Chọn trạng thái", "Đang làm việc", "Tạm nghỉ", "Đã nghỉ việc"
        });

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scroll, BorderLayout.CENTER);

        // Chia thanh chứa nút thành 3 cột (Lưu, Xóa, Hủy) dùng GridLayout(1, 3)
        JPanel actions = new JPanel(new GridLayout(1, 3, 6, 0));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(8, 0, 0, 0));

        saveBtn = new JButton("Lưu");
        deleteBtn = new JButton("Xóa");
        cancelBtn = new JButton("Hủy");

        UIHelper.styleButton(saveBtn, true, FontAwesomeSolid.SAVE);
        UIHelper.styleButton(deleteBtn, false, FontAwesomeSolid.TRASH_ALT);
        UIHelper.styleButton(cancelBtn, false, FontAwesomeSolid.TIMES);

        // Thiết kế riêng màu chữ đỏ cảnh báo cho nút Xóa
        deleteBtn.setForeground(new Color(215, 38, 56));

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

    public void clearForm() {
        table.clearSelection();
        codeField.setText("");
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        positionCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        codeField.requestFocus();
    }

    private void hienThiThongTinLenForm() {
        int r = table.convertRowIndexToModel(table.getSelectedRow());
        codeField.setText(String.valueOf(model.getValueAt(r, 1)));
        nameField.setText(String.valueOf(model.getValueAt(r, 2)));
        phoneField.setText(String.valueOf(model.getValueAt(r, 3)));
        emailField.setText(String.valueOf(model.getValueAt(r, 4)));
        positionCombo.setSelectedItem(String.valueOf(model.getValueAt(r, 5)));
        statusCombo.setSelectedItem(String.valueOf(model.getValueAt(r, 6)));
    }

    // =========================================================================
    // XỬ LÝ SỰ KIỆN NÚT BẤM (ACTIONLISTENER)
    // =========================================================================
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(addBtn)) {
            clearForm();
        } else if (source.equals(saveBtn)) {
            xuLyLuuNhanVien();
        } else if (source.equals(deleteBtn)) {
            xuLyXoaNhanVien(); // Xử lý khi nhấn nút Xóa
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

    private void xuLyLuuNhanVien() {
        String maNV = codeField.getText().trim();
        String hoTen = nameField.getText().trim();
        String sdt = phoneField.getText().trim();

        if (maNV.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || positionCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc (*).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Khung logic lưu dữ liệu sẵn sàng.");
    }

    // Khung hàm xử lý sự kiện Xóa nhân viên
    private void xuLyXoaNhanVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa từ bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa nhân viên này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Viết lệnh DAO / SQL Server để xóa dữ liệu ở đây

            // Xóa dòng trên giao diện bảng tạm thời
            model.removeRow(row);
            clearForm();
            JOptionPane.showMessageDialog(this, "Đã xóa nhân viên thành công.");
        }
    }
}