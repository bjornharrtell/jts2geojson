## Introduction

This library can convert JTS geometries to GeoJSON and back. Its API is similar to other io.* classes in JTS.

## Usage

```java

  GeoJSONWriter writer = new GeoJSONWriter();
  String json = writer.write(geometry);

  GeoJSONReader reader = new GeoJSONReader();
  Geometry geometry = reader.read(json);
  
```

## Features and FeatureCollections

JTS does not handle Features and FeatureCollections but they are supported. Example:

```java

  // assumes json is a String containing a GeoJSON Feature
  // the feature object contains a geometry and a Map of properties
  Feature feature = (Feature) GeoJSONFactory.create(json);
  
  GeoJSONReader reader = new GeoJSONReader();
  Geometry geometry = reader.read(feature.geometry);

```
