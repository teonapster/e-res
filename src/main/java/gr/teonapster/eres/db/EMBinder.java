package gr.teonapster.eres.db;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

public class EMBinder extends AbstractBinder {
	@Override
	protected void configure() {
		bindFactory(EMBinder.EMFF.class).to(EntityManagerFactory.class)
				.proxy(false).in(Singleton.class);
		bindFactory(EMBinder.EMF.class).to(EntityManager.class).proxy(true)
				.proxyForSameScope(false).in(RequestScoped.class);
	}

	public static class EMFF implements Factory<EntityManagerFactory> {
		@Override
		public EntityManagerFactory provide() {
			return ELContextListener.emf;
		}

		@Override
		public void dispose(EntityManagerFactory instance) {
			// nothing to do here.
		}
	}

	public static class EMF implements Factory<EntityManager> {
		@Override
		public EntityManager provide() {
			return ELRequestListener.getCurrentContext().getEntityManager();
		}

		@Override
		public void dispose(EntityManager instance) {
			// nothing to do here either.
		}
	}
}