package com.pagonxt.internetshop.service;

import com.pagonxt.internetshop.data.entity.CreditCardType;
import com.pagonxt.internetshop.data.entity.PaymentDetails;

public interface PaymentDetailsService {
    public PaymentDetails create(CreditCardType creditCardType);
}
