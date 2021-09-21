package com.eldorado.authservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eldorado.authservice.models.AuthUser;
import com.eldorado.authservice.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserAccessPasswordExpiryTest {

	@InjectMocks
	private UserAccessPasswordExpiry userAccessPasswordExpiry;

	@Mock
	private UserRepository userRepository;

	private AuthUser user;

	@BeforeEach
	void setUp() {
		user = new AuthUser();
		user.setEmail("faiz@123.com");
		user.setPassword("doug");
	}

	@Test
	void testIfUserIsNotPresentThenThrowException() {
		user = null;
		when(userRepository.findByEmail("faiz@123.com")).thenReturn(user);

		assertThrows(UsernameNotFoundException.class, () -> {
			userAccessPasswordExpiry.isUserExpired("faiz@123.com");
		});

	}

	@Test
	void testIsUserExpiredShouldReturnFalse() {

		user.setCreatedDateTime("2011-12-03T10:15:30+01:00[Europe/Paris]");

		when(userRepository.findByEmail("faiz@123.com")).thenReturn(user);

		Boolean expected = false;

		Boolean actual = userAccessPasswordExpiry.isUserExpired("faiz@123.com");

		assertEquals(actual, expected);

	}

	@Test
	void testIsUserExpiredShouldReturnTrue() {

		user.setCreatedDateTime(LocalDateTime.now());

		when(userRepository.findByEmail("faiz@123.com")).thenReturn(user);

		Boolean expected = true;

		Boolean actual = userAccessPasswordExpiry.isUserExpired("faiz@123.com");

		assertEquals(actual, expected);

	}

}
