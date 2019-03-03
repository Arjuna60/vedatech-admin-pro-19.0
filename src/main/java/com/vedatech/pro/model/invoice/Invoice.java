package com.vedatech.pro.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import com.vedatech.pro.model.accounting.AccountPolicy;
import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.bank.Bank;
import com.vedatech.pro.model.customer.Customer;
import com.vedatech.pro.model.supplier.Supplier;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice extends BaseEntity {


    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private GregorianCalendar fecha;
    private GregorianCalendar fechaPago;
    private String condicionesDePago;
    private BigDecimal subTotal;
    private BigDecimal total;
    private BigDecimal pago;
    private String folio;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="customer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Customer customer;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="supplier_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Supplier supplier;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItems> invoiceItems;

}
