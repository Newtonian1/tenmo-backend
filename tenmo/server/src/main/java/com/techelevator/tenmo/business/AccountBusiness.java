package com.techelevator.tenmo.business;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountBusiness {

    private AccountDao dao;

    public AccountBusiness(AccountDao dao) {
        this.dao = dao;
    }

    public Account getUserAccount(String username) throws UsernameNotFoundException {
        return dao.getUserAccount(username);
    }

//    public List<Account> getNonUserAccounts(String username) {
//        return dao.getNonUserAccounts(username);
//    }
}
