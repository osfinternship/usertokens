package com.studentbase.app.resources;

import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import com.studentbase.app.Secured;
import com.studentbase.app.UserSecureContext;
import com.studentbase.app.entity.User;
import com.studentbase.app.service.UserService;
import com.studentbase.app.service.Impl.UserServiceImpl;

@Path("/authentification")
public class AuthentificationResource {

	// Logger
	final static Logger LOG = Logger.getLogger(AuthentificationResource.class);
	
	// User service
	UserService userService = new UserServiceImpl();
	
	@GET
    @Secured
	@Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
	public Response listOfUsers(@Context SecurityContext securityContext) {

		LOG.info("Sec contx: " + securityContext);
	    Principal principal = securityContext.getUserPrincipal();
	    String username = principal.getName();

	    LOG.info("In rest username=" + username);
		return Response.ok().build();
	}
	
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User user) {

    	LOG.info("LOGIN: " + user);
    	
    	String username = user.getLogin();
    	String password = user.getPassword();
    	
        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(" { \"token\": \"" + token + "\" }").build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }      
    }

    /**
     * Check if user is exists in db
     * @param username Username
     * @param password Password
     * @throws Exception User isn't exists
     */
    private void authenticate(String username, String password) throws Exception {
    	if(userService.authentificate(username, password)){
    		LOG.info("Is authentificate");
    		return;
    	}
    	LOG.info("Isn't authentificate");
    	throw new RuntimeException("Username and password isn't correct");
    }

    /**
     * Generate random token
     * @param username
     * @return
     */
    private String issueToken(String username) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        LOG.info("issueToken: " +token);
		return token;
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	@GET
	@Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
	public Response listOfUsers() {
        GenericEntity<List<User>> genericUsers = new GenericEntity<List<User>>(userService.findAllUsers()) {};

		return Response.ok().entity(genericUsers).build();
	}
	
    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response login(User user) {
    	
    	if(userService.findByLogin(user.getLogin()) == null) {
    		LOG.info("User login isn't exists");
    	}
    	else {
    		LOG.info("User login is exists");
    	}
    			
    	return Response.ok().build();
    }
*/}