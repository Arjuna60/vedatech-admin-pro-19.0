package com.vedatech.pro.service.bank;

import com.vedatech.pro.model.bank.Bank;
import com.vedatech.pro.repository.bank.BankDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankServiceImp implements BankService {

    public final BankDao bankDao;

    public BankServiceImp(BankDao bankDao) {
        this.bankDao = bankDao;
    }

    @Override
    public List<Bank> findAll() {
        return (List<Bank>) bankDao.findAll();
    }

    @Override
    public Optional<Bank> findById(Long aLong) {
        return null;
    }

    @Override
    public Bank save(Bank object) {
        return bankDao.save(object);
    }

    @Override
    public void delete(Bank object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<Bank> object) {

    }


}
