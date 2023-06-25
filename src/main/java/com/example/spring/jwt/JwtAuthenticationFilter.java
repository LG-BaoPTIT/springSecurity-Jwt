package com.example.spring.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.spring.security.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		// kiem tra xem header Authorization co chua thong tin jwt ko
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
		
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
				
			//lay jwt tu request
			String jwt = getJwtFromRequest(request);
			if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
				//lay username tu Jwt
				String userName = jwtTokenProvider.getUserNameFromJwt(jwt);
				//lay thong tin ng dung tu username
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
				if(userDetails != null) {
					//neu ng dung hop le -> set thong tin ng dung cho security context
					UsernamePasswordAuthenticationToken authenticaton = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authenticaton.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticaton);
				}
			}
		}catch(Exception e) {
			log.error("fail on set user authentication", e);
		}
		filterChain.doFilter(request, response);
	}
	
	
}
