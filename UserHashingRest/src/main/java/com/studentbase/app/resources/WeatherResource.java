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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.studentbase.app.Secured;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

@Path("/weather")
public class WeatherResource {

	// Logger
	final static Logger LOG = Logger.getLogger(WeatherResource.class);
	
	// Cache management
	private static final String AUTHORIZATION_CACHE_NAME = "cache1";
	private static final String WEATHER_CACHE_NAME = "weather_cache";

	private static final Integer AUTHORIZATION_TOKEN_KEY = 1;
	
    private static final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

	// cache manager
	static CacheManager cacheManager;
	
	// cache instance
	static Ehcache authorizationCache;
	static Ehcache weatherCache;
	
	// getter and setter of cache manager
	public static CacheManager getCacheManager() {
		return cacheManager;
	}

	public static void setCacheManager(CacheManager cacheManager) {
		authorizationCache = cacheManager.getEhcache(AUTHORIZATION_CACHE_NAME);
		weatherCache = cacheManager.getEhcache(WEATHER_CACHE_NAME);
	}

	// API key
	private static final String API_KEY = "27d4f937fd01327742bdbecb58657ea3";

	@GET
    @Secured
	@Path("/city")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getWeatherByCity(@QueryParam("name") String city) {
		
		LOG.info("CITY = " + city);

		try {
			
		  if(expired(city)) {
			
			LOG.info("REQUEST TO WEATHER API...");
			
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
			weatherCache.put(new Element(city, resp.readEntity(String.class).toString()));	
			
			// create dynamic cache configuration
			CacheConfiguration config = weatherCache.getCacheConfiguration();
			
			// set time to live in the cache
			config.setTimeToLiveSeconds(calculateDifference(System.currentTimeMillis()));

		  }
				
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		LOG.info("GET INFO FROM CACHE: " + weatherCache.get(city) + "\nTime to dead: " + (weatherCache.get(city).getTimeToLive()));
		
		// return entity
		return Response.ok()
				.entity("{ \"result\": \"" + weatherCache.get(city).getObjectValue() + "\" }")
				.header(HttpHeaders.AUTHORIZATION, authorizationCache.get(AUTHORIZATION_TOKEN_KEY).getObjectValue())
				.build();
	}

    /**
     * Check if token expired
     * @param key
     * @return True of False
     */
    public boolean expired(final String key) {
    	boolean expired = true;
    	// Do a quiet get so we don't change the last access time.
    	final Element element = weatherCache.getQuiet(key);
    	if (element != null) {
    		expired = weatherCache.isExpired(element);
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