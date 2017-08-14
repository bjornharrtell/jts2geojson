package org.wololo.geojson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class GeoJSON {
    @JsonProperty("type")
    private String type;
    
    @JsonCreator
    public GeoJSON() {
        setType(getClass().getSimpleName());
    }
    
    public String toString() {
        final ObjectMapper mapper = GeoJSONFactory.getConfig().getMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonGenerationException e) {
            return "Unhandled exception occured when serializing this instance";
        } catch (JsonMappingException e) {
            return "Unhandled exception occured when serializing this instance";
        } catch (IOException e) {
            return "Unhandled exception occured when serializing this instance";
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
