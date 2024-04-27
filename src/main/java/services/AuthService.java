package services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import dtos.ResponseDTO;
import models.User;
import util.BCrypPassword;
import util.EmailUtil;
import util.JdbcToolKit;

public class AuthService {

    private final UserDAO userDao = new UserDAOImpl(JdbcToolKit.getConnection());


    public ResponseDTO<User> login(String email, String password) {
        Optional<User> user = userDao.getByEmail(email);
        if (user.isPresent()) {
        	if (user.get().getPassword().equals(BCrypPassword.hashPassword(password)) ) {
    			return new ResponseDTO<User>(user.get(), "User found", 1);
            	}
            else {
                return new ResponseDTO<User>(null, "Wrong password", 0);
            }
        }
        return new ResponseDTO<User>(null, "Wrong email", 0);
    }


    public boolean updateLastLogin(User user) {
        user.setLastLogin(LocalDateTime.now());
        return userDao.update(user.getUserId(), user);
    }

    public ResponseDTO<User> signUp(User user) {
        User rs = userDao.insert(user);
        if (rs != null) {
            new Thread(() -> {
                try {
                    EmailUtil.sendMail(rs.getEmail(), rs.getPassword());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            return new ResponseDTO<User>(rs, "Register OK!", 1);
        } else
            return new ResponseDTO<User>(null, "Register Failed", 0);
    }

}
