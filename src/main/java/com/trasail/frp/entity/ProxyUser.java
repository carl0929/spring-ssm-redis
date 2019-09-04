package com.trasail.frp.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

@Component
public class ProxyUser implements Serializable {
    private static final long serialVersionUID = 6464185626462928698L;

    private int id;
    private String sName;
    private String sPwd;
    private String userId;
    private String appName;
    private String country;
    private int env;
    private String op;
    private Timestamp createTime;

    @Override
    public String toString() {
        return "ProxyUser{" + "id=" + id + ", sName='" + sName + '\'' + ", sPwd='" + sPwd + '\'' + ", userId='" + userId + '\'' + ", appName='" + appName + '\'' + ", country='" + country + '\'' + ", env=" + env + ", op='" + op + '\'' + ", createTime=" + createTime + '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPwd() {
        return sPwd;
    }

    public void setsPwd(String sPwd) {
        this.sPwd = sPwd;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getEnv() {
        return env;
    }

    public void setEnv(int env) {
        this.env = env;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
