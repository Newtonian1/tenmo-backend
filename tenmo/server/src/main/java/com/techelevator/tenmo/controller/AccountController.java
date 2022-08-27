package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.business.AccountBusiness;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountBusiness accountBusiness;

    public AccountController(AccountBusiness accountBusiness) {
        this.accountBusiness = accountBusiness;
    }

    @RequestMapping(path = "/myaccount", method = RequestMethod.GET)
    public Account getUserAccount(Principal principal) {
        //principal.getName() retrieves the "sub" field from the JWT, which corresponds to the username
        try {
            return accountBusiness.getUserAccount(principal.getName());
        } catch (UsernameNotFoundException e) {
            return null;
        }
    }

}
