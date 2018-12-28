package com.faltdor.springmvc.services;

import com.faltdor.springmvc.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductServiceImpl implements ProductService {

    private Map<Long, Product> products;

    public ProductServiceImpl() {
        loadProducts();
    }

    private void loadProducts() {
        this.products = IntStream.range(1, 10)
                .mapToObj(index -> {
                  Product product = new Product();
                  product.setId(Long.valueOf(index));
                  product.setDescription("Product Number " + index);
                  product.setPrice(new BigDecimal(index * 9876));
                  product.setImageUrl("http://product/" + index);
                  return  product;
                }).collect(Collectors.toMap(Product::getId, product -> product));

    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(this.products.values());
    }

    @Override
    public Product getProductById(long id) {
        return this.products.get(id);
    }
}
