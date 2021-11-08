package com.igreendata.challenge.account.service;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.dto.AccountType;
import com.igreendata.challenge.account.repository.AccountRepository;
import com.igreendata.challenge.account.util.AccountGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository mockAccountRepository;

    AccountService accountService;

    @BeforeEach
    void initUseCase() {
        accountService = new AccountService(mockAccountRepository);
    }

    @Test
    public void givenSavingsAccount_whenCreateAccountIsInvoked_thenNewAccountIsCreated(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        Account expectedAccount = accountGenerator.generateAccount(customerId,Currency.getInstance("AUD") ,AccountType.CURRENT);
        when(mockAccountRepository.save(expectedAccount)).thenReturn(expectedAccount);
        Account actualAccount = accountService.createAccount(expectedAccount);
        Assertions.assertEquals(expectedAccount, actualAccount);
    }


    @Test
    public void givenNoAccounts_whenGetAccountsIsInvoked_thenReturnsEmptyList(){
        Long customerId = 12345L;
        List<Account> actualAccounts = accountService.getAccountsByCustomerId(customerId);
        List<Account> expectedAccounts = new ArrayList<>();
        Assertions.assertIterableEquals(expectedAccounts, actualAccounts);
    }

    @Test
    public void givenAustraliaCurrentAccounts_whenGetAccountsIsInvoked_thenReturnsThem(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        List <Account> expectedCurrentAccounts = accountGenerator.generateAccounts(10, customerId,Currency.getInstance("AUD") ,AccountType.CURRENT);
        when(mockAccountRepository.findByCustomerId(customerId)).thenReturn(expectedCurrentAccounts);
        List<Account> actualAccounts = accountService.getAccountsByCustomerId(customerId);
        Assertions.assertIterableEquals(expectedCurrentAccounts, actualAccounts);
    }

    @Test
    public void givenAustraliaCurrentAndSavingsAccounts_whenGetAccountsIsInvoked_thenReturnsThem(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        List <Account> currentAccounts = accountGenerator.generateAccounts(10,customerId, Currency.getInstance("AUD") ,AccountType.CURRENT);
        List <Account> savingsAccounts = accountGenerator.generateAccounts(10,customerId, Currency.getInstance("AUD") ,AccountType.SAVINGS);
        List <Account> expectedAllAccounts = new ArrayList<>();
        expectedAllAccounts.addAll(currentAccounts);
        expectedAllAccounts.addAll(savingsAccounts);
        when(mockAccountRepository.findByCustomerId(customerId)).thenReturn(expectedAllAccounts);
        List<Account> actualAccounts = accountService.getAccountsByCustomerId(customerId);
        Assertions.assertIterableEquals(expectedAllAccounts, actualAccounts);
    }

    @Test
    public void givenSingaporeCurrentAccounts_whenGetAccountsIsInvoked_thenReturnsThem(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        List <Account> expectedCurrentAccounts = accountGenerator.generateAccounts(10, customerId,Currency.getInstance("SGD") ,AccountType.CURRENT);
        when(mockAccountRepository.findByCustomerId(customerId)).thenReturn(expectedCurrentAccounts);
        List<Account> actualAccounts = accountService.getAccountsByCustomerId(customerId);
        Assertions.assertIterableEquals(expectedCurrentAccounts, actualAccounts);
    }

    @Test
    public void givenSingaporeCurrentAndSavingsAccounts_whenGetAccountsIsInvoked_thenReturnsThem(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        List <Account> currentAccounts = accountGenerator.generateAccounts(10, customerId,Currency.getInstance("SGD") ,AccountType.CURRENT);
        List <Account> savingsAccounts = accountGenerator.generateAccounts(10,customerId, Currency.getInstance("SGD") ,AccountType.SAVINGS);
        List <Account> expectedAllAccounts = new ArrayList<>();
        expectedAllAccounts.addAll(currentAccounts);
        expectedAllAccounts.addAll(savingsAccounts);
        when(mockAccountRepository.findByCustomerId(customerId)).thenReturn(expectedAllAccounts);
        List<Account> actualAccounts = accountService.getAccountsByCustomerId(customerId);
        Assertions.assertIterableEquals(expectedAllAccounts, actualAccounts);
    }


}
