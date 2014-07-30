package org.wololo.jts2geojson

import org.scalatest.WordSpec
import org.wololo.geojson.GeoJSONFactory
import org.wololo.geojson.Point
import java.util.HashMap
import org.wololo.geojson.Feature
import org.wololo.geojson.FeatureCollection

class GeoJSONFactorySpec extends WordSpec {
	"GeoJSONFactory" when {
	  "parsing GeoJSON to object" should {
	    val geometry = new Point(Array(1, 1))
		val properties = new HashMap[String, Object]()
		properties.put("test", 1.asInstanceOf[Object])
	    val feature = new Feature(geometry, properties)
	    
	    "be identical to programmatically created Feature" in {
	      val expected = """{"geometry":{"coordinates":[1.0,1.0],"type":"Point"},"properties":{"test":1},"type":"Feature"}"""
	      
	      val json = feature.toString
	      assert(expected == json)
	      
	      val geoJSON = GeoJSONFactory.create(json)
	      assert(expected == geoJSON.toString)
	    }
	    "be identical to programmatically created FeatureCollection" in {
	      val expected = """{"features":[{"geometry":{"coordinates":[1.0,1.0],"type":"Point"},"properties":{"test":1},"type":"Feature"},{"geometry":{"coordinates":[1.0,1.0],"type":"Point"},"properties":{"test":1},"type":"Feature"}],"type":"FeatureCollection"}"""
	      
	      val fc = new FeatureCollection(Array(feature, feature))
	      
	      assert(expected == fc.toString)
	    }
	  }
	  
	}
}