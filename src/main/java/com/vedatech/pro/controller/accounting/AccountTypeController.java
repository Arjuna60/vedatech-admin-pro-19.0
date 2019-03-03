package com.vedatech.pro.controller.accounting;


import com.vedatech.pro.model.accounting.AccountingType;
import com.vedatech.pro.repository.accounting.AccountingTypeDao;
import com.vedatech.pro.service.accounting.type.AccountingTypeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

}
