package com.vedatech.pro.repository.invoice;

import com.vedatech.pro.model.invoice.InvoiceItems;
import com.vedatech.pro.model.invoice.SalesByProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceItemsDao extends CrudRepository<InvoiceItems, Long> {

 @Query("SELECT d.claveUnidad, d.descripcion, SUM(d.cantidad), sum(d.importe) FROM InvoiceItems d GROUP BY d.claveUnidad")
 List<Object[]> getData();

    @Query("SELECT d.claveUnidad as claveUnidad, d.descripcion as descripcion, SUM(d.cantidad) as cantidad, sum(d.importe) as importe FROM InvoiceItems d GROUP BY d.claveUnidad")
    List<SalesByProduct> getDataSales();

}
