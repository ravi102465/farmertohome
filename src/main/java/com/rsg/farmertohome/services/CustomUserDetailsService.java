package com.rsg.farmertohome.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rsg.farmertohome.models.RoleTypes;
import com.rsg.farmertohome.models.UserDetail;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

	private static final int USER = 1;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder; 
	
	@Value("${api.url}")
	private String serviceUrl;
	
	@Value("${api.rootpath}")
	private String rootPath;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String apiURL = serviceUrl+ rootPath + "users/" + username;
		UserDetail userDetail = restTemplate.getForObject(apiURL, UserDetail.class);
		
		if (userDetail != null)
		{
			String[] roles =  userDetail.getRoles().stream()
					   .map(t -> t.getRoleType()).toArray(String[]::new);		   
		
			httpSession.setAttribute("username", userDetail.getFirstName() +" "+ userDetail.getLastName());
			httpSession.setAttribute("userId", userDetail.getUserId());
			httpSession.setAttribute("userDetail", userDetail);
			return new User(userDetail.getEmail(), userDetail.getPassword(),
					AuthorityUtils.createAuthorityList(roles));
						
		}
		
		throw new UsernameNotFoundException("username");
	}

	public void register(@Valid UserDetail registration) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		registration.setEnabled(1);
		registration.setLastUpdated(timestamp);
		registration.setLastLogin(new Timestamp(0));
		registration.setPassword(passwordEncoder.encode(registration.getPassword()));
		
		List<RoleTypes> roles= new ArrayList<>();
			roles.add(new RoleTypes(USER));

		registration.setRoles(roles);
			
		String apiURL = serviceUrl+ rootPath + "users";
		
		UserDetail userDetailPost = restTemplate.postForObject(apiURL, registration, UserDetail.class);
		
		System.out.println(userDetailPost.getEmail()); 
		
	}

	public boolean checkUserAlreadyPresent(String email) {
		String apiURL = serviceUrl+ rootPath + "users/" + email;
		
		UserDetail userDetail = restTemplate.getForObject(apiURL, UserDetail.class);
		
		return ObjectUtils.isNotEmpty(userDetail);
		
	}

	public void updateDetails(UserDetail oldDetails, @Valid UserDetail newDetails) {
		
    	BeanUtils.copyProperties(newDetails, oldDetails,
    			"email","userId","password","enabled","phoneNo",
    			"roles","lastLogin","lastUpdated");
		
    	oldDetails.setLastUpdated(new Timestamp(System.currentTimeMillis()));
    	if (StringUtils.isNotEmpty(newDetails.getPassword()))
    		oldDetails.setPassword(passwordEncoder.encode(newDetails.getPassword()));
		
		
		String apiURL = serviceUrl+ rootPath + "users/" + oldDetails.getUserId();
		restTemplate.put(apiURL, oldDetails);
	}

}
