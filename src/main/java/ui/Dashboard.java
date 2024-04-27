package ui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import models.User;

public class Dashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	public Dashboard(User user) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 996, 566);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 960, 68);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dashboard");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setBounds(10, 11, 940, 46);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 90, 135, 426);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("Home");
		btnNewButton.setBounds(0, 0, 135, 84);
		panel_1.add(btnNewButton);
		
		JButton btnCategory = new JButton("Category");
		btnCategory.setBounds(0, 87, 135, 84);
		panel_1.add(btnCategory);
		
		JButton btnBudget = new JButton("Budget");
		btnBudget.setBounds(0, 171, 135, 84);
		panel_1.add(btnBudget);
		
		JButton btnTransaction = new JButton("Transaction");
		btnTransaction.setBounds(0, 257, 135, 84);
		panel_1.add(btnTransaction);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(155, 90, 815, 426);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
	}
}
