package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.business.UserBusiness;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private UserBusiness userBusiness;

    public UserController(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public Map<Integer, String> getAllUsers() {
        List<User> userList = userBusiness.getAllUsers();
        Map<Integer, String> userIdName = new HashMap<>();
        for (User user : userList) {
            userIdName.put(user.getId(), user.getUsername());
        }
        return userIdName;
    }
}
