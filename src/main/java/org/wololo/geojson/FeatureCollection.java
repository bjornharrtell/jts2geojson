package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureCollection extends GeoJSON {
	public final Feature[] features;
	
	@JsonCreator
	public FeatureCollection(@JsonProperty("features") Feature[] features) {
		super();
		this.features = features;
	}
}
