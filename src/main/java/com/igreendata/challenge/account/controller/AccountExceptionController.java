package com.igreendata.challenge.account.controller;

import com.igreendata.challenge.account.exception.InsufficientBalanceException;
import com.igreendata.challenge.account.exception.InvalidAccountException;
import com.igreendata.challenge.account.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountExceptionController {

    @ExceptionHandler(value = InsufficientBalanceException.class)
    public ResponseEntity<Object> exception(InsufficientBalanceException exception) {
        return new ResponseEntity<>("Insufficient Balance for withdrawal", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidAccountException.class)
    public ResponseEntity<Object> exception(InvalidAccountException exception) {
        return new ResponseEntity<>("Invalid account", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidInputException.class)
    public ResponseEntity<Object> exception(InvalidInputException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
