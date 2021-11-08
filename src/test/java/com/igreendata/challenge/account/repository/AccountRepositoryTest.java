package com.igreendata.challenge.account.repository;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.dto.AccountType;
import com.igreendata.challenge.account.util.AccountGenerator;
import com.igreendata.challenge.account.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Currency;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class AccountRepositoryTest {

    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;



    @BeforeEach
    void initUseCase() {
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void givenAnAccount_whenSaveIsInvoked_thenAccountIsSaved(){
       Long accountId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        Account expectedAccount = accountGenerator.seedAnAccount(accountId, Currency.getInstance("AUD") , AccountType.CURRENT);
        Account actualAccount = accountRepository.save(expectedAccount);
        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void givenAnId_whenFindIsInvoked_thenAccountIsFetched(){
       Long accountId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        Account expectedAccount = accountGenerator.generateAccount(accountId,Currency.getInstance("AUD") , AccountType.CURRENT);
       List<Account> accounts = accountRepository.findByCustomerId(accountId);
        Assertions.assertEquals(expectedAccount, accounts.get(0));
    }

}
