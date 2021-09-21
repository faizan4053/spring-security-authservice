package com.eldorado.authservice.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eldorado.authservice.constants.AuthServiceConstants;
import com.eldorado.authservice.dto.RefreshTokenDto;
import com.eldorado.authservice.dto.UserCredentialsDto;
import com.eldorado.authservice.services.UserAccessPasswordExpiry;
import com.eldorado.authservice.services.UserAccessService;
import com.eldorado.authservice.services.UserDetailsServiceImpl;
import com.eldorado.authservice.utils.AuthServiceUtility;

/**
 * RestController to get access token
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/auth")
public class UserAccesController {

	@Autowired
	private UserAccessService userAccessService;

	@Autowired
	private UserAccessPasswordExpiry userAccessPasswordExpiry;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/getaccesstoken")
	public ResponseEntity<String> getAccessToken(@RequestBody UserCredentialsDto userCredentials) throws IOException {
		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(userCredentials.getUserName());
		} catch (UsernameNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(AuthServiceConstants.INVALID_CREDENTIALS);
		}

		if (!passwordEncoder.matches(userCredentials.getPassword(), userDetails.getPassword()))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(AuthServiceConstants.INVALID_CREDENTIALS);

		if (!userAccessPasswordExpiry.isUserExpired(userCredentials.getUserName()))
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(userCredentials.getUserName() + AuthServiceConstants.PASSWORD_EXPIRY);

		return userAccessService.getAccessToken(userCredentials);
	}

	@PostMapping("/getrefreshtoken")
	public ResponseEntity<String> getRefreshToken(@RequestBody RefreshTokenDto refreshTokenDto) throws IOException {

		String userName = AuthServiceUtility.getUserFromJwtToken(refreshTokenDto);
		if (!userAccessPasswordExpiry.isUserExpired(userName))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userName + AuthServiceConstants.PASSWORD_EXPIRY);

		return userAccessService.getRefreshToken(refreshTokenDto);
	}

}
