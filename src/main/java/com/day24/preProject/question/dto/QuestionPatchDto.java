package com.day24.preProject.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class QuestionPatchDto {

    @Size(max = 100)
    private String title;

    private String body;

    private boolean accepted;

}
