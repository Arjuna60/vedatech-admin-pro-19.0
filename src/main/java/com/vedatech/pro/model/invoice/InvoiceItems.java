package com.vedatech.pro.model.invoice;


import com.vedatech.pro.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "invoice_items")
public class InvoiceItems extends BaseEntity {

    private String claveProdServ;
    private BigDecimal cantidad;
    private String unidad;
    private String claveUnidad;
    private String descripcion;
    private BigDecimal valorUnitario;
    private BigDecimal importe;
    private BigDecimal descuento;
}
