package com.studentbase.app.resources;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.studentbase.app.Secured;
import com.studentbase.app.entity.User;
import com.studentbase.app.service.UserService;
import com.studentbase.app.service.Impl.UserServiceImpl;

import cache.CacheAPI;

@Path("/authentification")
public class AuthentificationResource {

	// Logger
	final static Logger LOG = Logger.getLogger(AuthentificationResource.class);
	
	// User service
	UserService userService = new UserServiceImpl();
	
	// Cache management
	CacheAPI<Integer, String> cache = new CacheAPI<>("cache1");
	
	// Responses
    private static final Response OK  = Response.status(Response.Status.OK).build();	
    private static final Response BAD_REQUEST  = Response.status(Response.Status.BAD_REQUEST).build();
    private static final Response NOT_FOUND    = Response.status(Response.Status.NOT_FOUND).build();
    private static final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    private static final Response UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).build();
	
	@GET
    @Secured
	@Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
	public Response listOfUsers(@Context ContainerRequestContext req) {
		LOG.info("List of users");

		GenericEntity<List<User>> users = new GenericEntity<List<User>>(userService.findAllUsers()) {};
		
		LOG.info("LIST OF USERS : " + users.getEntity());
		LOG.info("___________________" + req.getProperty("header-auth"));
		
		LOG.info("1");
		if(req.getProperty("header-auth") != null) {
			LOG.info("2");
			return Response.ok(users).header(HttpHeaders.AUTHORIZATION, req.getProperty("header-auth").toString()).build();
		}

		LOG.info(cache.get(1));
		LOG.info(cache.expired(1));
		
		return Response.ok(users).build();//ok(users);
	}
	
	@GET
    @Secured
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response oneUser(@PathParam("id") int id) {
	  User myUser = userService.findById(id);
	  
	  LOG.info("Get by id method: " + myUser);
	  	  
	  return ok(myUser);
	}
	
    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) {
        try {

        	LOG.info("Save new user");
        	
        	user.setPassword(md5Apache(user.getPassword()));
        	
        	userService.saveUser(user);
        	
            return OK;

        } catch (Exception e) {
            return BAD_REQUEST;
        }      
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User user) {

    	LOG.info("LOGIN: " + user);
    	
    	String username = user.getLogin();
    	String password = md5Apache(user.getPassword());
    	
        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Add element into cache
            cache.put(1, token);
            
            // Return the token on the response
            return ok(" { \"token\": \"" + token + "\" }");

        } catch (Exception e) {
            return UNAUTHORIZED;
        }      
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logout")
    public Response destroySession(@Context HttpServletRequest req) {
 	
    	req.removeAttribute(HttpHeaders.AUTHORIZATION);
    	
        // returning json 
        return ok(" { \"info\": \"session destroyed\" }");

    }


    @PUT
    @Secured
    @Path("/user/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("id") int id,
    						   User user,
    						   @Context SecurityContext securityContext) {
    	
    	LOG.info("Put method" + user + " " + id);
    	
	    User actualUser = userService.findById(id);
	    
	    actualUser.setPassword(md5Apache(user.getPassword()));
	    
	    userService.updateUser(actualUser);
	    
    	return OK;
    }
    
	@DELETE
    @Secured
    @Path("/user/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteUser(@PathParam("id") int id){
	    LOG.info("Delete method: " + id);
	    
        userService.deleteUserById(id);
        
        return OK;
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
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token

        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        LOG.info("issueToken: " + token);
		return token;
    }

    /**
     * MD5 hashing
     * @param st String to hashing
     * @return Md5 hashed string
     */
	public static String md5Apache(String st) {
	    String md5Hex = DigestUtils.md5Hex(st);
	 
	    return md5Hex;
	}

    /**
     * Return response with code 200(OK) and build returned entity
     *
     * @param entity Returned json instance from client
     * @return HTTP code K
     */
    private Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }
    
}