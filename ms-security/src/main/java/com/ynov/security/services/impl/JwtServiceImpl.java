package com.ynov.security.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ynov.security.services.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

	private DecodedJWT decodedJWT;

	@Value("${token.signing.key}")
	private String jwtSigningKey;

	@Override
	public String extractUsername(String token) {
		decodedJWT = JWT.decode(token);
		return decodedJWT.getSubject();
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		return JWT.create()
				.withSubject(userDetails.getUsername())
				.withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.sign(Algorithm.HMAC256(jwtSigningKey));
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		decodedJWT = JWT.decode(token);
		return decodedJWT.getExpiresAt();
	}
}
