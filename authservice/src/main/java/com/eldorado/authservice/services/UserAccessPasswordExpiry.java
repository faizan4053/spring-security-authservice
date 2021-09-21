package com.eldorado.authservice.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eldorado.authservice.constants.AuthServiceConstants;
import com.eldorado.authservice.models.AuthUser;
import com.eldorado.authservice.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserAccessPasswordExpiry {

	@Autowired
	private UserRepository userRepository;

	public boolean isUserExpired(String userName) {

		AuthUser user = userRepository.findByEmail(userName);
		if (user == null) {
			log.error(AuthServiceConstants.USER_NOT_PRESENT + userName);
			throw new UsernameNotFoundException(AuthServiceConstants.USER_NOT_PRESENT);
		}
		if (user.getCreatedDateTime() != null && Duration.between(user.getCreatedDateTime(), LocalDateTime.now())
				.toDays() >= AuthServiceConstants.EXPIRY_TIME) {
			user.setAccountNonExpired(false);
			userRepository.save(user);
			log.info(userName + AuthServiceConstants.USER_IS_EXPIRED);
			return false;
		}
		log.info(userName + AuthServiceConstants.USER_NOT_EXPIRED);
		return true;
	}

}
