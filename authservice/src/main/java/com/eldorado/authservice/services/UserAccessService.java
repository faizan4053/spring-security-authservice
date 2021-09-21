package com.eldorado.authservice.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.eldorado.authservice.constants.AuthServiceConstants;
import com.eldorado.authservice.dto.RefreshTokenDto;
import com.eldorado.authservice.dto.UserCredentialsDto;

@Service
public class UserAccessService {

	@Value("${baseUri}")
	private String baseUri;

	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<String> getAccessToken(UserCredentialsDto userCredentials) throws IOException {

		ResponseEntity<String> response = null;

		ObjectMapper objectMapper = new ObjectMapper();
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("username", userCredentials.getUserName());
		body.add("password", userCredentials.getPassword());
		body.add("grant_type", "password");

		String credentials = AuthServiceConstants.CLIENT_ID + ":" + AuthServiceConstants.CLIENT_PASSWORD;
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		HttpEntity<?> request = new HttpEntity<>(body, headers);
		String tokenUrl = baseUri + "oauth/token";

		try {
			response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
		} catch (HttpClientErrorException ex) {
			Map<String, String> res = new HashMap<>();
			res.put("error", "invalid_grant");
			res.put("error_description", "Bad credentials");
			String jsonResponse = objectMapper.writeValueAsString(res);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
		}
		return response;
	}

	public ResponseEntity<String> getRefreshToken(RefreshTokenDto refreshTokenDto) throws IOException {

		ResponseEntity<String> response = null;
		ObjectMapper objectMapper = new ObjectMapper();
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("refresh_token", refreshTokenDto.getRefreshToken());
		body.add("grant_type", "refresh_token");

		String credentials = AuthServiceConstants.CLIENT_ID + ":" + AuthServiceConstants.CLIENT_PASSWORD;
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		HttpEntity<?> request = new HttpEntity<>(body, headers);
		String tokenUrl = baseUri + "oauth/token";

		try {
			response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
		} catch (HttpClientErrorException ex) {
			Map<String, String> res = new HashMap<>();
			res.put("error", "invalid_token");
			res.put("error_description", "Invalid refresh token (expired)");
			String jsonResponse = objectMapper.writeValueAsString(res);

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse);
		}
		return response;
	}

}
