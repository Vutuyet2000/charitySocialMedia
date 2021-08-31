package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleEnum {
    ROLE_ADMIN("admin"), ROLE_USER("user");

    String type;
    RoleEnum(String s) {
        this.type = s;
    }

    @JsonValue
    public String getHashTagEnum(){
        return type;
    }
}
