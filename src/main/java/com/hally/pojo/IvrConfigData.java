package com.hally.pojo;

import com.hally.model.AbstractModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * function description.
 * <p/>
 * <p><h2>Change History</h2>
 * <p/>
 * 14-3-7 | hallywang | created
 * <p/>
 * </p>
 *
 * @author hallywang
 * @version 1.0.0
 */
@Entity
@Table(name="t_ivr_config_data")
public class IvrConfigData extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @Column(name="operate_id")
    private  String operateId;

    @Column(name="content")
    private  String content;

    @Column(name="config_type")
    private  String configType; // 0 单一数据，1 多条轮询， 2 多条随机

    @Column(name="param_a")
    private  String paramA;

    @Column(name="service_id")
    private String serviceId;

    @Column(name="status")
    private Integer status;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getParamA() {
        return paramA;
    }

    public void setParamA(String paramA) {
        this.paramA = paramA;
    }
}
