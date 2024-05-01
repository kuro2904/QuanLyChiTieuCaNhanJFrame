package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.Application;
import dao.BudgetDAO;
import dao.CategoryDAO;
import models.Budget;
import models.Category;
import models.Recurrence;
import util.DateUtil;

public class BudgetDAOImpl implements BudgetDAO {

	private Connection conn;
	private CategoryDAO categoryDao;

	public BudgetDAOImpl(Connection conn) {
		this.conn = conn;
		categoryDao = new CategoryDAOImpl(conn);
	}

	@Override
	public Budget insert(Budget data) {
	    String sql = "INSERT INTO t_budget(name, amount, expensed, recurrence, startDate, endDate, categoryId, userId) values (?,?,?,?,?,?,?,?)";

	    switch (data.getRecurrence()) {
	        case DAILY: {
	            data.setEndDate(new Date(data.getStartDate().getTime() + 86400000L));
	            break;
	        }
	        case WEEKLY: {
	            data.setEndDate(new Date(data.getStartDate().getTime() + (86400000L * 7)));
	            break;
	        }
	        case MONTHLY: {
	            data.setEndDate(new Date(data.getStartDate().getTime() + (86400000L * 7 * 30)));
	            break;
	        }
	        case YEARLY: {
	            data.setEndDate(new Date(data.getStartDate().getTime() + (86400000L * 7 * 30 * 365)));
	            break;
	        }
	        default:
	            break;
	    }

	    try {
	        Optional<Category> category = categoryDao.getById(data.getCategoryId());
	        if (category.isEmpty())
	            return null;
	        PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        st.setString(1, data.getName());
	        st.setFloat(2, data.getAmount());
	        st.setFloat(3, data.getExpensed());
	        st.setString(4, data.getRecurrence().name());
	        // Convert start date and end date strings to Timestamp
	        st.setTimestamp(5, new Timestamp(data.getStartDate().getTime()));
	        st.setTimestamp(6, new Timestamp(data.getEndDate().getTime()));
	        st.setInt(7, data.getCategoryId());
	        st.setInt(8, Application.curUser.getUserId());

	        int affectedRow = st.executeUpdate();
	        if (affectedRow == 0) {
	            return null;
	        }
	        try (ResultSet generatedKeys = st.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int budgetId = generatedKeys.getInt(1);
	                // Return the newly inserted user
	                return new Budget(budgetId, data.getName(), data.getAmount(), data.getExpensed(),
	                        data.getRecurrence(), data.getStartDate(), data.getEndDate(), data.getCategoryId(),
	                        data.getUserId());
	            } else {
	                // No generated keys, return null or throw an exception
	                return null;
	            }
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}


	@Override
	public Boolean delete(Budget data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean update(int id, Budget data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Budget> getById(int id) {
		String sql = "SELECT * FROM t_budget WHERE budgetId = ?";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				int budgetId = rs.getInt("budgetId");
				String name = rs.getString("name");
				float amount = rs.getFloat("amount");
				float expensed = rs.getFloat("expensed");
				Recurrence recurrence = Recurrence.valueOf(rs.getString("recurrence"));
				Date startDate = rs.getTimestamp("startDate");
				Date endDate = rs.getTimestamp("endDate");
				int categoryId = rs.getInt("categoryId");
				int userId = rs.getInt("userId");
				return Optional.of(new Budget(budgetId,name,amount,expensed,recurrence,startDate,endDate, categoryId,userId));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public List<Budget> getAll() {
		String sql = "Select * from t_budget";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Budget> budgets = new ArrayList<Budget>();
			while (rs.next()) {
				int id = rs.getInt("budgetId");
				String name = rs.getString("name");
				float amount = rs.getFloat("amount");
				float expensed = rs.getFloat("expensed");
				String recyrrence = rs.getString("recurrence");
				Date startDate = rs.getTimestamp("startDate");
				Date endDate = rs.getTimestamp("endDate");
				int categoryId = rs.getInt("categoryId");
				int userId = rs.getInt("userId");
				budgets.add(new Budget(id, name, amount, expensed, Recurrence.valueOf(recyrrence), startDate, endDate,
						categoryId, userId));
			}
			return budgets;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<>();
	}

}
