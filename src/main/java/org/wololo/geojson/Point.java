package org.wololo.geojson;

public class Point extends Geometry {
	public double[] coordinates;
	
	Point() { }
	
	public Point(double [] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
