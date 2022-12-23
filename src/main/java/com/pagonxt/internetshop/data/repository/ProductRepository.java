package com.pagonxt.internetshop.data.repository;

import com.pagonxt.internetshop.data.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

}
