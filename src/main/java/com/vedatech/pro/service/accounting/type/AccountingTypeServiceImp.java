package com.vedatech.pro.service.accounting.type;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import com.vedatech.pro.model.accounting.AccountingType;
import com.vedatech.pro.repository.accounting.AccountingTypeDao;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountingTypeServiceImp implements AccountingTypeService {

    public final AccountingTypeDao accountingTypeDao;

    public AccountingTypeServiceImp(AccountingTypeDao accountingTypeDao) {
        this.accountingTypeDao = accountingTypeDao;
    }

    @Override
    public List<AccountingType> findAll() {
        return (List<AccountingType>) accountingTypeDao.findAll();
    }

    @Override
    public Optional<AccountingType> findById(Long aLong) {
        return accountingTypeDao.findById(aLong);
    }

    @Override
    public AccountingType save(AccountingType object) {
        return accountingTypeDao.save(object);
    }

    @Override
    public void delete(AccountingType object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<AccountingType> object) {
        accountingTypeDao.saveAll(object);
    }


    public List<AccountingType> findAccountingTypeByState(Boolean value) {
        return accountingTypeDao.findAccountingTypeByState(value);
    }
}
