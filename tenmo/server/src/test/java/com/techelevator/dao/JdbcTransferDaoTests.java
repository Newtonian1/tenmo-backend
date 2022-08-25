package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests{

    private JdbcTransferDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void getTransferById() {
        Transfer transfer = sut.getTransfer(3001);
        Assert.assertEquals(2001, transfer.getSenderId());
        Assert.assertEquals(2002, transfer.getReceiverId());
        BigDecimal expectedAmount = new BigDecimal("50.00");
        Assert.assertEquals(expectedAmount, transfer.getAmount());
        Assert.assertEquals("Approved", transfer.getStatus());
        LocalDateTime retrievedDateTime = transfer.getDateTime();
        String dateTimeString = retrievedDateTime.toString();
        Assert.assertEquals("2022-08-24T04:44", dateTimeString);
    }

    @Test
    public void getAllTransfersShouldHave3() {
        List<Transfer> allTransfers = sut.getAllTransfers("bob");
        int expectedSize = 3;
        Assert.assertEquals(expectedSize, allTransfers.size());
    }

    @Test
    public void createTransferAddsTransfer() {
        BigDecimal transferAmount = new BigDecimal("5.00");
        LocalDateTime dateTime = LocalDateTime.now();
        Transfer transfer = new Transfer(0, 2002, 2001, transferAmount, "Approved", dateTime);
        int newId = sut.createTransfer(transfer);
        Assert.assertEquals(3005, newId);
        Transfer retrievedTransfer = sut.getTransfer(3005);
        Assert.assertEquals(2002, retrievedTransfer.getSenderId());
        Assert.assertEquals(2001, retrievedTransfer.getReceiverId());
        BigDecimal expectedAmount = new BigDecimal("5.00");
        Assert.assertEquals(expectedAmount, retrievedTransfer.getAmount());
        Assert.assertEquals("Approved", retrievedTransfer.getStatus());
        LocalDateTime retrievedDateTime = transfer.getDateTime();
        String retrievedDateTimeString = retrievedDateTime.toString().substring(0, 19);
        String expectedTimeString = LocalDateTime.now().toString().substring(0, 19);
        Assert.assertEquals(expectedTimeString, retrievedDateTimeString);
    }
}
