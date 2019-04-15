package com.vedatech.pro.repository.accounting;


import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.contabilidad.SubCuenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;


public interface SubAccountDao extends CrudRepository<SubAccount, Long> {

    Long countByAccountType_State(Boolean val);
    Long countAllByStatusAndAccountType_Id(Boolean val, Long Id);
    List<SubAccount> findAllByStatusAndAccountType_Id(Boolean val, Long Id);

    @Query("SELECT  e.balance FROM SubAccount e where e.id = ?1")
    BigDecimal getSubAccountBalance(Long id);

    //SubAccount findByCustomerId(Long id);

    List<SubAccount> findAllByAccountType_Id(Long Id);
    Boolean existsSubAccountByAccountNumber(String accountNumber);
}
