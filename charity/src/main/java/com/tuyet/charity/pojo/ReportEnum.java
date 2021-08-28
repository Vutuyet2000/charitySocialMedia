package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ReportEnum {
    khongthanhtoan("Người dùng không thanh toán"),
    tungukhongdung("Người dùng dùng từ ngữ không đúng đắn");

    private String content;
    ReportEnum(String s) {
        this.content = s;
    }

    //Deserialize enum (content: "khongthanhtoan")
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ReportEnum getEnumFromValue(String value) {
        for (ReportEnum enumReport : ReportEnum.values()) {
            if (enumReport.name().equals(value)) {
                return enumReport;
            }
        }
        throw new IllegalArgumentException();
    }

    //Serialize enum ("content":"Người dùng không thanh toán")
    @JsonValue
    public String getReportEnumContent() {
        return content;
    }
}
