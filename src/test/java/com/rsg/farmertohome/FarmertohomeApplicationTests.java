package com.rsg.farmertohome;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class FarmertohomeApplicationTests {

	@Test
	void contextLoads() {
		
		BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
		System.out.println();
		System.out.println(encoder.encode("kkhjk"));
		System.out.println();
	}

}
