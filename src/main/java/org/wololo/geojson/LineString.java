package org.wololo.geojson;

public class LineString extends GeoJSON {
	public double[][] coordinates;
	
	public LineString(double [][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
