package com.trasail.frp.entity;

import com.trasail.frp.common.utils.ErrorCodes;

import java.io.Serializable;

public class ReturnEntity implements Serializable {
    private static final long serialVersionUID = -2393588384336659540L;

    /**
     * 结果是否成功
     */
    public boolean mIsSucceed;

    /**
     * 失败信息
     */
    public String mErrorInfo;

    /**
     * 错误码，供接口请求响应使用
     */
    public int mErrorCode;

    /**
     * 获取默认实体
     * @return 不为null
     */
    public static ReturnEntity getDefault() {
        ReturnEntity res = new ReturnEntity();
        res.mIsSucceed = false;
        res.mErrorInfo = "no initialized!";
        res.mErrorCode = ErrorCodes.M_ERR_CODE_SUCCEED;

        return res;
    }
}
