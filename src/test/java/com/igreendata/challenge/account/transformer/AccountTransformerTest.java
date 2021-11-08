package com.igreendata.challenge.account.transformer;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.domain.AccountTransaction;
import com.igreendata.challenge.account.dto.AccountType;
import com.igreendata.challenge.account.dto.TransactionType;
import com.igreendata.challenge.account.dto.AccountDto;
import com.igreendata.challenge.account.dto.AccountTransactionDto;
import com.igreendata.challenge.account.service.AccountService;
import com.igreendata.challenge.account.transformers.AccountTransformer;
import com.igreendata.challenge.account.util.AccountGenerator;
import com.igreendata.challenge.account.util.AccountTransactionGenerator;
import com.igreendata.challenge.account.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
public class AccountTransformerTest {

    AccountTransformer accountTransformer;

    @Mock
    AccountService mockAccountService;

    @BeforeEach
    void initUseCase() {
        accountTransformer = new AccountTransformer();
    }

    @Test
    public void givenAccountDomain_whenConvertDomainToDtoIsInvoked_thenAccountDtoIsGenerated(){
        AccountGenerator accountGenerator = new AccountGenerator(mockAccountService);
        Account account = accountGenerator.seedAnAccount(12345L, Currency.getInstance("AUD"), AccountType.SAVINGS);
        AccountDto actual= accountTransformer.convertAccountDomainToDto(account);
        Assertions.assertEquals(actual.getAccountName(), account.getAccountName());
        Assertions.assertEquals(actual.getAccountNumber(), account.getAccountNumber());
        Assertions.assertEquals(actual.getBalance(), account.getBalance());
        Assertions.assertEquals(actual.getBalanceDate(), Utils.convertCalendarToString(account.getBalanceDate()));
        Assertions.assertEquals(actual.getCurrency(), account.getCurrency());
        Assertions.assertEquals(actual.getCustomerId(), account.getCustomerId());
        Assertions.assertEquals(actual.getAccountType(), account.getAccountType());
    }


    @Test
    public void givenAccountTransactionDomain_whenConvertDomainToDtoIsInvoked_thenAccountTransactionDtoIsGenerated(){
        AccountTransactionGenerator generator = new AccountTransactionGenerator();
        AccountTransaction accountTransaction = generator.seedAnAccountTransaction(12345L, Currency.getInstance("AUD"), TransactionType.CREDIT);
        AccountTransactionDto actual= accountTransformer.convertAccountTransactionDomainToDto(accountTransaction);
        Assertions.assertEquals(actual.getAccountNumber(), accountTransaction.getAccountNumber());
        Assertions.assertEquals(actual.getAcctTranid(), accountTransaction.getAcctTranid());
        Assertions.assertEquals(actual.getTransactionType(), accountTransaction.getTransactionType());
        Assertions.assertEquals(actual.getTransactionNarrative(), accountTransaction.getTransactionNarrative());
        Assertions.assertEquals(actual.getCreditAmount(), accountTransaction.getCreditAmount());
        Assertions.assertEquals(actual.getDebitAmount(), accountTransaction.getDebitAmount());
        Assertions.assertEquals(actual.getValueDate(), Utils.convertCalendarToString(accountTransaction.getValueDate()));
        Assertions.assertEquals(actual.getCurrency(), accountTransaction.getCurrency());
    }

    @Test
    public void givenAccountTransactionDto_whenConvertDtoToDomainIsInvoked_thenAccountTransactionDomainIsGenerated(){
        AccountTransactionGenerator generator = new AccountTransactionGenerator();
        AccountTransactionDto accountTransactionDto = generator.seedAnAccountTransactionDto(12345L, Currency.getInstance("AUD"), TransactionType.CREDIT);
        AccountTransaction actual= accountTransformer.convertAccountTransactionDtoToDomain(accountTransactionDto);
        Assertions.assertEquals(actual.getAccountNumber(), accountTransactionDto.getAccountNumber());
        Assertions.assertEquals(actual.getAcctTranid(), accountTransactionDto.getAcctTranid());
        Assertions.assertEquals(actual.getTransactionType(), accountTransactionDto.getTransactionType());
        Assertions.assertEquals(actual.getTransactionNarrative(), accountTransactionDto.getTransactionNarrative());
        Assertions.assertEquals(actual.getCreditAmount(), accountTransactionDto.getCreditAmount());
        Assertions.assertEquals(actual.getDebitAmount(), accountTransactionDto.getDebitAmount());
        Assertions.assertEquals(actual.getCurrency(), accountTransactionDto.getCurrency());
    }

}
