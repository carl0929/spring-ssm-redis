package com.trasail.frp.service;

import com.trasail.frp.entity.ReturnEntity;

public interface ISessionManageService {

    /**
     * 检查会话是否已经建立，且未超出超时时间
     * @param userName 用户名
     * @param appName 应用名
     * @param chanNo 渠道号
     * @param sessionId 会话id
     * @return 不为null
     */
    public ReturnEntity checkSessionEstablished(String userName, String appName, String chanNo, String sessionId);
}
