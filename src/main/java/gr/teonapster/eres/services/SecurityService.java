package gr.teonapster.eres.services;

import gr.teonapster.eres.db.ELRequestListener;
import gr.teonapster.eres.om.User;
import gr.teonapster.eres.om.UserType;

import java.util.Date;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.jvnet.hk2.annotations.Service;


@Service
@Singleton
public class SecurityService {
	private static final String SESSION_USER = SecurityService.class.getName()
			+ ".USER";
	private static final String REQEST_USER = SESSION_USER;
	private static final String SESSION_TIMESTAMP = SecurityService.class
			.getName() + ".TIMESTAMP";

	private static final long SESSION_DEFAULT_DURATION = 20 * 60 * 1000;

	@Inject
	private EntityManager em;

	@Inject
	private HttpServletRequest request;


	public User getLoggedInUser() {
		Object obj = request.getAttribute(REQEST_USER);
		if (obj != null && obj instanceof User)
			return (User) obj;
		User user = getLoggedInUser(request.getSession());
		request.setAttribute(REQEST_USER, user);
		return user;
	}

	public User getLoggedInUser(HttpSession session) {
		if (session == null)
			return null;
		Object obj = session.getAttribute(SESSION_USER);
		if (obj == null)
			return null;
		if (!(obj instanceof Integer))
			return null;
		Integer i = (Integer) obj;
		obj = session.getAttribute(SESSION_TIMESTAMP);
		if (obj == null || !(obj instanceof Long))
			return null;
		if (((Long) obj) + SESSION_DEFAULT_DURATION < System
				.currentTimeMillis())
			return null;
		User user = ELRequestListener.getCurrentContext().getEntityManager()
				.find(User.class, i);
		return user;
	}

	public void stampUser() {
		HttpSession session = request.getSession(false);
		if (session == null)
			return;
		session.setAttribute(SESSION_TIMESTAMP, System.currentTimeMillis());
	}

	public void logout() {
		HttpSession session = request.getSession(false);
		if (session == null)
			return;
		session.removeAttribute(SESSION_USER);
		session.removeAttribute(SESSION_TIMESTAMP);
		session.invalidate();
	}

	public void login(User u) {
		HttpSession session = request.getSession(true);
		stampUser();
		session.setAttribute(SESSION_USER, u.getId());
		u.setLastLogin(new Date());
		request.setAttribute(REQEST_USER, u);
	}

	public User authenticateUser(String name, String password) {
		try {
			User u = em
					.createQuery(
							"SELECT u FROM User u WHERE u.email = :name AND u.password = :pass",
							User.class).setParameter("name", name)
					.setParameter("pass", password).setMaxResults(1)
					.getSingleResult();
			return u;
		} catch (NoResultException e) {
			return null;
		}
	}

	public SecurityService mustBeLoggedIn() {
		if (getLoggedInUser() == null)
			throw new WebApplicationException(Status.UNAUTHORIZED);
		return this;
	}

	public SecurityService mustBeSiteAdmin() {
		User u = getLoggedInUser();
		if (u == null)
			throw new WebApplicationException(Status.UNAUTHORIZED);
		if (u.getType() != UserType.GLOBAL_ADMIN)
			throw new WebApplicationException(Status.FORBIDDEN);
		return this;
	}

	public SecurityService mustBeUserAdmin(User target) {
		if (target == null)
			throw new WebApplicationException(Status.NOT_FOUND);
		//return mustBeOrgAdmin(target.getOrganization());
		//Check wether user is admin
		return null;
	}
}
