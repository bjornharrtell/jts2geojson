package org.wololo.geojson;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class GeoJSONFactory {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static GeoJSON create(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            String type = node.get("type").asText();
            if (type.equals("FeatureCollection")) {
                return readFeatureCollection(node);
            } else if (type.equals("Feature")) {
                return readFeature(node);
            } else {
                return readGeometry(node, type);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static FeatureCollection readFeatureCollection(JsonNode node)
            throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
        Iterator<JsonNode> it = node.get("features").iterator();
        List<Feature> features = new ArrayList<Feature>();
        while (it.hasNext()) {
            JsonNode jFeature = it.next();
            features.add(readFeature(jFeature));
        }
        
        return new FeatureCollection(features.toArray(new Feature[features.size()]));
    }
    
    private static Feature readFeature(JsonNode node)
            throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
        JsonNode geometryNode = node.get("geometry");
        JavaType javaType = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        Object id = node.get("id");
        Map<String, Object> properties = mapper.readValue(node.get("properties").traverse(), javaType);
        Geometry geometry = readGeometry(geometryNode);
        return new Feature(id, geometry, properties);
    }
    
    private static Geometry readGeometry(JsonNode node)
            throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
        if (!node.isNull()) {
            final String type = node.get("type").asText();
            return readGeometry(node, type);
        } else {
            return null;
        }
    }

    private static Geometry readGeometry(JsonNode node, String type)
            throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
        return (Geometry) mapper.readValue(node.traverse(), Class.forName("org.wololo.geojson." + type));
    }

}
