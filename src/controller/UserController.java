package controller;

import dao.UserDAO;
import entity.User;

public class UserController {
    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
}