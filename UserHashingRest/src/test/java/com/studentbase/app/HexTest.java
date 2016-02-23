package com.studentbase.app;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentbase.app.entity.Grade;
import com.studentbase.app.entity.Student;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HexTest {

	//logger
	final static Logger LOG = Logger.getLogger(HexTest.class);

	// Cache management
	private static final String TOKEN_CACHE_NAME = "student_cache";
	
	static Cache cache = CacheManager.getInstance().getCache(TOKEN_CACHE_NAME);

	@BeforeClass
	public static void beforeTest() {
	}
	
	@AfterClass
	public static void afterTest() {
	
	}
	private Student createStudent() {
		Student student = new Student();
		student.setName("John");
		student.setSurname("Lock");
//		student.setBirthDate(new Date("11-11-2001"));
		
		Grade grade = new Grade();
		grade.setName("Math");
		grade.setGrade1(5.0d);
		grade.setGrade2(7.3d);
		
		double avg = (grade.getGrade1() + grade.getGrade2()) / 2;
		
		grade.setAverage(avg);
		
		student.getGrades().add(grade);
		
		LOG.info(student);
		return student;
	}
	
	@Test
	public void test11() {
		ObjectMapper mapper = new ObjectMapper();

		Student student = createStudent();
		// Convert object to JSON string
		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(student);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		LOG.info("Json: " + jsonInString);

//		char[] retrieveHex = Hex.encodeHex( jsonInString.getBytes() );

		String retrieveHex = Hex.encodeHexString( jsonInString.getBytes() );
		
		byte[] bytes1 = new String(retrieveHex).getBytes();
		byte[] bytes2 = new String(retrieveHex).getBytes();
		byte[] bytes3 = new String(retrieveHex).getBytes();
		
		LOG.info(new String(bytes1) +"\n" + new String(bytes2)+"\n" + new String(bytes3));
		
		LOG.info("HEX json: " + new String(retrieveHex));
		
		Checksum checksum1 = new CRC32();
		Checksum checksum2 = new CRC32();
		Checksum checksum3 = new CRC32();
		
        // update the current checksum with the specified array of bytes
        checksum1.update(bytes1, 0, bytes1.length);
        checksum2.update(bytes2, 0, bytes2.length);
        checksum3.update(bytes3, 0, bytes3.length);
        
        // get the current checksum value
        long checksumValue1 = checksum1.getValue();
        long checksumValue2 = checksum2.getValue();
        long checksumValue3 = checksum3.getValue();

        LOG.info("CRC32 checksum for input string is: " + checksumValue1 + "\n" + checksumValue2 + "\n" + checksumValue3);

//		cache.put(new Element(checksumValue, new String(retrieveHex)));
//		LOG.info(cache.get(checksumValue));
/*		String decodedHex = null;
		try {
			decodedHex = new String(Hex.decodeHex(retrieveHex));
		} catch (DecoderException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("HEX decoded: " + decodedHex);
		
		Student decodedStudent = null;
		try {
			decodedStudent = mapper.readValue(decodedHex, Student.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(decodedStudent);
*/	}
	
	@Ignore
	@Test
	public void test1() throws DecoderException {
		String foo = "I am a string";
		
		char[] bytearr = Hex.encodeHex( foo.getBytes() );
		
		System.out.println(bytearr);
		
		System.out.println( new String(Hex.decodeHex(bytearr)) );
	
	}
	
	public static String md5Apache(String st) {
	    String md5Hex = DigestUtils.md5Hex(st);
	 
	    return md5Hex;
	}
}
