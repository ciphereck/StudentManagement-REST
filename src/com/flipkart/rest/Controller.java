package com.flipkart.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.flipkart.model.StudentCourse;
import com.flipkart.service.Service;

@Path("generic")
public class Controller {
	Logger logger = Logger.getLogger(Controller.class);
	Service service = new Service();
	
	@GET
	@Path("/course")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCourses() {
		try {
			return Response.status(200).entity(service.getAllCourses()).build();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response.status(200).entity(null).build();
	}
	
	@GET
	@Path("reportCard/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response getReportCard(@PathParam("username") String username) {
		StudentCourse course = new StudentCourse();
		course.setStudentUsername(username);
		List<StudentCourse> courses = null;
		try {
			courses = service.getReportCard(course);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(courses)
				.build();
	}
}
