package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HashTagEnum {
    nghethuat("Nghệ thuật"),
    vatpham("Vật phẩm"),
    vanhoc("Văn học");

    String type;
    HashTagEnum(String s) {
        this.type = s;
    }

    //{"hashTag":"vatpham"}
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING) //ignore @JsonProperties
    public static HashTagEnum getEnumFromValue(String value) {
        for (HashTagEnum enumHashTag : HashTagEnum.values()) {
            if (enumHashTag.name().equals(value)) {
                return enumHashTag;
            }
        }
        throw new IllegalArgumentException();
    }

    @JsonValue
    public String getHashTagEnum(){
        return type;
    }
}
