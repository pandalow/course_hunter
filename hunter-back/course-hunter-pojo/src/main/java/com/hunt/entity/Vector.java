package com.hunt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_vector")
public class Vector {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "vector")
    private String vector;

}
