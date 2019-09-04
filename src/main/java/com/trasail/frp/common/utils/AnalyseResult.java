package com.trasail.frp.common.utils;

import java.io.Serializable;

public class AnalyseResult<RequestType, ResponseType> extends RequestAnalyseUtil.CodecResult implements Serializable {
    /**
     * 渠道号
     */
    public String mChanId;
    /**
     * 请求中解析得出的对象
     */
    public RequestType mRequestObj;
    /**
     * 响应数据的对象
     */
    public ResponseType mResponseObj;

    public static <RequestType, ResponseType> AnalyseResult<RequestType, ResponseType> getDefaultObj() {
        return new AnalyseResult<RequestType, ResponseType>();
    }
}
