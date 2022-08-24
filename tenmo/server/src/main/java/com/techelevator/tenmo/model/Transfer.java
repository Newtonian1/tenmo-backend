package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {

    private int id;

    @NotNull(message = "Transfer must have an associated sender id")
    @Min(value=2001)
    private int senderId;

    @NotNull(message = "Transfer must have an associated receiver id")
    @Min(value=2001)
    private int receiverId;

    @NotNull(message = "Transfer must have an associated amount")
    @Positive(message = "Amount cannot be negative")
    private BigDecimal amount;

    @NotNull(message = "Transfer must have an associated status")
    private String status;

    @NotNull(message = "Transfer must have an associated dateTime")
    private LocalDateTime dateTime;

    public Transfer() {
    }

    public Transfer(int id, int senderId, int receiverId, BigDecimal amount, String status, LocalDateTime dateTime) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.status = status;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
