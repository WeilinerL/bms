package com.ups.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Data {
    private Integer intDataId;

    private String strMacAddress;

    private String strDataName;

    private Double doubleReadValues;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateReadTime;

    private Integer intBatteryId;

    public Integer getIntDataId() {
        return intDataId;
    }

    public void setIntDataId(Integer intDataId) {
        this.intDataId = intDataId;
    }

    public String getStrMacAddress() {
        return strMacAddress;
    }

    public void setStrMacAddress(String strMacAddress) {
        this.strMacAddress = strMacAddress == null ? null : strMacAddress.trim();
    }

    public String getStrDataName() {
        return strDataName;
    }

    public void setStrDataName(String strDataName) {
        this.strDataName = strDataName == null ? null : strDataName.trim();
    }

    public Double getDoubleReadValues() {
        return doubleReadValues;
    }

    public void setDoubleReadValues(Double doubleReadValues) {
        this.doubleReadValues = doubleReadValues;
    }

    public Date getDateReadTime() {
        return dateReadTime;
    }

    public void setDateReadTime(Date dateReadTime) {
        this.dateReadTime = dateReadTime;
    }

    public Integer getIntBatteryId() {
        return intBatteryId;
    }

    public void setIntBatteryId(Integer intBatteryId) {
        this.intBatteryId = intBatteryId;
    }
}