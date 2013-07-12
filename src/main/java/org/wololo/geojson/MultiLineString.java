package org.wololo.geojson;

public class MultiLineString extends GeoJSON {
	public double[][][] coordinates;
	
	public MultiLineString(double [][][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
