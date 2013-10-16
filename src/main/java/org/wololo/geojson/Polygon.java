package org.wololo.geojson;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class Polygon extends Geometry {
	private final double[][][] coordinates;
	
	@JsonCreator
	public Polygon(@JsonProperty("coordinates") double [][][] coordinates) {
		super();
		this.coordinates = coordinates;
	}

	public double[][][] getCoordinates() {
		return coordinates;
	}
}
