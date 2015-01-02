package gr.teonapster.eres.rest;

import gr.teonapster.eres.db.*;
import gr.teonapster.eres.services.EresServices;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("resources")
public class EresApplication extends ResourceConfig {
	public EresApplication() {
		String pack = getClass().getPackage().getName();
		pack.substring(0, pack.lastIndexOf('.') - 1);

		packages(pack);
		registerInstances(new EMBinder(),new EresServices());
		register(LocatorSetFeature.class);
	}
}