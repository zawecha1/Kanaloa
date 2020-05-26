package com.sid.projects.kanaloa.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GuildMember
{
    @Getter
    @JsonProperty(value = "user")
    private JsonNode user;

    @Getter
    private List<String> roles;

    public String getId()
    {
        return user.get("id").asText();
    }

    public String getUsername()
    {
        return user.get("username").asText();
    }

    public String getAvatar()
    {
        return user.get("avatar").asText();
    }

    public String getDiscriminator()
    {
        return user.get("discriminator").asText();
    }
}
