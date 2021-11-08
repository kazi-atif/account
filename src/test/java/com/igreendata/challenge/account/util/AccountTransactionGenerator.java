package com.igreendata.challenge.account.util;

import com.igreendata.challenge.account.domain.AccountTransaction;
import com.igreendata.challenge.account.dto.TransactionType;
import com.igreendata.challenge.account.dto.AccountTransactionDto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.Random;

public class AccountTransactionGenerator {

    public  static final String DUMMY_COMMENTS = "Transaction";

    public AccountTransaction seedAnAccountTransaction(Long accountId, Currency currency, TransactionType transactionType) {
        Calendar now = new GregorianCalendar();
        Random random = new Random();
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setAccountNumber(accountId);
        accountTransaction.setTransactionType(transactionType);
        accountTransaction.setTransactionNarrative(DUMMY_COMMENTS + accountId );
        accountTransaction.setCurrency(currency);
        if (transactionType.equals(TransactionType.DEBIT)){
            accountTransaction.setDebitAmount(new BigDecimal(random.nextInt()));
        }else if (transactionType.equals(TransactionType.CREDIT)){
            accountTransaction.setCreditAmount(new BigDecimal(random.nextInt()));
        }

        accountTransaction.setValueDate(now);
        return accountTransaction;
    }

    public AccountTransactionDto seedAnAccountTransactionDto(Long accountId, Currency currency, TransactionType transactionType) {
        Calendar now = new GregorianCalendar();
        Random random = new Random();
        AccountTransactionDto accountTransaction = new AccountTransactionDto();
        accountTransaction.setAccountNumber(accountId);
        accountTransaction.setTransactionType(transactionType);
        accountTransaction.setTransactionNarrative(DUMMY_COMMENTS + accountId );
        accountTransaction.setCurrency(currency);
        if (transactionType.equals(TransactionType.DEBIT)){
            accountTransaction.setDebitAmount(new BigDecimal(random.nextInt()));
        }else if (transactionType.equals(TransactionType.CREDIT)){
            accountTransaction.setCreditAmount(new BigDecimal(random.nextInt()));
        }

        accountTransaction.setValueDate(Utils.convertCalendarToString(now));
        return accountTransaction;
    }

}
