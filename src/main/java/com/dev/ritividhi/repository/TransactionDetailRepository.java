package com.dev.ritividhi.repository;

import com.dev.ritividhi.models.TransactionDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailRepository extends MongoRepository<TransactionDetail, String> {


	TransactionDetail findByTransactionId(String transactionId);

	TransactionDetail findByOrderId(String orderId);

	TransactionDetail findByMerchantTransactionId(String merchantTransactionId);
	
}
