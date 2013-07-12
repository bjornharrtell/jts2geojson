package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Point extends Geometry {
	public final double[] coordinates;
	
	@JsonCreator
	public Point(@JsonProperty("coordinates") double [] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
