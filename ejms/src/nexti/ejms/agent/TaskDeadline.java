/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ý��ۿ�����Ʈ ��������ó�� ���μ���
 * ����:
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
		
		//Task����ó��
		if(!getIsRun()) return;
		setLastRunDateTime();
		DBConnection();
		if (!isConn) return;

		logger.debug("Run TaskDeadline");

		try {
			/**
			 * �������� ó���� ���Ͽ� ó��������� �����´�.
			 * DOCMST(����������)�� ��������(��������-04), ���������� ������
			 * TGTDEPT(����μ�)�� �������(�Է�����-02)
			 * INPUTUSR(�Է´����)�� �Է»���(�Է´��-01,�ӽ�����-02)
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
				
				// ��������ó��
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
