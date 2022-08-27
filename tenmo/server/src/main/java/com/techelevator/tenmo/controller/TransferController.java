package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.business.AccountBusiness;
import com.techelevator.tenmo.business.TransferBusiness;
import com.techelevator.tenmo.business.UserBusiness;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferBusiness transferBusiness;
    private UserBusiness userBusiness;
    private AccountBusiness accountBusiness;

    public TransferController(TransferBusiness transferBusiness, UserBusiness userBusiness, AccountBusiness accountBusiness) {
        this.transferBusiness = transferBusiness;
        this.userBusiness = userBusiness;
        this.accountBusiness = accountBusiness;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable int id, Principal principal) {
        //Make sure requesting user was either the sender or the receiver
        String username = principal.getName();
        List<Transfer> transferList = transferBusiness.getAllTransfers(username);
        Transfer transfer = null;
        for (Transfer t : transferList) {
            if (t.getId() == id) {
                transfer = t;
                break;
            }
        }
        return transfer;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(Principal principal) {
        return transferBusiness.getAllTransfers(principal.getName());
    }

    @RequestMapping(path = "/send/{receiver}/{amount}", method = RequestMethod.POST)
    public boolean createTransfer(@PathVariable String receiver, @PathVariable String amount, Principal principal) {

        //getSenderAccountId
        String sender = principal.getName();
        int senderUserId = userBusiness.findIdByUsername(sender);
        Account senderAccount = accountBusiness.getAccountByUserId(senderUserId);
        int senderAccountId = senderAccount.getId();

        //getReceiverAccountId
        int receiverUserId = userBusiness.findIdByUsername(receiver);
        Account receiverAccount = accountBusiness.getAccountByUserId(receiverUserId);
        int receiverAccountId = receiverAccount.getId();

        //getAmount
        BigDecimal transferAmount = new BigDecimal(amount);

        //getStatus
        String status = "APPROVED";

        //getDateTime
        LocalDateTime dateTime = LocalDateTime.now();

        //create Transfer object
        Transfer transfer = new Transfer(0, senderAccountId, receiverAccountId, transferAmount, status, dateTime);

        //add the Transfer
        return transferBusiness.createTransfer(transfer);
    }

}
