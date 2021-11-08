package com.igreendata.challenge.account.repository;

import com.igreendata.challenge.account.domain.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, String> {

    List<AccountTransaction> findByAccountNumber(Long accountNumber);
}
