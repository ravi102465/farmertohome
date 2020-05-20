package com.rsg.farmertohome.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rsg.farmertohome.models.Product;
import com.rsg.farmertohome.models.Transaction;
import com.rsg.farmertohome.services.TransactionService;

@Controller
public class TransactionController {
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping(path =  "/purchase", method = RequestMethod.POST)
	public String buyProduct(@Valid @ModelAttribute("buyproduct") Product product, 
			BindingResult result,HttpSession session, Model model)
	{
		List<Product> newPurchase = new ArrayList<Product>();
		newPurchase.add(product);
		session.setAttribute("newPurchase", newPurchase);
		
		long userId  = (long) session.getAttribute("userId");
		List<Transaction> transactions = transactionService.getDetail(userId);
		model.addAttribute("transactions", transactions);
		
		return "customer";
	}
	
	@RequestMapping(path =  "/purchase-ok", method = RequestMethod.GET)
	public String confrimPurchase(@RequestParam String action,HttpSession session)
	{
		System.out.println(action);
		if (action.contentEquals("Confirm"))
		{
			long userId  = (long) session.getAttribute("userId");
			@SuppressWarnings("unchecked")
			List<Product> newPurchase = (List<Product>)session.getAttribute("newPurchase");
			transactionService.purchase(newPurchase,userId);
		}

		session.removeAttribute("newPurchase");
		return "redirect:customer";
	}
	
}
