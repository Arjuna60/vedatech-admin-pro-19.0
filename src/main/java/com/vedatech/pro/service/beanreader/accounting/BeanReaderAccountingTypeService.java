package com.vedatech.pro.service.beanreader.accounting;


import com.vedatech.pro.model.dto.AccountingTypeDto;

import java.io.IOException;
import java.util.List;

public interface BeanReaderAccountingTypeService {

    List<AccountingTypeDto> readWithCsvBeanReaderAccountingType(AccountingTypeDto obj, String file) throws IOException;

}
