package com.vedatech.pro.controller.accounting;


import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.repository.accounting.SubAccountDao;
import com.vedatech.pro.service.accounting.subacc.RepositorySubAccount;
import com.vedatech.pro.service.accounting.subacc.SubAccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/account")
public class SubAccountController {

    public final SubAccountService subAccountService;
    public final SubAccountDao subAccountDao;

    public SubAccountController(SubAccountService subAccountService, SubAccountDao subAccountDao) {
        this.subAccountService = subAccountService;
        this.subAccountDao = subAccountDao;
    }

//-------------------Create a Bank Account--------------------------------------------------------

    @RequestMapping(value = "/addSubAccount/", method = RequestMethod.POST)
    public ResponseEntity<SubAccount> createUser(@RequestBody SubAccount subAccount, UriComponentsBuilder ucBuilder) {

        System.out.println("SUBACCOUNT " + subAccount.toString());
        //Sy
        // stem.out.println("Creating User " + subAccount.getNameAccount() );

/*

        if (bankDao.findBankByAccountNumber(bank.getAccountNumber()) != null) {
            System.out.println("A User with name " + bank.getNameBank() + " " + bank.getAccountNumber() + " already exist");
            HttpHeaders headers = new HttpHeaders();
            headers.set("error ", "bank account already exist");
            return new ResponseEntity<Bank>(headers, HttpStatus.CONFLICT);
        }
*/


        subAccountDao.save(subAccount);
     //  SubAccount newsubAccount = subAccountService.findBankByAccountNumber(bank.getAccountNumber());

        HttpHeaders headers = new HttpHeaders();
        headers.set("accepted ok","bank account is ok");

        return new ResponseEntity<SubAccount>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Sub-Account --------------------------------------------------------

    @RequestMapping(value = "/updateSubAccount", method = RequestMethod.PUT)
    public ResponseEntity<SubAccount> updateUser(@RequestBody SubAccount subAccount) {

        try {
            //   Bank currentBankAcc = bankService.findBankById(id);
//             SubAccount subAccountOrigin = subAccountService.findById(subAccount.g);

            subAccountService.save(subAccount);
            HttpHeaders headers = new HttpHeaders();
            headers.set("success", "the account is update success");
            return new ResponseEntity<SubAccount>(subAccount,headers, HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e);
           // System.out.println("User with id " + id + " not found");
            HttpHeaders headers = new HttpHeaders();
            headers.set("error", "la cuenta no existe");
            return new ResponseEntity<SubAccount>(headers, HttpStatus.NOT_FOUND);
        }

    }


    //------------------- Read by Id Sub-Account --------------------------------------------------------

    @RequestMapping(value = "findById/{id}", method = RequestMethod.GET)
    public ResponseEntity<SubAccount> searchSubAccById(@PathVariable("id") long id) {
        System.out.println("Updating User " + id);

        try {
            //   Bank currentBankAcc = bankService.findBankById(id);
           SubAccount subAccount = subAccountService.findById(id).get();
            HttpHeaders headers = new HttpHeaders();
            headers.set("success", "the account is update success");
            return new ResponseEntity<SubAccount>(subAccount, headers, HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e);
            System.out.println("User with id " + id + " not found");
            HttpHeaders headers = new HttpHeaders();
            headers.set("error", "la cuenta no existe");
            return new ResponseEntity<SubAccount>(headers, HttpStatus.NOT_FOUND);
        }

    }





    //-------------------Retrieve All Sub-Accounts--------------------------------------------------------

    @RequestMapping(value = "/getAllSubAccounts/", method = RequestMethod.GET)
    public ResponseEntity<List<SubAccount>> getAllSubAcc() {
        HttpHeaders headers = new HttpHeaders();
        List<SubAccount> accountNames = (List<SubAccount>) subAccountService.findAll();

        if (accountNames.isEmpty()) {
            headers.set("error", "no existen cuentas bancarias");
            return new ResponseEntity<List<SubAccount>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }


        return new ResponseEntity<List<SubAccount>>(accountNames, HttpStatus.OK);
    }


    //------------------- Read by Id Sub-Account --------------------------------------------------------

    @RequestMapping(value = "deleteById/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSubAccountById(@PathVariable("id") long id) {
        System.out.println("Updating User " + id);

        try {
            //   Bank currentBankAcc = bankService.findBankById(id);
            subAccountService.deleteById(id);
            HttpHeaders headers = new HttpHeaders();
            headers.set("success", "the account is update success");
            return new ResponseEntity<String>( headers, HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e);
            System.out.println("User with id " + id + " not found");
            HttpHeaders headers = new HttpHeaders();
            headers.set("error", "la cuenta no existe");
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }

    }


    @RequestMapping(value = "/getSubAccounts/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<SubAccount>> getSubAcc(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();

        List<SubAccount> subAccounts = subAccountDao.findAllByAccountType_Id(id);

        for (SubAccount s : subAccounts) {
            System.out.println("Sub Account Name " + s.getId());
        }

        return new ResponseEntity<List<SubAccount>>(subAccounts, HttpStatus.OK);
    }


}
