package ui.customPanels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class TransactionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TransactionPanel() {
		
		JLabel lblNewLabel = new JLabel("TransactionPanel");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		add(lblNewLabel);

	}

}
