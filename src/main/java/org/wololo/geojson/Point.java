package org.wololo.geojson;

public class Point extends GeoJSON {
	public double[] coordinates;
	
	public Point() {
		super();
	}
	
	public Point(double [] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
