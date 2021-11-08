package com.igreendata.challenge.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.dto.AccountType;
import com.igreendata.challenge.account.dto.AccountDto;
import com.igreendata.challenge.account.service.AccountService;
import com.igreendata.challenge.account.transformers.AccountTransformer;
import com.igreendata.challenge.account.util.AccountGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void givenCustomerId_whenGetAccountsIsInvoked_thenAccountsOfTheCustomersAreSentBack() throws Exception {

        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        AccountTransformer transformer = new AccountTransformer();
        List<Account> generateAccounts = accountGenerator.generateAccounts(2 , customerId, Currency.getInstance("AUD") , AccountType.CURRENT);
        List<AccountDto> expectedAccounts = generateAccounts.stream().map(p -> transformer.convertAccountDomainToDto(p)).collect(Collectors.toList());
        when(accountService.getAccountsByCustomerId(customerId)).thenReturn(generateAccounts);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/customers/12345/accounts")
                                .content(asJsonString(expectedAccounts))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(expectedAccounts)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void givenAccountId_whenGetAccountIsInvoked_thenAccountDetailsAreSentBack() throws Exception {

        Long customerId = 12345L;
        AccountGenerator accountGenerator = new AccountGenerator(accountService);
        AccountTransformer transformer = new AccountTransformer();
        Account generateAccount = accountGenerator.generateAccount( customerId, Currency.getInstance("AUD") , AccountType.CURRENT);
        AccountDto expectedAccount = transformer.convertAccountDomainToDto(generateAccount);
        when(accountService.getAccountByAccountNumber(generateAccount.getAccountNumber())).thenReturn(generateAccount);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/accounts/" + generateAccount.getAccountNumber())
                                .content(asJsonString(expectedAccount))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(expectedAccount)));
    }

}
