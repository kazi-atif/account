package com.igreendata.challenge.account.domain;

import com.igreendata.challenge.account.dto.AccountType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.Objects;

@Entity
public class Account {

    public Account() {
    }

    public Account(Long accountNumber, Long customerId, String accountName, AccountType accountType, Calendar balanceDate, Currency currency, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balanceDate = balanceDate;
        this.currency = currency;
        this.balance = balance;
    }

    @Id
    private Long accountNumber;

    private Long customerId;

    private String accountName;

    private AccountType accountType;

    private Calendar balanceDate;

    private Currency currency;

    private BigDecimal balance;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Calendar getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Calendar balanceDate) {
        this.balanceDate = balanceDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) && Objects.equals(customerId, account.customerId) && Objects.equals(accountName, account.accountName) && accountType == account.accountType && Objects.equals(balanceDate, account.balanceDate) && Objects.equals(currency, account.currency) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, customerId, accountName, accountType, balanceDate, currency, balance);
    }
}
