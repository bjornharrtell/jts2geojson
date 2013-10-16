package org.wololo.geojson;

import org.codehaus.jackson.annotate.JsonCreator;

public abstract class Geometry extends GeoJSON {
	@JsonCreator
	public Geometry() {
		super();
	}
}
