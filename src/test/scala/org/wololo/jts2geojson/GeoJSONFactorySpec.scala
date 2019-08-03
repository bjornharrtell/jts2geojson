package org.wololo.jts2geojson

import org.scalatest.WordSpec
import org.wololo.geojson._
import java.util.HashMap

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

        "be identical to programmatically created Feature without geometry" in {
          val feature = new Feature(null, properties)

          val expected = """{"type":"Feature","geometry":null,"properties":{"test":1}}"""

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

        "take care of bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "Point", "coordinates": [-8.311419016226296, 53.894485921596285], "bbox": [-8.311419016226296, 53.894485921596285, -8.311419016226296, 53.894485921596285] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[Point])
          val point = f.getGeometry.asInstanceOf[Point]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(-8.311419016226296, 53.894485921596285, -8.311419016226296, 53.894485921596285))
        }

        "take care of MultiPoint bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "MultiPoint", "coordinates": [[-8.311419016226296, 53.894485921596285]], "bbox": [-8.311419016226296, 53.894485921596285, -8.311419016226296, 53.894485921596285] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[MultiPoint])
          val point = f.getGeometry.asInstanceOf[MultiPoint]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(-8.311419016226296, 53.894485921596285, -8.311419016226296, 53.894485921596285))
        }

        "take care of LineString bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "LineString", "coordinates": [[0.0,0.0],[1.0,1.0]], "bbox": [0.0,0.0,1.0,1.0] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[LineString])
          val point = f.getGeometry.asInstanceOf[LineString]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(0.0, 0.0, 1.0, 1.0))
        }

        "take care of MultiLineString bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "MultiLineString", "coordinates": [[[0.0,0.0],[1.0,1.0]]], "bbox": [0.0,0.0,1.0,1.0] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[MultiLineString])
          val point = f.getGeometry.asInstanceOf[MultiLineString]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(0.0, 0.0, 1.0, 1.0))
        }

        "take care of Polygon bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "Polygon", "coordinates": [[[0.0,0.0],[0.0,1.0],[1.0,1.0],[0.0,1.0],[0.0,0.0]]], "bbox": [0.0,0.0,1.0,1.0] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[Polygon])
          val point = f.getGeometry.asInstanceOf[Polygon]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(0.0, 0.0, 1.0, 1.0))
        }

        "take care of MultiPolygon bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "MultiPolygon", "coordinates": [[[[0.0,0.0],[0.0,1.0],[1.0,1.0],[0.0,1.0],[0.0,0.0]]]], "bbox": [0.0,0.0,1.0,1.0] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[MultiPolygon])
          val point = f.getGeometry.asInstanceOf[MultiPolygon]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(0.0, 0.0, 1.0, 1.0))
        }

      }

      "parsing XYZ GeoJSON to object" should {
        val geometry = new Point(Array(1, 1, 1))
        val properties = new HashMap[String, Object]()
        properties.put("test", 1.asInstanceOf[Object])
        val feature = new Feature(geometry, properties)

        "be identical to programmatically created Feature" in {
          val expected = """{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0,1.0]},"properties":{"test":1}}"""

          val json = feature.toString
          assert(expected == json)

          val geoJSON = GeoJSONFactory.create(json)
          assert(expected == geoJSON.toString)
        }

        "be identical to programmatically created Feature with id" in {
          val feature = new Feature(1, geometry, properties)

          val expected = """{"type":"Feature","id":1,"geometry":{"type":"Point","coordinates":[1.0,1.0,1.0]},"properties":{"test":1}}"""

          val json = feature.toString
          assert(expected == json)

          val geoJSON = GeoJSONFactory.create(json)

          assert(expected == geoJSON.toString)
        }

        "be identical to programmatically created Feature without geometry" in {
          val feature = new Feature(null, properties)

          val expected = """{"type":"Feature","geometry":null,"properties":{"test":1}}"""

          val json = feature.toString
          assert(expected == json)

          val geoJSON = GeoJSONFactory.create(json)

          assert(expected == geoJSON.toString)
        }

        "be identical to programmatically created FeatureCollection" in {
          val expected = """{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0,1.0]},"properties":{"test":1}},{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0,1.0]},"properties":{"test":1}}]}"""

          val fc = new FeatureCollection(Array(feature, feature))
          assert(expected == fc.toString)
        }

        "take care of bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "Point", "coordinates": [-8.311419016226296, 53.894485921596285], "bbox": [-8.311419016226296, 53.894485921596285, -8.311419016226296, 53.894485921596285] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[Point])
          val point = f.getGeometry.asInstanceOf[Point]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(-8.311419016226296, 53.894485921596285, -8.311419016226296, 53.894485921596285))
        }

        "take care of MultiPoint bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "MultiPoint", "coordinates": [[-8.311419016226296, 53.894485921596285]], "bbox": [-8.311419016226296, 53.894485921596285, -8.311419016226296, 53.894485921596285] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[MultiPoint])
          val point = f.getGeometry.asInstanceOf[MultiPoint]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(-8.311419016226296, 53.894485921596285, -8.311419016226296, 53.894485921596285))
        }

        "take care of LineString bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "LineString", "coordinates": [[0.0,0.0],[1.0,1.0]], "bbox": [0.0,0.0,1.0,1.0] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[LineString])
          val point = f.getGeometry.asInstanceOf[LineString]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(0.0, 0.0, 1.0, 1.0))
        }

        "take care of MultiLineString bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "MultiLineString", "coordinates": [[[0.0,0.0],[1.0,1.0]]], "bbox": [0.0,0.0,1.0,1.0] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[MultiLineString])
          val point = f.getGeometry.asInstanceOf[MultiLineString]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(0.0, 0.0, 1.0, 1.0))
        }

        "take care of Polygon bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "Polygon", "coordinates": [[[0.0,0.0],[0.0,1.0],[1.0,1.0],[0.0,1.0],[0.0,0.0]]], "bbox": [0.0,0.0,1.0,1.0] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[Polygon])
          val point = f.getGeometry.asInstanceOf[Polygon]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(0.0, 0.0, 1.0, 1.0))
        }

        "take care of MultiPolygon bbox property" in {
          val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "MultiPolygon", "coordinates": [[[[0.0,0.0],[0.0,1.0],[1.0,1.0],[0.0,1.0],[0.0,0.0]]]], "bbox": [0.0,0.0,1.0,1.0] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[FeatureCollection])
          val fc = json.asInstanceOf[FeatureCollection]
          assert(fc.getFeatures.nonEmpty)
          val f = fc.getFeatures.head
          assert(f.getGeometry != null)
          assert(f.getGeometry.isInstanceOf[MultiPolygon])
          val point = f.getGeometry.asInstanceOf[MultiPolygon]
          assert(point.getBbox != null)
          assert(point.getBbox.toSeq == Seq(0.0, 0.0, 1.0, 1.0))
        }

      }

    }

}
