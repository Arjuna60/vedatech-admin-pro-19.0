package com.vedatech.pro.model.invoice;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public interface SalesByProduct {

    String getClaveUnidad();
    String getDescripcion();
    BigDecimal getCantidad();
    BigDecimal getImporte();
}
