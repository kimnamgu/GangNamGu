/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է��ϱ� manager
 * ����:
 */
package nexti.ejms.inputing.model;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.util.Utils;

public class InputingManager {
	
	private static Logger logger = Logger.getLogger(InputingManager.class);
	
	private static InputingManager instance = null;
	private static InputingDAO dao = null;
	
	public static InputingManager instance() {
		if (instance==null) instance = new InputingManager(); 
		return instance;
	}
	
	private InputingDAO getInputingDAO(){
		if (dao==null) dao = new InputingDAO(); 
		return dao;
	}
	
	private InputingManager() {
	}
	
	/**
	 * ����μ� �Է¿Ϸ� ���� üũ
	 * @param sysdocno �ý��۹�����ȣ
	 * @param tgtdeptcd ����μ��ڵ�
	 * @return ����Ϸ�sysdocno,tgtdeptcd,inputusrid(1��)
	 */
	public String[] isTgtdeptInputComplete(int sysdocno, String tgtdeptcd) throws Exception {
		
		Connection con = null;
		String[] result = null;
		
		try {
			con = ConnectionManager.getConnection(false);
			
			result = isTgtdeptInputComplete(con, sysdocno, tgtdeptcd);
			
			con.commit();
		} catch(Exception e) {
			logger.error("ERROR", e);
			try {
				con.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		
		return result;		
	}
	
	public String[] isTgtdeptInputComplete(Connection con, int sysdocno, String tgtdeptcd) throws Exception {
		return getInputingDAO().isTgtdeptInputComplete(con, sysdocno, tgtdeptcd);
	}

	/**
	 * �Է��ϱ� ���
	 * 
	 * @param userid : �����ID
	 * @param start : ��� ���� �ε���
	 * @param end : ��� ������ �ε���
	 * 
	 * @return List : �Է¸��
	 * @throws Exception 
	 */
	public List inputingList(String userid, String deptcd, int gubun, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		return getInputingDAO().inputingList(userid, deptcd, gubun, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
	}
	
	/**
	 * �Է¿Ϸ� ���
	 * 
	 * @param userid : �����ID
	 * @param start : ��� ���� �ε���
	 * @param end : ��� ������ �ε���
	 * @param searchtext : �˻���
	 * @param selyear : ���ó�
	 * 
	 * @return List : �Է¿Ϸ���
	 * @throws Exception 
	 */
	public List inputCompleteList(String userid, String deptcd, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end, String searchvalue, String selyear) throws Exception {
		return getInputingDAO().inputCompleteList(userid, deptcd, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end, searchvalue, selyear);
	}
	
	/**
	 * ������ �Է������� üũ
	 * 03: �ش����
	 * 04: �Է¿Ϸ�
	 * 05: �������� �ʰ�
	 */
	public int doLastInputCompleteCheck(int sysdocno, String usrid, String deptcd) throws Exception {
		
		Connection con = null;
		int result = 0;
		
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			result = doLastInputCompleteCheck(con, sysdocno, usrid, deptcd);
			
			con.commit();
		} catch(Exception e) {
			logger.error("ERROR", e);
			try {
				con.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		return result;
	}
	
	/**
	 * ������ �Է������� üũ
	 * 03: �ش����
	 * 04: �Է¿Ϸ�
	 * 05: �������� �ʰ�
	 */
	public int doLastInputCompleteCheck(Connection conn, int sysdocno, String usrid, String deptcd) throws Exception {
		int result = 0;

		//������ �Է��ڰ� �ƴϸ� ����
		if (getInputingDAO().IsLastInputUsr(conn, sysdocno, usrid, deptcd) == true) {
			/*
			//�����ڰ� �����ϸ�
			if(getInputingDAO().checkExistApprovalUsr(sysdocno, deptcd, "1")) {
				result = getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptcd, "03");
				result = getInputingDAO().updateSubmitDT(conn, sysdocno, usrid, deptcd, "1");
			}
			//�����ڰ� �����ϸ�
			else if(getInputingDAO().checkExistApprovalUsr(sysdocno, deptcd, "2")) {
				result = getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptcd, "04");
				result = getInputingDAO().updateSubmitDT(conn, sysdocno, usrid, deptcd, "2");
			} else {
				result = getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptcd, "05");
			}
			//�����ڰ�(������) ����, ������ ����μ��̸�
			if (getInputingDAO().checkExistApprovalUsr(sysdocno, deptcd, "2")!=true 
				&& DeliveryManager.instance().IsLastDeliveryDept(sysdocno, deptcd)) {
				result = getInputingDAO().updateDocState(conn, sysdocno, usrid, "05");
			}
			*/
			
			result = getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptcd, "05");

			CommTreatManager ctmgr = CommTreatManager.instance();
			String[][] deptInfo = FormatManager.instance().getInputDeptInfo(conn, sysdocno, deptcd);
			for ( int i = deptInfo.length - 1; i + 1 > 0; i-- ) {
				if ( "".equals(Utils.nullToEmptyString(deptInfo[i][0])) ) continue;
				
				int tcnt = 0;
				int scnt = 0;
				
				List fg = ctmgr.getFormationGroup(conn, "", sysdocno, null, deptInfo[i][0], "", "", "", "");
				if ( fg != null ) {
					tcnt = fg.size();
				}

				//fg = ctmgr.getFormationGroup(conn, "", sysdocno, null, deptInfo[i][0], "05,06", "", "", "03,04,05");
				fg = ctmgr.getFormationGroup(conn, "", sysdocno, null, deptInfo[i][0], "", "", "", "01,02");
				if ( fg != null ) {
					scnt = fg.size();
				}
				
				if ( tcnt > 0 && scnt < 1 ) {
					result += getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptInfo[i][0], "05");
				}
			}
			
			//�����ڰ�(������) ����, ������ ����μ��̸�
			if (DeliveryManager.instance().IsLastDeliveryDept(conn, sysdocno, deptcd)) {
				result = getInputingDAO().updateDocState(conn, sysdocno, usrid, "05");
			}

		}

		return result;
	}
	
	/**
	 * �Է¿Ϸ�ó�� ���μ���
	 * 03: �ش����
	 * 04: �Է¿Ϸ�
	 * 05: �������� �ʰ�
	 */
	private int inputingProcess(Connection conn, int sysdocno, String usrid, String deptcd, String uptusrid, String state) throws Exception {
		int result = 0;
		
		//�Է»��� ����
		result = getInputingDAO().updateInputState(conn, sysdocno, usrid, deptcd, uptusrid, state);
		
		if ( result > 0 ) {
			result += doLastInputCompleteCheck(conn, sysdocno, usrid, deptcd);
		}
		
		return result;
	}
	
	/** 
	 * �Է��ϱ� �� - ���չ����ش���� ó��
	 * 
	 * @param usrid : �����ID
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�	
	 * 
	 * @return int : ó�����
	 * @throws Exception 
	 */
	public int inputingNotApplicable(int sysdocno, String usrid, String deptcd) throws Exception {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			//���չ��� �ش���� ó���ϱ� �� �Էµ� �����ʹ� ����
			getInputingDAO().deleteInputData(conn, "DATALINEFRM", sysdocno, deptcd, usrid);
			getInputingDAO().deleteInputData(conn, "DATAFIXEDFRM", sysdocno, deptcd, usrid);
			getInputingDAO().deleteInputData(conn, "DATABOOKFRM", sysdocno, deptcd, usrid);

			//ó�����μ��� (���չ����ش����)
			result = inputingProcess(conn, sysdocno, usrid, deptcd, usrid, "03");
			
			conn.commit();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception ex) {}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/** 
	 * �Է��ϱ� �� - �Է¿Ϸ� ó��
	 * 
	 * @param usrid : �����ID
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ó�����
	 * @throws Exception 
	 */
	public int inputingComplete(int sysdocno, String usrid, String deptcd) throws Exception {
		Connection conn = null;
		int result = 0; 
		
		try {
			conn = ConnectionManager.getConnection(false);

			//ó�����μ��� (�Է¿Ϸ�)
			result = inputingProcess(conn, sysdocno, usrid, deptcd, usrid, "04");
			
			conn.commit();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception ex) {}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/** 
	 * ��������ó��(agent) - �������� �ʰ� ó��
	 * 
	 * @param usrid : �����ID
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param uptusrid : ������	
	 * 
	 * @return ����
	 */
	public void inputingDeadlineProc(Connection conn, int sysdocno, String usrid, String deptcd, String uptusrid) throws Exception {
		//ó�����μ��� (�Է¿Ϸ�)
		inputingProcess(conn, sysdocno, usrid, deptcd, uptusrid, "05");	
	}

	/** 
	 * �Է��ϱ� ��� ���� ��������	
	 * @throws Exception 
	 */
	public int inputingCnt(String userid, String deptcd, int gubun, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		return getInputingDAO().inputingCnt(userid, deptcd, gubun, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
	}
	
	/** 
	 * �Է��ϱ� ��� ���� ��������	
	 * @throws Exception 
	 */
	public int inputingTotCnt(String userid, String deptcd, int gubun) throws Exception {
		return getInputingDAO().inputingTotCnt(userid, deptcd, gubun);
	}

	/** 
	 * �Է¿Ϸ� ��� ���� ��������	
	 * 
	 * @param userid : �����ID
	 * @param searchvalue : �˻���
	 * @param selyear : ���ó⵵
	 * 
	 * @return int : ��� ī��Ʈ
	 * @throws Exception 
	 */
	public int inputCompleteCnt(String userid, String deptcd, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, String searchvalue, String selyear) throws Exception {
		return getInputingDAO().inputCompleteCnt(userid, deptcd, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, searchvalue, selyear);
	}
	
	/** 
	 * �Է¿Ϸ� ��� ���� ��������	
	 * 
	 * @param userid : �����ID
	 * @param searchvalue : �˻���
	 * @param selyear : ���ó⵵
	 * 
	 * @return int : ��� ī��Ʈ
	 * @throws Exception 
	 */
	public int inputCompleteTotCnt(String userid, String deptcd, String searchvalue, String selyear) throws Exception {
		return getInputingDAO().inputCompleteTotCnt(userid, deptcd, searchvalue, selyear);
	}
	
	/** 
	 * �Է��ϱ� - �Է´���� ������ ���� ���� Ȯ��
	 * 
	 * @param usrid : �����ID
	 * 
	 * @return true, false	
	 * @throws Exception 
	 */
	public String IsAssignInputUsrcharge(String usrid) throws Exception {
		String retCode = "";
		
		if(getInputingDAO().IsAssignInputUsrcharge(usrid)) {
			retCode = "ASSIGN";
		} else {
			retCode = "NOTASSIGN";
		}
		
		return retCode;
	}
	
	/** 
	 * �Է��ϱ� �� - �Էµ����� ���� ���� üũ
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * 
	 * @return true : ������ ����, false : ������ ������	
	 * @throws Exception 
	 */
	public String IsExistDataFrm(int sysdocno, String deptcd, String usrid) throws Exception {
		String retCode = "";
		
		boolean blnDataLine = getInputingDAO().IsExistDataLineFrm(sysdocno, deptcd, usrid);
		boolean blnDataFixed = getInputingDAO().IsExistDataFixedFrm(sysdocno, deptcd, usrid);
		boolean blnDataBook = getInputingDAO().IsExistDataBookFrm(sysdocno, deptcd, usrid);
		
		if(blnDataLine || blnDataFixed || blnDataBook) {
			retCode = "EXIST";
		} else {
			retCode = "NOTEXIST";
		}
		
		return retCode;
	}
	
	/** 
	 * ������������ �Ǽ� ��������-(�Է���Ȳ-����ȭ��)
	 * 
	 * @param usrid : �����ID
	 * 
	 * @return int : ������������ �Ǽ�
	 * @throws Exception 
	 */
	public int procCount(String userid, String deptcd) throws Exception {
		return getInputingDAO().procCount(userid,deptcd);
	}
	

	/**
	 * �Է��ϱ� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� �������� 
	 */
	public String getSearchInputing(int gubun, String sch_deptcd, String sch_userid) throws Exception{
		return getInputingDAO().getSearchInputing(gubun, sch_deptcd, sch_userid);
	}

	/**
	 * �Է¿Ϸ� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� �������� 
	 */
	public String getSearchInputComplete(String gubun, String sch_deptcd, String sch_userid) throws Exception{
		return getInputingDAO().getSearchInputComplete(gubun, sch_deptcd, sch_userid);
	}
}
