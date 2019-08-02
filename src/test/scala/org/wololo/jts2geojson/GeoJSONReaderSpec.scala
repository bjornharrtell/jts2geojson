package org.wololo.jts2geojson

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel

import org.scalatest.WordSpec

class GeoJSONReaderSpec extends WordSpec {
  "GeoJSONReader" when {
    "reading JTS object from GeoJSON" should {

      val reader = new GeoJSONReader()
      val writer = new GeoJSONWriter()
      val factory = new GeometryFactory()
      val srid = 25832
      val factorySrid = new GeometryFactory(new PrecisionModel(), srid)

      val coordArray = Array(
        new Coordinate(1, 1),
        new Coordinate(1, 2),
        new Coordinate(2, 2),
        new Coordinate(1, 1))

      "expected result for Point" in {
        val expected = factory.createPoint(new Coordinate(1, 1));
        val expectedSrid = factorySrid.createPoint(new Coordinate(1, 1));
        val json = """{"type":"Point","coordinates":[1.0,1.0]}"""

        var geometry = reader.read(json)
        assert(expected === geometry)
        assert(0 === geometry.getSRID())

        geometry = reader.read(json, factorySrid)
        assert(expectedSrid === geometry)
        assert(srid === geometry.getSRID())
      }

      "expected result for MultiPoint" in {
        val expected = factory.createMultiPointFromCoords(coordArray)
        val expectedSrid = factorySrid.createMultiPointFromCoords(coordArray)
        val json = """{"type":"MultiPoint","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}"""

        var geometry = reader.read(json)
        assert(expected === geometry)
        assert(0 === geometry.getSRID())

        geometry = reader.read(json, factorySrid)
        assert(expectedSrid === geometry)
        assert(srid === geometry.getSRID())
      }
    }

    "reading XYZ JTS object from GeoJSON" should {

      val reader = new GeoJSONReader()
      val writer = new GeoJSONWriter()
      val factory = new GeometryFactory()
      val srid = 25832
      val factorySrid = new GeometryFactory(new PrecisionModel(), srid)

      val coordArray = Array(
        new Coordinate(1, 1, 1),
        new Coordinate(1, 2, 1),
        new Coordinate(2, 2, 2),
        new Coordinate(1, 1, 1))

      "expected result for Point" in {
        val expected = factory.createPoint(new Coordinate(1, 1, 1));
        val expectedSrid = factorySrid.createPoint(new Coordinate(1, 1, 1));
        val json = """{"type":"Point","coordinates":[1.0,1.0,1.0]}"""

        var geometry = reader.read(json)
        assert(expected === geometry)
        assert(0 === geometry.getSRID())

        geometry = reader.read(json, factorySrid)
        assert(expectedSrid === geometry)
        assert(srid === geometry.getSRID())
      }

      "expected result for MultiPoint" in {
        val expected = factory.createMultiPointFromCoords(coordArray)
        val expectedSrid = factorySrid.createMultiPointFromCoords(coordArray)
        val json = """{"type":"MultiPoint","coordinates":[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]}"""

        var geometry = reader.read(json)
        assert(expected === geometry)
        assert(0 === geometry.getSRID())

        geometry = reader.read(json, factorySrid)
        assert(expectedSrid === geometry)
        assert(srid === geometry.getSRID())
      }
    }

  }
}