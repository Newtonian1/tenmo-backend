package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void findIdByUsernameBob() {
        int expectedId = 1001;
        int retrievedId = sut.findIdByUsername("bob");
        Assert.assertEquals(expectedId, retrievedId);
    }

    @Test
    public void findAllUsersReturns2() {
        List<User> userList = sut.findAll();
        int userListSize = userList.size();
        int expectedSize = 2;
        Assert.assertEquals(expectedSize, userListSize);
    }

    @Test
    public void findUserByUsernameBob() {
        User bob = sut.findByUsername("bob");
        Assert.assertEquals("bob", bob.getUsername());
        Assert.assertEquals(1001, bob.getId());
        Assert.assertEquals("$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2", bob.getPassword());
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

}
