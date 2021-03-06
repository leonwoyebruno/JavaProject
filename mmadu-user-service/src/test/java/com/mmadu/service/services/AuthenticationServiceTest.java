package com.mmadu.service.services;

import static com.mmadu.service.models.AuthenticationStatus.AUTHENTICATED;
import static com.mmadu.service.models.AuthenticationStatus.DOMAIN_INVALID;
import static com.mmadu.service.models.AuthenticationStatus.PASSWORD_INVALID;
import static com.mmadu.service.models.AuthenticationStatus.USERNAME_INVALID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

import com.mmadu.service.entities.AppUser;
import com.mmadu.service.models.AuthenticateRequest;
import com.mmadu.service.models.AuthenticateResponse;
import com.mmadu.service.repositories.AppDomainRepository;
import com.mmadu.service.repositories.AppUserRepository;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final String DOMAIN_ID = "domain-id";

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private AppDomainRepository appDomainRepository;

    private AppUser user;
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        user.setDomainId(DOMAIN_ID);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        lenient().doReturn(Optional.of(user)).when(appUserRepository).findByUsernameAndDomainId(USERNAME, DOMAIN_ID);
        lenient().doReturn(true).when(appDomainRepository).existsById(DOMAIN_ID);
        lenient().doReturn(false).when(appDomainRepository).existsById("invalid-domain");
        authenticationService = new AuthenticationServiceImpl();
        authenticationService.setAppUserRepository(appUserRepository);
        authenticationService.setAppDomainRepository(appDomainRepository);
    }

    @Test
    void givenCorrectUserNameAndPasswordWhenAuthenticateThenReturnAuthenticated() {
        AuthenticateResponse response = authenticationService.authenticate(DOMAIN_ID,
                new AuthenticateRequest(USERNAME, PASSWORD));
        assertThat(response.getStatus(), equalTo(AUTHENTICATED));
    }

    @Test
    void givenCorrectUserNameAndIncorrectPasswordWhenAuthenticateThenReturnInvalidPassword() {
        AuthenticateResponse response = authenticationService.authenticate(DOMAIN_ID,
                new AuthenticateRequest(USERNAME, "invalid-password"));
        assertThat(response.getStatus(), equalTo(PASSWORD_INVALID));
    }

    @Test
    void givenIncorrectUserNameWhenAuthenticateThenReturnInvalidUsername() {
        AuthenticateResponse response = authenticationService.authenticate(DOMAIN_ID,
                new AuthenticateRequest("invalid-username", "invalid-password"));
        assertThat(response.getStatus(), equalTo(USERNAME_INVALID));
    }

    @Test
    void givenIncorrectDomainIdhenAuthenticateThenReturnInvalidDomain() {
        AuthenticateResponse response = authenticationService.authenticate("invalid-domain",
                new AuthenticateRequest("invalid-username", "invalid-password"));
        assertThat(response.getStatus(), equalTo(DOMAIN_INVALID));
    }

}