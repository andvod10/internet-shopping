package com.pagonxt.internetshop.data.repository;

import com.pagonxt.internetshop.data.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {

}
