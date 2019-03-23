package com.vedatech.pro.repository.accounting;

import com.vedatech.pro.model.accounting.AccountPolicy;
import com.vedatech.pro.model.accounting.AccountingType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AccountPolicyDao extends CrudRepository<AccountPolicy, Long> {

//    public AccountPolicy findBySubAccountId(Long id);
    public List<AccountPolicy> findBySubAccountId(Long id);

//    @Query("select p from AccountingType p where p.nameAccount like %?1%")
//    public List<AccountingType> findByNombre(String term);

    @Query("SELECT SUM(e.credit)- SUM (e.debit) FROM AccountPolicy e where e.subAccount.id = ?1")
    BigDecimal getAccountPolicyBalanceBySubAccount(Long term);



    Boolean existsAccountPoliciesBySubAccountId(Long id);
}
