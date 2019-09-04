package com.trasail.frp.service.impl;

import com.trasail.frp.common.utils.DateTimeUtil;
import com.trasail.frp.common.utils.ErrorCodes;
import com.trasail.frp.entity.ReturnEntity;
import com.trasail.frp.entity.UserInfo;
import com.trasail.frp.service.ISessionManageService;
import com.trasail.frp.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service
public class SessionManageServiceImpl implements ISessionManageService {

    private static final int M_SEESION_TIMEOUT = 12 * 60 * 60 * 1000;

    @Autowired
    private IUserInfoService mUserInfoService;

    @Override
    public ReturnEntity checkSessionEstablished(String userName, String appName, String chanNo, String sessionId) {
        ReturnEntity res = ReturnEntity.getDefault();
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(appName)) {
            res.mErrorCode = ErrorCodes.M_ERR_CODE_UN_AN_LACK;
            res.mIsSucceed = false;
            return res;
        }

        if(StringUtils.isEmpty(chanNo)) {
            res.mErrorCode = ErrorCodes.M_ERR_CODE_CHNO_LACK;
            res.mIsSucceed = false;
            return res;
        }

        if(StringUtils.isEmpty(sessionId)) {
            res.mErrorCode = ErrorCodes.M_ERR_CODE_SID_LACK;
            res.mIsSucceed = false;
            return res;
        }

        // 检查用户是否存在
        UserInfo info = mUserInfoService.findRecordByKWs(userName, appName, chanNo);
        if(null == info) {
            res.mErrorCode = ErrorCodes.M_ERR_CODE_USER_UNREGISTER;
            res.mIsSucceed = false;
            return res;
        }

        // 检查会话id是否正确，如果不正确，则表示用户未登陆
        if(!StringUtils.equals(sessionId, info.getSessionId())) {
            res.mErrorCode = ErrorCodes.M_ERR_CODE_USER_UNLOGIN;
            res.mIsSucceed = false;
            return res;
        }

        // 检查会话是否已经超时
        Date esTime = DateTimeUtil.getDateFromString(info.getSessionEstablishTime(), "yyyy-MM-dd HH:mm:ss");
        if(null == esTime) {
            res.mErrorCode = ErrorCodes.M_ERR_CODE_SESSION_TIMEOUT;
            res.mIsSucceed = false;
            return res;
        }

        Date outTime = DateTimeUtil.getDateBAInterval(esTime, M_SEESION_TIMEOUT, false);
        if(outTime == null) {
            res.mErrorCode = ErrorCodes.M_ERR_CODE_SESSION_TIMEOUT;
            res.mIsSucceed = false;
            return res;
        }

        if(outTime.getTime() <= new Date().getTime()) {
            res.mErrorCode = ErrorCodes.M_ERR_CODE_SESSION_TIMEOUT;
            res.mIsSucceed = false;
            return res;
        }

        res.mIsSucceed = true;

        return res;
    }

}
