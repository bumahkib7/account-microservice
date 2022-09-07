package com.codeninja.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Account {

    private Long accountNumber;
    private Long customerNumber;
    private String customerName;
    private BigDecimal balance;
    private  AccountStatus accountStatus = AccountStatus.OPEN ;


    public void markOverdrawn() {
        this.accountStatus=AccountStatus.OVERDRAWN;
    }
    public void removeOverdrawnStatus() {
        accountStatus = AccountStatus.OPEN;
    }
    public void close() {
        accountStatus = AccountStatus.CLOSED;
        balance = BigDecimal.valueOf(0);
    }
    public void withdrawFunds(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
    public void addFunds(BigDecimal amount) {
        balance = balance.add(amount);
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public Long getAccountNumber() {
        return accountNumber;
    }
    public String getCustomerName() {
        return customerName;
    }
    public AccountStatus getAccountStatus() {
        return accountStatus;

    }

}
