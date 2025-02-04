package com.hunt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "institution")
public class Institution implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "website")
    private String website;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "name")
    private String name;
}
