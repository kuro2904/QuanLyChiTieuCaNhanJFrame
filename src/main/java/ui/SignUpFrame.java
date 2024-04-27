package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import application.Application;
import dtos.ResponseDTO;
import models.User;
import services.AuthService;


public class SignUpFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JTextField tfUserName;
    private final JTextField tfEmail;
    private final JButton btnSignUp;
    private final JButton btnBack;
    private final JPasswordField tfPassword;
    private final JPasswordField tfConfirmPassword;
    private final AuthService authService = new AuthService();

    /**
     * Create the frame.
     */
    public SignUpFrame(JFrame previous) {
        setResizable(false);
        setTitle("Quản lý chi tiêu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 403, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setVisible(true);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Sign Up");
        lblNewLabel.setBounds(131, 11, 110, 35);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("User Name");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(10, 87, 151, 17);
        contentPane.add(lblNewLabel_1);

        tfUserName = new JTextField();
        tfUserName.setColumns(10);
        tfUserName.setBounds(171, 87, 204, 23);
        contentPane.add(tfUserName);

        JLabel lblNewLabel_1_1 = new JLabel("Email");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_1.setBounds(10, 120, 151, 17);
        contentPane.add(lblNewLabel_1_1);

        tfEmail = new JTextField();
        tfEmail.setColumns(10);
        tfEmail.setBounds(171, 120, 204, 23);
        contentPane.add(tfEmail);

        JLabel lblNewLabel_1_1_1 = new JLabel("Password");
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_1_1.setBounds(10, 154, 151, 17);
        contentPane.add(lblNewLabel_1_1_1);

        JLabel lblNewLabel_1_1_2 = new JLabel("Confirm Password");
        lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1_1_2.setBounds(10, 188, 151, 17);
        contentPane.add(lblNewLabel_1_1_2);

        btnSignUp = new JButton("Sign Up");
        btnSignUp.setBounds(180, 227, 89, 23);
        contentPane.add(btnSignUp);

        btnBack = new JButton("Back");
        btnBack.setBounds(286, 227, 89, 23);
        contentPane.add(btnBack);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(171, 155, 204, 23);
        contentPane.add(tfPassword);

        tfConfirmPassword = new JPasswordField();
        tfConfirmPassword.setBounds(171, 189, 204, 23);
        contentPane.add(tfConfirmPassword);
        addListeningEvents(previous, this);
    }


    private void addListeningEvents(JFrame frame, JFrame currentFrame) {

        btnSignUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                performSignUp(currentFrame);
            }
        });
        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.setVisible(false);
                frame.setVisible(true);
            }
        });

    }

    private void performSignUp(JFrame currentFrame) {
        String email = tfEmail.getText().trim();
        String userName = tfUserName.getText().trim();
        String password = tfPassword.getText().trim();
        String confirmPassword = tfConfirmPassword.getText().trim();


        if (userName.isBlank() || userName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "User name can not be empty or blank",
                    "Err", JOptionPane.INFORMATION_MESSAGE, null);
            tfUserName.grabFocus();
            return;
        }
        if (email.isBlank() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email can not be empty or blank",
                    "Err", JOptionPane.INFORMATION_MESSAGE, null);
            tfEmail.grabFocus();
            return;
        }
        if (password.isBlank() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password can not be empty or blank",
                    "Err", JOptionPane.INFORMATION_MESSAGE, null);
            tfPassword.grabFocus();
            return;
        }
        if (confirmPassword.isBlank() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Confirm password can not be empty or blank",
                    "Err", JOptionPane.INFORMATION_MESSAGE, null);
            tfConfirmPassword.grabFocus();
            return;
        }

        if (!confirmPassword.equals(password)) {
            JOptionPane.showMessageDialog(null, "Password is not match",
                    "Err", JOptionPane.INFORMATION_MESSAGE, null);
            tfPassword.grabFocus();
            tfConfirmPassword.grabFocus();
            return;
        }

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreateAt(LocalDateTime.now());

        ResponseDTO<User> urs;
        urs = authService.signUp(user);
        if (urs.getStatus() == 0) {
            JOptionPane.showMessageDialog(null, "Failed to Sign up, Something went wrong!",
                    "Err", JOptionPane.INFORMATION_MESSAGE, null);
        } else {
            currentFrame.setVisible(false);
            Application.curUser = urs.getData();
            new Dashboard(Application.curUser);
        }
    }
}
