package com.vedatech.pro.controller.customer;

import com.vedatech.pro.model.bank.BankTransaction;
import com.vedatech.pro.model.customer.Customer;
import com.vedatech.pro.model.invoice.Invoice;
import com.vedatech.pro.model.invoice.InvoiceItems;
import com.vedatech.pro.model.invoice.jaxb.Comprobante;
import com.vedatech.pro.repository.customer.CustomerDao;
import com.vedatech.pro.repository.invoice.InvoiceDao;
import com.vedatech.pro.service.CfdiService;
import com.vedatech.pro.service.customer.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;

@Controller
@RequestMapping("/api/customer")
public class CustomerInvoiceController {

    HttpHeaders headers = new HttpHeaders();

    public final CustomerService customerService;
    public final CustomerDao customerDao;
    public final InvoiceDao invoiceDao;
    public final CfdiService cfdiService;

    public CustomerInvoiceController(CustomerService customerService, CustomerDao customerDao, InvoiceDao invoiceDao, CfdiService cfdiService) {
        this.customerService = customerService;
        this.customerDao = customerDao;
        this.invoiceDao = invoiceDao;
        this.cfdiService = cfdiService;
    }

    //-------------------Received Xml Customer File--------------------------------------------------------
    @RequestMapping(value = "/send-xml-file", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Customer> getXmlInvoice(@RequestBody String comprobante) throws IOException {
        HttpStatus status = HttpStatus.OK;

        Customer customer = new Customer();
        Invoice invoice = new Invoice();
        List<InvoiceItems> invoiceItemsList = new ArrayList<>();

        //Save the uploaded file to this folder
        System.out.println("Comprobante " + comprobante.toString());
        StringReader com = new StringReader(comprobante);
        JAXBContext context = null;


        try {

//            context = JAXBContext.newInstance(Comprobante.class);
           Comprobante unmarshal = (Comprobante) cfdiService.contextFile(Comprobante.class, comprobante);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            Comprobante unmarshal = (Comprobante) unmarshaller.unmarshal(com);
//            System.out.println(unmarshal.getEmisor().getNombre());
//            System.out.println(unmarshal.getReceptor().getNombre());
//            System.out.println("ADDENDA " +  unmarshal.getAddenda().getId());

//            System.out.println(unmarshal.getSubTotal());
//            System.out.println(unmarshal.getTotal());
//            Comprobante.Addenda addenda = unmarshal.getAddenda();
//            List<Object> list = addenda.getAny();
//            System.out.println("ADDENDA " + list);

            customer.setCompany(unmarshal.getReceptor().getNombre());
            invoice.setFecha(unmarshal.getFecha().toGregorianCalendar());
            invoice.setCondicionesDePago(unmarshal.getCondicionesDePago());
            Integer paymentDay = convertToInteger(unmarshal.getCondicionesDePago());
            GregorianCalendar gregorianCalendar = unmarshal.getFecha().toGregorianCalendar();
            gregorianCalendar.add(Calendar.DATE, paymentDay);
            System.out.println("Gregorian Calendar Payment " + gregorianCalendar.toString());
            invoice.setFechaPago(gregorianCalendar);
            ZonedDateTime zonedDateTime = gregorianCalendar.toZonedDateTime();
            Instant instant = zonedDateTime.toInstant();
            Date d = Date.from(instant);

            invoice.setFolio(unmarshal.getFolio());
            invoice.setSubTotal(unmarshal.getSubTotal());
            invoice.setTotal(unmarshal.getTotal());

            String findRfc = unmarshal.getReceptor().getRfc();
            System.out.println("RFC " + findRfc);


            try {

                Customer findCustomer = customerDao.findCustomerByCustomerRfc(findRfc);
                if (findCustomer != null) {
                    System.out.println("Customer " + findCustomer.getCompany());

                    System.out.println("Si existe Customer");
                    System.out.println("Customer Existe" + findCustomer.getCustomerRfc());

                    Comprobante.Conceptos conceptos = unmarshal.getConceptos();
                    List<Comprobante.Conceptos.Concepto> concepto = conceptos.getConcepto();

                    for (Comprobante.Conceptos.Concepto c : concepto) {
                        InvoiceItems invoiceItems = new InvoiceItems();

                        System.out.println("Cantidad " + c.getCantidad().toString() + " Descripcion " + c.getDescripcion().toString() + " Valor Unitatio " + c.getValorUnitario().toString() + " SubTotal " + c.getImporte().toString());

                        invoiceItems.setCantidad(c.getCantidad());
                        invoiceItems.setDescripcion(c.getDescripcion());
                        invoiceItems.setClaveProdServ(c.getClaveProdServ());
                        invoiceItems.setClaveUnidad(c.getClaveUnidad());
                        invoiceItems.setValorUnitario(c.getValorUnitario());
                        invoiceItems.setImporte(c.getImporte());
                        invoiceItemsList.add(invoiceItems);
                    }

                    invoice.setInvoiceItems(invoiceItemsList);
                    invoice.setCustomer(findCustomer);
                    invoiceDao.save(invoice);


                    BigDecimal Total = unmarshal.getTotal();
                    System.out.println("Total " + Total);
                    headers.set("La transaction fue exitosa", "fail");
                    status = HttpStatus.OK;

                } else {

                    headers.set("mensaje", "El Cliente no se ha dado de alta!!");
                    status = HttpStatus.NOT_FOUND;
                    System.out.println("el cliente no se ha dado de alta!!");

//                    Customer customer = new Customer();
                    customer.setCompany(unmarshal.getReceptor().getNombre());
                    customer.setCustomerRfc(unmarshal.getReceptor().getRfc());

                }

            } catch (NullPointerException e) {

                e.printStackTrace();
                System.out.println("Customer No Existe");

                headers.set("mensaje", "El Cliente no existe favor de verificar");
                status = HttpStatus.OK;
//                return ResponseEntity<String> (headers, HttpStatus.NOT_FOUND);
            }


        } catch (JAXBException e) {
            e.printStackTrace();
        }
//            File cfdi = new File("C:/SAT2/ANT021004RI7_PUHS6505319L9_764.xml");

//            System.out.println("CFDI " + cfdim);


        return new ResponseEntity<Customer>(customer, headers, status);
    }

    public Integer convertToInteger(String str) {

        System.out.println("LENGTH STR " + str.length() + " VALUE " + str);
        String replace = str.replaceAll("\\s", "");
        String replaceTwo = replace.replaceAll("[^a-zA-Z]","");
        String replaceTree= replace.replaceAll("[^0-9]","");

        System.out.println("REPLACE TWO " + replaceTwo);
        System.out.println("REPLACE TWO " + replaceTree);

        try {
            int valueIntger = Integer.valueOf(replaceTree);
            return valueIntger;

        } catch (NumberFormatException e) {

            System.out.println("ERROR " + e);
            return 0;
        }
    }




    @RequestMapping(value = "/getAllInvoiceCustomer", method = RequestMethod.GET)
    public ResponseEntity<List<Invoice>> getAllBankTransaction() {

        List<Invoice> invoiceList = (List<Invoice>) invoiceDao.findAllInvoicesByCustomer();
        if (invoiceList.isEmpty()) {
            headers.set("error", "no existen movimientos a la cuentas del Cliente");
            return new ResponseEntity<List<Invoice>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Invoice>>(invoiceList, HttpStatus.OK);

    }


    @RequestMapping(value = "/getAllInvoiceByCustomer/{id}", method = RequestMethod.POST)
    public ResponseEntity<List<Invoice>> getAllInvoiceByCustomer(@PathVariable (value = "id") Long id) {

        Customer customer = new Customer();
        List<Invoice> invoiceList = (List<Invoice>) invoiceDao.findAllByCustomerId(id);
        if (invoiceList.isEmpty()) {
            headers.set("error", "no existen movimientos a la cuentas del Cliente");
            return new ResponseEntity<List<Invoice>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Invoice>>(invoiceList, HttpStatus.OK);

    }



}

