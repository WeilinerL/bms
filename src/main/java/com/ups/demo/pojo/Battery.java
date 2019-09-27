package com.ups.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Battery {
    private Integer intBatteryId;

    private Integer intDeviceId;

    private Double doubleVoltage;

    private Double doubleTemprature;

    private Double doubleResistan;

    private Double doubleCapacity;

    private Integer intBatteryPack;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dataReadTime;

    public Integer getIntBatteryId() {
        return intBatteryId;
    }

    public void setIntBatteryId(Integer intBatteryId) {
        this.intBatteryId = intBatteryId;
    }

    public Integer getIntDeviceId() {
        return intDeviceId;
    }

    public void setIntDeviceId(Integer intDeviceId) {
        this.intDeviceId = intDeviceId;
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

    public Integer getIntBatteryPack() {
        return intBatteryPack;
    }

    public void setIntBatteryPack(Integer intBatteryPack) {
        this.intBatteryPack = intBatteryPack;
    }

    public Date getDataReadTime() {
        return dataReadTime;
    }

    public void setDataReadTime(Date dataReadTime) {
        this.dataReadTime = dataReadTime;
    }
}