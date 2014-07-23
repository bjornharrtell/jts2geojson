package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureCollection extends GeoJSON {
	private final Feature[] features;
	
	@JsonCreator
	public FeatureCollection(@JsonProperty("features") Feature[] features) {
		super();
		this.features = features;
	}

	public Feature[] getFeatures() {
		return features;
	}
}
