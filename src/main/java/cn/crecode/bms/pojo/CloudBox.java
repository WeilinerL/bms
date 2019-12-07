package cn.crecode.bms.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CloudBox {
    private Integer intCloudId;

    private String strCodeId;

    private String strMacAddress;

    private String strSeriesCode;

    private String strSeriesName;

    private String strVersion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateOfManufacture;

    private String strSerialNumber;

    public Integer getIntCloudId() {
        return intCloudId;
    }

    public void setIntCloudId(Integer intCloudId) {
        this.intCloudId = intCloudId;
    }

    public String getStrCodeId() {
        return strCodeId;
    }

    public void setStrCodeId(String strCodeId) {
        this.strCodeId = strCodeId == null ? null : strCodeId.trim();
    }

    public String getStrMacAddress() {
        return strMacAddress;
    }

    public void setStrMacAddress(String strMacAddress) {
        this.strMacAddress = strMacAddress == null ? null : strMacAddress.trim();
    }

    public String getStrSeriesCode() {
        return strSeriesCode;
    }

    public void setStrSeriesCode(String strSeriesCode) {
        this.strSeriesCode = strSeriesCode == null ? null : strSeriesCode.trim();
    }

    public String getStrSeriesName() {
        return strSeriesName;
    }

    public void setStrSeriesName(String strSeriesName) {
        this.strSeriesName = strSeriesName == null ? null : strSeriesName.trim();
    }

    public String getStrVersion() {
        return strVersion;
    }

    public void setStrVersion(String strVersion) {
        this.strVersion = strVersion == null ? null : strVersion.trim();
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public String getStrSerialNumber() {
        return strSerialNumber;
    }

    public void setStrSerialNumber(String strSerialNumber) {
        this.strSerialNumber = strSerialNumber == null ? null : strSerialNumber.trim();
    }
}