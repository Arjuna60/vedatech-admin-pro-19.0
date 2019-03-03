package com.vedatech.pro.model.dto;


import com.vedatech.pro.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountingTypeDto extends BaseEntity {

    private String name;
    private String account;
    private Double balance;

}
