package com.igreendata.challenge.account;

import com.igreendata.challenge.account.controller.AccountController;
import com.igreendata.challenge.account.controller.AccountTransactionController;
import com.igreendata.challenge.account.dto.AccountDto;
import com.igreendata.challenge.account.dto.AccountTransactionDto;
import com.igreendata.challenge.account.dto.AccountType;
import com.igreendata.challenge.account.dto.TransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Currency;

@Component
public class Simulation {

    private AccountController accountController;
    private AccountTransactionController transactionController;

    public Simulation(AccountController accountController, AccountTransactionController transactionController){
        this.accountController = accountController;
        this.transactionController = transactionController;
    }

    @PostConstruct
    public void doSimulation(){

        //Create Current Account
        AccountDto requestedAccount = new AccountDto(8888L, 12345L, "Test Account 1", AccountType.CURRENT, "08/11/2021", Currency.getInstance("AUD"), BigDecimal.ZERO);
        ResponseEntity<AccountDto> response = accountController.createAccount(requestedAccount);
        AccountDto createdAccount = response.getBody();

        //Deposit Money
        AccountTransactionDto transactionDto = new AccountTransactionDto(null, createdAccount.getAccountNumber(), "08/11/2021", Currency.getInstance("AUD"),
                null, new BigDecimal(1000), TransactionType.CREDIT, "Credited to " + createdAccount.getAccountNumber());
        response = transactionController.deposit(transactionDto);

        //Withdraw Money
        transactionDto = new AccountTransactionDto(null, createdAccount.getAccountNumber(), "08/11/2021", Currency.getInstance("AUD"),
                new BigDecimal(500), null, TransactionType.DEBIT, "Debited from " + createdAccount.getAccountNumber());
        response = transactionController.withdraw(transactionDto);

        //Deposit Money
        transactionDto = new AccountTransactionDto(null, createdAccount.getAccountNumber(), "08/11/2021", Currency.getInstance("AUD"),
                null, new BigDecimal(10000), TransactionType.CREDIT, "Credited to " + createdAccount.getAccountNumber());
        response = transactionController.deposit(transactionDto);


        //Withdraw Money
        transactionDto = new AccountTransactionDto(null, createdAccount.getAccountNumber(), "08/11/2021", Currency.getInstance("AUD"),
                new BigDecimal(5000), null, TransactionType.DEBIT, "Debited from " + createdAccount.getAccountNumber());
        response = transactionController.withdraw(transactionDto);


        //Create Savings Account
         requestedAccount = new AccountDto(9999L, 12345L, "Test Account 1", AccountType.SAVINGS, "08/11/2021", Currency.getInstance("AUD"), BigDecimal.ZERO);
         response = accountController.createAccount(requestedAccount);
        createdAccount = response.getBody();

        //Deposit Money
        transactionDto = new AccountTransactionDto(null, createdAccount.getAccountNumber(), "08/11/2021", Currency.getInstance("AUD"),
                null, new BigDecimal(900), TransactionType.CREDIT, "Credited to " + createdAccount.getAccountNumber());
        response = transactionController.deposit(transactionDto);

        //Withdraw Money
        transactionDto = new AccountTransactionDto(null, createdAccount.getAccountNumber(), "08/11/2021", Currency.getInstance("AUD"),
                new BigDecimal(400), null, TransactionType.DEBIT, "Debited from " + createdAccount.getAccountNumber());
        response = transactionController.withdraw(transactionDto);

        //Deposit Money
        transactionDto = new AccountTransactionDto(null, createdAccount.getAccountNumber(), "08/11/2021", Currency.getInstance("AUD"),
                null, new BigDecimal(9000), TransactionType.CREDIT, "Credited to " + createdAccount.getAccountNumber());
        response = transactionController.deposit(transactionDto);


        //Withdraw Money
        transactionDto = new AccountTransactionDto(null, createdAccount.getAccountNumber(), "08/11/2021", Currency.getInstance("AUD"),
                new BigDecimal(4000), null, TransactionType.DEBIT, "Debited from " + createdAccount.getAccountNumber());
        response = transactionController.withdraw(transactionDto);

    }


}
