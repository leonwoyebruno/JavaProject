package com.mmadu.identity.entities;

import com.mmadu.identity.entities.token.TokenCredentials;
import com.mmadu.identity.models.Revokable;
import com.mmadu.security.api.DomainPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Document
@EqualsAndHashCode
public class Token implements DomainPayload, Revokable {
    @Id
    private String id;
    private String grantAuthorizationId;
    private String domainId;
    private String clientInstanceId;
    private String clientIdentifier;
    private String clientId;
    private String userId;
    private String username;
    private ZonedDateTime expiryTime;
    private ZonedDateTime activationTime;
    private ZonedDateTime revokedTime;
    private boolean active;
    private boolean revoked;
    private boolean expired;
    private String type;
    private String provider;
    private List<String> labels;
    private TokenCredentials credentials;
    private List<String> scopes;
    private String tokenIdentifier;
    private String tokenString;
    private String category;
    private String issuer;
    private List<String> audience;
    private String authorizationGrantType;

    @Override
    public void revoke() {
        revoked = true;
        revokedTime = ZonedDateTime.now();
    }

    boolean isInvalid() {
        ZonedDateTime now = ZonedDateTime.now();
        return expired || revoked ||
                (expiryTime != null && now.isAfter(expiryTime)) ||
                (revokedTime != null && now.isAfter(revokedTime));
    }

    public boolean isValid() {
        return !isInvalid();
    }
}