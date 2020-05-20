package com.rsg.farmertohome.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rsg.farmertohome.models.Product;

@Service
public class FarmerService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${api.url}")
	private String serviceUrl;
	
	@Value("${api.rootpath}")
	private String rootPath;
	
	public List<Product> getListedProductByFarmer(long userId) {
		
		String apiURL = serviceUrl+ rootPath + "products/" + userId;
		
		 Product[] products = restTemplate.getForObject(apiURL,Product[].class);
		 
		 if (products != null)
		 {
			 return Arrays 
		            .stream(products) 
		            .collect(Collectors.toList());
		 }
		 else
		 {
			 return new ArrayList<Product>();
		 }
	}

	public Product listNewProduct(@Valid Product product) {
		
		String apiURL = serviceUrl+ rootPath + "products";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		product.setListedOn(timestamp);
		Product newProduct = restTemplate.postForObject(apiURL, product, Product.class);
		
		System.out.println(newProduct.getProductId() + newProduct.getProductName()); 
		return newProduct;
	}
	
}