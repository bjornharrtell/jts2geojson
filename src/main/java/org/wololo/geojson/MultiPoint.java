package org.wololo.geojson;

public class MultiPoint extends Geometry {
	public double[][] coordinates;
	
	MultiPoint() { }
	
	public MultiPoint(double [][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
