package com.vedatech.pro.controller.bank;

import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.bank.Bank;
import com.vedatech.pro.model.bank.BankTransaction;
import com.vedatech.pro.repository.accounting.SubAccountDao;
import com.vedatech.pro.repository.bank.BankDao;
import com.vedatech.pro.repository.bank.BankTransactionDao;
import com.vedatech.pro.service.accounting.subacc.SubAccountService;
import com.vedatech.pro.service.bank.BankService;
import com.vedatech.pro.service.bank.transaction.BankTransactionService;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    public  final SubAccountService subAccountService;
    public final SubAccountDao subAccountDao;
    public final BankService bankService;
    public final BankDao bankDao;
    public final BankTransactionService bankTransactionService;
    public final BankTransactionDao bankTransactionDao;

    public BankController(SubAccountService subAccountService, SubAccountDao subAccountDao, BankService bankService, BankDao bankDao, BankTransactionService bankTransactionService, BankTransactionDao bankTransactionDao) {
        this.subAccountService = subAccountService;
        this.subAccountDao = subAccountDao;
        this.bankService = bankService;
        this.bankDao = bankDao;
        this.bankTransactionService = bankTransactionService;
        this.bankTransactionDao = bankTransactionDao;
    }

    //-------------------Create a Bank Account--------------------------------------------------------

    @RequestMapping(value = "/addBankAccount/", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody Bank bank, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User ");


        SubAccount subAccount2 = subAccountDao.findById(bank.getSubAccount().getId()).get();
        System.out.println("Exist Bank? " + bankDao.existsBanksBySubAccount_AccountNumber(subAccount2.getAccountNumber()));

            /*Verifica si existe un numero de cuenta con el mismo numero*/
        if (bankDao.findBankByAccountNumber(bank.getAccountNumber()) != null ||  bankDao.existsBanksBySubAccount_AccountNumber(subAccount2.getAccountNumber())) {
            System.out.println("A User with name " + bank.getNameBank() + " " + bank.getAccountNumber() + " already exist");
            HttpHeaders headers = new HttpHeaders();
            headers.set("error ", "bank account or Number Account already exist please verfiy");
            String message ="La Cuenta Bancaria o La Subcuenta Contable ya existe";
            headers.set(message, "El numero de Cuenta o La Subcuenta Contable ya existe!");
            return new ResponseEntity<String>(message, headers, HttpStatus.CONFLICT);
        }

            /*Se van agregar los datos de la cuenta bancaria*/
        try {

            /* Se busca la subcuenta para agregar a la cuenta bancaria*/
            SubAccount subAccount = subAccountService.findById(bank.getSubAccount().getId()).get();
            bank.setBalanceToday(bank.getBalance());
            bankService.save(bank);
            subAccount.setBalance( bank.getBalance());
            subAccountService.save(subAccount);

        }catch (JDBCConnectionException e){

            HttpHeaders headers = new HttpHeaders();
            headers.set("error","Error al grabar datos en el servidor intente de nuevo");

            return new ResponseEntity<String>(headers, HttpStatus.CREATED);

        }


        Bank newBank = bankDao.findBankByAccountNumber(bank.getAccountNumber());

        HttpHeaders headers = new HttpHeaders();
        headers.set("accepted ok","bank account is ok");

        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Bank Account --------------------------------------------------------

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Bank> updateUser(@RequestBody Bank bank) {

        try {
            //   Bank currentBankAcc = bankService.findBankById(id);
            /* Se busca la subcuenta para agregar a la cuenta bancaria*/
            SubAccount subAccount = subAccountService.findById(bank.getSubAccount().getId()).get();
             if (bankTransactionDao.getTotalBankTransBalance(bank.getId()) != null ) {
                 bank.setBalanceToday(bank.getBalance().add( bankTransactionDao.getTotalBankTransBalance(bank.getId())));
             }
             bank.setBalanceToday(bank.getBalance());
            bankService.save(bank);
            subAccount.setBalance( bankDao.balanceBySubacc(bank.getSubAccount().getId()));
            subAccountService.save(subAccount);
            HttpHeaders headers = new HttpHeaders();
            headers.set("success", "the account is update success");
            return new ResponseEntity<Bank>(bank,headers, HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e);
            System.out.println("User with id " + " not found");
            HttpHeaders headers = new HttpHeaders();
            headers.set("error", "la cuenta no existe");
            return new ResponseEntity<Bank>(headers, HttpStatus.NOT_FOUND);
        }

    }


    //-------------------Retrieve All Bank Accounts--------------------------------------------------------

    @RequestMapping(value = "/getAllBankAccounts/", method = RequestMethod.GET)
    public ResponseEntity<List<Bank>> listAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        List<Bank> bankAccounts = bankService.findAll();
        List<String> stringList = new ArrayList<>();

        if (bankAccounts.isEmpty()) {
            headers.set("error", "no existen cuentas bancarias");
            return new ResponseEntity<List<Bank>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<List<Bank>>(bankAccounts, HttpStatus.OK);
    }


    //-------------------Retrieve All Bank Transactions--------------------------------------------------------

    @RequestMapping(value = "/getAllBankTransaction/", method = RequestMethod.GET)
    public ResponseEntity<List<BankTransaction>> listAllBankTransaction() {
        HttpHeaders headers = new HttpHeaders();
        List<BankTransaction> bankAccounts = bankTransactionService.findAll();
        List<String> stringList = new ArrayList<>();

        if (bankAccounts.isEmpty()) {
            headers.set("error", "no existen cuentas bancarias");
            return new ResponseEntity<List<BankTransaction>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<List<BankTransaction>>(bankAccounts, HttpStatus.OK);
    }


    //-------------------Create a Bank Account--------------------------------------------------------

    @RequestMapping(value = "/getBankAccount", method = RequestMethod.POST)
    public ResponseEntity<Bank> searchBank(@RequestBody Long bank) {
        System.out.println("Creating User ");

        Bank bankResult = bankDao.findBankByAccountNumber(bank);
        return new ResponseEntity<Bank>(bankResult, HttpStatus.OK);

    }


    }


