package com.igreendata.challenge.account.service;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.domain.AccountTransaction;
import com.igreendata.challenge.account.dto.AccountType;
import com.igreendata.challenge.account.dto.TransactionType;
import com.igreendata.challenge.account.exception.InsufficientBalanceException;
import com.igreendata.challenge.account.exception.InvalidAccountException;
import com.igreendata.challenge.account.repository.AccountTransactionRepository;
import com.igreendata.challenge.account.util.AccountGenerator;
import com.igreendata.challenge.account.util.AccountTransactionGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountTransactionServiceTest {

    @Mock
    AccountService mockAccountService;

    @Mock
    AccountTransactionRepository mockAccountTransactionRepository;

    AccountTransactionService accountTransactionService;

    @BeforeEach
    void initUseCase() {
        accountTransactionService = new AccountTransactionService(mockAccountTransactionRepository,mockAccountService);
    }

    @Test
    public void givenCreditTransactionAndNonExistentAccount_whenDepositIsDone_thenExceptionIsThrown(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(mockAccountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        Account account = accountGenerator.seedAnAccount(customerId,Currency.getInstance("AUD") ,AccountType.CURRENT);
        AccountTransaction transaction = accountTransactionGenerator.seedAnAccountTransaction(account.getAccountNumber(), Currency.getInstance("AUD"), TransactionType.CREDIT);
        Account expectedAccount = new Account(88888L, account.getCustomerId(), account.getAccountName(),
                account.getAccountType(), account.getBalanceDate(), account.getCurrency(), account.getBalance());
        expectedAccount.setBalance(account.getBalance().add(transaction.getCreditAmount()));
        when(mockAccountService.getAccountByAccountNumber(account.getAccountNumber())).thenReturn(null);
        Assertions.assertThrows(InvalidAccountException.class, () -> {
            accountTransactionService.deposit(account.getAccountNumber(), transaction);
        });
    }

    @Test
    public void givenDebitTransactionAndNonExistentAccount_whenWithdrawalIsDone_thenExceptionIsThrown(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(mockAccountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        Account account = accountGenerator.seedAnAccount(customerId,Currency.getInstance("AUD") ,AccountType.CURRENT);
        account.setBalance(new BigDecimal(10000));
        AccountTransaction transaction = accountTransactionGenerator.seedAnAccountTransaction(account.getAccountNumber(), Currency.getInstance("AUD"), TransactionType.DEBIT);
        transaction.setDebitAmount(new BigDecimal(11000));
        Account expectedAccount = new Account(88888L, account.getCustomerId(), account.getAccountName(),
                account.getAccountType(), account.getBalanceDate(), account.getCurrency(), account.getBalance());
        expectedAccount.setBalance(account.getBalance().subtract(transaction.getDebitAmount()));
        when(mockAccountService.getAccountByAccountNumber(account.getAccountNumber())).thenReturn(null);
        Assertions.assertThrows(InvalidAccountException.class, () -> {
            accountTransactionService.withdraw(account.getAccountNumber(), transaction);
        });
    }

    @Test
    public void givenCreditTransaction_whenDepositIsDone_thenAccountIsCredited(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(mockAccountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        Account account = accountGenerator.seedAnAccount(customerId,Currency.getInstance("AUD") ,AccountType.CURRENT);
        AccountTransaction transaction = accountTransactionGenerator.seedAnAccountTransaction(account.getAccountNumber(), Currency.getInstance("AUD"), TransactionType.CREDIT);
        Account expectedAccount = new Account(account.getAccountNumber(), account.getCustomerId(), account.getAccountName(),
                account.getAccountType(), account.getBalanceDate(), account.getCurrency(), account.getBalance());
        expectedAccount.setBalance(account.getBalance().add(transaction.getCreditAmount()));
        when(mockAccountService.getAccountByAccountNumber(account.getAccountNumber())).thenReturn(account);
        when(mockAccountService.saveAccount(account)).thenReturn(expectedAccount);
        when(mockAccountTransactionRepository.save(transaction)).thenReturn(transaction);
        Account updatedAccount = accountTransactionService.deposit(account.getAccountNumber(), transaction);
        Assertions.assertEquals(expectedAccount, updatedAccount);
    }

    @Test
    public void givenDebitTransactionAndAccountWithSufficientFund_whenWithdrawalIsDone_thenAccountIsDebited(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(mockAccountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        Account account = accountGenerator.seedAnAccount(customerId,Currency.getInstance("AUD") ,AccountType.CURRENT);
        account.setBalance(new BigDecimal(10000));
        AccountTransaction transaction = accountTransactionGenerator.seedAnAccountTransaction(account.getAccountNumber(), Currency.getInstance("AUD"), TransactionType.DEBIT);
        transaction.setDebitAmount(new BigDecimal(7000));
        Account expectedAccount = new Account(account.getAccountNumber(), account.getCustomerId(), account.getAccountName(),
                account.getAccountType(), account.getBalanceDate(), account.getCurrency(), account.getBalance());
        expectedAccount.setBalance(account.getBalance().subtract(transaction.getDebitAmount()));
        when(mockAccountService.getAccountByAccountNumber(account.getAccountNumber())).thenReturn(account);
        when(mockAccountService.saveAccount(account)).thenReturn(expectedAccount);
        when(mockAccountTransactionRepository.save(transaction)).thenReturn(transaction);
        Account updatedAccount = accountTransactionService.withdraw(account.getAccountNumber(), transaction);
        Assertions.assertEquals(expectedAccount, updatedAccount);
    }

    @Test
    public void givenDebitTransactionAndAccountWithInSufficientFund_whenWithdrawalIsDone_thenExceptionIsThrown(){
        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(mockAccountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        Account account = accountGenerator.seedAnAccount(customerId,Currency.getInstance("AUD") ,AccountType.CURRENT);
        account.setBalance(new BigDecimal(10000));
        AccountTransaction transaction = accountTransactionGenerator.seedAnAccountTransaction(account.getAccountNumber(), Currency.getInstance("AUD"), TransactionType.DEBIT);
        transaction.setDebitAmount(new BigDecimal(11000));
        Account expectedAccount = new Account(account.getAccountNumber(), account.getCustomerId(), account.getAccountName(),
                account.getAccountType(), account.getBalanceDate(), account.getCurrency(), account.getBalance());
        expectedAccount.setBalance(account.getBalance().subtract(transaction.getDebitAmount()));
        when(mockAccountService.getAccountByAccountNumber(account.getAccountNumber())).thenReturn(account);
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            accountTransactionService.withdraw(account.getAccountNumber(), transaction);
        });
    }

}
