package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dtos.ResponseDTO;
import models.User;
import services.AuthService;
import util.OTPUtil;


public class LoginFrm extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfEmaill;
    private JPasswordField tfPassword;
    private JLabel lbCreateAccount;
    private JButton btnExit;
    private JButton btnLogin;
    private AuthService authService = new AuthService();

    /**
     * Create the frame.
     */
    public LoginFrm() {
        setResizable(false);
        setTitle("Quản lý chi tiêu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 328, 270);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        lblNewLabel.setBounds(104, 11, 92, 45);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Email");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(10, 102, 78, 17);
        contentPane.add(lblNewLabel_1);

        tfEmaill = new JTextField();
        tfEmaill.setBounds(98, 99, 204, 20);
        contentPane.add(tfEmaill);
        tfEmaill.setColumns(10);

        JLabel lblNewLabel_1_1 = new JLabel("Password");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_1.setBounds(10, 133, 78, 17);
        contentPane.add(lblNewLabel_1_1);

        btnExit = new JButton("Exit");
        btnExit.setBounds(213, 197, 89, 23);
        contentPane.add(btnExit);

        btnLogin = new JButton("Login");

        btnLogin.setBounds(107, 197, 89, 23);
        contentPane.add(btnLogin);

        lbCreateAccount = new JLabel("Dont have an account? Create new!");
        lbCreateAccount.setBounds(122, 161, 180, 14);
        contentPane.add(lbCreateAccount);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(98, 130, 204, 20);
        contentPane.add(tfPassword);

        listenEvents(this);
    }

    /**
     * Launch the application.
     */


    private void listenEvents(JFrame frame) {
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }

            private void performLogin() {
                String email = tfEmaill.getText().toString();
                String password = tfPassword.getText().toString();
                ResponseDTO<User> login;
                if (email.isBlank() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email can not be empty or blank",
                            "Err", JOptionPane.INFORMATION_MESSAGE, null);
                    tfEmaill.grabFocus();
                    return;
                }
                if (password.isBlank() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password can not be empty or blank",
                            "Err", JOptionPane.INFORMATION_MESSAGE, null);
                    tfPassword.grabFocus();
                    return;
                }
                login = authService.login(email, password);
                if (login.getStatus() != 1) {
                    JOptionPane.showMessageDialog(null, login.getMessage(),
                            "Login Failed", JOptionPane.INFORMATION_MESSAGE, null);
                    return;
                }
                dispose();
                MailAuthorizationFrm.authorizeUser(login.getData(), OTPUtil.getSecureCode());
            }
        });
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        lbCreateAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                new SignUpFrame(frame);
            }
        });

    }
}
