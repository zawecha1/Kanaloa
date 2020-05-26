package com.sid.projects.kanaloa.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Guild
{
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private String icon;
    @Getter
    private String description;

    @Getter
    @JsonProperty(value = "owner_id")
    private String ownerId;

    @Getter
    private String region;

    @Getter
    private List<GuildRole> roles;
}
