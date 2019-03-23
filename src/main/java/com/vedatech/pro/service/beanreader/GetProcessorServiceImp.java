package com.vedatech.pro.service.beanreader;

import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.CellProcessor;

 /*
        This is a example how can program according to the class properties:

                final CellProcessor[] processors = new CellProcessor[] {
                new UniqueHashCode(), // customerNo (must be unique)
                new NotNull(), // firstName
                new NotNull(), // lastName
                new ParseDate("dd/MM/yyyy"), // birthDate
                new NotNull(), // mailingAddress
                new Optional(new ParseBool()), // married
                new Optional(new ParseInt()), // numberOfKids
                new NotNull(), // favouriteQuote
                new StrRegEx(emailRegex), // email
                new LMinMax(0L, LMinMax.MAX_LONG) // loyaltyPoints
  */


@Service
public class GetProcessorServiceImp implements GetProcessorService {

    @Override
    public CellProcessor[] getSupplierProcessors() {

            final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
            StrRegEx.registerMessage(emailRegex, "must be a valid email address");
            System.out.println("CELL PROCESSOR");

            final CellProcessor[] processors = new CellProcessor[] {
                    new NotNull(), // CustomerId
                    new NotNull(), // CustomerName
                    new NotNull(), // Country
                    new NotNull(), // Country
                    new NotNull(), // Country
                    new Optional(), // Country
                    new Optional(), // Country
                    new Optional(), // Country
                    new StrRegEx(emailRegex), // email
                    new Optional(), // Country
                    new Optional(), // Country
                    new Optional(), // Country
                    new Optional(), // Country
                    new Optional(), // Country
                    new Optional(), // Country
                    new Optional(),
                    new Optional(),
                    new Optional(),
                    new Optional(),
                    new Optional(),
                    new ParseBigDecimal(), // Balance
                    new Optional(new ParseBool()), // married
            };

            return processors;
        }


    @Override
    public CellProcessor[] getCustomerProcessors() {

        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");
        System.out.println("CELL PROCESSOR");

        final CellProcessor[] processors = new CellProcessor[] {
//                new NotNull(), // CustomerId
                new NotNull(), // CustomerName
                new NotNull(), // customerRfc
                new Optional(), // storeNum
                new ParseBigDecimal(), // Balance
                new ParseBigDecimal(), // Budget
                new Optional(new ParseBool()), // status

        };

        return processors;
    }


    @Override
    public CellProcessor[] getBankProcessors() {
        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");
        System.out.println("CELL PROCESSOR");

        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(new ParseLong()), // CustomerId
                new NotNull(), // CustomerName
                new ParseDate("dd/MM/yyyy"), //Fecha
                new NotNull(), // Country
                new Optional(new ParseLong()), // PinCode
                new ParseDouble() // Balancel
        };

        return processors;
    }

    @Override
    public CellProcessor[] getBankTransProcessors() {
        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");
        System.out.println("CELL PROCESSOR");

        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(), //Cuenta
                new NotNull(new ParseDate("dd/MM/yyyy")), // Fecha operacion
                new NotNull(new ParseDate("dd/MM/yyyy")), //Fecha
                new NotNull(), // referencia
                new NotNull(), // descripcion
                new NotNull(), // codigo
                new NotNull(), //sucursal
                new NotNull((new ParseDouble())), // deposito
                new NotNull((new ParseDouble())), // retiro
                new NotNull((new ParseDouble())), // saldo
                new NotNull(), // movimiento
                new Optional()  // descrip detallada
        };

        return processors;
    }


    @Override
    public CellProcessor[] getAccountingTypeProcessors() {
        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");
        System.out.println("CELL PROCESSOR");

        final CellProcessor[] processors = new CellProcessor[] {
                new Optional(new ParseLong()), // id,
                new NotNull(), // Name
                new NotNull(), // Account
               new NotNull( new ParseDouble()) // Balance

        };

        return processors;
    }


}



