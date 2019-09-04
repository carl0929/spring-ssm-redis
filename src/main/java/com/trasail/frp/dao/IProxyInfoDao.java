package com.trasail.frp.dao;

import com.trasail.frp.entity.ProxyInfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProxyInfoDao {

    List<ProxyInfo> getWorkUserListByopAndEnv(@Param("tableName") String tabName, @Param("op")String op, @Param("env")String env);
}
