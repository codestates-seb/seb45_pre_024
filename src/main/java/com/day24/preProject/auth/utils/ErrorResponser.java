package com.day24.preProject.auth.utils;

import com.day24.preProject.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorResponser {
    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorResponse.getStatus_code());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
