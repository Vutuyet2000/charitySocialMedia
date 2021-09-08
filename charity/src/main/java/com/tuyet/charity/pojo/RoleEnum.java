package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleEnum {
    ROLE_ADMIN("admin"),
    ROLE_USER("user");

    String type;
    RoleEnum(String s) {
        this.type = s;
    }

//    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
//    public static RoleEnum getDesrializerEnum(String content){
//        for(RoleEnum ro : RoleEnum.values()){
//            if(ro.type == content){
//                return ro;
//            }
//        }
//        return null;
//    }

    @JsonValue
    public String getHashTagEnum(){
        return type;
    }
}
