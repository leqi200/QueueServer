package com.tonsincs.vo;

/**
 * 通用报文数据包
 * @author mrxiao
 *
 */
public class CommonPackage {

	
	private Head HEAD;
	private Body BODY;
		
	public CommonPackage() {
		super();
	}

	public CommonPackage(Head hEAD, Body bODY) {
		super();
		HEAD = hEAD;
		BODY = bODY;
	}
	
	
	

	public Head getHEAD() {
		return HEAD;
	}

	public void setHEAD(Head hEAD) {
		HEAD = hEAD;
	}

	public Body getBODY() {
		return BODY;
	}

	public void setBODY(Body bODY) {
		BODY = bODY;
	}

	/**
	 * 包头结构定义
	 * @author mrxiao
	 *
	 */
	public class Head{
		
		private String RspCode;
		private String RspMsg;
		
		
		public Head() {
			super();
		}
		public Head(String rspCode, String rspMsg) {
			super();
			RspCode = rspCode;
			RspMsg = rspMsg;
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
		
		
	}
	
	/**
	 * 包体结构定义
	 * @author mrxiao
	 *
	 */
	public class Body{
		private String InstNo;	//网点机构编号
		private String SysName;	//网点名称
		private String YuYueCode;//预约验证码
		private String QueueNumber;//排除号码
		private String PersonalBusiness;//个人业务总的等待人数
		private String QueueNum;	//排除等候人数
		private String QueueTotalNum;//排队等候总人数
		private String WaiTime;	//当前号码等待时间
		private String WaiNum;	//当前业务办理人数
		private String WaiAvgTime;//当前业务平均等待时间
		private String EstimateTime;//预计等待时间
		private String CustNo;	//客户号
		private String BizType;//业务编码（或业务类型）
		private String BeginOrderTime;//预约开始时段（时间格式：2015-05-05 10:00:00 最少单位为小时）
		private String EndOrderTime; //预约结束时段（时间格式：2015-05-05 11:00:00 最少单位为小时）
		private String YuYueTime;	//远程取号时间（时间格式：2015-05-05 10:00:00 最少单位为小时）
		private String PerBusNum;//个人业务等待人数（如：个人综合业务｜10~个人缴费业务|10）
		private String ComBusNum;//对公等待人数（如：对公现金业务｜10~对公非现金业务|10）
		private String PerQueueList;//个人业务队列
		private String ComQueueList;//
		
		
		
		
		public Body() {
			super();
		}
		
		
		 
		
		/**
		 * 排队情况查询返回报文构造函数
		 * @param instNo
		 * @param personalBusiness
		 * @param queueTotalNum
		 */
		public Body(String instNo, String personalBusiness, String queueTotalNum) {
			super();
			InstNo = instNo;
			PersonalBusiness = personalBusiness;
			QueueTotalNum = queueTotalNum;
		}

		
		



		/**
		 * 预约成功响应返回报文构造函数
		 * @param instNo 网点编码
		 * @param yuYueCode预约码
		 * @param bizType业务编码
		 * @param beginOrderTime开始时间
		 * @param endOrderTime结束时间
		 */
		public Body(String instNo, String yuYueCode, String bizType,
				String beginOrderTime, String endOrderTime) {
			super();
			InstNo = instNo;
			YuYueCode = yuYueCode;
			BizType = bizType;
			BeginOrderTime = beginOrderTime;
			EndOrderTime = endOrderTime;
		}

		
		
		



		/**
		 * 远程取号成功响应返回报文构造函数
		 * @param instNo 网点编码
		 * @param sysName网点名称
		 * @param queueNumber排队号码
		 * @param queueNum排队总人数
		 * @param bizType业务编码
		 * @param yuYueTime预约的时间
		 */
		public Body(String instNo, String sysName, String queueNumber,
				String queueNum, String bizType, String yuYueTime) {
			super();
			InstNo = instNo;
			SysName = sysName;
			QueueNumber = queueNumber;
			QueueNum = queueNum;
			BizType = bizType;
			YuYueTime = yuYueTime;
		}




		public String getInstNo() {
			return InstNo;
		}
		public void setInstNo(String instNo) {
			InstNo = instNo;
		}
		public String getSysName() {
			return SysName;
		}
		public void setSysName(String sysName) {
			SysName = sysName;
		}
				
		public String getYuYueCode() {
			return YuYueCode;
		}

		public void setYuYueCode(String yuYueCode) {
			YuYueCode = yuYueCode;
		}
		
		public String getQueueNum() {
			return QueueNum;
		}

		public void setQueueNum(String queueNum) {
			QueueNum = queueNum;
		}

		public String getQueueNumber() {
			return QueueNumber;
		}
		public void setQueueNumber(String queueNumber) {
			QueueNumber = queueNumber;
		}
		public String getPersonalBusiness() {
			return PersonalBusiness;
		}
		public void setPersonalBusiness(String personalBusiness) {
			PersonalBusiness = personalBusiness;
		}
		public String getQueueTotalNum() {
			return QueueTotalNum;
		}
		public void setQueueTotalNum(String queueTotalNum) {
			QueueTotalNum = queueTotalNum;
		}
		public String getWaiTime() {
			return WaiTime;
		}
		public void setWaiTime(String waiTime) {
			WaiTime = waiTime;
		}
		public String getWaiNum() {
			return WaiNum;
		}
		public void setWaiNum(String waiNum) {
			WaiNum = waiNum;
		}
		public String getWaiAvgTime() {
			return WaiAvgTime;
		}
		public void setWaiAvgTime(String waiAvgTime) {
			WaiAvgTime = waiAvgTime;
		}
		public String getEstimateTime() {
			return EstimateTime;
		}
		public void setEstimateTime(String estimateTime) {
			EstimateTime = estimateTime;
		}
		public String getCustNo() {
			return CustNo;
		}
		public void setCustNo(String custNo) {
			CustNo = custNo;
		}
		public String getBizType() {
			return BizType;
		}
		public void setBizType(String bizType) {
			BizType = bizType;
		}
		public String getBeginOrderTime() {
			return BeginOrderTime;
		}
		public void setBeginOrderTime(String beginOrderTime) {
			BeginOrderTime = beginOrderTime;
		}
		public String getEndOrderTime() {
			return EndOrderTime;
		}
		public void setEndOrderTime(String endOrderTime) {
			EndOrderTime = endOrderTime;
		}
		public String getYuYueTime() {
			return YuYueTime;
		}
		public void setYuYueTime(String yuYueTime) {
			YuYueTime = yuYueTime;
		}
		public String getPerBusNum() {
			return PerBusNum;
		}
		public void setPerBusNum(String perBusNum) {
			PerBusNum = perBusNum;
		}
		public String getComBusNum() {
			return ComBusNum;
		}
		public void setComBusNum(String comBusNum) {
			ComBusNum = comBusNum;
		}
		public String getPerQueueList() {
			return PerQueueList;
		}
		public void setPerQueueList(String perQueueList) {
			PerQueueList = perQueueList;
		}
		public String getComQueueList() {
			return ComQueueList;
		}
		public void setComQueueList(String comQueueList) {
			ComQueueList = comQueueList;
		}
		
		
		
		
		
	}
}
