/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� manager
 * ����:
 */
package nexti.ejms.delivery.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;

public class DeliveryManager {
	
	private static Logger logger = Logger.getLogger(DeliveryManager.class);
	private static DeliveryManager instance = null;
	private static DeliveryDAO dao = null;
	
	public static DeliveryManager instance() {
		if (instance==null) instance = new DeliveryManager(); 
		return instance;
	}
	
	private DeliveryDAO getDeliveryDAO(){
		if (dao==null) dao = new DeliveryDAO(); 
		return dao;
	}
	
	private DeliveryManager() {
	}
	
	/**
	 * ����ϱ� ���
	 * 
	 * @param deptcd : �μ��ڵ�
	 * @param start : ����Ʈ ��������
	 * @param end : ����Ʈ ��������
	 * 
	 * @return List : ��� ��� ����Ʈ
	 * @throws Exception 
	 */
	public List deliveryList(String deptcd, String isSysMgr, String sch_deptcd, int start, int end) throws Exception{		
		return getDeliveryDAO().deliveryList(deptcd, isSysMgr, sch_deptcd, start, end);
	}
	
	/**
	 * ����ϱ� �� - �ݼ� �˾�â ������ ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * 
	 * @return DeliveryRetDocViewBean : �ݼ� �˾�â �����͸� ��� �ִ� Bean
	 * @throws Exception 
	 */
	public DeliveryRetDocViewBean viewReturnDoc(int sysdocno) throws Exception {
		return getDeliveryDAO().viewReturnDoc(sysdocno);
	}
	
	/** 
	 * ����ϱ� �� - �ݼ� ó��
	 * 
	 * @param RetDocBean : �ݼ� ����/���� ���¸� ��� �ִ� Bean
	 * @param usrid : �����ID
	 * 
	 * @return int : ó�����
	 * @throws Exception 
	 */
	public int deliveryReturnDoc(DeliveryRetDocBean RetDocBean, String usrid) throws Exception {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			result = getDeliveryDAO().deliveryReturnDoc(conn, RetDocBean, usrid);
			
			if(this.IsLastDeliveryDept(conn, RetDocBean.getSysdocno(), RetDocBean.getTgtdeptcd())) {
				result = updateDocState(conn, RetDocBean.getSysdocno(), "05", usrid);
			}
			
			try { conn.commit(); } catch (Exception e) {}
		} catch (SQLException e) {
			try { conn.rollback(); } catch (Exception ex) {}
		} finally {
			try { conn.setAutoCommit(true); } catch (Exception ex) {}
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/** 
	 * ���ó���� �������� ����
	 * 
	 * @param conn : Connection ��ü
	 * @param sysdocno : �ý��� ������ȣ
	 * @param docstate : ������ ��������
	 * @param usrid : �����ID
	 * 
	 * @return int : ó����� 
	 * @throws Exception : SQL ���� ����
	 */
	public int updateDocState(Connection conn, int sysdocno, String docstate, String usrid) throws Exception {
		return getDeliveryDAO().updateDocState(conn, sysdocno, docstate, usrid);
	}
	
	/** 
	 * �����Լ� - ������ ����μ����� �ƴ��� ���� üũ
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return true : ������ �μ� O, false : ������ �μ� X	
	 * @throws Exception 
	 */
	public boolean IsLastDeliveryDept(Connection conn, int sysdocno, String deptcd) throws Exception {
		return getDeliveryDAO().IsLastDeliveryDept(conn, sysdocno, deptcd);
	}
	
	public boolean IsLastDeliveryDept(int sysdocno, String deptcd) throws Exception {
		Connection conn = null;
		boolean result = false;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			IsLastDeliveryDept(conn, sysdocno, deptcd);
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}

			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/** 
	 * ����ϱ� �� - ���
	 * 
	 * @param submitstate : ������ ����μ� ����
	 * @param appusrnm : �Է´�������� ����
	 * @param usrid : �����ID
	 * @param sysdocno : �ý��� ������ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ó�����
	 * @throws Exception 
	 */
	public int deliveryProcess(String submitstate, String appusrnm, String usrid, int sysdocno, String deptcd) throws Exception {
		 return getDeliveryDAO().deliveryProcess(submitstate, appusrnm, usrid, sysdocno, deptcd);
	}
	
	public int deliveryProcess(Connection conn, String submitstate, String appusrnm, String usrid, int sysdocno, String sessionId, String deptcd) throws Exception {
		return getDeliveryDAO().deliveryProcess(conn, submitstate, appusrnm, usrid, sysdocno, sessionId, deptcd);
	}
	
	/** 
	 * ����ϱ� �� - �������� �ʰ� üũ, �Է´���� ���� �� ����/������ ���� ���� Ȯ��
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return String : OVERTIME, NONINPUTUSR, NONAPPROVAL, OK
	 * @throws Exception 
	 */
	public String IsAssignCharge(int sysdocno, String deptcd) throws Exception {
		String retCode = "OK";

		if(!getDeliveryDAO().IsAssignEndDt(sysdocno)) {
			return "OVERTIME";
		}
		
		if(!getDeliveryDAO().IsAssignInputUsrcharge(sysdocno, deptcd)) {
			return "NONINPUTUSR";
		}

		if(!getDeliveryDAO().IsAssignApprovalUsr(sysdocno, deptcd)) {
			return "NONAPPROVAL";
		}
		
		return retCode;
	}
	
	/**
	 * ��οϷ� ���
	 * 
	 * @param deptcd : �μ��ڵ�
	 * @param start : ����Ʈ ��������
	 * @param end : ����Ʈ ��������
	 * 
	 * @return List : ��οϷ� ��� ����Ʈ
	 * @throws Exception 
	 */
	public List deliveryCompList(String deptcd, String isSysMgr, String sch_deptcd, int start, int end) throws Exception {
		return getDeliveryDAO().deliveryCompList(deptcd, isSysMgr, sch_deptcd, start, end);
	}
	
	/**
	 * ����ϱ� �� - ��������/�����˸��� ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * 
	 * @return DeliveryDetailBean : ��������/�����˸����� ��� �ִ� Bean
	 * @throws Exception 
	 */
	public DeliveryDetailBean viewDeliveryDetail(int sysdocno) throws Exception {
		return getDeliveryDAO().viewDeliveryDetail(sysdocno);
	}
	
	/** 
	 * ����ϱ� ��� ���� ��������
	 * 
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ����ϱ� ��� �Ѱ���
	 * @throws Exception 
	 */
	public int deliCnt(String deptcd, String isSysMgr, String sch_deptcd) throws Exception{
		return getDeliveryDAO().deliCnt(deptcd, isSysMgr, sch_deptcd);
	}

	/** 
	 * ����ϱ� ��� �Ѱ��� ��������
	 * 
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ����ϱ� ��� �Ѱ���
	 * @throws Exception 
	 */
	public int deliTotCnt(String deptcd) throws Exception{
		return getDeliveryDAO().deliTotCnt(deptcd);
	}
	

	/** 
	 * ��οϷ� ��� �Ѱ��� ��������
	 * 
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ��οϷ� ��� �Ѱ��� 
	 * @throws Exception 
	 */
	public int deliCompCnt(String deptcd, String isSysMgr, String sch_deptcd) throws Exception {
		return getDeliveryDAO().deliCompCnt(deptcd, isSysMgr, sch_deptcd);
	}
	
	/** 
	 * ��οϷ� ��� �Ѱ��� ��������
	 * 
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return int : ��οϷ� ��� �Ѱ��� 
	 * @throws Exception 
	 */
	public int deliCompTotCnt(String deptcd) throws Exception {
		return getDeliveryDAO().deliCompTotCnt(deptcd);
	}
	
	/**
	 * ����ϱ� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� �������� 
	 */
	public String getSearchDelivery(String sch_deptcd) throws Exception{
		return getDeliveryDAO().getSearchDelivery(sch_deptcd);
	}

	/**
	 * ��οϷ� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� �������� 
	 */
	public String getSearchDeliveryComp(String sch_deptcd) throws Exception{
		return getDeliveryDAO().getSearchDeliveryComp(sch_deptcd);
	}
}
