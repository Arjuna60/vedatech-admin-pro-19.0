package com.vedatech.pro.model.accounting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import com.vedatech.pro.model.customer.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "sub_account")
public class SubAccount extends BaseEntity {


    @Column(name = "name_account")
    private String nameAccount;

    @Column(name = "account_number")
    private String accountNumber;

    @Column
    private BigDecimal balance;

    @Column
    private Boolean status;

    @ManyToOne()
    @JoinColumn(name="accounting_type_id")
    @JsonIgnoreProperties("subAccount")
    private AccountingType accountType;

   /* @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="customer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Customer customer;*/

    @Override
    public String toString() {
        return "SubAccount{" +
                "nameAccount='" + nameAccount + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", status=" + status +
                ", accountType=" + accountType +
//                ", customer=" + customer +
                '}';
    }
}
