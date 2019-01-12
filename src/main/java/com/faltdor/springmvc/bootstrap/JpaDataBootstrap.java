package com.faltdor.springmvc.bootstrap;

import com.faltdor.springmvc.domain.Customer;
import com.faltdor.springmvc.domain.Product;
import com.faltdor.springmvc.services.CustomerService;
import com.faltdor.springmvc.services.ProductService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class JpaDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final ProductService productService;
    private final CustomerService customerService;

    public JpaDataBootstrap(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadProductsData();
    }

    private void loadProductsData() {
        List<Product> products = IntStream.range(1, 20)
                .mapToObj(index -> {
                    Product product = new Product();
                    product.setDescription("Product Number " + index);
                    product.setPrice(new BigDecimal(index * 9876));
                    product.setImageUrl("http://product/" + index);
                    return product;
                }).map(productService::saveOrUpdate)
                .collect(Collectors.toList());

    }

    private void loadCustomersData() {
        IntStream.range(1, 10)
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
                }).map(customerService::saveOrUpdate)
                .collect(Collectors.toMap(Customer::getId, customer -> customer));

    }
}
