package com.vedatech.pro.controller.supplier;

import com.vedatech.pro.model.accounting.AccountPolicy;
import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.bank.Bank;
import com.vedatech.pro.model.bank.BankTransaction;
import com.vedatech.pro.model.supplier.Supplier;
import com.vedatech.pro.model.supplier.SupplierTransaction;
import com.vedatech.pro.repository.accounting.AccountPolicyDao;
import com.vedatech.pro.repository.accounting.AccountingTypeDao;
import com.vedatech.pro.repository.accounting.SubAccountDao;
import com.vedatech.pro.repository.supplier.SupplierDao;
import com.vedatech.pro.repository.supplier.SupplierTransactionDao;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierTransactionController {
    HttpHeaders headers = new HttpHeaders();

     public final SupplierTransactionDao supplierTransactionDao;
     public final SupplierDao supplierDao;
     public final SubAccountDao subAccountDao;
     public final AccountPolicyDao accountPolicyDao;

    public SupplierTransactionController(SupplierTransactionDao supplierTransactionDao, SupplierDao supplierDao, SubAccountDao subAccountDao, AccountPolicyDao accountPolicyDao) {
        this.supplierTransactionDao = supplierTransactionDao;
        this.supplierDao = supplierDao;
        this.subAccountDao = subAccountDao;
        this.accountPolicyDao = accountPolicyDao;
    }

    //------------------- Create a SupplierTransaction --------------------------------------------------------

    @RequestMapping(value = "/addTransaction/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> createTransaction(@RequestBody SupplierTransaction object,
                                             @PathVariable(value = "id") Long id) {
        System.out.println("Creating User " + id);

        try {
            headers.set("success ", "se grabaron los datos con exito");

            SupplierTransaction saveObject = supplierTransactionDao.save(object);
//            bankTransactionDtoService.deleteById(id);
            calcSumPolicy(object);
            return new ResponseEntity<String>(headers, HttpStatus.OK);

        }catch (JDBCConnectionException e){
            headers.set("error ", "se encontraron problemas en el servidor trate de nuevo");
            return new ResponseEntity<String>(headers, HttpStatus.CONFLICT);

        }


    }

    public void calcSumPolicy(SupplierTransaction object) {

//               BankTransaction bankTrans = bankTransactionService.findById(bankTransaction.getId()).get();
        BigDecimal ttlBalance = supplierTransactionDao.getTotalSupplierTransBalance(object.getSupplier().getId());
        System.out.println("Balance de transaccion " + ttlBalance);
        BigDecimal bankIniBalance = supplierDao.getInitialBalance(object.getSupplier().getId());
        System.out.println("Balance inicial del banco " + bankIniBalance);
//               bankTrans.getBank().setBalance(bankBalance + ttlBalance);
        Supplier supplier = supplierDao.findSupplierById(object.getSupplier().getId());
        supplier.setBalanceToday(bankIniBalance.add( ttlBalance));
        supplierDao.save(supplier);
        SubAccount subAccount = subAccountDao.findById(supplier.getSubAccount().getId()).get();

//               subAccount.setBalance( bankDao.sumBalanceBySubacc(subAccount.getId()));
        subAccount.setBalance(supplier.getBalanceToday());
        subAccountDao.save(subAccount);
        calcBalanceAccountType(object);
    }

    public void calcBalanceAccountType(SupplierTransaction supplierTransaction) {

        SubAccount subAccount = new SubAccount();
        List<AccountPolicy> accountingTypeList = supplierTransaction.getPoliza();

        for ( AccountPolicy sa: accountingTypeList ) {

            subAccount = subAccountDao.findById(sa.getSubAccount().getId()).get();
            System.out.println("SUBACCOUNT NAME "+ subAccount.getNameAccount());
            System.out.println("SUBACCOUNT BALANCE "+ subAccount.getBalance());
            accountPolicyDao.getAccountPolicyBalanceBySubAccount(subAccount.getId());
            subAccount.setBalance(subAccount.getBalance().add( accountPolicyDao.getAccountPolicyBalanceBySubAccount(subAccount.getId())));
            subAccountDao.save(subAccount);
        }
    }


}
