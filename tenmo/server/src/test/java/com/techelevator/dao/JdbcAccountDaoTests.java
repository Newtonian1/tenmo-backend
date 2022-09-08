package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public class JdbcAccountDaoTests extends BaseDaoTests{

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAccountById2001() {
        Account account = sut.getAccountById(2001);
        Assert.assertEquals(2001, account.getId());
        Assert.assertEquals(1001, account.getUserId());
        Assert.assertEquals(new BigDecimal("950.00"), account.getBalance());
    }

    @Test
    public void getUserAccountByUsernameBob() {
        Account account = sut.getUserAccount("bob");
        Assert.assertEquals(2001, account.getId());
        Assert.assertEquals(1001, account.getUserId());
        Assert.assertEquals(new BigDecimal("950.00"), account.getBalance());
    }

    @Test
    public void getNonUserAccountsShouldHave1() {
        List<Account> accountList = sut.getNonUserAccounts("bob");
        int expectedListSize = 1;
        Assert.assertEquals(expectedListSize, accountList.size());
        int expectedAccountId = 2002;
        Assert.assertEquals(expectedAccountId, accountList.get(0).getId());
    }

    @Test
    public void createAccountAddsAccount() {
        int newId = sut.createAccount(1001);
        int expectedId = 2003;
        Assert.assertEquals(expectedId, newId);
        List<Account> accountList = sut.getNonUserAccounts("user");
        int expectedListSize = 2;
        Assert.assertEquals(expectedListSize, accountList.size());
    }

    @Test
    public void updateAccountsChangesBalances() {
        Account accountBob = sut.getUserAccount("bob");
        BigDecimal newBobBalance = new BigDecimal("50.00");
        accountBob.setBalance(newBobBalance);
        Account accountUser = sut.getUserAccount("user");
        BigDecimal newUserBalance = new BigDecimal("25.00");
        accountUser.setBalance(newUserBalance);
        boolean updated = sut.updateAccounts(accountBob, accountUser);
        Assert.assertTrue(updated);
        Assert.assertEquals(newBobBalance, sut.getUserAccount("bob").getBalance());
        Assert.assertEquals(newUserBalance, sut.getUserAccount("user").getBalance());

        //Rollback (because balances are updated in a SQL transaction that is committed, they cannot be auto rolled back)
        newBobBalance = new BigDecimal("950.00");
        accountBob.setBalance(newBobBalance);
        newUserBalance = new BigDecimal("1050.00");
        accountUser.setBalance(newUserBalance);
        sut.updateAccounts(accountBob,accountUser);
    }

}
