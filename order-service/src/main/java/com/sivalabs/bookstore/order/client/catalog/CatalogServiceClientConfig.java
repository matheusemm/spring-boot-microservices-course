package com.sivalabs.bookstore.order.client.catalog;

import com.sivalabs.bookstore.order.ApplicationProperties;
import java.time.Duration;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class CatalogServiceClientConfig {

    @Bean
    RestClient catalogServiceRestClient(ApplicationProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.catalogServiceUrl())
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(5))
                        .withReadTimeout(Duration.ofSeconds(5))))
                .build();
    }
}
