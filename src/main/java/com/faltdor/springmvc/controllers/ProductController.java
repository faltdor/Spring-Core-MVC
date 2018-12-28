package com.faltdor.springmvc.controllers;

import com.faltdor.springmvc.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/products")
    public String listProducts(Model model){

        model.addAttribute("products", this.productService.getAllProducts());

        return "products";
    }

    @RequestMapping("/product/{id}")
    public String getProductById(@PathVariable long id, Model model){

        model.addAttribute("product", this.productService.getProductById(id));

        return "product";
    }
}
