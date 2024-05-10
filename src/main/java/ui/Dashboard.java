package ui;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import models.User;
import ui.dashboardPanel.BudgetPanel;
import ui.dashboardPanel.CategoryPanel;
import ui.dashboardPanel.HomePanel;
import ui.dashboardPanel.SettingPanel;
import ui.dashboardPanel.TransactionPanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
    private JButton btnSetting;

    private void initView(JPanel panel) {
        currPanel = panel;
        contentPanel.add(currPanel, BorderLayout.CENTER);
        contentPanel.revalidate(); // Revalidate the content panel
        contentPanel.repaint(); // Repaint the content panel
    }

    private void addEvents() {
        btnTransaction.addActionListener(e -> changePanel(listJpanel.get(3)));
        btnHome.addActionListener(e -> changePanel(listJpanel.get(0)));
        btnCategory.addActionListener(e -> changePanel(listJpanel.get(1)));
        btnBudget.addActionListener(e -> changePanel(listJpanel.get(2)));
        btnSetting.addActionListener(e -> changePanel(listJpanel.get(4)));
    }

    private void changePanel(JPanel panel) {
        if (currPanel != null) {
            contentPanel.remove(currPanel); // Remove the current panel
        }
        initView(panel); // Initialize the new panel
    }

    public Dashboard(User user) throws Exception {
    	setResizable(false);
        setTitle("Quản lý chi tiêu");
        listJpanel = List.of(new HomePanel(), new CategoryPanel(), new BudgetPanel(), new TransactionPanel(), new SettingPanel());
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
        
        btnSetting = new JButton("Setting");
        navBarPanel.add(btnSetting);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPane.add(contentPanel, BorderLayout.CENTER);

        initView(listJpanel.get(0));
        addEvents();

        // Add a component listener to handle resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int navBarHeight = getHeight();
                navBarPanel.setPreferredSize(new Dimension(navBarWidth, navBarHeight));
                contentPane.revalidate();
            }
        });
    }
}
