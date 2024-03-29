package com.paszkowski.movies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int year;
    private String category;
    private String description;
    @Min(1) @Max(5)
    private int grade;

    public Movie(String title, int year, String category, String description, int grade) {
        this.title = title;
        this.year = year;
        this.category = category;
        this.description = description;
        this.grade = grade;
    }
}
