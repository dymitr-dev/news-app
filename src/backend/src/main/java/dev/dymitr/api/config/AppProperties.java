package dev.dymitr.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppProperties {
    @Value("${app.swagger-host}")
    private String swaggerHost;

    @Value("${app.news-api.api-key}")
    private String newsApiKey;

    @Value("${app.news-api.endpoint}")
    private String newsApiEndpoint;
}