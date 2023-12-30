package com.thevoid.api.models.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoidRolesEnum {
    VOID_DWELLER("VOID_DWELLER", 1L),
    VOID_LORD("VOID_LORD", 2L),
    VOID_WHISPER("VOID_WHISPER", 3L),
    VOID_JAILER("VOID_JAILER", 4L);
    public final String role;
    public final Long id;

}
