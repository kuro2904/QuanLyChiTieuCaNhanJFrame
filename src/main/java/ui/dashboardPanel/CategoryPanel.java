package ui.dashboardPanel;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import dao.CategoryDAO;
import dao.impl.CategoryDAOImpl;
import models.Category;
import ui.dialogs.AddOrUpdateCategory;
import util.JdbcToolKit;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CategoryPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private List<Category> categories;
    private JButton btnAdd;
    private JButton btnRefresh;
    private CategoryDAO dao = new CategoryDAOImpl(JdbcToolKit.getConnection());

    public CategoryPanel() {
        setLayout(new BorderLayout());
        
        JLabel lblCategorypanel = new JLabel("CategoryPanel");
        lblCategorypanel.setFont(new Font("Tahoma", Font.PLAIN, 29));
        add(lblCategorypanel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
        
        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Name", "Type" }
        ) {
            Class[] columnTypes = new Class[] { Object.class, String.class };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            boolean[] columnEditables = new boolean[] { false, false };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setResizable(false);
        scrollPane.setViewportView(table);
        
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        
        btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {
            AddOrUpdateCategory addDialog = new AddOrUpdateCategory();
            addDialog.setVisible(true);
        });
        buttonPanel.add(btnAdd);
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> refreshTable());
        buttonPanel.add(btnRefresh);

        initView();
        eventListener();
    }

    void initView() {
        categories = dao.getAll();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Category cate : categories) {
            model.addRow(new Object[] {
                cate.getName(),
                cate.isType() ? "Income" : "Expensed"
            });
        }
    }

    void eventListener() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        showPopupMenu(e.getX(), e.getY());
                    }
                }
            }
        });
    }

    private void showPopupMenu(int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e -> performDelete());
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(e -> performEdit());
        popupMenu.add(deleteItem);
        popupMenu.add(editItem);
        popupMenu.show(table, x, y);
    }

    private void performEdit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Category selectedCategory = categories.get(selectedRow);
            AddOrUpdateCategory addDialog = new AddOrUpdateCategory(selectedCategory);
            addDialog.setVisible(true);
        }
    }

    private void performDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(selectedRow);
            // Code to delete the corresponding category from the database
        } else {
            JOptionPane.showMessageDialog(CategoryPanel.this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear table
        categories.clear(); // Clear category list
        initView(); // Reinitialize table data
    }
}
