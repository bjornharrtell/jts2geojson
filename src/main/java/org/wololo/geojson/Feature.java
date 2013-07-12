package org.wololo.geojson;

import java.util.Map;

public class Feature extends GeoJSON {
	public Geometry geometry;
	public Map<String, Object> properties;
	
	Feature() {
		super();
	}
	
	public Feature(Geometry geometry, Map<String, Object> properties) {
		super();
		this.geometry = geometry;
		this.properties = properties;
	}
}
