package org.wololo.jts2geojson

import org.scalatest.WordSpec
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Coordinate
import org.wololo.geojson.Feature
import org.wololo.geojson.FeatureCollection

class GeoJSONWriterSpec extends WordSpec {
  "GeoJSONWriter" when {
    "writing GeoJSON from JTS object" should {
      
      val reader = new GeoJSONReader()
      val writer = new GeoJSONWriter()
      val factory = new GeometryFactory()

      val point = factory.createPoint(new Coordinate(1, 1))
      val lineString = factory.createLineString(Array(
        new Coordinate(1, 1),
        new Coordinate(1, 2),
        new Coordinate(2, 2),
        new Coordinate(1, 1)))
      val polygon = factory.createPolygon(lineString.getCoordinates())

      "expected result for Point" in {
        val expected = """{"coordinates":[1.0,1.0],"type":"Point"}"""

        val json = writer.write(point)
        assert(expected === json.toString)

        val geometry = reader.read(json)
        assert("POINT (1 1)" === geometry.toString)
      }

      "expected result for LineString" in {
        val json = writer.write(lineString)
        assert("""{"coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]],"type":"LineString"}""" === json.toString)
      }

      "expected result for Polygon" in {
        val json = writer.write(polygon);
        assert("""{"coordinates":[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]],"type":"Polygon"}""" === json.toString)
      }

      "expected result for MultiPoint" in {
        val multiPoint = factory.createMultiPoint(lineString.getCoordinates())
        val json = writer.write(multiPoint)
        assert("""{"coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]],"type":"MultiPoint"}""" === json.toString)
      }

      "expected result for MultiLineString" in {
        val multiLineString = factory.createMultiLineString(Array(lineString, lineString))
        val json = writer.write(multiLineString)
        assert("""{"coordinates":[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]],[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]],"type":"MultiLineString"}""" === json.toString)
      }

      "expected result for MultiPolygon" in {
        val multiPolygon = factory.createMultiPolygon(Array(polygon, polygon))
        val json = writer.write(multiPolygon)
        assert("""{"coordinates":[[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]],[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]],"type":"MultiPolygon"}""" === json.toString)
      }

      "expected result for FeatureCollection" in {
        val json = writer.write(point)
        val feature1 = new Feature(json, null)
        val feature2 = new Feature(json, null)
        val featureCollection = new FeatureCollection(Array(feature1, feature2))
        assert("""{"features":[{"geometry":{"coordinates":[1.0,1.0],"type":"Point"},"properties":null,"type":"Feature"},{"geometry":{"coordinates":[1.0,1.0],"type":"Point"},"properties":null,"type":"Feature"}],"type":"FeatureCollection"}""" === featureCollection.toString)
      }
    }
  }
}