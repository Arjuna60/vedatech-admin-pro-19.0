package com.vedatech.pro.service;

import com.vedatech.pro.model.customer.Customer;
import com.vedatech.pro.model.invoice.Invoice;
import com.vedatech.pro.model.invoice.InvoiceItems;
import com.vedatech.pro.model.invoice.SalesByProduct;
import com.vedatech.pro.model.invoice.jaxb.Comprobante;
import com.vedatech.pro.model.supplier.Supplier;
import com.vedatech.pro.repository.customer.CustomerDao;
import com.vedatech.pro.repository.invoice.InvoiceDao;
import com.vedatech.pro.repository.invoice.InvoiceItemsDao;
import com.vedatech.pro.repository.supplier.SupplierDao;
import org.springframework.http.converter.xml.Jaxb2CollectionHttpMessageConverter;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.*;

@Service
public class CfdiServiceImp implements CfdiService {

    public final CustomerDao customerDao;
    public final SupplierDao supplierDao;
    public final InvoiceDao invoiceDao;
    public final InvoiceItemsDao invoiceItemsDao;

    public CfdiServiceImp(CustomerDao customerDao, SupplierDao supplierDao, InvoiceDao invoiceDao, InvoiceItemsDao invoiceItemsDao) {
        this.customerDao = customerDao;
        this.supplierDao = supplierDao;
        this.invoiceDao = invoiceDao;
        this.invoiceItemsDao = invoiceItemsDao;
    }

    @Override
    public <T> T contextFile(Class<T> tClass, String comprobante) {

        try {
            StringReader com = new StringReader(comprobante);
            JAXBContext context = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Comprobante unmarshal = (Comprobante) unmarshaller.unmarshal(com);
            System.out.println("CFDI SERVICE IMPL " +unmarshal.getReceptor().getRfc() +  unmarshal.getReceptor().getNombre());
            return (T) unmarshal;
        }catch (JAXBException e) {
//            throw new RuntimeException("Could not unmarshall workflow xml",e);
            return null;
        }

    }


    public Boolean existCustomer(Comprobante comprobante) {

        if (! customerDao.existsCustomerByCustomerRfc(comprobante.getReceptor().getRfc())) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public Supplier saveSupplier(Comprobante comprobante) {
        Supplier supplier = new Supplier();
        supplier.setCompany(comprobante.getEmisor().getNombre());
        supplier.setSupplierRfc(comprobante.getEmisor().getRfc());
        return supplierDao.save(supplier);
    }


    @Override
    public Boolean existSupplier(Comprobante comprobante) {
        if (! supplierDao.existsSupplierBySupplierRfc(comprobante.getEmisor().getRfc())) {
            return false;
        } else {
            return true;
        }
    }


    //    Verifica que la Addenda contenga NumSucursal si existe regresa un true, de lo contranrio false
    public Boolean existBranch(Comprobante comprobante) {

        try {
//            String numSucursal = comprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal();
            if (comprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal() != null ) {
                System.out.println("COMPROBANTE CON NUMERO DE SUCURSAL");
                return true;
            } else {
                System.out.println("COMPROBANTE NO TIENE SUCURSAL");
                return false;
            }
       }catch (Error error){
            System.out.println("NO EXISTE SUCURSAL EN ADDENDA" + error);
            return false;
        }
    }

    public Customer saveCustomer(Comprobante comprobante) {
        Customer customer = new Customer();
        customer.setCompany(comprobante.getReceptor().getNombre());
        customer.setCustomerRfc(comprobante.getReceptor().getRfc());
        if( existBranch(comprobante) ){
            customer.setStoreNum( comprobante.getAddenda().getFacturaInterfactura().getEncabezado().getNumSucursal());
        }
        return customerDao.save(customer);
    }




    public void fillInvoice(Invoice invoice, Comprobante comprobante) {

        invoice.setFolio(comprobante.getFolio());
        invoice.setFecha(comprobante.getFecha().toGregorianCalendar());
         if (comprobante.getCondicionesDePago() != null ){
             Integer paymentDay = convertToInteger(comprobante.getCondicionesDePago());
             GregorianCalendar gregorianCalendar = comprobante.getFecha().toGregorianCalendar();
             gregorianCalendar.add(Calendar.DATE, paymentDay);
             System.out.println("Gregorian Calendar Payment " + gregorianCalendar.toString());
             invoice.setFechaPago(gregorianCalendar);
         }

        invoice.setTotal(comprobante.getTotal());
//        getConceptos(comprobante);
        invoice.setInvoiceItems(getConceptos(comprobante));
        Invoice invoiceSaved = invoiceDao.save(invoice);
        calculateBalance(invoiceSaved);
        stadisticVolumeProducts();

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

    public void calculateBalance(Invoice invoice){

//        List<Customer> customerList = (List<Customer>) customerDao.findAll();

//        for (Customer c : customerList ){
//            c.setBalance(invoiceDao.getSumTotalByCustomer(c.getId()));
//            customerDao.save(c);
//        }

          Customer customer = invoice.getCustomer();
          customer.setBalance( invoiceDao.getSumTotalByCustomer(invoice.getCustomer().getId()));
          customerDao.save(customer);
//        List<Supplier> supplierList = (List<Supplier>) supplierDao.findAll();
//
//        for (Supplier c : supplierList ){
//            c.setBalanceToday(c.getBalance().add(invoiceDao.getSumTotalBySupplier(c.getId())));
//            supplierDao.save(c);
//        }
    }

      public List<InvoiceItems> getConceptos(Comprobante comprobante){

        List<Comprobante.Conceptos.Concepto> conceptos = comprobante.getConceptos().getConcepto();
        List<InvoiceItems> itemsList = new ArrayList<>();
        for (Comprobante.Conceptos.Concepto c: conceptos) {
            InvoiceItems invoiceItems = new InvoiceItems();
            System.out.println("Descripcion " + c.getDescripcion() + " Num Identificacion " + c.getNoIdentificacion());
            invoiceItems.setCantidad(c.getCantidad());
            invoiceItems.setClaveProdServ(c.getClaveProdServ());
            invoiceItems.setClaveUnidad(c.getNoIdentificacion());
            invoiceItems.setDescripcion(c.getDescripcion());
            invoiceItems.setImporte(c.getImporte());
            invoiceItems.setValorUnitario(c.getValorUnitario());
            invoiceItems.setUnidad(c.getUnidad());
            itemsList.add(invoiceItems);
        }

                return itemsList;
        }

        public void stadisticVolumeProducts () {

            List<Object[]> results = invoiceItemsDao.getData();
            List<SalesByProduct> salesByProductList = invoiceItemsDao.getDataSales();

            for (int i = 0; i < results.size(); i++) {
                Object[] arr = results.get(i);
                for (int j = 0; j < arr.length; j++) {
                    System.out.print(arr[j] + " ");
                }
                System.out.println();
            }

            for ( SalesByProduct s: salesByProductList ) {
                System.out.println("Sales by Product " + s.getDescripcion() + "  " + s.getCantidad());
            }


        }


}
