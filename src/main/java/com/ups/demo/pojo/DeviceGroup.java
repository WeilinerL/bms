package com.ups.demo.pojo;

import org.springframework.stereotype.Component;

@Component
public class DeviceGroup {
    private Integer intGroupId;

    private String strGroupName;

    public Integer getIntGroupId() {
        return intGroupId;
    }

    public void setIntGroupId(Integer intGroupId) {
        this.intGroupId = intGroupId;
    }

    public String getStrGroupName() {
        return strGroupName;
    }

    public void setStrGroupName(String strGroupName) {
        this.strGroupName = strGroupName == null ? null : strGroupName.trim();
    }
}