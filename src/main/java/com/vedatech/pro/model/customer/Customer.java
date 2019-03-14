package com.vedatech.pro.model.customer;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.accounting.AccountPolicy;
import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.info.ContactInfo;
import com.vedatech.pro.model.invoice.Invoice;
import com.vedatech.pro.model.invoice.InvoiceItems;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends ContactInfo {

    private String company;
    private String displayName;
    private String storeNum;
    private Boolean status;
    private BigDecimal balance;
    private BigDecimal budget;
    @Column(name = "customer_rfc")
    private String customerRfc;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="sub_account_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    SubAccount subAccount;

   /* @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id")
    private List<Invoice> invoices;
*/


}
