package com.trasail.frp.service;

public interface IAutoInitTable {

    /**
     * 获取表名称，不能为null
     * @return
     */
    public String getTableName();

    /**
     * 获取创建表的SQL
     * @return 不能为null
     */
    public String getCreateTableSql();
}