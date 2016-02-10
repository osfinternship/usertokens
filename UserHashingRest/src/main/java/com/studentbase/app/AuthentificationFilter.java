package com.studentbase.app;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import cache.CacheAPI;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthentificationFilter implements ContainerRequestFilter {

	// Logger
	final static Logger LOG = Logger.getLogger(AuthentificationFilter.class);

	// Cache management
	CacheAPI<Integer, String> cache = new CacheAPI<>("cache1");
		
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
	
//	    HttpServletRequest httpRequest = httpRequestProvider.get();
  
//	    requestContext.setSecurityContext(new UserSecurityContext(httpRequest.getHeader("username")));

	    // Get the HTTP Authorization header from the request
        String authorizationHeader = 
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        LOG.info("auth header:  " + authorizationHeader);
        
        // Check if the HTTP Authorization header is present and formatted correctly 
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

        	LOG.info("TOKEN: " + token);
        	
            // Validate the toke
            String newToken = validateToken(token);

            if(newToken != null)
            	requestContext.setProperty("header-auth", newToken);
        } catch (Exception e) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
        }
	}
	
	/**
	 * Validate token
	 * @param token Encoded token
	 * @return Decoded username from token
	 */
    private String validateToken(String token) {
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid

        LOG.info("validate token - " + token + " " + cache.get(1));
        
        if(cache.expired(1)) {
        	LOG.info("Token is expired - " + cache.get(1));
        	
            Random random = new SecureRandom();
            String newToken = new BigInteger(130, random).toString(32);

            cache.put(1, newToken);
        	LOG.info("Generated new token - " + cache.get(1));
        	
        	return newToken;
        }
        else {    
        	if(cache.get(1).equals(token)) {
        		LOG.info("Tokens the same");
        		return null;
        	}
        	else {
        		LOG.info("Tokens aren't the same");
        		throw new RuntimeException("Tokens is different");
        	}
        }
    }
}
