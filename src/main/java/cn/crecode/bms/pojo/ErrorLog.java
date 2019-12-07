package cn.crecode.bms.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ErrorLog {
    private Integer intId;

    private String intDeviceId;

    private String strContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateLogDate;

    public Integer getIntId() {
        return intId;
    }

    public void setIntId(Integer intId) {
        this.intId = intId;
    }

    public String getIntDeviceId() {
        return intDeviceId;
    }

    public void setIntDeviceId(String intDeviceId) {
        this.intDeviceId = intDeviceId == null ? null : intDeviceId.trim();
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent == null ? null : strContent.trim();
    }

    public Date getDateLogDate() {
        return dateLogDate;
    }

    public void setDateLogDate(Date dateLogDate) {
        this.dateLogDate = dateLogDate;
    }
}