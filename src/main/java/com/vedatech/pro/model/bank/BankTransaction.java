package com.vedatech.pro.model.bank;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import com.vedatech.pro.model.accounting.AccountPolicy;
import com.vedatech.pro.model.invoice.Invoice;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bank_transactions")
public class BankTransaction extends BaseEntity {


    @Column(name = "fecha_operacion")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaOperacion;

    @Column(name = "fecha")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha;

    @Column(name = "referencia")
    private String referencia;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "codigo_transfer")
    private String codTransac;

    @Column
    private String sucursal;

    @Column
    private Double deposito;

    @Column
    private Double retiro;

    @Column
    private Double saldo;

    @Column
    private String movimiento;

    @Column(name = "descrip_detalle")
    private String DescripcionDetallada;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="bank_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Bank bank;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poliza_id")
    private List<AccountPolicy> poliza;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoices_id")
    private List<Invoice> invoices;



    @Override
    public String toString() {
        return "BankTransaction{" +
                "fechaOperacion=" + fechaOperacion +
                ", fecha=" + fecha +
                ", referencia='" + referencia + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", codigoTransf='" + codTransac + '\'' +
                ", sucursal='" + sucursal + '\'' +
                ", deposito=" + deposito +
                ", retiro=" + retiro +
                ", saldo=" + saldo +
                ", movimiento='" + movimiento + '\'' +
                ", descripcionDetalle='" + DescripcionDetallada + '\'' +
                '}';
    }
}

