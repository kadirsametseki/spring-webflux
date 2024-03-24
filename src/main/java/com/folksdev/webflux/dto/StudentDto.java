package com.folksdev.webflux.dto;

import java.util.Set;

public record StudentDto(String name, String email, Set<CourseDto> courses) {
}
