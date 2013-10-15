package org.wololo.jts2geojson;

import java.util.Map;

import com.vividsolutions.jts.geom.Geometry;

public class Feature {
	private final Geometry geometry;
	private final Map<String, Object> properties;
	
	public Feature(Geometry geometry, Map<String, Object> properties) {
		this.geometry = geometry;
		this.properties = properties;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
}
