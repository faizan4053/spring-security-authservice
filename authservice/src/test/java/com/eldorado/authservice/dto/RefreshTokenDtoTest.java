package com.eldorado.authservice.dto;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RefreshTokenDtoTest {
	
	RefreshTokenDto refreshToken;
	
	
	@BeforeEach
	 public void  setUser() {
		
		refreshToken=new RefreshTokenDto();
		refreshToken.setRefreshToken("dummy_refresh_token");
	}
	
	@Test
	void testGetRefreshToken() {
		String expected="dummy_refresh_token";
		String actual=refreshToken.getRefreshToken();
		assertEquals(expected,actual);
	}
	
	

}
