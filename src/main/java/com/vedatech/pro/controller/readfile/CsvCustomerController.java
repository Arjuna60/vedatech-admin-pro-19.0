package com.vedatech.pro.controller.readfile;

import com.vedatech.pro.model.customer.Customer;
import com.vedatech.pro.model.supplier.Supplier;
import com.vedatech.pro.service.ReadFileService;
import com.vedatech.pro.service.customer.CustomerService;
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
public class CsvCustomerController {

    public final ReadFileService readFileService;
    public final CustomerService customerService;

    public CsvCustomerController(ReadFileService readFileService, CustomerService customerService) {
        this.readFileService = readFileService;
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customer-file", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileSupplierUpload(@RequestParam("file") MultipartFile file, String charset) throws Exception {

        System.out.println("SUPPLIER FILE");
        String message="";
        Customer obj = new Customer();
        List<Customer> objList = new ArrayList<>();
        try {
//            File fileCnv = convert(file);
            File fileCnv = readFileService.convert(file);

            objList = (List<Customer>) readFileService.readWithCsvBeanReader(Customer.class, fileCnv.getName());

            if ( objList.size()==0) {
                message = "El Formato del Archivo: " + file.getOriginalFilename() + " esta erroneo o no contiene datos, favor de verificar" + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            } else {
//                bankTransactionDtoService.saveAll(objList);
                customerService.saveAll(objList);
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

}
