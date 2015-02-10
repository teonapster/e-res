package gr.teonapster.eres.rest;

import gr.teonapster.eres.om.Reservation;
import gr.teonapster.eres.om.User;
import gr.teonapster.eres.om.UserType;
import gr.teonapster.eres.services.SecurityService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

@Path("/user")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class UserControl {
	@Inject
	protected EntityManager em;

	@Inject
	protected SecurityService ss;

	
	@DELETE
	@Path("/{u}")
	public void deleteUser(@PathParam("u") @Identified User u) {
		ss.mustBeUserAdmin(u);
		em.remove(u);
	}

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public User createUser(User newUser){
		if(newUser==null)
				throw new WebApplicationException(Status.NOT_FOUND);
		ss.mustBeLoggedIn();
		ss.mustBeSiteAdmin();
		em.persist(newUser);
		em.flush();
		return newUser;
	}
	
	@POST
	@Path("/{u}")
	@Consumes(MediaType.APPLICATION_JSON)
	public User saveUser(@PathParam("u") @Identified User u, User changes) {
		if (u == null)
			throw new WebApplicationException(Status.NOT_FOUND);
		ss.mustBeLoggedIn();
		boolean self = ss.getLoggedInUser().getId() == u.getId();
		if (!self)
			ss.mustBeUserAdmin(u);
		if (u.getId() != changes.getId())
			throw new WebApplicationException(Status.BAD_REQUEST);
		if (self && !u.getEmail().equals(changes.getEmail()))
			throw new WebApplicationException(Status.FORBIDDEN);

		if (changes.getPassword() != null && changes.getPassword().length() > 0)
			u.setPassword(changes.getPassword());
		if (ss.getLoggedInUser().getType() != UserType.USER) {
			u.setEmail(changes.getEmail());
			u.setType(changes.getType());
		}
		
		em.flush();
		return u;
	}
	
	@POST
	@Path("/{u}/reservation/{r}")
	@Consumes(MediaType.APPLICATION_JSON)
	public User saveUser(@PathParam("u") @Identified User u, 
					     @PathParam("r") @Identified Reservation r) {
		if(u==null||r==null)
			throw new WebApplicationException(Status.NOT_FOUND);
		ss.mustBeLoggedIn();
		em.persist(r);
		u.getReservation().add(r);
		em.flush();
		return u;
	}

	@GET
	@Path("/{u}")
	public User getUser(@PathParam("u") @Identified User u) {
		ss.mustBeUserAdmin(u);
		return u;
	}
	
}
