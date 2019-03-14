package com.vedatech.pro.controller.supplier;


import com.vedatech.pro.model.accounting.AccountPolicy;
import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.supplier.Supplier;
import com.vedatech.pro.repository.accounting.AccountPolicyDao;
import com.vedatech.pro.repository.accounting.SubAccountDao;
import com.vedatech.pro.repository.supplier.SupplierDao;
import com.vedatech.pro.service.balance.BalanceService;
import com.vedatech.pro.service.supplier.SupplierService;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/supplier")
public class SupplierController {

    public final SupplierService supplierService;
    public final SupplierDao supplierDao;
    public final SubAccountDao subAccountDao;
    public final AccountPolicyDao accountPolicyDao;
    public final BalanceService balanceService;

    public SupplierController(SupplierService supplierService, SupplierDao supplierDao, SubAccountDao subAccountDao, AccountPolicyDao accountPolicyDao, BalanceService balanceService) {
        this.supplierService = supplierService;
        this.supplierDao = supplierDao;
        this.subAccountDao = subAccountDao;
        this.accountPolicyDao = accountPolicyDao;
        this.balanceService = balanceService;
    }

    //-------------------Create a Bank Account--------------------------------------------------------

    @RequestMapping(value = "/addSupplier/", method = RequestMethod.POST)
    public ResponseEntity<Supplier> createUser(@RequestBody Supplier supplier) {
        System.out.println("Creating User ");
        HttpHeaders headers = new HttpHeaders();
        /*Verifica si existe un numero de cuenta con el mismo numero*/

//        if (bankDao.findBankByAccountNumber(bank.getAccountNumber()) != null ||  bankDao.existsBanksBySubAccount_AccountNumber(subAccount2.getAccountNumber())) {
//            System.out.println("A User with name " + bank.getNameBank() + " " + bank.getAccountNumber() + " already exist");
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("error ", "bank account or Number Account already exist please verfiy");
//            return new ResponseEntity<Bank>(headers, HttpStatus.CONFLICT);
//        }
        SubAccount subAccount2 = subAccountDao.findById(supplier.getSubAccount().getId()).get();

        if ( supplierDao.existsSuppliersBySubAccount_AccountNumber(subAccount2.getAccountNumber())) {
            System.out.println("A Supplier with name " + supplier.getDisplayName() + " " + subAccount2.getAccountNumber() + " already exist");
            headers.set("error ", "supplier account or Number Account already exist please verfiy");
            return new ResponseEntity<Supplier>(headers, HttpStatus.CONFLICT);
        }

         try {

           supplier.setBalanceToday( supplier.getBalance());
           Supplier newSupplier = supplierService.save(supplier);
           subAccountDao.save(subAccount2);
           headers.set("accepted ok","supplier account is ok");
           return new ResponseEntity<Supplier>(supplier, headers, HttpStatus.CREATED);

         }catch (JDBCConnectionException e){

            headers.set("error","Error al grabar datos en el servidor intente de nuevo");

            return new ResponseEntity<Supplier>(headers, HttpStatus.CREATED);

        }

    }



    //-------------------Retrieve All Accounts Type--------------------------------------------------------

    @RequestMapping(value = "/getAllSuppliers", method = RequestMethod.GET)
    public ResponseEntity<List<Supplier>> getAllSubAcc() {
        HttpHeaders headers = new HttpHeaders();
        List<Supplier> suppliers = (List<Supplier>) supplierService.findAll();

        if (suppliers.isEmpty()) {
            headers.set("error", "no existen cuentas bancarias");
            return new ResponseEntity<List<Supplier>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }


        return new ResponseEntity<List<Supplier>>(suppliers, HttpStatus.OK);
    }


    //------------------- Update a Bank Account --------------------------------------------------------

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Supplier> updateUser(@RequestBody Supplier supplier) {


        BigDecimal accountingPolicyBalance = BigDecimal.valueOf(0.00);
        try {
            //   Bank currentBankAcc = bankService.findBankById(id);
            /* Se busca la subcuenta para agregar a la cuenta bancaria*/

            if (supplier.getSubAccount().getId() != null) {
                SubAccount subAccount = subAccountDao.findById(supplier.getSubAccount().getId()).get();
                System.out.println("EL SUPPLIER TIENE UNA SUBCUENTA => " + subAccount.getNameAccount());
                if( accountPolicyDao.existsAccountPoliciesBySubAccountId(supplier.getSubAccount().getId()) ) {
                    accountingPolicyBalance = accountPolicyDao.getAccountPolicyBalanceBySubAccount(supplier.getSubAccount().getId());
                    subAccount.setBalance( supplier.getSubAccount().getBalance().add(accountingPolicyBalance));
                    supplier.setBalanceToday(subAccount.getBalance());
                } else {
                    subAccount.setBalance(supplier.getBalance());
                    supplier.setBalanceToday(supplier.getBalance());

                }

            }

            Supplier suppliernew = supplierDao.save(supplier);
            HttpHeaders headers = new HttpHeaders();
            headers.set("success", "the account is update success");
            return new ResponseEntity<Supplier>(suppliernew, headers, HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e);
            System.out.println("User with id " + " not found");
            HttpHeaders headers = new HttpHeaders();
            headers.set("error", "la cuenta no existe");
            return new ResponseEntity<Supplier>(headers, HttpStatus.NOT_FOUND);
        }

    }


}
