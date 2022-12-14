package com.techelevator.tenmo.business;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBusiness {

    private UserDao userDao;
    private AccountDao accountDao;

    public UserBusiness(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    public User getUser(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public int findIdByUsername(String username) {
        return userDao.findIdByUsername(username);
    }

    public boolean createUser(String username, String password) {
        if (findIdByUsername(username) != -1) {
            return false;
        }
        userDao.create(username, password);
        int userId = userDao.findIdByUsername(username);
        accountDao.createAccount(userId);
        return true;
    }
}
