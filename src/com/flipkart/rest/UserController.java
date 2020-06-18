package com.flipkart.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.flipkart.exception.BadCredentialException;
import com.flipkart.model.Auth;
import com.flipkart.service.CredentialService;

@Path("auth")
public class UserController {
	CredentialService CredentialService = new CredentialService();
	Logger logger = Logger.getLogger(UserController.class);
	
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Auth auth) {
		String role = "";
		try {
			role = CredentialService.checkIdentityAndRole(auth.getUsername(), auth.getPassword());
		} catch (SQLException | BadCredentialException e) {
			logger.error(e.getMessage());
		}
		return Response
				.status(201)
				.entity(role)
				.build();
	}
}
