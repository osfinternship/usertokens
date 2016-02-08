package com.studentbase.app.resources;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
*/
    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response login(User user) {
    	
    	LOG.info(">>>>>>>>>>>>>>>>>>>>>>. I'm here" + user.getLogin() + " " + user.getPassword());    
    	
        java.net.URI location = null;
		try {
			location = new java.net.URI("../pages/page1.html");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Response.temporaryRedirect(location).build();

//    	return Response.ok().build();
    }
}