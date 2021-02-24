package org.wololo.jts2geojson

import org.scalatest.WordSpec
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Coordinate
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
        val expected = """{"type":"Point","coordinates":[1.0,1.0]}"""

        val json = writer.write(point)
        assert(expected === json.toString)

        val geometry = reader.read(json)
        assert("POINT (1 1)" === geometry.toString)
      }

      "expected result for LineString" in {
        val json = writer.write(lineString)
        assert("""{"type":"LineString","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""" === json.toString)
      }

      "expected result for LineString with id" in {
        val json = writer.write(lineString)
        assert("""{"type":"LineString","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""" === json.toString)
      }

      "expected result for LinearString with id" in {
        val ring = factory.createLinearRing(lineString.getCoordinates)
        val json = writer.write(ring)
        assert("""{"type":"LineString","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""" === json.toString)
      }

      "expected result for Polygon" in {
        val json = writer.write(polygon);
        assert("""{"type":"Polygon","coordinates":[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]}""" === json.toString)
      }

      "expected result for MultiPoint" in {
        val multiPoint = factory.createMultiPointFromCoords(lineString.getCoordinates())
        val json = writer.write(multiPoint)
        assert("""{"type":"MultiPoint","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""" === json.toString)
      }

      "expected result for MultiLineString" in {
        val multiLineString = factory.createMultiLineString(Array(lineString, lineString))
        val json = writer.write(multiLineString)
        assert("""{"type":"MultiLineString","coordinates":[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]],[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]}""" === json.toString)
      }

      "expected result for MultiPolygon" in {
        val multiPolygon = factory.createMultiPolygon(Array(polygon, polygon))
        val json = writer.write(multiPolygon)
        assert("""{"type":"MultiPolygon","coordinates":[[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]],[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]]}""" === json.toString)
      }

      "expected result for FeatureCollection" in {
        val json = writer.write(point)
        val feature1 = new Feature(json, null)
        val feature2 = new Feature(json, null)
        val featureCollection = new FeatureCollection(Array(feature1, feature2))
        assert("""{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":null},{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":null}]}""" === featureCollection.toString)
      }
    }

    "writing XYZ GeoJSON from JTS object" should {

      val reader = new GeoJSONReader()
      val writer = new GeoJSONWriter()
      val factory = new GeometryFactory()

      val point = factory.createPoint(new Coordinate(1, 1, 1))
      val lineString = factory.createLineString(Array(
        new Coordinate(1, 1, 1),
        new Coordinate(1, 2, 1),
        new Coordinate(2, 2, 2),
        new Coordinate(1, 1, 1)))
      val polygon = factory.createPolygon(lineString.getCoordinates())

      "expected result for Point" in {
        val expected = """{"type":"Point","coordinates":[1.0,1.0,1.0]}"""

        val json = writer.write(point)
        assert(expected === json.toString)

        val geometry = reader.read(json)
        assert("POINT (1 1)" === geometry.toString)
      }

      "expected result for LineString" in {
        val json = writer.write(lineString)
        assert("""{"type":"LineString","coordinates":[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]}""" === json.toString)
      }

      "expected result for LineString with id" in {
        val json = writer.write(lineString)
        assert("""{"type":"LineString","coordinates":[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]}""" === json.toString)
      }

      "expected result for Polygon" in {
        val json = writer.write(polygon);
        assert("""{"type":"Polygon","coordinates":[[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]]}""" === json.toString)
      }

      "expected result for MultiPoint" in {
        val multiPoint = factory.createMultiPointFromCoords(lineString.getCoordinates())
        val json = writer.write(multiPoint)
        assert("""{"type":"MultiPoint","coordinates":[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]}""" === json.toString)
      }

      "expected result for MultiLineString" in {
        val multiLineString = factory.createMultiLineString(Array(lineString, lineString))
        val json = writer.write(multiLineString)
        assert("""{"type":"MultiLineString","coordinates":[[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]],[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]]}""" === json.toString)
      }

      "expected result for MultiPolygon" in {
        val multiPolygon = factory.createMultiPolygon(Array(polygon, polygon))
        val json = writer.write(multiPolygon)
        assert("""{"type":"MultiPolygon","coordinates":[[[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]],[[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]]]}""" === json.toString)
      }

      "expected result for FeatureCollection" in {
        val json = writer.write(point)
        val feature1 = new Feature(json, null)
        val feature2 = new Feature(json, null)
        val featureCollection = new FeatureCollection(Array(feature1, feature2))
        assert("""{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0,1.0]},"properties":null},{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0,1.0]},"properties":null}]}""" === featureCollection.toString)
      }
    }

  }
}
