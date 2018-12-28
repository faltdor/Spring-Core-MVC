package com.faltdor.springmvc.services;

import com.faltdor.springmvc.domain.Product;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.*;
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

    @Override
    public Product saveOrUpdate(Product product) {
        return Optional.ofNullable(product)
                .filter(Objects::nonNull)
                .map(p -> {
                    if (p.getId() == null){
                        p.setId(getNextId());
                    }
                    products.put(p.getId(), p);
                    return p;
                }).orElseThrow(ValidationException::new);
    }

    private synchronized Long getNextId() {
        return Long.valueOf(this.products.size() + 1);
    }
}
