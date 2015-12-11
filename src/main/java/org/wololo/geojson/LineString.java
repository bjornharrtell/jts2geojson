package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LineString extends Geometry {
    private final double[][] coordinates;
    private final double[] bbox;

    @JsonCreator
    public LineString(@JsonProperty("coordinates") double [][] coordinates) {
        super();
        this.coordinates = coordinates;
        this.bbox = null;
    }

    @JsonCreator
    public LineString(@JsonProperty("coordinates") double [][] coordinates, @JsonProperty("bbox") double [] bbox) {
        super();
        this.coordinates = coordinates;
        this.bbox = bbox;
    }

    public double[][] getCoordinates() {
        return coordinates;
    }

    public double[] getBbox() {
        return bbox;
    }
}
