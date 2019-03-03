package com.vedatech.pro.repository.invoice;

import com.vedatech.pro.model.invoice.Invoice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceDao extends CrudRepository<Invoice, Long> {

     public List<Invoice> findAllByCustomerId(Long id);
     public List<Invoice> findAllBySupplierId(Long id);
}
