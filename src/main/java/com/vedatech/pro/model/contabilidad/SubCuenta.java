package com.vedatech.pro.model.contabilidad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import com.vedatech.pro.model.accounting.AccountingType;
import com.vedatech.pro.model.customer.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "subcuentas")
public class SubCuenta extends BaseEntity {


    @Column(name = "nombre_subcuenta")
    private String nombreSubCuenta;

    @Column(name = "numero_subcuenta")
    private String numeroSubCuenta;

    @Column
    private BigDecimal balance;

    @Column
    private Boolean estado;

    @ManyToOne()
    @JoinColumn(name="cuentas_contables_id")
    @JsonIgnoreProperties("subCuentas")
    private CuentaContable cuentaContable;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="customer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Customer customer;


}
