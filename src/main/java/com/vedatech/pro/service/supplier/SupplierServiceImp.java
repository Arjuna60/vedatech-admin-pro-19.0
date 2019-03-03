package com.vedatech.pro.service.supplier;

import com.vedatech.pro.model.supplier.Supplier;
import com.vedatech.pro.repository.supplier.SupplierDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImp implements SupplierService {

    public final SupplierDao supplierDao;

    public SupplierServiceImp(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    @Override
    public List<Supplier> findAll() {
        return (List<Supplier>) supplierDao.findAll();
    }

    @Override
    public Optional<Supplier> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Supplier save(Supplier object) {
        return supplierDao.save(object);
    }

    @Override
    public void delete(Supplier object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<Supplier> object) {
        supplierDao.saveAll(object);
    }
}
