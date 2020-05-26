package com.mmadu.service.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmadu.service.models.AuthenticateRequest;
import com.mmadu.service.models.AuthenticateResponse;
import com.mmadu.service.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.mmadu.service.models.AuthenticationStatus.AUTHENTICATED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthenticationController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class
})
class AuthenticationControllerTest {

    public static final String DOMAIN_ID = "domain-0";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService service;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testAuthentication() throws Exception {
        doReturn(AuthenticateResponse.builder().status(AUTHENTICATED).build())
                .when(service).authenticate(eq(DOMAIN_ID), any());

        mockMvc.perform(post("/domains/{domainId}/authenticate", DOMAIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new AuthenticateRequest("username", "password"))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value(AUTHENTICATED.name()));
    }
}