package com.sivalabs.bookstore.gateway.config;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

import jakarta.annotation.PostConstruct;
import java.util.stream.Collectors;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private final RouteDefinitionLocator locator;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public SwaggerConfig(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.locator = locator;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @PostConstruct
    public void init() {
        swaggerUiConfigProperties.setUrls(locator.getRouteDefinitions().collectList().block().stream()
                .filter(definition -> definition.getId().matches(".*-service"))
                .map(definition -> {
                    var name = definition.getId().replaceAll("-service", "");
                    return new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                            name, DEFAULT_API_DOCS_URL + "/" + name, null);
                })
                .collect(Collectors.toSet()));
    }
}
