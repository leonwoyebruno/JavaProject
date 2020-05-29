package com.mmadu.identity.providers.users;

import com.mmadu.identity.config.RestResourceConfig;
import com.mmadu.identity.models.users.Domain;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.MediaType;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
@SpringBootTest(classes = {
        RestResourceConfig.class,
        DomainServiceImpl.class
})
@MockServerSettings(ports = 18000)
@TestPropertySource(properties = {
        "mmadu.userService=http://localhost:18000",
        "mmadu.domainKey=2222"
})
class DomainServiceImplTest {
    @Autowired
    private DomainService domainService;
    @Value("classpath:responses/get-domain-by-id.json")
    private Resource domainFile;

    @Test
    void findById(MockServerClient server) throws Exception {
        HttpRequest request = mockDomainApi(server);
        Domain domain = domainService.findById("1").block();
        assertAll(
                () -> assertEquals("new-domain", domain.getName()),
                () -> server.verify(request, VerificationTimes.exactly(1))
        );
    }

    private HttpRequest mockDomainApi(MockServerClient server) throws IOException {
        HttpRequest request = request()
                .withMethod("GET")
                .withPath("/appDomains/1")
                .withHeader("domain-auth-token", "2222");
        server.when(
                request
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(
                                        IOUtils.toString(domainFile.getInputStream(), StandardCharsets.UTF_8),
                                        MediaType.JSON_UTF_8
                                )
                );
        return request;
    }
}