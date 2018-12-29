package com.faltdor.springmvc.controllers;

import com.faltdor.springmvc.domain.Product;
import com.faltdor.springmvc.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProductControllerTest {

    @Mock
    private ProductService productService;
    private ProductController productController;
    MockMvc  mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void listProducts() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        Mockito.when(productService.listAll()).thenReturn((List)products);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products", hasSize(2)));

    }

    @Test
    public void getProductById() throws Exception {

        Mockito.when(productService.getById(1L)).thenReturn(new Product());
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));

    }

    @Test
    public void createProduct() throws Exception {
        Mockito.when(productService.getById(Mockito.anyLong())).thenReturn(new Product());
        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void createUpdateProduct() throws Exception {
        Product product = new Product();
        product.setId(Long.valueOf(2));
        String description = "Product Number " + 2;
        product.setDescription(description);
        BigDecimal price = new BigDecimal(2 * 9876);
        product.setPrice(price);
        String imageUrl = "http://product/" + 2;
        product.setImageUrl(imageUrl);
        Long id = 1L;
        product.setId(id);

        Mockito.when(productService.saveOrUpdate(Mockito.any(Product.class))).thenReturn(product);
        mockMvc.perform(post("/product")
                        .param("id", "1")
                        .param("description", description)
                        .param("price", price.toString())
                        .param("imageUrl", imageUrl) )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"))
                .andExpect(model().attribute("product", instanceOf(Product.class)))
                .andExpect(model().attribute("product", hasProperty("id", is(id))))
                .andExpect(model().attribute("product", hasProperty("description", is(description))))
                .andExpect(model().attribute("product", hasProperty("price", is(price))))
                .andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));

        //verify properties of bound object
        ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
        verify(productService).saveOrUpdate(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(description, boundProduct.getValue().getDescription());
        assertEquals(price, boundProduct.getValue().getPrice());
        assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
    }

    @Test
    public void editProduct() throws Exception {
        Mockito.when(productService.getById(1L)).thenReturn(new Product());
        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void deleteProduct() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"));
        verify(productService, times(1)).delete(id);
    }
}