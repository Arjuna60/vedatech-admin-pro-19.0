package com.vedatech.pro.controller.readfile;

import com.vedatech.pro.model.bank.BankTransaction;
import com.vedatech.pro.model.bank.BankTransactionDto;
import com.vedatech.pro.service.ReadFileService;
import com.vedatech.pro.service.bank.beanreader.BankTransactionDtoService;
import com.vedatech.pro.service.bank.beanreader.BeanReaderBankTransService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class CsvBankTransController {

    public final ReadFileService readFileService;
    public final BankTransactionDtoService bankTransactionDtoService;
    public final BeanReaderBankTransService beanReaderBankTransService;

    public CsvBankTransController(ReadFileService readFileService, BankTransactionDtoService bankTransactionDtoService, BeanReaderBankTransService beanReaderBankTransService) {
        this.readFileService = readFileService;
        this.bankTransactionDtoService = bankTransactionDtoService;
        this.beanReaderBankTransService = beanReaderBankTransService;
    }

    @RequestMapping(value = "/bank-transaction-file", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileBankTransUpload(@RequestParam("file") MultipartFile file, String charset) throws Exception {

        System.out.println("BANK-TRANSACTION-FILE");
        String message="";
        BankTransactionDto obj = new BankTransactionDto();
        List<BankTransactionDto> objList = new ArrayList<>();
        try {
//            File fileCnv = convert(file);
            File fileCnv = readFileService.convert(file);

            objList = (List<BankTransactionDto>) readFileService.readWithCsvBeanReader(BankTransactionDto.class, fileCnv.getName());

            if ( objList.size()==0) {
                message = "El Formato del Archivo: " + file.getOriginalFilename() + " esta erroneo o no contiene datos, favor de verificar" + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            } else {
                bankTransactionDtoService.saveAll(objList);
            }

            //   bankTransactionDao.saveAll(objecList);
            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);

        } catch (Exception e) {
            System.out.println("Error "+ e);
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


//    public File convert(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        convFile.createNewFile();
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }



}

