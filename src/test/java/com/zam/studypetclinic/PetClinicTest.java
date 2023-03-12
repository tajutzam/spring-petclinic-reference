package com.zam.studypetclinic;

import com.zam.studypetclinic.services.JwtService;
import com.zam.studypetclinic.services.impl.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class PetClinicTest {

	@Test
	void contextLoads() {
	}


	@Test
	void testExtractAllClaims(){

		JwtService jwtService = new JwtServiceImpl();
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6YW0iLCJpYXQiOjE2NzgzNjkwMjIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9XSwiZW1haWwiOiJ6YW0iLCJleHAiOjE2NzgzNzA0NjJ9.6Az7wqiwyfEHSuES1fJFOB1GR06WTmvJEQbUqyfTJpw";
		Claims claims = jwtService.extractAllClaims(token);
		ArrayList<Map<String , String>> authorities = (ArrayList<Map<String , String>>) claims.get("authorities");
		System.out.println(authorities);
	}
}
