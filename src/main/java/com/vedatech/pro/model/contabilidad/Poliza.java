package com.vedatech.pro.model.contabilidad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.invoice.Invoice;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
@Table(name = "polizas")
public class Poliza extends BaseEntity {


    @Column(name = "fecha_operacion")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private GregorianCalendar fechaOperacion;

    @Column
    private String concepto;

    @Column
    private BigDecimal credito;

    @Column
    private BigDecimal debito;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="subcuentas_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SubCuenta subCuenta;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="invoices_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Invoice invoice;

}
