package org.wololo.jts2geojson;

import org.wololo.geojson.*;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.PrecisionModel;

public class GeoJSONReader {
	final static GeometryFactory factory = new GeometryFactory(
			new PrecisionModel(PrecisionModel.FLOATING));

	public Geometry read(String json) {
		GeoJSON geoJSON = GeoJSONFactory.create(json);

		if (geoJSON instanceof Point) {
			return convert((Point) geoJSON);
		} else if (geoJSON instanceof LineString) {
			return convert((LineString) geoJSON);
		} else if (geoJSON instanceof Polygon) {
			return convert((Polygon) geoJSON);
		} else if (geoJSON instanceof MultiPoint) {
			return convert((MultiPoint) geoJSON);
		} else if (geoJSON instanceof MultiLineString) {
			return convert((MultiLineString) geoJSON);
		} else if (geoJSON instanceof MultiPolygon) {
			return convert((MultiPolygon) geoJSON);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	Geometry convert(Point point) {
		return factory.createPoint(convert(point.coordinates));
	}

	Geometry convert(MultiPoint multiPoint) {
		return factory.createMultiPoint(convert(multiPoint.coordinates));
	}

	Geometry convert(LineString lineString) {
		return factory.createLineString(convert(lineString.coordinates));
	}

	Geometry convert(MultiLineString multiLineString) {
		int size = multiLineString.coordinates.length;
		com.vividsolutions.jts.geom.LineString[] lineStrings = new com.vividsolutions.jts.geom.LineString[size];
		for (int i = 0; i < size; i++) {
			lineStrings[i] = factory
					.createLineString(convert(multiLineString.coordinates[i]));
		}
		return factory.createMultiLineString(lineStrings);
	}

	Geometry convert(Polygon polygon) {
		return convertToPolygon(polygon.coordinates);
	}

	com.vividsolutions.jts.geom.Polygon convertToPolygon(
			double[][][] coordinates) {
		LinearRing shell = factory.createLinearRing(convert(coordinates[0]));

		if (coordinates.length > 1) {
			int size = coordinates.length - 1;
			LinearRing[] holes = new LinearRing[size];
			for (int i = 0; i < size; i++) {
				holes[i] = factory
						.createLinearRing(convert(coordinates[i + 1]));
			}
			return factory.createPolygon(shell, holes);
		} else {
			return factory.createPolygon(shell);
		}
	}

	Geometry convert(MultiPolygon multiPolygon) {
		int size = multiPolygon.coordinates.length;
		com.vividsolutions.jts.geom.Polygon[] polygons = new com.vividsolutions.jts.geom.Polygon[size];
		for (int i = 0; i < size; i++) {
			polygons[i] = convertToPolygon(multiPolygon.coordinates[i]);
		}
		return factory.createMultiPolygon(polygons);
	}

	Coordinate convert(double[] c) {
		return new Coordinate(c[0], c[1]);
	}

	Coordinate[] convert(double[][] ca) {
		Coordinate[] coordinates = new Coordinate[ca.length];
		for (int i = 0; i < ca.length; i++) {
			coordinates[i] = convert(ca[i]);
		}
		return coordinates;
	}
}
