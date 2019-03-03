package com.vedatech.pro.model.accounting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private Double balance;

    @Column
    Double balanceToday;

    @Column
    private Boolean status;

    @ManyToOne()
    @JoinColumn(name="accounting_type_id")
    @JsonIgnoreProperties("subAccount")
    private AccountingType accountType;


}
