package com.eldorado.authservice.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

import com.eldorado.authservice.dto.RefreshTokenDto;

public class AuthServiceUtility {

	private AuthServiceUtility() {

	}

	public static String getUserFromJwtToken(RefreshTokenDto refreshTokenDto) throws IOException {

		String[] chunks = refreshTokenDto.getRefreshToken().split("\\.");

		String payload = new String(Base64.decodeBase64(chunks[1]));

		Map<String, Object> mapping = new ObjectMapper().readValue(payload, HashMap.class);
		String userName;
		userName = mapping.get("user_name").toString();

		return userName;
	}

}
