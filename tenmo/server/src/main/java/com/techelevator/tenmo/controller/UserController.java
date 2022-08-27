package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.business.UserBusiness;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserBusiness userBusiness;

    public UserController(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<String> getAllUsers() {
        List<User> userList = userBusiness.getAllUsers();
        List<String> userNames = new ArrayList<>();
        for (User user : userList) {
            userNames.add(user.getUsername());
        }
        return userNames;
    }
}
