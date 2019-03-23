package com.vedatech.pro.repository.bank;

import com.vedatech.pro.model.bank.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface BankDao extends CrudRepository<Bank, Long> {

        Bank findBankByAccountNumber(Long accountNumber);
        Bank findBankById(Long id);

        @Query("SELECT SUM(e.balance) FROM Bank e where e.id = ?1")
        BigDecimal getInitialBalance(Long id);

        @Query("SELECT SUM(e.balance) FROM Bank e where e.subAccount.id = ?1")
        BigDecimal balanceBySubacc(Long id);

        @Query("SELECT SUM(e.balanceToday) FROM Bank e where e.subAccount.id = ?1")
        BigDecimal sumBalanceBySubacc(Long id);


        Boolean existsBanksBySubAccount_AccountNumber(String accountNumber) ;


}
