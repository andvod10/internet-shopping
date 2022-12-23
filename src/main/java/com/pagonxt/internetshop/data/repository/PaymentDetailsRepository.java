package com.pagonxt.internetshop.data.repository;

import com.pagonxt.internetshop.data.entity.PaymentDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepository extends CrudRepository<PaymentDetails, String> {

}
