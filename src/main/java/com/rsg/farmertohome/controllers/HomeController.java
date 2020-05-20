package com.rsg.farmertohome.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rsg.farmertohome.models.Product;
import com.rsg.farmertohome.services.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;

    @GetMapping("/")
    public String root(Model model) {
    	List<Product> products = productService.getAll();
    	model.addAttribute("products", products);
        return "index";
    }
    
    @GetMapping("/contact")
    public String contact() {

        return "contact";
    }
    
}