package com.trasail.frp.service.impl;

import com.trasail.frp.dao.ProxyUserDao;
import com.trasail.frp.entity.ProxyUser;
import com.trasail.frp.service.ProxyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyUserServiceImpl implements ProxyUserService {

    @Autowired
    private ProxyUserDao mapper;

    /**
     * 插入代理信息
     *
     * @param info
     * @return
     */
    @Override
    public boolean insertProxyUserInfo(ProxyUser info) {
        int i = mapper.insertProxyUserInfo(getTableName(), info);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }



    /**
     * 获取表名称，不能为null
     *
     * @return
     */
    @Override
    public String getTableName() {
        return M_TAB_NAME;
    }
    private static final String M_TAB_NAME = "proxy_user";
    private static final String M_SQL_TAB_CREATE = "id bigint(20) NOT NULL AUTO_INCREMENT, " +
            "s_name varchar(50) , " +
            "s_pwd varchar(255) , " +
            "user_id varchar(50), " +
            "app_name varchar(50), " +
            "country varchar(50), " +
            "op varchar(50), " +
            "env int(2), " +
            "create_time timestamp NOT NULL, " +
            "PRIMARY KEY (id)";
    /**
     * 获取创建表的SQL
     *
     * @return 不能为null
     */
    @Override
    public String getCreateTableSql() {
        return M_SQL_TAB_CREATE;
    }
}
