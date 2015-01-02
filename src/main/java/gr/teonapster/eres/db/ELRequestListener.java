package gr.teonapster.eres.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebListener("JPA & Session Setup")
public class ELRequestListener implements ServletRequestListener {
	public static class CurrentContext {
		private EntityManager instance;
		private HttpSession session;

		public CurrentContext(HttpSession sess) {
			this.session = sess;
		}

		public EntityManager getEntityManager() {
			if (instance != null) {
				return instance;
			}
			instance = ELContextListener.emf.createEntityManager();
			instance.getTransaction().begin();
			return instance;
		}

		public HttpSession getCurrentSession() {
			return session;
		}
	}

	private static ThreadLocal<CurrentContext> ems = new ThreadLocal<CurrentContext>();

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		closeCurrent();
		ems.remove();
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		ems.set(new CurrentContext(((HttpServletRequest) arg0
				.getServletRequest()).getSession()));
	}

	@FunctionalInterface
	public interface Generator<T, E extends Throwable> {
		public T generate() throws E;
	}

	public static <T, E extends Throwable> T withContext(HttpSession s,
			Generator<T, E> r) throws E {
		CurrentContext currentContext = ems.get();
		try {
			ems.set(new CurrentContext(s));
			return r.generate();
		} finally {
			closeCurrent();
			ems.remove();
			if (currentContext != null)
				ems.set(currentContext);
		}
	}

	private static void closeCurrent() {
		CurrentContext instance = ems.get();
		if (instance == null || instance.getEntityManager() == null)
			return;
		try {
			EntityTransaction transaction = instance.getEntityManager()
					.getTransaction();
			if (transaction.isActive()) {
				if (transaction.getRollbackOnly()) {
					transaction.rollback();
				} else {
					transaction.commit();
				}
			}
		} finally {
			ems.remove();
			instance.getEntityManager().close();
		}
	}

	public static CurrentContext getCurrentContext() {
		CurrentContext context = ems.get();
		if (context == null)
			throw new IllegalStateException(
					"Must not get EntityManager outside of request");
		return context;
	}
}
