package com.igreendata.challenge.account.dto;

import java.math.BigDecimal;
import java.util.Currency;

public class AccountTransactionDto {
    private Long acctTranid;

    private Long accountNumber;

    private String valueDate;

    private Currency currency;

    private BigDecimal debitAmount;

    private BigDecimal creditAmount;

    private TransactionType transactionType;

    private String transactionNarrative;

    public AccountTransactionDto() {
    }

    public AccountTransactionDto(Long acctTranid, Long accountNumber, String valueDate, Currency currency, BigDecimal debitAmount, BigDecimal creditAmount, TransactionType transactionType, String transactionNarrative) {
        this.acctTranid = acctTranid;
        this.accountNumber = accountNumber;
        this.valueDate = valueDate;
        this.currency = currency;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.transactionType = transactionType;
        this.transactionNarrative = transactionNarrative;
    }

    public Long getAcctTranid() {
        return acctTranid;
    }

    public void setAcctTranid(Long acctTranid) {
        this.acctTranid = acctTranid;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionNarrative() {
        return transactionNarrative;
    }

    public void setTransactionNarrative(String transactionNarrative) {
        this.transactionNarrative = transactionNarrative;
    }
}
