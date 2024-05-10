package ui.dashboardPanel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SettingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public SettingPanel() {
		setLayout(null);
		
		JLabel lblSettingPanel = new JLabel("Setting Panel");
		lblSettingPanel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblSettingPanel.setBounds(10, 11, 842, 35);
		add(lblSettingPanel);
		
		JLabel lblYourAmount = new JLabel("Your Wallets");
		lblYourAmount.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblYourAmount.setBounds(10, 137, 211, 35);
		add(lblYourAmount);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 842, 413);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);

	}
}
