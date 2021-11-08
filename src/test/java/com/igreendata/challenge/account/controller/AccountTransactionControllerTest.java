package com.igreendata.challenge.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.domain.AccountTransaction;
import com.igreendata.challenge.account.dto.AccountType;
import com.igreendata.challenge.account.dto.TransactionType;
import com.igreendata.challenge.account.dto.AccountTransactionDto;
import com.igreendata.challenge.account.exception.InsufficientBalanceException;
import com.igreendata.challenge.account.exception.InvalidAccountException;
import com.igreendata.challenge.account.exception.InvalidInputException;
import com.igreendata.challenge.account.service.AccountService;
import com.igreendata.challenge.account.service.AccountTransactionService;
import com.igreendata.challenge.account.transformers.AccountTransformer;
import com.igreendata.challenge.account.util.AccountGenerator;
import com.igreendata.challenge.account.util.AccountTransactionGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTransactionControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountTransactionService accountTransactionService;

    @Test
    public void givenCustomerId_whenGetAccountsIsInvoked_thenAccountsOfTheCustomersAreSentBack() throws Exception {
        Long accountId = 12345L;
        AccountTransactionGenerator generator = new AccountTransactionGenerator();
        AccountTransformer transformer = new AccountTransformer();
        AccountTransaction creditTransaction = generator.seedAnAccountTransaction( accountId, Currency.getInstance("AUD") , TransactionType.CREDIT);
        AccountTransactionDto expectedTransaction = transformer.convertAccountTransactionDomainToDto(creditTransaction);
        List<AccountTransactionDto> expectedTransactions = new ArrayList<>();
        List<AccountTransaction> creditTransactions = new ArrayList<>();
        creditTransactions.add(creditTransaction);
        expectedTransactions.add(expectedTransaction);
        when(accountTransactionService.getAccountsTransactions(accountId)).thenReturn(creditTransactions);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/transactions/" + accountId)
                                .content(asJsonString(expectedTransactions))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(expectedTransactions)));
    }

    @Test
    public void givenTransactionWithInvalidDate_whenDepositIsInvoked_thenInvalidInputResponseIsSent() throws Exception {
        Long accountId = 12345L;
        AccountGenerator generator = new AccountGenerator(accountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        AccountTransformer transformer = new AccountTransformer();
        AccountTransactionDto creditTransactionDto = accountTransactionGenerator.seedAnAccountTransactionDto( accountId, Currency.getInstance("AUD") , TransactionType.CREDIT);
        AccountTransaction creditTransaction = transformer.convertAccountTransactionDtoToDomain(creditTransactionDto);
        creditTransactionDto.setValueDate("99/99/9999");
        Account expectedAccount  = generator.seedAnAccount(12345L,Currency.getInstance("AUD"),AccountType.SAVINGS);
        when(accountTransactionService.deposit(creditTransaction.getAccountNumber(), creditTransaction)).thenThrow(InvalidInputException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/transactions/deposit")
                                .content(asJsonString(expectedAccount))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest()) ;
    }

    @Test
    public void givenTransactionWithInvalidAccountNumber_whenDepositIsInvoked_thenInvalidInputResponseIsSent() throws Exception {
        Long accountId = 12345L;
        Long wrongAccountId = 12345L;
        AccountGenerator generator = new AccountGenerator(accountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        AccountTransformer transformer = new AccountTransformer();
        AccountTransactionDto creditTransactionDto = accountTransactionGenerator.seedAnAccountTransactionDto( accountId, Currency.getInstance("AUD") , TransactionType.CREDIT);
        AccountTransaction creditTransaction = transformer.convertAccountTransactionDtoToDomain(creditTransactionDto);
        Account expectedAccount  = generator.seedAnAccount(12345L,Currency.getInstance("AUD"),AccountType.SAVINGS);
        when(accountTransactionService.deposit(wrongAccountId, creditTransaction)).thenThrow(InvalidAccountException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/transactions/deposit")
                                .content(asJsonString(expectedAccount))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest()) ;
    }

    @Test
    public void givenTransactionWithInvalidAccountNumber_whenWithdrawalIsInvoked_thenInvalidInputResponseIsSent() throws Exception {
        Long accountId = 12345L;
        Long wrongAccountId = 12345L;
        AccountGenerator generator = new AccountGenerator(accountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        AccountTransformer transformer = new AccountTransformer();
        AccountTransactionDto creditTransactionDto = accountTransactionGenerator.seedAnAccountTransactionDto( accountId, Currency.getInstance("AUD") , TransactionType.CREDIT);
        AccountTransaction creditTransaction = transformer.convertAccountTransactionDtoToDomain(creditTransactionDto);
        Account expectedAccount  = generator.seedAnAccount(12345L,Currency.getInstance("AUD"),AccountType.SAVINGS);
        when(accountTransactionService.withdraw(wrongAccountId, creditTransaction)).thenThrow(InvalidAccountException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/transactions/deposit")
                                .content(asJsonString(expectedAccount))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest()) ;
    }

    @Test
    public void givenTransactionWithInSufficientAmount_whenWithdrawalIsInvoked_thenInvalidInputResponseIsSent() throws Exception {
        Long accountId = 12345L;
        Long wrongAccountId = 12345L;
        AccountGenerator generator = new AccountGenerator(accountService);
        AccountTransactionGenerator accountTransactionGenerator = new AccountTransactionGenerator();
        AccountTransformer transformer = new AccountTransformer();
        AccountTransactionDto creditTransactionDto = accountTransactionGenerator.seedAnAccountTransactionDto( accountId, Currency.getInstance("AUD") , TransactionType.CREDIT);
        AccountTransaction creditTransaction = transformer.convertAccountTransactionDtoToDomain(creditTransactionDto);
        Account expectedAccount  = generator.seedAnAccount(12345L,Currency.getInstance("AUD"),AccountType.SAVINGS);
        when(accountTransactionService.withdraw(wrongAccountId, creditTransaction)).thenThrow(InsufficientBalanceException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/transactions/deposit")
                                .content(asJsonString(expectedAccount))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
