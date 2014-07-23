package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Point extends Geometry {
	private final double[] coordinates;
	
	@JsonCreator
	public Point(@JsonProperty("coordinates") double [] coordinates) {
		super();
		this.coordinates = coordinates;
	}

	public double[] getCoordinates() {
		return coordinates;
	}
}
