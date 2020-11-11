/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 마감시한처리 프로세스
 * 설명:
 */
package nexti.ejms.agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.inputing.model.InputingManager;

public class TaskDeadline extends TaskBase {
	private static TaskDeadline _instance;
	public static TaskDeadline getInstance() {
		if(_instance == null) { _instance = new TaskDeadline(); }
		return _instance;
	}

	private static Logger logger = Logger.getLogger(TaskDeadline.class);
	private boolean isConn = false;
	private Connection Conn = null;

	public void DBConnection() {
		if(!isConn) {
			ConnectionManager.close(Conn);
			try {
				Conn = ConnectionManager.getConnection();
			} catch (Exception e) {
				logger.error("error", e);
			}
			if(Conn==null) isConn = false;
			else isConn=true;
		}
	}
	
	public void run() {
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//Task공통처리
		if(!getIsRun()) return;
		setLastRunDateTime();
		DBConnection();
		if (!isConn) return;

		logger.debug("Run TaskDeadline");

		try {
			/**
			 * 마감시한 처리를 위하여 처리대상목록을 가져온다.
			 * DOCMST(문서마스터)의 문서상태(취합진행-04), 마감시한이 지난것
			 * TGTDEPT(제출부서)의 제출상태(입력진행-02)
			 * INPUTUSR(입력담당자)의 입력상태(입력대기-01,임시저장-02)
			 */
			sql = "SELECT A.SYSDOCNO, A.ENDDT, A.DOCSTATE, B.TGTDEPTCD, B.TGTDEPTNM, B.SUBMITSTATE, C.INPUTUSRID, C.INPUTSTATE " +
				  "FROM DOCMST A, TGTDEPT B, INPUTUSR C " +
				  "WHERE A.SYSDOCNO = B.SYSDOCNO " +
				  "  AND B.SYSDOCNO = C.SYSDOCNO " +
				  "  AND B.TGTDEPTCD = C.TGTDEPT " +
				  "  AND A.ENDDT < TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') " +
				  "  AND A.DOCSTATE = '04' " +
				  "  AND B.SUBMITSTATE IN ('02') " +
				  "  AND C.INPUTSTATE IN ('01','02') " +
				  "ORDER BY A.SYSDOCNO ";
			pstmt = Conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int rowcount = 0;
			int sysdocno = 0;
			String tgtdeptcd = "";
			String inputusrid = "";
			while(rs.next()) {
				rowcount++;
				sysdocno = rs.getInt("SYSDOCNO");
				tgtdeptcd = rs.getString("TGTDEPTCD");
				inputusrid = rs.getString("INPUTUSRID");
				
				// 마감시한처리
				InputingManager.instance().inputingDeadlineProc(Conn, sysdocno, inputusrid, tgtdeptcd, "AGENT");
			}
			try { pstmt.close(); } catch (Exception ex) {}

			setLastRunStat("TaskDeadline input user Count : "+rowcount);
			if (rowcount > 0) {
				logger.info(getLastRunStat());
			}
			
			try { Conn.commit(); } catch (Exception e) {};
			setLastRunStat("RUNNING COMPLETE!!!");
			logger.debug(getLastRunStat());
		} catch (Exception e) {
			try { Conn.rollback(); } catch (Exception ee) { }
			setLastRunStat("error"+ e);
			logger.error(getLastRunStat(), e);
		} finally {
			ConnectionManager.close(Conn, pstmt, rs);
			isConn = false;
		}
	}
	
}
