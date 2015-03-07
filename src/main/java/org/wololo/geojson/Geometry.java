package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"type", "coordinates"})
public abstract class Geometry extends GeoJSON {
    @JsonCreator
    public Geometry() {
        super();
    }
}
