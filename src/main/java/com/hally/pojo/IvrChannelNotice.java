package com.hally.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 2014/4/12 | hallywang | created <p/> </p>
 *
 * @author hallywang
 * @version 1.0.0
 */
@Entity
@Table(name = "t_ivr_channel_notice")
public class IvrChannelNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="channel_code")
    private String channelCode; //渠道号，可以使用拨打号

    @Column(name="service_id")
    private String serviceId;

    @Column(name="notice_url")
    private String noticeUrl;
    private Integer status = 1; // 0 失效,1 有效

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
