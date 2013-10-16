package org.wololo.geojson;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

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
