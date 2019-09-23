package com.ups.demo.pojo;

import org.springframework.stereotype.Component;

@Component
public class DeviceShare {
    private Integer intSharedId;

    private Integer intDeviceId;

    private Integer intSharedUserId;

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

    public Integer getIntSharedUserId() {
        return intSharedUserId;
    }

    public void setIntSharedUserId(Integer intSharedUserId) {
        this.intSharedUserId = intSharedUserId;
    }
}