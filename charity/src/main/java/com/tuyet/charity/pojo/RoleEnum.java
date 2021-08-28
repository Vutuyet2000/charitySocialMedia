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

//    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
//    public static RoleEnum getEnumFromValue(@JsonProperty("role") String value) {
//        for (RoleEnum enumRole : RoleEnum.values()) {
//            if (enumRole.name().equals(value)) {
//                return enumRole;
//            }
//        }
//        throw new IllegalArgumentException();
//    }

    @JsonValue
    public String getHashTagEnum(){
        return type;
    }
}
