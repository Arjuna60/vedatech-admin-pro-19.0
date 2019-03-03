package com.vedatech.pro.service.accounting.subacc;

import com.vedatech.pro.model.accounting.SubAccount;
import com.vedatech.pro.service.CrudServices;
import org.springframework.data.repository.CrudRepository;

public interface SubAccountService extends CrudServices<SubAccount, Long> {
}
