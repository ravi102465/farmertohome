package com.rsg.farmertohome.services;

import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rsg.farmertohome.models.Product;
import com.rsg.farmertohome.models.Transaction;

@Service
public class TransactionService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${api.url}")
	private String serviceUrl;
	
	@Value("${api.rootpath}")
	private String rootPath;
	
	public List<Transaction> getDetail(long userId) {
		String apiURL = serviceUrl+ rootPath + "transaction/" + userId;
		
		Transaction[] transactions = restTemplate.getForObject(apiURL, Transaction[].class);
		return Arrays.asList(transactions);
		
	}

	public void purchase(List<Product> newPurchase, long userId) {
		
		for(Product purchase :newPurchase)
		{
			Transaction transaction = new Transaction();
			transaction.setUserId(userId);
			transaction.setProductId(purchase.getProductId());
			String apiURL = serviceUrl+ rootPath + "transaction";
			
			restTemplate.postForObject(apiURL, transaction, Transaction.class);
		}
	}

}
