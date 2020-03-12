package com.tonsincs.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 排除服务器状态定义
 * 
 * @author mrxiao
 * 
 */
public class QueueServerState {

	/**
	 * 状态提示消息
	 */
	private static Map<String, String> MSG = new HashMap<String, String>();

	static {
		MSG.put("-1", "服务器处理消息异常");
		MSG.put("0", "处理成功");
		MSG.put("1", "网点不信息存在");
		MSG.put("2", "排队号码正在受理中或者不存在");
		MSG.put("3", "报文包格式不合法");
		MSG.put("4", "请求参数错误");
		MSG.put("5", "内部系统转发异常");
		MSG.put("6", "预约达到系统限制");
		MSG.put("7", "网点预约记录已经存在");
		MSG.put("8", "排队号码已经存在");
		MSG.put("9", "网点预约记录不存在");
		MSG.put("10", "交易码错误");
	}
	/**
	 * 服务器异常状态码
	 */
	public static final String ERROR = "-1";

	/**
	 * 处理成功状态码
	 */
	public static final String SUCCESS = "0";

	/**
	 * 网点不信息存在状态码
	 */
	public static final String SYSNO_NONEXIST = "1";
	/**
	 * 排队号码正在受理中或者不存在状态码
	 */
	public static final String NUMBER_ACCEPT = "2";

	/**
	 * 报文包格式不合法状态码
	 */
	public static final String ILLEGAL_FORMAT = "3";
	
	/**
	 * 请求参数错误状态码
	 */
	public static final String ILLEGAL_PARAMETER = "4";

	/**
	 * 内部系统转发异常状态码
	 */
	public static final String TRANSMIT_EXCEPTION = "5";

	/**
	 * 预约达到系统限制状态码
	 */
	public static final String YUYUE_LIMIT = "6";

	/**
	 * 网点预约记录已经存在状态码
	 */
	public static final String SYSNO_YUYUE_EXIST = "7";

	/**
	 * 排队号码已经存在状态码
	 */
	public static final String NUMBER_EXIST = "8";

	/**
	 * 网点预约记录不存在状态码
	 */
	public static final String SYSNO_YUYUE_NONEXIST = "9";
	
	/**
	 * 交易码错误状态码
	 */
	public static final String TRANCODE_ERROR="10";

	/**
	 * 获得消息提示信息
	 * 
	 * @param key
	 * @return
	 */
	public static String getMessages(String key) {
		String msg = null;

		if (MSG.containsKey(key))
			msg = MSG.get(key);

		return msg;
	}

}
