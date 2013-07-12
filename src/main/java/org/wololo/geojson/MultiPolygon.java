package org.wololo.geojson;

public class MultiPolygon extends Geometry {
	public double[][][][] coordinates;
	
	MultiPolygon() { }
	
	public MultiPolygon(double [][][][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
