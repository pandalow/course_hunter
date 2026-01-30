package com.hunt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "course")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String code;
    private String semester;
    private int credits;

    @Column(columnDefinition = "TEXT")
    private String outline;
    @Column(columnDefinition = "TEXT")
    private String outcomes;
    @Column(columnDefinition = "TEXT")
    private String assessments;

    // --- 关联对象 ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToMany
    @JoinTable(
            name = "course_teacher",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> teachers;

    // --- 统计字段 (保持原有 Formula) ---
    @org.hibernate.annotations.Formula("(SELECT AVG(r.score) FROM rating r WHERE r.course_id = id)")
    private Double avgScore;

    @org.hibernate.annotations.Formula("(SELECT COUNT(*) FROM comment c WHERE c.course_id = id)")
    private Integer commentCount;

    @org.hibernate.annotations.Formula("(SELECT c.content FROM comment c WHERE c.course_id = id ORDER BY c.create_time DESC LIMIT 1)")
    private String latestComment;
}
