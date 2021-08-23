package com.sbbetting.training.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbbetting.training.database.model.User;
import com.sbbetting.training.error.ApiServiceError;
import com.sbbetting.training.error.TokenError;
import com.sbbetting.training.error.base.ServiceError;
import com.sbbetting.training.error.response.ApiError;
import com.sbbetting.training.exception.ServiceException;
import com.sbbetting.training.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger LOG = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    private final TokenService service;
    private final ObjectMapper objectMapper;

    @Autowired
    public RestAuthenticationEntryPoint(TokenService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        ApiError apiError = null;
        try {
            final String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String token = service.extractBearerToken(tokenHeader);

            if (service.isTokenExpired(token)) {
                throw new ServiceException(TokenError.ACCESS_TOKEN_EXPIRED_ERROR);
            }

            final User user = service.getUser(token);
            if (user == null) {
                throw new ServiceException(TokenError.INVALID_TOKEN_ERROR);
            }

            throw new ServiceException(exception.getMessage(), HttpStatus.UNAUTHORIZED);

        } catch (ServiceException e) {
            LOG.trace("Authorization exception caught, Reason : {}", e.getMessage());
            apiError = new ApiError(e.getCode(), e.getStatus().getReasonPhrase(), e.getMessage(), e.getTimestamp());
        } catch (Exception e) {
            LOG.trace("Authorization exception caught, Reason : {}", e.getMessage(), e);
            final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            apiError = new ApiError(status.value(), status.getReasonPhrase(), e.getMessage(), System.currentTimeMillis());
        } finally {
            if (apiError != null) {
                sendApiErrorResponse(apiError, response);
            } else {
                LOG.warn("Can't send specified error response to user, internal service exception will be sent");
                sendInternalServiceErrorResponse(response);
            }
        }
    }

    private void sendApiErrorResponse(ApiError apiError,
                                      HttpServletResponse response) throws IOException {
        response.setStatus(apiError.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), apiError);
    }

    private void sendInternalServiceErrorResponse(HttpServletResponse response) throws IOException {
        final ServiceError error = ApiServiceError.INTERNAL_SERVICE_ERROR;
        response.setStatus(error.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final ApiError apiError = new ApiError(error.getCode(), error.getStatus().getReasonPhrase(), error.getMessage(), System.currentTimeMillis());
        objectMapper.writeValue(response.getWriter(), apiError);
    }
}
