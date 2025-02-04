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
@Table(name = "country")
public class Country implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "english_name")
    private String name;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "code")
    private Integer code;

    @Column(name = "chinese_name")
    private String chineseName;

    @Column(name = "french_name")
    private String frenchName;

    @Column(name = "italian_name")
    private String italianName;

    @Column(name = "japanese_name")
    private String japaneseName;

    @Column(name = "russian_name")
    private String russianName;

    @Column(name = "german_name")
    private String germanName;

    @Column(name = "spanish_name")
    private String spanishName;
}
