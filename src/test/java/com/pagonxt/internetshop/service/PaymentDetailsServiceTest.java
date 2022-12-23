package com.pagonxt.internetshop.service;

import com.pagonxt.internetshop.configuration.TestConfigurations;
import com.pagonxt.internetshop.data.entity.CreditCardType;
import com.pagonxt.internetshop.data.entity.PaymentDetails;
import com.pagonxt.internetshop.data.repository.PaymentDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {TestConfigurations.class})
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class PaymentDetailsServiceTest {
    @Autowired
    private PaymentDetailsService paymentDetailsService;
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Test
    void createPaymentDetails() {
        var paymentDetails = this.paymentDetailsService.create(CreditCardType.GOLD);
        var persistedPaymentDetailsOptional = this.paymentDetailsRepository.findById(paymentDetails.getId());
        assertThat(persistedPaymentDetailsOptional.isPresent()).isTrue();
        PaymentDetails persistedPaymentDetails = persistedPaymentDetailsOptional.get();

        assertThat(paymentDetails).isEqualTo(persistedPaymentDetails);
        assertThat(persistedPaymentDetails.getCreditCardType()).isEqualTo(CreditCardType.GOLD);
    }
}
