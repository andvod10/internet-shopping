package com.pagonxt.internetshop.service;

import com.pagonxt.internetshop.data.entity.CreditCardType;
import com.pagonxt.internetshop.data.entity.PaymentDetails;
import com.pagonxt.internetshop.data.repository.PaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PaymentDetailsServiceImpl implements PaymentDetailsService {
    private final PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    public PaymentDetailsServiceImpl(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    @Override
    @Transactional
    public PaymentDetails create(CreditCardType creditCardType) {
        var paymentDetails = PaymentDetails.builder()
                .creditCardType(creditCardType)
                .build();
        return this.paymentDetailsRepository.save(paymentDetails);
    }
}
