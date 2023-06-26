package com.example.spring.jwt;



import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.spring.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	
	@Value("${ra.jwt.secret}")
	private String JWT_SECRET;
	
	@Value("${ra.jwt.expiration}")
	private String JWT_EXPIRATION; 
	//tao jwt tu thong tin user
	public String generateToken(CustomUserDetails customUserDetails) {
		
		Date now = new Date();
		Date dateExpiration = new Date(now.getTime() + JWT_EXPIRATION);
				
		return Jwts.builder()
				.setSubject(customUserDetails.getUsername())
				.setIssuedAt(now)
				.setExpiration(dateExpiration)
				.signWith(SignatureAlgorithm.HS512, JWT_EXPIRATION)
				.compact();
		
	}
	
	//lay thong tin tu jwt
	public String getUserNameFromJwt(String token) {
		Claims claims = Jwts.parser()
							.setSigningKey(JWT_SECRET)
							.parseClaimsJws(token)
							.getBody();
		return claims.getSubject();
	}
	
	// validate jwt token
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
			.setSigningKey(JWT_SECRET)
			.parseClaimsJws(token);
			return true;
		}catch(MalformedJwtException mje) {
			log.error("Invalid JWT Token");
		}
		catch(ExpiredJwtException eje) {
			log.error("Expired JWT Token");
		}
		catch(UnsupportedJwtException uje) {
			log.error("Unsupported JWT Token");
		}
		catch(IllegalArgumentException iae) {
			log.error("JWT claims String is empty");
		}
		return false;
	}
	
}
