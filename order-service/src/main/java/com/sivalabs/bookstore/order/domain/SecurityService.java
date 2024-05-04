package com.sivalabs.bookstore.order.domain;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoggedInUserName() {
        var authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var jwt = (Jwt) authentication.getPrincipal();

        return jwt.getClaimAsString("preferred_username");
    }
}
