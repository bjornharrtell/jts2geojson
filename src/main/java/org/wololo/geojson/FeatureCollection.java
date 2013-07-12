package org.wololo.geojson;

public class FeatureCollection extends GeoJSON {
	public Feature[] features;
	
	public FeatureCollection(Feature[] features) {
		super();
		this.features = features;
	}
}
