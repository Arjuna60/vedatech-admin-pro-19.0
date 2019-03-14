package com.vedatech.pro.controller.bank;


import com.vedatech.pro.model.accounting.AccountPolicy;
import com.vedatech.pro.model.accounting.AccountingType;
import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.bank.Bank;
import com.vedatech.pro.model.bank.BankTransaction;
import com.vedatech.pro.repository.accounting.AccountPolicyDao;
import com.vedatech.pro.repository.accounting.AccountingTypeDao;
import com.vedatech.pro.repository.accounting.SubAccountDao;
import com.vedatech.pro.repository.bank.BankDao;
import com.vedatech.pro.repository.bank.BankTransactionDao;
import com.vedatech.pro.service.bank.beanreader.BankTransactionDtoService;
import com.vedatech.pro.service.bank.transaction.BankTransactionService;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class BankTransactionController {

     HttpHeaders headers = new HttpHeaders();

     public final BankTransactionService bankTransactionService;
     public final BankTransactionDtoService bankTransactionDtoService;
     public final BankTransactionDao bankTransactionDao;
     public final BankDao bankDao;
     public final SubAccountDao subAccountDao;
     public final AccountPolicyDao accountPolicyDao;
     public final AccountingTypeDao accountingTypeDao;

    public BankTransactionController(BankTransactionService bankTransactionService, BankTransactionDtoService bankTransactionDtoService, BankTransactionDao bankTransactionDao, BankDao bankDao, SubAccountDao subAccountDao, AccountPolicyDao accountPolicyDao, AccountingTypeDao accountingTypeDao) {
        this.bankTransactionService = bankTransactionService;
        this.bankTransactionDtoService = bankTransactionDtoService;
        this.bankTransactionDao = bankTransactionDao;
        this.bankDao = bankDao;
        this.subAccountDao = subAccountDao;
        this.accountPolicyDao = accountPolicyDao;
        this.accountingTypeDao = accountingTypeDao;
    }

    @RequestMapping(value = "/getAllBankTransaction/", method = RequestMethod.GET)
    public ResponseEntity<List<BankTransaction>> getAllBankTransaction() {

        List<BankTransaction> bankTransactionList = bankTransactionService.findAll();
        if (bankTransactionList.isEmpty()) {
            headers.set("error", "no existen movimientos a la cuentas bancarias");
            return new ResponseEntity<List<BankTransaction>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<BankTransaction>>(bankTransactionList, HttpStatus.OK);

    }

    @RequestMapping(value = "/getBankAccount", method = RequestMethod.POST)
    public ResponseEntity<Bank> searchBank(@RequestBody Long bank) {
        System.out.println("Creating User ");

        Bank bankResult = bankDao.findBankByAccountNumber(bank);
        return new ResponseEntity<Bank>(bankResult, HttpStatus.OK);

    }

    //-------------------Create a Bank Account--------------------------------------------------------

    @RequestMapping(value = "/addTransaction/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody BankTransaction bankTransaction,
                                           @PathVariable (value = "id") Long id) {
        System.out.println("Creating User " + id);

        try {
            headers.set("success ", "se grabaron los datos con exito");
           BankTransaction saveBankTrans = bankTransactionDao.save(bankTransaction);
//            bankTransactionDtoService.deleteById(id);
            calcSumPolicy(saveBankTrans);
            return new ResponseEntity<String>(headers, HttpStatus.OK);

        }catch (JDBCConnectionException e){
            headers.set("error ", "se encontraron problemas en el servidor trate de nuevo");
            return new ResponseEntity<String>(headers, HttpStatus.CONFLICT);

        }


    }

    public void calcSumPolicy(BankTransaction bankTransaction) {

//               BankTransaction bankTrans = bankTransactionService.findById(bankTransaction.getId()).get();
               /*Calcula el balance total de la cuenta sumando y restando Depositos y retiros */
               BigDecimal ttlBalance = bankTransactionDao.getTotalBankTransBalance(bankTransaction.getBank().getId());
               System.out.println("Balance de transaccion " + ttlBalance);
               BigDecimal bankIniBalance = bankDao.getInitialBalance(bankTransaction.getBank().getId());
               System.out.println("Balance inicial del banco " + bankIniBalance);
//               bankTrans.getBank().setBalance(bankBalance + ttlBalance);
               Bank bank = bankDao.findBankById(bankTransaction.getBank().getId());
               bank.setBalanceToday(bankIniBalance.add( ttlBalance));
               bankDao.save(bank);
               SubAccount subAccount = subAccountDao.findById(bank.getSubAccount().getId()).get();

//               subAccount.setBalance( bankDao.sumBalanceBySubacc(subAccount.getId()));
               subAccount.setBalance(bank.getBalanceToday());
               subAccountDao.save(subAccount);
                calcBalanceAccountType(bankTransaction);
    }

    public void calcBalanceAccountType(BankTransaction bankTransaction) {

        SubAccount subAccount = new SubAccount();
        List<AccountPolicy> accountingTypeList = bankTransaction.getPoliza();

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
