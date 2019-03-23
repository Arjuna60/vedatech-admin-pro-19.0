package com.vedatech.pro.controller.invoice;


import com.vedatech.pro.model.accounting.AccountingType;
import com.vedatech.pro.model.customer.Customer;
import com.vedatech.pro.model.invoice.Invoice;
import com.vedatech.pro.model.invoice.jaxb.Comprobante;
import com.vedatech.pro.repository.customer.CustomerDao;
import com.vedatech.pro.repository.invoice.InvoiceDao;
import com.vedatech.pro.repository.supplier.SupplierDao;
import com.vedatech.pro.service.CfdiService;
import io.reactivex.Flowable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.io.FileUtils;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    public final CfdiService cfdiService;
    public final CustomerDao customerDao;
    public final SupplierDao supplierDao;
    public final InvoiceDao invoiceDao;

    public InvoiceController(CfdiService cfdiService, CustomerDao customerDao, SupplierDao supplierDao, InvoiceDao invoiceDao) {
        this.cfdiService = cfdiService;
        this.customerDao = customerDao;
        this.supplierDao = supplierDao;
        this.invoiceDao = invoiceDao;
    }

    HttpHeaders headers = new HttpHeaders();


    //-------------------Received Xml Customer File--------------------------------------------------------
    @RequestMapping(value = "/customer-xml-file", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> customerXmlInvoice(@RequestBody String comprobante) throws JAXBException {

        String receptorRfc = "ANT021004RI7";
        Customer customer = new Customer();
        Invoice invoice = new Invoice();
        String message = "";
       try {


           Comprobante unmarshalComprobante = (Comprobante) cfdiService.contextFile(Comprobante.class, comprobante);
           if (unmarshalComprobante == null ){
               message = "Fail to save the folio exist";
               return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
           }
           if (unmarshalComprobante.getAddenda().getFacturaInterfactura() == null){
               message = "Fail to save the folio exist";
               return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
           }


           if ( !unmarshalComprobante.getEmisor().getRfc().equals(receptorRfc)){
               message = "Fail to save the folio " + unmarshalComprobante.getFolio() + "! exist";
               return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
           }
           if (invoiceDao.existsInvoiceByFolio(unmarshalComprobante.getFolio()) ){
               message = "Fail to save the folio " + unmarshalComprobante.getFolio() + "! exist";
               return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
           }


           if (!cfdiService.existCustomer(unmarshalComprobante)) {
               invoice.setCustomer(cfdiService.saveCustomer(unmarshalComprobante));
               cfdiService.fillInvoice(invoice, unmarshalComprobante);

           } else if (!cfdiService.existBranch(unmarshalComprobante)) {
               invoice.setCustomer(customerDao.findCustomerByCustomerRfc(unmarshalComprobante.getReceptor().getRfc()));
               cfdiService.fillInvoice(invoice, unmarshalComprobante);
           } else if (customerDao.findCustomerByCustomerRfcAndStoreNum(unmarshalComprobante.getReceptor().getRfc(), unmarshalComprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal()) != null) {
               invoice.setCustomer(customerDao.findCustomerByCustomerRfcAndStoreNum(unmarshalComprobante.getReceptor().getRfc(), unmarshalComprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal()));
               cfdiService.fillInvoice(invoice, unmarshalComprobante);
           } else {
               invoice.setCustomer(cfdiService.saveCustomer(unmarshalComprobante));
               cfdiService.fillInvoice(invoice, unmarshalComprobante);
           }

           return new ResponseEntity<String>(HttpStatus.OK);
       }catch (Error e) {
           System.out.println("Error " + e);
           return new ResponseEntity<String>(HttpStatus.CONFLICT);

       }
    }


    //-------------------Received Xml Supplier File--------------------------------------------------------
    @RequestMapping(value = "/supplier-xml-file", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> supplierXmlInvoice(@RequestBody String comprobante) throws JAXBException {

        String receptorRfc = "ANT021004RI7";
        Customer customer = new Customer();
        Invoice invoice = new Invoice();
        String message = "";
        try {


            Comprobante unmarshalComprobante = (Comprobante) cfdiService.contextFile(Comprobante.class, comprobante);
            if (unmarshalComprobante == null ){
                message = "Fail to save the folio exist";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
//            if (unmarshalComprobante.getAddenda().getFacturaInterfactura() == null){
//                message = "Fail to save the folio exist";
//                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
//            }


            if ( !unmarshalComprobante.getReceptor().getRfc().equals(receptorRfc)){
                message = "Verifique su comprobante xml, RFC equivocado " ;
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
            if (invoiceDao.existsInvoiceByFolio(unmarshalComprobante.getFolio()) ){
                message = "ya existe la factura en la base de datos";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }


            if (!cfdiService.existSupplier(unmarshalComprobante)) {
//                invoice.setSupplier(cfdiService.saveSupplier(unmarshalComprobante));
//                cfdiService.fillInvoice(invoice, unmarshalComprobante);
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);

            } else {
                invoice.setSupplier(supplierDao.findBySupplierRfc(unmarshalComprobante.getEmisor().getRfc()));
                cfdiService.fillInvoice(invoice, unmarshalComprobante);
            }

            return new ResponseEntity<String>(HttpStatus.OK);
           }catch (Error e) {
            System.out.println("Error " + e);
            return new ResponseEntity<String>(HttpStatus.CONFLICT);

        }
    }


    @RequestMapping(value = "/upload",headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    public ResponseEntity<String> getAllSubAcc(@RequestParam("file") MultipartFile file,
                                               @RequestParam("dataType") String dataType) {

        Flowable<String> nombres = Flowable.just("Edilberto Ramos", "Irasema Ramos", "Cesar Ramos", "Javier Ramos","Cris Ramos");

        nombres.subscribe(element -> System.out.println("Elements" + element));

       List<String> files = new ArrayList<>();
        String message = "";
        try {
            System.out.println(dataType);

          //  System.out.println(file.getOriginalFilename());
          //  System.out.println("LENGTH " + file.getSize());
            File file1 = convert(file);

            String comprobanteFile = FileUtils.readFileToString(file1, "UTF-8");
//            getInvoices(files);

            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }

    }

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public void getInvoices(List<String> invoices) throws JAXBException {

        List<String> stringList = new ArrayList<>();
        for (String f: invoices){
            customerXmlInvoice(f);
        }
    }

 }


