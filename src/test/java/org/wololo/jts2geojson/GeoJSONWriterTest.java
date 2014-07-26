package org.wololo.jts2geojson;
import java.io.IOException;

import org.junit.Test;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class GeoJSONWriterTest {

	@Test
	public void test() throws IOException {
		GeoJSONWriter writer = new GeoJSONWriter();
		
		Point point = new GeometryFactory().createPoint(new Coordinate(1, 1));
		org.wololo.geojson.Geometry json = writer.write(point);
		System.out.println(json);
		
		GeoJSONReader reader = new GeoJSONReader();
		Geometry geometry = reader.read(json);
		System.out.println(geometry);
		
		LineString lineString = new GeometryFactory().createLineString(new Coordinate[] { 
				new Coordinate(1, 1),
				new Coordinate(1, 2),
				new Coordinate(2, 2),
				new Coordinate(1, 1)});
		json = writer.write(lineString);
		System.out.println(json);
		
		Polygon polygon = new GeometryFactory().createPolygon(lineString.getCoordinates());
		json = writer.write(polygon);
		System.out.println(json);
		
		MultiPoint multiPoint = new GeometryFactory().createMultiPoint(lineString.getCoordinates());
		json = writer.write(multiPoint);
		System.out.println(json);
		
		
		MultiLineString multiLineString = new GeometryFactory().createMultiLineString(new LineString[] {lineString, lineString});
		json = writer.write(multiLineString);
		System.out.println(json);
		
		MultiPolygon multiPolygon = new GeometryFactory().createMultiPolygon(new Polygon[] {polygon, polygon});
		json = writer.write(multiPolygon);
		System.out.println(json);
		
		geometry = reader.read(json);
		System.out.println(geometry);
		
		Feature feature1 = new Feature(json, null);
		Feature feature2 = new Feature(json, null);
		
		FeatureCollection featureCollection = new FeatureCollection(new Feature[] { feature1, feature2 } );
		String fcstr = featureCollection.toString();
		System.out.println(fcstr);
		
		FeatureCollection featureCollection2 = (FeatureCollection) GeoJSONFactory.create(fcstr);
	}

}
