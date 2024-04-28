package ui.customPanels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class BudgetPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */


	public BudgetPanel() {
		
		JLabel lblBudgetpane = new JLabel("BudgetPane");
		lblBudgetpane.setFont(new Font("Tahoma", Font.PLAIN, 29));
		add(lblBudgetpane);

	}

}
