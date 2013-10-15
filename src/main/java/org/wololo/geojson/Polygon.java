package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
