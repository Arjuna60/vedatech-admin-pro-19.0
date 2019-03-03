package com.vedatech.pro.model.bank;

import com.vedatech.pro.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "bank_transaction_dto")
public class BankTransactionDto extends BaseEntity {

    @Column
    private String Cuenta;

    @Column(name = "date_operation")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaOperacion;

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha;

    @Column(name = "reference")
    private String referencia;

    @Column(name = "description")
    private String descripcion;

    @Column(name = "code_transfer")
    private String codTransac;

    @Column
    private String sucursal;

    @Column
    private Double depositos;

    @Column
    private Double retiros;

    @Column
    private Double saldo;

    @Column
    private String movimiento;

    @Lob
    @Column(name = "description_details")
    private String DescripcionDetallada;


    @Override
    public String toString() {
        return "BankTransactionDto{" +
                "Cuenta='" + Cuenta + '\'' +
                ", fechaOperacion=" + fechaOperacion +
                ", fecha=" + fecha +
                ", referencia='" + referencia + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", codTransac='" + codTransac + '\'' +
                ", sucursal='" + sucursal + '\'' +
                ", deposito=" + depositos +
                ", retiro=" + retiros +
                ", saldo=" + saldo +
                ", movimiento='" + movimiento + '\'' +
                ", DescripcionDetallada='" + DescripcionDetallada + '\'' +
                '}';
    }
}
