package com.day24.preProject.response;

import com.day24.preProject.exception.ExceptionCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ValidationErrorResponse {
    private int status_code;
    private List<Object> message;

    public ValidationErrorResponse(int status_code, List<Object> message) {
        this.status_code = status_code;
        this.message = message;
    }

    public static ValidationErrorResponse of (BindingResult bindingResult){
        return new ValidationErrorResponse(ExceptionCode.INVALID_BODY_VALUE.getStatus(), FieldError.of(bindingResult));
    }
    public static ValidationErrorResponse of (Set<ConstraintViolation<?>> violations){
        return new ValidationErrorResponse(ExceptionCode.INVALID_REQUEST_PARAMETER.getStatus(), ConstraintViolationError.of(violations));
    }
    @Getter
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;

        public FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }
        public static List<Object> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(err -> new FieldError(
                            err.getField(),
                            err.getRejectedValue() == null ? "":err.getRejectedValue().toString(),
                            err.getDefaultMessage())).collect(Collectors.toList());
        }
    }
    @Getter
    public static class ConstraintViolationError {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        public ConstraintViolationError(String propertyPath, Object rejectedValue, String reason) {
            this.propertyPath = propertyPath;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }
        public static List<Object> of (Set<ConstraintViolation<?>> constraintViolations) {
            return constraintViolations.stream()
                    .map(violation -> new ConstraintViolationError(
                            violation.getPropertyPath().toString(),
                            violation.getInvalidValue().toString(),
                            violation.getMessage()))
                    .collect(Collectors.toList());
        }
    }
}
