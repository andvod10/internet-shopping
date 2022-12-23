package com.pagonxt.internetshop.data.mapper;

import com.pagonxt.internetshop.data.entity.CreditCardType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@Component
@PropertySource("classpath:application.properties")
public class DiscountMapper {
    private final int gold;
    private final int silver;
    private final int normal;

    DiscountMapper(
            @Value("${internetshop.card.type.discount.gold}")
                    int gold,
            @Value("${internetshop.card.type.discount.silver}")
                    int silver,
            @Value("${internetshop.card.type.discount.normal}")
                    int normal
    ) {
        this.gold = gold;
        this.silver = silver;
        this.normal = normal;
    }

    public int getDiscount(CreditCardType creditCardType) {
        return switch (creditCardType) {
            case GOLD -> gold;
            case SILVER -> silver;
            case NORMAL -> normal;
        };
    }
}
