package com.techelevator.tenmo.business;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TransferBusiness {

    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferBusiness(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    public Transfer getTransfer(int id) {
        return transferDao.getTransfer(id);
    }

    public List<Transfer> getAllTransfers(String username) {
        return transferDao.getAllTransfers(username);
    }

    public boolean createTransfer(Transfer transfer) {
        //Check if sender balance is sufficient
        int senderAccountId = transfer.getSenderId();
        int receiverAccountId = transfer.getReceiverId();
        Account senderAccount = accountDao.getAccountById(senderAccountId);
        BigDecimal senderBalance = senderAccount.getBalance();
        BigDecimal amount = transfer.getAmount();
        if(senderBalance.compareTo(amount) < 0) {
            return false;
        }

        //Log transfer into data table
        transferDao.createTransfer(transfer);

        //Update balance of sender account
        BigDecimal newSenderBalance = senderBalance.subtract(amount);
        senderAccount.setBalance(newSenderBalance);
        boolean wasSenderAccountUpdated = accountDao.updateAccount(senderAccount);

        //Update balance of receiver account
        Account receiverAccount = accountDao.getAccountById(receiverAccountId);
        BigDecimal receiverBalance = receiverAccount.getBalance();
        BigDecimal newReceiverBalance = receiverBalance.subtract(amount);
        receiverAccount.setBalance(newReceiverBalance);
        boolean wasReceiverAccountUpdated = accountDao.updateAccount(receiverAccount);
        return wasSenderAccountUpdated && wasReceiverAccountUpdated;
    }
}