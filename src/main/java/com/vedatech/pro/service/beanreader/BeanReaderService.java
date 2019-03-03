package com.vedatech.pro.service.beanreader;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.ICsvBeanReader;

import java.io.IOException;
import java.util.List;

public interface BeanReaderService {

    <T> ICsvBeanReader readWithCsvBeanReader(Class<T> tClass, String file) throws IOException;
    <T> List<T> BeanReaderSave(Class<T> tClass, ICsvBeanReader beanReader, CellProcessor[] processors, String[] header) throws IOException;


}
