package org.wololo.jts2geojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class DataFactory {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static Data fromJson(String json) {
        try {
            return mapper.readerFor(Data.class).readValue(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
