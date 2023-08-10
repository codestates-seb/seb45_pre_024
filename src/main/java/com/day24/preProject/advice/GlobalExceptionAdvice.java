package com.day24.preProject.advice;

import com.day24.preProject.exception.BusinessLogicException;
import com.day24.preProject.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse argNotValidException(MethodArgumentNotValidException e){
        return ErrorResponse.of(e.getBindingResult());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse constraintViolationException(ConstraintViolationException e) {
        return ErrorResponse.of(e.getConstraintViolations());
    }

    @ExceptionHandler
    public ResponseEntity buiSinessLogicException(BusinessLogicException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getExceptionCode()),
                                    HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException) {
        return ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse httpMessageNotReadableException(HttpMessageNotReadableException e){
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, "Request body is missing");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse missingErvletRequestParameterException(MissingServletRequestParameterException e){
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exception(Exception e){
        log.error("# server error", e);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
