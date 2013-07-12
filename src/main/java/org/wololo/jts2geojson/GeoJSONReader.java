package org.wololo.jts2geojson;

import org.wololo.geojson.*;

import com.vividsolutions.jts.geom.Geometry;


public class GeoJSONReader {
	
	static public Geometry fromGeoJSON(String json) {
		
		// TODO: identify json type, use jackson deserialization
		
		throw new UnsupportedOperationException();
	}
		
	static Geometry convert(Point point) {
		throw new UnsupportedOperationException();
	}
	static Geometry convert(MultiPoint multiPoint) {
		throw new UnsupportedOperationException();
	}
	
	static Geometry convert(LineString lineString) {
		throw new UnsupportedOperationException();
	}
	
	static Geometry convert(MultiLineString multiLineString) {
		throw new UnsupportedOperationException();
	}
	
	static Geometry convert(Polygon polygon) {
		throw new UnsupportedOperationException();
	}
	
	static Geometry convert(MultiPolygon multiPolygon) {
		throw new UnsupportedOperationException();
	}
}
