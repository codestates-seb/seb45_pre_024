package com.day24.preProject.auth.handler;

import com.day24.preProject.auth.utils.ErrorResponser;
import com.day24.preProject.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        sendErrorResponse(response);
    }
    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        ErrorResponser.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);
    }
}
