package dao;

import java.util.Optional;

import models.User;

public interface UserDAO extends DAO<User> {
    Optional<User> getByEmail(String email);
}
