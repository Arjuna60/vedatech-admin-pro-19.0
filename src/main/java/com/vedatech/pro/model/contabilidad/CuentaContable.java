package com.vedatech.pro.model.contabilidad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vedatech.pro.model.BaseEntity;
import com.vedatech.pro.model.accounting.SubAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cuentas_contables")
public class CuentaContable extends BaseEntity {

    @Column(name = "nombre_cuena_contable")
    private String nombreCuentaContable;
    @Column(name = "num_cuenta_contable")
    private String numCuentaContable;
    private Double balance;
    private Boolean estado;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "cuentaContable", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties("cuentaContable")
    private List<SubCuenta> subCuentas;
}
