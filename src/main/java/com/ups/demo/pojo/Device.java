package com.ups.demo.pojo;

import org.springframework.stereotype.Component;

@Component
public class Device {
    private Integer intDeviceId;

    private String strCodeId;

    private Integer intUserId;

    private String strDeviceName;

    private String strDeviceAddress;

    private Integer intDetailId;

    private Integer intGroupId;

    private String strStatus;

    public Integer getIntDeviceId() {
        return intDeviceId;
    }

    public void setIntDeviceId(Integer intDeviceId) {
        this.intDeviceId = intDeviceId;
    }

    public String getStrCodeId() {
        return strCodeId;
    }

    public void setStrCodeId(String strCodeId) {
        this.strCodeId = strCodeId == null ? null : strCodeId.trim();
    }

    public Integer getIntUserId() {
        return intUserId;
    }

    public void setIntUserId(Integer intUserId) {
        this.intUserId = intUserId;
    }

    public String getStrDeviceName() {
        return strDeviceName;
    }

    public void setStrDeviceName(String strDeviceName) {
        this.strDeviceName = strDeviceName == null ? null : strDeviceName.trim();
    }

    public String getStrDeviceAddress() {
        return strDeviceAddress;
    }

    public void setStrDeviceAddress(String strDeviceAddress) {
        this.strDeviceAddress = strDeviceAddress == null ? null : strDeviceAddress.trim();
    }

    public Integer getIntDetailId() {
        return intDetailId;
    }

    public void setIntDetailId(Integer intDetailId) {
        this.intDetailId = intDetailId;
    }

    public Integer getIntGroupId() {
        return intGroupId;
    }

    public void setIntGroupId(Integer intGroupId) {
        this.intGroupId = intGroupId;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus == null ? null : strStatus.trim();
    }
}