package com.folksdev.webflux.model;

import com.folksdev.webflux.model.metadata.CourseMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "course")
public class Course implements Serializable, Persistable<UUID> {

    @Id
    @Column("course_id")
    private UUID id;
    private String name;
    private String description;
    private Integer duration;
    private String teacher;
    private CourseMetadata courseMetadata;

    @Transient
    private boolean isUpdated = false;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return !this.isUpdated || this.id == null;
    }
}