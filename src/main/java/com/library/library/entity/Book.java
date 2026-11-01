package com.library.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Поле название не заполнено")
    private String title;

    @NotBlank(message = "Поле автор не заполнено")
    private String author;

    @Column(name = "publication_year")
    private Integer year;

    @NotNull(message = "Поле количество не заполнено")
    @Min(value = 1, message = "Количество должно быть не меньше 1")
    private Integer totalCount;

    private Integer availableCount;
}