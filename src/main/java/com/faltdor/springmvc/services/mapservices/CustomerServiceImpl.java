package com.faltdor.springmvc.services.mapservices;

import com.faltdor.springmvc.domain.Address;
import com.faltdor.springmvc.domain.Customer;
import com.faltdor.springmvc.domain.DomainObject;
import com.faltdor.springmvc.services.CustomerService;
import com.faltdor.springmvc.services.mapservices.AbstractMapService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Profile("map")
public class CustomerServiceImpl extends AbstractMapService implements CustomerService {

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
                    Address address = new Address();
                    address.setAddressLine1("Address Line " + index);
                    address.setAddressLine2("Address two " + index);
                    address.setCity("City" + index);
                    customer.setEmail("email " + index + "@email.com");
                    customer.setFirstName("First Name " + index);
                    customer.setLastName("Last Name " + index);
                    customer.setPhoneNumber(" 11111111111 " + index);
                    address.setState("TO " + index);
                    address.setZipCode(" MNJM" + index);
                    customer.setBillingAddress(address);
                    customer.setShippingAddress(address);
                    return customer;
                }).collect(Collectors.toMap(Customer::getId, customer -> customer));
    }

    @Override
    public Customer saveOrUpdate(Customer domainObject) {
        return (Customer)super.saveOrUpdate(domainObject);
    }
}