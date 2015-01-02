package gr.teonapster.eres.rest;

import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.hk2.api.ServiceLocator;

public class LocatorSetFeature implements Feature {

	private final ServiceLocator scopedLocator;
	private static ServiceLocator locator;

	@Inject
	private LocatorSetFeature(ServiceLocator scopedLocator) {
		this.scopedLocator = scopedLocator;
	}

	@Override
	public boolean configure(FeatureContext context) {
		locator = scopedLocator; // this would set our member locator variable
		return true;
	}

	public static ServiceLocator getLocator() {
		return locator;
	}

	public static <T> T init(T t) {
		if (locator == null)
			throw new IllegalStateException();
		getLocator().inject(t);
		return t;
	}

}
