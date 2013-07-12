package org.wololo.geojson;

public class MultiPolygon extends GeoJSON {
	public double[][][][] coordinates;
	
	public MultiPolygon(double [][][][] coordinates) {
		super();
		this.coordinates = coordinates;
	}
}
