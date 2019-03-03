package com.vedatech.pro.controller.bank;


import com.vedatech.pro.model.bank.BankTransactionDto;
import com.vedatech.pro.service.bank.beanreader.BankTransactionDtoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dto")
public class BankTransactionDtoController {
    HttpHeaders headers = new HttpHeaders();

    BankTransactionDtoService bankTransactionDtoService;

    public BankTransactionDtoController(BankTransactionDtoService bankTransactionDtoService) {
        this.bankTransactionDtoService = bankTransactionDtoService;
    }

    @RequestMapping(value = "/getAllBankTransaction/", method = RequestMethod.GET)
    public ResponseEntity<List<BankTransactionDto>> getAllBankTransaction() {

        List<BankTransactionDto> bankTransactionDtoList = bankTransactionDtoService.findAll();
        if (bankTransactionDtoList.isEmpty()) {
            headers.set("error", "no existen movimientos de cuentas bancarias");
            return new ResponseEntity<List<BankTransactionDto>>(headers, HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<BankTransactionDto>>(bankTransactionDtoList, HttpStatus.OK);

    }
}
