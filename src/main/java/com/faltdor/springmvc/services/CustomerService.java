package com.faltdor.springmvc.services;

import com.faltdor.springmvc.domain.Customer;
import com.faltdor.springmvc.domain.Product;

import java.util.List;

public interface CustomerService {

    List<Customer>  getAllCustomers();

    Customer getCustomerById(long id);

    Customer saveOrUpdate(Customer customer);

    void deleteCustomerById(long id);
}
