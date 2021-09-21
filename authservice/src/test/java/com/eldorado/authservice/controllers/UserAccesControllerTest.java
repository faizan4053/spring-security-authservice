package com.eldorado.authservice.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.eldorado.authservice.dto.RefreshTokenDto;
import com.eldorado.authservice.dto.UserCredentialsDto;
import com.eldorado.authservice.services.UserAccessPasswordExpiry;
import com.eldorado.authservice.services.UserAccessService;
import com.eldorado.authservice.services.UserDetailsServiceImpl;
import com.eldorado.authservice.utils.AuthServiceUtility;

@ExtendWith(MockitoExtension.class)
class UserAccesControllerTest {

	@InjectMocks
	UserAccesController userController;

	@Mock
	UserAccessService userAccessService;

	@Mock
	private UserAccessPasswordExpiry userAccessPasswordExpiry;

	@Mock
	private UserDetailsServiceImpl userDetailsService;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	private UserCredentialsDto userCredentialsDto;

	private RefreshTokenDto refreshTokenDto;

	private UserDetails userDetails;

	private String refreshToken;

	private String userName;

	@BeforeEach
	void setUp() throws IOException {
		userCredentialsDto = mock(UserCredentialsDto.class);
		userDetails = mock(UserDetails.class);
		refreshTokenDto = new RefreshTokenDto();
		userDetails = mock(UserDetails.class);

		refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiY291cG9uc2VydmljZSJdLCJ1c2VyX25hbWUiOiJmYWl6QGdtYWlsLmNvbSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiIzYTUzZjA3MS02OWY5LTRjM2ItOTViYS01YTFmMjNhZmQ4ZjciLCJleHAiOjE2Mjg4NTcxOTksImp0aSI6ImExZDJlMTJmLTNjN2EtNGNmMC04MDQxLTgxZjI3MTYwZjk3NiIsImNsaWVudF9pZCI6ImNvdXBvbmNsaWVudGFwcCJ9.sgGW8Ynp3LfyPsIzmp0mfETKUjSuUyKASA7MjvJ7j1U";
		refreshTokenDto.setRefreshToken(refreshToken);
		userName = AuthServiceUtility.getUserFromJwtToken(refreshTokenDto);
	}

	@Test
	void testGetAccessTokenWhenUserCredentialsAreNotValid() throws IOException {

		when(userDetailsService.loadUserByUsername(userCredentialsDto.getUserName())).thenReturn(userDetails);

		when(passwordEncoder.matches(userCredentialsDto.getPassword(), userDetails.getPassword())).thenReturn(true);

		when(userAccessPasswordExpiry.isUserExpired(userCredentialsDto.getUserName())).thenReturn(false);

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		assertEquals(userController.getAccessToken(userCredentialsDto).getStatusCode(), response.getStatusCode());
	}

	@Test
	void testGetAccessTokenWhenUserNameIsNotValid() throws IOException {

		when(userDetailsService.loadUserByUsername(userCredentialsDto.getUserName())).thenReturn(userDetails);

		when(passwordEncoder.matches(userCredentialsDto.getPassword(), userDetails.getPassword())).thenReturn(false);

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.FORBIDDEN).body("");

		assertEquals(userController.getAccessToken(userCredentialsDto).getStatusCode(), response.getStatusCode());
	}

	@Test
	void testGetAccessTokenWhenUserPasswordIsNotValid() throws IOException {

		when(userDetailsService.loadUserByUsername(userCredentialsDto.getUserName()))
				.thenThrow(UsernameNotFoundException.class);

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.FORBIDDEN).body("");

		assertEquals(userController.getAccessToken(userCredentialsDto).getStatusCode(), response.getStatusCode());
	}

	@Test
	void testGetAccessTokenWhenUserCredentialsAreValid() throws IOException {

		when(userDetailsService.loadUserByUsername(userCredentialsDto.getUserName())).thenReturn(userDetails);

		when(passwordEncoder.matches(userCredentialsDto.getPassword(), userDetails.getPassword())).thenReturn(true);

		when(userAccessPasswordExpiry.isUserExpired(userCredentialsDto.getUserName())).thenReturn(true);

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("");

		when(userAccessService.getAccessToken(userCredentialsDto)).thenReturn(response);

		assertEquals(userController.getAccessToken(userCredentialsDto).getStatusCode(), response.getStatusCode());

	}

	@Test
	void testGetRefreshTokenWhenRefreshToknIsValid() throws IOException {

		when(userAccessPasswordExpiry.isUserExpired(userName)).thenReturn(true);

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("");

		when(userAccessService.getRefreshToken(refreshTokenDto)).thenReturn(response);

		assertEquals(userController.getRefreshToken(refreshTokenDto).getStatusCode(), response.getStatusCode());

	}

	@Test
	void testGetRefreshTokenWhenRefreshToknIsNotValid() throws IOException {

		when(userAccessPasswordExpiry.isUserExpired(userName)).thenReturn(false);

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("");

		assertEquals(userController.getRefreshToken(refreshTokenDto).getStatusCode(), response.getStatusCode());

	}
}
