package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.business.UserBusiness;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private UserBusiness userBusiness;

    public UserController(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userBusiness.getAllUsers();
    }
}
