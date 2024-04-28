package ui;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import models.User;
import ui.customPanels.BudgetPanel;
import ui.customPanels.CategoryPanel;
import ui.customPanels.HomePanel;
import ui.customPanels.TransactionPanel;

import java.util.List;

public class Dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnCategory;
    private JButton btnBudget;
    private JButton btnHome;
    private JButton btnTransaction;
    private JPanel currPanel;
    private JPanel contentPanel;
    private List<JPanel> listJpanel;
    private JLabel lblNewLabel;

    private void initView(JPanel panel) {
        currPanel = panel;
        contentPanel.add(currPanel);
        contentPanel.revalidate(); // Revalidate the content panel
        contentPanel.repaint(); // Repaint the content panel
    }

    private void addEvents() {
        btnTransaction.addActionListener(e -> changePanel(listJpanel.get(3)));
        btnHome.addActionListener(e -> changePanel(listJpanel.get(0)));
        btnCategory.addActionListener(e -> changePanel(listJpanel.get(1)));
        btnBudget.addActionListener(e -> changePanel(listJpanel.get(2)));
    }

    private void changePanel(JPanel panel) {
        if (currPanel != null) {
            contentPanel.remove(currPanel); // Remove the current panel
        }
        initView(panel); // Initialize the new panel
    }

    public Dashboard(User user) {
    	setTitle("Quản lý chi tiêu");
        listJpanel = List.of(new HomePanel(), new CategoryPanel(), new TransactionPanel(), new BudgetPanel());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int appWidth = 996; // Application width
        int navBarWidth = appWidth * 2 / 10; // Width of the navigation bar (2/10 of the application width)
        setBounds(100, 100, appWidth, 579); // Set the bounds of the frame

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel navBarPanel = new JPanel(new GridLayout(0, 1));
        navBarPanel.setPreferredSize(new Dimension(navBarWidth, getHeight()));
        navBarPanel.setBackground(Color.GRAY);

        btnHome = new JButton("Home");
        btnCategory = new JButton("Category");
        btnBudget = new JButton("Budget");
        btnTransaction = new JButton("Transaction");

        navBarPanel.add(btnHome);
        navBarPanel.add(btnCategory);
        navBarPanel.add(btnBudget);
        navBarPanel.add(btnTransaction);

        contentPane.add(navBarPanel, BorderLayout.WEST);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPane.add(contentPanel, BorderLayout.CENTER);

        initView(listJpanel.get(0));
        addEvents();
    }
}
