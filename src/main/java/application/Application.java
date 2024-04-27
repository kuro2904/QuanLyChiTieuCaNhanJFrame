package application;

import java.awt.EventQueue;

import javax.swing.JFrame;

import models.User;
import ui.LoginFrm;

public class Application {

    public static User curUser;
    private JFrame frame;

    /**
     * Create the application.
     */
    public Application() {
        initialize();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Application window = new Application();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new LoginFrm();
    }

}
