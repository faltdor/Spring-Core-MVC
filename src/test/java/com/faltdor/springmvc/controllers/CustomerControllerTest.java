package com.faltdor.springmvc.controllers;

import com.faltdor.springmvc.domain.Customer;
import com.faltdor.springmvc.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void listCustomer() throws Exception {
        Mockito.when(customerService.listAll()).thenReturn((List)Arrays.asList(new Customer(), new Customer()));

        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers"))
                .andExpect(model().attribute("customers", hasSize(2)));
    }

    @Test
    public void showCustomer() throws Exception {

        Mockito.when(customerService.getById(Mockito.anyLong())).thenReturn(new Customer());

        mockMvc.perform(get("/customer/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));

    }

    @Test
    public void createCustomer() throws Exception {
        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void createUpdateCustomer() throws Exception {
        int index = 9999;
        Customer customer = new Customer();

        Long id = Long.valueOf(1);
        customer.setId(id);
        String addressLineOne = "Address Line " + index;
        customer.setAddressLineOne(addressLineOne);
        String addressLineTwo = "Address two " + index;
        customer.setAddressLineTwo(addressLineTwo);
        String city = "City" + index;
        customer.setCity(city);
        String email = "email " + index + "@email.com";
        customer.setEmail(email);
        String firstName = "First Name " + index;
        customer.setFirstName(firstName);
        String lastName = "Last Name " + index;
        customer.setLastName(lastName);
        String phoneNumber = "11111111111 " + index;
        customer.setPhoneNumber(phoneNumber);
        String state = "TO " + index;
        customer.setState(state);
        String zipCode = " MNJM" + index;
        customer.setZipCode(zipCode);

        Mockito.when(customerService.saveOrUpdate(Mockito.any(Customer.class))).thenReturn(customer);
        mockMvc.perform(post("/customer")
                .param("id", "1")
                .param("addressLineOne", addressLineOne)
                .param("addressLineTwo", addressLineTwo)
                .param("city", city)
                .param("email", email)
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("phoneNumber", phoneNumber)
                .param("state", state)
                .param("zipCode", zipCode))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andExpect(model().attribute("customer", hasProperty("id", is(id))))
                .andExpect(model().attribute("customer", hasProperty("addressLineOne", is(addressLineOne))))
                .andExpect(model().attribute("customer", hasProperty("addressLineTwo", is(addressLineTwo))))
                .andExpect(model().attribute("customer", hasProperty("email", is(email))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is(firstName))))
                .andExpect(model().attribute("customer", hasProperty("lastName", is(lastName))))
                .andExpect(model().attribute("customer", hasProperty("phoneNumber", is(phoneNumber))))
                .andExpect(model().attribute("customer", hasProperty("state", is(state))))
                .andExpect(model().attribute("customer", hasProperty("zipCode", is(zipCode))));

        //verify properties of bound object
        ArgumentCaptor<Customer> boundProduct = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdate(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(addressLineOne, boundProduct.getValue().getAddressLineOne());
        assertEquals(addressLineTwo, boundProduct.getValue().getAddressLineTwo());
        assertEquals(email, boundProduct.getValue().getEmail());
        assertEquals(firstName, boundProduct.getValue().getFirstName());
        assertEquals(lastName, boundProduct.getValue().getLastName());
        assertEquals(phoneNumber, boundProduct.getValue().getPhoneNumber());
        assertEquals(state, boundProduct.getValue().getState());
        assertEquals(zipCode, boundProduct.getValue().getZipCode());
    }

    @Test
    public void editCustomer() throws Exception {
        Mockito.when(customerService.getById(Mockito.anyLong())).thenReturn(new Customer());
        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void deleteCustomer() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));
        verify(customerService, times(1)).delete(id);
    }

}