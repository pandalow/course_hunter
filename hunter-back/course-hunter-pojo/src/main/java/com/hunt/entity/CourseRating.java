package com.hunt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true) // Lombok will generate equals() and hashCode() methods for this class
@Data
@Entity
@Table(name = "course_rating")
public class CourseRating extends Rating {
    @Column(name = "course_id")
    private Long courseId;
}
