package com.flipkart.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.flipkart.constant.Roles;
import com.flipkart.exception.IllegalObjectException;
import com.flipkart.exception.IllegalRoleException;
import com.flipkart.model.Student;
import com.flipkart.model.User;
import com.flipkart.service.Service;
import com.flipkart.service.StudentService;

@Path("student")
public class StudentController {
	StudentService studentService = new StudentService();
	Service service = new Service();
	Logger logger = Logger.getLogger(StudentController.class);
	
	@GET
	@Path("/detail/{username}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyDetails(@PathParam("username") String username) {
		User user = null;
		try {
			user = studentService.getDetailByUsername(username, Roles.ADMIN.toString());
		} catch (SQLException | IllegalRoleException e) {
			logger.error(e.getMessage());
		}
		return Response.status(200).entity(user).build();
	}
	
	@GET
	@Path("/course/{username}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCourses(@PathParam("username") String username) {
		try {
			return Response.status(200).entity(studentService.getStudentCourse(username)).build();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response.status(200).entity(null).build();
	}
	
	@PUT
	@Path("/detail")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response RespoeditDetails(Student student) {
		try {
			return Response
					.status(200)
					.entity("updated : " + (studentService.editUser(student)==1))
					.build();
		} catch (IllegalObjectException | SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(null)
				.build();
	}
}
