package com.hunt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "program")
public class Program implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "college_id")
    private Long collegeId;

    @Column(name = "degree")
    private String degree;

    @Column(name = "code")
    private String code;

    @Column(name = "duration")
    private String duration;

    @Column(name = "category")
    private String category;

    @Column(name = "institution_id")
    private Long institutionId;

    @ManyToMany(mappedBy = "programs")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<CourseUGC> courseUGCSet;
}
