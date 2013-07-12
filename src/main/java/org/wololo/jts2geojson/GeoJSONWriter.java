package org.wololo.jts2geojson;

import org.wololo.geojson.GeoJSON;

import com.vividsolutions.jts.geom.*;

public class GeoJSONWriter {
	
	static public GeoJSON fromJts(Geometry geometry) {
		
		if (geometry.getClass().equals(Point.class)) {
			return convert((Point) geometry);
		} else if (geometry.getClass().equals(LineString.class)) {
			return convert((LineString) geometry);
		} else if (geometry.getClass().equals(Polygon.class)) {
			return convert((Polygon) geometry);
		} else if (geometry.getClass().equals(MultiPoint.class)) {
			return convert((MultiPoint) geometry);
		} else if (geometry.getClass().equals(MultiLineString.class)) {
			return convert((MultiLineString) geometry);
		} else if (geometry.getClass().equals(MultiPolygon.class)) {
			return convert((MultiPolygon) geometry);
		} else {
			throw new RuntimeException("Unknown JTS Geometry type");
		}
	}
		
	static GeoJSON convert(Point point) {
		GeoJSON json = new org.wololo.geojson.Point(convert(point.getCoordinate()));
		return json;
	}
	static GeoJSON convert(MultiPoint multiPoint) {
		GeoJSON json = new org.wololo.geojson.MultiPoint(convert(multiPoint.getCoordinates()));
		return json;
	}
	
	static GeoJSON convert(LineString lineString) {
		GeoJSON json = new org.wololo.geojson.LineString(convert(lineString.getCoordinates()));
		return json;
	}
	
	static GeoJSON convert(MultiLineString multiLineString) {
		int size = multiLineString.getNumGeometries();
		double [][][] lineStrings = new double[size][][];
		for (int i=0; i<size; i++) {
			lineStrings[i] = convert(multiLineString.getGeometryN(i).getCoordinates());
		}
		GeoJSON json = new org.wololo.geojson.MultiLineString(lineStrings);
		return json;
	}
	
	static GeoJSON convert(Polygon polygon) {
		int size = polygon.getNumInteriorRing()+1;
		double [][][] rings = new double[size][][];
		rings[0] = convert(polygon.getExteriorRing().getCoordinates());
		for (int i=0; i<size-1; i++) {
			rings[i+1] = convert(polygon.getInteriorRingN(i).getCoordinates());
		}
		GeoJSON json = new org.wololo.geojson.Polygon(rings);
		return json;
	}
	
	static GeoJSON convert(MultiPolygon multiPolygon) {
		int size = multiPolygon.getNumGeometries();
		double [][][][] polygons = new double[size][][][];
		for (int i=0; i<size; i++) {
			polygons[i] = ((org.wololo.geojson.Polygon)convert((Polygon)multiPolygon.getGeometryN(i))).coordinates;
		}
		GeoJSON json = new org.wololo.geojson.MultiPolygon(polygons);
		return json;
	}
	
	static double[] convert(Coordinate coordinate) {
		return new double[] {coordinate.x, coordinate.y};
	}
	
	static double[][] convert(Coordinate[] coordinates) {
		double[][] array = new double[coordinates.length][];
		for (int i=0; i<coordinates.length; i++) {
			array[i] = convert(coordinates[i]);
		}
		return array;
	}
}
