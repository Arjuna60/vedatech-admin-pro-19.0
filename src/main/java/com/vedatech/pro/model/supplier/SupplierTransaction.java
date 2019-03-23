package com.vedatech.pro.model.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import com.vedatech.pro.model.accounting.AccountPolicy;
import com.vedatech.pro.model.bank.Bank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "supplier_transaction")
public class SupplierTransaction extends BaseEntity {

    @Column(name = "fecha_operacion")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaOperacion;

    @Column(name = "fecha_pago")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaPago;

    @Column(name = "referencia")
    private String referencia;

    @Column(name = "descripcion")
    private String descripcion;

    @Column
    private Double invoiceTotal;

    @Column
    private Double payment;


    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="supplier_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Supplier supplier;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bank_trans_id")
    private List<AccountPolicy> poliza;

}
