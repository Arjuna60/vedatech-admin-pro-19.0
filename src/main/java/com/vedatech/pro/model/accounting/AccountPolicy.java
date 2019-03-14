package com.vedatech.pro.model.accounting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import com.vedatech.pro.model.bank.BankTransaction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;


@Getter
@Setter
@Entity
@Table(name = "account_policy")
public class AccountPolicy extends BaseEntity {

    @Column(name = "fecha_operacion")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private GregorianCalendar fechaOperacion;

    @Column
    private String concept;

    @Column
    private BigDecimal credit;

    @Column
    private BigDecimal debit;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="sub_account_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SubAccount subAccount;


}
