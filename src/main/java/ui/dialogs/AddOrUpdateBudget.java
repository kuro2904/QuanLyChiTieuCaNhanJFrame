package ui.dialogs;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import application.Application;
import dao.BudgetDAO;
import dao.CategoryDAO;
import dao.impl.BudgetDAOImpl;
import dao.impl.CategoryDAOImpl;
import models.Budget;
import models.Category;
import models.Recurrence;
import util.JdbcToolKit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

public class AddOrUpdateBudget extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField tfName;
    private JTextField tfAmount;
    private JComboBox<String> cbCategory;
    private JComboBox<String> cbBoxRecurrence;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JLabel lblEndDate;
    private JButton cancelButton;
    private JButton okButton;
    private Budget budget;
    private CategoryDAO cateDao = new CategoryDAOImpl(JdbcToolKit.getConnection());
    private BudgetDAO budgetDao = new BudgetDAOImpl(JdbcToolKit.getConnection());
    private List<Category> categories = cateDao.getAll();
    private List<Recurrence> recurrences = new ArrayList<Recurrence>(Arrays.asList(Recurrence.values()));

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            AddOrUpdateBudget dialog = new AddOrUpdateBudget();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public AddOrUpdateBudget() {
        initView();
    }
    
    public AddOrUpdateBudget(Budget budget) {
    	this.budget = budget;
        initView();
    }
    
    private void initView() {
    	setVisible(true);
    	setBounds(100, 100, 450, 364);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        JLabel lbDialogDes = new JLabel("<Dynamic>");
        lbDialogDes.setHorizontalAlignment(SwingConstants.CENTER);
        lbDialogDes.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lbDialogDes.setBounds(10, 11, 404, 35);
        contentPanel.add(lbDialogDes);
        
        JLabel lblNewLabel = new JLabel("Name");
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewLabel.setBounds(10, 76, 64, 24);
        contentPanel.add(lblNewLabel);
        
        tfName = new JTextField();
        tfName.setColumns(10);
        tfName.setBounds(116, 76, 308, 24);
        contentPanel.add(tfName);
        
        JLabel lblName = new JLabel("Amount");
        lblName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblName.setBounds(10, 111, 64, 24);
        contentPanel.add(lblName);
        
        tfAmount = new JTextField();
        tfAmount.setColumns(10);
        tfAmount.setBounds(116, 111, 308, 24);
        contentPanel.add(tfAmount);
        
        JLabel lblNewLabel1 = new JLabel("Start Date");
        lblNewLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewLabel1.setBounds(10, 146, 97, 24);
        contentPanel.add(lblNewLabel1);
        
        startDateChooser = new JDateChooser();
        startDateChooser.setDateFormatString("dd/MM/yyyyy");
        startDateChooser.setBounds(117, 146, 307, 24);
        contentPanel.add(startDateChooser);
        
        JPanel panel = new JPanel();
        panel.setBounds(10, 249, 414, 24);
        contentPanel.add(panel);
        panel.setLayout(null);
        
        lblEndDate = new JLabel("End Date");
        lblEndDate.setBounds(0, 0, 97, 24);
        panel.add(lblEndDate);
        lblEndDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        endDateChooser = new JDateChooser();
        endDateChooser.setDateFormatString("dd/MM/yyyyy");
        endDateChooser.setBounds(107, 0, 307, 24);
        panel.add(endDateChooser);
        lblEndDate.setVisible(false);
        endDateChooser.setVisible(false);
        
        JLabel lblRecurrence = new JLabel("Recurrence");
        lblRecurrence.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblRecurrence.setBounds(10, 181, 97, 24);
        contentPanel.add(lblRecurrence);
        
        cbBoxRecurrence = new JComboBox<>();
        cbBoxRecurrence.setBounds(117, 181, 307, 22);
        for(Recurrence recurrence : recurrences) {
        	cbBoxRecurrence.addItem(recurrence.name());
        }
        contentPanel.add(cbBoxRecurrence);
        
        JLabel lblBudgetFor = new JLabel("Budget For");
        lblBudgetFor.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblBudgetFor.setBounds(10, 214, 97, 24);
        contentPanel.add(lblBudgetFor);
        
        cbCategory = new JComboBox<>();
        for(Category category : categories ) {
        	cbCategory.addItem(category.getName());
        }
        cbCategory.setBounds(117, 214, 307, 22);
        contentPanel.add(cbCategory);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        
        cbBoxRecurrence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cbBoxRecurrence.getSelectedItem().equals("Custom")) {
                    lblEndDate.setVisible(true);
                    endDateChooser.setVisible(true);
                } else {
                    lblEndDate.setVisible(false);
                    endDateChooser.setVisible(false);
                }
            }
        });
        
        if(budget != null) {
        	lbDialogDes.setText("Edit Budget");
        	tfName.setText(budget.getName());
        	tfAmount.setText(String.valueOf(budget.getAmount()));
        	startDateChooser.setDate(budget.getStartDate());
        	cbBoxRecurrence.setSelectedItem(budget.getRecurrence().name());
        	cbCategory.setSelectedItem(categories.get(budget.getCategoryId()));
        }else {
        	lbDialogDes.setText("Create Budget");
        }
        addEventListener();
    }

	private void addEventListener() {
		okButton.addActionListener(v -> {
			performInsert();
		});
		
		cancelButton.addActionListener(v -> {
			dispose();
		});
		
	}
	
	private void performInsert() {
		String name = tfName.getText();
		float amount = Float.valueOf(tfAmount.getText());
		Date startDate = startDateChooser.getDate();
		Recurrence recurrence = Recurrence.valueOf(String.valueOf(cbBoxRecurrence.getSelectedItem()));
		Category category = categories.get(cbCategory.getSelectedIndex());
		Budget budget = new Budget();
		budget.setName(name);
		budget.setAmount(amount);
		budget.setRecurrence(recurrence);
		budget.setStartDate(startDate);
		budget.setExpensed(0);
		budget.setCategoryId(category.getCategoryId());
		budget.setUserId(Application.curUser.getUserId());
		if(recurrence == Recurrence.CUSTOM) {
			budget.setEndDate(endDateChooser.getDate());
		}
		if(budgetDao.insert(budget) != null) {
			dispose();
			JOptionPane.showMessageDialog(null, "Insert Successfully",
                    "Notification", JOptionPane.INFORMATION_MESSAGE, null);
		}else {
			JOptionPane.showMessageDialog(null, "Insert Failed",
                    "Notification", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
}
