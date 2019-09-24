package com.ups.demo.pojo;

import org.springframework.stereotype.Component;

@Component
public class Device {
    private Integer intDeviceId;

    private String strCodeId;

    private String strUserTel;

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

    public String getStrUserTel() {
        return strUserTel;
    }

    public void setStrUserTel(String strUserTel) {
        this.strUserTel = strUserTel == null ? null : strUserTel.trim();
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