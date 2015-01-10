package gr.teonapster.eres.db;

import java.util.ArrayList;
import java.util.List;

import gr.teonapster.eres.om.House;
import gr.teonapster.eres.om.Reservation;
import gr.teonapster.eres.om.User;
import gr.teonapster.eres.om.UserType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ELContextListener implements ServletContextListener {
	public static EntityManagerFactory emf;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		emf.close();
		System.out.println("CLOSE EMF");
		emf = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		emf = Persistence.createEntityManagerFactory("eres");
		initDB();
	}

	private void initDB() {
		EntityManager em = emf.createEntityManager();
		try {
			if (em.createQuery("SELECT u FROM User u", User.class)
					.setMaxResults(1).getSingleResult() != null)
				return;
		} catch (NoResultException e) {
			// ignore, empty DB.
		}
		em.getTransaction().begin();


		User u = new User();
		u.setEmail("admin@admin");
		u.setPassword("admin");
		u.setType(UserType.GLOBAL_ADMIN);

		em.persist(u);
		
		User u2 = new User();
		u2.setEmail("client@client");
		u2.setPassword("client");
		u2.setType(UserType.USER);

		em.persist(u2);
		
		House h1 = new House();
		h1.setName("Αρχονταρίκι");
		h1.setLocation("Μεταμόρφωση");
		h1.setRooms(4);
		em.persist(h1);
		
		House h2 = new House();
		h2.setName("Λοξάνδρα");
		h2.setLocation("Μεταμόρφωση");
		h2.setRooms(2);
		em.persist(h2);

		/*Reservation r1 = new Reservation();
		List<House> hl = new ArrayList<House>();
		hl.add(h1);
		u.getReservations().add(r1);
		h1.getReservations().add(r1);
		r1.setHouses(hl);
		r1.setUser(u2);
		em.persist(r1);*/
		
		em.flush();
		em.getTransaction().commit();
		em.close();
	}
}
