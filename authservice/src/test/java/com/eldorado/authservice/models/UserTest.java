package com.eldorado.authservice.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * Test for AuthUser Pojo Class
 */

class UserTest {

	/*
	 * Object of type AuthUser
	 */
	private AuthUser user;

	/*
	 * Object of type PasswordEncoder
	 */

	private PasswordEncoder passwordEncoder;

	/*
	 * Initializing passwordEncoder and user objects before each test case
	 */

	@BeforeEach
	public void setUser() {
		passwordEncoder = new BCryptPasswordEncoder();
		user = new AuthUser();
		user.setId(1L);
		user.setFirstName("Mohammad");
		user.setLastName("Faizan");
		user.setEmail("faizan@gmail.com");
		user.setPassword(passwordEncoder.encode("faizan@123"));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		user.setCreatedBy("faizan");
		user.setCreated(LocalDateTime.now());
		user.setLastThreePasswords(List.of("password1", "password2", "password3"));
		user.setModifiedBy("faizan");
		user.setModified(LocalDateTime.now());
		user.setEnabled(true);
		user.setCreatedDateTime(LocalDateTime.now());
		user.setCreatedDateTime(LocalDateTime.now().toString());
		user.setAddress1("address");
		user.setAddress2("address");
		user.setAddress3("address");
		user.setImage("image_url");
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setId(1L);
		role.setName("USER");
		roles.add(role);
		user.setRoles(roles);
	}

	/*
	 * Testing getFirstName() function
	 */

	@Test
	void testFirstName() {
		String expected = "Mohammad";
		String actual = user.getFirstName();
		assertEquals(expected, actual);
	}

	/*
	 * Testing getLastName() function
	 */

	@Test
	void testLastName() {
		String expected = "Faizan";
		String actual = user.getLastName();
		assertEquals(expected, actual);
	}

	/*
	 * Testing getEmail() function
	 */

	@Test
	void testEmail() {
		String expected = "faizan@gmail.com";
		String actual = user.getEmail();
		assertEquals(expected, actual);
	}

	/*
	 * Testing getPassword() function
	 */

	@Test
	void testPassword() {
		String expected = passwordEncoder.encode("faizan@123");
		String actual = user.getPassword();
		assertNotEquals(expected, actual);
	}

	/*
	 * Testing getId() function
	 */

	@Test
	void testUserId() {
		Long expected = 1L;
		Long actual = user.getId();
		assertEquals(expected, actual);
	}

	/*
	 * Testing AccountNonExpired function
	 */
	@Test
	void testAccountNonExpired() {
		assertTrue(user.isAccountNonExpired());
	}

	/*
	 * Testing isAccountNonLocked function
	 */
	@Test
	void testAccountNonLocked() {
		assertTrue(user.isAccountNonLocked());
	}

	/*
	 * Testing isCredentialsNonExpired function
	 */
	@Test
	void testCredentialsNonExpired() {
		assertTrue(user.isCredentialsNonExpired());
	}

	/*
	 * Testing isEnabled function
	 */
	@Test
	void testEnabled() {
		assertTrue(user.isEnabled());
	}

	@Test
	void testUserRoles() {
		Set<Role> actual = new HashSet<>();
		Role role = new Role();
		role.setId(1L);
		role.setName("USER");
		actual.add(role);
		Set<Role> expectedRoles = user.getRoles();
		assertNotEquals(actual, expectedRoles);
	}

	@Test
	void testLastThreePasswords() {
		assertNotNull(user.getLastThreePasswords());
	}

	@Test
	void testCreatedBy() {
		String expected = "faizan";
		String actual = user.getCreatedBy();
		assertEquals(expected, actual);
	}

	@Test
	void testCreated() {
		assertNotNull(user.getCreated());
	}

	@Test
	void testModifiedBy() {
		String expected = "faizan";
		String actual = user.getModifiedBy();
		assertEquals(expected, actual);
	}

	@Test
	void testModified() {
		assertNotNull(user.getModified());
	}

	@Test
	void testCreatedDateTime() {
		assertNotNull(user.getCreatedDateTime());
	}
	
	@Test
	void testAddress1() {
		String expected = "address";
		String actual = user.getAddress1();
		assertEquals(expected, actual);
	}
	
	@Test
	void testAddress2() {
		String expected = "address";
		String actual = user.getAddress2();
		assertEquals(expected, actual);
	}
	
	@Test
	void testAddress3() {
		String expected = "address";
		String actual = user.getAddress3();
		assertEquals(expected, actual);
	}
	
	@Test
	void testImageUrl() {
		String expected = "image_url";
		String actual = user.getImage();
		assertEquals(expected, actual);
	}

}
