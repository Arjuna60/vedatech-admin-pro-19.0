package com.vedatech.pro.service.balance;

import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.model.supplier.Supplier;
import com.vedatech.pro.repository.accounting.AccountPolicyDao;
import com.vedatech.pro.repository.accounting.SubAccountDao;
import com.vedatech.pro.repository.supplier.SupplierDao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceServiceImp implements BalanceService {

    public final AccountPolicyDao accountPolicyDao;
    public final SubAccountDao subAccountDao;
    public final SupplierDao supplierDao;

    public BalanceServiceImp(AccountPolicyDao accountPolicyDao, SubAccountDao subAccountDao, SupplierDao supplierDao) {
        this.accountPolicyDao = accountPolicyDao;
        this.subAccountDao = subAccountDao;
        this.supplierDao = supplierDao;
    }

    @Override
    public BigDecimal calculateSubAccountBalance(Long id) {
        return subAccountDao.getSubAccountBalance(id);
    }

    @Override
    public Boolean existPolicies(SubAccount subAccount) {
        return   accountPolicyDao.existsAccountPoliciesBySubAccountId(subAccount.getId());
    }

    @Override
    public <T> void calculateBalanceSubAccount2(Class<T> tClass, Long id) {

    }


}
