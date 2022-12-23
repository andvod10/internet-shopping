package com.pagonxt.internetshop.service;

import com.pagonxt.internetshop.data.entity.Product;

public interface ProductService {
    public Product create(String name, float cost);
}
