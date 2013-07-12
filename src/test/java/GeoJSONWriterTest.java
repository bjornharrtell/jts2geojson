import java.io.IOException;

import org.junit.Test;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Coordinate;
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
		Point point = new GeometryFactory().createPoint(new Coordinate(1, 1));
		
		GeoJSON geoJSON = GeoJSONWriter.fromJts(point);
		
		System.out.println(geoJSON);
		
		
		LineString lineString = new GeometryFactory().createLineString(new Coordinate[] { 
				new Coordinate(1, 1),
				new Coordinate(1, 2),
				new Coordinate(2, 2),
				new Coordinate(1, 1)});
		
		geoJSON = GeoJSONWriter.fromJts(lineString);
		
		System.out.println(geoJSON);
		
		
		Polygon polygon = new GeometryFactory().createPolygon(lineString.getCoordinates());
		
		geoJSON = GeoJSONWriter.fromJts(polygon);
		
		System.out.println(geoJSON);
		
		
		MultiPoint multiPoint = new GeometryFactory().createMultiPoint(lineString.getCoordinates());
		
		geoJSON = GeoJSONWriter.fromJts(multiPoint);
		
		System.out.println(geoJSON);
		
		
		MultiLineString multiLineString = new GeometryFactory().createMultiLineString(new LineString[] {lineString, lineString});
		
		geoJSON = GeoJSONWriter.fromJts(multiLineString);
		
		System.out.println(geoJSON);
		
		
		MultiPolygon multiPolygon = new GeometryFactory().createMultiPolygon(new Polygon[] {polygon, polygon});
		
		geoJSON = GeoJSONWriter.fromJts(multiPolygon);
		
		System.out.println(geoJSON);
		
		
	}

}
