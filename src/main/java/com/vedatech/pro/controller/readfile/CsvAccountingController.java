package com.vedatech.pro.controller.readfile;

import com.vedatech.pro.model.accounting.AccountingType;
import com.vedatech.pro.model.dto.AccountingTypeDto;
import com.vedatech.pro.service.ReadFileService;
import com.vedatech.pro.service.accounting.type.AccountingTypeServiceImp;
import com.vedatech.pro.service.beanreader.accounting.BeanReaderAccountingTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class CsvAccountingController {

    public final ReadFileService readFileService;
    public final BeanReaderAccountingTypeService beanReaderAccountingTypeService;
    public final AccountingTypeServiceImp accountingTypeServiceImp;

    public CsvAccountingController(ReadFileService readFileService, BeanReaderAccountingTypeService beanReaderAccountingTypeService, AccountingTypeServiceImp accountingTypeServiceImp) {
        this.readFileService = readFileService;
        this.beanReaderAccountingTypeService = beanReaderAccountingTypeService;
        this.accountingTypeServiceImp = accountingTypeServiceImp;
    }

    @RequestMapping(value = "/accounting-type-file2", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileAccountingTypeUpload(@RequestParam("file") MultipartFile file, String charset) throws Exception {

        //  System.out.println("File " + file);
        String message="";
        AccountingTypeDto obj = new AccountingTypeDto();
        try {

            List<AccountingTypeDto> objecList;
            List<AccountingType> accountingTypeList = new ArrayList<>();
            File fileCnv= readFileService.convert(file);
            System.out.println("File Cnv " + fileCnv.toString());
            System.out.println("--------------------------------");
            objecList =  beanReaderAccountingTypeService.readWithCsvBeanReaderAccountingType(obj, fileCnv.getName());

            if ( objecList.size()==0) {
                message = "El Formato del Archivo: " + file.getOriginalFilename() + " esta erroneo o no contiene datos, favor de verificar" + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            } else {
                System.out.println("process ok");
                for(AccountingTypeDto at: objecList){

                    AccountingType accountingType = new AccountingType();
                    accountingType.setName(at.getName());
                    accountingType.setAccount(at.getAccount());
                    accountingType.setBalance(at.getBalance());
                    accountingType.setState(false);
                    accountingTypeList.add(accountingType);
                    //  accountingTypeServiceImp.save(accountingType);
                }
                accountingTypeServiceImp.saveAll(accountingTypeList);
            }
            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);

        } catch (Exception e) {
            System.out.println("Error "+ e);
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


    @RequestMapping(value = "/accounting-type-file", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileAccountingTypeUpload2(@RequestParam("file") MultipartFile file, String charset) throws Exception {

        //  System.out.println("File " + file);
        String message="";
        AccountingTypeDto obj = new AccountingTypeDto();
        try {

            List<AccountingTypeDto> objecList;
            List<AccountingTypeDto> objecList2;
            List<AccountingType> accountingTypeList = new ArrayList<>();
            File fileCnv= readFileService.convert(file);
            System.out.println("File Cnv " + fileCnv.toString());
            System.out.println("--------------------------------");
//            objecList2 =  beanReaderAccountingTypeService.readWithCsvBeanReaderAccountingType(obj, fileCnv.getName());
            objecList = (List<AccountingTypeDto>) readFileService.readWithCsvBeanReader( AccountingTypeDto.class, fileCnv.getName());
            if ( objecList.size()==0 ) {
                message = "El Formato del Archivo: " + file.getOriginalFilename() + " esta erroneo o no contiene datos, favor de verificar" + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            } else {
                System.out.println("process ok");
                for(AccountingTypeDto at: objecList){

                    AccountingType accountingType = new AccountingType();
                    accountingType.setName(at.getName());
                    accountingType.setAccount(at.getAccount());
                    accountingType.setBalance(at.getBalance());
                    accountingType.setState(false);
                    accountingTypeList.add(accountingType);
                    //  accountingTypeServiceImp.save(accountingType);
                }
                accountingTypeServiceImp.saveAll(accountingTypeList);
            }
            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);

        } catch (Exception e) {
            System.out.println("Error "+ e);
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
}
