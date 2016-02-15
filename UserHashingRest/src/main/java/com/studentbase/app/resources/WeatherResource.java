package com.studentbase.app.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

@Path("/weather")
public class WeatherResource {

	// Logger
	final static Logger LOG = Logger.getLogger(WeatherResource.class);
	
	// Cache management
	private static final String TOKEN_CACHE_NAME = "weather_cache";

	// cache manager
	static CacheManager cacheManager;
	
	// cache instance
	static Ehcache cache;
	
	// getter and setter of cache manager
	public static CacheManager getCacheManager() {
		return cacheManager;
	}

	public static void setCacheManager(CacheManager cacheManager) {
		cache = cacheManager.getEhcache(TOKEN_CACHE_NAME);
	}

	// API key
	private static final String API_KEY = "27d4f937fd01327742bdbecb58657ea3";
	
	// Responses
    private static final Response OK  = Response.status(Response.Status.OK).build();	
    private static final Response BAD_REQUEST  = Response.status(Response.Status.BAD_REQUEST).build();
    private static final Response NOT_FOUND    = Response.status(Response.Status.NOT_FOUND).build();
    private static final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    private static final Response UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).build();

    /**
     * Return response with code 200(OK) and build returned entity
     *
     * @param entity Returned json instance from client
     * @return HTTP code K
     */
    private Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }

	@GET
	@Path("/city")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getWeatherByCity(@QueryParam("name") String city) {
		
		LOG.info("CITY = " + city);
				
		if(expired(city)) {
			
			// get client service
			Client client = ClientBuilder.newClient();
			
			// set target API
			WebTarget webTarget = client
					.target("http://api.openweathermap.org/data/2.5/forecast");

			// get data from API
			Response resp = webTarget.path("/city")
					.queryParam("APPID", API_KEY)
					.queryParam("q", city)
					.request().get();

			LOG.info("NEW ELEMENT IN CACHE: " + city);
			
			// put new element into cache
			cache.put(new Element(city, resp.readEntity(String.class).toString()));	
			
			// create dynamic cache configuration
			CacheConfiguration config = cache.getCacheConfiguration();
			
			// set time to live in the cache
			config.setTimeToLiveSeconds(calculateDifference(System.currentTimeMillis()));

		}
				
		LOG.info("GET INFO FROM CACHE: " + cache.get(city) + "\nTime to dead: " + (cache.get(city).getTimeToLive()));
		
		// return entity
		return ok("{ \"result\": \"" + cache.get(city).getObjectValue() + "\" }");
	}

    /**
     * Check if token expired
     * @param key
     * @return True of False
     */
    public boolean expired(final String key) {
    	boolean expired = true;
    	// Do a quiet get so we don't change the last access time.
    	final Element element = cache.getQuiet(key);
    	if (element != null) {
    		expired = cache.isExpired(element);
    	}
    	
    	return expired;
    }

	/**
	 * Calculate time between current time and midnight
	 * @param currentTimeInMilis
	 * @return 
	 */
	public long calculateDifference(long currentTimeInMilis) {
		SimpleDateFormat dateF = new SimpleDateFormat("dd-M-yyyy");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = dateF.format(new Date()) + " 23:59:59";
		
		Date date = null;
		try {
			date = sdf.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        		
		LOG.info("LEAVE: " + (date.getTime() - currentTimeInMilis) / 1000);
		return (date.getTime() - currentTimeInMilis) / 1000;
	}
}