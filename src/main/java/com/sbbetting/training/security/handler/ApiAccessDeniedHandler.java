package com.sbbetting.training.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbbetting.training.error.AuthorizationError;
import com.sbbetting.training.error.base.ServiceError;
import com.sbbetting.training.error.response.ApiError;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException exception) throws IOException, ServletException {
        final ServiceError error = AuthorizationError.ACCESS_DENIED;
        response.setStatus(error.getCode());
        final ApiError apiError = new ApiError(error.getCode(), error.getStatus().getReasonPhrase(), exception.getMessage(), System.currentTimeMillis());
        objectMapper.writer().writeValue(response.getOutputStream(), apiError);
    }
}
