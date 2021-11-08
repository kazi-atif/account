package com.igreendata.challenge.account.transformers;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.domain.AccountTransaction;
import com.igreendata.challenge.account.dto.AccountDto;
import com.igreendata.challenge.account.dto.AccountTransactionDto;
import com.igreendata.challenge.account.exception.InvalidInputException;
import com.igreendata.challenge.account.util.Utils;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class AccountTransformer {

    public AccountDto convertAccountDomainToDto(Account account){
        AccountDto accountDto = null;
        if (account != null) {
            accountDto = new AccountDto(account.getAccountNumber(), account.getCustomerId(), account.getAccountName(), account.getAccountType(),
                    Utils.convertCalendarToString(account.getBalanceDate()), account.getCurrency(), account.getBalance());
        }
        return accountDto;

    }

    public AccountTransactionDto convertAccountTransactionDomainToDto(AccountTransaction transaction){
        AccountTransactionDto accountTransactionDto = null;
        if (transaction != null) {
            accountTransactionDto = new AccountTransactionDto(transaction.getAcctTranid(), transaction.getAccountNumber(),
                    Utils.convertCalendarToString(transaction.getValueDate()), transaction.getCurrency(), transaction.getDebitAmount(), transaction.getCreditAmount(),
                    transaction.getTransactionType(), transaction.getTransactionNarrative());
        }
        return accountTransactionDto;

    }

    public Account convertAccountDtoToDomain(AccountDto accountDto){
        Account account = null;
        if (accountDto != null) {
            try {
                account = new Account(accountDto.getAccountNumber(), accountDto.getCustomerId(), accountDto.getAccountName(), accountDto.getAccountType(),
                        Utils.convertStringToCalendar(accountDto.getBalanceDate()), accountDto.getCurrency(), accountDto.getBalance());
            } catch (ParseException ex) {
                throw new InvalidInputException("Excepted format of value Date is dd/mm/yyyy");
            }
        }
        return account;

    }

    public AccountTransaction convertAccountTransactionDtoToDomain(AccountTransactionDto transactionDto){
        AccountTransaction accountTransaction = null;
        if (transactionDto != null) {
            try {
                accountTransaction = new AccountTransaction(transactionDto.getAcctTranid(), transactionDto.getAccountNumber(),
                        Utils.convertStringToCalendar(transactionDto.getValueDate()), transactionDto.getCurrency(), transactionDto.getDebitAmount(), transactionDto.getCreditAmount(),
                        transactionDto.getTransactionType(), transactionDto.getTransactionNarrative());
            } catch (ParseException ex) {
                throw new InvalidInputException("Excepted format of value Date is dd/mm/yyyy");
            }
        }
        return accountTransaction;

    }
}
