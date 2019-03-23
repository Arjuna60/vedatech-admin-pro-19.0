package com.vedatech.pro.model.info;


import com.vedatech.pro.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class ShippingAddress extends BaseEntity {



    private String shippingStreet;

    private String shippingCity;

    private String shippingState;

    private String shippingCode;

    private String shippingCountry;


}
