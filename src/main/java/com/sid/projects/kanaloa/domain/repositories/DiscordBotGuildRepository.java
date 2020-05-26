package com.sid.projects.kanaloa.domain.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.projects.kanaloa.domain.models.GuildMember;
import com.sid.projects.kanaloa.domain.models.GuildRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Repository
public class DiscordBotGuildRepository implements IDiscordBotGuildRepository
{

    private final RestTemplate restTemplate;
    private final HttpHeaders discordBotAuthHeaders;

    private final String guildId = System.getProperty("GUILD_ID");

    @Autowired
    public DiscordBotGuildRepository(
            @Qualifier("discordAuthenticateWithBotToken") HttpHeaders discordBotAuthHeaders,
            RestTemplate restTemplate)
    {
        this.discordBotAuthHeaders = discordBotAuthHeaders;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<GuildRole> findAllGuildRoles()
    {
        RequestEntity<JsonNode> requestEntity =
                new RequestEntity<>(discordBotAuthHeaders, HttpMethod.GET, URI.create("https://discordapp.com/api/guilds/" + guildId));

        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(requestEntity, JsonNode.class);

        if (responseEntity.getBody() != null && responseEntity.getBody().has("roles")) {
            try
            {
                List<GuildRole> roleList = new ObjectMapper().readValue(responseEntity.getBody().get("roles").toString(), new TypeReference<List<GuildRole>>() {});
                return roleList;
            } catch (JsonProcessingException e)
            {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public GuildMember findMemberById(long memberId) throws JsonProcessingException
    {
        RequestEntity<JsonNode> requestEntity =
                new RequestEntity<>(discordBotAuthHeaders, HttpMethod.GET, URI.create("https://discordapp.com/api/guilds/" + guildId + "/members/" + memberId));
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(requestEntity, JsonNode.class);
        return new ObjectMapper().treeToValue(responseEntity.getBody(), GuildMember.class);
    }
}
