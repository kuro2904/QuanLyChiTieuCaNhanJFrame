package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import application.Application;
import models.User;
import services.AuthService;
import util.EmailUtil;

public class MailAuthorizationFrm extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static JTextField textField;
    private final String code;
    private JButton btnNewButton;
    private User user;
    private AuthService authService = new AuthService();

    public static void authorizeUser(User user, String code) {
        MailAuthorizationFrm authorizeFrm = new MailAuthorizationFrm(user, code);
        authorizeFrm.setVisible(true);
        authorizeFrm.sendAuthorizationEmail();
    }

    private void sendAuthorizationEmail() {
        new Thread(() -> {
            try {
                new EmailUtil().sendMail(user.getEmail(), code);
            } catch (IOException e) {
                // Log or handle the exception appropriately
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to send authorization email", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    private void addEvent(JFrame frame, User user) {
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isAuthorizationCodeValid()) {
                    if (authService.updateLastLogin(user)) {
                        Application.curUser = user;
                        frame.dispose();
                        try {
							new Dashboard(Application.curUser).setVisible(true);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update last login", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The security code is incorrect", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean isAuthorizationCodeValid() {
        return textField.getText().equals(code);
    }

    public MailAuthorizationFrm(User user, String code) {
        this.user = user;
        this.code = code;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 316, 241);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTextArea txtrWeHaveSent = new JTextArea();
        txtrWeHaveSent.setBackground(UIManager.getColor("Button.background"));
        txtrWeHaveSent.setWrapStyleWord(true);
        txtrWeHaveSent.setLineWrap(true);
        txtrWeHaveSent.setRows(2);
        txtrWeHaveSent.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        txtrWeHaveSent.setEditable(false);
        txtrWeHaveSent.setText(
                "We have sent the security code to your email.  Please check your email and enter your code into this field below to authorize ");
        txtrWeHaveSent.setBounds(10, 11, 285, 67);
        contentPane.add(txtrWeHaveSent);

        textField = new JTextField();
        textField.setBounds(10, 110, 280, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Your code");
        lblNewLabel.setBounds(10, 85, 64, 14);
        contentPane.add(lblNewLabel);

        JLabel lbNotify = new JLabel("Notify");
        lbNotify.setHorizontalAlignment(SwingConstants.CENTER);
        lbNotify.setBounds(96, 141, 194, 14);
        contentPane.add(lbNotify);

        btnNewButton = new JButton("Confirm");
        btnNewButton.setBounds(83, 166, 112, 25);
        contentPane.add(btnNewButton);
        addEvent(this, user);
    }
}
