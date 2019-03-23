package com.vedatech.pro.service;


import java.util.List;
import java.util.Optional;

public interface CrudServices<T, ID> {

    List<T> findAll();

   Optional<T> findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);

    void saveAll(List<T> object);
}
