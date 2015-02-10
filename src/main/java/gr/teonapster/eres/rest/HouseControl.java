package gr.teonapster.eres.rest;

import java.util.Collection;
import java.util.List;

import gr.teonapster.eres.om.House;
import gr.teonapster.eres.om.User;
import gr.teonapster.eres.services.SecurityService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
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

@Path("/house")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class HouseControl {
	@Inject
	protected EntityManager em;

	@Inject
	protected SecurityService ss;

	
	@DELETE
	@Path("/{h}")
	public void deleteHouse(@PathParam("h") @Identified House h) {
		ss.mustBeLoggedIn();
		ss.mustBeSiteAdmin();
		em.remove(h);
	}

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public House createHouse(House newHouse){
		if(newHouse==null)
				throw new WebApplicationException(Status.NOT_FOUND);
		ss.mustBeLoggedIn();
		ss.mustBeSiteAdmin();
		em.persist(newHouse);
		em.flush();
		return newHouse;
	}
	
	@POST
	@Path("/{h}")
	@Consumes(MediaType.APPLICATION_JSON)
	public House saveHouse(@PathParam("h") @Identified House h, House changes) {
		if (h == null)
			throw new WebApplicationException(Status.NOT_FOUND);
		ss.mustBeLoggedIn();
		boolean self = ss.getLoggedInUser().getId() == h.getId();
		if (!self)
			ss.mustBeSiteAdmin();
		if (h.getId() != changes.getId())
			throw new WebApplicationException(Status.BAD_REQUEST);
		if (self && !h.getName().equals(changes.getName()))
			throw new WebApplicationException(Status.FORBIDDEN);
		em.merge(changes);
		return changes;
	}

	
	@GET
	@Path("/list")
	public Collection<House> getHouses() {
		ss.mustBeLoggedIn();
		 Query query = em.createQuery("SELECT h FROM House h");
		    return (Collection<House>) query.getResultList();
	}
	
	@GET
	@Path("{h}/view")
	public House getHouse(@PathParam("h") @Identified House h){
		ss.mustBeLoggedIn();
		return h;
	}
}

