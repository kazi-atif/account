package com.igreendata.challenge.account.service;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AccountService {

    AccountRepository accountRepository;

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountByAccountNumber(Long accountNumber) {
       Optional<Account> optional =  accountRepository.findById(accountNumber);
       if (optional.isPresent()){
           return optional.get();
       }
        return null;
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        List<Account> accounts =  accountRepository.findByCustomerId(customerId);
        return accounts;
    }

    public Account createAccount(Account account) {
        accountRepository.save(account);
        return account;
    }

    public Account saveAccount(Account account) {
        accountRepository.save(account);
        return account;
    }
}
