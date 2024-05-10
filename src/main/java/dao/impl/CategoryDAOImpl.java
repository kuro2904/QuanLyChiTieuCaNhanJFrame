package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.Application;
import dao.CategoryDAO;
import models.Category;
import util.JdbcToolKit;

public class CategoryDAOImpl implements CategoryDAO {

	private Connection conn;

	public CategoryDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Category insert(Category data) {
		conn = JdbcToolKit.getConnection();
		String sql = "INSERT INTO t_category(name, type, userId) VALUES (?,?,?)";
		try {
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, data.getName());
			st.setBoolean(2, data.isType());
			st.setInt(3, data.getUserId());
			// Execute the INSERT query
			int affectedRows = st.executeUpdate();
			if (affectedRows == 0) {
				// Insert failed, return null or throw an exception
				return null;
			}
			// Retrieve the generated Category
			try (ResultSet generatedKeys = st.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int categoryId = generatedKeys.getInt(1);
					// Return the newly inserted user
					return new Category(categoryId, data.getName(), data.isType(), data.getUserId());
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
	public Boolean delete(Category data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean update(int id, Category data) {
		String sql = "UPDATE t_category SET name=?, type=? WHERE categoryId=?";

		// Retrieve a new connection within a try-with-resources block
		try (PreparedStatement st = conn.prepareStatement(sql)) {

			Category category = getById(id).orElseThrow(() -> new Exception("The category does not exist!"));
			category.setName(data.getName());
			category.setType(data.isType());

			// Set parameters for the prepared statement
			st.setString(1, category.getName());
			st.setBoolean(2, category.isType());
			st.setInt(3, id);

			// Execute the update statement
			int rs = st.executeUpdate();
			if (rs > 0) {
				return true;
			}
		} catch (Exception e) {
			// Handle exceptions appropriately
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Optional<Category> getById(int categoryId) {
		String sql = "SELECT * FROM t_category WHERE categoryId=?";
		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, categoryId);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("categoryId");
					String name = rs.getString("name");
					boolean type = rs.getBoolean("type");
					int userId = rs.getInt("userId");
					return Optional.of(new Category(id, name, type, userId));
				}
			}
		} catch (SQLException ex) {
			// Log or throw an exception
			ex.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public List<Category> getAll() {
		String sql = "Select * from t_category where userId =?";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, Application.curUser.getUserId());
			ResultSet rs = st.executeQuery();
			List<Category> categories = new ArrayList<Category>();
			while (rs.next()) {
				int id = rs.getInt("categoryId");
				String name = rs.getString("name");
				boolean type = rs.getBoolean("type");
				int userId = rs.getInt("userId");
				categories.add(new Category(id, name, type, userId));
			}
			return categories;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<>();
	}

}
