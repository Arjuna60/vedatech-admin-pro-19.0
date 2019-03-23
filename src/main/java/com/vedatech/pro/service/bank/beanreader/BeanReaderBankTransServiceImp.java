package com.vedatech.pro.service.bank.beanreader;


import com.vedatech.pro.model.bank.BankTransactionDto;
import com.vedatech.pro.service.beanreader.BeanReaderService;
import com.vedatech.pro.service.beanreader.GetProcessorService;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.ICsvBeanReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeanReaderBankTransServiceImp implements BeanReaderBankTransService {

    public final BeanReaderService beanReaderService;
    public final GetProcessorService getProcessorService;

    public BeanReaderBankTransServiceImp(BeanReaderService beanReaderService, GetProcessorService getProcessorService) {
        this.beanReaderService = beanReaderService;
        this.getProcessorService = getProcessorService;
    }

    @Override
    public List<BankTransactionDto> readWithCsvBeanReaderBankTrans(BankTransactionDto obj, String file) throws IOException {

        ICsvBeanReader beanReader = null;
        List<BankTransactionDto> objects = new ArrayList<>();
        try {
            beanReader = beanReaderService.readWithCsvBeanReader(BankTransactionDto.class, file);
            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessorService.getBankTransProcessors();
            objects = beanReaderService.BeanReaderSave(BankTransactionDto.class, beanReader,processors,header);

        } finally {
            if (beanReader != null) {
                beanReader.close();
            }
            return objects;
        }

    }

}

