package org.wololo.geojson;

public class MultiLineString extends Geometry {
	public double[][][] coordinates;
	
	MultiLineString() { }
	
	public MultiLineString(double [][][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
