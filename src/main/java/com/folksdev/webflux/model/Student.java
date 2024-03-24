package com.folksdev.webflux.model;

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
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "student")
public class Student implements Serializable, Persistable<UUID> {

    @Id
    private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    @Column("course_id")
    private Set<String> courses;

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