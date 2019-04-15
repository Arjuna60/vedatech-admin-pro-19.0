package com.vedatech.pro.controller.customer;

import com.vedatech.pro.model.customer.Customer;
import com.vedatech.pro.repository.accounting.SubAccountDao;
import com.vedatech.pro.service.customer.CustomerService;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

        private final CustomerService customerService;
        private final SubAccountDao subAccountDao;

    public CustomerController(CustomerService customerService, SubAccountDao subAccountDao) {
        this.customerService = customerService;
        this.subAccountDao = subAccountDao;
    }

//-------------------Create a Bank Account--------------------------------------------------------

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public ResponseEntity<List<Customer>> createUser(@RequestBody Customer customer) {
        System.out.println("Creating User ");
        HttpHeaders headers = new HttpHeaders();

        try {
            if (customer.getStatus() != false) {
                if (subAccountDao.existsSubAccountByAccountNumber(customer.getSubAccount())) {
                    System.out.println("Existe una subcuenta " + customer.getSubAccount());
                }
                System.out.println("LA CUENTA NO EXISTE");
            }

//            SubAccount subAccount = subAccountDao.findById(customer.getSubAccount().getId()).get();
//            subAccount.setBalance(customer.getBalance());
//            subAccountDao.save(subAccount);
//            customer.setSubAccount(subAccount);

            customerService.save(customer);
            List<Customer> customers = customerService.findAll();
            headers.set("accepted ok","cliente actualizado con exito");
            String message ="Cliente Actualizado con exito";
            return new ResponseEntity<List<Customer>>(customers, headers, HttpStatus.CREATED);
        }catch (JDBCConnectionException e){

            headers.set("error","Error al grabar datos en el servidor intente de nuevo");

            return new ResponseEntity<List<Customer>>(headers, HttpStatus.CREATED);

        }

    }


    //-------------------Create a Bank Account--------------------------------------------------------

    @RequestMapping(value = "/deleteCustomer", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteCustomer(@RequestBody Customer customer) {
        System.out.println("Creating User ");
        HttpHeaders headers = new HttpHeaders();

        try {
//            SubAccount subAccount = subAccountDao.findById(customer.getSubAccount().getId()).get();
//            subAccount.setBalance(customer.getBalance());
//            subAccountDao.save(subAccount);
//            customer.setSubAccount(subAccount);

            customerService.delete(customer);
            headers.set("accepted ok","bank account is ok");
            return new ResponseEntity<Customer>(customer, headers, HttpStatus.CREATED);
        }catch (JDBCConnectionException e){

            headers.set("error","Error al grabar datos en el servidor intente de nuevo");

            return new ResponseEntity<Customer>(headers, HttpStatus.CREATED);

        }

    }


    //-------------------Retrieve All Accounts Type--------------------------------------------------------

    @RequestMapping(value = "/getAllCustomers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getAllSubAcc() {
        HttpHeaders headers = new HttpHeaders();
        List<Customer> customers =  customerService.findAll();

        if (customers.isEmpty()) {
            headers.set("error", "no existen cuentas bancarias");
            return new ResponseEntity<List<Customer>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }


        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }
}
