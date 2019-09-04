package com.trasail.frp.service;

import com.trasail.frp.entity.ProxyUser;

public interface ProxyUserService extends IAutoInitTable{

    /**
     * 插入代理信息
     * @param info
     * @return
     */
    public boolean insertProxyUserInfo(ProxyUser info);
}
