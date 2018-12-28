package com.faltdor.springmvc.services;

import com.faltdor.springmvc.domain.Customer;
import com.faltdor.springmvc.domain.DomainObject;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CustomerServiceImpl extends  AbstractMapService implements CustomerService {

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Customer getById(Long id) {
        return (Customer)super.getById(id);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    protected void loadDomainObjects() {
        domainObjectMap = IntStream.range(1, 10)
                .mapToObj(index -> {
                    Customer customer = new Customer();
                    customer.setId(Long.valueOf(index));
                    customer.setAddressLineOne("Address Line " + index);
                    customer.setAddressLineTwo("Address two " + index);
                    customer.setCity("City" + index);
                    customer.setEmail("email " + index + "@email.com");
                    customer.setFirstName("First Name " + index);
                    customer.setLastName("Last Name " + index);
                    customer.setPhoneNumber(" 11111111111 " + index);
                    customer.setState("TO " + index);
                    customer.setZipCode(" MNJM" + index);
                    return customer;
                }).collect(Collectors.toMap(Customer::getId, customer -> customer));
    }

    @Override
    public Customer saveOrUpdate(Customer domainObject) {
        return (Customer)super.saveOrUpdate(domainObject);
    }
}