package com.flipkart.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.flipkart.model.Admin;
import com.flipkart.model.Auth;
import com.flipkart.model.Course;
import com.flipkart.model.StudentCourse;
import com.flipkart.model.User;
import com.flipkart.service.AdminService;
import com.flipkart.service.ProfessorService;
import com.flipkart.service.Service;

@Path("/admin")
public class AdminController {
	AdminService adminService = new AdminService();
	Service service = new Service();
	Logger logger = Logger.getLogger(AdminController.class);
	
	@GET
	@Path("/detail/{username}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyDetails(@PathParam("username") String username) {
		User user = null;
		try {
			user = adminService.getDetailByUsername(username, Roles.ADMIN.toString());
		} catch (SQLException | IllegalRoleException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(user)
				.build();
	}
	
	@PUT
	@Path("/detail")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response editDetails(Admin admin) {
		try {
			return Response
					.status(200)
					.entity("updated : " + (adminService.editUser(admin)==1))
					.build();
		} catch (IllegalObjectException | SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(null)
				.build();
	}
	
	@GET
	@Path("/users/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUser(@PathParam("role") String role) {
		try {
			return Response
					.status(200)
					.entity(adminService.getUserByRole(role))
					.build();
		} catch (IllegalRoleException | SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.build();
	}
	
	@POST
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response addUser(Auth user) {
		try {
			return Response
					.status(201)
					.entity(adminService.addUser(user.getUsername(), user.getPassword(), user.getRole())==1)
					.build();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(201)
				.entity("failed")
				.build();
	}
	
	@DELETE
	@Path("/user/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response deleteUser(@PathParam("username") String username) {
		int res=0;
		try {
			res = adminService.deleteUser(username);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(username + " deleted successfully: " + (res==1))
				.build();
	}
	
	
	@POST
	@Path("/course")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response addCourse(Course course) {
		int success = 0;
		try {
			success = adminService.addCourse(course);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(201)
				.entity(success == 1)
				.build();
	}
	
	@DELETE
	@Path("/course/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response deleteCourse(@PathParam("courseId") String courseId) {
		int res=0;
		try {
			res = adminService.deleteCourse(courseId);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(courseId + " deleted successfully: " + (res==1))
				.build();
	}
	
	@PUT
	@Path("/course")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response editCourse(Course course) {
		int success = 0;
		try {
			success = adminService.editCourse(course);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(201)
				.entity(success == 1)
				.build();
	}
}
