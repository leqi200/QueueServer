package com.tonsincs.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.tonsincs.util.C3P0ConnentionProvider;
import com.tonsincs.vo.CommPackage;
import com.tonsincs.vo.QueueServerState;
import com.tonsincs.vo.TranCodes;

/**
 * 排队服务接口实现
 * 
 * @author mrxiao
 * 
 */
public class QueueServices {

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static Logger log = Logger.getLogger(QueueServices.class);

	public static CommPackage createNumber(String serial, String mobile,
			String khinfo) {
		Connection conn = null;
		CallableStatement callStmt = null;
		CommPackage cp = null;

		try {
			conn = C3P0ConnentionProvider.getConnection();
			callStmt = conn.prepareCall("{?=call sendnumber(?,?,?,?,?,?,?,?)}");
			// 设置参数
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setInt(2, Integer.parseInt(serial));
			callStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			callStmt.setString(4, mobile);
			callStmt.setString(5, khinfo);

			callStmt.registerOutParameter(6, Types.VARCHAR);
			callStmt.registerOutParameter(7, Types.VARCHAR);
			callStmt.registerOutParameter(8, Types.INTEGER);
			callStmt.registerOutParameter(9, Types.VARCHAR);

			// 执行过程
			callStmt.execute();

			int ret = callStmt.getInt(1);
			String number = callStmt.getString(6);
			String typeName = callStmt.getString(7);
			int waithNum = callStmt.getInt(8);
			String windows = callStmt.getString(9);
			
		
			//打印执行结果
			log.info("[sendnumber]执行结果：ret:" + ret + " number:" + number + " typeName:"
					+ typeName + " waithNum:" + waithNum + " windows:"
					+ windows);

			cp = new CommPackage(typeName, windows, QueueServerState.SUCCESS,
					QueueServerState.getMessages(QueueServerState.SUCCESS),
					number, String.valueOf(waithNum));

		} catch (SQLException e) {
			log.error("", e);
		} finally {
			try {
				callStmt.close();
				conn.close();

			} catch (SQLException e) {
				log.error("", e);
			}
		}
		return cp;
	}

}
