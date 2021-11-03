package com.epoint.bookmanagement.utils;

import java.util.List;

public class StringUtil {
	
	/*
	 * 判断是否为空
	 */
	public static boolean isBlank(String str) {
		if(str==null || " ".equals(str)) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * 判断是否非空
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	/*
	 * 将list中的字符串，通过连接符连接
	 */
	public static String joinListToString(List<String> list,String str) {
		String result="";
		if(list != null && list.isEmpty()) {
			int strLength=str.length();
			StringBuffer sb = new StringBuffer();
			for(String s: list) {
				sb.append(s).append(str);
			}
			String tempString = sb.toString();
			result=tempString.substring(0,tempString.length()-strLength);
		}
		return result;
	}
}
