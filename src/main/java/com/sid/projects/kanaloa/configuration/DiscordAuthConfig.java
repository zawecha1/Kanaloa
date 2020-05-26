package com.sid.projects.kanaloa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class DiscordAuthConfig
{
    @Bean("discordAuthenticateWithBotToken")
    public HttpHeaders discordAuthenticateWithBotToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bot " + System.getProperty("BOT_TOKEN"));
        headers.add(HttpHeaders.USER_AGENT, System.getProperty("DISCORD_BOT_USER_AGENT"));

        return headers;
    }
}
