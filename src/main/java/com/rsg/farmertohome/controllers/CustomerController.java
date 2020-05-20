package com.rsg.farmertohome.controllers;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rsg.farmertohome.models.Transaction;
import com.rsg.farmertohome.services.TransactionService;


@Controller
public class CustomerController {

	@Autowired
	TransactionService customerDetail;
	@GetMapping("/customer")
	public String getDetails(Model model,HttpSession session)
	{
		long userId  = (long) session.getAttribute("userId");
		List<Transaction> transactions = customerDetail.getDetail(userId);
		model.addAttribute("transactions", transactions);
		return "customer";
	}
	
}
