package com.tonsincs.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ProjectName:QueueServer
 * @ClassName: CommPackage
 * @Description: TODO(排队机通用报文)
 * @author MrXiao
 * @date 2015-5-6 下午4:45:11
 * 
 * @version V1.0
 */
@XmlRootElement(name = "Package")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommPackage implements Serializable {

	private String TranCode;
	private String InstNo;
	private String BizType;
	private String SerialName;
	private String ServCounter;
	private String TermNo;
	private String RspCode;
	private String RspMsg;
	private String QueueNumber;
	private String QueueNum;

	public CommPackage() {
		super();
	}

	public CommPackage(String rspCode, String rspMsg) {
		super();
		RspCode = rspCode;
		RspMsg = rspMsg;
	}

	/**
	 * 排队取号返回报文
	 * 
	 * @param serialName业务名称
	 * @param servCounter受理窗口
	 * @param rspCode响应码
	 * @param rspMsg响应消息
	 * @param queueNumber排队号码
	 * @param queueNum相同业务前面等待人数
	 */
	public CommPackage(String serialName, String servCounter, String rspCode,
			String rspMsg, String queueNumber, String queueNum) {
		super();
		SerialName = serialName != null ? serialName : "";
		ServCounter = servCounter != null ? servCounter : "";
		RspCode = rspCode;
		RspMsg = rspMsg;
		QueueNumber = queueNumber != null ? queueNumber : "";
		QueueNum = queueNum != null ? queueNum : "";
	}

	public String getTranCode() {
		return TranCode;
	}

	public void setTranCode(String tranCode) {
		TranCode = tranCode;
	}

	public String getInstNo() {
		return InstNo;
	}

	public void setInstNo(String instNo) {
		InstNo = instNo;
	}

	public String getBizType() {
		return BizType;
	}

	public void setBizType(String bizType) {
		BizType = bizType;
	}

	public String getSerialName() {
		return SerialName;
	}

	public void setSerialName(String serialName) {
		SerialName = serialName;
	}

	public String getServCounter() {
		return ServCounter;
	}

	public void setServCounter(String servCounter) {
		ServCounter = servCounter;
	}

	public String getTermNo() {
		return TermNo;
	}

	public void setTermNo(String termNo) {
		TermNo = termNo;
	}

	public String getRspCode() {
		return RspCode;
	}

	public void setRspCode(String rspCode) {
		RspCode = rspCode;
	}

	public String getRspMsg() {
		return RspMsg;
	}

	public void setRspMsg(String rspMsg) {
		RspMsg = rspMsg;
	}

	public String getQueueNumber() {
		return QueueNumber;
	}

	public void setQueueNumber(String queueNumber) {
		QueueNumber = queueNumber;
	}

	public String getQueueNum() {
		return QueueNum;
	}

	public void setQueueNum(String queueNum) {
		QueueNum = queueNum;
	}

}
