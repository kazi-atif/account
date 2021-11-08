package com.igreendata.challenge.account.domain;

import com.igreendata.challenge.account.dto.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

@Entity
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long acctTranid;

    private Long accountNumber;

    private Calendar valueDate;

    private Currency currency;

    private BigDecimal debitAmount;

    private BigDecimal creditAmount;

    private TransactionType transactionType;

    private String transactionNarrative;

    public AccountTransaction() {
    }

    public AccountTransaction(Long acctTranid, Long accountNumber, Calendar valueDate, Currency currency, BigDecimal debitAmount, BigDecimal creditAmount, TransactionType transactionType, String transactionNarrative) {
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

    public Calendar getValueDate() {
        return valueDate;
    }

    public void setValueDate(Calendar valueDate) {
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
