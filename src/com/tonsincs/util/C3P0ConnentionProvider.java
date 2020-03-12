package com.tonsincs.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.DataSources;

public class C3P0ConnentionProvider {
	private static final String JDBC_DRIVER = "driverClass";
	private static final String JDBC_URL = "jdbcUrl";
	private static Logger log=Logger.getLogger(C3P0ConnentionProvider.class);
	private static DataSource ds;
	/**
	 * 初始化连接池代码块
	 */
	static {
		initDBSource();
	}

	/**
	 * 初始化c3p0连接池
	 */
	private static final void initDBSource() {
		Properties c3p0Pro = new Properties();
		try {
			// 加载配置文件
			// String path = C3P0ConnentionProvider.class.getResource("/")
			//		.getPath();
			String path = System.getProperty("user.dir");
			
			//String websiteURL = (path.replace("/build/classes", "")
			//		.replace("%20", " ").replace("classes/", "") + "c3p0.properties")
			//		.replaceFirst("/", "");
			//FileInputStream in = new FileInputStream(websiteURL);
			path =path+"/config/c3p0.properties";
			System.out.println("c3p0Path:"+path);
			log.info("c3p0Path:"+path);
			FileInputStream in = new FileInputStream(path);
			c3p0Pro.load(in);
		} catch (Exception e) {
			log.error("",e);
			//e.printStackTrace();
		}

		String drverClass = c3p0Pro.getProperty(JDBC_DRIVER);
		if (drverClass != null) {
			try {
				// 加载驱动类
				Class.forName(drverClass);
			} catch (ClassNotFoundException e) {
				log.error("",e);
				//e.printStackTrace();
			}

		}

		Properties jdbcpropes = new Properties();
		Properties c3propes = new Properties();
		for (Object key : c3p0Pro.keySet()) {
			String skey = (String) key;
			if (skey.startsWith("c3p0.")) {
				c3propes.put(skey, c3p0Pro.getProperty(skey));
			} else {
				jdbcpropes.put(skey, c3p0Pro.getProperty(skey));
			}
		}

		try {
			// 建立连接池
			DataSource unPooled = DataSources.unpooledDataSource(
					c3p0Pro.getProperty(JDBC_URL), jdbcpropes);
			ds = DataSources.pooledDataSource(unPooled, c3propes);

		} catch (SQLException e) {
			log.error("",e);
			//e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接对象
	 * 
	 * @return 数据连接对象
	 * @throws SQLException
	 */
	public static synchronized Connection getConnection() throws SQLException {
		final Connection conn = ds.getConnection();
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		return conn;
	}
}
