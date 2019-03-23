package com.vedatech.pro.service.beanreader;

import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeanReaderServiceImp implements BeanReaderService {


    @Override
    public ICsvBeanReader readWithCsvBeanReader(Class aClass, String file) throws IOException {

        List<Class> objects = new ArrayList<>();
        ICsvBeanReader beanReader = null;
        beanReader = new CsvBeanReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
      //  final String[] header = beanReader.getHeader(true);

        return beanReader;
    }


    @Override
    public <T> List<T> BeanReaderSave(Class<T> tClass, ICsvBeanReader beanReader, CellProcessor[] processors, String[] header) throws IOException {

        List<T> objects = new ArrayList<>();
        T object;

        while ((object = beanReader.read(tClass, header, processors)) != null) {
            // System.out.println("BEAN READER LINE " + beanReader.getLineNumber() + " ROW " + beanReader.getRowNumber());
//            System.out.println(String.format("lineNo=%s, rowNo=%s, customer=%s", beanReader.getLineNumber(),
//                   beanReader.getRowNumber(), object));
            objects.add(object);
        }

        return objects;
    }
}
