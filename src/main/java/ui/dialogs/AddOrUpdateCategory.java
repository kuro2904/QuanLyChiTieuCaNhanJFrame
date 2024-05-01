package ui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import application.Application;
import dao.CategoryDAO;
import dao.impl.CategoryDAOImpl;
import models.Category;
import util.JdbcToolKit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddOrUpdateCategory extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfCateName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbExpensed;
	private JRadioButton rdbtnIncome;
	private JLabel lbDialogDes;
	private boolean isUpdate = false;
	private Category category;
	private JButton okButton;
	private JButton cancelButton;
	private CategoryDAO dao = new CategoryDAOImpl(JdbcToolKit.getConnection());

	/**
	 * Create the dialog.
	 */

	private void initView(boolean isUpdate) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		lbDialogDes = new JLabel("<Dynamic>");
		lbDialogDes.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		lbDialogDes.setBounds(152, 11, 141, 35);
		contentPanel.add(lbDialogDes);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 91, 64, 24);
		contentPanel.add(lblNewLabel);

		tfCateName = new JTextField();
		tfCateName.setBounds(84, 91, 340, 24);
		contentPanel.add(tfCateName);
		tfCateName.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBounds(152, 138, 272, 46);
		contentPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		rdbExpensed = new JRadioButton("Expensed");
		buttonGroup.add(rdbExpensed);
		panel.add(rdbExpensed);

		rdbtnIncome = new JRadioButton("Income");
		buttonGroup.add(rdbtnIncome);
		panel.add(rdbtnIncome);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");

				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		if (isUpdate) {
			lbDialogDes.setText("Update Category");
			tfCateName.setText(category.getName());
			if (category.isType()) {
				rdbtnIncome.setSelected(true);
			} else {
				rdbExpensed.setSelected(true);
			}
		} else {
			lbDialogDes.setText("Create Category");
		}
		setVisible(true);
		addEventListener();
	}

	private void addEventListener() {
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isUpdate) {
					performUpdate();
				} else {
					performInsert();
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	private void performInsert() {
		Category category = new Category();
		category.setName(tfCateName.getText());
		if (rdbExpensed.isSelected()) {
			category.setType(false);
		} else if(rdbtnIncome.isSelected()) {
			category.setType(true);
		}else {
			JOptionPane.showMessageDialog(null, "Category type must be selected", "Notification",
					JOptionPane.INFORMATION_MESSAGE, null);
		}
		category.setUserId(Application.curUser.getUserId());
		if(dao.insert(category)!= null) {
			dispose();
			JOptionPane.showMessageDialog(null, "Insert completed", "Notification",
					JOptionPane.INFORMATION_MESSAGE, null);
		}else {
			JOptionPane.showMessageDialog(null, "Insert Failed! Something went wrong!", "Notification",
					JOptionPane.INFORMATION_MESSAGE, null);
		}
		
	}

	private void performUpdate() {
		String name = tfCateName.getText();
		category.setName(name);
		if (rdbExpensed.isSelected()) {
			category.setType(false);
		} else {
			category.setType(true);
		}
		if (dao.update(category.getCategoryId(), category)) {
			dispose();
			JOptionPane.showMessageDialog(null, "Update completed", "Notification",
					JOptionPane.INFORMATION_MESSAGE, null);
		} else {
			JOptionPane.showMessageDialog(null, "Update failed, something went wrong!", "Notification",
					JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
	
	public AddOrUpdateCategory(Category category) {
		this.category = category;
		isUpdate = true;
		initView(isUpdate);
	}

	public AddOrUpdateCategory() {
		initView(isUpdate);
	}
}
