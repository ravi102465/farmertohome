package com.rsg.farmertohome.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.rsg.farmertohome.models.Product;
import com.rsg.farmertohome.models.Transaction;
import com.rsg.farmertohome.services.CustomUserDetailsService;
import com.rsg.farmertohome.services.TransactionService;

@AutoConfigureMockMvc
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	@MockBean
	private TransactionService service;

	@MockBean
	private CustomUserDetailsService userService;
	
	@MockBean 
	private HttpSession mockHttpSession;
	
    @Test
    public void NonLoggedInAccess() throws Exception {
    	this.mockMvc.perform(get("/customer"))
                .andExpect(redirectedUrl("http://localhost/login"));

    }
	@Test
	@WithMockUser
	public void customersShouldRturnCustomerTransactionDetail() throws Exception {
		//Mock User
		long userId = 1;
		
		//mock transaction
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setProductId(1);
		transaction.setTransactionId(1);
		transaction.setUserId(userId);
		transaction.setProducts(new Product());
		transaction.getProducts().setProductName("Test component");
		transactions.add(transaction);
	    /*Mockito.doAnswer(new Answer<Object>(){
	        @Override
	        public Object answer(InvocationOnMock invocation) throws Throwable {
	            
	            return userId;
	        }
	    }).when(mockHttpSession).getAttribute("userId");*/
		//when(mockHttpSession.getAttribute("userId")).thenReturn(userId);
        //this.mockMvc = MockMvcBuilders.standaloneSetup(CustomerController.class)
        // .build();
		//mockHttpSession.setAttribute("userId", userId);
		//when(request.getSession()).thenReturn(mockHttpSession);
		/*
		 * when(mockHttpSession.getAttribute("userId")).thenReturn(userId);
		 * when(service.getDetail(userId)).thenReturn(transactions);
		 * this.mockMvc.perform(get("/customer")).andDo(print())
		 * .andExpect(status().isOk())
		 * .andExpect(content().string(containsString("Test component")));
		 */
	}
}
