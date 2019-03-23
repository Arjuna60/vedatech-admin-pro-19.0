package com.vedatech.pro.service.bank.transaction;

import com.vedatech.pro.model.bank.BankTransaction;
import com.vedatech.pro.repository.bank.BankTransactionDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankTransactionServiceImp implements BankTransactionService {

    public final BankTransactionDao bankTransactionDao;

    public BankTransactionServiceImp(BankTransactionDao bankTransactionDao) {
        this.bankTransactionDao = bankTransactionDao;
    }

    @Override
    public List<BankTransaction> findAll() {
        return (List<BankTransaction>) bankTransactionDao.findAll();
    }

    @Override
    public Optional<BankTransaction> findById(Long aLong) {
        return bankTransactionDao.findById(aLong);
    }

    @Override
    public BankTransaction save(BankTransaction object) {
        return bankTransactionDao.save(object);
    }

    @Override
    public void delete(BankTransaction object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<BankTransaction> object) {

    }
}
