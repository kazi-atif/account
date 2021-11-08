package com.igreendata.challenge.account.repository;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.domain.AccountTransaction;
import com.igreendata.challenge.account.dto.AccountType;
import com.igreendata.challenge.account.dto.TransactionType;
import com.igreendata.challenge.account.util.AccountGenerator;
import com.igreendata.challenge.account.service.AccountService;
import com.igreendata.challenge.account.util.AccountTransactionGenerator;
import com.igreendata.challenge.account.service.AccountTransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Currency;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class AccountTransactionRepositoryTest {

    AccountTransactionService accountTransactionService;


    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountTransactionRepository accountTransactionRepository;

    @BeforeEach
    void initUseCase() {
        accountService = new AccountService(accountRepository);
        accountTransactionService= new AccountTransactionService(accountTransactionRepository, accountService);

    }

    @Test
    public void givenACreditAccountTransaction_whenSaveIsInvoked_thenAccountTransactionIsSaved(){
       Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        Account account = accountGenerator.generateAccount(customerId, Currency.getInstance("AUD") , AccountType.CURRENT);
        AccountTransaction expectedTransaction =
                accountTransactionGenerator.seedAnAccountTransaction(account.getAccountNumber(),Currency.getInstance("AUD"), TransactionType.CREDIT);
        AccountTransaction actualTransaction  = accountTransactionRepository.save(expectedTransaction);
        verifyTransaction(expectedTransaction, actualTransaction);
    }

    @Test
    public void givenADebitAccountTransaction_whenSaveIsInvoked_thenAccountTransactionIsSaved(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        Account account = accountGenerator.generateAccount(customerId, Currency.getInstance("AUD") , AccountType.CURRENT);
        AccountTransaction expectedTransaction =
                accountTransactionGenerator.seedAnAccountTransaction(account.getAccountNumber(),Currency.getInstance("AUD"), TransactionType.DEBIT);
        AccountTransaction actualTransaction  = accountTransactionRepository.save(expectedTransaction);
        verifyTransaction(expectedTransaction, actualTransaction);
    }

    private void verifyTransaction(AccountTransaction expectedTransaction, AccountTransaction actualTransaction) {
        Assertions.assertEquals(expectedTransaction.getTransactionType(), actualTransaction.getTransactionType());
        Assertions.assertEquals(expectedTransaction.getAccountNumber(), actualTransaction.getAccountNumber());
        Assertions.assertEquals(expectedTransaction.getTransactionNarrative(), actualTransaction.getTransactionNarrative());
        Assertions.assertEquals(expectedTransaction.getCurrency(), actualTransaction.getCurrency());
        Assertions.assertEquals(expectedTransaction.getCreditAmount(), actualTransaction.getCreditAmount());
        Assertions.assertEquals(expectedTransaction.getDebitAmount(), actualTransaction.getDebitAmount());
        Assertions.assertEquals(expectedTransaction.getValueDate(), actualTransaction.getValueDate());
    }
}
