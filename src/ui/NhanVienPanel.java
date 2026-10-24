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
    private JButton deleteBtn;
    private JButton cancelBtn;

    public NhanVienPanel() {
        setLayout(new BorderLayout());
        setBackground(UIHelper.BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createContent(), BorderLayout.CENTER);

        // Đăng ký sự kiện cho các nút bấm và bảng dữ liệu
        addBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        table.addMouseListener(this);
    }

    // Bố cục tổng thể: Khung thông tin chi tiết ở TRÊN (NORTH), Khung danh sách ở DƯỚI (CENTER)
    private JPanel createContent() {
        JPanel outer = new JPanel(new BorderLayout(0, 15));
        outer.setBackground(UIHelper.BG);

        JPanel topPanel = createFormPanel();
        // Cố định chiều cao vừa vặn cho khung thông tin chi tiết ở phía trên
        topPanel.setPreferredSize(new Dimension(0, 210));

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

        // Tự động phân bổ độ rộng các cột trải đều
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        int[] columnWidths = {50, 90, 180, 120, 180, 140, 140};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

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

    // Khung form thông tin chi tiết bố trí dạng lưới ngang (2 hàng, 4 cột)
    private JPanel createFormPanel() {
        JPanel p = UIHelper.createCardPanel();
        p.setLayout(new BorderLayout(0, 8));
        p.setBorder(new CompoundBorder(new LineBorder(UIHelper.BORDER, 1, true), BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0), "Thông tin chi tiết",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14), UIHelper.NAVY)));

        JPanel formGrid = new JPanel(new GridLayout(2, 4, 15, 6));
        formGrid.setBackground(Color.WHITE);
        formGrid.setBorder(new EmptyBorder(2, 10, 2, 10));

        codeField = new JTextField();
        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        positionCombo = new JComboBox<>(new String[]{
                "Chọn chức vụ", "Lễ tân", "Bác sĩ", "Phụ tá", "Quản lý"
        });
        UIHelper.styleComboBox(positionCombo);

        statusCombo = new JComboBox<>(new String[]{
                "Chọn trạng thái", "Đang làm việc", "Tạm nghỉ", "Đã nghỉ việc"
        });
        UIHelper.styleComboBox(statusCombo);

        // Thêm các trường vào lưới hiển thị
        formGrid.add(createFieldWrapper("Mã nhân viên *", codeField));
        formGrid.add(createFieldWrapper("Họ và tên *", nameField));
        formGrid.add(createFieldWrapper("Số điện thoại *", phoneField));
        formGrid.add(createFieldWrapper("Email", emailField));
        formGrid.add(createFieldWrapper("Chức vụ *", positionCombo));
        formGrid.add(createFieldWrapper("Trạng thái", statusCombo));

        p.add(formGrid, BorderLayout.CENTER);

        // Các nút thao tác chức năng đặt gọn ở góc phải phía dưới form
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(0, 0, 0, 5));

        saveBtn = new JButton("Lưu");
        deleteBtn = new JButton("Xóa");
        cancelBtn = new JButton("Hủy");

        UIHelper.styleButton(saveBtn, true, FontAwesomeSolid.SAVE);
        UIHelper.styleButton(deleteBtn, false, FontAwesomeSolid.TRASH_ALT);
        UIHelper.styleButton(cancelBtn, false, FontAwesomeSolid.TIMES);
        deleteBtn.setForeground(new Color(215, 38, 56));

        saveBtn.setPreferredSize(new Dimension(85, 30));
        deleteBtn.setPreferredSize(new Dimension(85, 30));
        cancelBtn.setPreferredSize(new Dimension(85, 30));

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
        if (comp instanceof JTextField) {
            UIHelper.styleTextField((JTextField) comp);
        }
        wrap.add(l, BorderLayout.NORTH);
        wrap.add(comp, BorderLayout.CENTER);
        return wrap;
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
            xuLyXoaNhanVien();
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
            model.removeRow(row);
            clearForm();
            JOptionPane.showMessageDialog(this, "Đã xóa nhân viên thành công.");
        }
    }
}