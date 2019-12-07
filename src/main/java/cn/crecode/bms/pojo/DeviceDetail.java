package cn.crecode.bms.pojo;

import org.springframework.stereotype.Component;

@Component
public class DeviceDetail {
    private Integer intDetailId;

    private String strDeviceBrand;

    private String strDeviceModel;

    public Integer getIntDetailId() {
        return intDetailId;
    }

    public void setIntDetailId(Integer intDetailId) {
        this.intDetailId = intDetailId;
    }

    public String getStrDeviceBrand() {
        return strDeviceBrand;
    }

    public void setStrDeviceBrand(String strDeviceBrand) {
        this.strDeviceBrand = strDeviceBrand == null ? null : strDeviceBrand.trim();
    }

    public String getStrDeviceModel() {
        return strDeviceModel;
    }

    public void setStrDeviceModel(String strDeviceModel) {
        this.strDeviceModel = strDeviceModel == null ? null : strDeviceModel.trim();
    }
}