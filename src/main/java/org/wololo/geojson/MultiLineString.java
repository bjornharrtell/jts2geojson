package org.wololo.geojson;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class MultiLineString extends Geometry {
	private final double[][][] coordinates;
	
	@JsonCreator
	public MultiLineString(@JsonProperty("coordinates") double [][][] coordinates) {
		super();
		this.coordinates = coordinates;
	}

	public double[][][] getCoordinates() {
		return coordinates;
	}
}
