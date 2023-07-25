package com.springboot.systems.system_wide.services.configs.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/*
 * We will define the utilities method for generating and validating JWT token. 
 */

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	/*-----The validity below is converted from minutes to milliseconds-----*/
	public static final long JWT_TOKEN_VALIDITY = 12*60*60*1000;
	
	private final String secret = "techgeeknext";

	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public Date getIssuedAtDateFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getIssuedAt();
	}

	public Date getExpirationDateFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			System.out.println(String.format("Error : Invalid JWT signature: %s", e.getMessage()));
		} catch (MalformedJwtException e) {
			System.out.println(String.format("Error : Invalid JWT token: %s", e.getMessage()));
		} catch (ExpiredJwtException e) {
			System.out.println(String.format("Error : JWT token is expired: %s", e.getMessage()));
		} catch (UnsupportedJwtException e) {
			System.out.println(String.format("Error : JWT token is unsupported: %s", e.getMessage()));
		} catch (IllegalArgumentException e) {
			System.out.println(String.format("Error : JWT claims string is empty: %s", e.getMessage()));
		}
		return false;
	}

	public String generateJwtToken(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
