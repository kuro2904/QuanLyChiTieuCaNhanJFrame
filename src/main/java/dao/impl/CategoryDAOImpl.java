package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import application.Application;
import dao.CategoryDAO;
import models.Category;
import models.Type;
import util.JdbcToolKit;

public class CategoryDAOImpl implements CategoryDAO{

	private Connection conn;

    public CategoryDAOImpl(Connection conn) {
        this.conn = conn;
    }
	
	@Override
	public Category insert(Category data) {
		conn = JdbcToolKit.getConnection();
		String sql = "INSERT INTO t_category(name, type, userId) VALUES (?,?,?)";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, data.getName());
			st.setString(2, data.getType().name());
			st.setInt(3, Application.curUser.getUserId());
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
                    String name = generatedKeys.getString(2);
                    String type = generatedKeys.getString(3);
                    int userId = generatedKeys.getInt(4);
                    // Return the newly inserted user
                    return new Category(categoryId,name,Type.valueOf(type),userId);
                } else {
                    // No generated keys, return null or throw an exception
                    return null;
                }
            }
		}catch(Exception ex) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Category> getById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
