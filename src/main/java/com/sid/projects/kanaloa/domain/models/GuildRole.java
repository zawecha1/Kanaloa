package com.sid.projects.kanaloa.domain.models;

import lombok.Getter;


public class GuildRole
{
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private long permissions;
    @Getter
    private long position;
    @Getter
    private long color;
    @Getter
    private boolean hoist;
    @Getter
    private boolean managed;
    @Getter
    private boolean mentionable;
}
