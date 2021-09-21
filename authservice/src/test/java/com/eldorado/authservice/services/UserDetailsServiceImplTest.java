package com.eldorado.authservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eldorado.authservice.models.AuthUser;
import com.eldorado.authservice.models.AuthUserDetails;
import com.eldorado.authservice.repositories.UserRepository;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

	@InjectMocks
	UserDetailsServiceImpl userService;

	@Mock
	private UserRepository userRepository;
	
	@Test
	void testLoadUserByUsernameWhenUserIsNotPresent() {

		AuthUser user = null;
		when(userRepository.findByEmail("faiz@123.com")).thenReturn(user);
		
		assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername("faiz@123.com");
		});
	}

	@Test
	void testLoadUserByUsernamewhenPresentandCorrectCredentials() {

		AuthUser user = new AuthUser();
		user.setEmail("faiz@123.com");
		user.setPassword("doug");
		when(userRepository.findByEmail("faiz@123.com")).thenReturn(user);

		UserDetails userDetails = new AuthUserDetails(user);
		assertEquals(userService.loadUserByUsername("faiz@123.com").getUsername(), userDetails.getUsername());
	}

	@Test
	void testLoadUserByUsernamewhenUserIsNotPresent() {

		AuthUser user = null;
		when(userRepository.findByEmail("faiz@123.com")).thenThrow(UsernameNotFoundException.class);

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername("faiz@123.com");
		});
	}
}
