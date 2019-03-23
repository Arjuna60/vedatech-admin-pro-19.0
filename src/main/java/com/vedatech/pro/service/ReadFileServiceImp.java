package com.vedatech.pro.service;


import com.vedatech.pro.model.dto.AccountingTypeDto;
import com.vedatech.pro.repository.accounting.AccountingTypeDao;
import com.vedatech.pro.service.beanreader.GetProcessorService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadFileServiceImp implements ReadFileService {

     public final GetProcessorService getProcessors;
//      public final SupplierService supplierService;

    public ReadFileServiceImp(GetProcessorService getProcessors
    ) {
        this.getProcessors = getProcessors;
//        this.supplierService = supplierService;
    }

    @Override
    public <T> List<T> readWithCsvBeanReader(Class<T> tClass, String filename) throws IOException {

        List<T> objects = new ArrayList<>();
        ICsvBeanReader beanReader = null;
//
//        if (tClass.getName() == "com.vedatech.pro.model.dto.AccountingTypeDto") {
//            System.out.println(" TCLAS ES ACCOUNTING TYPE DAO CLASS");
//        }else {
//            System.out.println("LAS CLASES NO SON IGUALES" + tClass.getName());
//        }

        try {
            beanReader = new CsvBeanReader(new FileReader(filename), CsvPreference.STANDARD_PREFERENCE);
            CellProcessor[] processors = new CellProcessor[100];
            // the header elements are used to map the values to the bean (names must match)
            final String[] header = beanReader.getHeader(true);

            if (tClass.getName() == "com.vedatech.pro.model.dto.AccountingTypeDto") {
                System.out.println(" TCLAS ES ACCOUNTING TYPE DAO CLASS");
                processors = getProcessors.getAccountingTypeProcessors();
            }

            if (tClass.getName() == "com.vedatech.pro.model.bank.BankTransactionDto") {
                System.out.println(" LA CLASE ES BANK-TRANSACTIO DTO");
                processors = getProcessors.getBankTransProcessors();
            }

            if (tClass.getName() == "com.vedatech.pro.model.supplier.Supplier") {
                System.out.println(" LA CLASE ES SUPPLIER");
                processors = getProcessors.getSupplierProcessors();
            }

            if (tClass.getName() == "com.vedatech.pro.model.customer.Customer") {
                System.out.println(" LA CLASE ES CUSTOMER");
                processors = getProcessors.getCustomerProcessors();
            }

            T object;
            // System.out.println("CUSTOMER " + customer.getFirstName());
            while( (object = beanReader.read(tClass, header, processors)) != null) {
                // System.out.println("BEAN READER LINE " + beanReader.getLineNumber() + " ROW " + beanReader.getRowNumber());
                System.out.println(String.format("lineNo=%s, rowNo=%s, customer=%s", beanReader.getLineNumber(),
                        beanReader.getRowNumber(), object));
                objects.add(object);
            }

        }
        finally {
            if( beanReader != null ) {
                beanReader.close();
            }
        }

     return objects;
    }


    @Override
    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}


