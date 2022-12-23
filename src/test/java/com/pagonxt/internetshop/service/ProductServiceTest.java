package com.pagonxt.internetshop.service;

import com.pagonxt.internetshop.configuration.TestConfigurations;
import com.pagonxt.internetshop.data.entity.Product;
import com.pagonxt.internetshop.data.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {TestConfigurations.class})
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void createProduct() {
        var product = this.productService.create("Product Name", 10.0F);
        var persistedProductOptional = this.productRepository.findById(product.getId());
        assertThat(persistedProductOptional.isPresent()).isTrue();
        Product persistedProduct = persistedProductOptional.get();

        assertThat(product).isEqualTo(persistedProduct);
    }
}
