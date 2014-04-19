package com.hally.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-11 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Entity
@Table(name = "t_ivr_user_logs")
public class IvrUserLogs implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String msisdn;

    @Column(name = "operate_id")
    private String operateId;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "touch_button")
    private String touchButton;

    @Column(name = "call_number")
    private String callNumber;

    @Column(name = "call_time")
    private Date callTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "call_second")
    private Long callSecond;//持续了多少秒

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "province_name")
    String provinceName; //省份

    @Column(name = "city_name")
    String cityName ; //城市

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getCallSecond() {
        return callSecond;
    }

    public void setCallSecond(Long callSecond) {
        this.callSecond = callSecond;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getTouchButton() {
        return touchButton;
    }

    public void setTouchButton(String touchButton) {
        this.touchButton = touchButton;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
