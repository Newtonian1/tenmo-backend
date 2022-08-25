package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccountById(int id) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        return null;
    }

    @Override
    public Account getUserAccount(String username) throws UsernameNotFoundException {
        String sql = "SELECT account_id, account.user_id, balance FROM account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public List<Account> getNonUserAccounts(String username) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, account.user_id, balance FROM account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE username NOT ILIKE ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()) {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public int createAccount(int userId) {
        double startingBalance = 1000.00;
        String sql = "INSERT INTO account (user_id, balance) " +
                "VALUES (?,?) RETURNING account_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, userId, startingBalance);
        return newId;
    }

    @Override
    public boolean updateAccount(Account account) {
        String sql = "UPDATE account SET user_id = ?, balance = ? WHERE account_id = ?;";
        int numberOfRows =
                jdbcTemplate.update(sql, account.getUserId(), account.getBalance(),
                        account.getId());
        return numberOfRows == 1;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(new BigDecimal(rs.getDouble("balance") + ""));
        return account;
    }
}
