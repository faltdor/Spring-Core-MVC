package com.faltdor.springmvc.controllers;

import com.faltdor.springmvc.domain.Customer;
import com.faltdor.springmvc.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping({"/list", "/"})
    public String listCustomer(Model model){

        model.addAttribute("customers", this.customerService.listAll());

        return "customers";
    }

    @RequestMapping("/show/{id}")
    public String showCustomer(@PathVariable long id, Model model){
        model.addAttribute("customer", this.customerService.getById(id));
        return "customer";
    }

    @RequestMapping("/new")
    public String createCustomer(Model model){
        model.addAttribute("customer", new Customer());
        return "customerform";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createUpdateCustomer(Customer customer){

        this.customerService.saveOrUpdate(customer);
        return "redirect:/customer/list";
    }

    @RequestMapping("/edit/{id}")
    public String editCustomer(@PathVariable long id, Model model){
        model.addAttribute("customer", this.customerService.getById(id));
        return "customerform";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable long id){
        this.customerService.delete(id);
        return "redirect:/customer/list";
    }
}
