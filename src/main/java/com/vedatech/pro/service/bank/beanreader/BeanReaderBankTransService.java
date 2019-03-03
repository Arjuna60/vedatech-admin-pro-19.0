package com.vedatech.pro.service.bank.beanreader;


import com.vedatech.pro.model.bank.BankTransactionDto;

import java.io.IOException;
import java.util.List;

public interface BeanReaderBankTransService {

    List<BankTransactionDto> readWithCsvBeanReaderBankTrans(BankTransactionDto obj, String file) throws IOException;

}
