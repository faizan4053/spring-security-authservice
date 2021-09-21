package com.eldorado.authservice.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.eldorado.authservice.dto.RefreshTokenDto;
import com.eldorado.authservice.dto.UserCredentialsDto;
import com.eldorado.authservice.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserAccessServiceTest {

	@InjectMocks
	UserAccessService userAccessService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private UserAccessPasswordExpiry userAccessPasswordExpiry;

	private UserCredentialsDto userCredentials;
	private RefreshTokenDto refreshTokenDto;

	@BeforeEach
	void setUp() {
		userCredentials = new UserCredentialsDto();
		userCredentials.setUserName("faiz@123.com");
		userCredentials.setPassword("doug");
		refreshTokenDto = new RefreshTokenDto();
		refreshTokenDto.setRefreshToken("refresh_token");
	}

	@Test
	void testUserAccessTokenWhenUserCredentialsAreValid() throws IOException {

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("");

		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<String>>any())).thenReturn(response);

		ResponseEntity<String> observe = userAccessService.getAccessToken(userCredentials);

		assertEquals(response.getStatusCode(), observe.getStatusCode());
	}

	@Test
	void testUserAccessTokenWhenUserCredentialsAreNotValid() throws IOException {

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");

		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<String>>any()))
						.thenThrow(HttpClientErrorException.class);

		ResponseEntity<String> observe = userAccessService.getAccessToken(userCredentials);

		assertEquals(response.getStatusCode(), observe.getStatusCode());
	}

	@Test
	void testUserRefreshTokenWhenTokenValid() throws IOException {

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("");

		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<String>>any())).thenReturn(response);

		ResponseEntity<String> observe = userAccessService.getRefreshToken(refreshTokenDto);

		assertEquals(response.getStatusCode(), observe.getStatusCode());
	}

	@Test
	void testUserRefreshTokenWhenTokenIsNotValid() throws IOException {

		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");

		when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<String>>any()))
						.thenThrow(HttpClientErrorException.class);

		ResponseEntity<String> observe = userAccessService.getRefreshToken(refreshTokenDto);

		assertEquals(response.getStatusCode(), observe.getStatusCode());
	}

}
