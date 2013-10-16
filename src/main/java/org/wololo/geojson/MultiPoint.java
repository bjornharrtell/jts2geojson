package org.wololo.geojson;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

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
