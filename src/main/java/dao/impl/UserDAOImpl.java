package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import dao.UserDAO;
import models.User;
import util.BCrypPassword;
import util.JdbcToolKit;

public class UserDAOImpl implements UserDAO {

    private Connection conn;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User insert(User data) {
        String sql = "INSERT INTO t_user(userName,email,password,createAt,total_money) VALUES(?,?,?,?,?)";
        try (Connection conn = JdbcToolKit.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Check if the user already exists
            if (getByEmail(data.getEmail()).isPresent()) {
                // User already exists, return null or throw an exception
                return null;
            }

            // Hash the password
            String hashedPassword = BCrypPassword.hashPassword(data.getPassword());

            // Set parameters for the prepared statement
            st.setString(1, data.getUserName());
            st.setString(2, data.getEmail());
            st.setString(3, hashedPassword);
            st.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            // Execute the INSERT query
            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                // Insert failed, return null or throw an exception
                return null;
            }

            // Retrieve the generated user ID
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    // Return the newly inserted user
                    return new User(userId, data.getUserName(), data.getEmail(), hashedPassword,"0");
                } else {
                    // No generated keys, return null or throw an exception
                    return null;
                }
            }
        } catch (SQLException ex) {
            // Handle SQL exceptions
            ex.printStackTrace();
            // Return null or throw an exception
            return null;
        }
    }

    @Override
    public Boolean delete(User data) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Boolean update(int id, User data) {
        String sql = "UPDATE t_user SET userName=?, email=?, password=?, lastLogin=?, total_money=? WHERE userId=?";
        conn = JdbcToolKit.getConnection();

        try {
            User user = getById(id).orElseThrow(() -> new Exception("The user is not exists!"));
            if (data.getEmail() != null) user.setEmail(data.getEmail());
            if (data.getUserName() != null) user.setUserName(data.getUserName());
            if (data.getPassword() != null) user.setPassword(data.getPassword());
            if (data.getLastLogin() != null) user.setLastLogin(data.getLastLogin());
            if (data.getTotalMoney() != null) user.setTotalMoney(data.getTotalMoney());
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, user.getUserName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPassword());
            st.setTimestamp(4, Timestamp.valueOf(user.getLastLogin()));
            st.setString(5, user.getTotalMoney());
            st.setInt(6, user.getUserId());
            int rs = st.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<User> getById(int userId) {
        conn = JdbcToolKit.getConnection();
        String sql = "SELECT * FROM t_user WHERE userId=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            List<User> users = new ArrayList<User>();
            while (rs.next()) {
            	 int id = rs.getInt("userId");
                 String userName = rs.getString("userName");
                 String usEmail = rs.getString("email");
                 String usPassword = rs.getString("password");
                 Date createAt = rs.getTimestamp("createAt");
                 Date lastLogin = rs.getTimestamp("lastLogin");
                 String totalMoney = rs.getString("total_money");

                users.add(
                		new User(
                                id,
                                userName,
                                usEmail,
                                usPassword,
                                LocalDateTime.ofInstant(createAt.toInstant(), ZoneId.systemDefault()),
                                LocalDateTime.ofInstant(lastLogin.toInstant(), ZoneId.systemDefault()),
                                totalMoney));
            }
            return Optional.of(users.get(0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        conn = JdbcToolKit.getConnection();
        String sql = "Select * from t_user";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                int id = rs.getInt("userId");
                String userName = rs.getString("userName");
                String usEmail = rs.getString("email");
                String usPassword = rs.getString("password");
                Date createAt = rs.getTimestamp("createAt");
                Date lastLogin = rs.getTimestamp("lastLogin");
                String totalMoney = rs.getString("total_money");
                users.add(
                        new User(
                                id,
                                userName,
                                usEmail,
                                usPassword,
                                LocalDateTime.ofInstant(createAt.toInstant(), ZoneId.systemDefault()),
                                LocalDateTime.ofInstant(lastLogin.toInstant(), ZoneId.systemDefault()),
                                totalMoney));
            }
            return users;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        conn = JdbcToolKit.getConnection();
        String sql = "SELECT userId, userName, email, password, createAt, total_money FROM t_user WHERE email=?";
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
            	int id = rs.getInt("userId");
                String userName = rs.getString("userName");
                String usEmail = rs.getString("email");
                String usPassword = rs.getString("password");
                Date createAt = rs.getTimestamp("createAt");
                String totalMoney = rs.getString("total_money");
                users.add(
                        new User(
                                id,
                                userName,
                                usEmail,
                                usPassword,
                                LocalDateTime.ofInstant(createAt.toInstant(), ZoneId.systemDefault()),
                                totalMoney));
            }
            if (!users.isEmpty()) {
                return Optional.of(users.get(0));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }


}
