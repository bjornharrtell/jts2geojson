package org.wololo.jts2geojson;

import org.wololo.geojson.*;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.PrecisionModel;

public class GeoJSONReader {
    final static GeometryFactory factory = new GeometryFactory(
            new PrecisionModel(PrecisionModel.FLOATING));

    public Geometry read(String json) {
        GeoJSON geoJSON = GeoJSONFactory.create(json);
        return read(geoJSON);
    }
    
    public Geometry read(GeoJSON geoJSON) {
        if (geoJSON instanceof Point) {
            return convert((Point) geoJSON);
        } else if (geoJSON instanceof LineString) {
            return convert((LineString) geoJSON);
        } else if (geoJSON instanceof Polygon) {
            return convert((Polygon) geoJSON);
        } else if (geoJSON instanceof MultiPoint) {
            return convert((MultiPoint) geoJSON);
        } else if (geoJSON instanceof MultiLineString) {
            return convert((MultiLineString) geoJSON);
        } else if (geoJSON instanceof MultiPolygon) {
            return convert((MultiPolygon) geoJSON);
        } else if (geoJSON instanceof GeometryCollection) {
            return convert((GeometryCollection) geoJSON);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    Geometry convert(Point point) {
        return factory.createPoint(convert(point.getCoordinates()));
    }

    Geometry convert(MultiPoint multiPoint) {
        return factory.createMultiPoint(convert(multiPoint.getCoordinates()));
    }

    Geometry convert(LineString lineString) {
        return factory.createLineString(convert(lineString.getCoordinates()));
    }

    Geometry convert(MultiLineString multiLineString) {
        int size = multiLineString.getCoordinates().length;
        com.vividsolutions.jts.geom.LineString[] lineStrings = new com.vividsolutions.jts.geom.LineString[size];
        for (int i = 0; i < size; i++) {
            lineStrings[i] = factory.createLineString(convert(multiLineString.getCoordinates()[i]));
        }
        return factory.createMultiLineString(lineStrings);
    }

    Geometry convert(Polygon polygon) {
        return convertToPolygon(polygon.getCoordinates());
    }

    com.vividsolutions.jts.geom.Polygon convertToPolygon(double[][][] coordinates) {
        LinearRing shell = factory.createLinearRing(convert(coordinates[0]));

        if (coordinates.length > 1) {
            int size = coordinates.length - 1;
            LinearRing[] holes = new LinearRing[size];
            for (int i = 0; i < size; i++) {
                holes[i] = factory.createLinearRing(convert(coordinates[i + 1]));
            }
            return factory.createPolygon(shell, holes);
        } else {
            return factory.createPolygon(shell);
        }
    }

    Geometry convert(MultiPolygon multiPolygon) {
        int size = multiPolygon.getCoordinates().length;
        com.vividsolutions.jts.geom.Polygon[] polygons = new com.vividsolutions.jts.geom.Polygon[size];
        for (int i = 0; i < size; i++) {
            polygons[i] = convertToPolygon(multiPolygon.getCoordinates()[i]);
        }
        return factory.createMultiPolygon(polygons);
    }

    Geometry convert(GeometryCollection gc) {
        int size = gc.getGeometries().length;
        com.vividsolutions.jts.geom.Geometry[] geometries = new com.vividsolutions.jts.geom.Geometry[size];
        for (int i = 0; i < size; i++) {
            geometries[i] = read(gc.getGeometries()[i]);
        }
        return factory.createGeometryCollection(geometries);
    }

    Coordinate convert(double[] c) {
        if(c.length == 2){
            return new Coordinate(c[0], c[1]);
        }
        else{
            return new Coordinate(c[0], c[1], c[2]);
        }
    }

    Coordinate[] convert(double[][] ca) {
        Coordinate[] coordinates = new Coordinate[ca.length];
        for (int i = 0; i < ca.length; i++) {
            coordinates[i] = convert(ca[i]);
        }
        return coordinates;
    }
}
