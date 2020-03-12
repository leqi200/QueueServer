package com.tonsincs.handler;

/**
 * 排队业务消息工厂
 * @author mrxiao
 *
 */
public class QueueHandlerFactory {
	
	/**
	 * 创建排队业务链消息
	 * @return
	 */
	public static QueueHandler installQueueHandler(){
		QueueHandler takeHandler=new TakeHandler();
		
		return takeHandler;
	}

}
