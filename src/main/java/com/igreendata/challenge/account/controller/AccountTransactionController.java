package com.igreendata.challenge.account.controller;

import com.igreendata.challenge.account.domain.Account;
import com.igreendata.challenge.account.domain.AccountTransaction;
import com.igreendata.challenge.account.dto.AccountDto;
import com.igreendata.challenge.account.dto.AccountTransactionDto;
import com.igreendata.challenge.account.service.AccountTransactionService;
import com.igreendata.challenge.account.transformers.AccountTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountTransactionController {
        public static final String METHOD_GET_TRANSACTIONS = "api/transactions/{accountId}";
        public static final String METHOD_POST_ACCOUNT_WITHDRAW = "api/transactions/withdraw";
        private static final String METHOD_POST_ACCOUNT_DEPOSIT = "api/transactions/deposit";

        private AccountTransactionService accountTransactionService;

        private AccountTransformer transformer;

        Logger logger = LoggerFactory.getLogger(AccountController.class);

        public AccountTransactionController (AccountTransactionService accountTransactionService, AccountTransformer accountTransformer){
                this.accountTransactionService = accountTransactionService;
                this.transformer = accountTransformer;
        }

        @GetMapping(path = METHOD_GET_TRANSACTIONS)
        public ResponseEntity<List<AccountTransactionDto>> getTransactions(@PathVariable(name = "accountId") Long accountId) {
            logger.info("Getting transactions for Account with Id " + accountId);
            List<AccountTransaction>  transactions = accountTransactionService.getAccountsTransactions(accountId);
            List<AccountTransactionDto>  transactionDtos = transactions.stream().map(p -> transformer.convertAccountTransactionDomainToDto(p)).collect(Collectors.toList());
            return new ResponseEntity<>(transactionDtos, HttpStatus.OK);
        }

        @PostMapping(path = METHOD_POST_ACCOUNT_WITHDRAW)
        public ResponseEntity<AccountDto> withdraw(@RequestBody AccountTransactionDto transactionDto ) {
            logger.info("Received withdrawal request for Account with Id " + transactionDto.getAccountNumber());
            AccountTransaction transaction = transformer.convertAccountTransactionDtoToDomain(transactionDto);
            Account updatedAccount = accountTransactionService.withdraw(transaction.getAccountNumber(), transaction);
            AccountDto  accountDto = transformer.convertAccountDomainToDto(updatedAccount);
            return new ResponseEntity<>(accountDto, HttpStatus.OK);

        }

        @PostMapping(path = METHOD_POST_ACCOUNT_DEPOSIT)
        public ResponseEntity<AccountDto> deposit(@RequestBody AccountTransactionDto transactionDto) {
            logger.info("Received deposit request for Account with Id " + transactionDto.getAccountNumber());
            AccountTransaction transaction = transformer.convertAccountTransactionDtoToDomain(transactionDto);
            Account updatedAccount = accountTransactionService.deposit(transactionDto.getAccountNumber(), transaction);
            AccountDto  accountDto = transformer.convertAccountDomainToDto(updatedAccount);
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        }

}

