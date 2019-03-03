package com.vedatech.pro.repository.supplier;

import com.vedatech.pro.model.supplier.SupplierTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SupplierTransactionDao extends CrudRepository<SupplierTransaction, Long> {

    @Query("SELECT SUM(e.invoiceTotal) - SUM(e.payment) FROM SupplierTransaction e where e.supplier.id = ?1")
    Double getTotalSupplierTransBalance(Long id);

}
