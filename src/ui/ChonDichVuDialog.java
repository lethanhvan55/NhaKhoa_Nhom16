package ui;

import dao.DichVu_DAO;
import model.DichVu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class ChonDichVuDialog extends JDialog implements ActionListener {

    private JTable tblChonDichVu;
    private DefaultTableModel model;
    private JSpinner spnSoLuong;

    private JButton btnXacNhan;
    private JButton btnDong;

    private List<DichVu> danhSach;
    private DichVu dichVuDuocChon;
    private int soLuongDuocChon;

    private DichVu_DAO dichVuDAO;

    public ChonDichVuDialog() {
        setTitle("Chọn dịch vụ");
        setSize(650, 400);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 10));

        dichVuDAO = new DichVu_DAO();
        danhSach = dichVuDAO.getAllDichVu();

        taoGiaoDien();
        dangKySuKien();
        taiDanhSachDichVu();

        setLocationRelativeTo(null);
    }

    private void taoGiaoDien() {
        model = new DefaultTableModel(
                new Object[]{"Mã dịch vụ", "Tên dịch vụ", "Đơn giá", "Trạng thái"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblChonDichVu = new JTable(model);
        tblChonDichVu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblChonDichVu.setRowHeight(28);

        spnSoLuong = new JSpinner(
                new SpinnerNumberModel(1, 1, 100, 1)
        );

        btnXacNhan = new JButton("Thêm vào hóa đơn");
        btnDong = new JButton("Đóng");

        JPanel pnlSoLuong = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSoLuong.add(new JLabel("Số lượng:"));
        pnlSoLuong.add(spnSoLuong);

        JPanel pnlTrungTam = new JPanel(new BorderLayout(5, 10));
        pnlTrungTam.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        pnlTrungTam.add(new JScrollPane(tblChonDichVu), BorderLayout.CENTER);
        pnlTrungTam.add(pnlSoLuong, BorderLayout.SOUTH);

        JPanel pnlNut = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlNut.add(btnXacNhan);
        pnlNut.add(btnDong);

        add(pnlTrungTam, BorderLayout.CENTER);
        add(pnlNut, BorderLayout.SOUTH);
    }

    private void dangKySuKien() {
        btnXacNhan.addActionListener(this);
        btnDong.addActionListener(this);
    }


    private void taiDanhSachDichVu() {
        model.setRowCount(0);

        if (danhSach == null || danhSach.isEmpty()) {
            return;
        }

        for (DichVu dv : danhSach) {
            model.addRow(new Object[]{
                    dv.getMaDV().trim(),
                    dv.getTenDV(),
                    dv.getDonGia(),
                    dv.getTrangThai()
            });
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnXacNhan) {
            xuLyXacNhan();
        } else if (e.getSource() == btnDong) {
            dispose();
        }
    }


    private void xuLyXacNhan() {
        int row = tblChonDichVu.getSelectedRow();

        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn dịch vụ cần thêm.",
                    "Thông báo",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = tblChonDichVu.convertRowIndexToModel(row);
        String maDV = model.getValueAt(modelRow, 0).toString().trim();

        dichVuDuocChon = null;

        if (danhSach != null) {
            for (DichVu dv : danhSach) {
                if (dv.getMaDV().trim().equals(maDV)) {
                    dichVuDuocChon = dv;
                    break;
                }
            }
        }

        if (dichVuDuocChon == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Không tìm thấy dịch vụ đã chọn.", "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        soLuongDuocChon = (Integer) spnSoLuong.getValue();
        dispose();
    }


    public DichVu getDichVuDuocChon() {
        return dichVuDuocChon;
    }

    public int getSoLuongDuocChon() {
        return soLuongDuocChon;
    }
}