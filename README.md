## Introduction

This Java library can convert JTS geometries to GeoJSON and back. Its API is similar to other io.* classes in JTS.

## Usage

```java

  GeoJSONWriter writer = new GeoJSONWriter();
  String json = writer.write(geometry);

  GeoJSONReader reader = new GeoJSONReader();
  Geometry geometry = reader.read(json);
  
```

## Features and FeatureCollections

JTS does not have anything like GeoJSON Feature or FeatureCollection but they can be parsed by this library. Example:

```java

  // parse Feature or FeatureCollection
  Feature feature = (Feature) GeoJSONFactory.create(json);
  FeatureCollection featureCollection = (FeatureCollection) GeoJSONFactory.create(json);
  
  // parse Geometry from Feature
  GeoJSONReader reader = new GeoJSONReader();
  Geometry geometry = reader.read(feature.geometry);
  geometry = reader.read(featureCollection.features[0].geometry);
  
  // create and serialize a FeatureCollection
  List<Features> features = new ArrayList<Features>();
  Map<String, Object> properties = new HashMap<String, Object>();
  features.add(new Feature(geometry, properties);
  GeoJSONWriter writer = new GeoJSONWriter();
  String json = writer.write(features);

```
