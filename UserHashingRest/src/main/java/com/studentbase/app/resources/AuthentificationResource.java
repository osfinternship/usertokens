package com.studentbase.app.resources;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
import com.studentbase.app.temp.UserStrategy;
import com.studentbase.app.temp.dao.impl.UserCassandraStrategyImpl;
import com.studentbase.app.temp.dao.impl.UserMySQLStrategyImpl;
import com.studentbase.app.temp.entity.AbstractUser;
import com.studentbase.app.temp.entity.UserCassandra;
import com.studentbase.app.temp.entity.UserMySQL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Path("/authentification")
public class AuthentificationResource {

	// Logger
	final static Logger LOG = Logger.getLogger(AuthentificationResource.class);
			
	// Cache management
	private static final String TOKEN_CACHE_NAME = "cache1";
	private static final Integer TOKEN_CACHE_KEY = 1;
	
	static Cache cache = CacheManager.getInstance().getCache(TOKEN_CACHE_NAME);
	
	// Responses
    private static final Response OK  = Response.status(Response.Status.OK).build();	
    private static final Response BAD_REQUEST  = Response.status(Response.Status.BAD_REQUEST).build();
    private static final Response NOT_FOUND    = Response.status(Response.Status.NOT_FOUND).build();
    private static final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    private static final Response UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).build();
	
	private static UserStrategy userStrategy;
	
	static int strategy;

	static {
		strategy = 1;
		if(strategy == 0) {
			setUserStrategy(new UserMySQLStrategyImpl());
		} else {
			setUserStrategy(new UserCassandraStrategyImpl());
		}
	}
	
	public static void setUserStrategy(UserStrategy actualUserStrategy) {
		userStrategy = actualUserStrategy;
	}
    
	@GET
    @Secured
	@Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
	public Response listOfUsers() {
		LOG.info("List of users");

		GenericEntity<List<AbstractUser>> users = new GenericEntity<List<AbstractUser>>(userStrategy.findAllUsers()) {};
				
		return Response.ok(users)
				.header(HttpHeaders.AUTHORIZATION, cache.get(TOKEN_CACHE_KEY).getObjectValue())
				.build();
	}
	
	@GET
    @Secured
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response oneUser(@PathParam("id") String id) {
		
		try {
			AbstractUser myUser = (AbstractUser) userStrategy.findById(id);
	  
			LOG.info("Get by id method: " + myUser);
	  	  
			return ok(myUser);
		} catch (Exception e) {
        	LOG.error(e.getMessage());
        	return SERVER_ERROR;
		}
	}
	
    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(AbstractUser user) {
        try {

        	LOG.info("Save new user" + user);
        	      	
        	user.setPassword(md5Apache(user.getPassword()));
        	
        	if(strategy == 0) {
        		userStrategy.saveUser(new UserMySQL(user));
        	} else {
        		userStrategy.saveUser(new UserCassandra(user));
        	}
        	
            return OK;

        } catch (Exception e) {
        	LOG.error(e.getMessage());
            return BAD_REQUEST;
        }      
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(AbstractUser user) {

    	LOG.info("LOGIN: " + user);
    	
    	String username = user.getLogin();
    	String password = md5Apache(user.getPassword());
    	
        try {
            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Add element into cache
            cache.put(new Element(TOKEN_CACHE_KEY, token));
            
            // Return the token on the response
            return ok(" { \"token\": \"" + token + "\" }");

        } catch (Exception e) {
        	LOG.error(e.getMessage());
            return SERVER_ERROR;
        }      
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logout")
    public Response destroySession(@Context HttpServletRequest req) {
 	
    	req.removeAttribute(HttpHeaders.AUTHORIZATION);
    	
        return ok(" { \"info\": \"session destroyed\" }");
    }


    @PUT
    @Secured
    @Path("/user/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("id") String id,
    						   AbstractUser user) {
    	
    	try {
    		LOG.info("Put method" + user + " " + id);
    	
    		AbstractUser actualUser = (AbstractUser) userStrategy.findById(id);
	    
    		actualUser.setPassword(md5Apache(user.getPassword()));
	    
	    	userStrategy.updateUser(actualUser);
	    
    		return OK;
        } catch (Exception e) {
        	LOG.error(e.getMessage());
            return SERVER_ERROR;
        }      
    }
    
	@DELETE
    @Secured
    @Path("/user/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteUser(@PathParam("id") String id){
		
		try {
			LOG.info("Delete method: " + id);
	    
			userStrategy.deleteUserById(id);

			return OK;
        } catch (Exception e) {
        	LOG.error(e.getMessage());
            return SERVER_ERROR;
        }      
    }

    /**
     * Check if user is exists in db
     * @param username Username
     * @param password Password
     * @throws Exception User isn't exists
     */
    private void authenticate(String username, String password) throws Exception {
    	if(userStrategy.authentificate(username, password)) {
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