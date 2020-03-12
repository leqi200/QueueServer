package com.tonsincs.handler;

import com.tonsincs.service.QueueServices;
import com.tonsincs.vo.CommPackage;
import com.tonsincs.vo.QueueServerState;
import com.tonsincs.vo.TranCodes;

/**
 * 取号消息处理者
 * 
 * @author mrxiao
 * 
 */
public class TakeHandler extends QueueHandler {

	@Override
	public CommPackage doHandler(CommPackage cp) {
		CommPackage c = null;

		// 如果请求值不合法则提示参数错误
		if (cp.getTranCode() == null || cp.getBizType() == null
				|| cp.getTermNo() == null) {
			
			c = new CommPackage(QueueServerState.ILLEGAL_PARAMETER,
					QueueServerState
							.getMessages(QueueServerState.ILLEGAL_PARAMETER));
			
		} else if (TranCodes.SYS_REMOTE_TAKE.equals(cp.getTranCode().trim())) {// 判断当前交易码是否匹配
			
			c = QueueServices.createNumber(cp.getBizType(), cp.getBizType(),
					cp.getTermNo());
			
		} else {
			// 如果交易码不匹配，转交给下一个消息处理者
			/*
			 * 目前这个消息链只到达这层
			 */
			// c=successor.doHandler(cp);
			c = new CommPackage(QueueServerState.TRANCODE_ERROR,
					QueueServerState
							.getMessages(QueueServerState.TRANCODE_ERROR));
		}

		return c;
	}

}
