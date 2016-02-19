package com.studentbase.app;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthentificationFilter implements ContainerRequestFilter {

	// Logger
	final static Logger LOG = Logger.getLogger(AuthentificationFilter.class);

	// Cache management
	private static final String TOKEN_CACHE_NAME = "cache1";
	private static final Integer TOKEN_CACHE_KEY = 1;

	static Cache cache = CacheManager.getInstance().getCache("cache1");
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
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
        	
            // Validate the token and send header
            validateToken(token);
        	
        } catch (Exception e) {
        	LOG.error("UNAUTHORIZED");
            
        	requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
        }
	}
	
	/**
	 * Validate token
	 * @param token Encoded token
	 * @return Decoded username from token
	 */
    private void validateToken(String token) {
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid    	
    	
    	if(expired(TOKEN_CACHE_KEY)) {
    		LOG.info("Cache is expired!");

//    		cache.put(new Element(TOKEN_CACHE_KEY, generateNewToken()));
    		throw new RuntimeException("Tokens is expired, please login again!");
    		
//    		LOG.info("New token generated and pushed into cache: \n" + cache.getQuiet(TOKEN_CACHE_KEY));
//    		return;
    	}
    	else {
    		LOG.info("Comparing tokens: \n" + token + " \n" + cache.get(TOKEN_CACHE_KEY).getObjectValue().toString());	
    		
    		if(cache.get(TOKEN_CACHE_KEY).getObjectValue().equals(token)) {
    			LOG.info("Tokens the same");
    			return;
    		}
    		else {
        		LOG.info("Tokens aren't the same");
        		throw new RuntimeException("Tokens is different");
    		}
    	}
    }

    /**
     * Check if token expired
     * @param key
     * @return True of False
     */
    public boolean expired(final Integer key) {
    	boolean expired = true;
    	// Do a quiet get so we don't change the last access time.
    	final Element element = cache.getQuiet(key);
    	if (element != null) {
    		expired = cache.isExpired(element);
    	}
    	
    	return expired;
    }

    /**
     * Generation new token
     * @return New token
     */
    private String generateNewToken() {
    	Random random = new SecureRandom();
    	return new BigInteger(130, random).toString(32);
    }
}

