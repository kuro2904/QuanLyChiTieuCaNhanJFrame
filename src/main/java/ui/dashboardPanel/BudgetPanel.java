package ui.dashboardPanel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.BudgetDAO;
import dao.CategoryDAO;
import dao.impl.BudgetDAOImpl;
import dao.impl.CategoryDAOImpl;
import models.Budget;
import models.Category;
import ui.dialogs.AddOrUpdateBudget;
import util.DateUtil;
import util.JdbcToolKit;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ListSelectionModel;

public class BudgetPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private List<Budget> budgets;
    private BudgetDAO budgetDao = new BudgetDAOImpl(JdbcToolKit.getConnection());
    private CategoryDAO categoryDao = new CategoryDAOImpl(JdbcToolKit.getConnection());
    /**
     * Create the panel.
     * @throws Exception 
     */
    public BudgetPanel() throws Exception {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel lblBudgetpane = new JLabel("Budget Panel");
        lblBudgetpane.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblBudgetpane.setBorder(new EmptyBorder(0, 0, 10, 0));
        lblBudgetpane.setHorizontalAlignment(JLabel.CENTER);
        add(lblBudgetpane, BorderLayout.NORTH);

        // Table
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add button
        JPanel buttonPanel = new JPanel();
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> {
			try {
				onRefreshButtonClick(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        btnRefresh.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(btnRefresh);
        add(buttonPanel, BorderLayout.SOUTH);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(this::onAddButtonClick);
        btnAdd.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(btnAdd);
        add(buttonPanel, BorderLayout.SOUTH);
        initView();
    }
    
    private void initView() throws Exception {
    	budgets = budgetDao.getAll();
    	DefaultTableModel model = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                    "Name", "Amount", "Expensed","Recurrence", "Start Date", "End Date", "Category"
                }
            );
    	for(Budget budget : budgets) {
    		Category category = categoryDao.getById(budget.getCategoryId()).orElseThrow(() -> new Exception("Cannot found Category"));
    		model.addRow(new Object[] {
    				budget.getName(),
    				String.valueOf(budget.getAmount()),
    				String.valueOf(budget.getExpensed()),
    				budget.getRecurrence().name(),
    				DateUtil.fromDateToString(budget.getStartDate()),
    				budget.getEndDate() == null ? "": DateUtil.fromDateToString(budget.getEndDate()),
    				category.getName() + " - " + (category.isType() ? "Income" : "Expensed")
    		});
    	}
    	table.setModel(model);
    }

    private void onRefreshButtonClick(ActionEvent e) throws Exception {
    	initView();
    }
    
    private void onAddButtonClick(ActionEvent e) {
        new AddOrUpdateBudget();
    }
}
