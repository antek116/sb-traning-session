package com.sbbetting.training.service;

import com.sbbetting.training.database.model.User;
import com.sbbetting.training.database.repository.UserRepository;
import com.sbbetting.training.exception.ServiceException;
import com.sbbetting.training.model.dto.response.TokenDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationServiceTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    private AuthorizationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new AuthorizationService(tokenService, userRepository);
    }

    @Test
    void shouldDoNotReturnNullValue_andReturnTOkenDTO() {
        //given
        final String email = "abc@gmail.com";
        final User user = new User("", email, "", null);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(tokenService.createToken(user.getUserId())).thenReturn("asiahudhsauhduasuad");

        //when
        final TokenDTO token = service.createToken(email, "");

        //
        assertNotNull(token);
    }

    @Test
    void shouldReturnTokenInTokenDTO_whenTokenDTOIsCreated() {
        //given
        final String email = "abc@gmail.com";
        final User user = new User("", email, "", null);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(tokenService.createToken(user.getUserId())).thenReturn("asiahudhsauhduasuad");

        //when
        final TokenDTO token = service.createToken(email, "");

        //then
        assertNotNull(token);
        assertNotNull(token.getToken());
        assertFalse(token.getToken().isEmpty());
    }

    @Test
    void shouldThrowServiceException_whenPasswordNotMatch() {
        //given
        final String email = "abc@gmail.com";
        final User user = new User("", email, "Dupa123", null);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //when
        final ServiceException exception = assertThrows(ServiceException.class, () -> service.createToken(email, ""));

        //then
        assertNotNull(exception);

    }
}