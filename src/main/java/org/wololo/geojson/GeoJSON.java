package org.wololo.geojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class GeoJSON {
	static final ObjectMapper mapper = new ObjectMapper();
	
	public String type;
	
	public GeoJSON() {
		type = getClass().getSimpleName();
	}
	
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
