package ui.customPanels;

import application.Application;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class HomePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lbNameUser;
	private JLabel blTotalMoney;

	/**
	 * Create the panel.
	 */
	public HomePanel() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 179, 103);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hello");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 11, 159, 23);
		panel.add(lblNewLabel);
		
		lbNameUser = new JLabel("Lương Trung");
		lbNameUser.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lbNameUser.setHorizontalAlignment(SwingConstants.CENTER);
		lbNameUser.setBounds(10, 45, 159, 47);
		panel.add(lbNameUser);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(199, 11, 530, 103);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Your amount:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 11, 189, 87);
		panel_1.add(lblNewLabel_2);
		
		blTotalMoney = new JLabel("4444444");
		blTotalMoney.setHorizontalAlignment(SwingConstants.CENTER);
		blTotalMoney.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		blTotalMoney.setBounds(209, 11, 311, 87);
		panel_1.add(blTotalMoney);

		lbNameUser.setText(Application.curUser.getUserName());
		blTotalMoney.setText(Application.curUser.getTotalMoney());

	}
}
