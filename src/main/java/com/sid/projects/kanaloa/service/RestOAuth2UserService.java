package com.sid.projects.kanaloa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.projects.kanaloa.domain.models.GuildMember;
import com.sid.projects.kanaloa.domain.models.GuildRole;
import com.sid.projects.kanaloa.domain.repositories.DiscordBotGuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.*;

@Component
public class RestOAuth2UserService implements OAuth2UserService
{
    private final RestOperations restOperations;
    private final DiscordBotGuildRepository discordBotGuildRepository;

    @Autowired
    public RestOAuth2UserService(RestOperations restOperations, DiscordBotGuildRepository discordBotGuildRepository)
    {
        this.restOperations = restOperations;
        this.discordBotGuildRepository = discordBotGuildRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException
    {
        String userInfoUrl = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUri();
        ParameterizedTypeReference<Map<String, Object>> typeReference =
                new ParameterizedTypeReference<Map<String, Object>>() {};
        ResponseEntity<Map<String, Object>> responseEntity = restOperations.exchange(userInfoUrl, HttpMethod.GET,
                new HttpEntity<>(bearerTokenHeaders(oAuth2UserRequest)), typeReference);
        Map<String, Object> userAttributes = responseEntity.getBody();

        try
        {
            GuildMember guildMember = discordBotGuildRepository
                    .findMemberById(Long.parseLong((String) userAttributes.get("id")));
            List<GuildRole> guildRoles = discordBotGuildRepository.findAllGuildRoles();
            List<GrantedAuthority> authoritiesList = new ArrayList<>();

            guildMember.getRoles().forEach(roleId -> {
                SimpleGrantedAuthority simpleGrantedAuthority =
                        new SimpleGrantedAuthority("ROLE_" + Objects.requireNonNull(guildRoles.stream()
                                .filter(guildRole -> guildRole.getId().equals(roleId)).findFirst().orElse(null))
                                .getName().toUpperCase());
                authoritiesList.add(simpleGrantedAuthority);
            });

            Set<GrantedAuthority> authorities = new HashSet<>(authoritiesList);
            return new DefaultOAuth2User(authorities, userAttributes, oAuth2UserRequest.getClientRegistration()
                    .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Build and return http headers with extracted bearer token for discord oAuth
     * @param oAuth2UserRequest
     * @return headers containing the bearer token and user agent
     */
    private HttpHeaders bearerTokenHeaders(OAuth2UserRequest oAuth2UserRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", oAuth2UserRequest.getAccessToken()
                .getTokenValue()));
        headers.set(HttpHeaders.USER_AGENT,  System.getProperty("DISCORD_BOT_USER_AGENT"));

        return headers;
    }
}