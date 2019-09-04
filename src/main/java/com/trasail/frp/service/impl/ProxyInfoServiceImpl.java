package com.trasail.frp.service.impl;

import com.trasail.frp.dao.IProxyInfoDao;
import com.trasail.frp.entity.ProxyInfo;
import com.trasail.frp.service.IProxyInfoSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProxyInfoServiceImpl implements IProxyInfoSerice {

    @Autowired
    private IProxyInfoDao infomapper;

    public List<ProxyInfo> getWorkUserListByopAndEnv(String op, String env) {
        return infomapper.getWorkUserListByopAndEnv(getTableName(),op,env);
    }

    private static final String M_TAB_NAME = "proxy_info";


    public String getTableName() {
        return M_TAB_NAME;
    }
}
