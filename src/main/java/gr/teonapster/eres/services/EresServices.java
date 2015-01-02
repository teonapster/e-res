package gr.teonapster.eres.services;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class EresServices extends AbstractBinder {
	private static final Class<?>[] classes = {SecurityService.class};

	@Override
	protected void configure() {
		for (Class<?> klass : classes) {
			bind(klass).to(klass).in(Singleton.class);
		}
	}
}
