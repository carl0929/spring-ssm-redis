package com.trasail.frp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProxyUserBeans implements Serializable {
    private static final long serialVersionUID = -2605173181980074936L;

    public static class Request {
        @Expose
        @SerializedName("s_name")
        private String sName;

        @Expose
        @SerializedName("s_pwd")
        private String sPwd;

        @Expose
        @SerializedName("country")
        private String country;

        @Expose
        @SerializedName("op")
        private String op;

        @Expose
        @SerializedName("env")
        private int env;

        @Expose
        @SerializedName("user_id")
        private String userId;

        @Expose
        @SerializedName("app_name")
        private String appName;

        @Expose
        @SerializedName("sid")
        private String sessionId;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
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

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public int getEnv() {
            return env;
        }

        public void setEnv(int env) {
            this.env = env;
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

        @Override
        public String toString() {
            return "Request{" + "sName='" + sName + '\'' + ", sPwd='" + sPwd + '\'' + ", country='" + country + '\'' + ", op='" + op + '\'' + ", env=" + env + ", userId='" + userId + '\'' + ", appName='" + appName + '\'' + ", sessionId='" + sessionId + '\'' + '}';
        }
    }

    public static class Response {
        @Expose
        @SerializedName("proxy_name")
        private String proxyName;

      /*  @Expose
        @SerializedName("tfid")
        private String mTaskFlowId;

        @Expose
        @SerializedName("sid")
        private String mScriptId;

        @Expose
        @SerializedName("script")
        private String  mScriptInfo;*/

        @Expose
        @SerializedName("error")
        private String mErrorInfo;

        public String getProxyName() {
            return proxyName;
        }

        public void setProxyName(String proxyName) {
            this.proxyName = proxyName;
        }

        public String getErrorInfo() {
            return mErrorInfo;
        }

        public void setErrorInfo(String mErrorInfo) {
            this.mErrorInfo = mErrorInfo;
        }

        @Override
        public String toString() {
            return "Response{" + "proxyName='" + proxyName + '\'' + ", mErrorInfo='" + mErrorInfo + '\'' + '}';
        }
    }

}
