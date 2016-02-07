package com.studentbase.app.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.studentbase.app.entity.User;
import com.studentbase.app.service.UserService;
import com.studentbase.app.service.Impl.UserServiceImpl;

@Path("/")
public class MainResource {

	//logger
	final static Logger LOG = Logger.getLogger(MainResource.class);

	UserService userService = new UserServiceImpl();
	
	@GET
	@Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
	public Response listOfUsers() {
        GenericEntity<List<User>> genericUsers = new GenericEntity<List<User>>(userService.findAllUsers()) {};

		return Response.ok().entity(genericUsers).build();
	}
	
/*	@GET
	@Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
	public Response newStudent() {
        GenericEntity<List<Student>> genericUsers = new GenericEntity<List<Student>>(studentService.getAllStudents()) {};

		return Response.ok().entity("{'new_user': '" + studentService.getAllStudents().get(studentService.getAllStudents().size() - 1) + "'}").build();
	}
	
    @POST
    @Path("/checkSurname")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response checkIfSurnameExists(@FormParam("surname") String surname,
    		@Context HttpServletResponse servletResponse) throws IOException {
    		    	
    	if(studentService.getBySurname(surname)) {
    		LOG.info("Student with this surname is exists");
        	servletResponse.sendRedirect("/webapi/list");
        	return Response.ok().build();
    	}
    	
    	if(surname != null) {
    		Student student = new Student();
    		student.setName(surname);
    		student.setSurname(surname);
    		student.setBirthDate(new Date());
    		studentService.create(student);
    		
    		LOG.info("New student created: " + student);
        	servletResponse.sendRedirect("/webapi/new");
    	}
    	
    	return Response.ok().build();
    }
*/}