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
import org.wololo.geojson.internals.Config;


public class GeoJSONFactory {
    private static Config config = new Config();

    public static Config getConfig() {
        return config;
    }

    public static void setConfig(Config config) {
        if (config != null) {
            GeoJSONFactory.config = config;
        }
    }

    public static GeoJSON create(String json) {
        try {
            JsonNode node = config.getMapper().readTree(json);
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
        JavaType javaType = config.getMapper().getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        Object id = node.get("id");
        Map<String, Object> properties = config.getMapper().readValue(node.get("properties").traverse(), javaType);
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
        return (Geometry) config.getMapper().readValue(node.traverse(), Class.forName("org.wololo.geojson." + type));
    }

}
