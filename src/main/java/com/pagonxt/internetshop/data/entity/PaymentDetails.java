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
@Table(name = "payment_details")
public class PaymentDetails extends BaseEntity {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "credit_card_type")
    private CreditCardType creditCardType;
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentDetails")
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();
}
