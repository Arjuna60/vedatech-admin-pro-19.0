package com.vedatech.pro.service.customer;

import com.vedatech.pro.model.customer.Customer;
import com.vedatech.pro.repository.customer.CustomerDao;
import com.vedatech.pro.service.CfdiService;
import com.vedatech.pro.service.CfdiServiceImp;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {

    public final CustomerDao customerDao;

    public CustomerServiceImp(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerDao.findAll();
    }

    @Override
    public Optional<Customer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Customer save(Customer object) {
        return customerDao.save(object);
    }

    @Override
    public void delete(Customer object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<Customer> object) {
        customerDao.saveAll(object);
    }


}
