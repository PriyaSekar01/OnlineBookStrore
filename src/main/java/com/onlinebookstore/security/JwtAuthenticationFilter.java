package com.onlinebookstore.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final String HEADER_STRING = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";

//	@Autowired
	private JwtService jwtService;
//	@Autowired
	private UserDetailsService userDetailsService;

	private HandlerExceptionResolver exceptionResolver;
	@Autowired
	private BlacklistService blacklistService;

	@Autowired
	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService,
			HandlerExceptionResolver exceptionResolver, BlacklistService blacklistService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.exceptionResolver = exceptionResolver;
		this.blacklistService = blacklistService;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader(HEADER_STRING);
		String jwttoken;
		String userName;
		try {
			if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			jwttoken = authHeader.substring(TOKEN_PREFIX.length());
			if (blacklistService.isBlacklisted(jwttoken)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}
			userName = jwtService.extractUserName(jwttoken);
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
				if (jwtService.isTokenValid(jwttoken, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			exceptionResolver.resolveException(request, response, null, e);
		}
	}
}