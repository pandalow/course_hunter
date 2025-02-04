package com.hunt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
// Table name must be lowercase
@Table(name = "course")

public class Course implements Serializable {
    // Primary key
    @Id
    @Column(name = "id")
    // Automatically generated primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "code")
    private String code;

    @Column(name = "semester")
    private String semester;

    @Column(name = "credits")
    private int credits;

    @Column(name = "outline")
    private String outline;

    @Column(name = "assessments")
    private String assessments;

    @Column(name = "outcomes")
    private String outcomes;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "country_name")
    private String countryName;

    @ManyToMany
    @JoinTable(
            name = "course_teacher",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Teacher> teachers;



}
