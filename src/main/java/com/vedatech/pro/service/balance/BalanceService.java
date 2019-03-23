package com.vedatech.pro.service.balance;


import com.vedatech.pro.model.accounting.SubAccount;

import java.math.BigDecimal;

public interface BalanceService {

    BigDecimal calculateSubAccountBalance(Long id);
    Boolean existPolicies(SubAccount subAccount);
    public <T> void calculateBalanceSubAccount2(Class<T> tClass, Long id);
}
