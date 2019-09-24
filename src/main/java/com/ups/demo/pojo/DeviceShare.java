package com.ups.demo.pojo;

import org.springframework.stereotype.Component;

@Component
public class DeviceShare {
    private Integer intSharedId;

    private Integer intDeviceId;

    private String strSharedUserTel;

    public Integer getIntSharedId() {
        return intSharedId;
    }

    public void setIntSharedId(Integer intSharedId) {
        this.intSharedId = intSharedId;
    }

    public Integer getIntDeviceId() {
        return intDeviceId;
    }

    public void setIntDeviceId(Integer intDeviceId) {
        this.intDeviceId = intDeviceId;
    }

    public String getStrSharedUserTel() {
        return strSharedUserTel;
    }

    public void setStrSharedUserTel(String strSharedUserTel) {
        this.strSharedUserTel = strSharedUserTel == null ? null : strSharedUserTel.trim();
    }
}