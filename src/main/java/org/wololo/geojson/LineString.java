package org.wololo.geojson;

public class LineString extends Geometry {
	public double[][] coordinates;
	
	LineString() { }
	
	public LineString(double [][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
