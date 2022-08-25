package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer getTransfer(int id);

    List<Transfer> getAllTransfers(String username);

    int createTransfer(Transfer transfer);

}
