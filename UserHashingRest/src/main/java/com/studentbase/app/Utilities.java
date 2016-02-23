package com.studentbase.app;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentbase.app.entity.Grade;
import com.studentbase.app.entity.Student;

public class Utilities {

	// Logger
	final static Logger LOG = Logger.getLogger(Utilities.class);

	private static Student createStudent() {
		Student student = new Student();
		student.setName("John");
		student.setSurname("Lock");
//		student.setBirthDate(new Date());
		
		Grade grade = new Grade();
		grade.setName("Math");
		grade.setGrade1(5.0d);
		grade.setGrade2(7.3d);
		
		double avg = (grade.getGrade1() + grade.getGrade2()) / 2;
		
		grade.setAverage(avg);
		
		student.getGrades().add(grade);
		
		return student;
	}

	public static long createMockStudentAndGetCheckSum() {
		ObjectMapper mapper = new ObjectMapper();

		Student student = createStudent();

		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(student);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error on creating JSON");
		}
		LOG.info("JsonInString: " + jsonInString);

		String retrieveHex = Hex.encodeHexString( jsonInString.getBytes() );
		LOG.info("RETRIEVE HEX: " + retrieveHex);
		
		byte[] bytes = retrieveHex.getBytes();
		
		Checksum checksum = new CRC32();
		
        // update the current checksum with the specified array of bytes
        checksum.update(bytes, 0, bytes.length);
        
        // get the current checksum value
        long checksumValue = checksum.getValue();

        LOG.info("CRC32 checksum: " + checksumValue);

        return checksumValue;
	}
	
	public static boolean compareCheckSum(long actualCheckSum) {
		
		// create mock student
		long mockCheckSum = createMockStudentAndGetCheckSum();
		
		LOG.info("Checking cheksSums...\nActual checksum" + actualCheckSum + "\nRetrieved checksum" + mockCheckSum);

		if (mockCheckSum == actualCheckSum) {
			LOG.info("Check sums is similar");
			EmailService.sendMessage("robannnnn@gmail.com", "robannnnn@bigmir.net", "Check sums", "Check sums is similar");
			return true;
		}
		LOG.info("Check sums isn't similar");
		EmailService.sendMessage("robannnnn@gmail.com", "robannnnn@bigmir.net", "Check sums", "Check sums isn't similar");
		return false;
	}
	
}
