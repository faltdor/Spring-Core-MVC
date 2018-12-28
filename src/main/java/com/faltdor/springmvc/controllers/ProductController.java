package com.faltdor.springmvc.controllers;

import com.faltdor.springmvc.domain.Product;
import com.faltdor.springmvc.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping("/product/new")
    public String createProduct(Model model){
        model.addAttribute("product", new Product());
        return "productform";
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public String createUpdateProduct(Product product){

        this.productService.saveOrUpdate(product);
        return "redirect:/products";
    }

    @RequestMapping("/product/edit/{id}")
    public String editProduct(@PathVariable long id, Model model){
        model.addAttribute("product", this.productService.getProductById(id));
        return "productform";
    }

}
