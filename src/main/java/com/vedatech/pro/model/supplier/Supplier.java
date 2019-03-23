package com.vedatech.pro.model.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.info.ContactInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "suppliers")
public class Supplier extends ContactInfo
{

    private String company;
    private String displayName;
    private Boolean status;
//    @Value("${supplierBalance}")
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(0.00);
    @Builder.Default
    private BigDecimal balanceToday = BigDecimal.valueOf(0.00);
    @Column(name = "supplier_rfc")
    private String supplierRfc;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="sub_account_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    SubAccount subAccount;
}

