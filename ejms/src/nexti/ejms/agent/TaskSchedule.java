/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 스케쥴러 프로세스
 * 설명:
 */
package nexti.ejms.agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;

public class TaskSchedule extends TaskBase {
	private static TaskSchedule _instance;
	public static TaskSchedule getInstance() {
		if(_instance == null) { _instance = new TaskSchedule(); }
		return _instance;
	}
	private static Logger logger = Logger.getLogger(TaskSchedule.class);
	private boolean isConn = false;
	
	private Connection DBConn = null;

	public void DBConnection() {
		if(!isConn) {
			try {
				DBConn = ConnectionManager.getConnection();
			} catch (Exception e) {
				logger.error("error", e);
			}

			if(DBConn==null) {
				isConn = false;
			}
			else {
				isConn=true;
			}
		}
	}
	
	public void run() {
		if(!getIsRun()) return;
		setLastRunDateTime();
		DBConnection();
		if (!isConn) return;
		long lSeq = 0;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		logger.debug("Run TaskSchedule");
		sql =   "SELECT A.*,B.P_SEQ, B.RUN_RESULT FROM AGENT_MST A, \n" +
				"	(SELECT B.P_ID, B.P_SEQ, B.P_TYPE, B.P_T1, B.P_T2, B.P_T3, B.P_T4, B.P_T5, B.P_T6,C.RUN_DT,NVL(C.RUN_RESULT,'0') RUN_RESULT \n" +
				"	 FROM AGENT_DTL B,  \n" +
				"	     (SELECT A.P_ID,A.P_SEQ,A.RUN_DT,A.RUN_RESULT \n" +
				"		  FROM AGENT_LOG A,  \n" +
				"			  (SELECT P_ID,P_SEQ, MAX(RUN_DT) RUN_DT  \n" +
				"			   FROM AGENT_LOG  \n" +
				"			   WHERE  SUBSTR(RUN_DT,1,8) = TO_CHAR(SYSDATE,'YYYYMMDD')  \n" +
				"			   GROUP BY P_ID, P_SEQ) B  \n" +
				"		  WHERE A.P_ID=B.P_ID AND A.P_SEQ=B.P_SEQ AND A.RUN_DT = B.RUN_DT ) C \n" +
				"	 WHERE ( \n" +
				"			( B.P_TYPE = '001' AND B.P_T1 <= TO_CHAR(SYSDATE,'HH24MI') ) OR  \n" +
				"			( B.P_TYPE = '002' AND SUBSTR(B.P_T2,1,2) = '0'||TO_CHAR(SYSDATE,'D') AND SUBSTR(B.P_T2,3,4) <= TO_CHAR(SYSDATE,'HH24MI') )  OR  \n" +
				"			( B.P_TYPE = '003' AND SUBSTR(B.P_T3,1,2) = TO_CHAR(SYSDATE,'DD') AND  SUBSTR(B.P_T3,3,4) <= TO_CHAR(SYSDATE,'HH24MI') ) OR  \n" +
				"			( B.P_TYPE = '004' AND SUBSTR(B.P_T3,1,4) = TO_CHAR(SYSDATE,'MMDD') AND  SUBSTR(B.P_T4,5,4) <= TO_CHAR(SYSDATE,'HH24MI') ) \n" +
				"	) AND B.P_ID = C.P_ID(+) AND B.P_SEQ = C.P_SEQ(+) ) B \n" +
				"WHERE A.P_ID = B.P_ID AND B.RUN_RESULT < '1' \n" +
				"AND TO_NUMBER(NVL(B.P_T5, '00')) != TO_CHAR(SYSDATE, 'D')";
		try {
			pstmt = DBConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				setLastRunStat("RUN SCHEDULE : "+rs.getString("P_ID")+" - "+rs.getInt("P_SEQ"));
				logger.debug(getLastRunStat());
				lSeq = AgentUtil.AgentlogStart(rs.getString("P_ID"),rs.getInt("P_SEQ"));
				String pid = rs.getString("P_ID").toLowerCase();
				ControlBase control = null;
				if ("sidoldap".equalsIgnoreCase(pid)) {
					if(appInfo.isSidoldap()) {
						control = ControlSidoLdapGetting.getInstance();
					}
				} else if ("usrdept".equalsIgnoreCase(pid)) {
					if(appInfo.isUsrdept()) {
						control = ControlUsrDeptGetting.getInstance();
					}
				} else {
						setLastRunStat("can not support agent id: " + pid);
						logger.error(getLastRunStat());
				}
				try {
					if (control!=null) {
						control.setlSeq(lSeq);
						control.run();
					}
					setLastRunStat(control.getClass().getName()+ " Running flag = "+control.getIsRun());
					logger.debug(getLastRunStat());
				} catch (Exception e) {	}
			}
			setLastRunStat("RUNNING COMPLETE!!!");
			logger.debug(getLastRunStat());
		} catch (Exception e) {
			setLastRunStat("error: "+ e);
			logger.error(getLastRunStat(), e);
		} finally {
			ConnectionManager.close(DBConn, pstmt, rs);
			isConn = false;
		}
	}
}
