package com.sivalabs.bookstore.webapp.client;

import com.sivalabs.bookstore.webapp.ApplicationProperties;
import com.sivalabs.bookstore.webapp.client.catalog.CatalogServiceClient;
import com.sivalabs.bookstore.webapp.client.order.OrderServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class ClientsConfig {

    private final ApplicationProperties properties;

    public ClientsConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    CatalogServiceClient catalogServiceClient() {
        var restClient = RestClient.create(properties.apiGatewayUrl());
        var proxyFactory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return proxyFactory.createClient(CatalogServiceClient.class);
    }

    @Bean
    OrderServiceClient orderServiceClient() {
        var restClient = RestClient.create(properties.apiGatewayUrl());
        var proxyFactory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return proxyFactory.createClient(OrderServiceClient.class);
    }
}
