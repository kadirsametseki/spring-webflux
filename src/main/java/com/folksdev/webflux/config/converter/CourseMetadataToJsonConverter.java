package com.folksdev.webflux.config.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folksdev.webflux.model.metadata.CourseMetadata;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.io.IOException;

@Slf4j
@WritingConverter
public class CourseMetadataToJsonConverter implements Converter<CourseMetadata, Json> {

    private final ObjectMapper objectMapper;

    public CourseMetadataToJsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Json convert(CourseMetadata courseMetadata) {
        try {
            return Json.of(objectMapper.writeValueAsBytes(courseMetadata));
        } catch (IOException e) {
            log.error("Failed to convert CourseMetadata to JSON", e);
            throw new RuntimeException(e);
        }
    }
}