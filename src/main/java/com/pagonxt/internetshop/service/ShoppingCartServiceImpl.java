package com.pagonxt.internetshop.service;

import com.pagonxt.internetshop.data.entity.CreditCardType;
import com.pagonxt.internetshop.data.entity.PaymentDetails;
import com.pagonxt.internetshop.data.entity.Product;
import com.pagonxt.internetshop.data.entity.ShoppingCart;
import com.pagonxt.internetshop.data.mapper.DiscountMapper;
import com.pagonxt.internetshop.data.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final DiscountMapper discountMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, DiscountMapper discountMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.discountMapper = discountMapper;
    }

    @Override
    @Transactional
    public ShoppingCart create(PaymentDetails paymentDetails) {
        var shoppingCart = ShoppingCart.builder()
                .paymentDetails(paymentDetails)
                .build();
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public void addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.addProduct(product);
        this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public void removeProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.removeProduct(product);
        this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public float calculateTotalPrice(ShoppingCart shoppingCart) {
        var creditCardType = shoppingCart.getPaymentDetails().getCreditCardType();
        return calculateDiscount(shoppingCart.getProducts().stream()
                .map(Product::getCost)
                .reduce(0.0F, Float::sum), creditCardType);
    }

    private float calculateDiscount(Float productSum, CreditCardType creditCardType) {
        return productSum - productSum * this.discountMapper.getDiscount(creditCardType) * 0.01F;
    }
}
