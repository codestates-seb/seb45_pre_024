package com.day24.preProject.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    INVALID_BODY_VALUE(400, "Invalid body value"),
    INVALID_REQUEST_PARAMETER(400, "Invalid request parpameter"),

    QUESTION_NOT_FOUND(404, "Question not found"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    QUESTION_REPLY_NOT_FOUND(404, "Question reply not found"),
    ANSWER_REPLY_NOT_FOUND(404, "Answer reply not found"),

    ;
    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
