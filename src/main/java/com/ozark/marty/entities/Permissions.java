package com.ozark.marty.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {

    ADMIN_READ("admin:get"),
    ADMIN_UPDATE("admin:put"),
    ADMIN_CREATE("admin:post"),
    ADMIN_DELETE("admin:delete");

    @Getter
    private final String permission;
}
