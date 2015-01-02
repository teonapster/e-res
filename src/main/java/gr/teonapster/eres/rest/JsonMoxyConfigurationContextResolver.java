package gr.teonapster.eres.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;

@Provider
public class JsonMoxyConfigurationContextResolver implements
		ContextResolver<MoxyJsonConfig> {

	private final MoxyJsonConfig config;

	public JsonMoxyConfigurationContextResolver() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(MarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME, Boolean.TRUE);

		config = new MoxyJsonConfig();
		config.setFormattedOutput(true);
		config.setMarshalEmptyCollections(true);
		config.setMarshallerProperties(map);
		config.setUnmarshallerProperties(map);
		config.setAttributePrefix("");
	}

	@Override
	public MoxyJsonConfig getContext(Class<?> objectType) {
		return config;
	}
}