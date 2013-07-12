package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LineString extends Geometry {
	public final double[][] coordinates;
	
	@JsonCreator
	public LineString(@JsonProperty("coordinates") double [][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
