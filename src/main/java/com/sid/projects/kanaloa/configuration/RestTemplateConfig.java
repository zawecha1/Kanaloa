package com.sid.projects.kanaloa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    /**
     * Rest template no auth rest template.
     *
     * @since  1.0
     * @author JavaBloxx
     *
     * @return the rest template
     */
    @Bean("noAuthRestTemplate")
    public RestTemplate restTemplateNoAuth() {
        return new RestTemplate();
    }
}
