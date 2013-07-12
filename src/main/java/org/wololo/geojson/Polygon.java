package org.wololo.geojson;

public class Polygon extends GeoJSON {
	public double[][][] coordinates;
	
	public Polygon(double [][][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
