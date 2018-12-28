package com.faltdor.springmvc.services;

import com.faltdor.springmvc.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product>  getAllProducts();

    Product getProductById(long id);

    Product saveOrUpdate(Product product);

    void deleteProductById(long id);
}
