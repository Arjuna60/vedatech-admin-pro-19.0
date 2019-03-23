package com.vedatech.pro.repository.bank;

import com.vedatech.pro.model.accounting.AccountPolicy;
import com.vedatech.pro.model.bank.BankTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BankTransactionDao extends CrudRepository<BankTransaction, Long> {


    @Query("SELECT SUM(e.deposito) - SUM(e.retiro) FROM BankTransaction e where e.bank.id = ?1")
    BigDecimal getTotalBankTransBalance(Long id);

    @Query("SELECT SUM(e.deposito) - SUM(e.retiro) FROM BankTransaction e where e.bank.subAccount = ?1")
    BigDecimal getBalanceBySubAccount(Long id);
}
