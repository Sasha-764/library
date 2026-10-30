package com.library.library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    @Column(name = "publication_year")
    private Integer year;
    private Integer totalCount;
    private Integer availableCount;
}