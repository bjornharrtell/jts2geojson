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
          val expected = """{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"test":1}}"""
          
          val json = feature.toString
          assert(expected == json)
          
          val geoJSON = GeoJSONFactory.create(json)
          assert(expected == geoJSON.toString)
        }
        
        "be identical to programmatically created Feature with id" in {
          val feature = new Feature(1, geometry, properties)
        
          val expected = """{"type":"Feature","id":1,"geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"test":1}}"""

          val json = feature.toString
          assert(expected == json)
          
          val geoJSON = GeoJSONFactory.create(json)
          
          assert(expected == geoJSON.toString)
        }
        
        "be identical to programmatically created FeatureCollection" in {
          val expected = """{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"test":1}},{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"test":1}}]}"""
          
          val fc = new FeatureCollection(Array(feature, feature))
          assert(expected == fc.toString)
        }
      }
      
    }
}
