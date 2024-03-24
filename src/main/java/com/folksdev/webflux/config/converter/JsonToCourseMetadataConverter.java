package com.folksdev.webflux.config.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folksdev.webflux.model.metadata.CourseMetadata;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;

@Slf4j
@ReadingConverter
public class JsonToCourseMetadataConverter implements Converter<Json, CourseMetadata> {

    private final ObjectMapper objectMapper;

    public JsonToCourseMetadataConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public CourseMetadata convert(Json source) {
        try {
            return objectMapper.readValue(source.asString(), CourseMetadata.class);
        } catch (IOException e) {
            log.error("Failed to convert JSON to CourseMetadata", e);
            throw new RuntimeException(e);
        }
    }
}