package com.faltdor.springmvc.services;

import com.faltdor.springmvc.domain.DomainObject;
import com.faltdor.springmvc.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Profile("map")
public class ProductServiceImpl extends AbstractMapService implements ProductService {


    @Override
    public Product getById(Long id) {
        return (Product)super.getById(id);
    }

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Product saveOrUpdate(Product product) {
        return (Product)super.saveOrUpdate(product);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    protected void loadDomainObjects() {
        this.domainObjectMap = IntStream.range(1, 10)
                .mapToObj(index -> {
                    Product product = new Product();
                    product.setId(Long.valueOf(index));
                    product.setDescription("Product Number " + index);
                    product.setPrice(new BigDecimal(index * 9876));
                    product.setImageUrl("http://product/" + index);
                    return product;
                }).collect(Collectors.toMap(Product::getId, product -> product));
    }
}


