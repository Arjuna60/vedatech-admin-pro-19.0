package com.vedatech.pro.repository.bank;

import com.vedatech.pro.model.bank.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BankDao extends CrudRepository<Bank, Long> {

        Bank findBankByAccountNumber(Long accountNumber);
        Bank findBankById(Long id);

        @Query("SELECT SUM(e.balance) FROM Bank e where e.id = ?1")
        Double getInitialBalance(Long id);

        @Query("SELECT SUM(e.balance) FROM Bank e where e.subAccount.id = ?1")
        Double balanceBySubacc(Long id);

        @Query("SELECT SUM(e.balanceToday) FROM Bank e where e.subAccount.id = ?1")
        Double sumBalanceBySubacc(Long id);


        Boolean existsBanksBySubAccount_AccountNumber(String accountNumber) ;


}
