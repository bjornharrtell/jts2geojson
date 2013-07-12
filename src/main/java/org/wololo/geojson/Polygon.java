package org.wololo.geojson;

public class Polygon extends Geometry {
	public double[][][] coordinates;
	
	Polygon() {	}
	
	public Polygon(double [][][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
