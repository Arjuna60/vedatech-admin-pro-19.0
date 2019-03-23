package com.vedatech.pro.service.accounting.policy;

import com.vedatech.pro.repository.accounting.AccountPolicyDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.vedatech.pro.model.accounting.AccountPolicy;

@Service
public class AccountPolicyServiceImp implements AccountPolicyService {

    AccountPolicyDao accountPolicyDao;

    public AccountPolicyServiceImp(AccountPolicyDao accountPolicyDao) {
        this.accountPolicyDao = accountPolicyDao;
    }

    @Override
    public List<AccountPolicy> findAll() {
        return (List<AccountPolicy>) accountPolicyDao.findAll();
    }

    @Override
    public Optional<AccountPolicy> findById(Long aLong) {
        return accountPolicyDao.findById(aLong);
    }

    @Override
    public AccountPolicy save(AccountPolicy object) {
        return null;
    }

    @Override
    public void delete(AccountPolicy object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<AccountPolicy> object) {

    }
}
