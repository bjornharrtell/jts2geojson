package org.wololo.geojson;

public class MultiPoint extends GeoJSON {
	public double[][] coordinates;
	
	public MultiPoint(double [][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
