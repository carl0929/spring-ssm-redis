package com.trasail.frp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SafeRequestOutlayerObj implements Serializable {
    private static final long serialVersionUID = -6671901280434766381L;
    /**
     * 渠道号，明文
     */
    @Expose
    @SerializedName("c")
    public String mChannelId;

    /**
     * AES秘钥被加密后的密文
     */
    @Expose
    @SerializedName("k")
    public String mAesKeyCipher;

    /**
     * 业务数据，被AES明文的秘钥加密过，加密前是json
     */
    @Expose
    @SerializedName("d")
    public String mBussinessData;
}
