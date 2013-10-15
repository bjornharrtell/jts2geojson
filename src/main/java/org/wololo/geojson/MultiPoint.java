package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MultiPoint extends Geometry {
	private final double[][] coordinates;
	
	@JsonCreator
	public MultiPoint(@JsonProperty("coordinates") double [][] coordinates) {
		super();
		this.coordinates = coordinates;
	}

	public double[][] getCoordinates() {
		return coordinates;
	}
}
