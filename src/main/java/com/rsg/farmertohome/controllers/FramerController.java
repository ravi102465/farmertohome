package com.rsg.farmertohome.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rsg.farmertohome.models.Product;
import com.rsg.farmertohome.services.FarmerService;

@Controller
public class FramerController {
	
	@Autowired
	FarmerService farmerService;
	@GetMapping("/farmer")
	public String Framer(Model model,HttpSession session)
	{
		long userId  = (long) session.getAttribute("userId");
		List<Product> Products = farmerService.getListedProductByFarmer(userId);
		model.addAttribute("newproduct", new Product());
		model.addAttribute("listedproducts", Products);
		return "product/farmer";
	}
	
    @PostMapping("/farmer")
    public String registration(@Valid @ModelAttribute("newproduct") Product product, 
			BindingResult result,HttpSession session) 
    {
    	
    	if (result.hasErrors())
    	{
    		System.out.println("has error");
    		return "product/farmer";
    	}
    	
		long userId  = (long) session.getAttribute("userId");
		product.setUserId(userId);
		
    	Product listedProduct = farmerService.listNewProduct(product);
    	
    	System.out.println(listedProduct.getProductId() +  listedProduct.getProductName());
    	return "redirect:farmer";
    }
}
