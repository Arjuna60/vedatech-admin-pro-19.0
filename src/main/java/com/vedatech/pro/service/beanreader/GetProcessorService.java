package com.vedatech.pro.service.beanreader;

import org.supercsv.cellprocessor.ift.CellProcessor;

public interface GetProcessorService {

    CellProcessor[] getSupplierProcessors();
    public CellProcessor[] getCustomerProcessors();
    public CellProcessor[] getBankProcessors();
    public CellProcessor[] getBankTransProcessors();
    public CellProcessor[] getAccountingTypeProcessors();


}
