package com.trasail.frp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SafeResponseObj implements Serializable {
    private static final long serialVersionUID = 1971405625168106619L;
    /**
     * 错误码，见{@link ErrorCodes}
     */
    @Expose
    @SerializedName("cd")
    public int mErrorCode;

    /**
     * 响应数据
     */
    @Expose
    @SerializedName("c")
    public String mResponseContent;
}
