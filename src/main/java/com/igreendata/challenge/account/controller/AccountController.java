package com.igreendata.challenge.account.controller;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.dto.AccountDto;
import com.igreendata.challenge.account.service.AccountService;
import com.igreendata.challenge.account.transformers.AccountTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.stream.Collectors;

@RestController
public class AccountController {
    public static final String METHOD_GET_ACCOUNTS = "api/customers/{customerId}/accounts";
    public static final String METHOD_POST_CREATE_ACCOUNT = "api/accounts";
    private static final String METHOD_GET_ACCOUNT = "api/accounts/{accountId}";

    private AccountService accountService;

    private AccountTransformer transformer;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController (AccountService accountService, AccountTransformer accountTransformer){
        this.accountService = accountService;
        this.transformer = accountTransformer;
    }
    @GetMapping(path = METHOD_GET_ACCOUNTS)
    public ResponseEntity<List<AccountDto>> getAccounts(@PathVariable(name = "customerId") Long customerId) {
        logger.info("Getting Accounts for Customer with Id " + customerId);
        List<Account>  accounts = accountService.getAccountsByCustomerId(customerId);
        List<AccountDto>  accountDtos = accounts.stream().map(p -> transformer.convertAccountDomainToDto(p)).collect(Collectors.toList());
        return new ResponseEntity<>(accountDtos, HttpStatus.OK);
    }

    @PostMapping(path = METHOD_POST_CREATE_ACCOUNT)
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto ) {
        logger.info("Create Account for Customer with Id " + accountDto.getCustomerId());
        Account account = transformer.convertAccountDtoToDomain(accountDto);
        Account createdAccount = accountService.createAccount(account);
        accountDto = transformer.convertAccountDomainToDto(createdAccount);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping(path = METHOD_GET_ACCOUNT)
    public ResponseEntity<AccountDto> getAccount(@PathVariable(name = "accountId") Long accountId ) {
        logger.info("Get Details for account " + accountId);
        Account account = accountService.getAccountByAccountNumber(accountId);
        AccountDto  accountDto = transformer.convertAccountDomainToDto(account);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

}

