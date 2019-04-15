package com.vedatech.pro.repository.accounting;

import java.util.List;
import java.util.Optional;

import com.vedatech.pro.model.accounting.AccountingType;

import com.vedatech.pro.model.accounting.SubAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingTypeDao extends CrudRepository<AccountingType, Long> {

        Optional<AccountingType> findAccountingTypeById(Long Id);

         List<AccountingType> findAccountingTypeByState(Boolean val);



}
