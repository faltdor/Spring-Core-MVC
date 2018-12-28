package com.faltdor.springmvc.services;

import com.faltdor.springmvc.domain.Customer;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<Long, Customer> customers;

    public CustomerServiceImpl() {
        loadCustomers();
    }

    private void loadCustomers() {
        this.customers = IntStream.range(1, 10)
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
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(this.customers.values());
    }

    @Override
    public Customer getCustomerById(long id) {
        return this.customers.get(id);
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        return Optional.ofNullable(customer)
                .filter(Objects::nonNull)
                .map(p -> {
                    if (p.getId() == null){
                        p.setId(getNextId());
                    }
                    customers.put(p.getId(), p);
                    return p;
                }).orElseThrow(ValidationException::new);
    }

    @Override
    public void deleteCustomerById(long id) {
        this.customers.remove(id);
    }

    private synchronized Long getNextId() {
        return Long.valueOf(this.customers.size() + 1);
    }
}