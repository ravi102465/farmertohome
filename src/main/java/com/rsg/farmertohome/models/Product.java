package com.rsg.farmertohome.models;

import java.sql.Timestamp;

import lombok.Data;
@Data
public class Product {
	private long productId;

	private String productName;

	private String productDescription;

	private long userId;
	
	private Timestamp listedOn;
}

