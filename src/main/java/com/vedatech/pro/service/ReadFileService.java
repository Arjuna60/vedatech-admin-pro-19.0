package com.vedatech.pro.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ReadFileService {

     public  <T> List<T> readWithCsvBeanReader(Class<T> tClass, String file) throws IOException;
     File convert(MultipartFile file) throws IOException;
}
