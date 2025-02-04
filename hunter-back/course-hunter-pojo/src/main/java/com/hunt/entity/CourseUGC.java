package com.hunt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
@Entity
// Table name must be lowercase
@Table(name = "course_ugc")
public class CourseUGC implements Serializable {
    // Primary key
    @Id
    @Column(name = "id")
    // Automatically generated primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "institution_id")
    private Long institutionId;
    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "rating")
    private double rating;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "latest_comment")
    private String latestComment;

    @Column(name = "comments_count")
    private Integer commentsCount;

    @Column(name = "latest_comment_time")
    private Instant latestCommentTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Course course;

    @ManyToMany
    @JoinTable(
            name = "program_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Program> programs;

}
