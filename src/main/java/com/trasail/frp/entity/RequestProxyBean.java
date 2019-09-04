package com.trasail.frp.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class RequestProxyBean implements Serializable {

    private static final long serialVersionUID = 3778677345383029975L;

    private String proxyName;
    private String tracklink;

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }

    public String getTracklink() {
        return tracklink;
    }

    public void setTracklink(String tracklink) {
        this.tracklink = tracklink;
    }
}
