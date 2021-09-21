package com.eldorado.authservice.constants;

/**
 * 
 * @author Mohammad Faizan Class For Constants for AuthService
 *
 */
public class AuthServiceConstants {

	private AuthServiceConstants() {

	}

	public static final String USER_NOT_PRESENT = "User is not present for username ";
	public static final String USER_IS_PRSENT = "User is present with username ";
	public static final String PASSWORD_EXPIRY = " your password is expired";
	public static final String INVALID_CREDENTIALS = "invalid credentials";
	public static final Integer EXPIRY_TIME=90;
	
	/**
	 * RESOURCE_ID is the identity for client
	 */
	public static final String RESOURCE_ID = "eldoradoresource";

	/**
	 * client credentials
	 */
	public static final String CLIENT_ID = "eldoradoclient";
	public static final String CLIENT_PASSWORD = "9999";
	public static final String USER_NOT_EXPIRED = " is not expired";
	public static final String USER_IS_EXPIRED = " is expired";

}
