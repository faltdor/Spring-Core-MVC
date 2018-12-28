package com.faltdor.springmvc.services;

import java.util.List;

public interface CrudService<T> {

    T getById(Long id);

    List<?> listAll();

    T saveOrUpdate(T domainObject);

    void delete(Long id);
}
