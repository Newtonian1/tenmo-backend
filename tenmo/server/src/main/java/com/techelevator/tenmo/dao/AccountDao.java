package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    Account getAccountById(int id);

    Account getUserAccount(String username);

    List<Account> getNonUserAccounts(String username);

    int createAccount(int userId);

    boolean updateAccount(Account account);
}
