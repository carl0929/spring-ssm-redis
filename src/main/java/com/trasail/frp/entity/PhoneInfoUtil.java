package com.trasail.frp.entity;

import com.trasail.frp.common.utils.Base64Util;

import java.text.MessageFormat;

public class PhoneInfoUtil {
    /**
     * 手机身份标识错误值：{@value}
     */
    public static final String M_ERR_IDENTIFY_STR = "ERROR";

    /**
     * 根据手机信息生成唯一身份标识
     * @param info
     * @return 不为null
     */
    public static String generatePhoneIdentifyStr(PhoneInfo info) {
        if(null == info) {
            return M_ERR_IDENTIFY_STR;
        }

        String str = MessageFormat.format("{0}_{1}_{2}_{3}",
                info.getIMEI(), info.getMac(), info.getSerialNo(), info.getAndroidId());
        byte[] data = MessageDigestUtil.getSHA1Digest(str.getBytes());
        if(null == data) {
            return M_ERR_IDENTIFY_STR;
        }

        data = Base64Util.bas64Encode(data);
        if(null == data) {
            return M_ERR_IDENTIFY_STR;
        }

        return new String(data);
    }
}

