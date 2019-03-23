package com.vedatech.pro.model.bank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import com.vedatech.pro.model.accounting.SubAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "banks")
public class Bank extends BaseEntity {


    // @NotEmpty
    @Column(name = "name_bank")
    private String nameBank;

    // @NotBlank
    @Column(name = "account_number")
    private Long accountNumber;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    String phone;

    @Column
    private String observation;

    @Column
    private BigDecimal balance;

    @Column
    private BigDecimal balanceToday;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="sub_account_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    SubAccount subAccount;



}
