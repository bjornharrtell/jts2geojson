package org.wololo.jts2geojson;

import java.util.List;

import org.wololo.geojson.Feature;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class GeoJSONWriter {

    final static GeoJSONReader reader = new GeoJSONReader();
        
    public org.wololo.geojson.Geometry write(Geometry geometry) {
        Class<? extends Geometry> c = geometry.getClass();
        if (c.equals(Point.class)) {
            return convert((Point) geometry);
        } else if (c.equals(LineString.class)) {
            return convert((LineString) geometry);
        } else if (c.equals(Polygon.class)) {
            return convert((Polygon) geometry);
        } else if (c.equals(MultiPoint.class)) {
            return convert((MultiPoint) geometry);
        } else if (c.equals(MultiLineString.class)) {
            return convert((MultiLineString) geometry);
        } else if (c.equals(MultiPolygon.class)) {
            return convert((MultiPolygon) geometry);
        } else if (c.equals(GeometryCollection.class)) {
            return convert((GeometryCollection) geometry);
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    public org.wololo.geojson.FeatureCollection write(List<Feature> features) {
        int size = features.size();
        org.wololo.geojson.Feature[] featuresJson = new org.wololo.geojson.Feature[size];
        for (int i=0; i<size; i++) {
            featuresJson[i] = features.get(i);
        }
        return new org.wololo.geojson.FeatureCollection(featuresJson);
    }

    org.wololo.geojson.Point convert(Point point) {
        org.wololo.geojson.Point json = new org.wololo.geojson.Point(
                convert(point.getCoordinate()));
        return json;
    }

    org.wololo.geojson.MultiPoint convert(MultiPoint multiPoint) {
        return new org.wololo.geojson.MultiPoint(
                convert(multiPoint.getCoordinates()));
    }

    org.wololo.geojson.LineString convert(LineString lineString) {
        return new org.wololo.geojson.LineString(
                convert(lineString.getCoordinates()));
    }

    org.wololo.geojson.MultiLineString convert(MultiLineString multiLineString) {
        int size = multiLineString.getNumGeometries();
        double[][][] lineStrings = new double[size][][];
        for (int i = 0; i < size; i++) {
            lineStrings[i] = convert(multiLineString.getGeometryN(i).getCoordinates());
        }
        return new org.wololo.geojson.MultiLineString(lineStrings);
    }

    org.wololo.geojson.Polygon convert(Polygon polygon) {
        int size = polygon.getNumInteriorRing() + 1;
        double[][][] rings = new double[size][][];
        rings[0] = convert(polygon.getExteriorRing().getCoordinates());
        for (int i = 0; i < size - 1; i++) {
            rings[i + 1] = convert(polygon.getInteriorRingN(i).getCoordinates());
        }
        return new org.wololo.geojson.Polygon(rings);
    }

    org.wololo.geojson.MultiPolygon convert(MultiPolygon multiPolygon) {
        int size = multiPolygon.getNumGeometries();
        double[][][][] polygons = new double[size][][][];
        for (int i = 0; i < size; i++) {
            polygons[i] = convert((Polygon) multiPolygon.getGeometryN(i)).getCoordinates();
        }
        return new org.wololo.geojson.MultiPolygon(polygons);
    }

    org.wololo.geojson.GeometryCollection convert(GeometryCollection gc) {
        int size = gc.getNumGeometries();
        org.wololo.geojson.Geometry[] geometries = new org.wololo.geojson.Geometry[size];
        for (int i = 0; i < size; i++) {
            geometries[i] = write((Geometry) gc.getGeometryN(i));
        }
        return new org.wololo.geojson.GeometryCollection(geometries);
    }

    double[] convert(Coordinate coordinate) {
        if(Double.isNaN( coordinate.z )) {
            return new double[] { coordinate.x, coordinate.y };
        }
        else {
            return new double[] { coordinate.x, coordinate.y, coordinate.z };
        }
    }

    double[][] convert(Coordinate[] coordinates) {
        double[][] array = new double[coordinates.length][];
        for (int i = 0; i < coordinates.length; i++) {
            array[i] = convert(coordinates[i]);
        }
        return array;
    }
}
