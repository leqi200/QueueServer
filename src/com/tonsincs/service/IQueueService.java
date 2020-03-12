package com.tonsincs.service;

/**
 * 排队机服务接口
 * @author mrxiao
 *
 */
public interface IQueueService {
	
	/**
	 * 生成排队号码
	 * @return
	 */
	public String createNumber(String serial,String mobile,String khinfo);
	
	//public String 
}
