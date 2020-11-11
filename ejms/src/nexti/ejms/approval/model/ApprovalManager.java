/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ϱ� manager
 * ����:
 */
package nexti.ejms.approval.model;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.commtreat.model.CommTreatManager;

public class ApprovalManager {
	
	private static Logger logger = Logger.getLogger(ApprovalManager.class);
	
	private static ApprovalManager instance = null;
	private static ApprovalDAO dao = null;
	
	public static ApprovalManager instance() {
		if (instance==null) instance = new ApprovalManager(); 
		return instance;
	}
	
	private ApprovalDAO getApprovalDAO(){
		if (dao==null) dao = new ApprovalDAO(); 
		return dao;
	}
	
	private ApprovalManager() {
	}
	
	/**
	 * ���պμ� ����Ϸ� ���� üũ
	 * gubun(1) : ����, gubun(2) : ����
	 * @param sysdocno �ý��۹�����ȣ
	 * @param gubun ����
	 * @return ����Ϸ����Ϸ�sysdocno,sancusrid(1��)
	 */
	public String[] isColSancComplete(int sysdocno, String gubun) throws Exception {
		String[] result = null;
		
		result = getApprovalDAO().isColSancComplete(sysdocno, gubun);
		
		return result;
	}
	
	/**
	 * ����μ� ����Ϸ� ���� üũ
	 * gubun(1) : ����, gubun(2) : ����
	 * @param sysdocno �ý��۹�����ȣ
	 * @param tgtdeptcd ����μ��ڵ�
	 * @param gubun ����
	 * @return ����Ϸ����Ϸ�sysdocno,tgtdeptcd,sancusrid(1��)
	 */
	public String[] isTgtSancComplete(int sysdocno, String tgtdeptcd, String gubun) throws Exception {
		String[] result = null;
		
		result = getApprovalDAO().isTgtSancComplete(sysdocno, tgtdeptcd, gubun);
		
		return result;
	}
	
	/**
	 * �����ϱ� ���
	 * @throws Exception 
	 */
	public List approvalList(SearchBean search, String deptcd) throws Exception{
		List result = null;
		
		result = getApprovalDAO().appList(search, deptcd);
		
		return result;		
	}
	
	/**
	 * �����ϱ� ��� ���� ��������	
	 * @throws Exception 
	 */
	public int appTotCnt(SearchBean search, String deptcd) throws Exception{
		int result = 0;
		
		result = getApprovalDAO().appTotCnt(search, deptcd);
		
		return result;
	}
	
	/**
	 * ����Ϸ� ���
	 * @throws Exception 
	 */
	public List appCompList(SearchBean search, String deptcd) throws Exception{
		List result = null;
		
		result = getApprovalDAO().appCompList(search, deptcd);
		
		return result;		
	}
	
	/**
	 * ����Ϸ� ��� ���� ��������	
	 * @throws Exception 
	 */
	public int appCompTotCnt(SearchBean search, String deptcd) throws Exception{
		int result = 0;
		
		result = getApprovalDAO().appCompTotCnt(search, deptcd);
		
		return result;
	}
	
	/**
	 * �����ϱ�
	 * gbn: ���պμ�(1), ����μ�(2)
	 * @throws Exception 
	 */
	public int doSanc(String gbn, int sysdocno, String deptcd, String sancusrid) throws Exception{
		int result = 0;
		String status = "";
		if("1".equals(gbn)){
			//���պμ� ����
			status = CommTreatManager.instance().getDocState(sysdocno);
			
			if("02".equals(status)){
				//����
				result = getApprovalDAO().doColSanc(sysdocno, "1", deptcd, sancusrid);
			} else if("03".equals(status)) {
				//����
				result = getApprovalDAO().doColSanc(sysdocno, "2", deptcd, sancusrid);
			}
		} else {
			//����μ� ����
			status = CommTreatManager.instance().getTgtdeptState(sysdocno, deptcd);
			
			if("03".equals(status)){
				//����
				result = getApprovalDAO().doTgtSanc(sysdocno, "1", deptcd, sancusrid);
			} else if("04".equals(status)){
				//����
				result = getApprovalDAO().doTgtSanc(sysdocno, "2", deptcd, sancusrid);
			}
		}
		
		return result;
	}
	
	/** 
	 * ���պμ� ������� üũ(������ ������ ���� üũ�Ͽ� ó��)
	 * gubun : ����(1), ����(2)	
	 * @throws Exception 
	 */
	public int doLastColSancCheck(int sysdocno, String gubun, String deptcd, String sancusrid) throws Exception  {
		
		int result =0;
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			result = getApprovalDAO().doLastColSancCheck(conn, sysdocno, gubun, deptcd, sancusrid);
			
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
	 * ����μ� ������� üũ(������ ������ ���� üũ�Ͽ� ó��)
	 * gubun : ����(1), ����(2)	
	 * @throws Exception 
	 */
	public int doLastTgtSancCheck(int sysdocno, String gubun, String deptcd, String sancusrid) throws Exception  {
		
		int result =0;
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			result = getApprovalDAO().doLastTgtSancCheck(conn, sysdocno, gubun, deptcd, sancusrid);
			
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
}
