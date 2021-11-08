package com.igreendata.challenge.account.service;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.domain.AccountTransaction;
import com.igreendata.challenge.account.exception.InsufficientBalanceException;
import com.igreendata.challenge.account.exception.InvalidAccountException;
import com.igreendata.challenge.account.repository.AccountTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class AccountTransactionService {

    AccountTransactionRepository accountTransactionRepository;

    AccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountTransactionService.class);

    public AccountTransactionService(AccountTransactionRepository accountTransactionRepository, AccountService accountService) {
        this.accountTransactionRepository = accountTransactionRepository;
        this.accountService = accountService;
    }

    public List<AccountTransaction> getAccountsTransactions(Long accountId) {
        List<AccountTransaction> transactions =  accountTransactionRepository.findByAccountNumber(accountId);
        return transactions;
    }

    @Transactional
    public Account deposit(Long accountId, AccountTransaction transaction){
        Account account = accountService.getAccountByAccountNumber(accountId);
        if (account == null){
            logger.info("Invalid account found " + accountId);
            throw new InvalidAccountException();
        }
        account.setBalance(account.getBalance().add(transaction.getCreditAmount()));
        account =  accountService.saveAccount(account);
        accountTransactionRepository.save(transaction);
        return account;

    }

    @Transactional
    public Account withdraw(Long accountId, AccountTransaction transaction){
        Account account = accountService.getAccountByAccountNumber(accountId);
        if (account == null){
            logger.info("Invalid account found " + accountId);
            throw new InvalidAccountException();
        }
        if (account.getBalance().compareTo(transaction.getDebitAmount()) == -1) {
            logger.info("Insufficient balance found for account : " + accountId);
            throw new InsufficientBalanceException();
        }
        account.setBalance(account.getBalance().subtract(transaction.getDebitAmount()));
        account =  accountService.saveAccount(account);
        accountTransactionRepository.save(transaction);
        return account;
    }

}
