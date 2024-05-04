package com.sivalabs.bookstore.webapp.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public SecurityHelper(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    public String getAccessToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            var client = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
            return client.getAccessToken().getTokenValue();
        }
        return null;
    }
}
