package com.rsg.farmertohome.models;




import java.sql.Timestamp;

import lombok.Data;

@Data
public class Transaction {
	
	private long transactionId;

	private long productId;
	
	private long userId;
	
	private Product products;
	
	private Timestamp purchasedOn; 

}
