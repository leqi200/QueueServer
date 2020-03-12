package com.tonsincs.service;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.tonsincs.handler.QueueHandler;
import com.tonsincs.handler.QueueHandlerFactory;
import com.tonsincs.util.BeanUtils;
import com.tonsincs.vo.CommPackage;
import com.tonsincs.vo.QueueServerState;

/**
 * 排队机管理服务消息管理对象 见意，以后版本升级中把这个管理服务对象设计成单例并且要同步机制锁定获得该实例
 * 
 * @author mrxiao
 * 
 */
public class QueueServiceManage {

	// 日志记录器
	private static Logger log = Logger.getLogger(QueueServiceManage.class);
	// 消息处理对象
	private static QueueHandler queueHandler = QueueHandlerFactory
			.installQueueHandler();

	/**
	 * 接收并处理每一次接收到报文消息请求
	 * 
	 * @param content消息报文内容
	 * @return返回一个结果报文
	 */
	public static String doServer(String content) {
		StringBuffer msg = new StringBuffer();
		CommPackage cp = null;

		try {
			// 判断数据合法性
			if (content != null) {
				cp = queueHandler.doHandler(BeanUtils.converyToJavaBean(
						content, CommPackage.class));
			}

			// 转换成字符串响应报文
			if (cp != null) {
				msg.append(createMessage(cp));
			}

		} catch (JAXBException e) {

			cp = new CommPackage(QueueServerState.ILLEGAL_FORMAT,
					QueueServerState
							.getMessages(QueueServerState.ILLEGAL_FORMAT));
			msg.append(createMessage(cp));
			log.error("", e);

		}

		return msg.toString();
	}

	/**
	 * 创建消息报文
	 * 
	 * @param cp
	 * @return
	 */
	private static String createMessage(CommPackage cp) {
		String msg = "";
		try {
			msg = BeanUtils.beanToXML(cp, "UTF-8");
		} catch (IOException e) {
			log.error("", e);
		}

		return msg;

	}

}
