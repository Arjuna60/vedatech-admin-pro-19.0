package com.vedatech.pro.model.product;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.vedatech.pro.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    public String code;
    public String quantity;
    public String productName;
    public BigDecimal unitPrice;
    public BigDecimal subTotal;
}
