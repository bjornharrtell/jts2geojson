package org.wololo.geojson.internals;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is a volatile class.
 * It may be changed if the configuration options change.
 */
public final class Config {
    private final ObjectMapper mapper;

    public Config() {
        this(new ObjectMapper());
    }

    public Config(ObjectMapper mapper) {
        if (mapper == null) {
            throw new RuntimeException("ObjectMapper instance cannot be null");
        }
        this.mapper = mapper;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
