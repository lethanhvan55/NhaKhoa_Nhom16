package ui;
import dao.DichVu_DAO;
import model.DichVu;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Chon_DichVuDialog extends JDialog {

    private JPanel pnlDanhSach;
    private Map<String, JCheckBox> dsCheckBox;

    private List<String> dsMaDVDaChon;
    private boolean daXacNhan = false;

    public Chon_DichVuDialog(
            Frame parent,
            List<String> dsDaChon) {

        super(parent, "Chọn dịch vụ áp dụng", true);

        dsCheckBox = new LinkedHashMap<>();
        dsMaDVDaChon = new ArrayList<>(dsDaChon);

        khoiTaoGiaoDien();
        taiDanhSachDichVu();

        setSize(450, 500);
        setLocationRelativeTo(parent);
    }

    private void khoiTaoGiaoDien() {

        setLayout(new BorderLayout(10, 10));

        JLabel lblTieuDe =
                new JLabel("CHỌN DỊCH VỤ ÁP DỤNG");

        lblTieuDe.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblTieuDe.setFont(
                new Font("Arial", Font.BOLD, 18));

        add(lblTieuDe, BorderLayout.NORTH);

        pnlDanhSach = new JPanel();

        pnlDanhSach.setLayout(
                new BoxLayout(pnlDanhSach, BoxLayout.Y_AXIS));

        JScrollPane scroll =
                new JScrollPane(pnlDanhSach);

        add(scroll, BorderLayout.CENTER);

        JPanel pnlNut = new JPanel(
                new FlowLayout(FlowLayout.RIGHT));

        JButton btnXacNhan = new JButton("Xác nhận");
        JButton btnHuy = new JButton("Hủy");

        btnXacNhan.addActionListener(e -> xacNhan());
        btnHuy.addActionListener(e -> dispose());

        pnlNut.add(btnHuy);
        pnlNut.add(btnXacNhan);

        add(pnlNut, BorderLayout.SOUTH);
    }

    private void taiDanhSachDichVu() {

        // Lấy danh sách dịch vụ từ DAO
        DichVu_DAO dao = new DichVu_DAO();
        List<DichVu> dsDichVu = dao.getAllDichVu();

        for (DichVu dv : dsDichVu) {

            // Chỉ hiển thị dịch vụ đang cung cấp
            if (!"Đang cung cấp".equalsIgnoreCase(
                    dv.getTrangThai())) {
                continue;
            }

            String maDV = dv.getMaDV();

            JCheckBox chk = new JCheckBox(
                    dv.getTenDV() + " (" + maDV + ")");

            // Tích sẵn dịch vụ đã chọn trước đó
            chk.setSelected(
                    dsMaDVDaChon.contains(maDV));

            dsCheckBox.put(maDV, chk);

            pnlDanhSach.add(chk);
        }

        pnlDanhSach.revalidate();
        pnlDanhSach.repaint();
    }

    private void xacNhan() {

        dsMaDVDaChon.clear();

        for (Map.Entry<String, JCheckBox> entry
                : dsCheckBox.entrySet()) {

            if (entry.getValue().isSelected()) {
                dsMaDVDaChon.add(entry.getKey());
            }
        }

        daXacNhan = true;
        dispose();
    }

    public boolean isDaXacNhan() {
        return daXacNhan;
    }

    public List<String> getDanhSachMaDVDaChon() {
        return new ArrayList<>(dsMaDVDaChon);
    }
}
