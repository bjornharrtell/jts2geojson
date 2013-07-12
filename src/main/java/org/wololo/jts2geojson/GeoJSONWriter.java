package org.wololo.jts2geojson;

import org.wololo.geojson.GeoJSON;
import com.vividsolutions.jts.geom.*;

public class GeoJSONWriter {

	public String write(Geometry geometry) {
		Class<? extends Geometry> c = geometry.getClass();
		if (c.equals(Point.class)) {
			return convert((Point) geometry).toString();
		} else if (c.equals(LineString.class)) {
			return convert((LineString) geometry).toString();
		} else if (c.equals(Polygon.class)) {
			return convert((Polygon) geometry).toString();
		} else if (c.equals(MultiPoint.class)) {
			return convert((MultiPoint) geometry).toString();
		} else if (c.equals(MultiLineString.class)) {
			return convert((MultiLineString) geometry).toString();
		} else if (c.equals(MultiPolygon.class)) {
			return convert((MultiPolygon) geometry).toString();
		} else {
			throw new UnsupportedOperationException();
		}
	}

	GeoJSON convert(Point point) {
		GeoJSON json = new org.wololo.geojson.Point(
				convert(point.getCoordinate()));
		return json;
	}

	GeoJSON convert(MultiPoint multiPoint) {
		GeoJSON json = new org.wololo.geojson.MultiPoint(
				convert(multiPoint.getCoordinates()));
		return json;
	}

	GeoJSON convert(LineString lineString) {
		GeoJSON json = new org.wololo.geojson.LineString(
				convert(lineString.getCoordinates()));
		return json;
	}

	GeoJSON convert(MultiLineString multiLineString) {
		int size = multiLineString.getNumGeometries();
		double[][][] lineStrings = new double[size][][];
		for (int i = 0; i < size; i++) {
			lineStrings[i] = convert(multiLineString.getGeometryN(i)
					.getCoordinates());
		}
		GeoJSON json = new org.wololo.geojson.MultiLineString(lineStrings);
		return json;
	}

	GeoJSON convert(Polygon polygon) {
		int size = polygon.getNumInteriorRing() + 1;
		double[][][] rings = new double[size][][];
		rings[0] = convert(polygon.getExteriorRing().getCoordinates());
		for (int i = 0; i < size - 1; i++) {
			rings[i + 1] = convert(polygon.getInteriorRingN(i).getCoordinates());
		}
		GeoJSON json = new org.wololo.geojson.Polygon(rings);
		return json;
	}

	GeoJSON convert(MultiPolygon multiPolygon) {
		int size = multiPolygon.getNumGeometries();
		double[][][][] polygons = new double[size][][][];
		for (int i = 0; i < size; i++) {
			polygons[i] = ((org.wololo.geojson.Polygon) convert((Polygon) multiPolygon
					.getGeometryN(i))).coordinates;
		}
		GeoJSON json = new org.wololo.geojson.MultiPolygon(polygons);
		return json;
	}

	double[] convert(Coordinate coordinate) {
		return new double[] { coordinate.x, coordinate.y };
	}

	double[][] convert(Coordinate[] coordinates) {
		double[][] array = new double[coordinates.length][];
		for (int i = 0; i < coordinates.length; i++) {
			array[i] = convert(coordinates[i]);
		}
		return array;
	}
}
