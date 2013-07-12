package org.wololo.geojson;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Feature extends GeoJSON {
	public final Geometry geometry;
	public final Map<String, Object> properties;
	
	@JsonCreator
	public Feature(@JsonProperty("geometry") Geometry geometry, @JsonProperty("properties") Map<String, Object> properties) {
		super();
		this.geometry = geometry;
		this.properties = properties;
	}
}
