package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class Geometry extends GeoJSON {
	@JsonCreator
	public Geometry() {
		super();
	}
}
