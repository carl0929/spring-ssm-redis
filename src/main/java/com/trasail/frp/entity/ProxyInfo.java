package com.trasail.frp.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

@Component
public class ProxyInfo implements Serializable {


    private static final long serialVersionUID = -8436461208217464811L;


    private int id;
    private String uid;
    private String proxyName;
    private int isOnline;
    private String country;
    private String op;

    private Timestamp createTime;
    private int env;

    @Override
    public String toString() {
        return "ProxyInfo{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", proxyName='" + proxyName + '\'' +
                ", isOnline=" + isOnline +
                ", country='" + country + '\'' +
                ", op='" + op + '\'' +
                ", createTime=" + createTime +
                ", env=" + env +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public int getEnv() {
        return env;
    }

    public void setEnv(int env) {
        this.env = env;
    }
}
