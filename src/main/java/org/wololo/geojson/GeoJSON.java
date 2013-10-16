package org.wololo.geojson;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public abstract class GeoJSON {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@JsonProperty("type")
	private String type;
	
	@JsonCreator
	public GeoJSON() {
		setType(getClass().getSimpleName());
	}
	
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonGenerationException e) {
			return "Unhandled exception occured when serializing this instance";
		} catch (JsonMappingException e) {
			return "Unhandled exception occured when serializing this instance";
		} catch (IOException e) {
			return "Unhandled exception occured when serializing this instance";
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
