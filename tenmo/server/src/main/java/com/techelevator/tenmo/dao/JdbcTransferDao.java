package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransfer(int id) {
        String sql = "SELECT transfer_id, sender_id, receiver_id, amount, status, date_time FROM transfer WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()){
            return mapRowToTransfer(rowSet);
        }
        return null;
    }

    @Override
    public List<Transfer> getAllTransfers(String username) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, sender_id, receiver_id, amount, status, date_time FROM transfer " +
                "JOIN account ON account.account_id = transfer.sender_id " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE username ILIKE ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        sql = "SELECT transfer_id, sender_id, receiver_id, amount, status, date_time FROM transfer " +
                "JOIN account ON account.account_id = transfer.receiver_id " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE username ILIKE ?;";
        results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public int createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (sender_id, receiver_id, amount, status, date_time)" +
                "VALUES (?,?,?,?,?) RETURNING transfer_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getSenderId(), transfer.getReceiverId(),
                transfer.getAmount(), transfer.getStatus(), transfer.getDateTime());
        return newId;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setId(rs.getInt("transfer_id"));
        transfer.setSenderId(rs.getInt("sender_id"));
        transfer.setReceiverId(rs.getInt("receiver_id"));
        transfer.setAmount(new BigDecimal(rs.getDouble("amount") + ""));
        transfer.setStatus(rs.getString("status"));
        transfer.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        return transfer;
    }
}
