package org.wololo.geojson;

import java.util.Map;

public class Feature extends GeoJSON {
	public GeoJSON geometry;
	public Map<String, Object> properties;
	
	Feature() {
		super();
	}
}
