/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ó����Ȳ manager
 * ����:
 */
package nexti.ejms.commtreat.model;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.util.Utils;

public class CommTreatManager {
	private static CommTreatManager instance = null;
	private static CommTreatDAO dao = null;
	private static Logger logger = Logger.getLogger(CommTreatManager.class);
	
	public static CommTreatManager instance() {
		if (instance==null) instance = new CommTreatManager(); 
		return instance;
	}
	
	private CommTreatDAO getCommTreatDAO(){
		if (dao==null) dao = new CommTreatDAO(); 
		return dao;
	}
	
	private CommTreatManager() {}
	
	/**
	 * ���մ������ ��������
	 * @param sysdocno
	 * @param tgtdeptcd
	 * @param sch_deptid
	 * @param sch_submitsate
	 * @param sch_userid
	 * @param sch_inputstate
	 * @return
	 * @throws Exception
	 */
	public String targetDeptCheck(String type, String sessionId, int sysdocno, String tgtdeptcd, String sch_deptid, String sch_submitsate, String sch_userid, String sch_inputstate) throws Exception {
		StringBuffer result = null;
		boolean check = false;
		
		if ( "DEPT_EXIST".equals(type) ) {
			if (  !tgtdeptcd.equals(sch_deptid) ) {
				if ( getFormationGroup(sessionId, sysdocno, null, tgtdeptcd, sch_deptid, "", "", "") != null ) {
					check = true;
				}
			}
		} else if ( "USER_EXIST".equals(type) ) {
			if ( getFormationGroup(sessionId, sysdocno, null, tgtdeptcd, "", "", sch_userid, "") != null ) {
				check = true;
			}
		} else if ( "DEPT_DATA_EXIST".equals(type) ) {
			if ( getFormationGroup(sessionId, sysdocno, null, tgtdeptcd, sch_deptid, "02,03,04,05", "", "") != null ) {
				check = true;
			}
		} else if ( "USER_DATA_EXIST".equals(type) ) {
			if ( getFormationGroup(sessionId, sysdocno, null, tgtdeptcd, "", "", sch_userid, "02,03,04,05") != null ) {
				check = true;
			}
		}
		
		result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		result.append("<result value=\"" + check + "\"/>\n");
		
		return result.toString();
	}
	
	/**
	 * ���մ�󷹺� ��������
	 * @param con
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public int getTargetDeptLevel(Connection con, int sysdocno, String deptid) throws Exception {
		int result = 0;
		
		String tgtdeptcd = "";
		
		if ( sysdocno <= 0 ) {
			tgtdeptcd = "M_" + deptid;
		} else {
			tgtdeptcd = ColldocManager.instance().getColldoc(sysdocno).getColdeptcd();
		}
		
		List fgrp = getCommTreatDAO().getFormationGroup(con, "", sysdocno, null, tgtdeptcd, deptid, "", "", "", false);
		
		if ( fgrp != null ) {
			result = ((CommTreatBean)fgrp.get(0)).getLevel();
		}

		return result;
	}
	
	/**
	 * ���մ�󷹺� ��������
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public int getTargetDeptLevel(int sysdocno, String deptid) throws Exception {
		int result = 0;
		Connection con = null;
		
		try {
			con = ConnectionManager.getConnection();
			
			result = getTargetDeptLevel(con, sysdocno, deptid);
		} catch ( Exception e ) {
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		return result; 
	}
	
	/**
	 * ���մ�� ����
	 * @param sessionId
	 * @param sysdocno
	 * @param tgtdeptcd
	 * @param sch_deptid
	 * @param sch_submitsate
	 * @param sch_userid
	 * @param sch_inputstate
	 * @return
	 * @throws Exception
	 */
	public List getFormationGroup(String sessionId, int sysdocno, String userid, String tgtdeptcd, String sch_deptid, String sch_submitsate, String sch_userid, String sch_inputstate) throws Exception {		
		List result = null;
		Connection con = null;
		
		try {
			con = ConnectionManager.getConnection();
			
			result = getFormationGroup(con, sessionId, sysdocno, userid, tgtdeptcd, sch_deptid, sch_submitsate, sch_userid, sch_inputstate);
		} catch(Exception e) {
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		return result;
	}
	
	public List getFormationGroup(Connection con, String sessionId, int sysdocno, String userid, String tgtdeptcd, String sch_deptid, String sch_submitsate, String sch_userid, String sch_inputstate) throws Exception {
		return getCommTreatDAO().getFormationGroup(con, sessionId, sysdocno, userid, tgtdeptcd, sch_deptid, sch_submitsate, sch_userid, sch_inputstate, false);
	}
	
	/**
	 * �������μ� ��������
	 * @param con
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public String getPreDeptcd(int sysdocno, String deptid, boolean isDeliveried) throws Exception {
		String result = null;
		Connection con = null;
		
		try {
			con = ConnectionManager.getConnection();
			
			result = getPreDeptcd(con, sysdocno, deptid, isDeliveried);
		} catch ( Exception e ) {
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		return result; 
	}
	
	/**
	 * �������μ� ��������
	 * @param con
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public String getPreDeptcd(Connection con, int sysdocno, String deptid, boolean isDeliveried) throws Exception {
		return getCommTreatDAO().getPreDeptcd(con, sysdocno, deptid, isDeliveried);
	}
	
	/** 
	 * �������� ����
	 * @param sysdocno : �ý��� ������ȣ
	 * @param usrid : �����ID
	 * @param docstate : ��������(01 : �ۼ���, 02 : ������, 03 : ���δ��, 04 : ��������, 05 : �������, 06 : �����Ϸ�)
	 * @return int : ó�����
	 * @throws Exception : SQL���� ����
	 */
	public int updateDocState(int sysdocno, String usrid, String docstate) throws Exception {
		
		int result = 0;
		
		result = getCommTreatDAO().updateDocState(sysdocno, usrid, docstate);
		
		return result;
	}
	
	/** 
	 * ������� ����
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param submitstate : �������(01 : ��δ��, 02 : �Է�����, 03 : ������, 04 : ���δ��, 05 : ����Ϸ�, 06 : �ݼ�
	 * @return int : ó�����
	 * @throws Exception : SQL���� ����
	 */
	public int updateSubmitState(int sysdocno, String deptcd, String usrid, String submitstate) throws Exception {
		
		int result = 0;
		
		result = getCommTreatDAO().updateSubmitState(sysdocno, deptcd, usrid, submitstate);
		
		return result;
	}
	
	//����μ� ���翩��
	public int IsDocSanctgt(int sysdocno, String deptcd, String userid) throws Exception {
		return getCommTreatDAO().IsDocSanctgt(sysdocno, deptcd, userid);
	}
	
	/**
	 * ����ϱ� �� - ó����Ȳ ����
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return CommTreatBean : ó����Ȳ �����͸� ��� �ִ� Bean
	 * @throws Exception 
	 */
	public CommTreatBean viewCommTreatStatus(int sysdocno, String deptcd) throws Exception {
		CommTreatBean result = new CommTreatBean();
		
		result.setAppntusrnm(getCommTreatDAO().getInputUsrnm(sysdocno, deptcd));
		result.setSancusrnm1(getCommTreatDAO().getApprovalUsrnm("1", sysdocno, deptcd));
		result.setSancusrnm2(getCommTreatDAO().getApprovalUsrnm("2", sysdocno, deptcd));
		
		return result;
	}
	
	/**
	 * �Է¿Ϸ� ����� ����� �����Ѵ�.
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return List : �Է¿Ϸ� ����� ��� ����Ʈ
	 * @throws Exception 
	 */
	public List setInputUsr(int sysdocno, String deptcd) throws Exception{
		List result = new ArrayList();		
		List inusrList = getCommTreatDAO().inputUsr(sysdocno, deptcd);
		
		String chrgunitnm = "";
		String temp = "";
		for(int i=0;i<inusrList.size();i++){
			String[] usrinfo = (String[])inusrList.get(i);					
			usrinfo[0] = Utils.nullToEmptyString(usrinfo[0]);
			
			if ( "".equals(Utils.nullToEmptyString(usrinfo[0])) ) {
				usrinfo[0] = "����������";
			}
			
			if(chrgunitnm.equals(usrinfo[0])){
				temp = temp + ", " + usrinfo[1];
			} else {
				if(i>0){
					InputUsrBean usr = new InputUsrBean();	
					usr.setChrusrnm(temp);
					result.add(usr);
					temp = "";
				}
				temp = usrinfo[0] + " : " + usrinfo[1];				
			}
			
			chrgunitnm = usrinfo[0];
			
			//�������̸� �ٷ� �ִ´�.
			if(i==inusrList.size()-1){
				InputUsrBean usr = new InputUsrBean();	
				usr.setChrusrnm(temp);
				result.add(usr);
			}
		}
		
		return result;
	}
	
	/**
	 * �������� ���չ��� �� - ó����Ȳ ����
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param user_id : �����ID
	 * 
	 * @return CommTreatBean : ó����Ȳ �����͸� ��� �ִ� Bean
	 * @throws Exception 
	 */
	public CommTreatBean viewCommTreatStatus1(int sysdocno, String deptcd, String user_id) throws Exception {
		CommTreatBean result = new CommTreatBean();
		
		result.setSancusrnm1(getCommTreatDAO().getApprovalUsrnm1("1", sysdocno, deptcd, user_id));
		result.setSancusrnm2(getCommTreatDAO().getApprovalUsrnm1("2", sysdocno, deptcd, user_id));
		
		CommTreatBean ctbean = getTgtdeptCnt(sysdocno, deptcd);
		
		result.setTcnt(ctbean.getTcnt());
		result.setScnt(ctbean.getScnt());
		result.setFcnt(ctbean.getFcnt());
		
		return result;
	}
	
	/**
	 * ������Ȳ�Ǽ�
	 * @param sysdocno
	 * @param deptcd
	 * @return
	 * @throws Exception
	 */
	public CommTreatBean getTgtdeptCnt(int sysdocno, String deptcd) throws Exception {
		CommTreatBean result = null;
		Connection con = null;
		
		try {
			con = ConnectionManager.getConnection();
			
			result = getTgtdeptCnt(con, sysdocno, deptcd);
						
		} catch ( Exception e ) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con);
		}
		
		return result;
	}
	
	/**
	 * ������Ȳ�Ǽ�
	 * @param sysdocno
	 * @param deptcd
	 * @return
	 * @throws Exception
	 */
	public CommTreatBean getTgtdeptCnt(Connection con, int sysdocno, String deptcd) throws Exception {
		CommTreatBean result = new CommTreatBean();
		
		int tcnt = 0;
		int scnt = 0;
		int fcnt = 0;
		
		List fg = getCommTreatDAO().getFormationGroup(con, "", sysdocno, null, deptcd, "", "", "", "", true);
		if ( fg != null ) {
			tcnt = fg.size();
		}
		
		fg = getCommTreatDAO().getFormationGroup(con, "", sysdocno, null, deptcd, "", "05,06", "", "", true);
		if ( fg != null ) {
			scnt = fg.size();
		}
		
		fcnt = tcnt - scnt;
		
		result.setTcnt(Integer.toString(tcnt));
		result.setScnt(Integer.toString(scnt));
		result.setFcnt(Integer.toString(fcnt));
		
		return result;
	}
	
	/**
	 * ����Ͻ� ������ ��������
	 */
	public String getTreatStatusDeliveryDT(int sysdocno) throws Exception {
		return getCommTreatDAO().getTreatStatusDeliveryDT(sysdocno);
	}
	
	/**
	 * ��οϷ� �� - ó����Ȳ ������ ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return CommTreatBean
	 * @throws Exception 
	 */
	public CommTreatBean viewDeliCompTreatStatus(int sysdocno, String deptcd) throws Exception {
		CcdManager ccdMgr = CcdManager.instance();
		commapprovalManager commappMgr = commapprovalManager.instance();
		
		CommTreatBean result = new CommTreatBean();
		
		String submitstate  = "";	 //����μ� �������
		String deliverydt 	= "";    //����Ͻ�
		List sanclist1  	= null;  //������ ���
		List sanclist2  	= null;  //������ ���
		List inputuser1 	= null;  //�Է´���� ���
		List inputuser2 	= null;  //���Է´���� ���
		
		//����μ� �������  ��������
		submitstate = ccdMgr.getCcdName("004", getCommTreatDAO().getTgtdeptState(sysdocno, deptcd));
		
		//����Ͻ� ��������
		deliverydt = getTreatStatusDeliveryDT(sysdocno);
		
		//�Է¿Ϸ� ����� ������ ��������
		inputuser1 = setInputUsr(sysdocno, deptcd);
		
		//���Է� ����� ������ ��������
		inputuser2 = getCommTreatDAO().inputUsrX(sysdocno, deptcd);
		
		//������ ������ ��������
		sanclist1 = commappMgr.getTgtApprInfo("1", sysdocno, deptcd);
		
		//������ ������ ��������
		sanclist2 = commappMgr.getTgtApprInfo("2", sysdocno, deptcd);
		
		CommTreatBean ctbean = getTgtdeptCnt(sysdocno, deptcd);
		
		result.setTcnt(ctbean.getTcnt());
		result.setScnt(ctbean.getScnt());
		result.setFcnt(ctbean.getFcnt());
		
		result.setSubmitstate(submitstate);
		result.setDeliverydt(deliverydt);
		result.setInputuser1(inputuser1);
		result.setInputuser2(inputuser2);
		result.setSancList1(sanclist1);
		result.setSancList2(sanclist2);
		
		return result;
	}
	
	/**
	 * �Է��ϱ� �� - ó����Ȳ ������ ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param usrid : �����ID
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return CommTreatBean
	 * @throws Exception 
	 */
	public CommTreatBean viewInputTreatStatus(int sysdocno, String usrid, String deptcd) throws Exception {
		CcdManager ccdMgr = CcdManager.instance();
		commapprovalManager commappMgr = commapprovalManager.instance();
		
		CommTreatBean result = null;
		
		String submitstate  = "";	 //����μ� �������
		List sanclist1  	= null;  //������ ���
		List sanclist2  	= null;  //������ ���
		List inputuser1 	= null;  //�Է´���� ���
		List inputuser2 	= null;  //���Է´���� ���
		
		//����μ� �������  ��������
		submitstate = ccdMgr.getCcdName("004", getCommTreatDAO().getTgtdeptState(sysdocno, deptcd));
		
		//�Է´���ڸ� �� ������ ��������
		result = getCommTreatDAO().getInputChrgunitName(sysdocno, usrid, deptcd);
		
		if (result == null) {
			return null;
		}
		
		//�Է¿Ϸ� ����� ������ ��������
		inputuser1 = setInputUsr(sysdocno, deptcd);
		
		//���Է� ����� ������ ��������
		inputuser2 = getCommTreatDAO().inputUsrX(sysdocno, deptcd);
		
		//������ ������ ��������
		sanclist1 = commappMgr.getTgtApprInfo("1", sysdocno, deptcd);
		
		//������ ������ ��������
		sanclist2 = commappMgr.getTgtApprInfo("2", sysdocno, deptcd);
		
		result.setSubmitstate(submitstate);
		result.setInputuser1(inputuser1);
		result.setInputuser2(inputuser2);
		result.setSancList1(sanclist1);
		result.setSancList2(sanclist2);
		
		return result;
	}
	
	/**
	 * �Է¿Ϸ� �� - ó����Ȳ ������ ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param usrid : �����ID
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return CommTreatBean
	 * @throws Exception 
	 */
	public CommTreatBean viewInputCompTreatStatus(int sysdocno, String usrid, String deptcd) throws Exception {
		commapprovalManager commappMgr = commapprovalManager.instance();
		
		CommTreatBean result = null;
		
		List sanclist1  	= null;  //������ ���
		List sanclist2  	= null;  //������ ���
		List inputuser1 	= null;  //�Է´���� ���
		List inputuser2 	= null;  //���Է´���� ���
		
		//�������/�Է¿Ϸ��Ͻ�/�Է¿Ϸ� ���� ��������
		result = getCommTreatDAO().getInputCompTreatData(sysdocno, deptcd, usrid);
		
		//�Է¿Ϸ� ����� ������ ��������
		inputuser1 = setInputUsr(sysdocno, deptcd);
		
		//���Է� ����� ������ ��������
		inputuser2 = getCommTreatDAO().inputUsrX(sysdocno, deptcd);
		
		//������ ������ ��������
		sanclist1 = commappMgr.getTgtApprInfo("1", sysdocno, deptcd);
		
		//������ ������ ��������
		sanclist2 = commappMgr.getTgtApprInfo("2", sysdocno, deptcd);
		
		result.setInputuser1(inputuser1);
		result.setInputuser2(inputuser2);
		result.setSancList1(sanclist1);
		result.setSancList2(sanclist2);
		
		return result;
	}
	
	/** 
	 * �Է��ϱ� ��(ó����Ȳ) - �Է´���� ������ ���� (INPUTUSR, USR_EXT TABLE)
	 * 
	 * @param chrgunitcd : ������ �������ڵ�
	 * @param usrid : �����ID
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ������	
	 * @throws Exception 
	 */
	public int changeChrgUnit(int chrgunitcd, String usrid, int sysdocno, String deptcd) throws Exception {
		Connection conn = null;
		String chrgunitnm = "";
		int result = 0;
		
		chrgunitnm = getCommTreatDAO().getChrgunitName(deptcd, chrgunitcd);
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			result += getCommTreatDAO().changeInputusrChrgunit(conn, chrgunitcd, chrgunitnm, usrid, sysdocno, deptcd);
			result += getCommTreatDAO().changeUsrextChrgunit(conn, chrgunitcd, usrid);
			result += getCommTreatDAO().changeDataLineFrmChrgunit(conn, chrgunitcd, usrid, sysdocno, deptcd);
			result += getCommTreatDAO().changeDataFixedFrmChrgunit(conn, chrgunitcd, usrid, sysdocno, deptcd);
			result += getCommTreatDAO().changeDataBookFrmChrgunit(conn, chrgunitcd, usrid, sysdocno, deptcd);
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				e = ex;
			}
			logger.error("ERROR",e);
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception ex) {
				logger.error("ERROR", ex);
				ConnectionManager.close(conn);
				throw ex;
			}
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/**
	 * �������� ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * 
	 * @return CommTreatBean ��������
	 * @throws Exception 
	 */
	public CommTreatBean getDocStates(int sysdocno, String deptcd) throws Exception{
		CommTreatBean result = new CommTreatBean();
		
		CommTreatBean ctbean = getTgtdeptCnt(sysdocno, deptcd);
		
		result.setTcnt(ctbean.getTcnt());
		result.setScnt(ctbean.getScnt());
		result.setFcnt(ctbean.getFcnt());
		
		result.setDocstate(getCommTreatDAO().getDocState(sysdocno).getDocstate());
		result.setDocstatenm(getCommTreatDAO().getDocState(sysdocno).getDocstatenm());
		result.setDeliverydt(getCommTreatDAO().getDocState(sysdocno).getDeliverydt());
		result.setEnddt(getCommTreatDAO().getDocState(sysdocno).getEnddt());
		
		return result;	 
	}

	/**
	 * �������� ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * 
	 * @return CommTreatBean ��������
	 * @throws Exception 
	 */
	public String getDocState(int sysdocno) throws Exception{
		return getCommTreatDAO().getDocState(sysdocno).getDocstate();
	}
	
	/**
	 * �������� ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * 
	 * @param gbn : ���ⱸ�� - �����ü(all),�Ϸ�(comp),������(nocomp)
	 * 
	 * @return List : ��������(CollprocDeptStatusBean)
	 * @throws Exception 
	 */
	public List getTgtdeptList(String deptcd, int sysdocno, String user_id, String gbn) throws Exception {
		return getFormationGroup("", sysdocno, user_id, deptcd , "", gbn, "", "");
	}	

	/** 
	 * ����μ� �������ɿ��� ����	
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param tgtdeptcd : �μ��ڵ�
	 * @param modifyyn : �������ɿ���
	 * 
	 * @return ����
	 * @throws Exception 
	 */
	public void updateTgtdept(int sysdocno, String tgtdeptcd, String modifyyn) throws Exception {
		getCommTreatDAO().updateTgtdept(sysdocno, tgtdeptcd, modifyyn);
	}	

	/**
	 * ������� �������� ��������
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return String : �������� ���� 
	 * @throws Exception 
	 */
	public String getTgtModifyYn(int sysdocno, String deptcd) throws Exception {
		return getCommTreatDAO().getTgtModifyYn(sysdocno, deptcd);
	}

	/**
	 * ������� ��������
	 */
	public String getTgtdeptState(int sysdocno, String deptcd) throws Exception {
		return getCommTreatDAO().getTgtdeptState(sysdocno, deptcd);
	}
	
	/**
	 * ������ - ó����Ȳ ����
	 * 
	 * @param gbn : ��ι���(1), ���⹮��(2)
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * 
	 * @return CommTreatBean
	 * @throws Exception 
	 */
	public CommTreatBean appTreatView(String gbn, int sysdocno, String deptcd, String usrid) throws Exception {
		commapprovalManager commappMgr = commapprovalManager.instance();
		CcdManager ccdMgr = CcdManager.instance();
		CommTreatBean result = new CommTreatBean();
		
		String state    = "";    //�������� ����
		String submitdt = "";    //����Ͻ�
		List sanclist1  = null;  //������ ���
		List sanclist2  = null;  //������ ���
		List inputuser1 = null;  //�Է´���� ���
		List inputuser2 = null;  //���Է´���� ���			
		String gbntemp  = "";		
		
		if("1".equals(gbn)){
			//��ι���
			if("02".equals(state)) {gbntemp = "1";} else if("03".equals(state)) {gbntemp = "2";}  
			//gbntemp: ����(1), ����(2)
			submitdt  = commappMgr.getColSubmitDt(sysdocno, gbntemp, usrid);			
			state = ccdMgr.getCcdName("003", getDocState(sysdocno));
			sanclist1 = commappMgr.getColApprInfo("1", sysdocno);
			sanclist2 = commappMgr.getColApprInfo("2", sysdocno); 
		} else {
			//���⹮��
			if("03".equals(state)) {gbntemp = "1";} else if("04".equals(state)) {gbntemp = "2";}  
			//gbntemp: ����(1), ����(2)
			submitdt  = commappMgr.getTgtSubmitDt(sysdocno, deptcd, gbntemp, usrid);
			state = ccdMgr.getCcdName("004", getTgtdeptState(sysdocno, deptcd));
			sanclist1 = commappMgr.getTgtApprInfo("1", sysdocno, deptcd); 
			sanclist2 = commappMgr.getTgtApprInfo("2", sysdocno, deptcd);	
			inputuser1 = setInputUsr(sysdocno, deptcd);
			inputuser2 = getCommTreatDAO().inputUsrX(sysdocno, deptcd);			
		}
				
		result.setSubmitstate(state);
		result.setSubmitdt(submitdt);
		result.setSancList1(sanclist1);
		result.setSancList2(sanclist2);
		result.setInputuser1(inputuser1);
		result.setInputuser2(inputuser2);
			
		return result;
	}	
}
