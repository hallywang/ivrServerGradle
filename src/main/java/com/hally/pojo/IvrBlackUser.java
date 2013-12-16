package com.hally.pojo;





import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-11-21 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Entity
@Table(name="t_ivr_black_user")
public class IvrBlackUser implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id ;

    @Column(name="user_type")
    private Integer userType; //类型

    private String  msisdn;
    private Integer scope; //0 全局；1 产品1,2 产品2，扩展。。。
    private String  comment; //备注
    private Integer status; // 0  无效，1 有效

    @Column(name="create_time")
    private Date  createTime;

    @Column(name="update_time")
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
