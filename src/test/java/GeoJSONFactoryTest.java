import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.geojson.Geometry;
import org.wololo.geojson.Point;
import org.wololo.jts2geojson.GeoJSONReader;


public class GeoJSONFactoryTest {
	@Test
	public void test() {
		Geometry geometry = new Point(new  double[] {1, 1});
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("test",1);
		Feature feature = new Feature(geometry, properties);
		System.out.println(feature);
		
		FeatureCollection fc = new FeatureCollection(new Feature[] {feature, feature});
		System.out.println(fc);
		
		System.out.println(new GeoJSONReader().read(fc.features[0].geometry));
		
		GeoJSON a = GeoJSONFactory.create(feature.toString());
		System.out.println(a);
	}
}
