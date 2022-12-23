package com.pagonxt.internetshop.service;

import com.pagonxt.internetshop.data.entity.PaymentDetails;
import com.pagonxt.internetshop.data.entity.Product;
import com.pagonxt.internetshop.data.entity.ShoppingCart;

public interface ShoppingCartService {
    public ShoppingCart create(PaymentDetails paymentDetails);
    public void addProduct(ShoppingCart shoppingCart, Product product);
    public void removeProduct(ShoppingCart shoppingCart, Product product);
    public float calculateTotalPrice(ShoppingCart shoppingCart);
}
