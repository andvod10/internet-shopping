package com.pagonxt.internetshop.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {
    @JoinColumn(name = "payment_details_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentDetails paymentDetails;
    @Builder.Default
    @Column(name = "is_active")
    private boolean isActive = false;
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingCart")
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
