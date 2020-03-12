package com.tonsincs.handler;

import com.tonsincs.vo.CommPackage;

/**
 * 排队消息处理抽象类
 * @author mrxiao
 *
 */
public abstract class QueueHandler {
	
	//消息处理后继
	protected QueueHandler successor;

	public void setSuccessor(QueueHandler successor) {
		this.successor = successor;
	}

	
	/**
	 * 消息处理
	 * @param cp消息报文
	 * @return返回处理后的消息报文
	 */
	public abstract CommPackage doHandler(CommPackage cp);
	
}
