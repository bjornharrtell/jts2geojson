package org.wololo.jts2geojson;

import java.util.List;

import org.locationtech.jts.geom.*;
import org.wololo.geojson.Feature;

public class GeoJSONWriter {

    final static GeoJSONReader reader = new GeoJSONReader();

    public org.wololo.geojson.Geometry write(Geometry geometry) {
        Class<? extends Geometry> c = geometry.getClass();
        if (c.equals(Point.class))
            return convert((Point) geometry);
        else if (c.equals(LineString.class))
            return convert((LineString) geometry);
        else if (c.equals(LinearRing.class))
            return convert((LinearRing) geometry);
        else if (c.equals(Polygon.class))
            return convert((Polygon) geometry);
        else if (c.equals(MultiPoint.class))
            return convert((MultiPoint) geometry);
        else if (c.equals(MultiLineString.class))
            return convert((MultiLineString) geometry);
        else if (c.equals(MultiPolygon.class))
            return convert((MultiPolygon) geometry);
        else if (c.equals(GeometryCollection.class))
            return convert((GeometryCollection) geometry);
        else
            throw new UnsupportedOperationException();
    }

    public org.wololo.geojson.FeatureCollection write(List<Feature> features) {
        var size = features.size();
        var featuresJson = new org.wololo.geojson.Feature[size];
        for (var i = 0; i < size; i++)
            featuresJson[i] = features.get(i);
        return new org.wololo.geojson.FeatureCollection(featuresJson);
    }

    org.wololo.geojson.Point convert(Point point) {
        return new org.wololo.geojson.Point(convert(point.getCoordinate()));
    }

    org.wololo.geojson.MultiPoint convert(MultiPoint multiPoint) {
        return new org.wololo.geojson.MultiPoint(
            convert(multiPoint.getCoordinates()));
    }

    org.wololo.geojson.LineString convert(LineString lineString) {
        return new org.wololo.geojson.LineString(
            convert(lineString.getCoordinates()));
    }

    org.wololo.geojson.LineString convert(LinearRing ringString) {
        return new org.wololo.geojson.LineString(
            convert(ringString.getCoordinates()));
    }

    org.wololo.geojson.MultiLineString convert(MultiLineString multiLineString) {
        var size = multiLineString.getNumGeometries();
        var lineStrings = new double[size][][];
        for (int i = 0; i < size; i++)
            lineStrings[i] = convert(multiLineString.getGeometryN(i).getCoordinates());
        return new org.wololo.geojson.MultiLineString(lineStrings);
    }

    org.wololo.geojson.Polygon convert(Polygon polygon) {
        var size = polygon.getNumInteriorRing() + 1;
        var rings = new double[size][][];
        rings[0] = convert(polygon.getExteriorRing().getCoordinates());
        for (int i = 0; i < size - 1; i++)
            rings[i + 1] = convert(polygon.getInteriorRingN(i).getCoordinates());
        return new org.wololo.geojson.Polygon(rings);
    }

    org.wololo.geojson.MultiPolygon convert(MultiPolygon multiPolygon) {
        var size = multiPolygon.getNumGeometries();
        var polygons = new double[size][][][];
        for (int i = 0; i < size; i++)
            polygons[i] = convert((Polygon) multiPolygon.getGeometryN(i)).getCoordinates();
        return new org.wololo.geojson.MultiPolygon(polygons);
    }

    org.wololo.geojson.GeometryCollection convert(GeometryCollection gc) {
        var size = gc.getNumGeometries();
        var geometries = new org.wololo.geojson.Geometry[size];
        for (int i = 0; i < size; i++)
            geometries[i] = write((Geometry) gc.getGeometryN(i));
        return new org.wololo.geojson.GeometryCollection(geometries);
    }

    double[] convert(Coordinate coordinate) {
        if (Double.isNaN( coordinate.getZ() ))
            return new double[] { coordinate.x, coordinate.y };
        else
            return new double[] { coordinate.x, coordinate.y, coordinate.getZ() };
    }

    double[][] convert(Coordinate[] coordinates) {
        var array = new double[coordinates.length][];
        for (int i = 0; i < coordinates.length; i++)
            array[i] = convert(coordinates[i]);
        return array;
    }
}
