package com.vedatech.pro.controller.accounting;


import com.vedatech.pro.model.accounting.AccountingType;
import com.vedatech.pro.repository.accounting.AccountingTypeDao;
import com.vedatech.pro.service.accounting.type.AccountingTypeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/account")
public class AccountTypeController {

        private final AccountingTypeService accountingTypeService;
        private final AccountingTypeDao accountingTypeDao;

    public AccountTypeController(AccountingTypeService accountingTypeService, AccountingTypeDao accountingTypeDao) {
        this.accountingTypeService = accountingTypeService;
        this.accountingTypeDao = accountingTypeDao;
    }

//-------------------Retrieve All Accounts Type--------------------------------------------------------

    @RequestMapping(value = "/getAllAccountsType/", method = RequestMethod.GET)
    public ResponseEntity<List<AccountingType>> getAllSubAcc() {
        HttpHeaders headers = new HttpHeaders();
        List<AccountingType> accountNames = (List<AccountingType>) accountingTypeService.findAll();

        if (accountNames.isEmpty()) {
            headers.set("error", "no existen cuentas bancarias");
            return new ResponseEntity<List<AccountingType>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }


        return new ResponseEntity<List<AccountingType>>(accountNames, HttpStatus.OK);
    }


    //-------------------Retrieve Accounts Type by Id--------------------------------------------------------

    @RequestMapping(value = "/getAccountTypeById/{id}", method = RequestMethod.GET)
    public ResponseEntity<AccountingType> getAccountType(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        AccountingType accountingType = accountingTypeDao.findById(id).get();

        if (accountingType == null) {
            headers.set("error", "no existen cuentas bancarias");
            return new ResponseEntity<AccountingType>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }


        return new ResponseEntity<AccountingType>(accountingType, HttpStatus.OK);
    }




}
