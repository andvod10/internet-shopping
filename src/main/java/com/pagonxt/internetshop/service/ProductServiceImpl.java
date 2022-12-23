package com.pagonxt.internetshop.service;

import com.pagonxt.internetshop.data.entity.Product;
import com.pagonxt.internetshop.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product create(String name, float cost) {
        var product = Product.builder()
                .name(name)
                .cost(cost)
                .build();
        return this.productRepository.save(product);
    }
}
