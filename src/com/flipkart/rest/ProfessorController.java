package com.flipkart.rest;

import java.sql.SQLException;
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
import com.flipkart.exception.ProfessorNotTeachingCourseException;
import com.flipkart.exception.StudentNotFoundException;
import com.flipkart.exception.StudentRegistrationNotCompletedException;
import com.flipkart.model.Admin;
import com.flipkart.model.Course;
import com.flipkart.model.ProfessorCourse;
import com.flipkart.model.Student;
import com.flipkart.model.StudentCourse;
import com.flipkart.model.User;
import com.flipkart.service.ProfessorService;
import com.flipkart.service.Service;

@Path("/professor")
public class ProfessorController {
	ProfessorService professorService = new ProfessorService();
	Service service = new Service();
	Logger logger = Logger.getLogger(ProfessorController.class);
	
	@GET
	@Path("/detail/{username}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyDetails(@PathParam("username") String username) {
		User user = null;
		try {
			user = professorService.getDetailByUsername(username, Roles.PROFESSOR.toString());
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
					.entity("updated : " + (professorService.editUser(admin)==1))
					.build();
		} catch (IllegalObjectException | SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(null)
				.build();
	}
	
	@POST
	@Path("/course/{username}/{coursId}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCourse(@PathParam("username") String username,
			@PathParam("courseId") String courseId) {
		ProfessorCourse course = new ProfessorCourse();
		course.setCourseId(courseId);
		course.setProfessourUsername(username);
		int success = 0;
		
		try {
			success = professorService.addCouseToTeach(course);
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return Response
				.status(201)
				.entity(success>0)
				.build();
	}
	
	@GET
	@Path("/course/{username}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyCourse(@PathParam("username") String username) {
		List<ProfessorCourse> course = null;
		try {
			course = professorService.getTeachingCourses(username);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(course)
				.build();
	}
	
	@GET
	@Path("/student/{username}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyStudent(@PathParam("username") String username) {
		List<Student> student = null;
		try {
			student = professorService.getStudentTaughtByProfessor(username);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(student)
				.build();
	}

	@DELETE
	@Path("/course/{username}/{courseId}")
	public Response removeCourse(@PathParam("username") String username, @PathParam("courseId") String courseId) {
		int success = 0;
		try {
			ProfessorCourse course = new ProfessorCourse();
			course.setCourseId(courseId);
			course.setProfessourUsername(username);
			success = professorService.deleteCourseToTeach(course);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(200)
				.entity(success > 0)
				.build();
	}
	
	@POST
	@Path("/grade/{username}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recordGrade(@PathParam("username") String username, StudentCourse course) {
		int success = 0;
		
		try {
			success = professorService.updateGrades(course, username);
		} catch (SQLException | IllegalRoleException | StudentNotFoundException | ProfessorNotTeachingCourseException
				| StudentRegistrationNotCompletedException e) {
			logger.error(e.getMessage());
		}

		return Response
				.status(201)
				.entity(success > 0)
				.build();
	}
}
