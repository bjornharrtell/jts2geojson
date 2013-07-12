## Introduction

This library can convert JTS geometries to GeoJSON and back. Its API is similar to other io.* classes in JTS.

## Usage

```java

GeoJSONWriter writer = new GeoJSONWriter();
String json = writer.write(geometry);

GeoJSONReader reader = new GeoJSONReader();
Geometry geometry = reader.read(json);
