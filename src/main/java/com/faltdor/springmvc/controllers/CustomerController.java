package com.faltdor.springmvc.controllers;

import com.faltdor.springmvc.domain.Customer;
import com.faltdor.springmvc.services.CustomerService;
import com.faltdor.springmvc.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/customers")
    public String listCustomer(Model model){

        model.addAttribute("customers", this.customerService.getAllCustomers());

        return "customers";
    }

    @RequestMapping("/customer/{id}")
    public String getCustomerById(@PathVariable long id, Model model){

        model.addAttribute("customer", this.customerService.getCustomerById(id));

        return "customer";
    }

    @RequestMapping("/customer/new")
    public String createCustomer(Model model){
        model.addAttribute("customer", new Customer());
        return "customerform";
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public String createUpdateCustomer(Customer customer){

        this.customerService.saveOrUpdate(customer);
        return "redirect:/customers";
    }

    @RequestMapping("/customer/edit/{id}")
    public String editCustomer(@PathVariable long id, Model model){
        model.addAttribute("customer", this.customerService.getCustomerById(id));
        return "customerform";
    }

    @RequestMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable long id){
        this.customerService.deleteCustomerById(id);
        return "redirect:/customers";
    }
}
