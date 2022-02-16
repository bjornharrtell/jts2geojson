package org.wololo.jts2geojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.wololo.geojson.GeoJSON;

class SampleClassWithGeoJsonField {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static SampleClassWithGeoJsonField read(String json) {
        try {
            return mapper.readerFor(SampleClassWithGeoJsonField.class).readValue(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public GeoJSON geometry;

    public String otherProperty;

}
