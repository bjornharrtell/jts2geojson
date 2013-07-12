package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MultiPoint extends Geometry {
	public final double[][] coordinates;
	
	@JsonCreator
	public MultiPoint(@JsonProperty("coordinates") double [][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
