package com.vedatech.pro.controller.contabilidad;


import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.contabilidad.Poliza;
import com.vedatech.pro.model.contabilidad.SubCuenta;
import com.vedatech.pro.model.customer.Customer;
import com.vedatech.pro.model.invoice.Invoice;
import com.vedatech.pro.repository.accounting.SubAccountDao;
import com.vedatech.pro.repository.contabilidad.PolizaDao;
import com.vedatech.pro.repository.contabilidad.SubCuentaDao;
import com.vedatech.pro.repository.customer.CustomerDao;
import com.vedatech.pro.repository.invoice.InvoiceDao;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.util.List;

@RestController
@RequestMapping("/api/assess/")
public class AssessController {

    public final CustomerDao customerDao;
    public final InvoiceDao invoiceDao;
    public final SubAccountDao subAccountDao;
    public final SubCuentaDao subCuentaDao;
    public final PolizaDao polizaDao;


    public AssessController(CustomerDao customerDao, InvoiceDao invoiceDao, SubAccountDao subAccountDao, SubCuentaDao subCuentaDao, PolizaDao polizaDao) {
        this.customerDao = customerDao;
        this.invoiceDao = invoiceDao;
        this.subAccountDao = subAccountDao;
        this.subCuentaDao = subCuentaDao;
        this.polizaDao = polizaDao;
    }

    //-------------------Received Xml Customer File--------------------------------------------------------
    @RequestMapping(value = "customer/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> assessCustomer(@PathVariable (value = "id") Long id) {


        Customer customer = customerDao.findById(id).get();
       SubAccount subAccount = new SubAccount();
        System.out.println("Cutomer for Assess " + customer.getCompany());
        List<Invoice> invoiceList =  invoiceDao.findAllByPolizas_Empty();

        for (Invoice i: invoiceList) {

            System.out.println(i.getFolio());
        }

        List<Invoice> invoiceNoPoliza =  invoiceDao.findAllByPolizas_EmptyAndCustomerId(customer.getId());

        for (Invoice i: invoiceNoPoliza) {

            System.out.println(i.getFolio());

            Poliza poliza = new Poliza();
//            poliza.setSubCuenta();
            poliza.setCredito(i.getTotal());
            poliza.setConcepto("Poliza no");
            poliza.setSubCuenta(subCuentaDao.findByCustomerId(customer.getId()));
            poliza.setInvoice(i);
            polizaDao.save(poliza);

        }

        return null;
    }


    //-------------------Received Xml Customer File--------------------------------------------------------
    @RequestMapping(value = "invoice/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> addPoliza(@PathVariable (value = "id") Long id) {

        Poliza poliza = new Poliza();

        Customer customer = customerDao.findById(id).get();
        System.out.println("Cutomer for Assess " + customer);
        List<Invoice> invoiceList =  invoiceDao.findAllByPolizas_Empty();

        for (Invoice i: invoiceList) {

            System.out.println(i.getFolio());
        }

        poliza.setConcepto("Concepto");


        return null;
    }

    }
