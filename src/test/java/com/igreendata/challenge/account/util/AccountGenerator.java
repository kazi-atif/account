package com.igreendata.challenge.account.util;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.dto.AccountType;
import com.igreendata.challenge.account.dto.AccountDto;
import com.igreendata.challenge.account.service.AccountService;

import java.math.BigDecimal;
import java.util.*;

public class AccountGenerator {

    public  static final String DUMMY_ACCOUNT_NAME = "Test";

    AccountService accountService;

    public AccountGenerator (AccountService accountService){
        this.accountService = accountService;

    }

    public List<Account> generateAccounts(int numberOfAccounts, Long customerId, Currency currency, AccountType accountType) {
        Calendar now = new GregorianCalendar();
        Random random = new Random();
        List<Account> generatedAccounts = new ArrayList<>();
        for (int i = 0 ; i < numberOfAccounts ; i++){
            Account account = generateAccount(customerId, currency, accountType);
            generatedAccounts.add(account);
        }

        return generatedAccounts;
    }

    public Account generateAccount(Long customerId,Currency currency, AccountType accountType) {
        Account account = seedAnAccount(customerId,currency, accountType);
        accountService.createAccount(account);
        return account;
    }

    public Account seedAnAccount(Long customerId, Currency currency, AccountType accountType) {
        Calendar now = new GregorianCalendar();
        Random random = new Random();
        Account account = new Account();
        account.setAccountNumber(random.nextLong());
        account.setCustomerId(customerId);
        account.setAccountName(DUMMY_ACCOUNT_NAME + account.getAccountNumber());
        account.setAccountType(accountType);
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(currency);
        account.setBalanceDate(now);
        return account;
    }

    public AccountDto seedAnAccountDto(Long customerId, Currency currency, AccountType accountType) {
        Calendar now = new GregorianCalendar();
        Random random = new Random();
        AccountDto account = new AccountDto();
        account.setAccountNumber(random.nextLong());
        account.setCustomerId(customerId);
        account.setAccountName(DUMMY_ACCOUNT_NAME + account.getAccountNumber());
        account.setAccountType(accountType);
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(currency);
        account.setBalanceDate(Utils.convertCalendarToString(now));
        return account;
    }

}
