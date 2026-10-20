package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import connectDB.ConnectDB;
import dao.TaiKhoan_DAO;
import model.TaiKhoan;

public class DangNhapPanel extends JFrame implements ActionListener {

    private final Color mauXanh = new Color(35, 105, 180);
    private final Color mauXanhDam = new Color(22, 67, 120);
    private final Color mauMint = new Color(135, 206, 250);
    private final Color mauChu = new Color(35, 55, 75);

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JLabel lblQuenMatKhau;

    public DangNhapPanel() {
        setTitle("Family Dental - Login");
        setSize(1000, 620);
        setMinimumSize(new Dimension(850, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel pnlTong = new JPanel(new BorderLayout());
        pnlTong.setBackground(Color.WHITE);

        JPanel pnlBanner = taoBanner();
        JPanel pnlDangNhap = taoFormDangNhap();

        JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, pnlBanner, pnlDangNhap );

        splitPane.setResizeWeight(0.4);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);
        splitPane.setContinuousLayout(true);

        pnlTong.add(splitPane, BorderLayout.CENTER);
        setContentPane(pnlTong);

        getRootPane().setDefaultButton(btnLogin);
    }

    private JPanel taoBanner() {
        JPanel pnlBanner = new JPanel(new BorderLayout());
        pnlBanner.setBackground(mauMint);
        pnlBanner.setBorder(new EmptyBorder(35, 25, 35, 25));

        JPanel pnlNoiDung = new JPanel();
        pnlNoiDung.setOpaque(false);
        pnlNoiDung.setLayout(
            new BoxLayout(pnlNoiDung, BoxLayout.Y_AXIS)
        );

        URL urlLogo = getClass().getResource("/img/logo.png");

        JLabel lblLogo;

        if (urlLogo != null) {
            ImageIcon iconLogo = new ImageIcon(urlLogo);

            Image anhLogo = iconLogo.getImage().getScaledInstance( 250, 250, Image.SCALE_SMOOTH);

            lblLogo = new JLabel(new ImageIcon(anhLogo));
        } else {
            lblLogo = new JLabel();
            System.out.println("Không tìm thấy ảnh /img/logo.png");
        }

        lblLogo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblTen = new JLabel("FAMILY DENTAL");
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTen.setForeground(mauXanhDam);
        lblTen.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblPhuDe = new JLabel("Nha khoa Gia đình");
        lblPhuDe.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblPhuDe.setForeground(mauChu);
        lblPhuDe.setAlignmentX(CENTER_ALIGNMENT);

        pnlNoiDung.add(Box.createVerticalGlue());
        pnlNoiDung.add(lblLogo);
        pnlNoiDung.add(Box.createVerticalStrut(15));
        pnlNoiDung.add(lblTen);
        pnlNoiDung.add(Box.createVerticalStrut(12));
        pnlNoiDung.add(lblPhuDe);
        pnlNoiDung.add(Box.createVerticalGlue());

        pnlBanner.add(pnlNoiDung, BorderLayout.CENTER);

        return pnlBanner;
    }

    private JPanel taoFormDangNhap() {
        JPanel pnlDangNhap = new JPanel(new BorderLayout());
        pnlDangNhap.setBackground(mauXanh);
        pnlDangNhap.setBorder(new EmptyBorder(40, 55, 40, 55));

        JPanel pnlForm = new JPanel();
        pnlForm.setOpaque(false);
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));

        JLabel lblChaoMung = new JLabel("FAMILY DENTAL");
        lblChaoMung.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblChaoMung.setForeground(new Color(205, 235, 255));
        lblChaoMung.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblTieuDe = new JLabel("ĐĂNG NHẬP");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTieuDe.setForeground(Color.WHITE);
        lblTieuDe.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblMoTa = new JLabel("Chào mừng quay trở lại");
        lblMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMoTa.setForeground(new Color(225, 239, 255));
        lblMoTa.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblUser = new JLabel("Tên đăng nhập");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(Color.WHITE);
        lblUser.setAlignmentX(CENTER_ALIGNMENT);
        lblUser.setMaximumSize(new Dimension(330, 25));

        txtUser = new JTextField();
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUser.setBackground(Color.WHITE);
        txtUser.setForeground(mauChu);

        Icon iconUser = FontIcon.of(FontAwesomeSolid.USER, 20, mauXanhDam);

        JPanel pnlUser = taoONhap(txtUser, iconUser);

        JLabel lblPass = new JLabel("Mật khẩu");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(Color.WHITE);
        lblPass.setAlignmentX(CENTER_ALIGNMENT);
        lblPass.setMaximumSize(new Dimension(330, 25));

        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPass.setBackground(Color.WHITE);
        txtPass.setForeground(mauChu);

        Icon iconLock = FontIcon.of(FontAwesomeSolid.LOCK,20,mauXanhDam);

        JPanel pnlPass = taoONhap(txtPass, iconLock);

        themPlaceholder(txtUser,"Nhập mã nhân viên hoặc tên đăng nhập" );

        themPlaceholderMatKhau(txtPass, "Nhập mật khẩu" );

        lblQuenMatKhau = new JLabel("Quên mật khẩu?");
        lblQuenMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblQuenMatKhau.setForeground(new Color(225, 242, 255));
        lblQuenMatKhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblQuenMatKhau.setAlignmentX(CENTER_ALIGNMENT);
        lblQuenMatKhau.setMaximumSize(new Dimension(330, 25));
        lblQuenMatKhau.setHorizontalAlignment(SwingConstants.RIGHT);

        lblQuenMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked( java.awt.event.MouseEvent e) {
                    JOptionPane.showMessageDialog(
                        DangNhapPanel.this, "Vui lòng liên hệ quản trị viên để được hỗ trợ " + "khôi phục mật khẩu.", "Quên mật khẩu",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        );

        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(mauXanhDam);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor( new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(CENTER_ALIGNMENT);
        btnLogin.setPreferredSize(new Dimension(180, 40));
        btnLogin.setMinimumSize(new Dimension(180, 40));
        btnLogin.setMaximumSize(new Dimension(180, 40));

        btnLogin.setIcon( FontIcon.of( FontAwesomeSolid.SIGN_IN_ALT,18,Color.WHITE  ));
        btnLogin.setIconTextGap(10);

        btnLogin.addActionListener(this);

        JLabel lblThongTin = new JLabel("© Family Dental - Dental Clinic System");
        lblThongTin.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblThongTin.setForeground(new Color(210, 230, 250));
        lblThongTin.setHorizontalAlignment(SwingConstants.CENTER);

        pnlForm.add(Box.createVerticalGlue());
        pnlForm.add(lblChaoMung);
        pnlForm.add(Box.createVerticalStrut(12));
        pnlForm.add(lblTieuDe);
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(lblMoTa);
        pnlForm.add(Box.createVerticalStrut(35));

        pnlForm.add(lblUser);
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(pnlUser);
        pnlForm.add(Box.createVerticalStrut(20));

        pnlForm.add(lblPass);
        pnlForm.add(Box.createVerticalStrut(8));
        pnlForm.add(pnlPass);
        pnlForm.add(Box.createVerticalStrut(10));

        pnlForm.add(lblQuenMatKhau);
        pnlForm.add(Box.createVerticalStrut(20));
        pnlForm.add(btnLogin);
        pnlForm.add(Box.createVerticalGlue());

        pnlDangNhap.add(pnlForm, BorderLayout.CENTER);
        pnlDangNhap.add(lblThongTin, BorderLayout.SOUTH);

        return pnlDangNhap;
    }

    private JPanel taoONhap(JTextField oNhap, Icon icon) {
        JPanel pnl = new JPanel(new BorderLayout(8, 0));
        pnl.setBackground(Color.WHITE);
        pnl.setAlignmentX(CENTER_ALIGNMENT);
        pnl.setMaximumSize(new Dimension(330, 45));
        pnl.setPreferredSize(new Dimension(330, 45));

        pnl.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder( new Color(210, 225, 240)), BorderFactory.createEmptyBorder(0, 10, 0, 10) ) );

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setPreferredSize(new Dimension(30, 30));
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

        oNhap.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        oNhap.setPreferredSize(new Dimension(270, 43));

        pnl.add(lblIcon, BorderLayout.WEST);
        pnl.add(oNhap, BorderLayout.CENTER);

        return pnl;
    }

    private JPanel taoONhap(JPasswordField oNhap, Icon icon) {
        JPanel pnl = new JPanel(new BorderLayout(8, 0));
        pnl.setBackground(Color.WHITE);
        pnl.setAlignmentX(CENTER_ALIGNMENT);
        pnl.setMaximumSize(new Dimension(330, 45));
        pnl.setPreferredSize(new Dimension(330, 45));

        pnl.setBorder( BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(210, 225, 240)),BorderFactory.createEmptyBorder(0, 10, 0, 10)));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setPreferredSize(new Dimension(30, 30));
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

        oNhap.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        oNhap.setPreferredSize(new Dimension(270, 43));

        pnl.add(lblIcon, BorderLayout.WEST);
        pnl.add(oNhap, BorderLayout.CENTER);

        return pnl;
    }

    private void themPlaceholder( JTextField txt,String placeholder) {
        txt.setText(placeholder);
        txt.setForeground(Color.GRAY);

        txt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt.getText().equals(placeholder)) 
                {txt.setText("");txt.setForeground(mauChu);}
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt.getText().trim().isEmpty()) {
                    txt.setText(placeholder); txt.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void themPlaceholderMatKhau(JPasswordField txt, String placeholder) {
        txt.setEchoChar((char) 0);
        txt.setText(placeholder);
        txt.setForeground(Color.GRAY);

        txt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String matKhau = new String(txt.getPassword());

                if (matKhau.equals(placeholder)) {
                    txt.setText("");
                    txt.setForeground(mauChu);
                    txt.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String matKhau = new String(txt.getPassword());

                if (matKhau.trim().isEmpty()) {
                    txt.setEchoChar((char) 0);
                    txt.setText(placeholder);
                    txt.setForeground(Color.GRAY);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o.equals(btnLogin)) {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());

            if (user.isEmpty()|| user.equals( "Nhập mã nhân viên hoặc tên đăng nhập")|| pass.isEmpty()||pass.equals("Nhập mật khẩu")) {

                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                TaiKhoan_DAO tkDAO = new TaiKhoan_DAO();
                TaiKhoan tk = tkDAO.checkLogin(user, pass);

                if (tk != null) {JOptionPane.showMessageDialog(
                        this,
                        "Đăng nhập thành công với quyền: "
                            + tk.getQuyen(),
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    MainFrame view = new MainFrame();
                    view.setVisible(true);
                    this.dispose();

                } else { JOptionPane.showMessageDialog(this,
                        "Tài khoản hoặc mật khẩu không chính xác!",
                        "Lỗi đăng nhập",
                        JOptionPane.ERROR_MESSAGE
                    );

                    txtPass.setText("");
                    txtPass.requestFocus();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Không thể đăng nhập. Vui lòng kiểm tra kết nối "
                        + "cơ sở dữ liệu!\n" + ex.getMessage(),
                    "Lỗi hệ thống",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {
        try {
            ConnectDB.getInstance().connect();
            new DangNhapPanel().setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                null,
                "Không thể kết nối cơ sở dữ liệu!\n" + e.getMessage(),
                "Lỗi kết nối",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
