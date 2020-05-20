package com.rsg.farmertohome.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;



@Data
public class UserDetail {
	
	private long userId;
	
	@NotEmpty()
	private String firstName;
	
	private String lastName;
	
	@DateTimeFormat(iso=ISO.DATE)
	@NotNull
	private Date dateOfBirth;
	
	private String title;
	
	private String company;
	
	private String email;
	
	private String address;
	
	private String password;
	
	private String phoneNo;
	
	private int enabled;
	
	
	private Timestamp lastUpdated;
	
	private Timestamp lastLogin;
	
	public UserDetail() {
		
	}
	
	private List<RoleTypes> roles;
	
	
	
}
