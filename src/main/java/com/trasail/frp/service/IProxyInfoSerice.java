package com.trasail.frp.service;

import com.trasail.frp.entity.ProxyInfo;

import java.util.List;

public interface IProxyInfoSerice {
    List<ProxyInfo> getWorkUserListByopAndEnv(String op,String env);
}
