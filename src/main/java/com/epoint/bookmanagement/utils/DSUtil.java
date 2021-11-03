package com.epoint.bookmanagement.utils;

import com.alibaba.druid.pool.*;;
//获取连接池对象
public class DSUtil {
	
	private static DruidDataSource ds;
	
	/*
	 * 获取连接池对象
	 * 
	 */
	public static DruidDataSource getDruidDataSouce() {
		if(ds==null) {
			ds = new DruidDataSource();
			ds.setDriverClassName(ConfigUtil.getJDBCConfigValue("driver"));
			ds.setUrl(ConfigUtil.getJDBCConfigValue("url"));
			ds.setUsername(ConfigUtil.getJDBCConfigValue("username"));
			ds.setPassword(ConfigUtil.getJDBCConfigValue("password"));
		}
		return ds;
	}
}
