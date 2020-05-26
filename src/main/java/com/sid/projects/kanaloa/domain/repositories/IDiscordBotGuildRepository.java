package com.sid.projects.kanaloa.domain.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.sid.projects.kanaloa.domain.models.GuildMember;
import com.sid.projects.kanaloa.domain.models.GuildRole;

import java.util.List;

public interface IDiscordBotGuildRepository
{
    List<GuildRole> findAllGuildRoles();
    GuildMember findMemberById(long memberId) throws JsonProcessingException;
}
