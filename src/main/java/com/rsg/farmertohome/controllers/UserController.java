package com.rsg.farmertohome.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rsg.farmertohome.models.UserDetail;
import com.rsg.farmertohome.services.CustomUserDetailsService;

@Controller
public class UserController {
	
	@Autowired
	private CustomUserDetailsService userDetailService;
	  
	@GetMapping("/signup") public String signup(Model model) {
		  model.addAttribute("registration", new UserDetail()); 
		  return "login/signup";
	  }
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request, HttpSession session,Model model) {
      
        var redirecturl = (DefaultSavedRequest)session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        String previous_url = "/";
        if (redirecturl != null)
        {
        	previous_url = redirecturl.getRequestURL();
        }
        System.out.println(redirecturl);
        request.getSession().setAttribute("url_prior_login", previous_url);

        return "login/login";
    }
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
    
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("registration") UserDetail registration, 
			BindingResult result, Model model) 
    {
    	if (result.hasErrors())
    	{
    		System.out.println("has error");
    		return "login/signup";
    	}
    	
    	if (!userDetailService.checkUserAlreadyPresent(registration.getEmail()))
    		userDetailService.register(registration);
    	else
    	{
    		model.addAttribute("userAlreadyPresent",true);
    		return "login/signup";
    	}
    	
    	System.out.println(registration.getEmail());
    	return "redirect:login?registered";
    }
    
    @RequestMapping(value="/profile", method = RequestMethod.GET)
    public String profile (HttpServletRequest request,Model model,HttpSession session) {
    	
    	model.addAttribute("userDetail", new UserDetail());
    	
        return "login/profile";
    }
    
    @PostMapping("/updateprofile")
    public String updateProfile(@Valid @ModelAttribute("updateDetail") UserDetail newDetails, 
			BindingResult result, Model model,HttpSession session,
			HttpServletRequest request, HttpServletResponse response) 
    {
    	UserDetail oldDetails = (UserDetail) session.getAttribute("userDetail");
    	if (result.hasErrors())
    	{
    		System.out.println("has error");
    		return "login/profile";
    	}
    	  	
    	
    	userDetailService.updateDetails(oldDetails,newDetails);
    	
    	if (StringUtils.isNotEmpty(newDetails.getPassword()))
    	{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null){    
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
    		return "redirect:login?passwordchanged";
    		
    	}
    	else
    		return "redirect:/";
    }
	  
}
