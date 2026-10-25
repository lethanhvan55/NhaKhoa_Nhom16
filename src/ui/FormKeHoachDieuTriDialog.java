package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import dao.KeHoachDieuTri_DAO;
import dao.BenhNhan_DAO;
import dao.DichVu_DAO;
import model.BenhNhan;
import model.KeHoachDieuTri;
import model.DichVu;
import utils.GiaoDienStyle;

public class FormKeHoachDieuTriDialog extends JDialog implements ActionListener {

    private Color mauChuDao = GiaoDienStyle.MAU_NAVY;
    private Color mauNen = GiaoDienStyle.MAU_NEN;
    private Color mauTrang = GiaoDienStyle.MAU_TRANG;

    private JTextField txtMaKH;
    private JComboBox<String> cboBenhNhan;
    private JTextField txtChanDoan;
    private JDateChooser dcNgayBatDau;
    private JDateChooser dcNgayKetThuc;
    private JComboBox<String> cboTrangThai;

    private JComboBox<String> cboDichVu;
    private JSpinner spnSoLuong;
    private JButton btnThemDV;
    private JButton btnXoaDV;

    private JTable tblDichVu;
    private DefaultTableModel modelDichVu;
    private JLabel lblTongTien;

    private JButton btnLuu;
    private JButton btnHuy;

    private DecimalFormat df = new DecimalFormat("#,##0");
    private double tongChiPhi = 0;
    private KeHoachDieuTri_DAO khDao = new KeHoachDieuTri_DAO();
    private boolean isThemMoi = true;

    public FormKeHoachDieuTriDialog(JFrame parent) {
        super(parent, "Lập Kế Hoạch Điều Trị Mới", true);
        this.isThemMoi = true;
        khoiTaoGiaoDien();
        dangKySuKien();
        loadDataCombobox();
        txtMaKH.setText(sinhMaTuDong());
        txtMaKH.setEditable(false);
    }

    public FormKeHoachDieuTriDialog(JFrame parent, String maKH) {
        super(parent, "Chỉnh Sửa Kế Hoạch Điều Trị", true);
        this.isThemMoi = false;
        khoiTaoGiaoDien();
        dangKySuKien();
        loadDataCombobox();
        loadDuLieuLenForm(maKH);
        txtMaKH.setEditable(false);
    }

    private void khoiTaoGiaoDien() {
        setSize(850, 620);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.setBackground(mauNen);
        pnlMain.setBorder(new EmptyBorder(15, 15, 15, 15));

        pnlMain.add(taoFormThongTinChung(), BorderLayout.NORTH);
        pnlMain.add(taoKhuVucDichVu(), BorderLayout.CENTER);
        pnlMain.add(taoPanelNutThaoTac(), BorderLayout.SOUTH);

        add(pnlMain, BorderLayout.CENTER);
    }

    private JPanel taoFormThongTinChung() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(mauTrang);
        pnl.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Thông tin chung kế hoạch",
                                TitledBorder.DEFAULT_JUSTIFICATION,
                                TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 14),
                                mauChuDao
                        )
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaKH = new JTextField();
        txtMaKH.setPreferredSize(new Dimension(180, 30));

        cboBenhNhan = new JComboBox<>();
        cboBenhNhan.setPreferredSize(new Dimension(180, 30));

        txtChanDoan = new JTextField();
        txtChanDoan.setPreferredSize(new Dimension(180, 30));

        dcNgayBatDau = new JDateChooser();
        dcNgayBatDau.setDateFormatString("dd/MM/yyyy");
        dcNgayBatDau.setPreferredSize(new Dimension(180, 30));
        dcNgayBatDau.setDate(new Date());

        dcNgayKetThuc = new JDateChooser();
        dcNgayKetThuc.setDateFormatString("dd/MM/yyyy");
        dcNgayKetThuc.setPreferredSize(new Dimension(180, 30));
        dcNgayKetThuc.setDate(new Date());

        cboTrangThai = new JComboBox<>(new String[]{
                "Chờ duyệt", "Đang điều trị", "Hoàn thành", "Đã hủy"
        });
        cboTrangThai.setPreferredSize(new Dimension(180, 30));

        themTruongForm(pnl, gbc, "Mã kế hoạch:", txtMaKH, 0, 0);
        themTruongForm(pnl, gbc, "Bệnh nhân *:", cboBenhNhan, 2, 0);
        themTruongForm(pnl, gbc, "Chẩn đoán *:", txtChanDoan, 0, 1);
        themTruongForm(pnl, gbc, "Ngày bắt đầu *:", dcNgayBatDau, 2, 1);
        themTruongForm(pnl, gbc, "Ngày kết thúc *:", dcNgayKetThuc, 0, 2);
        themTruongForm(pnl, gbc, "Trạng thái:", cboTrangThai, 2, 2);

        return pnl;
    }

    private void themTruongForm(JPanel pnl, GridBagConstraints gbc, String label, JComponent comp, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(60, 80, 100));
        pnl.add(lbl, gbc);

        gbc.gridx = x + 1;
        gbc.weightx = 1;
        pnl.add(comp, gbc);
    }

    private JPanel taoKhuVucDichVu() {
        JPanel pnl = new JPanel(new BorderLayout(5, 8));
        pnl.setBackground(mauTrang);
        pnl.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(218, 228, 238), 1, true),
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                                "Chi tiết dịch vụ điều trị",
                                TitledBorder.DEFAULT_JUSTIFICATION,
                                TitledBorder.DEFAULT_POSITION,
                                new Font("Segoe UI", Font.BOLD, 14),
                                mauChuDao
                        )
                )
        );

        JPanel pnlChonDV = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        pnlChonDV.setBackground(mauTrang);

        cboDichVu = new JComboBox<>();
        cboDichVu.setPreferredSize(new Dimension(280, 32));

        spnSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spnSoLuong.setPreferredSize(new Dimension(60, 32));

        btnThemDV = GiaoDienStyle.taoNut("+ Thêm DV", GiaoDienStyle.MAU_TEAL, 105);
        btnXoaDV = GiaoDienStyle.taoNut("Xóa DV", GiaoDienStyle.MAU_DO, 90);

        pnlChonDV.add(new JLabel("Chọn dịch vụ:"));
        pnlChonDV.add(cboDichVu);
        pnlChonDV.add(new JLabel("SL:"));
        pnlChonDV.add(spnSoLuong);
        pnlChonDV.add(btnThemDV);
        pnlChonDV.add(btnXoaDV);

        pnl.add(pnlChonDV, BorderLayout.NORTH);

        String[] cot = {"STT", "Mã DV", "Tên dịch vụ", "SL", "Đơn giá", "Thành tiền"};
        modelDichVu = new DefaultTableModel(null, cot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDichVu = new JTable(modelDichVu);
        GiaoDienStyle.dinhDangBang(tblDichVu);
        tblDichVu.setRowHeight(30);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tblDichVu.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tblDichVu.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);

        JScrollPane scrTable = new JScrollPane(tblDichVu);
        scrTable.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrTable.getViewport().setBackground(Color.WHITE);
        pnl.add(scrTable, BorderLayout.CENTER);

        lblTongTien = new JLabel("Tổng chi phí dự kiến: 0 VNĐ  ", SwingConstants.RIGHT);
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTongTien.setForeground(mauChuDao);
        lblTongTien.setBorder(new EmptyBorder(8, 5, 8, 10));

        pnl.add(lblTongTien, BorderLayout.SOUTH);

        return pnl;
    }

    private JPanel taoPanelNutThaoTac() {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        pnl.setBackground(mauNen);

        btnLuu = GiaoDienStyle.taoNut("Lưu kế hoạch", GiaoDienStyle.MAU_TEAL, 130);
        btnHuy = GiaoDienStyle.taoNut("Hủy bỏ", GiaoDienStyle.MAU_DO, 100);

        pnl.add(btnLuu);
        pnl.add(btnHuy);

        return pnl;
    }

    private void dangKySuKien() {
        btnThemDV.addActionListener(this);
        btnXoaDV.addActionListener(this);
        btnLuu.addActionListener(this);
        btnHuy.addActionListener(this);

        cboBenhNhan.addActionListener(e -> {
            if (cboBenhNhan.getSelectedIndex() > 0 && isThemMoi) {
                String selectedBN = (String) cboBenhNhan.getSelectedItem();
                String maBN = selectedBN.split(" - ")[0];
                loadDichVuVaoBangTheoBenhNhan(maBN);
            }
        });
    }

    private void loadDataCombobox() {
        cboBenhNhan.removeAllItems();
        cboBenhNhan.addItem("Chọn bệnh nhân");
        try {
            BenhNhan_DAO bnDao = new BenhNhan_DAO();
            List<BenhNhan> dsBN = bnDao.getAll();
            for (BenhNhan bn : dsBN) {
                cboBenhNhan.addItem(bn.getMaBenhNhan() + " - " + bn.getHoTen());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cboDichVu.removeAllItems();
        cboDichVu.addItem("Chọn dịch vụ");
        try {
            DichVu_DAO dvDao = new DichVu_DAO();
            List<DichVu> dsDV = dvDao.getAllDichVu();
            for (DichVu dv : dsDV) {
                String itemText = dv.getMaDV() + " - " + dv.getTenDV() + " (" + df.format(dv.getDonGia()) + ")";
                cboDichVu.addItem(itemText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String sinhMaTuDong() {
        try {
            List<KeHoachDieuTri> ds = khDao.getAll();
            if (ds == null || ds.isEmpty()) {
                return "KH0001";
            }
            
            int max = 0;
            for (KeHoachDieuTri kh : ds) {
                try {
                    String ma = kh.getMaKeHoach();
                    int so = Integer.parseInt(ma.replaceAll("\\D+", ""));
                    if (so > max) {
                        max = so;
                    }
                } catch (Exception ignored) {}
            }
            return String.format("KH%04d", max + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "KH0001";
        }
    }

    private void loadDichVuVaoBangTheoBenhNhan(String maBenhNhan) {
        modelDichVu.setRowCount(0);
        try {
            List<DichVu> dsDV = khDao.getDichVuTheoMaBenhNhan(maBenhNhan);
            
            int stt = 1;
            for (DichVu dv : dsDV) {
                int soLuong = 1; 
                double donGia = dv.getDonGia();
                double thanhTien = donGia * soLuong;

                modelDichVu.addRow(new Object[]{
                        stt++,
                        dv.getMaDV(),
                        dv.getTenDV(),
                        soLuong,
                        df.format(donGia),
                        df.format(thanhTien)
                });
            }
            tinhLaiTongChiPhi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDuLieuLenForm(String maKH) {
        try {
            List<KeHoachDieuTri> ds = khDao.getAll();
            for (KeHoachDieuTri kh : ds) {
                if (kh.getMaKeHoach().equals(maKH)) {
                    txtMaKH.setText(kh.getMaKeHoach());
                    txtChanDoan.setText(kh.getChanDoan());
                    
                    if (kh.getNgayBatDau() != null) {
                        dcNgayBatDau.setDate(Date.from(kh.getNgayBatDau().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    }
                    if (kh.getNgayKetThuc() != null) {
                        dcNgayKetThuc.setDate(Date.from(kh.getNgayKetThuc().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    }
                    
                    cboTrangThai.setSelectedItem(kh.getTrangThai());
                    
                    if (kh.getMaBenhNhan() != null) {
                        String maBN = kh.getMaBenhNhan().getMaBenhNhan();
                        for (int i = 0; i < cboBenhNhan.getItemCount(); i++) {
                            if (cboBenhNhan.getItemAt(i).startsWith(maBN)) {
                                cboBenhNhan.setSelectedIndex(i);
                                break;
                            }
                        }
                        loadDichVuVaoBangTheoBenhNhan(maBN);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src.equals(btnThemDV)) {
            xuLyThemDichVuVaoBang();
        } else if (src.equals(btnXoaDV)) {
            xuLyXoaDichVuKhoiBang();
        } else if (src.equals(btnLuu)) {
            xuLyLuuKeHoach();
        } else if (src.equals(btnHuy)) {
            dispose();
        }
    }

    private void xuLyThemDichVuVaoBang() {
        if (cboDichVu.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedDV = (String) cboDichVu.getSelectedItem();
        int soLuong = (int) spnSoLuong.getValue();

        String maDV = selectedDV.substring(0, selectedDV.indexOf(" - "));
        String phanConLai = selectedDV.substring(selectedDV.indexOf(" - ") + 3);
        String tenDV = phanConLai.substring(0, phanConLai.lastIndexOf(" ("));
        
        String giaStr = phanConLai.substring(phanConLai.lastIndexOf("(") + 1, phanConLai.lastIndexOf(")"))
                                  .replace(",", "").replace(".", "").replace(" VNĐ", "").trim();
        double donGia = Double.parseDouble(giaStr);
        double thanhTien = donGia * soLuong;

        for (int i = 0; i < modelDichVu.getRowCount(); i++) {
            if (modelDichVu.getValueAt(i, 1).equals(maDV)) {
                int slCu = Integer.parseInt(modelDichVu.getValueAt(i, 3).toString());
                int slMoi = slCu + soLuong;
                double thanhTienMoi = donGia * slMoi;

                modelDichVu.setValueAt(slMoi, i, 3);
                modelDichVu.setValueAt(df.format(thanhTienMoi), i, 5);

                tinhLaiTongChiPhi();
                return;
            }
        }

        int sttMoi = modelDichVu.getRowCount() + 1;
        modelDichVu.addRow(new Object[]{
                sttMoi, maDV, tenDV, soLuong, df.format(donGia), df.format(thanhTien)
        });

        tinhLaiTongChiPhi();
    }

    private void xuLyXoaDichVuKhoiBang() {
        int row = tblDichVu.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa khỏi bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modelDichVu.removeRow(row);
        
        for (int i = 0; i < modelDichVu.getRowCount(); i++) {
            modelDichVu.setValueAt(i + 1, i, 0);
        }
        
        tinhLaiTongChiPhi();
    }

    private void tinhLaiTongChiPhi() {
        tongChiPhi = 0;
        for (int i = 0; i < modelDichVu.getRowCount(); i++) {
            try {
                String thanhTienStr = modelDichVu.getValueAt(i, 5).toString().replace(",", "").replace(".", "");
                tongChiPhi += Double.parseDouble(thanhTienStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lblTongTien.setText("Tổng chi phí dự kiến: " + df.format(tongChiPhi) + " VNĐ  ");
    }

    private void xuLyLuuKeHoach() {
        if (modelDichVu.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Kế hoạch điều trị phải có ít nhất 1 dịch vụ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String maKH = txtMaKH.getText().trim();
            
            BenhNhan bn = new BenhNhan();
            if (cboBenhNhan.getSelectedIndex() > 0) {
                String selectedBN = (String) cboBenhNhan.getSelectedItem();
                String maBN = selectedBN.split(" - ")[0];
                bn.setMaBenhNhan(maBN);
            } else {
                bn.setMaBenhNhan("");
            }

            String chanDoan = txtChanDoan.getText().trim();
            Date ngayBDDate = dcNgayBatDau.getDate();
            Date ngayKTDate = dcNgayKetThuc.getDate();

            LocalDate ngayBD = ngayBDDate != null ? ngayBDDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            LocalDate ngayKT = ngayKTDate != null ? ngayKTDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

            String trangThai = cboTrangThai.getSelectedItem() != null ? cboTrangThai.getSelectedItem().toString() : "";
            KeHoachDieuTri kh = new KeHoachDieuTri(maKH, bn, chanDoan, ngayBD, ngayKT, tongChiPhi, trangThai);

            boolean kq;
            if (isThemMoi) {
                kq = khDao.themKeHoach(kh);
            } else {
                kq = khDao.suaKeHoach(kh);
            }

            if (kq) {
                String thongBao = isThemMoi ? "Thêm mới kế hoạch thành công!" : "Cập nhật kế hoạch thành công!";
                JOptionPane.showMessageDialog(this, thongBao);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thực hiện thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}