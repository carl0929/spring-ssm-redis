package com.trasail.frp.dao;

import com.trasail.frp.entity.ProxyUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProxyUserDao {

    /**
     * 插入代理信息
     * @param tabName
     * @param info
     */
   public int insertProxyUserInfo(@Param("tableName") String tabName, @Param("info")ProxyUser info);
}
