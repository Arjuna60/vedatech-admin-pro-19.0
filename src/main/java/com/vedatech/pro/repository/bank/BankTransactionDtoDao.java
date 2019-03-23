package com.vedatech.pro.repository.bank;

import com.vedatech.pro.model.bank.BankTransactionDto;
import org.springframework.data.repository.CrudRepository;


public interface BankTransactionDtoDao extends CrudRepository<BankTransactionDto, Long> {

    public void deleteById(Long id);
}
