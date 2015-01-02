package gr.teonapster.eres.rest;

import gr.teonapster.eres.om.User;
import gr.teonapster.eres.om.UserType;
import gr.teonapster.eres.services.SecurityService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Path("/userAccess")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class AccessControl {
	@XmlRootElement
	public static class UserStatus {
		public UserStatus(User u) {
			user = u.getEmail();
			uid = u.getId();
			lastLogin = u.getLastLogin();
			siteAdmin = u.getType() == UserType.GLOBAL_ADMIN;
			orgAdmin = u.getType() == UserType.ORGANIZATION_ADMIN;
		}

		public UserStatus() {
		}

		@XmlAttribute
		public String user;

		@XmlAttribute
		public int uid = -1;

		@XmlAttribute
		public Date lastLogin;

		@XmlAttribute
		public UserType type;

		@XmlAttribute
		public boolean siteAdmin = false;

		@XmlAttribute
		public String organization = null;

		@XmlAttribute
		public int orgid = -1;

		@XmlAttribute
		public boolean orgAdmin = false;

	}

	@Inject
	protected SecurityService ss;

//	@Inject
//	protected LogService ls;

	@Path("/status")
	@GET
	public UserStatus getStatus() {
		User u = ss.getLoggedInUser();
		if (u == null) {
			return new UserStatus();
		}
		ss.stampUser();
		return new UserStatus(u);
	}

	@Path("/login")
	@POST
	public UserStatus loginUser(@QueryParam("u") String user,
			@QueryParam("p") String password) {
		if (user == null || password == null)
			throw new WebApplicationException("Invalid Credentials",
					Status.UNAUTHORIZED);
		User u = ss.authenticateUser(user, password);
		if (u == null)
			throw new WebApplicationException("Invalid Credentials",
					Status.UNAUTHORIZED);
		UserStatus userStatus = new UserStatus(u);
		ss.login(u);
		//ls.log(LogLine.For.LOGIN());
		return userStatus;
	}

	@Path("/logout")
	@POST
	public UserStatus logoutUser() {
		//ls.log(LogLine.For.LOGOUT());
		ss.logout();
		return new UserStatus();
	}
}
