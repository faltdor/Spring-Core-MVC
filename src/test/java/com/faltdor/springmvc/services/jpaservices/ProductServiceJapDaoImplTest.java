package com.faltdor.springmvc.services.jpaservices;

import com.faltdor.springmvc.config.JpaIntegrationConfig;
import com.faltdor.springmvc.domain.Product;
import com.faltdor.springmvc.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("jpadao")
@SpringBootTest(classes = {JpaIntegrationConfig.class})
public class ProductServiceJapDaoImplTest {

    @Autowired
    ProductService productService;

    @Test
    public void testListMethod() throws Exception {

        List<Product> products = (List<Product>) productService.listAll();

        assert products.size() == 0;

    }
}
