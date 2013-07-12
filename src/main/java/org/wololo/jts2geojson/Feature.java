package org.wololo.jts2geojson;

import java.util.Map;

import com.vividsolutions.jts.geom.Geometry;

public class Feature {
	final Geometry geometry;
	final Map<String, Object> properties;
	
	public Feature(Geometry geometry, Map<String, Object> properties) {
		this.geometry = geometry;
		this.properties = properties;
	}
}
