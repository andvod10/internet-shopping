package com.pagonxt.internetshop.service;

import com.pagonxt.internetshop.configuration.TestConfigurations;
import com.pagonxt.internetshop.data.entity.CreditCardType;
import com.pagonxt.internetshop.data.entity.PaymentDetails;
import com.pagonxt.internetshop.data.entity.Product;
import com.pagonxt.internetshop.data.entity.ShoppingCart;
import com.pagonxt.internetshop.data.mapper.DiscountMapper;
import com.pagonxt.internetshop.data.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {TestConfigurations.class})
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "internetshop.card.type.discount.gold=22",
        "internetshop.card.type.discount.silver=11",
        "internetshop.card.type.discount.normal=0"
})
@Transactional
public class ShoppingCartServiceTest {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private PaymentDetailsService paymentDetailsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private ShoppingCart shoppingCart;
    private PaymentDetails paymentDetails;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setup() {
        paymentDetails = this.paymentDetailsService.create(CreditCardType.GOLD);
        shoppingCart = this.shoppingCartService.create(paymentDetails);
        product1 = this.productService.create("product1", 10.0F);
        product2 = this.productService.create("product2", 5.5F);
    }

    @Test
    void createShoppingCart() {
        var persistedShoppingCartOptional = this.shoppingCartRepository.findById(shoppingCart.getId());
        assertThat(persistedShoppingCartOptional.isPresent()).isTrue();
        ShoppingCart persistedShoppingCart = persistedShoppingCartOptional.get();

        assertThat(shoppingCart).isEqualTo(persistedShoppingCart);
    }

    @Test
    void addProductToShoppingCart() {
        var persistedShoppingCartOptional = this.shoppingCartRepository.findById(shoppingCart.getId());
        assertThat(persistedShoppingCartOptional.isPresent()).isTrue();
        ShoppingCart persistedShoppingCart = persistedShoppingCartOptional.get();
        assertThat(persistedShoppingCart.getProducts()).isEmpty();

        this.shoppingCartService.addProduct(shoppingCart, product1);

        var persistedShoppingCartOptionalUpdated = this.shoppingCartRepository.findById(shoppingCart.getId());
        assertThat(persistedShoppingCartOptionalUpdated.isPresent()).isTrue();
        ShoppingCart persistedShoppingCartUpdated = persistedShoppingCartOptionalUpdated.get();
        assertThat(persistedShoppingCartUpdated.getProducts().size()).isEqualTo(1);
    }

    @Test
    void removeProductFromShoppingCart() {
        this.shoppingCartService.addProduct(shoppingCart, product1);
        this.shoppingCartService.addProduct(shoppingCart, product2);

        var persistedShoppingCartOptional = this.shoppingCartRepository.findById(shoppingCart.getId());
        assertThat(persistedShoppingCartOptional.isPresent()).isTrue();
        ShoppingCart persistedShoppingCart = persistedShoppingCartOptional.get();
        assertThat(persistedShoppingCart.getProducts().size()).isEqualTo(2);

        this.shoppingCartService.removeProduct(shoppingCart, product1);

        var persistedShoppingCartOptionalUpdated = this.shoppingCartRepository.findById(shoppingCart.getId());
        assertThat(persistedShoppingCartOptionalUpdated.isPresent()).isTrue();
        ShoppingCart persistedShoppingCartUpdated = persistedShoppingCartOptionalUpdated.get();
        assertThat(persistedShoppingCartUpdated.getProducts().size()).isEqualTo(1);
        assertThat(persistedShoppingCartUpdated.getProducts()).contains(product2);
    }

    @Test
    void calculateProductsForShoppingCartWithGoldType() {
        this.shoppingCartService.addProduct(shoppingCart, product1);
        this.shoppingCartService.addProduct(shoppingCart, product2);

        var totalPrice = this.shoppingCartService.calculateTotalPrice(shoppingCart);
        var productsSum = product1.getCost() + product2.getCost();

        assertThat(totalPrice).isEqualTo(productsSum - productsSum * 22 * 0.01F);
        assertThat(shoppingCart.getPaymentDetails().getCreditCardType()).isEqualTo(CreditCardType.GOLD);
    }

    @Test
    void calculateProductsForShoppingCartWithNormalType() {
        paymentDetails = this.paymentDetailsService.create(CreditCardType.NORMAL);
        shoppingCart = this.shoppingCartService.create(paymentDetails);

        this.shoppingCartService.addProduct(shoppingCart, product1);
        this.shoppingCartService.addProduct(shoppingCart, product2);

        var totalPrice = this.shoppingCartService.calculateTotalPrice(shoppingCart);
        var productsSum = product1.getCost() + product2.getCost();

        assertThat(totalPrice).isEqualTo(productsSum - productsSum * 0 * 0.01F);
        assertThat(shoppingCart.getPaymentDetails().getCreditCardType()).isEqualTo(CreditCardType.NORMAL);
    }
}
