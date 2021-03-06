package com.mmadu.identity.repositories;

import com.mmadu.identity.entities.Token;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

    Optional<Token> findByTokenString(@Param("tokenString") String token);

    Optional<Token> findByClientIdentifierAndTokenString(@Param("clientIdentifier") String clientIdentifier,
                                                         @Param("tokenString") String token);

    List<Token> findByGrantAuthorizationIdAndTypeAndActive(
            @Param("grantAuthorizationId") String grantAuthorizationId,
            @Param("type") String type,
            @Param("active") Boolean active
    );

    List<Token> findByGrantAuthorizationId(@Param("grantAuthorizationId") String grantAuthorizationId);

    @DeleteQuery("{ $or: [ {expiryTime: { $lt: ?0}}, {revokedTime: { $lt: ?0}}, {expired: true}, {revoked: true} ] }")
    void deleteExpiredAndRevokedTokens(ZonedDateTime time);
}
