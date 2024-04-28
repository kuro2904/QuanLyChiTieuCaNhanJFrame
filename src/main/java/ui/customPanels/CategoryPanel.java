package ui.customPanels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class CategoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CategoryPanel() {
		
		JLabel lblCategorypanel = new JLabel("CategoryPanel");
		lblCategorypanel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		add(lblCategorypanel);

	}

}
