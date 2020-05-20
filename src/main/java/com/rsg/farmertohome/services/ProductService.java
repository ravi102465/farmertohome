package com.rsg.farmertohome.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rsg.farmertohome.models.Product;

@Service
public class ProductService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${api.url}")
	private String serviceUrl;
	
	@Value("${api.rootpath}")
	private String rootPath;
	
	public List<Product> getAll() {
		
		String apiURL = serviceUrl+ rootPath + "products";
		
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
	

}
