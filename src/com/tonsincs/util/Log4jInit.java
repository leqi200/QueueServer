package com.tonsincs.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * Log4j 初始化工具类
 * @author mrxiao
 *
 */
public class Log4jInit {

	/**
	 * 初始化Log4j日志信息
	 */
	public static void  initLog4j(){
		//获得当前目录的根路径
		String rootPath =System.getProperty("user.dir");
		//设置系统参数
		System.setProperty("QueueServer", rootPath);
		String log4jFile=rootPath+"/config/log4j.properties";
		System.out.println(log4jFile);
		
		//c3p0Pro.load(in);
		//Properties properties =new Properties();
		//try {
		//	FileInputStream in = new FileInputStream(log4jFile);
		//	properties.load(in);
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		PropertyConfigurator.configure(log4jFile);
		//PropertyConfigurator.configure(log4jFile);
		
	}
}
