package com.studentbase.app.resources;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentbase.app.Utilities;
import com.studentbase.app.entity.Student;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@Path("/student")
public class StudentResource {

	// Logger
	final static Logger LOG = Logger.getLogger(StudentResource.class);

	// Cache management
	private static final String TOKEN_CACHE_NAME = "student_cache";
	
	static Cache cache = CacheManager.getInstance().getCache(TOKEN_CACHE_NAME);

    private static final Response OK  = Response.status(Response.Status.OK).build();	
    private static final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    private static final Response BAD_REQUEST  = Response.status(Response.Status.BAD_REQUEST).build();

    @POST
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response newStudent(String hexStudent) {
        try {

        	LOG.info("HEX encoded: " + hexStudent);
        	
        	byte[] bytes = hexStudent.getBytes();

    		Checksum checksum = new CRC32();
    		
            // update the current checksum with the specified array of bytes
            checksum.update(bytes, 0, bytes.length);
            
            // get the current checksum value
            long checksumValue = checksum.getValue();

            if(Utilities.compareCheckSum(checksumValue)) {
        		ObjectMapper mapper = new ObjectMapper();

        		String decodedHex = null;
        		try {
        			decodedHex = new String(Hex.decodeHex(hexStudent.toCharArray()));
        		} catch (DecoderException e1) {
        			return SERVER_ERROR;
        		}
        		
        		Student decodedStudent = null;
        		try {
        			decodedStudent = mapper.readValue(decodedHex, Student.class);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}

        		LOG.info("DECODED STUDENT: " + decodedStudent);
                return OK;
            } else {
            	return SERVER_ERROR;
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage());
            return BAD_REQUEST;
        }      
    }

}
