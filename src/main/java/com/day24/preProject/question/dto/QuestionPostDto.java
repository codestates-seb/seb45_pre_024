package com.day24.preProject.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class QuestionPostDto {

    @NotBlank
    private long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String body;

}
