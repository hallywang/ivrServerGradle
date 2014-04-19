package com.hally.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * function description.
 * <p/>
 * <p><h2>Change History</h2>
 * <p/>
 * 2014/4/17 | hallywang | created
 * <p/>
 * </p>
 *
 * @author hallywang
 * @version 1.0.0
 */

@Entity
@Table(name = "t_mobile_info")
public class MobileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mobile_prefix")
    private String mobilePrefix;
    @Column(name = "province_id")
    private Integer provinceId;
    @Column(name = "province_name")
    private String provinceName;
    @Column(name = "city_id")
    private Integer cityId;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "county_id")
    private Integer countyId;
    @Column(name = "county_name")
    private String  countyName;
    @Column(name = "rese0")
    private String rese0;
    @Column(name = "rese1")
    private String rese1;
    @Column(name = "rese2")
    private String rese2;
    @Column(name = "in_date")
    private Date inDate ;
    @Column(name = "filename")
    private String filename;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobilePrefix() {
        return mobilePrefix;
    }

    public void setMobilePrefix(String mobilePrefix) {
        this.mobilePrefix = mobilePrefix;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getRese0() {
        return rese0;
    }

    public void setRese0(String rese0) {
        this.rese0 = rese0;
    }

    public String getRese1() {
        return rese1;
    }

    public void setRese1(String rese1) {
        this.rese1 = rese1;
    }

    public String getRese2() {
        return rese2;
    }

    public void setRese2(String rese2) {
        this.rese2 = rese2;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
