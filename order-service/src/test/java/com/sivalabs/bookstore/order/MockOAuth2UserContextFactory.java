package com.sivalabs.bookstore.order;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

public class MockOAuth2UserContextFactory implements WithSecurityContextFactory<WithMockOAuth2User> {

    @Override
    public SecurityContext createSecurityContext(WithMockOAuth2User annotation) {
        var username = StringUtils.hasLength(annotation.username()) ? annotation.username() : annotation.value();
        if (username == null) {
            throw new IllegalArgumentException(
                    annotation + " cannot have null username on both `username` and `value` properties");
        }

        var authorities = Arrays.stream(annotation.roles())
                .map(role -> {
                    if (role.startsWith("ROLE_")) {
                        throw new IllegalArgumentException("Roles cannot start with prefix ROLE_, got " + role);
                    }
                    return new SimpleGrantedAuthority("ROLE_" + role);
                })
                .toList();

        var claims = Map.of("preferred_username", username, "userId", annotation.id(), "realm_access", authorities);
        var headers = Map.<String, Object>of("header", "mock");
        var jwt = new Jwt("mock-jwt-token", Instant.now(), Instant.now().plusSeconds(300), headers, claims);
        var authentication = new JwtAuthenticationToken(jwt, authorities);

        var securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        return securityContext;
    }
}
