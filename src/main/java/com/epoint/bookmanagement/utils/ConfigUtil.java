package com.epoint.bookmanagement.utils;


import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;


//读取配置文件
public class ConfigUtil {
	/*
	 * ResourceBundle：读取.properties配置文件；
	 * getString(configname)，根据key找到value；
	 */
	public static String getJDBCConfigValue(String configName) {
		String configValue = null;
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
		configValue = bundle.getString(configName);
		if(StringUtils.isNotBlank(configValue)) {
			configValue=configValue.trim();
		}
		return configValue;
	}
}
