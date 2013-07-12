package org.wololo.geojson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeoJSONFactory {
	static final ObjectMapper mapper = new ObjectMapper();

	public static GeoJSON create(String json) {
		try {
			JsonNode node = mapper.readTree(json);
			String type = node.get("type").textValue();
			return (GeoJSON) mapper.readValue(node.traverse(),
					Class.forName("org.wololo.geojson." + type));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
