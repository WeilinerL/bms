package com.ups.demo.pojo;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Battery {
    private Integer intBatteryId;

    private Double doubleVoltage;

    private Double doubleTemprature;

    private Double doubleResistan;

    private Double doubleCapacity;

    private String strGroupName;

    private Date dataReadTime;

    public Integer getIntBatteryId() {
        return intBatteryId;
    }

    public void setIntBatteryId(Integer intBatteryId) {
        this.intBatteryId = intBatteryId;
    }

    public Double getDoubleVoltage() {
        return doubleVoltage;
    }

    public void setDoubleVoltage(Double doubleVoltage) {
        this.doubleVoltage = doubleVoltage;
    }

    public Double getDoubleTemprature() {
        return doubleTemprature;
    }

    public void setDoubleTemprature(Double doubleTemprature) {
        this.doubleTemprature = doubleTemprature;
    }

    public Double getDoubleResistan() {
        return doubleResistan;
    }

    public void setDoubleResistan(Double doubleResistan) {
        this.doubleResistan = doubleResistan;
    }

    public Double getDoubleCapacity() {
        return doubleCapacity;
    }

    public void setDoubleCapacity(Double doubleCapacity) {
        this.doubleCapacity = doubleCapacity;
    }

    public String getStrGroupName() {
        return strGroupName;
    }

    public void setStrGroupName(String strGroupName) {
        this.strGroupName = strGroupName == null ? null : strGroupName.trim();
    }

    public Date getDataReadTime() {
        return dataReadTime;
    }

    public void setDataReadTime(Date dataReadTime) {
        this.dataReadTime = dataReadTime;
    }
}